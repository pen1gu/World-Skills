package frame;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import static frame.BaseFrame.*;

public class LoginPanel extends JPanel {

	JTextField tfId = createComponent(new JTextField(), 100, 10, 160, 30);
	JPasswordField tfPw = createComponent(new JPasswordField(), 100, 50, 160, 30);

	public LoginPanel() {
		setPreferredSize(new Dimension(280, 130));
		setLayout(null);
		setBorder(new LineBorder(Color.black));

		add(createComponent(new JLabel("ID:", JLabel.LEFT), 30, 10, 80, 30));
		add(createComponent(new JLabel("PW:", JLabel.LEFT), 30, 50, 80, 30));

		add(tfId);
		add(tfPw);

		add(createComponent(createButton("로그인", e -> clickLogin()), 30, 90, 230, 30));
	}

	public void clickLogin() {
		changePage(createComponent(new SelectWorkPage(), 300, 200));
	}
}
