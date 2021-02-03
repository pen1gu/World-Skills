package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainFrame extends BaseFrame {

	JLabel lbLogin = new JLabel("로그인/회원가입");
	JLabel lbMyPage = new JLabel("마이페이지");

	JPanel imagePanel = createComponent(new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 40)), 0, 0, 600, 400);

	public MainFrame() {
		super(800, 500, "메인");
		setLayout(new BorderLayout());

		JPanel northPanel = createComponent(new JPanel(null), 800, 40);
		JPanel centerPanel = new JPanel(new BorderLayout());
		JPanel southPanel = createComponent(new JPanel(), 800, 100);

		JPanel center_west = createComponent(new JPanel(new FlowLayout()), 200, 350);
		JPanel center_center = createComponent(new JPanel(null), 650, 350);

		northPanel.setBackground(Color.white);
		southPanel.setBackground(Color.white);

		northPanel.add(
				createComponent(new JLabel(getImage("./지급자료/images/1.png", 140, 40), JLabel.RIGHT), 0, 0, 140, 40));
		northPanel.add(createComponent(lbLogin, 610, 10, 100, 20));
		northPanel.add(createComponent(lbMyPage, 710, 10, 80, 20));

		centerPanel.add(center_west, BorderLayout.WEST);
		centerPanel.add(center_center);

		center_west.setBackground(new Color(153, 204, 255));
		imagePanel.setBackground(new Color(204, 229, 255));

		center_west.add(
				createComponent(createButtonWithColor("CPU", e -> openFrame(new CPUForm()), Color.white), 150, 40));
		center_west.add(
				createComponent(createButtonWithColor("메인보드", e -> openFrame(new CPUForm()), Color.white), 150, 40));
		center_west.add(
				createComponent(createButtonWithColor("메모리", e -> openFrame(new CPUForm()), Color.white), 150, 40));
		center_west.add(
				createComponent(createButtonWithColor("그래픽카드", e -> openFrame(new CPUForm()), Color.white), 150, 40));
		center_west.add(createComponent(createButtonWithColor("SSD / HDD", e -> openFrame(new CPUForm()), Color.white),
				150, 40));
		center_west.add(
				createComponent(createButtonWithColor("케이스", e -> openFrame(new CPUForm()), Color.white), 150, 40));
		center_west
				.add(createComponent(createButtonWithColor("파워", e -> openFrame(new CPUForm()), Color.white), 150, 40));

		imagePanel.add(createComponent(new JLabel(getImage("./지급자료/images/2.jpg", 250, 250)), 250, 250));
		imagePanel.add(createComponent(new JLabel(getImage("./지급자료/images/3.jpg", 250, 250)), 250, 250));

		new Thread(() -> {
			while (true) {
				for (int i = 0; i < 600; i++) {
					for (int j = 0; j < 10; j++) {
						if (rootPaneCheckingEnabled) {
							
						}
					}
				}
			}
		});

		center_center.add(imagePanel);

		add(northPanel, BorderLayout.NORTH);
		add(centerPanel);
		add(southPanel, BorderLayout.SOUTH);
	}

	public static void main(String[] args) {
		new MainFrame().setVisible(true);
	}

}
