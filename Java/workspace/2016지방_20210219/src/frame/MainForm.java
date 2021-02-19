package frame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainForm extends BaseFrame {
	public MainForm() {
		super(500, 370, "보험계약 관리화면");

		JPanel northPanel = createComponent(new JPanel(), 600, 40);
		JPanel centerPanel = new JPanel(new FlowLayout());

		northPanel.add(createButton("고객 등록", e -> new InsertCustomerForm("등록").setVisible(true)));
		northPanel.add(createButton("고객조회", e -> openFrame(new CustomerLookUpForm())));
		northPanel.add(createButton("계약 관리", e -> openFrame(new ManagementForm())));
		northPanel.add(createButton("종료", e -> dispose())); // TODO: 종료 부분 확인

		centerPanel.add(new JLabel(getImage(400, 250, "./지급자료/img.jpg")));
		add(northPanel, BorderLayout.NORTH);
		add(centerPanel);
	}
}
