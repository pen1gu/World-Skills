package vehicle;

import static frame.BaseFrame.getImage;

import model.VehicleFunction;

public class Car extends VehicleFunction {

	final String department = "A";
	private String path;

	private int x;
	private int y;

	public Car(String path) {
		this.path = path;
		setSize(60, 80);
		setIcon(getImage(60, 80, path));
	}

	@Override
	public void move() {

	}

	@Override
	public void turn(boolean leftOrRight) {
		// TODO Auto-generated method stub
	}

	public void setDepartmentLocation(int x, int y) {
		this.x = x;
		this.y = y;
	}
}
