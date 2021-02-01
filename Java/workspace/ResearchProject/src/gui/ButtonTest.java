package gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;

public class ButtonTest extends JFrame {
	public ButtonTest() {
		setLayout(new FlowLayout());
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		Container container = getContentPane();

		container.add(new JLabel("이런"));
		container.add(new JLabel("이런"));
		container.add(new JLabel("이런"));
		container.add(new JLabel("이런"));

		JButton testBtn = new JButton("안녕하세요");
		testBtn.setPreferredSize(new Dimension(100, 100));
		testBtn.setBorder(new LineBorder(Color.black));
		testBtn.setForeground(Color.black);
		testBtn.setBackground(Color.white);

		container.add(testBtn);

		pack();
		revalidate();
		
		//버튼 ui 테스트 및 pack, revalidate 사용법
	}

	public static void main(String[] args) {
		new ButtonTest();
	}
}
