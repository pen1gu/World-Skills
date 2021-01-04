package exam;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class Exam01 extends JFrame {

	JLabel lbResult = new JLabel("", 0);
	JTextField txtMileUnit = new JTextField(15);

	int mileUnit = 1;
	double mile = 1.609344;

	public Exam01() {
		setTitle("");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setSize(400, 250);
		setLayout(new FlowLayout());

		JButton button = new JButton("변환");
		JPanel centerPanel = new JPanel(new BorderLayout());

		centerPanel.add(lbResult, BorderLayout.SOUTH);
		centerPanel.setPreferredSize(new Dimension(380, 150));

		lbResult.setFont(new Font("", Font.PLAIN, 20));

		button.addActionListener(e -> changeMileUnit());

		add(new JLabel("거리를 마일 단위로 입력하세요"));
		add(txtMileUnit);
		add(button);
		add(centerPanel);
		changeMileUnit();
	}

	public void changeMileUnit() {
		try {
			mileUnit = Integer.parseInt(txtMileUnit.getText());
		} catch (Exception e) {
			mileUnit = 1;
		}
		lbResult.setText(mileUnit + "mile은 " + (mile * mileUnit) + "km 입니다.");
	}

	public static void main(String[] args) {
		new Exam01().setVisible(true);
	}
}
