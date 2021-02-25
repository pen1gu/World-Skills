package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class TicketListFrame extends BaseFrame {

	int kindNo = 0;
	String currentTime;
	String[] menuArr;

	public TicketListFrame(int productCount, int kindNo, String currentTime, String[] menuArr) {
		super(300, 600, "식권");
		this.kindNo = kindNo;
		this.currentTime = currentTime;
		this.menuArr = menuArr;

		JPanel contentsPanel = createComponent(new JPanel(), 280, 600);
		JScrollPane pane = createComponent(new JScrollPane(), 300, 600);

		for (int i = 0; i < productCount; i++) {
			pane.add(new ProductPanel(i));
		}
	}

	class ProductPanel extends JPanel {
		public ProductPanel(int index) {
			setPreferredSize(new Dimension(300, 290));
			setLayout(new FlowLayout(FlowLayout.LEFT));

			LocalDate date = LocalDate.now();
			LocalDateTime dateTime = LocalDateTime.now();
			JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
			JPanel southPanel = new JPanel();

			add(new JLabel(date.format(DateTimeFormatter.ofPattern("yyyyMMdd")).toString() + " "
					+ dateTime.format(DateTimeFormatter.ofPattern("HHmmss")) + userNo + "-" + kindNo),
					BorderLayout.NORTH);

			try (PreparedStatement pst = connection
					.prepareStatement("select * from product where orderDate = ? and memberNo = ?")) {
				pst.setObject(1, currentTime);
				pst.setObject(2, userNo);

				ResultSet rs = pst.executeQuery();
				if (rs.next()) {
					centerPanel.add(createComponent(createLabel(new JLabel("식권"), new Font("굴림", 1, 26)), 200, 40));
					centerPanel.add(createComponent(createLabel(new JLabel(String.format("%,d원", rs.getObject(6))),
							new Font("굴림", Font.BOLD, 12)), 200, 50));

					southPanel.add(new JLabel("메뉴: " + menuArr[index]));
					southPanel.add(createComponent(new JLabel(index + "/" + kindNo), 100, 20));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}
}
