package frame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class BaseFrame extends JFrame {
    static int userNo;
    static String userId;
    static String userName;
    static boolean checkLogin;

    static {
        userNo = 1;
        userId = "wogns342";
        userName = "이재훈";
    }

    BaseFrame(String title, int WIDTH, int HEIGHT) {
        setSize(WIDTH, HEIGHT);
        setTitle(title);
        setDefaultCloseOperation(2);
        setLocationRelativeTo(null);
    }

    public static void errorMessage(String msg) {
        JOptionPane.showMessageDialog(null, msg, "ERR", JOptionPane.ERROR_MESSAGE);
    }

    public static void informationMessage(String msg) {
        JOptionPane.showMessageDialog(null, msg, "Message", JOptionPane.INFORMATION_MESSAGE);
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

    public void openModal(JDialog dialog) {
        dispose();
        dialog.setVisible(true);
    }

    public void openFrame(JFrame frame) {
        dispose();
        frame.setVisible(true);
    }
}
