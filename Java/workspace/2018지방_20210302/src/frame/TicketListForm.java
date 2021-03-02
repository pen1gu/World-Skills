package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import model.MenuInfo;

public class TicketListForm extends BaseFrame {

	ArrayList<MenuInfo> infoList;

	JPanel contentsPanel = createComponent(new JPanel(new FlowLayout()), 380, 600);

	public TicketListForm(ArrayList<MenuInfo> infoList) {
		super(400, 600, "식권");

		this.infoList = infoList;

		JScrollPane pane = createComponent(new JScrollPane(contentsPanel), 400, 600);

		int row = 0;
		for (int i = 0; i < infoList.size(); i++) {
			Color color = null;
			if (i % 2 == 0) {
				color = Color.cyan;
			} else {
				color = Color.magenta;
			}
			for (int j = 0; j < infoList.get(i).count; j++) {
				SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmss");
				String now = format.format(new Date()) + "-" + userNo + "-" + infoList.get(i).kind;

				contentsPanel.add(new TicketPanel(now, color, infoList.get(i).price / infoList.size(),
						infoList.get(i).name, j + 1, infoList.size()));
				row++;
			}
		}

		contentsPanel.setPreferredSize(new Dimension(380, 285 * row));
		add(pane);
	}

	private class TicketPanel extends JPanel {
		public TicketPanel(String date, Color color, int price, String menu, int index, int maxIndex) {
			setPreferredSize(new Dimension(380, 280));
			setLayout(new BorderLayout());

			setBackground(color);

			JPanel northPanel = createComponent(new JPanel(new FlowLayout(FlowLayout.LEFT)), 400, 40);
			JPanel centerPanel = new JPanel(new FlowLayout());
			JPanel southPanel = createComponent(new JPanel(new FlowLayout(FlowLayout.LEFT)), 400, 30);

			northPanel.setOpaque(false);
			centerPanel.setOpaque(false);
			southPanel.setOpaque(false);

			northPanel.add(new JLabel(date));

			centerPanel.add(createLabel(new JLabel("식권"), new Font("굴림", 0, 24)));
			centerPanel.add(createLabel(new JLabel(String.format("%,d원", price)), new Font("굴림", 1, 24)));

			southPanel.add(createComponent(new JLabel("메뉴: " + menu, 2), 200, 20));
			southPanel.add(createComponent(new JLabel(String.format("%d/%d", index, maxIndex, 4)), 80, 20));

			add(northPanel, BorderLayout.NORTH);
			add(centerPanel);
			add(southPanel, BorderLayout.SOUTH);
		}
	}
}
