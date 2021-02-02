package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.util.Arrays;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import model.CurrentCarInfo;
import model.ParkingAlgorithm;
import model.VehicleFunction;

public class MainForm extends BaseFrame {

	JLabel lbVehicle = createComponent(createLabel(new JLabel("승용/승합/화물/장애인/오토바이"), new Font("맑은 고딕", Font.BOLD, 21)),
			650, 30, 400, 30);

	CurrentCarInfo carInfo = new CurrentCarInfo();

	JPanel[][] park = new JPanel[11][10];

	ParkingAlgorithm pa = new ParkingAlgorithm();

	JPanel centerPanel;

	public MainForm() {
		super(1000, 700, "");

		JPanel northPanel = createComponent(new JPanel(null), 1000, 60);

		Image backgroundImage = getImage(1000, 600, "./지급자료/image/주차장.jpg").getImage();

		northPanel.add(
				createComponent(createButton("관리자", e -> openFrame(new AdminForm(), MainForm.this)), 150, 20, 100, 30));
		northPanel.add(lbVehicle);

		centerPanel = new JPanel(null) {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(backgroundImage, 0, 0, null);
			}
		};

		for (int i = 0; i < 10; i++)
			for (int j = 0; j < 11; j++) {
				centerPanel.add(park[j][i] = createComponent(new JPanel(), 100, 36));
				park[j][i].setBorder(new LineBorder(Color.blue));
			}

		for (int i = 0; i < 10; i++) { // parkA left
			centerPanel.add(park[i + 1][0] = createComponent(new ParkArea(true, 50, 100 + (36 * i)), 50, 100 + (36 * i),
					110, 36));
			centerPanel.add(park[i + 1][3] = createComponent(new ParkArea(false, 270, 100 + (36 * i)), 270,
					100 + (36 * i), 110, 36));

			centerPanel.add(park[i + 1][4] = createComponent(new ParkArea(true, 390, 100 + (36 * i)), 390,
					100 + (36 * i), 110, 36));

			centerPanel.add(park[i + 1][6] = createComponent(new ParkArea(false, 670, 100 + (36 * i)), 670,
					100 + (36 * i), 110, 36));

		} // 이 부분을 현재 입차 상태인지 아닌지 구분하여 개발

		for (int i = 0; i < 5; i++) {
			centerPanel.add(park[i + 1][8] = createComponent(new ParkArea(false, 740, 100 + (40 * i)), 740,
					103 + (40 * i), 110, 41));
		}

		for (int i = 0; i < 4; i++) {
			centerPanel.add(park[i + 1][8] = createComponent(new ParkArea(false, 740, 340 + (25 * i)), 740,
					340 + (25 * i), 110, 25));

			// 오토바이랑 장애인 주차구역은 따로 구현 아 ㅋㅋ
		}

		// ui 구현 완료 -> 다익스트라까지만 입히기

		lbVehicle.addMouseListener(new MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent e) {
				new ParkWhetherDialog().setVisible(true);
			};
		});

		add(northPanel, BorderLayout.NORTH);
		add(centerPanel);
	}

	public boolean checkParking() {
		// 현재 자리가 주차된 차가 있는지 확인
		/*
		 * try (PreparedStatement pst = connection.prepareStatement("select * from")) {
		 * 
		 * } catch (Exception e) { e.printStackTrace(); } return true;
		 */

		return true;
	}

	Thread moveThread;

	public void startPark() {
		// 입차 시스템

		if (moveThread != null)
			moveThread.stop();

		VehicleFunction vehicle = carInfo.getVehicleFunction();

		vehicle.setLocation(890, 420);
		centerPanel.add(vehicle);

		vehicle.move(park); // 구역도 보내야 함

//		moveThread = new Thread() {
//			@Override
//			public void run() {
//				super.run();
//
//				int x = 890, y = 420;
//				int currentX = 0, currentY = 0;
//
//				try {
//					for (int i = 1; i <= 400; i++) {
//						vehicle.setLocation(x, currentY = y - i);
//						Thread.sleep(10);
//					}
//					y = currentY;
//
//					vehicle.turn(-1); // 자동차가 도는거를 구현해야한다
//
//					for (int i = 1; i <= 690; i++) {
//						vehicle.setLocation(currentX = x - i, y);
//						Thread.sleep(10);
//					}
//					x = currentX;
//
//					vehicle.turn(0);
//
//					for (int i = 1; i <= 80; i++) {
//						vehicle.setLocation(x, currentY = y + i);
//						Thread.sleep(10);
//					}
//
//					y = currentY;
//
//					// if y 좌표가 자동차 길이 보다 작을 때
//
//					vehicle.turn(-1);
//					for (int i = 1; i <= 120; i++) {
//						vehicle.setLocation(currentX = x - i, y);
//						Thread.sleep(10);
//					}
//					currentX = x;
//
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		};
//
//		moveThread.start();
	}

	public static void main(String[] args) {
		new MainForm().setVisible(true);
	}

	class ParkWhetherDialog extends JFrame {
		public ParkWhetherDialog() {
			setSize(400, 150);
			setLocationRelativeTo(null);
			setLayout(new GridLayout(1, 0));
			add(createButton("입차",
					e -> openFrame(new SelectParkVehicleForm(MainForm.this, carInfo), ParkWhetherDialog.this)));
			add(createButton("취소", e -> dispose()));
		}
	}

	class ParkArea extends JPanel {

		Color blueOrRed = Color.blue;

		int x = 0, y = 0;

		public ParkArea(boolean leftOrRight, int x, int y) {
			this.x = x;
			this.y = y;

			setOpaque(false);
			setPreferredSize(new Dimension(100, 30));

			setLayout(null);

			int startX = 0;
			if (leftOrRight == true) {
				startX = 100;
			}

			if (!checkParking()) {
				blueOrRed = Color.red;
			}

			add(createComponent(new JLabel() {
				@Override
				protected void paintComponent(Graphics g) {
					super.paintComponent(g);
					g.setColor(blueOrRed);
					g.drawOval(0, 0, 10, 10);
					g.fillOval(0, 0, 10, 10);
				}
			}, startX, 15, 30, 30));
		}

	}
}
