package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;


import model.UserData;

public class MainFrame extends BaseFrame {

	UserData ud;
	JLabel lbUserLoginStaus = new JLabel("", JLabel.RIGHT);
	JLabel lbSession = new JLabel("login");

	JPanel southPanel = new JPanel(new BorderLayout());

	public MainFrame(UserData ud) {
		super(800, 400, "MAIN");

		this.ud = ud;

		JPanel northPanel = new JPanel(new BorderLayout());
		JPanel northInnerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

		JPanel centerPanel = new JPanel(new GridLayout(1, 0));
		
		changeSession();

		northPanel.add(createLabel(new JLabel("<html><p color = 'orange'>SMART air</p></html>", 0),
				new Font("맑은고딕", Font.BOLD, 24)), BorderLayout.NORTH);
		northInnerPanel.add(lbUserLoginStaus);
		northInnerPanel.add(lbSession);

		northPanel.add(northInnerPanel, BorderLayout.SOUTH);
		
		insertOpenComponentEvent(lbSession, new LoginModal(ud));
		int i = 1;
		for (JPanel panel : new JPanel[] { new FlightPanel(), new MyReservationPanel(), new SchedulePanel(),
				new MyInfoPanel() }) {
			JLabel label;
			insertOpenComponentEvent(label = new JLabel(getImage(200, 250, "./datafiles/img/button" + i + ".png")), panel);
			centerPanel.add(label);
			label.setBorder(new LineBorder(Color.black));
			i++;
		}

		southPanel.add(new JLabel("by YHJ",4));

		add(northPanel, BorderLayout.NORTH);
		add(centerPanel);
		add(southPanel, BorderLayout.SOUTH);
	}

	public void changeSession() {
		String txt = "";
		if (ud.isUserLoginStatus() == false) {
			lbSession.setText("<html><u>Login</u><html>");
		} else {
			lbSession.setText("<html><u>Logout</u><html>");
			txt = ud.getUserName() + "님 환영합니다.";
		}
		changeUserLoginStatus(txt);
		collapse();
	}
	
	public void insertOpenComponentEvent(JLabel label, Object object) {
		label.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				super.mousePressed(e);
				if (object instanceof JPanel) {
					southPanel.add((JPanel)object);
				}else if (object instanceof JDialog) {
					openDialog((JDialog)object);
				}
			}
		});
	}

	public void collapse() {
		MainFrame.this.setSize(800, 400);
		southPanel.setPreferredSize(new Dimension(800, 20));
	}

	public void changeUserLoginStatus(String plainTxt) {
		lbUserLoginStaus.setText(plainTxt);
	}
}
