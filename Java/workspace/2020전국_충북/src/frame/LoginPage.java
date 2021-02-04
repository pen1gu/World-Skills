package frame;

import static frame.BaseFrame.*;

import java.awt.Color;

import javax.swing.JButton;

public class LoginPage extends BasePage {
	public LoginPage() {
		super(800, 500);
		setBackground(Color.white);

		add(new JButton("안녕	"));
	}

}
