package frame;

import static frame.BaseFrame.*;

import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import model.ConnectionManager;
import model.UserData;

public class LoginPage extends BasePage {

	JTextField tfId = createComponent(new JTextField(), 330, 150, 120, 30);
	JPasswordField tfPw = createComponent(new JPasswordField(), 330, 190, 120, 30);
	JButton btnLogin = createComponent(createButtonWithoutMargin("로그인", e -> clickLogin()), 460, 150, 50, 70);
	JButton btnSignUp = createComponent(createButton("회원가입", e -> changePage(new SignUpPage())), 290,
			230, 220, 30);

	UserData userData;

	public LoginPage(UserData userData) {
		super(800, 500);
		this.userData = userData;

		setLayout(null);
		setBackground(Color.white);

		add(createComponent(new JLabel("ID :"), 290, 150, 30, 30));
		add(createComponent(new JLabel("PW :"), 290, 190, 30, 30));
		add(tfId);
		add(tfPw);

		add(btnLogin);
		add(btnSignUp);

		btnLogin.setBackground(Color.white);
		btnSignUp.setBackground(Color.white);
		btnLogin.setBorder(new LineBorder(Color.black));
		btnSignUp.setBorder(new LineBorder(Color.black));
	}

	public void clickLogin() {
		if (tfId.getText().isEmpty() || tfPw.getText().isEmpty()) {
			errorMessage("ID/PW를 모두 입력해주세요.");
			return;
		}

		ConnectionManager connectionManager = new ConnectionManager();
		connectionManager.connect();
		try {
			ResultSet rs = connectionManager.getSqlResults("select * from user where id = ? and pw = ?", tfId.getText(),
					tfPw.getText());
			if (rs.next()) {
				informationMessage(rs.getString(4) + "님 환영합니다.");
				userData.setUserName(rs.getString(4));
				userData.setUserNo(rs.getInt(1));
				closePage();
			} else {
				errorMessage("아이디 또는 패스워드가 일치하지 않습니다.");
				return;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		connectionManager.close();
	}
}
