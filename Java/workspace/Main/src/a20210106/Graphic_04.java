package a20210106;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class Graphic_04 extends JFrame {
	public Graphic_04() {
		setSize(500, 500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		add(new GraphicsPanel());
	}

	public static void main(String[] args) {
		new Graphic_04().setVisible(true);
	}

	class GraphicsPanel extends JPanel {

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.setColor(Color.yellow);
			g.fillOval(30, 30, 200, 200);
			g.setColor(Color.black);
			
			g.drawArc(60, 80, 50, 50, 180, -180);
			g.drawArc(150, 80, 50, 50, 180, -180);
			g.drawArc(80, 130, 100, 70, 180, 180);
		}
	}
}
