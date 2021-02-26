package frame;

import java.awt.FlowLayout;

import javax.swing.JPanel;

public class ManagementForm extends BaseFrame{
	public ManagementForm() {
		super(500, 300, "");
		JPanel northPanel = new JPanel(new FlowLayout());
		northPanel.add(createButton("메뉴등록", e->openFrame(new MenuAddForm())));
		northPanel.add(createButton("메뉴관리", e));
		northPanel.add(createButton("결제조회", e));
		northPanel.add(createButton("메뉴별주분현황", e));
		northPanel.add(createButton("종료", e->openFrame(new MainForm())));
	}
}
