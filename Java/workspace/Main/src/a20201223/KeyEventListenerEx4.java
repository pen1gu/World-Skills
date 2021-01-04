package a20201223;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

public class KeyEventListenerEx4 extends JFrame {


	public KeyEventListenerEx4() {
		setSize(500, 300);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		Container container = getContentPane();
		JLabel label = new JLabel("Christmas");
		container.setLayout(null);

		label.setSize(100, 30);
		label.setLocation(50, 50);

		container.add(label);

		container.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				super.keyPressed(e);
				int size = label.getFont().getSize();
				if (e.getKeyCode() == KeyEvent.VK_UP) {
					label.setFont(new Font("맑은고딕", 1, size + 5));
				} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					label.setFont(new Font("맑은고딕", 1, size - 5));
				}
				repaint();
			}
		});

		container.setFocusable(true);
		container.requestFocus();
	}

	public static void main(String[] args) {
		new KeyEventListenerEx4().setVisible(true);
	}
}
