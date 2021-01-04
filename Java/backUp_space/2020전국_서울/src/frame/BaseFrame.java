package frame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class BaseFrame extends JFrame {
	static Connection connection;
	static Statement statement;

	static int userNo = 1;
	static String userName = "신승민";
	static boolean loginCheck = false;

	static {
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost/reminder?serverTimezone=UTC", "user", "1234");
			statement = connection.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	BaseFrame(int w, int h, String title) {
		setSize(w, h);
		setTitle(title);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(2);
	}

	public static void errorMessage(String text) {
		JOptionPane.showMessageDialog(null, text, "메시지", JOptionPane.ERROR_MESSAGE);
	}

	public static void informationMessage(String text) {
		JOptionPane.showMessageDialog(null, text, "메시지", JOptionPane.ERROR_MESSAGE);
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

	public static JButton createButton(String text, ActionListener e) {
		JButton button = new JButton(text);
		button.addActionListener(e);
		return button;
	}

	public void openFrame(JFrame frame) {
		dispose();
		frame.setVisible(true);
	}

	public static ImageIcon getImage(int width, int height, String path) {
		return new ImageIcon(
				Toolkit.getDefaultToolkit().getImage(path).getScaledInstance(width, height, Image.SCALE_SMOOTH));
	}
}
