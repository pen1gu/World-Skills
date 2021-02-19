package frame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.Year;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import model.CustomerInfo;

public class CustomerLookUpForm extends BaseFrame {

	DefaultTableModel model = new DefaultTableModel(
			new String[] { "code", "name", "birth", "tel", "address", "company" }, 0);
	JTable table = new JTable(model);

	JTextField tfName = createComponent(new JTextField(), 140, 20);

	public CustomerLookUpForm() {
		super(800, 800, "고객 조회");

		JPanel northPanel = createComponent(new JPanel(new FlowLayout()), 800, 35);
		northPanel.add(new JLabel("성명"));
		northPanel.add(tfName);
		northPanel.add(createButton("조회", e -> clickSearch()));
		northPanel.add(createButton("전체보기", e -> clickReset()));
		northPanel.add(createButton("수정", e -> modifyCustomerInfo()));
		northPanel.add(createButton("삭제", e -> deleteUser()));
		northPanel.add(createButton("닫기", e -> openFrame(new MainForm())));

		JPanel centerPanel = new JPanel(new BorderLayout());
		JScrollPane pane = createComponent(new JScrollPane(table), 800, 750);

		centerPanel.add(pane);

		for (int i = 3; i < 6; i++) {
			table.getColumnModel().getColumn(i).setPreferredWidth(130);
		}

		add(northPanel, BorderLayout.NORTH);
		add(pane);
		clickReset();
	}

	public void clickSearch() {
		model.setRowCount(0);
		try (PreparedStatement pst = connection.prepareStatement("select * from customer where name like ?;")) {
			pst.setObject(1, tfName.getText() + "%");

			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				model.addRow(new Object[] { rs.getObject(1), rs.getObject(2), rs.getObject(3), rs.getObject(4),
						rs.getObject(5), rs.getObject(6) });
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void clickReset() {
		tfName.setText("");
		clickSearch();
	}

	public static CustomerInfo customerInfo = new CustomerInfo();

	public void modifyCustomerInfo() {
		customerInfo.setCode(model.getValueAt(table.getSelectedRow(), 0).toString());
		customerInfo.setName(model.getValueAt(table.getSelectedRow(), 1).toString());
		customerInfo.setBirth(model.getValueAt(table.getSelectedRow(), 2).toString());
		customerInfo.setTelNumber(model.getValueAt(table.getSelectedRow(), 3).toString());
		customerInfo.setAddress(model.getValueAt(table.getSelectedRow(), 4).toString());
		customerInfo.setCompany(model.getValueAt(table.getSelectedRow(), 5).toString());

		new InsertCustomerForm("수정").setVisible(true);
	}

	public void deleteUser() {
		int yesNo = JOptionPane.showConfirmDialog(null, table.getValueAt(table.getSelectedRow(), 1) + "님을 정말 삭제하시겠습니까?",
				"고객정보 삭제", JOptionPane.YES_NO_OPTION);

		if (yesNo == JOptionPane.NO_OPTION) {
			return;
		}

		try (PreparedStatement pst = connection.prepareStatement("delete from customer where code = ?")) {
			pst.setObject(1, table.getValueAt(table.getSelectedRow(), 0));

			pst.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}

		model.removeRow(table.getSelectedRow());
	}
}
