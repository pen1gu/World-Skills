package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import db.ConnectionManager;
import model.Member;

public class LoginForm extends BaseFrame {

	JTextField txtId = createComponent(new JTextField(), 150, 10, 150, 28);
	JPasswordField txtPwf = createComponent(new JPasswordField(), 150, 50, 150, 28);
	TimeCheck timeCheck;
	ConnectionManager CM;

	public LoginForm(ConnectionManager CM) {
		super(400, 200, "濡쒓렇�씤");
		this.CM = CM;
		timeCheck = new TimeCheck();

		JPanel centerPanel = new JPanel(null);
		
		
		JPanel southPanel = new JPanel(new FlowLayout());

		add(createLabel(new JLabel("Music Market", 0), new Font("Gothic", 1, 24), Color.red), BorderLayout.NORTH);

		centerPanel.add(createComponent(new JLabel("�븘�씠�뵒:"), 10, 10, 150, 20));
		centerPanel.add(createComponent(new JLabel("鍮꾨�踰덊샇:"), 10, 50, 150, 20));

		centerPanel.add(txtId);
		centerPanel.add(txtPwf);
		centerPanel.add(createComponent(createButtonWithOutMargin("濡쒓렇�씤", e -> clickLogin()), 310, 10, 65, 75));

		southPanel.add(createComponent(createButton("�쉶�썝媛��엯", e -> openFrame(new SignUpForm())), 170, 30));
		southPanel.add(createComponent(createButton("�븘�븘�뵒 / 鍮꾨�踰덊샇 李얘린", e -> openFrame(new SignUpForm())), 170, 30));

		add(centerPanel);
		add(southPanel, BorderLayout.SOUTH);
	}

	public void clickLogin() {
		String id = txtId.getText();
		String pw = txtPwf.getText();

		Member member = CM.getSQLResult(Member.class, "select * from `Member` where id = ? and pw = ?", id, pw); // �겢�옒�뒪
		if (member != null) {
			if (member.pw.equals(pw)) {
				informationMessage(member.name + "�떂 �솚�쁺�빀�땲�떎");
				openFrame(new MainForm(CM));
				return;	
			}
		}

		errorMessage("�븘�씠�뵒 �삉�뒗 鍮꾨�踰덊샇媛� �옒紐삳릺�뿀�뒿�땲�떎");
	}

	public static void main(String[] args) {
		new LoginForm(new ConnectionManager()).setVisible(true);
	}
}
