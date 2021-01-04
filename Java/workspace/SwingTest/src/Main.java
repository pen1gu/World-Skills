import db.ConnectionManager;
import frame.LoginForm;

public class Main {
	public static void main(String[] args) {
		new LoginForm(new ConnectionManager()).setVisible(true);
	}
}
