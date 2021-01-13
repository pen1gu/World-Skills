package frame;

import java.awt.event.MouseAdapter;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;

import static frame.BaseFrame.*;

public class FlightPanel extends JPanel {

	JButton btnStartStop;
	JButton btnLastStop;
	JTextField tfDate;
	JTextField tfTime;
	JSpinner spinPersonnelCount;
	JTextField tfAmout;

	public FlightPanel() {
		// 로그인 안 했을 시 editable

		setLayout(null);
		add(createComponent(btnStartStop = createButton("출발지", e -> selectStation()), 30, 10, 180, 300));
		add(createComponent(new JLabel(getImage(210, 100, "./datafiles/img/Right.png")), 260, 120, 210, 100));
		add(createComponent(btnLastStop = createButton("도착지", e -> selectStation()), 520, 10, 180, 300));

		add(createComponent(new JLabel("날짜:"), 60, 315, 30, 20));
		add(createComponent(tfDate = new JTextField(), 100, 315, 80, 20));
		add(createComponent(new JLabel("시간:"), 60, 340, 30, 20));
		add(createComponent(tfTime = new JTextField(), 100, 340, 80, 20));

		add(createComponent(new JLabel("인원수:"), 550, 315, 60, 20));
		add(createComponent(spinPersonnelCount = new JSpinner(), 610, 315, 40, 20));
		add(createComponent(new JLabel("운임요금:"), 530, 340, 60, 20));
		add(createComponent(tfAmout = new JTextField(), 590, 340, 80, 20));

		for (JComponent component : new JComponent[] { tfDate, tfTime, tfAmout })
			component.setEnabled(false);

		addPressEvent(tfDate);
		addPressEvent(tfTime);

		spinPersonnelCount.addChangeListener(e -> {
			getAmount();
		});

	}

	public void addPressEvent(JTextField field) {
		field.addMouseListener(new MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent e) {
				selectStation();
			};
		});
	}

	public void getAmount() {
		Integer amount = 0;
		tfAmout.setText(amount.toString());
	}

	public void selectStation() {
		new DestinationModal().setVisible(true);
	}

	public void openCalendar() {

	}

	public void hasReservation() {
		if (btnStartStop.getText().contentEquals("출발지")) {
			errorMessage("예매할 출발지를 입력해야합니다.");
			return;
		}

		if (btnLastStop.getText().equals("도착지")) {
			errorMessage("예매할 도착지를 입력해야합니다.");
			return;
		}

		if (tfDate.getText().isEmpty()) {
			errorMessage("예매할 날짜를 입력해야 합니다.");
			return;
		}

		if (tfTime.getText().isEmpty()) {
			errorMessage("예매할 시간을 입력해야 합니다.");
			return;
		}

		if ((Integer) spinPersonnelCount.getValue() <= 0) {
			errorMessage("예매할 인원수를 0명 이상 입력해야 합니다.");
			return;
		}

	}
}
