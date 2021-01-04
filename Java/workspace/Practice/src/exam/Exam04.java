package exam;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class Exam04 extends JFrame {

	JTextField txtInput = new JTextField(20);
	JTextField txtOutPut = new JTextField(20);
	List<Integer> numList = new ArrayList<Integer>();

	public Exam04() {
		setSize(500, 300);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setTitle("");

		Container container = getContentPane();
		JPanel northPanel = new JPanel();
		JPanel centerPanel = new JPanel(new GridLayout(4, 3));
		JPanel southPanel = new JPanel();

		container.setLayout(new BorderLayout());
		container.setBackground(Color.yellow);

		northPanel.add(new JLabel("숫자를 입력해주세요"));
		northPanel.add(txtInput);
		southPanel.add(new JLabel("결과입니다"));
		southPanel.add(txtOutPut);

		for (String operator : new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "CE", "계산", "+", "-",
				"*", "/" }) {
			JButton button;
			centerPanel.add(button = new JButton(operator));
			button.addActionListener(this::clickCalculate);
		}

		add(northPanel, BorderLayout.NORTH);
		add(centerPanel);
		add(southPanel, BorderLayout.SOUTH);
	}

	public void clickCalculate(ActionEvent e) {
		JButton button = (JButton) e.getSource();
		String btnText = button.getText();
		if (btnText.equals("+") || btnText.equals("-") || btnText.equals("*") || btnText.equals("/")) {
//			numList.add(btnText.substring());
		}
	}

	public static void main(String[] args) {
		new Exam04().setVisible(true);
	}
}
