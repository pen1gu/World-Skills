package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.sql.ResultSet;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;

import model.Connector;

public class MainForm extends BaseFrame {
	
	JPanel centerPanel = new JPanel();
	public MainForm() {
		super(800, 700, "메인");
		add(createLabel(new JLabel("결혼시작 예약프로그램", 0), new Font("맑은고딕", 1, 20)), BorderLayout.NORTH);

		JPanel westPanel = createComponent(new JPanel(new FlowLayout()), 80, 0);

		westPanel.add(createComponent(createButton("", e -> openFrame(new HallSearchForm())), 80, 25));
		westPanel.add(createComponent(createButton("", e -> openFrame(new ReservationCheckForm())), 80, 25));
		westPanel.add(createComponent(createButton("", e -> openFrame(new PopluarityHallForm())), 80, 25));
		westPanel.add(createComponent(createButton("", e -> openFrame(new ManagementForm())), 80, 25));
		westPanel.add(createComponent(createButton("", e -> System.exit(WindowConstants.EXIT_ON_CLOSE)), 80, 25));

		centerPanel.setBorder(new LineBorder(Color.red));

		add(westPanel, BorderLayout.WEST);
		add(centerPanel);
	}

	public static void main(String[] args) {
		new MainForm().setVisible(true);
	}
	
	public void insertTopHallList() {
		try (ResultSet rs = Connector.getSqlResult("select * from")){
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
