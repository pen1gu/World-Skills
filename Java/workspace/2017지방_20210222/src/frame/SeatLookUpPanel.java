package frame;

import static frame.BaseFrame.connection;
import static frame.BaseFrame.createButton;
import static frame.BaseFrame.createComponent;
import static frame.BaseFrame.createLabel;
import static frame.BaseFrame.userName;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.JLabel;
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
	JTextField tfCarnum_under = new JTextField();
	static JTextField tfUserId = new JTextField();

	TableData tableData;

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
		southPanel.add(createComponent(tfCarnum_under, 90, 20));
		southPanel.add(new JLabel("회원ID"));
		southPanel.add(createComponent(tfUserId, 90, 20));
		southPanel.add(createButton("예약", e -> clickSubmit()));

		tfCarNum.setText(tableData.getCarNum());
		tfCarPrimary.setText(tableData.getCarPrimary());

		tableData.formatDate();
		tfStartDate.setText(tableData.getDate());

		tfUserId.setText(userName);

		add(northPanel, BorderLayout.NORTH);
		add(centerPanel);
		add(southPanel, BorderLayout.SOUTH);
		lookUpSeat();
	}

	public void clickSubmit() {

	}

	public void lookUpSeat() {
		if (tfCarNum.getText().isEmpty() || tfCarPrimary.getText().isEmpty() || tfStartDate.getText().isEmpty()) {
			return;
		}

		model.setRowCount(0);

		ArrayList<Integer> list = new ArrayList<Integer>();
		try (PreparedStatement pst = connection.prepareStatement(
				"select bseat from tbl_ticket where bNumber = ? and bDate = bDate = DATE_FORMAT(?,'%Y%m%d') and bNumber2 = ?;")) {
			pst.setObject(1, tfCarPrimary.getText());
			pst.setObject(2, tfStartDate.getText());
			pst.setObject(3, tfCarNum.getText());

			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				list.add(rs.getInt(1));
			}

			int count = 0;

			for (int i = 0; i < 5; i++) {
				model.addRow(new Object[] { ++count, "", ++count, "", "--", ++count, "", ++count, "" });
				for (int j = 0; j < 9; i++) {
					if (j == 0 || j == 2 || j == 5 || j == 7) {
						table.getColumnModel().getColumn(j).setPreferredWidth(100);
					}
				}
			}

			repaint();
//			System.out.println(model.getValueAt(1, 1));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
