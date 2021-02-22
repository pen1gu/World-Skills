package frame;

import static frame.BaseFrame.*;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.TableData;

public class ReservationApplyPanel extends JPanel {

	JComboBox<String> cbPrimary = new JComboBox<String>();
	JComboBox<String> cbCarNum = new JComboBox<String>();
	JComboBox<String> cbYear = new JComboBox<String>();
	JComboBox<String> cbMonth = new JComboBox<String>();
	JComboBox<String> cbDay = new JComboBox<String>();

	TableData tableData;

	public ReservationApplyPanel(TableData tableData) {
		this.tableData = tableData;
		setPreferredSize(new Dimension(800, 520));
		setLayout(new BorderLayout());

		JPanel northPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

		northPanel.add(createLabel(new JLabel("배차차량조회 및 예약하기", JLabel.LEFT), new Font("맑은 고딕", Font.BOLD, 20)));
		centerPanel.add(createComponent(new JLabel("차량정보:", JLabel.RIGHT), 60, 20));

		centerPanel.add(cbPrimary);
		centerPanel.add(cbCarNum);
		centerPanel.add(cbYear);
		centerPanel.add(cbMonth);
		centerPanel.add(cbDay);
		centerPanel.add(createButton("좌석조회", e -> lookUpSeat()));
		centerPanel.add(createButton("메인으로", e -> MainFrame.openMain(-1)));

		cbCarNum.addItem("1호차");
		cbCarNum.addItem("2호차");

		LocalDate localDate = LocalDate.now();
		cbYear.addItem(String.format("%d년", localDate.getYear()));

		cbMonth.addItem("==월 선택==");
		cbDay.addItem("==일 선택==");

		int currentMonth = localDate.getMonth().getValue();
		for (int i = 0; i < 3; i++) {
			if (currentMonth + i > 12) {
				break;
			}

			cbMonth.addItem(String.format("%02d월", currentMonth + i));
		}

		cbMonth.addActionListener(this::changeDate);

		try {
			ResultSet rs = statement.executeQuery("select bNumber from tbl_bus;");
			while (rs.next()) {
				cbPrimary.addItem(rs.getString(1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		add(northPanel, BorderLayout.NORTH);
		add(centerPanel);
	}

	public void changeDate(ActionEvent e) {
		cbDay.removeAllItems();
		LocalDate localDate = LocalDate.of(LocalDate.now().getYear(),
				Integer.parseInt(cbMonth.getSelectedItem().toString().substring(0, 2)), 1);

		for (int i = 1; i <= localDate.lengthOfMonth(); i++) {
			cbDay.addItem(String.format("%02d월", i));
		}
	}

	public void lookUpSeat() {
		String month = (String) cbMonth.getSelectedItem();
		String day = (String) cbDay.getSelectedItem();

		if (month.equals("==일 선택==") || day.equals("==월 선택==")) {
			informationMessage("월 또는 일을 선택하여 주십시오.");
			return;
		}

		tableData.setCarPrimary((String) cbPrimary.getSelectedItem());
		tableData.setCarNum((String) cbCarNum.getSelectedItem());
		tableData.setYear((String) cbYear.getSelectedItem());
		tableData.setMonth(month);
		tableData.setDay(day);

		MainFrame.card.show(MainFrame.centerPanel, "seat");
		MainFrame.toggleButtons[1].setSelected(false);
		MainFrame.toggleButtons[2].setSelected(true);

		SeatLookUpPanel.tfCarNum.setText(tableData.getCarNum());
		SeatLookUpPanel.tfCarPrimary.setText(tableData.getCarPrimary());

		tableData.formatDate();
		SeatLookUpPanel.tfStartDate.setText(tableData.getDate());

		SeatLookUpPanel.tfUserId.setText(userName);
	}
}
