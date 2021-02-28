package frame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ManagementForm extends BaseFrame {
	public ManagementForm() {
		super(500, 350, "관리");
		JPanel northPanel = createComponent(new JPanel(new FlowLayout()), 500, 35);
		JLabel centerImage = new JLabel(getImage(500, 250, "./지급자료/main.jpg"));

		northPanel.add(createButton("메뉴등록", e -> openFrame(new MenuAddForm("신규 메뉴 등록"))));
		northPanel.add(createButton("메뉴관리", e -> openFrame(new MenuMagamentForm())));
		northPanel.add(createButton("결제조회", e -> openFrame(new PayFormLookUpForm())));
		northPanel.add(createButton("메뉴별주분현황", e -> openFrame(new PayListForm())));
		northPanel.add(createButton("종료", e -> openFrame(new MainForm())));

		northPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

		add(northPanel, BorderLayout.NORTH);
		add(centerImage);
	}
}
