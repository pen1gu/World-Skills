package frame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.sql.PreparedStatement;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class PayListForm extends BaseFrame {

	JLabel lbAmount = new JLabel();

	DefaultTableModel model = new DefaultTableModel(new String[] { "종류,주문수량" }, 0);
	JTable table = new JTable(model);

	public PayListForm() {
		super(300, 190, "메뉴별 주문현황");

		JPanel northPanel = createComponent(new JPanel(new FlowLayout(FlowLayout.RIGHT)), 300, 35);
		JPanel centerPanel = new JPanel(new BorderLayout());
		JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

		northPanel.add(createButton("닫기", e -> openFrame(new ManagementForm())));

		southPanel.add(lbAmount);
		JScrollPane pane = createComponent(new JScrollPane(table), 300, 120);

		lbAmount.setText("");

		try (PreparedStatement pst = connection.prepareStatement("select * from")){
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		centerPanel.add(pane);

		add(northPanel, BorderLayout.NORTH);
		add(centerPanel);
		add(southPanel, BorderLayout.SOUTH);
	}

	public void changeAmount() {
		
	}
}
