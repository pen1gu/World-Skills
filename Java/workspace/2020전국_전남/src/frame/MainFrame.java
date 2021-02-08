package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;

public class MainFrame extends BaseFrame {

	JTextField tfId = createComponent(new JTextField(), 100, 10, 160, 30);
	JPasswordField tfPw = createComponent(new JPasswordField(), 100, 50, 160, 30);

	public static JPanel centerPanel = new JPanel(new GridBagLayout());

	public MainFrame() {
		super(800, 600, "ProjectLibre");
		setLayout(new BorderLayout());
		JPanel northPanel = createComponent(new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0)), 800, 30);

		northPanel.add(createButton("파일", e -> new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("파일");
			}
		}));

//		button.setBorder(BorderFactory.createEtchedBorder());
		northPanel.add(createButton("일정관리", e -> new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
			}
		}));
		northPanel.add(createButton("보고서", e -> new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
			}
		}));

		northPanel.add(createButton("그래프", e -> new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
			}
		}));

		JPanel centerInnerPanel = createComponent(new JPanel(null), 280, 130); // 로그인 패널로 만들어 주어야한다.
 //border 만들기
		centerPanel.add(centerInnerPanel);
		centerPanel.setBorder(new LineBorder(Color.DARK_GRAY));
		centerInnerPanel.setBorder(new LineBorder(Color.black));

		centerInnerPanel.add(createComponent(new JLabel("ID:", JLabel.LEFT), 30, 10, 80, 30));
		centerInnerPanel.add(createComponent(new JLabel("PW:", JLabel.LEFT), 30, 50, 80, 30));

		centerInnerPanel.add(tfId);
		centerInnerPanel.add(tfPw);

		centerInnerPanel.add(createComponent(createButton("로그인", e -> clickLogin()), 30, 90, 230, 30));

		add(northPanel, BorderLayout.NORTH);
		add(centerPanel, BorderLayout.CENTER);
	}

	public void clickLogin() {
		changePage(createComponent(new SelectWorkPage(), 300, 200));
	}

	public static void main(String[] args) {
		new MainFrame().setVisible(true);
	}
}
