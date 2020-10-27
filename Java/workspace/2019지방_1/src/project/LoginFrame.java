package project;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginFrame extends BaseFrame {

	JTextField tfId = createComponent(new JTextField(), 70, 10, 150, 20);
	JPasswordField tfPw = createComponent(new JPasswordField(), 70, 40, 150, 20);

	JPanel centerPanel = new JPanel();
	JPanel southPanel = createComponent(new JPanel(), 320, 40);

	public LoginFrame() {
		super(320, 190, "로그인");
		addComponents();
		setComponents();
	}

	@Override
	public void addComponents() {
		add(createLabel(new JLabel("STARBOX", 0), new Font("맑은고딕", 1, 24)), BorderLayout.NORTH);

		centerPanel.add(createComponent(new JLabel("ID:", 4), 0, 10, 60, 20));
		centerPanel.add(createComponent(new JLabel("PW:", 4), 0, 40, 60, 20));
		centerPanel.add(tfId);
		centerPanel.add(tfPw);
		centerPanel.add(createComponent(createButtonWithoutMargin("로그인", e -> clickSubmit()), 230, 0, 70, 70));

		southPanel.add(createButton("회원가입", e -> openFrame(new SignUpFrame())));
		southPanel.add(createButton("취소", e -> dispose()));

		add(centerPanel);
		add(southPanel, BorderLayout.SOUTH);
	}

	@Override
	public void setComponents() {
	};

	public static void main(String[] args) {
		new LoginFrame().setVisible(true);
	}

	public void clickSubmit() {
		if (tfId.getText().isEmpty() || tfPw.getText().isEmpty()) {
			errorMessage("빈칸이 존재합니다.");
			return;
		}

		try (var pst = connection.prepareStatement("select * from user where u_id = ? and u_pw = ?")) {
			pst.setObject(1, tfId.getText());
			pst.setObject(2, tfPw.getText());

			var rs = pst.executeQuery();
			if (rs.next()) {
				userNo = rs.getInt(1);
				userName = rs.getString(4);
				userGrade = rs.getString(6);
				userPoint = rs.getInt(7);
				
				openFrame(new MainFrame());
			} else {
				errorMessage("회원정보가 틀립니다. 다시입력해주세요.");
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
