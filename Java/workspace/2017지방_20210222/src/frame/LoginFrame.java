package frame;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class LoginFrame extends BaseFrame {

	JRadioButton[] radioButtons = new JRadioButton[2];
	JTextField tfNumber = new JTextField();
	JPasswordField tfPassword = new JPasswordField();

	public LoginFrame() {
		super(300, 170, "회원 로그인");
		JPanel northPanel = new JPanel();
		JPanel centerPanel = new JPanel();
		JPanel southPanel = createComponent(new JPanel(null), 300, 70);

		northPanel.add(radioButtons[0] = new JRadioButton("회원 로그인"));
		northPanel.add(radioButtons[1] = new JRadioButton("비회원 로그인"));

		centerPanel.add(createLabel(new JLabel("로그인", JLabel.CENTER), new Font("굴림", Font.BOLD, 18)));

		southPanel.add(createComponent(new JLabel("회원 번호 :"), 10, 0, 80, 20));
		southPanel.add(createComponent(new JLabel("비밀 번호 :"), 10, 30, 80, 20));
		southPanel.add(createComponent(tfNumber, 90, 0, 110, 20));
		southPanel.add(createComponent(tfPassword, 90, 30, 110, 20));
		southPanel.add(createComponent(createButton("확인", e -> clickLogin()), 210, 0, 60, 25));
		southPanel.add(createComponent(createButton("취소", e -> dispose()), 210, 25, 60, 25));

		radioButtons[0].setSelected(true);

		radioButtons[0].addActionListener(this::clickRadio);
		radioButtons[1].addActionListener(this::clickRadio);

		add(northPanel, BorderLayout.NORTH);
		add(centerPanel);
		add(southPanel, BorderLayout.SOUTH);
	}

	public void clickRadio(ActionEvent e) {
		JRadioButton radio = (JRadioButton) e.getSource();
		if ("비회원 로그인".equals(radio.getText())) {
			tfNumber.setText("비회원");
			tfPassword.setText("비회원");

			tfNumber.setEditable(false);
			tfPassword.setEditable(false);

			radioButtons[0].setSelected(false);
		} else {
			tfNumber.setText("");
			tfPassword.setText("");

			tfNumber.setEditable(true);
			tfPassword.setEditable(true);
			radioButtons[1].setSelected(false);
		}
	}

	public void clickLogin() {
		String id = tfNumber.getText();
		String pw = tfPassword.getText();

		if (id.isEmpty() || pw.isEmpty()) {
			return;
		}

		if ("비회원".equals(id) && "비회원".equals(pw) && radioButtons[1].isSelected() == true) {
			userId = "비회원";
			userName = "";
			informationMessage("비회원으로 로그인 합니다.");
			openFrame(new MainFrame());
			return;
		}

		try (PreparedStatement pst = connection
				.prepareStatement("select cID, cPW from tbl_customer where cid = ? and cpw = ?")) {
			pst.setObject(1, id);
			pst.setObject(2, pw);

			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				userId = rs.getString(1);
				userName = rs.getString(2);
				informationMessage("로그인 완료");
				openFrame(new MainFrame());
			} else {
				informationMessage("없는 회원 입니다. 다시 확인하여 주십시오.");
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new LoginFrame().setVisible(true);
	}
}
