package frame;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import static frame.BaseFrame.*;

public class ProjectForm extends JPanel {

	DefaultTableModel tableModel = new DefaultTableModel(new String[] { "No", "이름", "기간", "시작", "끝" }, 0);
	JTable table = createComponent(new JTable(tableModel), 250, 550);

	public ProjectForm() {
		setPreferredSize(new Dimension(800, 550));

		add(table,BorderLayout.WEST);
		
		JScrollPane scrollPane = new JScrollPane();
//		add(S)
	}
}
