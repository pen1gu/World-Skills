package a20201223;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

public class KeyEventListenerEx3 extends JFrame {

	JLabel label = new JLabel("Xmas");

	public KeyEventListenerEx3() {
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		Container container = getContentPane();
		container.setLayout(null);
		label.setLocation(50, 50);
		label.setSize(100, 30);
		container.add(label);

		container.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				super.keyPressed(e);
				int keyCode = e.getKeyCode();

				switch (keyCode) {
				case KeyEvent.VK_UP: {
					label.setLocation(label.getX(), label.getY() - 10);
					break;
				}
				case KeyEvent.VK_DOWN:
					label.setLocation(label.getX(), label.getY() + 10);
				case KeyEvent.VK_LEFT:
					label.setLocation(label.getX() - 10, label.getY());
					break;
				case KeyEvent.VK_RIGHT:
					label.setLocation(label.getX() + 10, label.getY());
					break;
				default:
					throw new IllegalArgumentException("Unexpected value: " + keyCode);
				}
				repaint();
			}
		});

		setSize(500, 500);

		container.setFocusable(true);
		container.requestFocus();
	}

	public static void main(String[] args) {
		new KeyEventListenerEx3().setVisible(true);
	}
}
