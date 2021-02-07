package frame;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class BasePage extends JPanel {
	public BasePage(int width, int height) {
		setPreferredSize(new Dimension(width, height));
	}

	public void changePage(JPanel openPanel) {
		MainFrame.contentsPanel.removeAll();
		MainFrame.contentsPanel.add(openPanel);
		openPanel.setVisible(true);
		reconfiguration();
	}

	public void closePage() {
		MainFrame.contentsPanel.removeAll();
		MainFrame.contentsPanel.add(MainFrame.mainPage);
		reconfiguration();
	}

	public void reconfiguration() {
		MainFrame.contentsPanel.revalidate();
		MainFrame.contentsPanel.repaint();
	}

	public void setButtonSetting(JButton button, Color backColor, Color borderColor) {
		button.setBorder(new LineBorder(borderColor));
		button.setBackground(backColor);
	}
}
