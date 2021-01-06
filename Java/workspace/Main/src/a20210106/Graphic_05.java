package a20210106;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class Graphic_05 extends JFrame {

	public Graphic_05() {
		setSize(800, 700);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		add(new GraphicsPanel());
	}

	public void drawTree(Graphics g, int x1, int y1, double angle, int depth) {
		if (depth == 0) {
			return;
		}

		int x2 = x1 + (int) (Math.cos(Math.toRadians(angle)) * depth * 10.0);
		int y2 = y1 + (int) (Math.sin(Math.toRadians(angle)) * depth * 10.0);
		g.drawLine(x1, y1, x2, y2);
		drawTree(g, x2, y2, angle - 20, depth - 1);
		drawTree(g, x2, y2, angle + 20, depth - 1);
	}

	public static void main(String[] args) {
		new Graphic_05().setVisible(true);
	}

	class GraphicsPanel extends JPanel {
		@Override
		public void paint(Graphics g) {
			super.paint(g);
			g.setColor(Color.black);
			drawTree(g, 400, 600, -90, 10);
		}
	}
}
