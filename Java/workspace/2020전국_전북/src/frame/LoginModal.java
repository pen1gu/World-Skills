package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import model.Connector;
import model.UserData;

public class LoginModal extends BaseModal {

	UserData ud;
	JCheckBox idSaveCheckBox = new JCheckBox();
	JTextField tfId;
	JPasswordField tfPw;

	public LoginModal(UserData ud) {
		super(290, 260, "LOGIN");
		this.ud = ud;

		JPanel[] panels = new JPanel[3];
		JPanel centerPanel = new JPanel(null);
		JPanel southPanel = createComponent(new JPanel(new GridLayout(0, 1)), 400, 70);

		add(createComponent(createLabel(new JLabel("<html><p color = 'orange'>SMART air</p></html>", 0),
				new Font("맑은고딕", Font.BOLD, 35)), 300, 50), BorderLayout.NORTH);

		centerPanel.add(createComponent(new JLabel("아이디"), 0, 25, 50, 20));
		centerPanel.add(createComponent(new JLabel("비밀번호"), 0, 70, 50, 20));
		centerPanel.add(tfId = createComponent(new JTextField(), 50, 15, 120, 40));
		centerPanel.add(tfPw = createComponent(new JPasswordField(), 50, 60, 120, 40));
		centerPanel.add(createComponent(createButton("로그인", e -> hasLogin()), 180, 15, 90, 85));

		for (int i = 0; i < 3; i++) {
			southPanel.add(panels[i] = new JPanel(new FlowLayout()));
		}

		panels[0].add(idSaveCheckBox);
		panels[0].add(new JLabel("아이디 저장"));

		JLabel lbSearchInfo;
		JLabel lbSignUp;
		panels[1].add(lbSearchInfo = new JLabel("<html><u>아이디/비밀번호 찾기</u></html>"));
		panels[2].add(lbSignUp = new JLabel("<html><u>회원가입</u></html>"));

		add(centerPanel);
		add(southPanel, BorderLayout.SOUTH);
	}

	public void hasLogin() {
		if (tfId.getText().isEmpty() || tfPw.getText().isEmpty()) {
			return;
		}
		try (ResultSet rs = Connector.getSqlResult("select * from passenger where id = ? and pw = ?", tfId.getText(),
				tfPw.getText())) {

			if (rs.next()) {
				ud.setUserNo(1);
				ud.setUserName(rs.getString(4));
				ud.setUserLoginStatus(true);
				informationMessage("로그인이 완료되었습니다.");
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		errorMessage("아이디 또는 비밀번호가 잘못 입력되었습니다.");
	}
}
