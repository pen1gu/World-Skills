package frame;

import static frame.BaseFrame.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

public class SignUpPage extends BasePage {

	JButton btnEntrepreneur = createButtonWithoutMargin("사업자 회원가입", this::clickSignUp);
	JButton btnCommon = createButtonWithoutMargin("일반 회원가입", this::clickSignUp);

	public SignUpPage() {
		super(800, 400);

		setLayout(new BorderLayout());
		JPanel selectPanel = new JPanel(null);
		selectPanel.add(createComponent(btnEntrepreneur, 275, 140, 110, 110));
		selectPanel.add(createComponent(btnCommon, 4395, 140, 110, 110));

		add(selectPanel);

		setButtonSetting(btnCommon, Color.black);
		setButtonSetting(btnEntrepreneur, Color.black);

		selectPanel.setBackground(Color.white);
		setBackground(Color.white);
	}

	public void clickSignUp(ActionEvent event) {
		JButton button = (JButton) event.getSource();
		if (button.getText().contentEquals("사업자 회원가입")) {

		} else {

		}
	}

	public JPanel entrepreneurPanel() {
		JPanel panel = new JPanel(new FlowLayout());
		panel.setPreferredSize(new Dimension(270, 270));

		panel.add(createComponent(new JLabel("아이디", JLabel.LEFT), 40, 20));
		panel.add(createComponent(new JTextField(), 220, 30));
		panel.add(createComponent(new JLabel("비밀번호", JLabel.LEFT), 40, 20));
		panel.add(createComponent(new JTextField(), 220, 30));
		panel.add(createComponent(new JLabel("상호명", JLabel.LEFT), 40, 20));
		panel.add(createComponent(new JTextField(), 220, 30));
		panel.add(createComponent(new JLabel("주소", JLabel.LEFT), 40, 20));
		panel.add(createComponent(new JTextField(), 220, 30));
		panel.add(createComponent(createButton("회원가입", actionListener), width, height))
		
		
		return panel;
	}

	class EntrepreneurPanel {

		public void clickSignUp() {

		}
	}

}
