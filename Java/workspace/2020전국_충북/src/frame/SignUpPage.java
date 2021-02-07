package frame;

import static frame.BaseFrame.createButton;
import static frame.BaseFrame.createButtonWithoutMargin;
import static frame.BaseFrame.createComponent;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SignUpPage extends BasePage {

	JButton btnEntrepreneur = createButtonWithoutMargin("사업자 회원가입", this::clickSignUp);
	JButton btnCommon = createButtonWithoutMargin("일반 회원가입", this::clickSignUp);

	JPanel mainPanel;

	public SignUpPage() {
		super(800, 400);

		setLayout(new BorderLayout());

		mainPanel = new SelectPanel();

		add(mainPanel);

		setBackground(Color.white);
	}

	@Override
	public void changePage(JPanel openPanel) {
		mainPanel = openPanel;
		mainPanel.revalidate();
		mainPanel.repaint();
	}

	public void clickSignUp(ActionEvent event) {
		JButton button = (JButton) event.getSource();
		if (button.getText().contentEquals("사업자 회원가입")) {
			changePage(new EntrepreneurPanel());
		} else {

		}
	}

	class SelectPanel extends JPanel {
		public SelectPanel() {
			setLayout(null);
			setPreferredSize(new Dimension(800, 400));
			setBackground(Color.white);

			setButtonSetting(btnCommon, Color.white, Color.black);
			setButtonSetting(btnEntrepreneur, Color.white, Color.black);

			add(btnEntrepreneur = createComponent(btnEntrepreneur, 275, 140, 110, 110));
			add(btnCommon = createComponent(btnCommon, 435, 140, 110, 110));
		}
	}

	class EntrepreneurPanel extends JPanel {
		public EntrepreneurPanel() {
			setPreferredSize(new Dimension(800, 400));
			setLayout(new FlowLayout());

			setBackground(Color.black);

			add(createComponent(new JLabel("아이디", JLabel.LEFT), 40, 30));
			add(createComponent(new JTextField(), 220, 30));
			add(createComponent(new JLabel("비밀번호", JLabel.LEFT), 40, 30));
			add(createComponent(new JTextField(), 220, 30));
			add(createComponent(new JLabel("상호명", JLabel.LEFT), 40, 30));
			add(createComponent(new JTextField(), 220, 30));
			add(createComponent(new JLabel("주소", JLabel.LEFT), 40, 30));
			add(createComponent(new JTextField(), 220, 30));
			add(createComponent(createButton("회원가입", e -> clickSignUp()), 130, 30));
			add(createComponent(createButton("취소", e -> changePage(new SelectPanel())), 130, 30));
		}

		public void clickSignUp() {
			//회원가입
		}
	}

	class CommonPanel extends JPanel {
		public CommonPanel() {
			add(new JLabel("일반 회원가입"));
			setBackground(Color.white);
		}
	}

}
