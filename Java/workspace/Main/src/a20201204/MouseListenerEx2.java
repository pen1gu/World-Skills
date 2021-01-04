package a20201204;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class MouseListenerEx2 extends JFrame {
	public MouseListenerEx2() {
		setSize(800, 800);
		setLocationRelativeTo(null);
		setTitle("마우스 리스너");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		Container container = getContentPane();
		container.setLayout(new FlowLayout());

		JLabel label = new JLabel("안녕");
		label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				super.mousePressed(e);
				label.setText("좋아해");
			}

			@Override
			public void mouseExited(MouseEvent e) {
				super.mouseExited(e);
				label.setText("안녕");
			}

			public void mousePressed(MouseEvent e) {
				super.mousePressed(e);
				container.setBackground(new Color((int) (Math.random() * 256), (int) (Math.random() * 256),
						(int) (Math.random() * 256)));
			}
		});

		container.add(label);
	}

	public static void main(String[] args) {
		new MouseListenerEx2().setVisible(true);
	}
}
