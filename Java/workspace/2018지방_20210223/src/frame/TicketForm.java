package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class TicketForm extends BaseFrame {

	JLabel lbCurrentTime = new JLabel("");

	JButton[] buttons = new JButton[4];

	public TicketForm() {
		super(330, 500, "식권 발매 프로그램");
		setLayout(new BorderLayout());

		JTabbedPane tabbedPane = new JTabbedPane(1);
		JPanel centerPanel = new JPanel(new GridLayout(2, 2));
		JPanel southPanel = new JPanel(new FlowLayout());

		tabbedPane.addTab("메뉴", centerPanel);

		centerPanel.add(buttons[0] = createButtonWithImage(getImage(100, 150, "./지급자료/menu_1.png"),
				e -> openFrame(new TicketReleaseForm(1))));
		centerPanel.add(buttons[1] = createButtonWithImage(getImage(100, 150, "./지급자료/menu_2.png"),
				e -> openFrame(new TicketReleaseForm(2))));
		centerPanel.add(buttons[2] = createButtonWithImage(getImage(100, 150, "./지급자료/menu_3.png"),
				e -> openFrame(new TicketReleaseForm(3))));
		centerPanel.add(buttons[3] = createButtonWithImage(getImage(100, 150, "./지급자료/menu_4.png"),
				e -> openFrame(new TicketReleaseForm(4))));

		southPanel.add(lbCurrentTime);

		addWindowListener(new WindowAdapter() {
			public void windowClosed(WindowEvent e) {
//				if (e.get) {
//				}
			};
		});

		int i = 0;
		for (String text : new String[] { "한식", "중식", "일식", "양식" }) {
			buttons[i].setToolTipText(text);
		}

		add(createLabel(new JLabel("식권 발매 프로그램", JLabel.CENTER), new Font("굴림", Font.BOLD, 18)), BorderLayout.NORTH);
		add(tabbedPane);
		add(southPanel, BorderLayout.SOUTH);

		lbCurrentTime.setForeground(Color.white);
		southPanel.setBackground(Color.black);

		new Thread(() -> {
			while (true) {
				LocalDate localDate = LocalDate.now();
				LocalDateTime localDateTime = LocalDateTime.now();
				lbCurrentTime.setText(localDate.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일")).toString() + " "
						+ localDateTime.format(DateTimeFormatter.ofPattern("HH시 mm분 ss초")));
			}
		}).start();
	}
}
