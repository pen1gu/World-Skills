package frame;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

@SuppressWarnings("serial")
public class BaseFrame extends JFrame{
	
	public BaseFrame(int width, int height, String title) {
		setSize(width,height);
		setTitle(title);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(2);
	}
	
	public static void errorMessage(String msg) {
		JOptionPane.showInternalMessageDialog(null, msg,"메시지",JOptionPane.ERROR_MESSAGE);
	}
	
	public static void informationMessage(String msg) {
		JOptionPane.showInternalMessageDialog(null, msg,"메시지",JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static JLabel createLabel(JLabel label, Font font) {
		label.setFont(font);
		return label;
	}
	
	public static <T extends JComponent> T createComponenet(T comp, int x, int y, int w, int h) {
		comp.setBounds(x,y,w,h);
		return comp;
	}
	
	public static <T extends JComponent> T createComponenet(T comp, int w, int h) {
		comp.setPreferredSize(new Dimension(w,h));
		return comp;
	}
	
	public static JButton createButton(String text, ActionListener action) {
		JButton button = new JButton(text);
		button.addActionListener(action);
		return button;
	}
	
	public static JButton createButtonWithMargin(String text, ActionListener action) {
		JButton button = new JButton(text);
		button.addActionListener(action);
		button.setMargin(new Insets(0,0,0,0));
		return button;
	}
	
	public static ImageIcon getImage(int w, int h, String path) {
		return new ImageIcon(Toolkit.getDefaultToolkit().getImage(path).getScaledInstance(w, h, Image.SCALE_SMOOTH));
	}
	
	public void openFrame(JFrame frame) {
		dispose();
		frame.setVisible(true);
	}
}
