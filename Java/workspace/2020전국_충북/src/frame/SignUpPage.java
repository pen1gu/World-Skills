package frame;

import static frame.BaseFrame.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SignUpPage extends BasePage {

	JButton btnEntrepreneur = createButtonWithoutMargin("사업자 회원가입", this::clickSignUp);
	JButton btnCommon = createButtonWithoutMargin("일반 회원가입", this::clickSignUp);

	public SignUpPage() {
		super(800, 400);

		setLayout(new BorderLayout());
		JPanel selectPanel = new JPanel(null);
		selectPanel.add(createComponent(btnEntrepreneur, 200, 150, 100, 100));
		selectPanel.add(createComponent(btnCommon, 310, 150, 100, 100));

		add(selectPanel);

		selectPanel.setBackground(Color.white);
		setBackground(Color.white);
	}

	public void clickSignUp(ActionEvent event) {
		JButton button = (JButton) event.getSource();
		if (button.getText().contentEquals("사업자 회원가입")) {

		} else {

		}
	}
}
