package setting;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.tools.Tool;
import java.awt.*;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class BaseFrame extends JFrame {
    static Connection connection;
    static Statement statement;

    static String userName;
    static int userNo;
    static int userPoint;
    static String userGrade;

    static DefaultTableCellRenderer centerRender;

    static {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost/coffee?serverTimezone=UTC", "user", "1234");
            statement = connection.createStatement();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        userName = "이기민";
        userNo = 1;
        userGrade = "일반";
        userPoint = 14210;
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
