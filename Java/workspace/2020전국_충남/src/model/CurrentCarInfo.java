package model;

import javax.swing.JLabel;
import static frame.BaseFrame.getImage;

public class CurrentCarInfo {

	// 자동차의 타입을 기반으로하여 불러올 자동차를 판별?

	private String carType;
	private int carNo;
	private String parkNo;

	private VehicleFunction function;
	private JLabel lbCarImage;

	public void setFunction(AutoVehicle autoVehicle, String path) {
		function = autoVehicle.createFunction(carType, path);
	}

	public VehicleFunction getVehicleFunction() {
		return function;
	} // 이렇게 사용할 시 객체를 위임할 수 있음.

	public JLabel getCarImage() {
		return lbCarImage;
	}

	public void setCarImage(JLabel lbCarImage) {
		this.lbCarImage = lbCarImage;
	}

	public String getCarType() {
		return carType;
	}

	public void setCarType(String carType) {
		this.carType = carType;
	}

	public int getCarNo() {
		return carNo;
	}

	public void setCarNo(int carNo) {
		this.carNo = carNo;
	}

	public String getParkNo() {
		return parkNo;
	}

	public void setParkNo(String parkNo) {
		this.parkNo = parkNo;
	}

}
