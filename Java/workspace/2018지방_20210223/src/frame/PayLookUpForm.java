package frame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.print.PrinterException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class PayLookUpForm extends BaseFrame {

	JTextField tfMenuName = createComponent(new JTextField(), 100, 20);

	DefaultTableModel model = new DefaultTableModel(new String[] { "종류", "메뉴명", "사원명", "결제수량", "총결제금액", "결제일" }, 0);
	JTable table = new JTable(model);

	public PayLookUpForm() {
		super(500, 500, "결제조회");

		JPanel northPanel = createComponent(new JPanel(new FlowLayout()), 500, 35);
		JPanel centerPanel = new JPanel(new BorderLayout());

		northPanel.add(new JLabel("메뉴명:"));
		northPanel.add(tfMenuName);
		northPanel.add(createButton("조회", e -> clickLookUp()));
		northPanel.add(createButton("모두보기", e -> showAllTableElements()));
		northPanel.add(createButton("인쇄", e -> printTable()));
		northPanel.add(createButton("닫기", e -> openFrame(new ManagementForm())));

		JScrollPane scrollPane = createComponent(new JScrollPane(table), 500, 450);
		centerPanel.add(scrollPane);

		add(northPanel, BorderLayout.NORTH);
		add(centerPanel);

		for (int i = 0; i < 5; i++) {
			table.getColumnModel().getColumn(i).setCellRenderer(centerRender);
		}

		clickLookUp();
	}

	public void clickLookUp() {
		// TODO: 조회 메서드 만들기
		model.setRowCount(0);
		try {
			ResultSet rs = statement.executeQuery(
					"select cuisineName, mealName, memberName, orderCount, amount, Date_format(orderDate,\"%Y-%c-%d\") from \r\n"
							+ "orderList as odl \r\n" + "inner join meal as m \r\n" + "on odl.mealNo = m.mealNo \r\n"
							+ "inner join cuisine as c \r\n" + "on odl.cuisineNo = c.cuisineNo\r\n"
							+ "inner join member as mb \r\n" + "on odl.memberNo = mb.memberNo\r\n"
							+ "where mealName like '%" + tfMenuName.getText() + "%';");

			while (rs.next()) {
				model.addRow(new Object[] { rs.getObject(1), rs.getObject(2), rs.getObject(3), rs.getObject(4),
						rs.getObject(5), rs.getObject(6) });
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void showAllTableElements() {
		tfMenuName.setText("");
		clickLookUp();
	}

	public void printTable() {
		try {
			table.print();
		} catch (PrinterException e) {
			e.printStackTrace();
		}
	}
}
