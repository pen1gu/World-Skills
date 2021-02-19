package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.sql.ResultSet;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;

import model.ConnectionManager;
import model.Connector;

public class MainForm extends BaseFrame {

	JPanel centerPanel = new JPanel(new GridLayout(0, 1));
	Object returnValue = "";

	public MainForm() {
		super(800, 700, "메인");
		add(createComponent(createLabel(new JLabel("결혼시작 예약프로그램", 0), new Font("맑은고딕", 1, 20)), 800, 40),
				BorderLayout.NORTH);

		JPanel westPanel = createComponent(new JPanel(new FlowLayout()), 80, 0);

		westPanel.add(
				createComponent(createButtonWithoutMargin("웨딩홀 검색", e -> openFrame(new HallSearchForm())), 80, 25));
		westPanel.add(createComponent(createButtonWithoutMargin("예약확인", e -> openInputDialog()), 80, 25));
		westPanel.add(
				createComponent(createButtonWithoutMargin("인기 웨딩홀", e -> openFrame(new PopluarityHallForm())), 80, 25));
		westPanel.add(createComponent(createButtonWithoutMargin("관리", e -> openFrame(new ManagementForm())), 80, 25));
		westPanel.add(createComponent(createButtonWithoutMargin("종료", e -> System.exit(WindowConstants.EXIT_ON_CLOSE)),
				80, 25));

		add(westPanel, BorderLayout.WEST);
		add(centerPanel);

		insertTopHallList();
	}

	public static void main(String[] args) {
		new MainForm().setVisible(true);
	}

	public void insertTopHallList() {
		try (ResultSet rs = statement.executeQuery(
				"select count(w.wNo) as c,wName, wpeople,wprice, wadd from weddinghall as w inner join reservation as r on w.wNo = r.wNo group by w.wNo order by c desc limit 3;")) {
			int cnt = 0;
			while (rs.next()) {
				centerPanel.add(new HallInfoPanel(cnt, rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4)));
				cnt++;
			}

			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void openInputDialog() {
		returnValue = JOptionPane.showInputDialog("예약번호를 확인하세요.");
		ConnectionManager connectionManager = new ConnectionManager();
		connectionManager.connect();

		try {
			ResultSet rs = connectionManager.getSqlResults("select * from reservation where rno = ?;", returnValue);
			if (rs.next()) {
				openFrame(new ReservationCheckForm());
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		connectionManager.freeConnection();
	}

	class HallInfoPanel extends JPanel {
		public HallInfoPanel(int rank, int no, String name, int price, String addr) {
			setPreferredSize(new Dimension(700, 200));
			setBorder(new LineBorder(Color.black));
			setLayout(new GridLayout(1, 0));

			add(new JLabel(getImage("./datafiles/호텔이미지/" + name + "/" + name + "1.jpg", 350, 200)));

			JPanel contentsPanel = new JPanel(new GridLayout(0, 1));

			contentsPanel.add(new JLabel(String.format("예약 %d위 (%d건)", rank, no), JLabel.LEFT));
			contentsPanel.add(new JLabel(String.format("이름: %s", name), JLabel.LEFT));
			contentsPanel.add(new JLabel(String.format("가격: %d", price), JLabel.LEFT));
			contentsPanel.add(new JLabel(String.format("주소: %s", addr), JLabel.LEFT));

			add(contentsPanel);
		}
	}
}