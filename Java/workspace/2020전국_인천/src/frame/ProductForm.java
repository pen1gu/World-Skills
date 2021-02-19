package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.sql.ResultSet;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class ProductForm extends BaseFrame {

	JTextField tfName = new JTextField();
	JTextField tfMaxPrice = new JTextField();
	JTextField tfMinPrice = new JTextField();

	JPanel center_scroll = createComponent(new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0)), 560, 400);

	DefaultTableModel model = new DefaultTableModel(
			new String[] { "상품번호", "상품 카테고리", "상품 이름", "상품 가격", "상품 재고", "상품 설명" }, 0);
	JTable table = new JTable(model);

	String currentCategory = "채소";

	public ProductForm() {
		super(840, 700, "상품목록");

		table.getColumnModel().getColumn(5).setPreferredWidth(140);

		JPanel westPanel = createComponent(new JPanel(), 200, 600);

		JPanel west_north = createComponent(new JPanel(), 200, 170);
		JPanel west_north_north = new JPanel();
		JPanel west_north_center = createComponent(new JPanel(new FlowLayout()), 200, 160);

		JPanel west_center = createComponent(new JPanel(), 200, 500);
		JPanel west_center_north = createComponent(new JPanel(new FlowLayout(FlowLayout.LEFT)), 200, 430);
		JPanel west_center_center = new JPanel();

		JPanel centerPanel = new JPanel();
		JPanel center_center = new JPanel(new BorderLayout());
		JPanel center_south = createComponent(new JPanel(new BorderLayout()), 620, 140);

		westPanel.add(west_north, BorderLayout.NORTH);
		westPanel.add(west_center);

		west_north.add(west_north_north, BorderLayout.NORTH);
		west_north.add(west_north_center);

		west_center.add(west_center_north, BorderLayout.NORTH);
		west_center.add(west_center_center);

		centerPanel.add(center_center);
		centerPanel.add(center_south, BorderLayout.SOUTH);

		/////

		west_north_north.add(createComponent(
				createLabel(new JLabel("유저 : " + userName, JLabel.LEFT), new Font("굴림", Font.BOLD, 18)), 200, 30));
		west_north_center.add(createComponent(new JLabel("이름"), 60, 20));
		west_north_center.add(createComponent(tfName, 80, 20));
		west_north_center.add(createComponent(new JLabel("최대 가격"), 60, 20));
		west_north_center.add(createComponent(tfMaxPrice, 80, 20));
		west_north_center.add(createComponent(new JLabel("최저 가격"), 60, 20));
		west_north_center.add(createComponent(tfMinPrice, 80, 20));
		west_north_center.add(createComponent(createButton("검색", e -> searchProduct()), 140, 35));

		////

		west_center_north.add(createLabel(new JLabel("카테고리"), new Font("굴림", 1, 18)));
		String[] arr = new String[] { "채소", "과일", "정육", "해산물", "가공식품", "유제품", "생활용품", "주방용품" };
		JLabel[] label = new JLabel[arr.length];
		for (int i = 0; i < arr.length; i++) {
			int cnt = i;
			west_center_north.add(label[i] = createComponent(new JLabel(arr[i], JLabel.LEFT), 190, 45));

			label[i].addMouseListener(new MouseAdapter() {
				public void mouseEntered(java.awt.event.MouseEvent e) {
					label[cnt].setForeground(Color.blue);
				};

				public void mouseExited(java.awt.event.MouseEvent e) {
					if (label[cnt].getForeground() != Color.red) {
						label[cnt].setForeground(Color.black);
					}
				};

				public void mouseClicked(java.awt.event.MouseEvent e) {
					currentCategory = label[cnt].getText();
					for (int i = 0; i < arr.length; i++) {
						if (currentCategory.equals(arr[i])) {
							label[i].setForeground(Color.red);
						} else {
							label[i].setForeground(Color.black);
						}
					}
				};
			});
		}

		west_center_center.add(createButton("인기상품", e -> openFrame(new TopProductForm())));
		west_center_center.add(createButton("구매목록", e -> openFrame(new BuyListForm())));

		/////

		JScrollPane productPane = createComponent(new JScrollPane(center_scroll), 620, 445);
		JScrollPane listPane = createComponent(new JScrollPane(table), 600, 120);

		center_center.setBorder(BorderFactory.createEmptyBorder(60, 0, 0, 0));
		center_center.add(productPane);
		center_south.add(listPane);

		///////
		add(westPanel, BorderLayout.WEST);
		add(centerPanel);
		searchProduct();
	}

	public void searchProduct() {

		int max_price = 1000000, min_price = 0;
		if (!"".equals(tfMaxPrice.getText())) {
			try {
				max_price = Integer.parseInt(tfMaxPrice.getText());
			} catch (Exception e) {
				errorMessage("최대 최소 가격은 숫자로 입력해주세요.", "Error");
				return;
			}
		}

		if (!"".equals(tfMinPrice.getText())) {
			try {
				min_price = Integer.parseInt(tfMinPrice.getText());
			} catch (Exception e) {
				errorMessage("최대 최소 가격은 숫자로 입력해주세요.", "Error");
				return;
			}
		}

		if (min_price > max_price) {
			errorMessage("최소 가격은 최대 가격보다 낮아야 합니다.", "Error");
			return;
		}

		center_scroll.removeAll();
		model.setRowCount(0);

		try (var pst = connection.prepareStatement(
				"select * from product as p inner join category as c on c.c_no = p.c_no where p_name like ? and (p_price between ? and ?) and c.c_name = ?")) {
			pst.setObject(1, "%" + tfName.getText() + "%");
			pst.setObject(2, min_price);
			pst.setObject(3, max_price);
			pst.setObject(4, currentCategory);

			int cnt = 0, row = 0;
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				ProductPanel productPanel = new ProductPanel(200, 220, rs.getString(3), rs.getInt(4), rs.getString(6),
						rs.getInt(5), currentCategory);
				center_scroll.add(productPanel);
				model.addRow(new Object[] { rs.getInt(1), currentCategory, rs.getString(3), rs.getInt(4), rs.getInt(5),
						rs.getString(6) });
				cnt++;
			}

			row = cnt / 3;

			if (row % 3 != 0) {
				row++;
			}

			center_scroll.setPreferredSize(new Dimension(600, row * 220));
			center_scroll.revalidate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new ProductForm().setVisible(true);
	}
}
