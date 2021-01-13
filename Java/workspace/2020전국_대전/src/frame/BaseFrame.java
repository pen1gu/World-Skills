package frame;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
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
		setLocationRelativeTo(null);
		setTitle(title);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}

	public static void errorMessage(String caption, String txt) {
		JOptionPane.showInternalMessageDialog(null, caption, txt, JOptionPane.ERROR_MESSAGE);
	}

	public static void informationMessage(String caption, String txt) {
		JOptionPane.showInternalMessageDialog(null, caption, txt, JOptionPane.INFORMATION_MESSAGE);
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
	
	public static JButton createButton(String text, ActionListener actionListener) {
		JButton button = new JButton(text);
		button.addActionListener(actionListener);
		return button;
	}
	
	public static JButton createButtonWithoutMargin(String text, ActionListener actionListener) {
		JButton button = new JButton(text);
		button.addActionListener(actionListener);
		button.setMargin(new Insets(0, 0, 0, 0));
		return button;
	}
	
	public static ImageIcon getImage(String path, int width, int height) {
		return new ImageIcon();
	}

}
