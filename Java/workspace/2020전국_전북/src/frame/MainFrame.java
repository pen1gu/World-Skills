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
		super(750, 400, "MAIN");

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

//		insertOpenComponentEvent(lbSession, );
		lbSession.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				super.mousePressed(e);
				if (ud.isUserLoginStatus() == false) {
//					openDialog((JDialog) object);
					new LoginModal(ud, MainFrame.this).setVisible(true);
					return;
				}
				
				ud.setUserLoginStatus(false);
				changeSession();
				southPanel.removeAll();
				southPanel.add(new JLabel("by YHJ", 4));
			}
		});
		
		int i = 1;
		for (JPanel panel : new JPanel[] { new FlightPanel(), new MyReservationPanel(), new SchedulePanel(),
				new MyInfoPanel() }) {
			JLabel label;
			insertOpenComponentEvent(label = new JLabel(getImage(200, 250, "./datafiles/img/button" + i + ".png")),
					panel);
			centerPanel.add(label);
			label.setBorder(new LineBorder(Color.black));
			i++;
		}

		southPanel.add(new JLabel("by YHJ", 4));

		add(northPanel, BorderLayout.NORTH);
		add(centerPanel);
		add(southPanel, BorderLayout.SOUTH);
	}

	public void changeSession() {
		String txt = "";
		if (ud.isUserLoginStatus() == false) {
			resetSession();
		} else {
			lbSession.setText("<html><u>Logout</u><html>");
			txt = ud.getUserName() + "님 환영합니다.";
		}
		changeUserLoginStatus(txt);
		restoreFrameSize();
	}

	public void insertOpenComponentEvent(JLabel label, Object object) {
		label.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				super.mousePressed(e);
				if (object instanceof JPanel) {
					southPanel.removeAll();
					southPanel.add((JPanel) object);
					resizeFrame();
				} else if (object instanceof JDialog) {
//					if (ud.isUserLoginStatus() == false) {
//						openDialog((JDialog) object);
//						return;
//					}
					
					ud.setUserLoginStatus(false);
					changeSession();
					southPanel.removeAll();
					southPanel.add(new JLabel("by YHJ", 4));
				}
			}
		});
	}

	public void resizeFrame() {
		MainFrame.this.setSize(750, 600);
		southPanel.setPreferredSize(new Dimension(750, 400));
		setLocationRelativeTo(null);
	}

	public void restoreFrameSize() {
		MainFrame.this.setSize(750, 400);
		southPanel.setPreferredSize(new Dimension(750, 20));
		setLocationRelativeTo(null);
	}


	public void resetSession() {
		lbSession.setText("<html><u>Login</u><html>");
		lbUserLoginStaus.setText("");
	}

	public void changeUserLoginStatus(String plainTxt) {
		lbUserLoginStaus.setText(plainTxt);
	}
}
