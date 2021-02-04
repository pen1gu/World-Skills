package frame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javax.swing.Action;
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
		setLocationRelativeTo(null);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setTitle(title);
	}

	public static void errorMessage(String message) {
		JOptionPane.showMessageDialog(null, message, "", JOptionPane.ERROR_MESSAGE);
	}

	public static void informationMessage(String message) {
		JOptionPane.showMessageDialog(null, message, "", JOptionPane.INFORMATION_MESSAGE);
	}

	public static JLabel createLabel(JLabel label, Font font) {
		label.setFont(font);
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

	public static JButton createButton(String txt, ActionListener actionListener) {
		JButton btn = new JButton(txt);
		btn.addActionListener(actionListener);
		return btn;
	}

	public static JButton createButtonWithColor(String txt, ActionListener actionListener, Color color) {
		JButton btn = new JButton(txt);
		btn.addActionListener(actionListener);
		btn.setBackground(color);
		return btn;
	}

	public static JButton createButtonWithoutMargin(String txt, ActionListener actionListener) {
		JButton btn = new JButton(txt);
		btn.addActionListener(actionListener);
		btn.setMargin(new Insets(0, 0, 0, 0));
		return btn;
	}

	public static ImageIcon getImage(String path, int width, int height) {
		return new ImageIcon(
				Toolkit.getDefaultToolkit().getImage(path).getScaledInstance(width, height, Image.SCALE_SMOOTH));
	}

	public static ImageIcon getImage(byte[] bytes, int width, int height) {
		return new ImageIcon(
				Toolkit.getDefaultToolkit().createImage(bytes).getScaledInstance(width, height, Image.SCALE_SMOOTH));
	}

	public void openFrame(JFrame frame) {
		dispose();
		frame.setVisible(true);
	}

}
