package frame;

import model.UserData;

public class GUI {
	public static void main(String[] args) {
		UserData ud = new UserData();
		ud.setUserLoginStatus(false);
		new MainFrame(ud).setVisible(true);
	}
}
