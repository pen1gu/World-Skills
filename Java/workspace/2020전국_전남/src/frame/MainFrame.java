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
			}
		}));
		northPanel.add(createButton("보고서", e -> new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			}
		}));

		northPanel.add(createButton("그래프", e -> changePage(new ProjectForm())));

		// border 만들기
		centerPanel.add(new LoginPanel());
		centerPanel.setBorder(new LineBorder(Color.DARK_GRAY));

		add(northPanel, BorderLayout.NORTH);
		add(centerPanel, BorderLayout.CENTER);
	}

	public static void main(String[] args) {
		new MainFrame().setVisible(true);
	}
}
