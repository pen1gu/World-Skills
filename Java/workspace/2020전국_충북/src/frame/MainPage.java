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
	JPanel imagePanel = createComponent(new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 40)), 0, 0, 600, 400);

	JLabel lbMain = createComponent(new JLabel(getImage("./지급자료/images/1.png", 140, 40), JLabel.RIGHT), 0, 0, 140, 40);

	ImageFiles imageFiles = new ImageFiles();

	Thread imageThread;

	static boolean checkOpen = false;

	public MainPage() {
		super(800, 420);
		setLayout(new BorderLayout());

		JPanel centerPanel = new JPanel(new BorderLayout());
		JPanel southPanel = createComponent(new JPanel(), 800, 100);

		JPanel center_west = createComponent(new JPanel(new FlowLayout()), 200, 350);
		JPanel center_center = createComponent(new JPanel(null), 600, 350);

		southPanel.setBackground(Color.white);

		centerPanel.add(center_west, BorderLayout.WEST);
		centerPanel.add(center_center);

		center_west.setBackground(new Color(153, 204, 255));
		imagePanel.setBackground(new Color(204, 229, 255));

		center_west.add(
				createComponent(createButtonWithColor("CPU", e -> changePage(new SearchPage()), Color.white), 150, 40));
		center_west.add(createComponent(createButtonWithColor("메인보드", e -> changePage(new SearchPage()), Color.white),
				150, 40));
		center_west.add(
				createComponent(createButtonWithColor("메모리", e -> changePage(new SearchPage()), Color.white), 150, 40));
		center_west.add(createComponent(createButtonWithColor("그래픽카드", e -> changePage(new SearchPage()), Color.white),
				150, 40));
		center_west.add(createComponent(
				createButtonWithColor("SSD / HDD", e -> changePage(new SearchPage()), Color.white), 150, 40));
		center_west.add(
				createComponent(createButtonWithColor("케이스", e -> changePage(new SearchPage()), Color.white), 150, 40));
		center_west.add(
				createComponent(createButtonWithColor("파워", e -> changePage(new SearchPage()), Color.white), 150, 40));

		JLabel[] label = new JLabel[imageFiles.list.size() / 2 + 1];
		int count = 2;
		for (int i = 0; i < imageFiles.list.size() / 2; i++) {
			label[i] = createComponent(new JLabel(getImage(imageFiles.list.get(count), 250, 250)), 30 + (280 * (i)), 30,
					250, 250);
			center_center.add(label[i]);
			count += 2;
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


		add(centerPanel);
		add(southPanel, BorderLayout.SOUTH);
	}
}
