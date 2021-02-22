package frame;

import java.sql.Statement;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableCellRenderer;

public class BaseFrame extends JFrame {

	static Connection connection;
	static Statement statement;

	static String userId;
	static String userName;

	static DefaultTableCellRenderer centerRender;

	static {
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost/sw3_1?serverTimezone=UTC", "user", "1234");
			statement = connection.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		userId = "user01";
		userName = "강**";
		centerRender = new DefaultTableCellRenderer();
		centerRender.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
	}

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

	public static void customMessage(String msg, String title, int option) {
		JOptionPane.showMessageDialog(null, msg, title, option);
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

	public static JButton createButton(String txt, ActionListener e) {
		JButton button = new JButton(txt);
		button.addActionListener(e);
		return button;
	}
	
	public static JToggleButton createToggleButton(String txt, ActionListener e) {
		JToggleButton button = new JToggleButton(txt);
		button.addActionListener(e);
		return button;
	}

	public static JButton createButtonWithoutMargin(String txt, ActionListener e) {
		JButton button = new JButton(txt);
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

	public static void changePanel(JPanel openPanel, JPanel closePanel) {
		openPanel.setVisible(true);
		closePanel.setVisible(false);
	}
}
