package a20201120;

import java.awt.*;
import java.util.*;
import javax.swing.*;

public class SwingEx extends JFrame {

	public SwingEx() {
		setSize(300,300);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Random random = new Random();

		Container container = getContentPane();
		container.setLayout(null);

		for (int i = 0; i < 30; i++) {
			JLabel label;
			label = new JLabel(Integer.toString(i));
			label.setBounds(random.nextInt(300), random.nextInt(300), 20, 20);
			label.setForeground(Color.pink);
			container.add(label);
		}
	}

	public static void main(String[] args) {
		new SwingEx().setVisible(true);
	}

}