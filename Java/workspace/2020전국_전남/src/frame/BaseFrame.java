package frame;

import java.awt.Color;
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
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;

public class BaseFrame extends JFrame {
	static Connection connection;
	static Statement statement;

	static {
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost/Projectlibre?serverTimezone=UTC", "user",
					"1234");
			statement = connection.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public BaseFrame(int width, int height, String title) {
		setSize(width, height);
		setTitle(title);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
	}

	public static void errorMessage(String msg, String caption) {
		JOptionPane.showMessageDialog(null, msg, caption, JOptionPane.ERROR_MESSAGE);
	}

	public static void informatinoMessage(String msg, String caption) {
		JOptionPane.showMessageDialog(null, msg, caption, JOptionPane.INFORMATION_MESSAGE);
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

	public static JButton createButton(String msg, ActionListener e) {
		JButton button = new JButton(msg);
		button.addActionListener(e);
		return button;
	}

	public static JButton createButtonWithoutMargin(String msg, ActionListener e) {
		JButton button = new JButton(msg);
		button.addActionListener(e);
		button.setMargin(new Insets(0, 0, 0, 0));
		return button;
	}

	public static ImageIcon getImage(int width, int height, String path) {
		return new ImageIcon(
				Toolkit.getDefaultToolkit().getImage(path).getScaledInstance(width, height, Image.SCALE_SMOOTH));
	}

	public void openFrame(JFrame frame) {
		dispose();
		frame.setVisible(true);
	}

	public void changePage(JPanel openPanel) {
		MainFrame.centerPanel.removeAll();
		MainFrame.centerPanel.add(openPanel);
		MainFrame.centerPanel.revalidate();
		MainFrame.centerPanel.repaint();
	}

	public static void setDefaultColor(JPanel panel) {
		panel.setBackground(Color.white);
		panel.setBorder(new LineBorder(Color.gray));
	}
}
