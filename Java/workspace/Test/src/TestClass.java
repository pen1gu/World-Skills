import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.Arrays;
import java.util.Vector;

import javax.swing.BorderFactory;
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

		int[] arr1 = new int[4];
		int[] arr2 = new int[4];
		int[] arr3 = new int[4];
		int[] arr4 = new int[4];

		Arrays.fill(arr1, 0);
		Arrays.fill(arr2, 0);
		Arrays.fill(arr3, 0);
		Arrays.fill(arr4, 0);
		arr1[0] = 30;
		arr2[1] = 30;
		arr3[2] = 30;
		arr4[3] = 30;

		new Thread() {
			public void run() {
				try {
					while (true) {
						for (int i = 0; i < 4; i++) {
							jScrollPane.setBorder(
									BorderFactory.createMatteBorder(arr1[i], arr2[i], arr3[i], arr4[i], Color.blue));
							repaint();
							Thread.sleep(50);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			};

		}.start();

		add(jScrollPane);
	}

	public static void main(String[] args) {
		new TestClass().setVisible(true);
	}
}
