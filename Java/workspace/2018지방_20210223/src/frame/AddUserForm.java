package frame;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class AddUserForm extends BaseFrame {

	JTextField tfUserId = new JTextField();
	JTextField tfUserName = new JTextField();
	JPasswordField tfPassword = new JPasswordField();
	JPasswordField tfPasswordConfirm = new JPasswordField();

	public AddUserForm() {
		super(400, 290, "사원등록");
		setLayout(new BorderLayout());

		JPanel centerPanel = new JPanel(new GridLayout(0, 2));

		centerPanel.add(new JLabel("사원번호:"));
		centerPanel.add(tfUserId);
		centerPanel.add(new JLabel("사 원 명:"));
		centerPanel.add(tfUserName);
		centerPanel.add(new JLabel("패스워드:"));
		centerPanel.add(tfPassword);
		centerPanel.add(new JLabel("패스워드 재입력:"));
		centerPanel.add(tfPasswordConfirm);
		centerPanel.add(createButton("등록", e -> clickSubmit()));
		centerPanel.add(createButton("닫기", e -> openFrame(new MainForm())));

		centerPanel.setBorder(emptyBorder);

		tfUserId.setEditable(false);

		try {
			ResultSet rs = statement.executeQuery("select memberNo from member order by memberNo desc limit 1;");
			rs.next();

			tfUserId.setText(Integer.toString(rs.getInt(1) + 1));
		} catch (Exception e) {
			e.printStackTrace();
		}

		add(centerPanel);
	}

	private void clickSubmit() {
		String id = tfUserId.getText();
		String name = tfUserName.getText();
		String pw = tfPassword.getText();
		String pwConfirm = tfPasswordConfirm.getText();

		if (id.isEmpty() || name.isEmpty() || pw.isEmpty() || pwConfirm.isEmpty()) {
			errorMsg("항목 누락");
			return;
		}

		if (!pw.equals(pwConfirm)) {
			errorMsg("패스워드 확인 요망");
			return;
		}

		try (PreparedStatement pst = connection.prepareStatement("insert into member values(0,?,?)")) {
			pst.setObject(1, name);
			pst.setObject(2, pw);

			pst.execute();
			informationMsg("사원이 등록되었습니다.");
			openFrame(new MainForm());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
