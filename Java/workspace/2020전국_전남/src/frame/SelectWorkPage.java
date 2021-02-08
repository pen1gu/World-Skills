package frame;

import static frame.BaseFrame.createButton;
import static frame.BaseFrame.setDefaultColor;

import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SelectWorkPage extends JPanel {
	public SelectWorkPage() {
		setLayout(new GridLayout(0, 1, 10, 10));
		add(new JLabel("  작업을 선택하세요"));
		add(createButton("프로젝트 생성", e -> test()));
		add(createButton("프로젝트 열기", e -> test()));
		add(createButton("프로젝트 직원 추가", e -> test()));
		add(createButton("로그인 화면", e -> test()));

		setDefaultColor(SelectWorkPage.this);
		setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

	}

	public void test() {

	}
}
