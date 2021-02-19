package frame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.sql.ResultSet;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginForm extends BaseFrame {

	JTextField tfId = createComponent(new JTextField(), 110, 20, 120, 20);
	JPasswordField tfPw = createComponent(new JPasswordField(), 110, 60, 120, 20);

	public LoginForm() {
		super(350, 180, "로그인");
		setLayout(new BorderLayout());

		JPanel centerPanel = new JPanel(null);
		JPanel southPanel = createComponent(new JPanel(new FlowLayout(FlowLayout.RIGHT)), 350, 25);

		add(createLabel(new JLabel("기능마켓", JLabel.CENTER), new Font("맑은 고딕", Font.BOLD, 25)), BorderLayout.NORTH);

		centerPanel.add(createComponent(new JLabel("아이디", JLabel.LEFT), 55, 20, 60, 20));
		centerPanel.add(createComponent(new JLabel("비밀번호", JLabel.LEFT), 55, 60, 60, 20));
		centerPanel.add(tfId);
		centerPanel.add(tfPw);

		centerPanel.add(createComponent(createButton("로그인", e -> clickLogin()), 245, 0, 75, 75));

		// TODO : 테스트함. 바꿔야함.
		JLabel lbSignUp = new JLabel("회원가입");
		southPanel.add(lbSignUp);

		lbSignUp.addMouseListener(new MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent e) {
				openFrame(new SignUpForm());
			};
		});

		add(centerPanel);
		add(southPanel, BorderLayout.SOUTH);
	}

	public void clickLogin() {
		if (tfId.getText().isEmpty() || tfPw.getText().isEmpty()) {
			errorMessage("빈칸이 있습니다.", "Error");
			return;
		}

		if ("admin".equals(tfId.getText()) && "1234".equals(tfPw.getText())) {
			openFrame(new AdminForm());
			return;
		}

		try (var pst = connection.prepareStatement("select * from user where u_id = ? and u_pw = ?")) {
			pst.setObject(1, tfId.getText());
			pst.setObject(2, tfPw.getText());

			ResultSet rs = pst.executeQuery();

			if (rs.next()) {
				userName = rs.getString(5);
				userNo = rs.getInt(1);
				informationMessage(userName + "님 환영합니다.", "Information");
				openFrame(new ProductForm());
			} else {
				errorMessage("아이디나 비밀번호가 틀렸습니다.", "Error");
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new LoginForm().setVisible(true);
	}
}
