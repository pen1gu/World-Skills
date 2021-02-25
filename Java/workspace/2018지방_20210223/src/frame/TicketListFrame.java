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

	public TicketListFrame(int productCount, int kindNo, String currentTime) {
		super(300, 600, "식권");

		this.kindNo = kindNo;
		this.currentTime = currentTime;
		JPanel contentsPanel = createComponent(new JPanel(), 280, 600);
		JScrollPane pane = createComponent(new JScrollPane(), 300, 600);

		for (int i = 0; i < productCount; i++) {
			pane.add(new ProductPanel());
		}
	}

	class ProductPanel extends JPanel {
		public ProductPanel() {
			setPreferredSize(new Dimension(300, 290));
			setLayout(new FlowLayout(FlowLayout.LEFT));

			LocalDate date = LocalDate.now();
			LocalDateTime dateTime = LocalDateTime.now();

			add(new JLabel(date.format(DateTimeFormatter.ofPattern("yyyyMMdd")).toString() + " "
					+ dateTime.format(DateTimeFormatter.ofPattern("HHmmss")) + userNo + "-" + kindNo),
					BorderLayout.NORTH);

			JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

			centerPanel.add(createComponent(createLabel(new JLabel("식권"), new Font("굴림", 1, 26)), 200, 40));
			try (PreparedStatement pst = connection.prepareStatement("select * from ")) {
				pst.setObject(1, currentTime);

				ResultSet rs = pst.executeQuery();
				if (rs.next()) {
					
			centerPanel.add(createComponent( createLabel(new JLabel(), ), width, height))
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			JPanel southPanel = new JPanel();
		}
	}
}
