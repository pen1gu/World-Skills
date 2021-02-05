package frame;

import static frame.BaseFrame.*;

import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SignUpPage extends BasePage {

	JButton btnEntrepreneur = createButton("사업자 회원가입", this::clickSignUp);
	JButton btnCommon = createButton("일반 회원가입", this::clickSignUp);

	public SignUpPage() {
		super(800, 400);

		JPanel selectPanel = new JPanel(null);
		selectPanel.add(createComponent(btnEntrepreneur, 200, 150, 100, 100));
		selectPanel.add(createComponent(btnEntrepreneur, 310, 150, 100, 100));

//		add(createComponent(new JLabel(""), width, height))
	}

	public void clickSignUp(ActionEvent event) {
		JButton button = (JButton) event.getSource();
		if (button.getText().contentEquals("사업자 회원가입")) {

		} else {

		}
	}
}
