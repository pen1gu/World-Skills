package frame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.Date;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import model.Connector;
import static frame.BaseFrame.*;

public class DestinationInfoPanel extends JPanel {

	JSpinner spYear = new JSpinner();
	JSpinner spMonth = new JSpinner();
	JSpinner spDay = new JSpinner();

	JComboBox<String> cbAirPlane = createComponent(new JComboBox<String>(), 0, 30);
	JComboBox<String> cbStartRegion1 = createComponent(new JComboBox<String>(), 0, 30);
	JComboBox<String> cbStartRegion2 = createComponent(new JComboBox<String>(), 0, 30);

	DefaultTableModel model = new DefaultTableModel(
			"n,Rend,Country,Departure,Arrive,Air,Type,StartTime,LastTime,Fare,선택".split(","), 0);
	JTable table = new JTable(model);

	public DestinationInfoPanel() {
		setSize(800, 600); // 개발 보류
		setLayout(new BorderLayout(30, 0));
		JPanel northPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel centerPanel = new JPanel(new GridLayout(0, 1));
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

		table.removeColumn((table.getColumnModel().getColumn(0)));

		cbAirPlane.addItem("국내선");
		cbAirPlane.addItem("국제선");
		
		cbStartRegion1.addItem("");
		cbStartRegion2.addItem("");
		
		cbAirPlane.addActionListener(e -> resetItemList());
		cbStartRegion1.addActionListener(e -> changeTableList());
		cbStartRegion2.addActionListener(e -> changeTableList());

		JScrollPane scrollPane = createComponent(new JScrollPane(table), 800, 300);

		southPanel.add(new JLabel("도착지"), BorderLayout.NORTH);
		southPanel.add(scrollPane);

		add(northPanel, BorderLayout.NORTH);
		add(centerPanel, BorderLayout.CENTER);
		add(southPanel, BorderLayout.SOUTH);
		changeTableList();
		changeDate();
		insertComboBoxList();
	}

	public void insertComboBoxList() {
		try (ResultSet rs = Connector.getSqlResult(
				"select departure from airinfo  where rend = ? group by country, departure",
				cbAirPlane.getSelectedItem())) {

			while (rs.next()) {
				cbStartRegion1.addItem(rs.getString(1));
				cbStartRegion2.addItem(rs.getString(1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void resetItemList() {
		cbStartRegion1.removeAllItems();
		cbStartRegion2.removeAllItems();
//		cbStartRegion1.addItem("출발지역선택1");
//		cbStartRegion2.addItem("출발지역선택2(최종)");
		insertComboBoxList();
		changeTableList();
	}

	public void changeDate() {
		LocalDate localDate = LocalDate.now();
		// 날짜 부분은 일단 건너뛰기
	}

	public void changeTableList() {
		model.setRowCount(0);

		String cbRegion1 = cbStartRegion1.getSelectedItem().toString();
		String cbRegion2 = cbStartRegion2.getSelectedItem().toString();

		String region1 = cbRegion1.equals("출발지역선택1") ? "" : cbRegion1;
		String region2 = cbRegion1.equals("출발지역선택2(최종)") ? "" : cbRegion1;

		try (ResultSet rs = Connector.getSqlResult(
				"select * from airinfo where rend = ? and departure like ? or departure like ?",
				cbAirPlane.getSelectedItem(), "%" + region1 + "%", "%" + region2 + "%")) {

			while (rs.next()) {
				Object[] objects = new Object[10];
				for (int i = 0; i < 10; i++) {
					objects[i] = rs.getObject(i + 1);
				}

				model.addRow(objects);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
