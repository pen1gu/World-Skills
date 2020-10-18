package frame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class BaseFrame extends JFrame {
    static Connection connection;
    static Statement statement;

    static String userName;
    static int userNo;

    static {
        userNo = 1;
        userName = "장지용";

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost/market?serverTimezone=UTC", "user", "1234");
            statement = connection.createStatement();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    BaseFrame(String title, int width, int height) {
        setSize(width, height);
        setTitle(title);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(2);
    }

    public static void errorMessage(String text) {
        JOptionPane.showMessageDialog(null, text, "메시지", JOptionPane.ERROR_MESSAGE);
    }

    public static void informationMessage(String text) {
        JOptionPane.showMessageDialog(null, text, "메시지", JOptionPane.INFORMATION_MESSAGE);
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

    public static JButton createButtonWithOutMargin(String text, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.addActionListener(actionListener);
        button.setMargin(new Insets(0, 0, 0, 0));
        return button;
    }

    public static ImageIcon getImage(String path, int width, int height) {
        return new ImageIcon(Toolkit.getDefaultToolkit().getImage(path).getScaledInstance(width, height, Image.SCALE_SMOOTH));
    }

    public void openFrame(JFrame frame) {
        dispose();
        frame.setVisible(true);
    }

}