package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class UserForm extends BaseFrame {

	JButton[] buttons = new JButton[4];

	JLabel lbCurrentTime = createLabel(new JLabel(), new Font("맑은 고딕", 1, 16));

	public UserForm() {
		super(400, 600, "식권 발매 프로그램");

		JTabbedPane tabbedPane = new JTabbedPane();

		JPanel centerPanel = new JPanel(new GridLayout(0, 2));
		JPanel southPanel = createComponent(new JPanel(), 400, 30);

		add(createComponent(createLabel(new JLabel("식권 발매 프로그램", 0), new Font("굴림", Font.BOLD, 18)), 400, 30),
				BorderLayout.NORTH);

		tabbedPane.addTab("메뉴", centerPanel);

		centerPanel.add(buttons[0] = createButtonWithImage(getImage("./지급자료/menu_1.png", 150, 250),
				e -> openFrame(new TicketReleaseForm(1))));
		centerPanel.add(buttons[1] = createButtonWithImage(getImage("./지급자료/menu_2.png", 150, 250),
				e -> openFrame(new TicketReleaseForm(2))));
		centerPanel.add(buttons[2] = createButtonWithImage(getImage("./지급자료/menu_3.png", 150, 250),
				e -> openFrame(new TicketReleaseForm(3))));
		centerPanel.add(buttons[3] = createButtonWithImage(getImage("./지급자료/menu_4.png", 150, 250),
				e -> openFrame(new TicketReleaseForm(4))));

		int count = 0;
		for (String element : new String[] { "한식", "중식", "일식", "양식" }) {
			buttons[count].setToolTipText(element);
			count++;
		}

		add(tabbedPane);

		southPanel.setBackground(Color.black);

		southPanel.add(lbCurrentTime);
		lbCurrentTime.setForeground(Color.white);

		new Thread(() -> {
			while (true) {
				try {
					LocalDate date = LocalDate.now();
					LocalDateTime dateTime = LocalDateTime.now();

					lbCurrentTime.setText(String.format("현재시간 : %d년 %02d월 %02d일 %02d시 %02d분 %02d초", date.getYear(),
							date.getMonthValue(), date.getDayOfMonth(), dateTime.getHour(), dateTime.getMinute(),
							dateTime.getSecond()));

					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		}).start();

		add(southPanel, BorderLayout.SOUTH);
	}
}
