package frame;

import javax.swing.JPanel;

public class BasePage extends JPanel {
	public BasePage(int width, int height) {
		setSize(width, height);
	}

	public void changePage(JPanel closePanel, JPanel openPanel) {
		closePanel.setVisible(false);
		openPanel.setVisible(true);
	}
}
