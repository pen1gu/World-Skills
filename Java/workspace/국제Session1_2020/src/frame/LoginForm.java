package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import db.ConnectionManager;
import model.Member;

public class LoginForm extends BaseFrame {

	JTextField txtId = createComponent(new JTextField(), 150, 10, 150, 28);
	JPasswordField txtPwf = createComponent(new JPasswordField(), 150, 50, 150, 28);
	TimeCheck timeCheck;
	ConnectionManager CM;

	public LoginForm(ConnectionManager CM) {
		super(400, 200, "로그인");
		this.CM = CM;
		timeCheck = new TimeCheck();

		JPanel centerPanel = new JPanel(null);
		JPanel southPanel = new JPanel(new FlowLayout());

		add(createLabel(new JLabel("Music Market", 0), new Font("Gothic", 1, 24), Color.red), BorderLayout.NORTH);

		centerPanel.add(createComponent(new JLabel("아이디:"), 10, 10, 150, 20));
		centerPanel.add(createComponent(new JLabel("비밀번호:"), 10, 50, 150, 20));

		centerPanel.add(txtId);
		centerPanel.add(txtPwf);
		centerPanel.add(createComponent(createButtonWithOutMargin("로그인", e -> clickLogin()), 310, 10, 65, 75));

		southPanel.add(createComponent(createButton("회원가입", e -> openFrame(new SignUpForm())), 170, 30));
		southPanel.add(createComponent(createButton("아아디 / 비밀번호 찾기", e -> openFrame(new SignUpForm())), 170, 30));

		add(centerPanel);
		add(southPanel, BorderLayout.SOUTH);
	}

	public void clickLogin() {
		String id = txtId.getText();
		String pw = txtPwf.getText();

		Member member = CM.getSQLResult(Member.class, "select * from `Member` where id = ? and pw = ?", id, pw); // 클래스
		if (member != null) {
			if (member.pw.equals(pw)) {
				informationMessage(member.name + "님 환영합니다");
				openFrame(new MainForm(CM));
				return;
			}
		}

		errorMessage("아이디 또는 비밀번호가 잘못되었습니다");
	}

	public static void main(String[] args) {
		new LoginForm(new ConnectionManager()).setVisible(true);
	}
}
