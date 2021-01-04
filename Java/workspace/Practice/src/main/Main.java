package main;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class Main extends JFrame {

	static final int WIDTH = 300, HEIGHT = 500;

	JTextField txtField = new JTextField(10);
	JPanel panel = new JPanel();

	public Main() {
		setTitle("1번 문제");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(WIDTH, HEIGHT);
		setLayout(new FlowLayout(FlowLayout.CENTER));
		JButton button = new JButton();

		panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		add(txtField);
		add(button);
		add(panel);
		button.addActionListener(e -> getStars());
		setResizable(false);
	}

	public void getStars() {
		panel.removeAll();

		for (int i = 1; i <= Integer.parseInt(txtField.getText()); i++) {
			JLabel label;
			StringBuilder stars = new StringBuilder();
			for (int j = 1; j <= i * 2 - 1; j++) {
				stars.append("*");
			}
			panel.add(label = new JLabel(stars.toString(), 0));
			label.setFont(new Font("맑은고딕", Font.PLAIN, 18));
			label.setPreferredSize(new Dimension(WIDTH - 20, 20));
		}

		for (int i = Integer.parseInt(txtField.getText()) - 1; i >= 1; i--) {
			JLabel label;
			StringBuilder stars = new StringBuilder();
			for (int j = i * 2 - 1; j >= 1; j--) {
				stars.append("*");
			}
			panel.add(label = new JLabel(stars.toString(), 0));
			label.setFont(new Font("맑은고딕", Font.PLAIN, 18));
			label.setPreferredSize(new Dimension(WIDTH - 20, 20));
		}

		revalidate();
		repaint();
	}

	public static void main(String[] args) {
		new Main().setVisible(true);
	}
}
