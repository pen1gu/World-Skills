package vehicle;

import static frame.BaseFrame.getImage;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import model.VehicleFunction;

public class Car extends VehicleFunction {

	final String department = "A";
	int turnCheck = 0;
//	boolean check = false;

	private int x;
	private int y;

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
		} else if (turnCheck == 2) { // 위
			g2d.rotate(Math.PI, 25, 30);
		} else if (turnCheck == 0) { // 아래
			g2d.rotate(Math.PI * 2, 25, 30);
		}
		super.paintComponent(g2d);
		setPreferredSize(new Dimension(60, 60));
	}

	@Override
	public void move() {

	}

	@Override
	public void turn(int leftOrRight) {
		turnCheck = leftOrRight;
	}

	public void setDepartmentLocation(int x, int y) {
		this.x = x;
		this.y = y;
	}
}
