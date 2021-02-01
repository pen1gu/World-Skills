package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.time.YearMonth;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class SelectDateForm extends BaseFrame {

	int year = LocalDate.now().getYear();
	int month = LocalDate.now().getMonthValue();

	JPanel calendarPanel = new JPanel(new GridLayout(6, 7));
	JLabel lbYear = new JLabel(year + "");
	JLabel lbMonth = new JLabel(month + "");
	JButton btnLeft = new JButton("<");
	JButton btnRight = new JButton(">");

	public SelectDateForm() {
		super(300, 380, "기간선택");

		setLayout(new BorderLayout());

		JPanel northPanel = new JPanel(new BorderLayout());
		JPanel northPanel_north = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
		JPanel northPanel_south = createComponent(new JPanel(new GridLayout(1, 0)), 300, 30);

		for (String day : new String[] { "일", "월", "화", "수", "목", "금", "토" }) {
			JLabel label = new JLabel(day, JLabel.CENTER);
			if (day.contentEquals("일")) {
				label.setForeground(Color.red);
			} else if (day.contentEquals("토")) {
				label.setForeground(Color.blue);
			}
			northPanel_south.add(label);
		}

		btnLeft.addActionListener(this::changeDate);
		btnRight.addActionListener(this::changeDate);

		northPanel.add(northPanel_north, BorderLayout.NORTH);
		northPanel.add(northPanel_south);

		northPanel_north.add(btnLeft);
		northPanel_north.add(lbYear);
		northPanel_north.add(new JLabel("년"));
		northPanel_north.add(lbMonth);
		northPanel_north.add(new JLabel("월"));
		northPanel_north.add(btnRight);

		calendarPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 40, 0));

		btnLeft.setEnabled(false);

		add(northPanel, BorderLayout.NORTH);
		add(calendarPanel);
		remakeCalendar();
	}

	public void changeDate(ActionEvent e) {
		JButton btn = (JButton) e.getSource();
		System.out.println(month);
		if (btn.getText().equals("<")) {
			month--;
			if (month <= LocalDate.now().getMonthValue())
				btn.setEnabled(false);
			else
				btn.setEnabled(true);

			if (month < 1) {
				month = 12;
				year--;
			}
		} else if (btn.getText().equals(">")) {
			month++;
			if (month > 12) {
				month = 1;
				year++;
			}
		}
		lbYear.setText(year + "");
		lbMonth.setText(month + "");
		remakeCalendar();
	}

	public void remakeCalendar() {
		calendarPanel.removeAll();
		LocalDate localDate = LocalDate.of(year, month, 1);
		int cnt = localDate.getDayOfWeek().getValue(); // 첫 일이 시작되는 수

		for (int i = 1; i < 42; i++) {
			if (cnt >= i) {
				calendarPanel.add(new JLabel());
			} else if (YearMonth.from(localDate).lengthOfMonth() >= i - cnt) {
				int day = i;
				JButton btn;
				calendarPanel.add(btn = createButton((i - cnt) + "", e -> selectDate(day)));
				btn.setMargin(new Insets(0, 0, 0, 0));

				if ((i - cnt < LocalDate.now().getDayOfMonth() && month <= LocalDate.now().getMonthValue())
						|| year < LocalDate.now().getYear()) {
					btn.setEnabled(false);
					continue;
				}

				if (i % 7 == 1) {
					btn.setForeground(Color.red);
				} else if (i % 7 == 0) {
					btn.setForeground(Color.blue);
				}
				btn.setBorder(new LineBorder(Color.black));

			} else {
				calendarPanel.add(new JLabel());
			}
		}
		revalidate();
		repaint();
	}

	public void selectDate(int day) {
		// click 시 tf에 넣기
	}

	public static void main(String[] args) {
		new SelectDateForm().setVisible(true);
	}
}
