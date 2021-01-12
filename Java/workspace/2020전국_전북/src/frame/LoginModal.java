package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import model.UserData;

public class LoginModal extends BaseModal {

	UserData ud;

	public LoginModal(UserData ud) {
		super(300, 280, "LOGIN");

		this.ud = ud;
		add(createComponent(createLabel(new JLabel("<html><p color = 'orange'>SMART air</p></html>", 0),
				new Font("맑은고딕", Font.BOLD, 35)), 300, 50), BorderLayout.NORTH);

		JPanel centerPanel = new JPanel(null);

		centerPanel.add(createComponent(new JLabel("아이디"), 0, 25, 50, 20));
		centerPanel.add(createComponent(new JLabel("비밀번호"), 0, 70, 50, 20));
		centerPanel.add(createComponent(new JTextField(), 50, 15, 120, 40));
		centerPanel.add(createComponent(new JTextField(), 50, 60, 120, 40));

		centerPanel.add(createComponent(createButton("로그인", e -> hasLogin()), 185, 15, 100, 100));
		JPanel[] panels = new JPanel[3];
		JPanel southPanel = createComponent(new JPanel(new GridLayout(0, 1)), 400, 50);
		for (int i = 0; i < 3; i++) {
			southPanel.add(panels[i] = new JPanel(new FlowLayout()));
			panels[i].setBorder(new LineBorder(Color.blue));
		}

		add(centerPanel);
		add(southPanel, BorderLayout.SOUTH);
	}

	public void hasLogin() {

	}
}
