package frame;

import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;

import javax.swing.JLabel;

public class PublicBase {
	public void addPressEvent(JLabel label, ActionEvent event) {
		label.addMouseListener(new MouseAdapter() {
		});
	}
}
