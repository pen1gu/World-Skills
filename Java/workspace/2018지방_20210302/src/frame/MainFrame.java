package frame;

import java.awt.GridLayout;

import javax.swing.JPanel;

public class MainFrame extends BaseFrame {
	public MainFrame() {
		super(300, 250, "메인");
		JPanel contentsPanel = new JPanel(new GridLayout(0,1));

		contentsPanel.add(createButton("사원등록", e -> openFrame(new InsertUserForm())));
		contentsPanel.add(createButton("사용자", e -> openFrame(new UserForm())));
		contentsPanel.add(createButton("관리자", e -> openFrame(new ManagementForm())));
		contentsPanel.add(createButton("종료", e -> dispose()));

		contentsPanel.setBorder(topDownMargin);

		add(contentsPanel);
	}

	public static void main(String[] args) {
		new MainFrame().setVisible(true);
	}
}
