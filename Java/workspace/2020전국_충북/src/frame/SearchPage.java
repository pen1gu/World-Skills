package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;

import model.ConnectionManager;
import model.ProductFiles;
import static frame.BaseFrame.*;

public class SearchPage extends BasePage {

	JPanel searchContentsPanel = createComponent(new JPanel(), 760, 200);
	ProductFiles pf = new ProductFiles();

	JCheckBox[] checkBox = new JCheckBox[29];

	ConnectionManager cm = new ConnectionManager();

	JPanel southPanel = new JPanel();

	String[] categorys = new String[] { "AMD", "인텔", "AMD(소켓AM4)", "AMD(소켓TR4)", "인텔(소켓1151)", "인텔(소켓1151v2)",
			"인텔(소켓1200)", "인텔(소켓2066)", "마티스", "서밋 릿지", "스카레이크", "스카이레이크", "카비레이크", "커피레이크", "커피레이크-R", "케스케이드레이크",
			"코멧레이크-R", "코멧레이크S", "피나클 릿지", "피카소", "화이트헤븐", "10코어", "12코어", "14코어", "듀얼 코어", "옥타(8)코어", "옥타(8)코어",
			"쿼드(4)코어", "헥사(6)코어" };

	JPanel mainPanel = createComponent(new JPanel(), 760, 200);

	public SearchPage() {
		super(800, 400);

		setLayout(new BorderLayout());

		JScrollPane scrollPane = new JScrollPane(mainPanel);

		mainPanel.setBackground(Color.white);
		mainPanel.setBackground(Color.white);

		JPanel category_center = createComponent(new JPanel(new GridLayout(10, 4)), 670, 200);
		JPanel category_west = createComponent(new JPanel(null), 60, 200);

		category_center.setBackground(Color.white);
		category_west.setBackground(Color.white);

		category_west.add(createComponent(new JLabel("브랜드", JLabel.LEFT), 0, 10, 80, 20));
		category_west.add(createComponent(new JLabel("소켓 구분", JLabel.LEFT), 0, 40, 80, 20));
		category_west.add(createComponent(new JLabel("코드네임", JLabel.LEFT), 0, 90, 80, 20));
		category_west.add(createComponent(new JLabel("코어 수", JLabel.LEFT), 0, 160, 80, 20));

		JPanel categoryPanel = createComponent(new JPanel(), 750, 200);
		categoryPanel.setBackground(Color.white);
		categoryPanel.add(category_west, BorderLayout.WEST);
		categoryPanel.add(category_center, BorderLayout.CENTER);

		int count = 0;
		for (int i = 0; i < 36; i++) {
			if (i == 2 || i == 3 || i == 10 || i == 11 || i == 25 || i == 26 || i == 27) {
				JPanel panel;
				category_center.add(createComponent(panel = new JPanel(), 170, 20));
				panel.setBackground(Color.white);
			} else {
				category_center.add(checkBox[count] = createComponent(new JCheckBox(categorys[count]), 150, 20));
				checkBox[count].setBackground(Color.white);
				checkBox[count].addActionListener(e -> search());
				count++;
			}
		}

		mainPanel.add(categoryPanel);
		mainPanel.add(searchContentsPanel);
		add(scrollPane);
		search();
	}

	class ResultPanel extends JPanel {

		int no, price, count;
		String name, description, sellerName;
		byte[] image;

		public ResultPanel(int no, String name, String description, byte[] image, String sellerName, int count,
				int price, int rating) {
			this.no = no;
			this.price = price;
			this.count = count;
			this.name = name;
			this.sellerName = sellerName;
			this.description = description;
			this.image = image;
			setLayout(new FlowLayout(FlowLayout.LEFT));

			setPreferredSize(new Dimension(760, 150));
			setBackground(Color.white);
			setBorder(new LineBorder(Color.black));

			add(new JLabel(getImage(image, 80, 80)));
			add(createLabel(new JLabel(name), new Font("맑은 고딕", 1, 18)));
			add(new JLabel(description));
			add(new JLabel(price + ""));
			add(createButton("바로 구매", e -> buy()));
		}

		public void buy() {
//			changePage(new JPanel());
			// '화면 변경과 동시에 선택한 정보 보내주어야함
		}
	}

	public void search() {
		ArrayList<String> checkList = new ArrayList<String>();
		StringBuilder builder = new StringBuilder();

		for (int i = 0; i < checkBox.length; i++)
			if (!checkBox[i].isSelected())
				checkList.add(checkBox[i].getText());

		builder.append("select p.serial, p.name, p.description,image, sl.name, count, price, rating "
				+ "from product as p inner join image as i " + "on p.thumb = i.serial inner join stock as s "
				+ "on p.serial = s.product inner join seller as sl " + "on s.seller = sl.serial "
				+ "inner join review as r on r.product = p.serial");

		for (int i = 0; i < checkList.size(); i++) {
			if (i == 0) {
				builder.append(" where p.description REGEXP '" + checkList.get(i));
			} else if (i == checkList.size() - 1) {
				builder.append("';");
			} else {
				builder.append("|" + checkList.get(i));
			}
		}

		resizePanel(builder.toString());
	}

	public void resizePanel(String sql) {
		cm.connect();

		searchContentsPanel.removeAll();

		int count = 0;
		try {
			ResultSet rs = cm.select(sql);
			while (rs.next()) {
				searchContentsPanel.add(new ResultPanel(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getBytes(4),
						rs.getString(5), rs.getInt(6), rs.getInt(7), rs.getInt(8)));
				count++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		int row = count / 3;
		if (count % 3 != 0) {
			row++;
		}

		mainPanel.setPreferredSize(new Dimension(760, 120 * row + 250));
		searchContentsPanel.setPreferredSize(new Dimension(760, 120 * row));
		searchContentsPanel.revalidate();
		searchContentsPanel.repaint();
		mainPanel.revalidate();
		mainPanel.repaint();

		cm.close();
	}

}
