package frame;

import java.awt.Dimension;

import javax.swing.JPanel;

public class BasePage extends JPanel {
	public BasePage(int width, int height) {
		setPreferredSize(new Dimension(width, height));
	}

	public void changePage(JPanel openPanel) {
		MainFrame.contentsPanel.removeAll();

		MainFrame.contentsPanel.add(openPanel);
		openPanel.setVisible(true);

	}

	public void closePage() {
		MainFrame.contentsPanel.removeAll();
		MainFrame.contentsPanel.add(MainFrame.mainPage);
		MainFrame.contentsPanel.revalidate();
		MainFrame.contentsPanel.repaint();
	}

	public void reconfiguration() {
		revalidate();
		repaint();
	}
}
