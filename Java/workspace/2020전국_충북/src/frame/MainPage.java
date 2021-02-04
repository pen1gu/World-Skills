package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;

import javax.swing.JLabel;
import javax.swing.JPanel;

import model.ImageFiles;

import static frame.BaseFrame.*;

public class MainPage extends BasePage {
	JLabel lbLogin = new JLabel("로그인/회원가입");
	JLabel lbMyPage = new JLabel("마이페이지");

	JPanel imagePanel = createComponent(new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 40)), 0, 0, 600, 400);

	JLabel lbMain = createComponent(new JLabel(getImage("./지급자료/images/1.png", 140, 40), JLabel.RIGHT), 0, 0, 140, 40);

	ImageFiles imageFiles = new ImageFiles();

	MainFrame mainFrame;
	Thread imageThread;

	public MainPage(MainFrame mainFrame) {
		super(800, 500);
		setLayout(new BorderLayout());

		this.mainFrame = mainFrame;

		JPanel northPanel = createComponent(new JPanel(null), 800, 40);
		JPanel centerPanel = new JPanel(new BorderLayout());
		JPanel southPanel = createComponent(new JPanel(), 800, 100);

		JPanel center_west = createComponent(new JPanel(new FlowLayout()), 200, 350);
		JPanel center_center = createComponent(new JPanel(null), 600, 350);

		northPanel.setBackground(Color.white);
		southPanel.setBackground(Color.white);

		northPanel.add(lbMain);
		northPanel.add(createComponent(lbLogin, 610, 10, 100, 20));
		northPanel.add(createComponent(lbMyPage, 710, 10, 80, 20));

		centerPanel.add(center_west, BorderLayout.WEST);
		centerPanel.add(center_center);

		center_west.setBackground(new Color(153, 204, 255));
		imagePanel.setBackground(new Color(204, 229, 255));

		center_west.add(createComponent(
				createButtonWithColor("CPU", e -> changePage(MainPage.this, new CPUForm()), Color.white), 150, 40));
		center_west.add(createComponent(
				createButtonWithColor("메인보드", e -> changePage(MainPage.this, new CPUForm()), Color.white), 150, 40));
		center_west.add(createComponent(
				createButtonWithColor("메모리", e -> changePage(MainPage.this, new CPUForm()), Color.white), 150, 40));
		center_west.add(createComponent(
				createButtonWithColor("그래픽카드", e -> changePage(MainPage.this, new CPUForm()), Color.white), 150, 40));
		center_west.add(createComponent(
				createButtonWithColor("SSD / HDD", e -> changePage(MainPage.this, new CPUForm()), Color.white), 150,
				40));
		center_west.add(createComponent(
				createButtonWithColor("케이스", e -> changePage(MainPage.this, new CPUForm()), Color.white), 150, 40));
		center_west.add(createComponent(
				createButtonWithColor("파워", e -> changePage(MainPage.this, new CPUForm()), Color.white), 150, 40));

//		imagePanel.add();

		JLabel[] label = new JLabel[imageFiles.list.size() / 2 + 1];
		int count = 2;
		for (int i = 0; i < imageFiles.list.size() / 2; i++) {
			label[i] = createComponent(new JLabel(getImage(imageFiles.list.get(count), 250, 250)), 30 + (280 * (i)), 30,
					250, 250);
			center_center.add(label[i]);
			count += 2;
			System.out.println(i);
		}

		imageThread = new Thread() {
			public void run() {
				try {
					while (true) {
						for (int i = 0; i < 280; i++) {
							for (int j = 0; j < imageFiles.list.size() / 2; j++) {
								if (label[j].getX() < -280) {
									label[j].setLocation((imageFiles.list.size() / 2) * 280, 30);
								}
								label[j].setLocation(label[j].getX() - 1, 30);
							}
							Thread.sleep(3);
						}
						Thread.sleep(1000);
					}
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		};
		
		imageThread.start();

		center_center.add(imagePanel);

		lbMain.addMouseListener(new MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent e) {

			};
		});

		lbLogin.addMouseListener(new MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent e) {
				LoginPage loginPage = new LoginPage();
				mainFrame.contentsPanel.add(loginPage);
				mainFrame.resetContents();

				changePage(MainPage.this, loginPage);
				revalidate();
			};
		});

		add(northPanel, BorderLayout.NORTH);
		add(centerPanel);
		add(southPanel, BorderLayout.SOUTH);
	}
}
