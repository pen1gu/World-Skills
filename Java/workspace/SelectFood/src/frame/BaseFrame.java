package frame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

public class BaseFrame extends JFrame {
	public BaseFrame(int width, int height, String title) {
		setSize(width, height);
		setTitle(title);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	}

	public static void errorMessage(String msg) {
		JOptionPane.showMessageDialog(null, msg, "메시지", JOptionPane.ERROR_MESSAGE);
	}

	public static void informationMessage(String msg) {
		JOptionPane.showMessageDialog(null, msg, "메시지", JOptionPane.INFORMATION_MESSAGE);
	}

	public static JLabel createLabel(JLabel label, Font font, Color color) {
		label.setFont(font);
		label.setForeground(color);
		return label;
	}

	public static <T extends JComponent> T createComponent(T comp, int x, int y, int width, int height) {
		comp.setBounds(x, y, width, height);
		return comp;
	}

	public static <T extends JComponent> T createComponent(T comp, int width, int height) {
		comp.setPreferredSize(new Dimension(width, height));
		return comp;
	}

	public JButton createButton(String text, ActionListener actionListener) {
		JButton button = new JButton(text);
		button.addActionListener(actionListener);
		return button;
	}

	public static JButton createButtonWithOutMargin(String text, ActionListener actionListener) {
		JButton button = new JButton(text);
		button.addActionListener(actionListener);
		button.setMargin(new Insets(0, 0, 0, 0));
		return button;
	}

	public static ImageIcon getImage(String path, int width, int height) {
		return new ImageIcon(
				Toolkit.getDefaultToolkit().getImage(path).getScaledInstance(width, height, Image.SCALE_SMOOTH));
	}

	public void openFrame(JFrame frame) {
		dispose();
		frame.setVisible(true);
	}
}