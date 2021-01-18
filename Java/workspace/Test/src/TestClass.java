import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

public class TestClass extends JFrame {

	TestClass() {
		setSize(500, 500);
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		DefaultTableModel model = new DefaultTableModel(new String[] { "d", "f", "g", "h" }, 0);
		JTable table = new JTable(model);

		for (int i = 1; i <= 12; i++) {
			model.addRow(new Vector<Vector<Object>>());
		}

		JScrollPane jScrollPane = new JScrollPane(table);
		jScrollPane.setPreferredSize(new Dimension(500, 500));

		add(jScrollPane);
	}

	public static void main(String[] args) {
		new TestClass().setVisible(true);
	}
}
