package frame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class PayListForm extends BaseFrame {

	JLabel lbAmount = new JLabel();

	DefaultTableModel model = new DefaultTableModel(new String[] { "종류", "주문수량" }, 0);
	JTable table = new JTable(model);

	public PayListForm() {
		super(300, 200, "메뉴별 주문현황");

		JPanel northPanel = createComponent(new JPanel(new FlowLayout(FlowLayout.RIGHT)), 300, 35);
		JPanel centerPanel = new JPanel(new BorderLayout());
		JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

		northPanel.add(createButton("닫기", e -> openFrame(new ManagementForm())));

		southPanel.add(lbAmount);
		JScrollPane pane = createComponent(new JScrollPane(table), 300, 80);

		try (PreparedStatement pst = connection.prepareStatement(
				"select c.cuisineName, count(*)  from orderlist as odl inner join cuisine as c on odl.cuisineNo = c.cuisineNo group by c.cuisineName;")) {
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				model.addRow(new Object[] { rs.getObject(1), rs.getObject(2) });
			}

			for (int i = 0; i < 2; i++) {
				table.getColumnModel().getColumn(i).setCellRenderer(centerRender);
			}

			int amount = 0;
			for (int i = 0; i < 4; i++) {
				System.out.println(model.getValueAt(i, 1));
				amount += Integer.parseInt(model.getValueAt(i, 1).toString());

			}

			lbAmount.setText(String.format("합계: %d개", amount));
		} catch (Exception e) {
			e.printStackTrace();
		}

		table.setRowSorter(new TableRowSorter(model));

		centerPanel.add(pane);

		add(northPanel, BorderLayout.NORTH);
		add(centerPanel);
		add(southPanel, BorderLayout.SOUTH);
	}
}
