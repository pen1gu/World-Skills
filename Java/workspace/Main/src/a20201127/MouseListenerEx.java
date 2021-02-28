package a20201127;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class MouseListenerEx extends JFrame {
	Container container = getContentPane();
	JLabel label = new JLabel("안녕");

	public MouseListenerEx() {
		setSize(800, 800);
		setLocationRelativeTo(null);
		setTitle("마우스 리스너");
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		container.setLayout(null);

		label.setSize(150, 20);

		container.addMouseListener(new MouseEventListener());
		container.add(label);
	}

	class MouseEventListener extends MouseAdapter {
		@Override
		public void mousePressed(MouseEvent e) {
			super.mousePressed(e);
			container.setBackground(
					new Color((int) (Math.random() * 256), (int) (Math.random() * 256), (int) (Math.random() * 256)));
		}
	}

	public static void main(String[] args) {
		new MouseListenerEx().setVisible(true);
	}
}
