package frame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class ReservationCheckForm extends BaseFrame {

	JLabel lbImg = new JLabel();

	public ReservationCheckForm() {
		super(600, 800, "예약");

		JPanel centerPanel = createComponent(new JPanel(), 600, 800);
		JPanel centerInner = createComponent(new JPanel(), 500, 700);
		JPanel centerInner_south = new JPanel();

		centerPanel.add(createComponent(createButton("<", this::changeHall), 50, 80));
		centerPanel.add(centerInner);
		centerPanel.add(createComponent(createButton(">", this::changeHall), 50, 80));

		centerInner.add(lbImg, BorderLayout.NORTH);

		add(centerPanel);
		add(createButton("예약하기", e -> haveBooking()), BorderLayout.SOUTH);
		
		
	}

	public void changeHall(ActionEvent e) {
		
	}

	public void haveBooking() {

	}

	public static void main(String[] args) {
		new ReservationCheckForm().setVisible(true);
	}
}
