package a20210106;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class Graphic_01 extends JFrame {

	public Graphic_01() {
		setSize(500, 500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		Container container = getContentPane();
		container.add(new GraphicsPanel());
	}

	public static void main(String[] args) {
		new Graphic_01().setVisible(true);
	}

	class GraphicsPanel extends JPanel {

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.setColor(Color.red);
			g.drawRect(10, 10, 50, 50);
			g.drawRect(50, 50, 50, 100);
			g.setColor(Color.orange);
			g.drawOval(100, 100, 100, 100);
			g.setColor(Color.black);
			g.drawLine(250, 250, 400, 400);
		}
	}
}
