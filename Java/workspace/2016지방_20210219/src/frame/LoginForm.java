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

	JTextField tfId = createComponent(new JTextField(), 100, 0, 120, 20);
	JPasswordField tfPw = createComponent(new JPasswordField(), 100, 30, 120, 20);

	public LoginForm() {
		super(280, 170, "로그인");
		setLayout(new BorderLayout());

		add(createComponent(createLabel(new JLabel("관리자 로그인", JLabel.CENTER), new Font("굴림", Font.BOLD, 23)), 280, 40),
				BorderLayout.NORTH);

		JPanel centerPanel = new JPanel(null);
		centerPanel.add(createComponent(new JLabel("이름"), 30, 0, 60, 20));
		centerPanel.add(createComponent(new JLabel("비밀번호"), 30, 30, 60, 20));

		centerPanel.add(tfId);
		centerPanel.add(tfPw);

		JPanel southPanel = new JPanel(new FlowLayout());
		southPanel.add(createButton("확인", e -> clickLogin()));
		southPanel.add(createButton("종료", e -> dispose()));

		add(southPanel, BorderLayout.SOUTH);
		add(centerPanel);
	}

	public void clickLogin() {

		if (tfId.getText().isEmpty() || tfPw.getText().isEmpty()) {
			return;
		}

		try (PreparedStatement pst = connection
				.prepareStatement("select name, passwd from admin where name = ? and passwd = ?")) {
			pst.setObject(1, tfId.getText());
			pst.setObject(2, tfPw.getText());

			ResultSet rs = pst.executeQuery();

			if (rs.next()) {
				openFrame(new MainForm());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new LoginForm().setVisible(true);
	}
}
