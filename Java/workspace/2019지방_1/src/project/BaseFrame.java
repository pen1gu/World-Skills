package project;

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
import javax.swing.table.DefaultTableCellRenderer;

public abstract class BaseFrame extends JFrame {

	static Connection connection;
	static Statement statement;

	static int userNo;
	static String userName;
	static String userGrade;
	static int userPoint;

	static DefaultTableCellRenderer centerRender = new DefaultTableCellRenderer();

	static {
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost/coffee?serverTimezone=UTC", "user",
					"1234");
			statement = connection.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}

		userNo = 1;
		userName = "이기민";
		userGrade = "일반";
		userPoint = 14210;

		centerRender.setHorizontalAlignment(0);
	}

	public BaseFrame(int width, int height, String title) {
		setSize(width, height);
		setTitle(title);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(2);
	}

	public static void errorMessage(String msg) {
		JOptionPane.showMessageDialog(null, msg, "메시지", JOptionPane.ERROR_MESSAGE);
	}

	public static void informationMessage(String msg) {
		JOptionPane.showMessageDialog(null, msg, "메시지", JOptionPane.INFORMATION_MESSAGE);
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

	public static JButton createButtonWithoutMargin(String text, ActionListener e) {
		JButton button = new JButton(text);
		button.addActionListener(e);
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
	
	public abstract void addComponents();
	public abstract void setComponents();
}
