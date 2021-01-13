package frame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class DestinationInfoPanel extends JPanel {

	JSpinner spYear = new JSpinner();
	JSpinner spMonth = new JSpinner();
	JSpinner spDay = new JSpinner();

	JComboBox<String> cbAirPlane = new JComboBox<String>();
	JComboBox<String> cbStartRegion1 = new JComboBox<String>();
	JComboBox<String> cbStartRegion2 = new JComboBox<String>();

	DefaultTableModel model = new DefaultTableModel("Rend,Country,Departure,Arrive,Air,Type,StartTime,LastTime,Fare,선택".split(","),0);
	JTable table = new JTable();
	public DestinationInfoPanel() {
		setSize(800, 600);

		JPanel northPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel centerPanel = new JPanel(new GridLayout(1, 0));
		JPanel southPanel = new JPanel(new BorderLayout());

		northPanel.add(new JLabel("날짜"));
		northPanel.add(spYear);
		northPanel.add(new JLabel("년"));
		northPanel.add(spMonth);
		northPanel.add(new JLabel("월"));
		northPanel.add(spDay);
		northPanel.add(new JLabel("일"));

		centerPanel.add(new JLabel("출발지"));
		centerPanel.add(cbAirPlane);
		centerPanel.add(cbStartRegion1);
		centerPanel.add(cbStartRegion2);

		southPanel.add(new JLabel("도착지"), BorderLayout.NORTH);
		southPanel.add(comp)

		add(northPanel, BorderLayout.NORTH);
		add(centerPanel, BorderLayout.CENTER);
		add(southPanel, BorderLayout.SOUTH);
	}
}
