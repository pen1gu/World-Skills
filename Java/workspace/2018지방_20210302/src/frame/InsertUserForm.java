package frame;

import java.awt.GridLayout;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class InsertUserForm extends BaseFrame {

	JTextField tfUserNo = new JTextField();
	JTextField tfUserName = new JTextField();
	JPasswordField tfPassword = new JPasswordField();
	JPasswordField tfPasswordCheck = new JPasswordField();

	public InsertUserForm() {
		super(350, 250, "사원등록");

		JPanel contentsPanel = new JPanel(new GridLayout(0, 2));

		contentsPanel.add(new JLabel("사원번호:"));
		contentsPanel.add(tfUserNo);
		contentsPanel.add(new JLabel("사 원 명:"));
		contentsPanel.add(tfUserName);
		contentsPanel.add(new JLabel("패스워드:"));
		contentsPanel.add(tfPassword);
		contentsPanel.add(new JLabel("패스워드 재입력:"));
		contentsPanel.add(tfPasswordCheck);
		contentsPanel.setBorder(topDownMargin);

		contentsPanel.add(createButton("등록", e -> clickSubmit()));
		contentsPanel.add(createButton("닫기", e -> openFrame(new MainFrame())));

		add(contentsPanel);

		tfUserNo.setEnabled(false);

		try {
			ResultSet rs = statement.executeQuery("select memberNo from member order by memberNo desc limit 1;");
			rs.next();

			tfUserNo.setText("" + (rs.getInt(1) + 1));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void clickSubmit() {
		String name = tfUserName.getText();
		String pw = tfPassword.getText();
		String pwCheck = tfPasswordCheck.getText();

		if (name.isEmpty() || pw.isEmpty() || pwCheck.isEmpty()) {
			errorMsg("항목 누락");
			return;
		}

		if (!pw.equals(pwCheck)) {
			errorMsg("패스워드 확인 요망");
			return;
		}

		try (PreparedStatement pst = connection.prepareStatement("insert into member values(?,?,?)")) {
			pst.setObject(1, tfUserNo.getText());
			pst.setObject(2, name);
			pst.setObject(3, pw);

			pst.execute();

			infoMsg("사원이 등록되었습니다");
			openFrame(new MainFrame());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
