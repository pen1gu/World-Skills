package exam;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class Exam02 extends JFrame {

	JLabel lbNorth = new JLabel("아래의 버튼 중에서 하나를 클릭하시오");
	JLabel lbSouth = new JLabel("");

	public Exam02() {
		setTitle("");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setSize(400, 300);

		Container container = getContentPane();
		JPanel centerPanel = new JPanel(new GridLayout(1, 0));

		for (String gbb : new String[] { "가위", "바위", "보" }) {
			JButton button;
			centerPanel.add(button = new JButton(gbb));
			button.addActionListener(this::clickButton);
		}
		container.setLayout(new BorderLayout());
		container.setBackground(Color.yellow);
		
		container.add(lbNorth, BorderLayout.NORTH);
		container.add(centerPanel);
		container.add(lbSouth, BorderLayout.SOUTH);
	}

	public void clickButton(ActionEvent e) {
		JButton button = (JButton) e.getSource();
		String btnTxt = button.getText();
		String[] txtArr = { "가위", "바위", "보" };
		String resultTxt = txtArr[(int) (Math.random() * 3)];

		if (btnTxt.equals("가위")) {
			if (resultTxt.equals("보")) {
				changeLabel(0);
			} else if (resultTxt.equals("가위")) {
				changeLabel(1);
			} else {
				changeLabel(2);
			}
		} else if (btnTxt.equals("바위")) {
			if (resultTxt.equals("가위")) {
				changeLabel(0);
			} else if (resultTxt.equals("바위")) {
				changeLabel(1);
			} else {
				changeLabel(2);
			}
		} else if (btnTxt.contentEquals("보")) {
			if (resultTxt.equals("주먹")) {
				changeLabel(0);
			} else if (resultTxt.equals("보")) {
				changeLabel(1);
			} else {
				changeLabel(2);
			}
		}
	}

	public void changeLabel(int check) {
		String txt = "";
		if (check == 0) {
			txt = "축하합니다~ 이겼습니다";
		} else if (check == 1) {
			txt = "한번 더 ~비겼습니다";
		} else if (check == 2) {
			txt = "다음 기회에~ 졌습니다";
		}
		lbSouth.setText(txt);
	}

	public static void main(String[] args) {
		new Exam02().setVisible(true);
	}
}
