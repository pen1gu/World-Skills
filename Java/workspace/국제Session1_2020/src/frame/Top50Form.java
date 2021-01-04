package frame;

import java.awt.Color;
import java.awt.FlowLayout;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;

import db.ConnectionManager;

public class Top50Form extends BaseFrame {

	JPanel innerScrollPanel = createComponent(new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0)), 750, 450);
	ConnectionManager CM;

	public Top50Form(ConnectionManager CM) {
		super(800, 500, "실시간 Top50");
		this.CM = CM;

		JScrollPane scrollPane = createComponent(new JScrollPane(innerScrollPanel), 750, 450);

		try (PreparedStatement pst = CM.connection.prepareStatement(
				"select title, singer, genre,sum(amount) as sum from music as m inner join buy as b on b.music_no = m.`No` group by title order by sum desc, title limit 50;")) {
			ResultSet resultSet = pst.executeQuery();

			int cnt = 1;
			while (resultSet.next()) {
				innerScrollPanel.add(contentsPanel(cnt++, resultSet.getString(1), resultSet.getNString(2),
						resultSet.getString(3), resultSet.getInt(4)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		add(scrollPane);
	}

	public JPanel contentsPanel(int no, String title, String singer, String genre, int amount) {
		JPanel panel = createComponent(new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0)), 750, 90);
		JLabel[] labels = { new JLabel(no + "", 0),
				new JLabel(getImage("./Datafiles/이미지 지급자료/앨범 사진/" + title + ".jpg", 40, 50)), new JLabel(title, 0),
				new JLabel(singer, 0), new JLabel(genre, 0), new JLabel(amount + "", 0) };

		int[] elementsSize = { 80, 90, 180, 180, 110, 100 };
		for (int i = 0; i < labels.length; i++) {
			panel.add(createComponent(labels[i], elementsSize[i], 90));
			labels[i].setBorder(new LineBorder(Color.LIGHT_GRAY));
		}
		panel.setBorder(new LineBorder(Color.LIGHT_GRAY));
		return panel;
	}

	public static void main(String[] args) {
		new Top50Form(new ConnectionManager()).setVisible(true);
	}
}
