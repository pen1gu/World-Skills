package exam;

import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

public class Exam03 extends JFrame {

	JLabel lbMainTitle = new JLabel("Love JAVA");

	public Exam03() {
		setTitle("");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setSize(400, 150);
		setLayout(new FlowLayout());

		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				int cursor = e.getModifiers();
				if (cursor == 16) {
					moveText(true);
				} else if (cursor == 4) {
					moveText(false);
				}
			};
		});

		add(lbMainTitle);
	}

	public void moveText(boolean check) {
		String txt = lbMainTitle.getText();
		if (check) {
			lbMainTitle.setText(txt.substring(1, txt.length()) + txt.substring(0, 1));
		} else {
			lbMainTitle.setText(txt.substring(txt.length() - 1, txt.length()) + txt.substring(0, txt.length() - 1));
		}
	}

	public static void main(String[] args) {
		new Exam03().setVisible(true);
	}
}
