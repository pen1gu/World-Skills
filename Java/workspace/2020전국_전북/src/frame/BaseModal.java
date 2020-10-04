package frame;

import javax.swing.*;
import static frame.BaseFrame.createComponent;
import static frame.BaseFrame.createButton;
import static frame.BaseFrame.createLabel;

public class BaseModal extends JDialog {
    BaseModal(String title, int WIDTH, int HEIGHT, JFrame frame) {
        super(frame, title);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(2);
    }
}



