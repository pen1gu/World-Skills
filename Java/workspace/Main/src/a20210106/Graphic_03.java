package a20210106;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class Graphic_03 extends JFrame {
	public Graphic_03() {
		setSize(500, 500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		add(new GraphicsPanel());
	}

	public static void main(String[] args) {
		new Graphic_03().setVisible(true);
	}

	class GraphicsPanel extends JPanel {

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.setColor(Color.red);
			g.fillRect(10, 10, 50, 50);
			g.setColor(Color.orange);
			g.fillOval(10, 70, 50, 50);
			g.setColor(Color.yellow);
			g.fillRoundRect(10, 130, 50, 50, 20, 10);
			g.setColor(Color.green);
			g.fillArc(10, 190, 50, 50, 0, 270);
			g.setColor(Color.blue);
			int[] x = { 30, 10, 30, 60 };
			int[] y = { 250, 275, 300, 275 };
			g.fillPolygon(x, y, 4);
		}
	}
}
