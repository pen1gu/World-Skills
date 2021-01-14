import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

public class MoveCar extends JFrame {
	public MoveCar() {
		setSize(1000, 100);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		setLayout(null);

		JLabel lbCar = new JLabel(new ImageIcon(
				Toolkit.getDefaultToolkit().getImage("./Car.png").getScaledInstance(100, 50, Image.SCALE_SMOOTH)));
		lbCar.setBounds(10, 10, 100, 50);

		new Thread() {
			@Override
			public void run() {
				try {
					for (int i = 10; i < 1000; i++) {
						lbCar.setLocation(i, 10);
						Thread.sleep(2);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.start();;

		add(lbCar);
	}

	public static void main(String[] args) {
		new MoveCar().setVisible(true);
	}
}
