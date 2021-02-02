package vehicle;

import static frame.BaseFrame.getImage;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import model.ParkingAlgorithm;
import model.VehicleFunction;

public class Car extends VehicleFunction {

	final String department = "A";
	int turnCheck = 0;
//	boolean check = false;

	private int x = 890;
	private int y = 420;

	final static int carSpeed = 5;

	public Car(String path) {
		setSize(60, 60);
		setIcon(getImage(60, 60, path));

	}

	@Override
	protected void paintComponent(Graphics g) { // 공부
		Graphics2D g2d = (Graphics2D) g;
		if (turnCheck == 1) {
			g2d.rotate(Math.PI / 2, 25, 30); // 오른쪽
		} else if (turnCheck == -1) {
			g2d.rotate(Math.PI * 1.5, 25, 30); // 왼쪽
		} else if (turnCheck == 2) { // 아래
			g2d.rotate(Math.PI, 25, 30);
		} else if (turnCheck == 0) { // 위
			g2d.rotate(Math.PI * 2, 25, 30);
		}
		super.paintComponent(g2d);
		setPreferredSize(new Dimension(60, 60));
	}

	public void move(JPanel[][] department) {
		// 들어가야할 장소를 찾는다
		// 그 장소까지의 최단거리를 찾는다.
		int index = searchEmptySpace();
		System.out.println(index);

		if (index == -1) {
			JOptionPane.showMessageDialog(null, "주차할 공간이 없습니다.", "error", JOptionPane.ERROR_MESSAGE);
			return;
		} // 빈 주차 공간 확인

		new Thread(() -> {
			moveUp(x, 400);
			turn(-1);
			moveLeft(690, y);
			turn(2);
			moveDown(x, (index * 36) + 36);
			turn(-1);
			moveLeft(120, y);
		}).start(); // 돌리는 동작, 알고리즘 클래스화, 주차 반대 공간 활용
	}

	public int searchEmptySpace() {
		for (int i = 1; i < ParkingAlgorithm.parked.length; i++) {
			if (ParkingAlgorithm.parked[i][0] == false) { // 현재 자동차 객체가 들어있는지 확인.
				ParkingAlgorithm.parked[i][0] = true;
				return i;
			}
		}

		return -1;
	}

	@Override
	public void turn(int leftOrRight) {
		turnCheck = leftOrRight;
	}

	public void setDepartmentLocation(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void moveUp(int x, int y) {
		try {
			for (int i = 1; i <= y; i++) {
				setLocation(x, this.y - i);
				Thread.sleep(carSpeed);
			}

			this.y -= y;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void moveDown(int x, int y) {
		try {
			for (int i = 1; i <= y; i++) {
				setLocation(x, this.y + i);
				Thread.sleep(carSpeed);
			}
			this.y += y;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void moveRight(int x, int y) {
		try {
			for (int i = 1; i <= x; i++) {
				setLocation(this.x + i, y);
				Thread.sleep(carSpeed);
			}
			this.x += x;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void moveLeft(int x, int y) {
		try {
			for (int i = 1; i <= x; i++) {
				setLocation(this.x - i, y);
				Thread.sleep(carSpeed);
			}
			this.x -= x;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
