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
import javax.swing.border.LineBorder;

public class TicketListFrame extends BaseFrame {

	int kindNo = 0;
	String[] menuArr;
	int[] mealNoArr;

	public TicketListFrame(int[] productCount, int kindNo, String[] menuArr, int[] mealNoArr, int[] menuPriceArr) {
		super(300, 500, "식권");
		this.kindNo = kindNo;
		this.menuArr = menuArr;
		this.mealNoArr = mealNoArr;

		setLayout(new BorderLayout());

		JPanel contentsPanel = createComponent(new JPanel(new FlowLayout()), 260, 600);
		JScrollPane pane = createComponent(new JScrollPane(contentsPanel), 300, 600);

		int amount = 0, colorCount = 0;
		for (int i = 0; i < productCount.length; i++) {
			for (int j = 0; j < productCount[i]; j++) {
				Color color = null;
				if (colorCount % 2 == 0) {
					color = Color.cyan;
				} else {
					color = Color.pink;
				}

				contentsPanel.add(new ProductPanel(i, menuPriceArr[i], color, productCount[i]));
				colorCount++;
			}
			amount += productCount[i];
		}

		contentsPanel.setPreferredSize(new Dimension(300, 160 * amount));
		contentsPanel.revalidate();

		add(pane);
	}

	int count = 1;

	class ProductPanel extends JPanel {
		public ProductPanel(int index, int price, Color color, int mealAmount) {
			setPreferredSize(new Dimension(280, 150));
			setLayout(new FlowLayout(FlowLayout.LEFT));

			LocalDate date = LocalDate.now();
			LocalDateTime dateTime = LocalDateTime.now();
			setBackground(color);
			setBorder(new LineBorder(Color.black));
			add(new JLabel(date.format(DateTimeFormatter.ofPattern("yyyyMMdd")).toString() + ""
					+ dateTime.format(DateTimeFormatter.ofPattern("HHmmss")) + "-" + userNo + "-" + kindNo),
					BorderLayout.NORTH);

			add(createComponent(createLabel(new JLabel("식권", 0), new Font("굴림", 1, 26)), 250, 40));
			add(createComponent(createLabel(new JLabel(String.format("%,d원", price/mealAmount, 0),0), new Font("굴림", Font.BOLD, 32)),
					250, 50));
			if (count == mealAmount+1)
				count = 1;
			add(new JLabel("메뉴: " + menuArr[index]));
			add(createComponent(new JLabel(count + "/" + mealAmount), 100, 20));
			count++;
		}
	}
}
