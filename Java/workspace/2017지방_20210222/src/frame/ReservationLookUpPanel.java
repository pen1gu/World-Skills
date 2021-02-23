package frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import static frame.BaseFrame.*;

public class ReservationLookUpPanel extends JPanel {

	JTextField tfId = new JTextField(6);
	DefaultTableModel model = new DefaultTableModel(new String[] { "성명", "예약일", "차량번호", "좌석번호", "운임금액", "발권상태" }, 0);
	JTable table = new JTable(model);

	public ReservationLookUpPanel() {
		setPreferredSize(new Dimension(800, 520));
		JPanel northPanel = createComponent(new JPanel(new FlowLayout(FlowLayout.LEFT)), 800, 30);
		JPanel centerPanel = createComponent(new JPanel(new FlowLayout(FlowLayout.LEFT)), 780, 480);

		northPanel.add(createLabel(new JLabel(" 고객티켓예약조회", JLabel.RIGHT), new Font("맑은 고딕", Font.BOLD, 20)));
		centerPanel.add(createComponent(new JLabel("고객 ID", JLabel.RIGHT), 50, 20));
		centerPanel.add(tfId);
		centerPanel.add(createButton("조회", this::clickLookUp));

		table.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 30));

		JScrollPane pane = createComponent(new JScrollPane(table), 600, 250);

		centerPanel.add(pane);

		add(northPanel, BorderLayout.NORTH);
		add(centerPanel);
	}

	public void clickLookUp(ActionEvent e) {
		model.setRowCount(0);

		String id = tfId.getText();
		String name = "";

		try (PreparedStatement pst = connection.prepareStatement("select * from tbl_ticket where cID =?")) {
			pst.setObject(1, id);

			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				name = rs.getString(5).equals("비회원") ? "" : rs.getString(5);

				model.addRow(new Object[] { name, rs.getObject(1), rs.getObject(2), rs.getObject(4), rs.getObject(6),
						rs.getObject(7) });
			}
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}
}
