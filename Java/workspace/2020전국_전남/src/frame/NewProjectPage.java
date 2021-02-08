package frame;

import java.awt.Dimension;

import javax.swing.JPanel;

public class NewProjectPage extends BaseFrame {
	public NewProjectPage() {
		super(600, 300, "새 프로젝트");
	}

	public static void main(String[] args) {
		new NewProjectPage().setVisible(true);
	}
}
