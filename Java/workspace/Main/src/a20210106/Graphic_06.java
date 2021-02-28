package a20210106;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class Graphic_06 extends JFrame {

	public Graphic_06() {
		setTitle("주사위 굴리기");
		setSize(200, 200);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		add(new DoubleDice());

	}

	public static void main(String[] args) {
		new Graphic_06().setVisible(true);
	}

}

class DoubleDice extends JPanel {
	int dice1 = 2;
	int dice2 = 6;

	public DoubleDice() {
		super();
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				super.mousePressed(e);
				roll();
			}
		});
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawDice(g, dice1, 10, 10);
		drawDice(g, dice2, 60, 60);
	}

	public void drawDice(Graphics g, int val, int x, int y) {
		g.setColor(Color.white);
		g.fillRect(x, y, 35, 35);
		g.setColor(Color.black);
		g.drawRect(x, y, 34, 34);

		switch (val) {
		case 1:
			g.fillOval(x + 13, y + 13, 9, 9);
			break;

		case 2:
			g.fillOval(x + 3, y + 3, 9, 9);
			g.fillOval(x + 23, y + 23, 9, 9);
		case 3:
			g.fillOval(x + 3, y + 3, 9, 9);
			g.fillOval(x + 13, y + 13, 9, 9);
			g.fillOval(x + 23, y + 23, 9, 9);
			break;
		case 4:
			g.fillOval(x + 3, y + 3, 9, 9);
			g.fillOval(x + 3, y + 23, 9, 9);
			g.fillOval(x + 23, y + 3, 9, 9);
			g.fillOval(x + 23, y + 23, 9, 9);
			break;
		case 5:
			g.fillOval(x + 3, y + 3, 9, 9);
			g.fillOval(x + 23, y + 3, 9, 9);
			g.fillOval(x + 13, y + 13, 9, 9);
			g.fillOval(x + 3, y + 23, 9, 9);
			g.fillOval(x + 23, y + 23, 9, 9);
			break;
		case 6:
			g.fillOval(x + 3, y + 3, 9, 9);
			g.fillOval(x + 3, y + 13, 9, 9);
			g.fillOval(x + 3, y + 23, 9, 9);
			g.fillOval(x + 23, y + 3, 9, 9);
			g.fillOval(x + 23, y + 13, 9, 9);
			g.fillOval(x + 23, y + 23, 9, 9);
			break;
		default:
			throw new IllegalArgumentException("Unexpected value: " + val);
		}
	}

	void roll() {
		dice1 = (int) (Math.random() * 6) + 1;
		dice2 = (int) (Math.random() * 6) + 1;
		repaint();
	}
}
