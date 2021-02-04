package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;

import javax.swing.JLabel;
import javax.swing.JPanel;

import model.ImageFiles;

public class MainFrame extends BaseFrame {
	JPanel contentsPanel = new JPanel();

	public MainFrame() {
		super(800, 500, "메인");
		contentsPanel.add(new MainPage(MainFrame.this));
		setLayout(new BorderLayout());
		add(contentsPanel);
	}

	public static void main(String[] args) {
		new MainFrame().setVisible(true);
	}

	public void resetContents() {
		contentsPanel.removeAll();
	}
}
