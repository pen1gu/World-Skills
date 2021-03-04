import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class CalendarFrame extends BaseFrame {

	JButton leftButton = createButton("◀", this::changeDate);
	JButton rightButton = createButton("▶", this::changeDate);

	JPanel centerPanel = createComponent(new JPanel(new GridLayout(0, 7)), 500, 470);

	int year = LocalDate.now().getYear();
	int month = LocalDate.now().getMonthValue();

	JLabel lbDate = new JLabel();

	public CalendarFrame() {
		super(500, 500, "");

		JPanel northPanel = createComponent(new JPanel(new FlowLayout()), 500, 40);

		northPanel.add(leftButton);
		northPanel.add(lbDate);
		northPanel.add(rightButton);
		lbDate.setText(String.format("%d년 %2d월", year, month));

		add(northPanel, BorderLayout.NORTH);
		add(centerPanel);
		settingCalendar();
	}

	public static void main(String[] args) {
		new CalendarFrame().setVisible(true);
	}

	public void changeDate(ActionEvent e) {
		JButton button = (JButton) e.getSource();
		if (button.getText().equals("◀")) {
			month--;
			if (month < 1) {
				month = 1;
				year--;
			}
		} else {
			month++;
			if (month > 12) {
				month = 12;
				year++;
			}
		}
		settingCalendar();

		lbDate.setText(String.format("%d년 %2d월", year, month));
	}

	public void settingCalendar() {
		centerPanel.removeAll();

		LocalDate date = LocalDate.of(year, month, 1);
		int week = date.atStartOfDay().getDayOfWeek().getValue(); //
		System.out.println(week);
		int dayCount = 1;

		for (int i = 1; i <= 42; i++) {
			int cnt = i;
			if (i <= week) {
				centerPanel.add(new JLabel(""));
			} else if (i <= date.lengthOfMonth() + week) {
				centerPanel.add(createButton("" + dayCount, e -> JOptionPane.showMessageDialog(null, cnt + "")));
				dayCount++;
			} else {
				centerPanel.add(new JLabel(""));
			}
		}
	}
}
