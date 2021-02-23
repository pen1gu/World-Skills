package frame;

import static frame.BaseFrame.*;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import model.TableData;

public class SeatLookUpPanel extends JPanel {

	static JTextField tfCarPrimary;
	static JTextField tfStartDate;
	static JTextField tfCarNum;

	DefaultTableModel model = new DefaultTableModel(
			new String[] { "좌측창가", "예약", "좌측통로", "메인", "통로", "우측창가", "예약", "우측통로", "예약" }, 0);

	JTable table = new JTable(model);

	JTextField tfStartDate_under = new JTextField();
	JTextField tfPrimary_under = new JTextField();
	JTextField tfSeatNum = new JTextField();
	JTextField tfUserId = new JTextField();

	TableData tableData;

	ArrayList<Integer> reservationList = new ArrayList<Integer>();
	Map<String, Integer> priceMap = new HashMap<String, Integer>();

	public SeatLookUpPanel(TableData tableData) {
		this.tableData = tableData;

		setPreferredSize(new Dimension(800, 520));
		setLayout(new BorderLayout());

		JPanel northPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel southPanel = createComponent(new JPanel(new FlowLayout(FlowLayout.LEFT)), 800, 40);

		northPanel.add(createLabel(new JLabel("좌석표 선택"), new Font("맑은 고딕", 1, 20)));

		centerPanel.add(createComponent(new JLabel("차량번호", JLabel.RIGHT), 90, 20));
		centerPanel.add(createComponent(tfCarPrimary = new JTextField(), 90, 20));
		centerPanel.add(new JLabel("차량일자"));
		centerPanel.add(createComponent(tfStartDate = new JTextField(), 90, 20));
		centerPanel.add(new JLabel("호차번호"));
		centerPanel.add(createComponent(tfCarNum = new JTextField(), 90, 20));
		centerPanel.add(createButton("좌석조회", e -> lookUpSeat()));

		JScrollPane scrollPane = createComponent(new JScrollPane(table), 570, 280);
		centerPanel.add(scrollPane);

		southPanel.add(createComponent(new JLabel("출발일자", JLabel.RIGHT), 70, 20));
		southPanel.add(createComponent(tfStartDate_under, 90, 20));
		southPanel.add(new JLabel("차량번호"));
		southPanel.add(createComponent(tfPrimary_under, 90, 20));
		southPanel.add(new JLabel("좌석번호"));
		southPanel.add(createComponent(tfSeatNum, 90, 20));
		southPanel.add(new JLabel("회원ID"));
		southPanel.add(createComponent(tfUserId, 90, 20));
		southPanel.add(createButton("예약", e -> clickSubmit()));

		tfCarNum.setText(tableData.getCarNum());
		tfCarPrimary.setText(tableData.getCarPrimary());

		tableData.formatDate();
		tfStartDate.setText(tableData.getDate());

		tfUserId.setText(userId);

		try {
			ResultSet rs = statement.executeQuery("select bPrice from tbl_bus;");
			int i = 1;
			while (rs.next()) {
				priceMap.put(String.format("A0%02d", i), rs.getInt(1));
				i++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		add(northPanel, BorderLayout.NORTH);
		add(centerPanel);
		add(southPanel, BorderLayout.SOUTH);
		lookUpSeat();
	}

	public void clickSubmit() {
		if (tfSeatNum.getText().trim().isEmpty()) {
			informationMessage("좌석번호를 입력해주십시오.");
			return;
		}

		String[] seatArr = tfSeatNum.getText().trim().split(",|, | ");
		Integer[] seatArr_int = new Integer[seatArr.length];
		int count = 0;
		try {
			for (int i = 0; i < seatArr.length; i++) {
				if (seatArr[i].equals("") && i != seatArr.length - 1) {
					count++;
					continue;
				}

				seatArr_int[i - count] = Integer.parseInt(seatArr[i]);
				if (seatArr_int[i - count] > 20 || seatArr_int[i - count] < 0)
					throw new Exception();
			}
		} catch (Exception e) {
			informationMessage("올바르지 못한 좌석정보입니다.");
			return;
		}

		String duplicateArr = duplicateCheck(seatArr_int);

		if (duplicateArr.length() > 0) {
			informationMessage("좌석번호 " + duplicateArr.substring(2, duplicateArr.length()) + "이(가) 중복입력되었습니다.");
			return;
		}

		String result = "";
		for (int i = 0; i < reservationList.size(); i++) {
			for (int j = 0; j < seatArr_int.length; j++) {
				if (reservationList.get(i) == seatArr_int[j]) {
					result += reservationList.get(i) + " ";
					break;
				}
			}
		}

		if (result.length() > 0) {
			informationMessage("좌석번호 " + result.trim().replace(" ", ",").substring(0, result.length() - 1)
					+ "은 이미 예약되어 있는 좌석입니다.");
			return;

		}

		int yesNo = JOptionPane.showConfirmDialog(null,
				"차량번호[" + tfCarPrimary.getText() + "]\n예약일자[" + tfStartDate_under.getText() + "]\n좌석번호["
						+ tfSeatNum.getText().trim().replace(" ", ",") + "]\n고객번호[" + tfUserId.getText()
						+ "]\n예약하시겠습니까?",
				"웹 페이지 메시지", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

		if (yesNo != JOptionPane.YES_OPTION) {
			return;
		}

		for (int i = 0; i < seatArr_int.length; i++) {
			try (PreparedStatement pst = connection.prepareStatement("insert into tbl_ticket values(?,?,?,?,?,?,?)")) {
				pst.setObject(1, tfStartDate_under.getText());
				pst.setObject(2, tfCarPrimary.getText());
				pst.setObject(3, tfCarNum.getText());
				pst.setObject(4, seatArr[i]);
				pst.setObject(5, tfUserId.getText());
				pst.setObject(6, priceMap.get(tfCarPrimary.getText()));
				pst.setObject(7, "X");

				pst.execute();

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		JOptionPane.showMessageDialog(null,
				"차량번호[" + tfCarPrimary.getText() + "]\n예약일자[" + tfStartDate_under.getText() + "]\n좌석번호["
						+ tfSeatNum.getText().trim().replace(" ", ",") + "]\n고객번호[" + tfUserId.getText()
						+ "]\n예약되었습니다.",
				"웹 페이지 메시지", JOptionPane.WARNING_MESSAGE);

		MainFrame.card.show(MainFrame.centerPanel, "ticket");
		MainFrame.toggleButtons[2].setSelected(false);
		MainFrame.toggleButtons[3].setSelected(true);
	}

	public void lookUpSeat() {
		if (tfCarNum.getText().isEmpty() || tfCarPrimary.getText().isEmpty() || tfStartDate.getText().isEmpty()) {
			return;
		}

		model.setRowCount(0);
		reservationList.clear();

		try (PreparedStatement pst = connection.prepareStatement(
				"select distinct bseat from tbl_ticket where bNumber = ? and bDate = DATE_FORMAT(?,'%Y%m%d') and bNumber2 = ?;")) {
			pst.setObject(1, tfCarPrimary.getText());
			pst.setObject(2, tfStartDate.getText());
			pst.setObject(3, tfCarNum.getText());

			ResultSet rs = pst.executeQuery();

			ArrayList<Integer> list = new ArrayList<Integer>();

			while (rs.next()) {
				list.add(rs.getInt(1));
				reservationList.add(rs.getInt(1));
			}

			int count = 1, showCount = 1;

			for (int i = 0; i < 5; i++) {
				String[] arr = new String[4];

				for (int j = 0; j < 4; j++) {
					for (int k = 0; k < list.size(); k++) {
						if (count == list.get(k)) {
							arr[(list.get(k) - 1) % 4] = "O";
							list.remove(k);
						}
					}
					count++;
				}
				model.addRow(new Object[] { showCount++, arr[0], showCount++, arr[1], "--", showCount++, arr[2],
						showCount++, arr[3] });
			}

			for (int j = 0; j < 9; j++) {
				if (j == 0 || j == 2 || j == 5 || j == 7) {
					table.getColumnModel().getColumn(j).setPreferredWidth(120);
				}
				table.getColumnModel().getColumn(j).setCellRenderer(centerRender);
			}

			repaint();
		} catch (Exception e) {
			e.printStackTrace();
		}
		tfStartDate_under.setEnabled(false);
		tfPrimary_under.setEnabled(false);

		tfStartDate_under.setText(tfStartDate.getText());
		tfPrimary_under.setText(tfCarPrimary.getText());
	}

	private String duplicateCheck(Integer[] arr) {
		Arrays.sort(arr);

		StringBuilder builder = new StringBuilder();

		List<Integer> duplicateList = new ArrayList<Integer>();
		for (int i = 0; i < arr.length; i++) {
			for (int j = i + 1; j < arr.length; j++) {
				if (arr[i].equals(arr[j])) {
					if (duplicateList.contains(arr[j]))
						break;

					builder.append(", ");
					builder.append(arr[j]);
					duplicateList.add(arr[j]);
					break;
				}
			}
		}

		return builder.toString();
	}
}