package frame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginForm extends BaseFrame {

	JTextField tfId = createComponent(new JTextField(), 65, 10, 120, 20);
	JPasswordField tfPw = createComponent(new JPasswordField(), 65, 40, 120, 20);

	public LoginForm() {
		super(300, 180, "로그인");

		add(createLabel(new JLabel("STARBOX", 0), new Font("Gothic", 1, 24)), BorderLayout.NORTH);

		JPanel centerPanel = new JPanel(null);

		centerPanel.add(createComponent(new JLabel("ID:", 4), 0, 10, 60, 20));
		centerPanel.add(createComponent(new JLabel("PW:", 4), 0, 40, 60, 20));

		centerPanel.add(tfId);
		centerPanel.add(tfPw);

		centerPanel.add(createComponent(createButton("로그인", e -> clickLogin()), 190, 0, 70, 70));

		JPanel southPanel = new JPanel(new FlowLayout());
		southPanel.add(createButton("회원가입", e -> openFrame(new SignUpForm())));
		southPanel.add(createButton("종료", e -> dispose()));

		add(centerPanel);
		add(southPanel, BorderLayout.SOUTH);
	}

	public static void main(String[] args) {
		new LoginForm().setVisible(true);
	}

	public void clickLogin() {
		String id = tfId.getText();
		String pw = tfPw.getText();

		if (id.isEmpty() || pw.isEmpty()) {
			errorMsg("빈칸이 존재합니다.");
			return;
		}

		if (id.equals("admin") && pw.equals("1234")) {
			openFrame(new AdminForm());
		}

		try (PreparedStatement pst = connection.prepareStatement("select * from user where u_id =? and u_pw = ?")) {
			pst.setObject(1, id);
			pst.setObject(2, pw);

			ResultSet rs = pst.executeQuery();

			if (rs.next()) {
				userNo = rs.getInt(1);
				userName = rs.getString(4);
				userPoint = rs.getInt(6);
				userGrade = rs.getString(7);

				openFrame(new MainForm());
			} else {
				errorMsg("회원정보가 틀립니다. 다시입력해주세요.");
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
