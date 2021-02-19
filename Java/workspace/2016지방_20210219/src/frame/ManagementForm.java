package frame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;

import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class ManagementForm extends BaseFrame {

	JTextField[] fields = new JTextField[5];
	JComboBox<String>[] comboboxes = new JComboBox[3];

	DefaultTableModel model = new DefaultTableModel(
			new String[] { "n", "custoomerCode", "contractName", "regPrice", "regDate", "monthPrice", "adminName" }, 0);
	JTable table = new JTable(model);

	public ManagementForm() {
		super(800, 800, "보험 계약");

		setLayout(new FlowLayout());

		JPanel northPanel = createComponent(new JPanel(new GridLayout(1, 0)), 500, 80);
		JPanel north_west = new JPanel(new GridLayout(0, 2));
		JPanel north_east = new JPanel(new GridLayout(0, 2));

		north_west.add(new JLabel("고객코드:", 4));
		north_west.add(fields[0] = new JTextField());
		north_west.add(new JLabel("고 객 명:", 4));
		north_west.add(comboboxes[0] = new JComboBox<String>());
		north_west.add(new JLabel("생년월일:", 4));
		north_west.add(fields[1] = new JTextField());
		north_west.add(new JLabel("연 락 처:", 4));
		north_west.add(fields[2] = new JTextField());

		north_east.add(new JLabel("보험상품:", 4));
		north_east.add(comboboxes[1] = new JComboBox<String>());
		north_east.add(new JLabel("가입금액:", 4));
		north_east.add(fields[3] = new JTextField());
		north_east.add(new JLabel("월보험료:", 4));
		north_east.add(fields[4] = new JTextField());

		northPanel.add(north_west, BorderLayout.WEST);
		northPanel.add(north_east, BorderLayout.EAST);

		JPanel centerPanel = createComponent(new JPanel(), 600, 40);

		centerPanel.add(new JLabel("담당자 :"));
		centerPanel.add(comboboxes[2] = new JComboBox<String>());
		centerPanel.add(createButton("가입", e -> clickJoin()));
		centerPanel.add(createButton("삭제", e -> clickDelete()));
		centerPanel.add(createButton("파일로저장", e -> clickSaveFile()));
		centerPanel.add(createButton("닫기", e -> openFrame(new MainForm())));

		JPanel southPanel = createComponent(new JPanel(), 800, 680);

		setUserList();

		comboboxes[0].addActionListener(e -> setUserData());
		comboboxes[1].addActionListener(e -> setContractData());

		fields[3].setText("");
		fields[4].setText("");

		for (int i = 0; i < fields.length; i++) {
			fields[i].setEnabled(false);
		}

		DefaultTableCellRenderer center = new DefaultTableCellRenderer();
		center.setHorizontalAlignment(SwingConstants.CENTER);

		table.removeColumn(table.getColumn("n"));
		for (int i = 0; i < 6; i++) {
			table.getColumnModel().getColumn(i).setCellRenderer(center);
		}

		southPanel.add(new JLabel("<고객 보험 계약현황>", 0), BorderLayout.NORTH);

		JScrollPane pane = createComponent(new JScrollPane(table), 780, 600);
		southPanel.add(pane);

		add(northPanel);
		add(centerPanel);
		add(southPanel);
	}

	public void clickJoin() {
		if (fields[3].getText().isEmpty() || fields[4].getText().isEmpty()) {
			return;
		}

		try (PreparedStatement pst = connection.prepareStatement("insert into contract values(?,?,?,?,?,?)")) {
			pst.setObject(1, fields[0].getText());
			pst.setObject(2, comboboxes[1].getSelectedItem());
			pst.setObject(3, fields[3].getText());
			pst.setObject(4, String.format("%d-%02d-%02d", LocalDate.now().getYear(), LocalDate.now().getMonthValue(),
					LocalDate.now().getDayOfMonth()));
			pst.setObject(5, fields[4].getText());
			pst.setObject(6, comboboxes[2].getSelectedItem());

			pst.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}

		setTableUser();
	}

	public void clickDelete() {
		int yesNo = JOptionPane
				.showConfirmDialog(
						null, table.getValueAt(table.getSelectedRow(), 0) + "("
								+ table.getValueAt(table.getSelectedRow(), 1) + ")" + "을 삭제하시겠습니까?",
						"계약정보 삭제", JOptionPane.YES_NO_OPTION);

		if (yesNo != JOptionPane.YES_OPTION) {
			return;
		}

		try {
			statement
					.execute("delete from contract where contractName = '" + table.getValueAt(table.getSelectedRow(), 1)
							+ "' and regDate = '" + table.getValueAt(table.getSelectedRow(), 3) + "'");
		} catch (Exception e) {
			e.printStackTrace();
		}
		setTableUser();
	}

	public void clickSaveFile() {
		JFileChooser fileChooser = new JFileChooser();

		int nRow = model.getRowCount(), nCol = model.getColumnCount();
		Object[][] tableData = new Object[nRow][nCol];

		fileChooser.setDialogTitle("텍스트 파일로 저장하기");
		int userSelection = fileChooser.showSaveDialog(null);

		if (userSelection == JFileChooser.APPROVE_OPTION) {
			File f = fileChooser.getSelectedFile();
			try {
				FileWriter writer = new FileWriter(f.getPath() + ".txt");
				writer.write("고객명 : " + comboboxes[0].getSelectedItem() + "(" + fields[0].getText() + ")\n\n");
				writer.write("담당자명 : " + comboboxes[2].getSelectedItem() + "\n\n");

				writer.write("보험상품\t가입금액\t가입일\t월보혐료\t\n");

				for (int i = 0; i < nRow; i++) {
					for (int j = 0; j < nCol; j++) {
						writer.write(model.getValueAt(i, j).toString() + "\t");
					}
					writer.write("\n");
				}
				writer.flush();
				writer.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void setUserList() {
		try {
			ResultSet rs = statement.executeQuery("select name from customer order by name");
			while (rs.next()) {
				comboboxes[0].addItem(rs.getString(1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (String element : new String[] { "무배당암보험", "변액연금보험", "여성건강보험", "연금보험", "의료설비보험", "종신보험" }) {
			comboboxes[1].addItem(element);
		}

		try {
			ResultSet rs = statement.executeQuery("select name from admin order by name;");
			while (rs.next()) {
				comboboxes[2].addItem(rs.getString(1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		setUserData();
	}

	public void setContractData() {
		try {
			ResultSet rs = statement.executeQuery("select regPrice,monthPrice from contract where contractName = '"
					+ comboboxes[1].getSelectedItem() + "'");
			if (rs.next()) {
				fields[3].setText(rs.getString(1));
				fields[4].setText(rs.getString(2));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setUserData() {
		try (PreparedStatement pst = connection
				.prepareStatement("select code, birth, tel from customer where name = ?")) {
			pst.setObject(1, comboboxes[0].getSelectedItem());

			ResultSet rs = pst.executeQuery();

			if (rs.next()) {
				fields[0].setText(rs.getString(1));
				fields[1].setText(rs.getString(2));
				fields[2].setText(rs.getString(3));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		setTableUser();
	}

	public void setTableUser() {
		model.setRowCount(0);
		try (PreparedStatement pst = connection
				.prepareStatement("select * from contract where customerCode = ? order by regDate desc;")) {
			pst.setObject(1, fields[0].getText());

			ResultSet rs = pst.executeQuery();
			int count = 1;
			while (rs.next()) {
				model.addRow(new Object[] { count, rs.getObject(1), rs.getObject(2), rs.getObject(3), rs.getObject(4),
						rs.getObject(5), rs.getObject(6) });
				count++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
