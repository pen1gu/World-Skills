package model;

import static frame.BaseFrame.createComponent;

import javax.swing.JLabel;
import javax.swing.JPanel;

public abstract class VehicleFunction extends JLabel {

	private JLabel lbCarImage;

	public VehicleFunction() {
		setSize(60, 80);
	}

	public void setCarImage(JLabel lbcarImage) {
		this.lbCarImage = lbcarImage;
	}

	public JLabel getCarImage() {
		return createComponent(lbCarImage, 60, 120);
	}

	public abstract void move(JPanel[][] department);

	public abstract void turn(int leftOrRight);
}
