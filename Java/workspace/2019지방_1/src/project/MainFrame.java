package project;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MainFrame extends BaseFrame {

	JPanel northPanel = new JPanel();
	JPanel westPanel = createComponent(new JPanel(), 55, 0);
	JPanel centerPanel = new JPanel();
//	JPanel detailPanel = createComponent(new JPanel(), 300, );
	
	public MainFrame() {
		super(800, 600, "STARBOX");
		addComponents(); 
		setComponents();
	}

	public static void main(String[] args) {
		new MainFrame().setVisible(true);
	}

	@Override
	public void addComponents() {
		northPanel.add(createLabel(new JLabel("회원명 : " + userName + "/ 회원등급 : " + userGrade + "/ 총 누적 포인트 : " + userPoint),
				new Font("맑은고딕", 1, 20)), BorderLayout.NORTH);
	}

	@Override
	public void setComponents() {

	}
}
