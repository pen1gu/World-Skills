package a20210106;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class Graphic_02 extends JFrame {

	public Graphic_02() {
		setSize(500, 500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		add(new GraphicsPanel());
	}

	public static void main(String[] args) {
		new Graphic_02().setVisible(true);
	}

	class GraphicsPanel extends JPanel {

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawString("자바는 재미있다", 30, 30);
			g.drawString("꿀잼", 30, 60);
			g.setColor(Color.magenta);

			for (int i = 1; i <= 5; i++) {
				g.setFont(new Font("궁서체", Font.PLAIN, i * 10));
				g.drawString("재밌당", 20, 50 + 60 * i);
			}
		}
	}
}
