package frame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class SignUpForm extends BaseFrame {

	JTextField[] fields = new JTextField[5];
	JPasswordField[] pwFields = new JPasswordField[2];

	boolean checkDuplicate = false;

	public SignUpForm() {
		super(350, 400, "회원가입");
		setLayout(new BorderLayout());

		JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel eastPanel = createComponent(new JPanel(null), 90, 0);
		JPanel southPanel = createComponent(new JPanel(new FlowLayout(FlowLayout.RIGHT)), 350, 40);

		int count = 0, pwCount = 0, fieldCount = 0;
		for (String element : new String[] { "이름", "아이디", "비밀번호", "비밀번호 체크", "전화번호", "생년월일", "주소" }) { // 중복확인 만들어야함
			centerPanel.add(createComponent(new JLabel(element), 80, 40));
			if (count == 2 || count == 3) {
				centerPanel.add(createComponent(pwFields[pwCount] = new JPasswordField(), 140, 20));
				pwCount++;
			} else {
				centerPanel.add(createComponent(fields[fieldCount] = new JTextField(), 140, 20));
				fieldCount++;
			}
			count++;
		}

		eastPanel.add(createComponent(createButtonWithoutMargin("중복확인", e -> duplicateCheck()), 0, 60, 80, 25));

		southPanel.add(createButton("회원가입", e -> clickSubmit()));
		southPanel.add(createButton("취소", e -> openFrame(new LoginForm())));

		fields[1].addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				checkDuplicate = false;
			};
		});

		add(centerPanel);
		add(eastPanel, BorderLayout.EAST);
		add(southPanel, BorderLayout.SOUTH);
	}

	public void duplicateCheck() {
		String id = fields[1].getText();

		if (id.isEmpty()) {
			errorMessage("아이디를 입력하세요", "Error");
			return;
		}

		try (var pst = connection.prepareStatement("select u_id from user where u_id = ?")) {
			pst.setObject(1, id);

			var rs = pst.executeQuery();
			if (rs.next()) {
				errorMessage("아이디가 중복되었습니다.", "Error");
				return;
			}

			informationMessage("사용가능한 아이디입니다.", "Information");
			checkDuplicate = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void clickSubmit() {
		for (int i = 0; i < fields.length; i++) {
			if (fields[i].getText().isEmpty()) {
				errorMessage("빈칸이 있습니다.", "Error");
				return;
			}
		}

		if (checkDuplicate == false) {
			errorMessage("중복확인을 해주세요.", "Error");
			return;
		}

		if (!pwFields[1].getText().equals(pwFields[0].getText())) {
			errorMessage("비밀번호를 확인해주세요.", "Error");
			return;
		}

		try (var pst = connection.prepareStatement("insert into user values(0,?,?,?,?,?,?)")) {
			pst.setObject(1, fields[1].getText());
			pst.setObject(2, pwFields[1].getText());
			pst.setObject(3, fields[4].getText());
			pst.setObject(4, fields[0].getText());
			pst.setObject(5, fields[2].getText());
			pst.setObject(6, fields[3].getText());

			pst.execute();

			informationMessage("회원가입이 완료되었습니다.", "Information");
			openFrame(new LoginForm());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
