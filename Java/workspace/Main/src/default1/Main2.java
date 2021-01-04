package default1;

import java.awt.Button;
import java.awt.Choice;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;

public class Main2 extends JFrame {

	Choice choice = new Choice();

	public Main2() {
		setSize(500, 500);
		setLocationRelativeTo(null);
		setLayout(new FlowLayout());
//		Canvas canvas = new Canvas();

		choice.add("hello world1");
		choice.add("hello world2");
		choice.add("hello world3");
		add(choice);

		Button button = new Button("Hello");
		button.addActionListener(e -> getChoice());
		add(button);
	}

	public void getChoice() {
		System.out.println(choice.getSelectedItem());
	}

	public static void main(String[] args) {
		new Main2().setVisible(true);
	}
}
