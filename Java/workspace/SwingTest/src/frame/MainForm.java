package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import component.Legend;
import db.ConnectionManager;

public class MainForm extends BaseFrame {
	ConnectionManager CM;

	public MainForm(ConnectionManager CM) {
		super(500, 600, "메인");
		this.CM = CM;
		JPanel northPanel = new JPanel(new BorderLayout());
		JPanel centerPanel = new JPanel();
		JPanel north_south = new JPanel(new FlowLayout());

		northPanel.add(createLabel(new JLabel("Music Market", 0), new Font("Gothic", 1, 24), Color.red),
				BorderLayout.NORTH);
		northPanel.add(north_south);
		north_south.add(createButton("실시간 Top50", e -> openFrame(new Top5Form())));
		north_south.add(createButton("상세정보", e -> openFrame(new MoreInfoForm())));
		north_south.add(createButton("이용권 구매", e -> openFrame(new PassBuyForm())));
		north_south.add(createButton("마이페이지", e -> openFrame(new MyPageForm())));
		north_south.add(createButton("로그인", e -> openFrame(new LoginForm(CM))));

		JPanel chartLegend = createComponent(new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20)), 450, 200);
		chartLegend.setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.black), "실시간 차트",
				TitledBorder.CENTER, TitledBorder.TOP, new Font("맑은고딕", 1, 18)));

		chartLegend.add(new JLabel(getImage("./Datafiles/이미지 지급자료/앨범 사진/#with_you.jpg", 110, 110)));
		chartLegend.add(new JLabel(getImage("./Datafiles/이미지 지급자료/앨범 사진/#with_you.jpg", 110, 110)));
		chartLegend.add(new JLabel(getImage("./Datafiles/이미지 지급자료/앨범 사진/#with_you.jpg", 110, 110)));

		JPanel musicLegend = createComponent(new JPanel(), 215, 215);
		musicLegend.add(new JLabel(getImage("./Datafiles/이미지 지급자료/앨범 사진/#with_you.jpg", 150, 150)));

		centerPanel.add(chartLegend);
		centerPanel.add(musicLegend);
		centerPanel.add(new Legend("분석", 215, 215));

		add(northPanel, BorderLayout.NORTH);
		add(centerPanel);
	}

	public static void main(String[] args) {
		new MainForm(new ConnectionManager()).setVisible(true);
	}

	public void createTitleBorder() {
		
	}
}
