package a20201223;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

public class KeyEventListener2 extends JFrame {

	JLabel label = new JLabel();

	public KeyEventListener2() {
		setSize(500, 500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		Container container = getContentPane();
		container.setLayout(new FlowLayout());

		container.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				label.setText(KeyEvent.getKeyText(e.getKeyCode()));
				if (e.getKeyChar() == '%')
					container.setBackground(Color.yellow);
				else if (e.getKeyCode() == KeyEvent.VK_F1)
					container.setBackground(Color.blue);
			}
		});

		container.add(label);
		container.setFocusable(true);
		container.requestFocus();
	}

	public static void main(String[] args) {
		new KeyEventListener2().setVisible(true);
	}
}
