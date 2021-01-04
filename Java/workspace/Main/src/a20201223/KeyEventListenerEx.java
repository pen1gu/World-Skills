package a20201223;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

public class KeyEventListenerEx extends JFrame {

	JLabel[] label = new JLabel[3];

	public KeyEventListenerEx() {
		setSize(500, 300);
		setLocationRelativeTo(null);
		setLayout(new FlowLayout());
		setTitle("");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		label[0] = new JLabel("getKeyCode");
		label[1] = new JLabel("getKeyChar");
		label[2] = new JLabel("getKeyText");

		for (int i = 0; i < label.length; i++) {
			label[i].setBackground(Color.yellow);
			label[i].setOpaque(true);
			add(label[i]);
		}

		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				label[0].setText(Integer.toBinaryString(e.getKeyCode()));
				label[1].setText(" " + e.getKeyChar() + " ");
				label[2].setText(KeyEvent.getKeyText(e.getKeyCode()));
			}
		});
	}

	public static void main(String[] args) {
		new KeyEventListenerEx().setVisible(true);
	}
}
