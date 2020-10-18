package setting;

import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class AdminMenuFrame extends BaseFrame {

	public AdminMenuFrame() {
		super(270, 180, "°ü¸®ÀÚ ¸Þ´º");
		
		var jp = new JPanel(new GridLayout(3,1));
		
		jp.setBorder(BorderFactory.createEmptyBorder(5,0,5,0));
		
		jp.add(createButton("메뉴 등록", w -> openFrame(new MenuAddFrame())));
		jp.add(createButton("메뉴 관리", w -> openFrame(new MenuEditFrame())));
		jp.add(createButton("로그아웃", w -> openFrame(new LoginFrame())));
		add(jp);
	}
	
	public static void main(String[] args) {
		new AdminMenuFrame().setVisible(true);
	}

}
