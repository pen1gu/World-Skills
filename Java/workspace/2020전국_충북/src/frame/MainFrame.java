package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseAdapter;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import model.UserData;

public class MainFrame extends BaseFrame {

	public static JPanel contentsPanel = new JPanel(new BorderLayout());
	public static boolean checkLogin;
	public static MainPage mainPage = new MainPage();

	JLabel lbMain = createComponent(new JLabel(getImage("./지급자료/images/1.png", 140, 40), JLabel.RIGHT), 0, 0, 140, 40);
	JLabel lbLogin = new JLabel("로그인/회원가입");
	JLabel lbMyPage = new JLabel("마이페이지");

	UserData userData = new UserData();

	public MainFrame() {
		super(800, 500, "메인");
		setLayout(new BorderLayout());
 
		JPanel northPanel = createComponent(new JPanel(null), 800, 40);
		northPanel.setBackground(Color.white);
		northPanel.add(lbMain);
		northPanel.add(createComponent(lbLogin, 610, 10, 100, 20));
		northPanel.add(createComponent(lbMyPage, 710, 10, 80, 20));

		contentsPanel.setBorder(new LineBorder(Color.DARK_GRAY));
		contentsPanel.add(mainPage);

		add(northPanel, BorderLayout.NORTH);
		add(contentsPanel);

		lbLogin.addMouseListener(new MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent e) {
				changePage(new LoginPage(userData));
			};
		});

		lbMain.addMouseListener(new MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent e) {
				changePage(mainPage);
			};
		});
	}

	public static void main(String[] args) {
		new MainFrame().setVisible(true);
	}
}
