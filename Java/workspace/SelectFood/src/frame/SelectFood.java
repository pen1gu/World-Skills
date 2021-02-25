package frame;

import javax.swing.JTextField;

public class SelectFood extends BaseFrame{
	
	JTextField tfAddMenu = new JTextField();
	
	public SelectFood() {
		super(600, 400, "랜덤 메뉴 판");
		setLayout(null);
		
	}
	
	public static void main(String[] args) {
		String[] arr = { "한식", "일식", "중식", "분식" };

		int hcnt = 0, icnt = 0, jcnt = 0, bcnt = 0;
		for (int i = 0; i < 100; i++) {
			String menu = arr[(int) (Math.random() * 4)];
			if ("한식".equals(menu)) {
				hcnt++;
			} else if ("일식".equals(menu)) {
				icnt++;
			} else if ("중식".equals(menu)) {
				continue;
//				jcnt++;
			} else if ("분식".equals(menu)) {
				bcnt++;
			}
		}

		System.out.println("한식: " + hcnt + " 일식: " + icnt + " 중식: " + jcnt + " 분식: " + bcnt);
	}
}
