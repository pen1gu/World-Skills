package a20201204;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class MouseEventEx extends JFrame {
	Container container = getContentPane();
	JLabel label = new JLabel("안녕");

	public MouseEventEx() {
		setSize(600, 600);
		setLocationRelativeTo(null);
		setTitle("마우스 이벤트");
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		container.setLayout(null);

		label.setSize(50, 20);

		addMouseListener(new MyMouseListener());
		
		container.add(label);
	}

	public static void main(String[] args) {
		new MouseEventEx().setVisible(true);
	}

	class MyMouseListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			label.setLocation(e.getX(), e.getY());
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			label.setText("x좌표: " + e.getX() + " 좌표: " + e.getY());
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			container.setBackground(Color.cyan);
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			container.setBackground(Color.yellow);
		}

	}
}
