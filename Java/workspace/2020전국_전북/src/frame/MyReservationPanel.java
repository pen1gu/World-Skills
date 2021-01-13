package frame;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import frame.BaseFrame.*;

public class MyReservationPanel extends JPanel {
	
	JCheckBox allCheckBox = new JCheckBox("All");
	DefaultTableModel model = new DefaultTableModel();
	JTable table = new JTable(model);
	
	
	MyReservationPanel() {
		setSize(800,600);
		setLayout(new BorderLayout());
		add(allCheckBox);
		
		
		//table 보류
	}
}
