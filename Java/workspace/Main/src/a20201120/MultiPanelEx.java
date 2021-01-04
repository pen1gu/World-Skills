package a20201120;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MultiPanelEx extends JFrame {

	JPanel centerPanel = new JPanel(null);
	
	JButton btnOpen = new JButton("열기");
	JButton btnClose = new JButton("닫기");
	JButton btnExit = new JButton("나가기");

	public MultiPanelEx() {
		setSize(400, 400);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		JPanel northPanel = new JPanel(new FlowLayout());
		JPanel southPanel = new JPanel(new FlowLayout());

		northPanel.setPreferredSize(new Dimension(400, 40));
		southPanel.setPreferredSize(new Dimension(400, 40));

		northPanel.setBackground(Color.gray);
		southPanel.setBackground(Color.yellow);

		northPanel.add(btnOpen);
		northPanel.add(btnClose);
		northPanel.add(btnExit);

		btnExit.addActionListener(e -> dispose());
		btnOpen.addActionListener(e -> openPanel());
		btnClose.addActionListener(e -> closePanel());

		Random random = new Random();
		for (int i = 0; i < 20; i++) {
			JLabel label;
			centerPanel.add(label = new JLabel("*"));
			label.setForeground(Color.magenta);
			label.setLocation(random.nextInt(300), random.nextInt(300));
			label.setSize(10, 10);
		}

		southPanel.add(new JButton("Word Input"));
		southPanel.add(new JTextField(20));

		add(northPanel, BorderLayout.NORTH);
		add(centerPanel);
		add(southPanel, BorderLayout.SOUTH);
		
		
		int a = 1 | 2;
		System.out.println(a);
	}

	public void openPanel() {
	}

	public void closePanel() {

	}

	public static void main(String[] args) {
		new MultiPanelEx().setVisible(true);
	}
}
