package frame;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import static frame.BaseFrame.*;

public class CarLookUpPanel extends JPanel {

	DefaultTableModel model = new DefaultTableModel(
			new String[] { "차량번호", "출발지", "도착지", "첫차시간", "소요시간", "운임횟수", "운임금액" }, 0);
	JTable table = new JTable(model);

	public CarLookUpPanel() {
		setPreferredSize(new Dimension(800, 520));
		setLayout(new GridBagLayout());

		JScrollPane scrollPane = createComponent(new JScrollPane(table), 690, 420);

		for (int i = 0; i < 7; i++) {
			table.getColumnModel().getColumn(i).setCellRenderer(centerRender);
		}

		add(scrollPane);
		resetTable();
	}

	public void resetTable() {
		model.setRowCount(0);

		try (PreparedStatement pst = connection.prepareStatement("select * from tbl_bus;")) {
			ResultSet rs = pst.executeQuery();

			int count = 0;
			while (rs.next()) {
				model.addRow(new Object[] { rs.getObject(1), rs.getObject(2), rs.getObject(3), rs.getObject(4),
						rs.getObject(5), rs.getObject(6), rs.getObject(7) });
				table.setRowHeight(count, 25);
				count++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
