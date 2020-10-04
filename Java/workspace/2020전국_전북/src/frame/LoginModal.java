package frame;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;


public class LoginModal extends BaseModal {

    static int width = 400, height = 400;

    LoginModal() {
        super("LOGIN", width, height, new LoginFrame());
    }

    public static void main(String[] args) {
        new LoginModal().setVisible(true);
    }
    
    static class LoginFrame extends BaseFrame {


        JTextField tfId = createComponent(new JTextField(), 1, 1, 1, 1);
        JPanel centerpanel = createComponent(new JPanel(), width, height);

        LoginFrame() {
            super("LOGIN", 400, 400);
            setLayout(new FlowLayout());
//            add(createLabel(new JLabel("<html><p style = 'color:orange'>SMART air<p></html>", 0), new Font("Gothic", 1, 30)), BorderLayout.NORTH);

            centerpanel.add(createComponent(new JButton("what"), 100, 100));
            add(centerpanel);
        }
    }
}
