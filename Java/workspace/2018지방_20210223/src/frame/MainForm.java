package frame;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class MainForm extends BaseFrame {
	public MainForm() {
		super(320, 240, "메인");
		setLayout(new BorderLayout());

		JPanel centerPanel = new JPanel(new GridLayout(0, 1));
		centerPanel.add(createButton("사원등록", e -> openFrame(new AddUserForm())));
		centerPanel.add(createButton("사용자", e -> openFrame(new TicketForm())));
		centerPanel.add(createButton("관리자", e -> openFrame(new ManagementForm())));
		centerPanel.add(createButton("종료", e -> dispose()));

		centerPanel.setBorder(emptyBorder);

		add(centerPanel);
	}

	public static void main(String[] args) {
		new MainForm().setVisible(true);
	}
}
