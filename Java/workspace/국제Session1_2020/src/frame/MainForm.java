package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import db.ConnectionManager;

public class MainForm extends BaseFrame {
	ConnectionManager CM;

	public MainForm(ConnectionManager CM) {
		super(500, 600, "메인");
		this.CM = CM;
		JPanel northPanel = new JPanel(new BorderLayout());
		JPanel centerPanel = new JPanel();
		JPanel north_south = new JPanel(new FlowLayout());

		JPanel chartLegend = createComponent(new JPanel(new BorderLayout()), 450, 200);
		JPanel musicLegend = createComponent(new JPanel(new BorderLayout()), 215, 215);
		JPanel analysisLegend = createComponent(new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 15)), 215, 215);

		ArrayList<String> chartList = new ArrayList<String>();
		JPanel chartInnerPanel = new JPanel(null);
		JPanel[] inner_chart = new JPanel[3];

		ArrayList<String> musicList = new ArrayList<String>();
		JPanel musicInnerPanel = new JPanel(null);
		JPanel[] inner_latestMusic = new JPanel[5];

		northPanel.add(createLabel(new JLabel("Music Market", 0), new Font("Gothic", 1, 24), Color.red),
				BorderLayout.NORTH);
		northPanel.add(north_south);
		north_south.add(createButton("실시간 Top50", e -> openFrame(new Top50Form(CM))));
		north_south.add(createButton("상세정보", e -> openFrame(new MoreInfoForm())));
		north_south.add(createButton("이용권 구매", e -> openFrame(new PassBuyForm())));
		north_south.add(createButton("마이페이지", e -> openFrame(new MyPageForm())));
		north_south.add(createButton("로그인", e -> openFrame(new LoginForm(CM))));

		// 실시간 차트 데이터
		try (PreparedStatement pst = CM.connection.prepareStatement(
				"select title, sum(amount) as sum from music as m inner join buy as b on b.music_no = m.`No` group by title order by sum desc, title limit 9;")) {
			ResultSet resultSet = pst.executeQuery();
			while (resultSet.next()) {
				chartList.add(resultSet.getString(1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (int i = 0; i < inner_chart.length; i++) {
			inner_chart[i] = createComponent(new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20)), 440 * i, 0, 440,
					175);
			chartInnerPanel.add(inner_chart[i]);
		}

		// 최신음악 데이터
		try (PreparedStatement pst = CM.connection
				.prepareStatement("select title from music order by date desc, title  limit 5;")) {
			ResultSet resultSet = pst.executeQuery();
			while (resultSet.next()) {
				musicList.add(resultSet.getString(1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (int i = 0; i < inner_latestMusic.length; i++) {
			inner_latestMusic[i] = createComponent(new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20)), 205 * i, 0,
					205, 205);
			musicInnerPanel.add(inner_latestMusic[i]);
		}

		addSlideContents(inner_chart, chartList, 3, 110);
		chartLegend.add(new JLabel("* * *", 0), BorderLayout.SOUTH);
		movePanel(inner_chart, 440, inner_chart.length);

		addSlideContents(inner_latestMusic, musicList, 1, 130);
		musicLegend.add(new JLabel("* * * * *", 0), BorderLayout.SOUTH);
		movePanel(inner_latestMusic, 205, inner_latestMusic.length);

		chartLegend.setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.black), "실시간 차트",
				TitledBorder.CENTER, TitledBorder.TOP, new Font("맑은고딕", 1, 18)));

		musicLegend.setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.black), "최신음악", TitledBorder.CENTER,
				TitledBorder.TOP, new Font("맑은고딕", 1, 18)));

		analysisLegend.setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.black), "분석",
				TitledBorder.CENTER, TitledBorder.TOP, new Font("맑은고딕", 1, 18)));

		for (String contents : new String[] { "장르별", "연령별", "성별  / 장르별" }) {
			analysisLegend.add(createComponent(
					createButton(contents, e -> JOptionPane.showMessageDialog(null, "session2")), 200, 35));
		}

		musicLegend.add(musicInnerPanel);
		chartLegend.add(chartInnerPanel);
		centerPanel.add(chartLegend);
		centerPanel.add(musicLegend);
		centerPanel.add(analysisLegend);

		add(northPanel, BorderLayout.NORTH);
		add(centerPanel);
	}

	public void movePanel(JPanel[] panels, int width, int contentsCount) {
		new Thread() {
			public void run() {
				while (true) {
					try {
						Thread.sleep(1000);
						for (int i = 0; i < width; i++) {
							for (int j = 0; j < contentsCount; j++) {
								panels[j].setLocation(panels[j].getLocation().x - 1, 0);
								if (panels[j].getLocation().x <= -width) {
									panels[j].setLocation(width * (contentsCount - 1), 0);
								}
							}
							Thread.sleep(2);
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			};
		}.start();
	}

	public <T> void addSlideContents(JPanel[] panels, ArrayList<T> list, int panelContentsCount, int size) {
		for (int i = 0; i < list.size(); i++) {
			JPanel panel = new JPanel(new BorderLayout());
			JLabel lbImage = new JLabel();
			JLabel lbCaption = new JLabel("", JLabel.CENTER);
			lbImage.setIcon(getImage("./Datafiles/이미지 지급자료/앨범 사진/" + list.get(i) + ".jpg", size, size));
			lbCaption.setText(list.get(i).toString());
			panel.add(lbImage);
			panel.add(lbCaption, BorderLayout.SOUTH);
			panels[i / panelContentsCount].add(panel);
		}
	}

	public static void main(String[] args) {
		new MainForm(new ConnectionManager()).setVisible(true);
	}
}
