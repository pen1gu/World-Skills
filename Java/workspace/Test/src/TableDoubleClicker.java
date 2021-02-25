import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class TableDoubleClicker extends JFrame {

	JCheckBox box = new JCheckBox();

	DefaultTableModel model = new DefaultTableModel(new String[] { "1", "2" }, 0);
	JTable table = new JTable(model);
	JButton button = new JButton("클릭");

	public TableDoubleClicker() {
		setLayout(new BorderLayout());

//			@Override
//			public boolean isCellEditable(int row, int column) {
//				// TODO Auto-generated method stub
//				return false;
//			}

		JScrollPane pane = new JScrollPane(table);

		for (int i = 0; i < 10; i++) {
			model.addRow(new Object[] { false, "column2" });
		}

		table.getColumn("1").setCellEditor(new DefaultCellEditor(box));
		table.getColumn("1").setCellRenderer(dcr);

		box.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				checkEmptyBox();
			}
		});

		button.addActionListener(this::checkAllBoxs);

		table.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseClicked(e);

				if (e.getClickCount() == 2) {
					JOptionPane.showMessageDialog(null, "더블클릭");
//				list.get(row).setEnabled(true);
				}
			}
		});

		add(pane);
		add(button, BorderLayout.SOUTH);
		setSize(500, 500);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
		revalidate();

	}

	DefaultTableCellRenderer dcr = new DefaultTableCellRenderer() {
		public Component getTableCellRendererComponent // 셀렌더러
		(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			JCheckBox box = new JCheckBox();
			box.setSelected(((Boolean) value).booleanValue());
			box.setBackground(Color.white);
			box.setHorizontalAlignment(JLabel.CENTER);
			return box;
		}
	};

	public void checkAllBoxs(ActionEvent e) {
		for (int i = 0; i < model.getRowCount(); i++) {
			table.getModel().setValueAt(true, i, 0);
		}
		button.setEnabled(false);
		checkEmptyBox();
	}

	public void checkEmptyBox() {
		for (int i = 0; i < table.getRowCount(); i++) {
			if (!box.isSelected()) {
				button.setEnabled(true);
				return;
			}
		}
	}

	public static void main(String[] args) {
		new TableDoubleClicker();
	}

}
