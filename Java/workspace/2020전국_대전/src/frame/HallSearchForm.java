package frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class HallSearchForm extends BaseFrame {

	JPanel contentsPanel = createComponent(new JPanel(new FlowLayout(FlowLayout.LEFT)), 650, 400);
	JComboBox<String> cbRegion = createComponent(new JComboBox<String>(), 70, 60, 120, 25);
	JComboBox<String> cbWeddingType = createComponent(new JComboBox<String>(), 70, 95, 120, 25);
	JComboBox<String> cbMealType = createComponent(new JComboBox<String>(), 70, 130, 120, 25);
	JTextField tfPersonMinCount = createComponent(new JTextField(), 70, 165, 60, 25);
	JTextField tfPersonMaxCount = createComponent(new JTextField(), 140, 165, 60, 25);
	JTextField tfMinPrice = createComponent(new JTextField(), 70, 200, 60, 25);
	JTextField tfMaxPrice = createComponent(new JTextField(), 140, 200, 60, 25);
	JLabel lbSearchResult = createComponent(new JLabel(), 70, 225, 200, 25);

	public HallSearchForm() {
		super(1000, 400, "검색");
		JPanel westPanel = createComponent(new JPanel(null), 250, 0);
		JPanel centerPanel = new JPanel(new BorderLayout());
		JScrollPane scrollPane = createComponent(new JScrollPane(contentsPanel), 700, 400);

		int cnt = 0;
		for (String txt : new String[] { "지역:", "예식형태:", "식사종류:", "수용인원:", "홀사용료:" }) {
			westPanel.add(createComponent(new JLabel(txt, 4), 0, 35 * cnt + 60, 60, 20));
			cnt++;
		}

		westPanel.add(cbRegion);
		westPanel.add(cbWeddingType);
		westPanel.add(cbMealType);
		westPanel.add(tfPersonMinCount);
		westPanel.add(tfPersonMaxCount);
		westPanel.add(createComponent(new JLabel("~"), 132, 165, 10, 20));
		westPanel.add(createComponent(new JLabel("~"), 132, 190, 10, 20));
		westPanel.add(tfMaxPrice);
		westPanel.add(tfMinPrice);
		westPanel.add(lbSearchResult);

		JPanel west_inner = createComponent(new JPanel(new FlowLayout()), 0, 270, 250, 30);
		west_inner.add(createButton("초기화", e -> resetSearch()));
		west_inner.add(createButton("검색", e -> hasSearch()));
		west_inner.add(createButton("닫기", e -> openFrame(new MainForm())));

		centerPanel.add(scrollPane);

		westPanel.add(west_inner);
		add(westPanel, BorderLayout.WEST);
		add(centerPanel);
		insertComboData();
		hasSearch();
	}

	public void resetSearch() {
		cbRegion.setSelectedIndex(0);
		cbWeddingType.setSelectedIndex(0);
		cbMealType.setSelectedIndex(0);
		tfPersonMaxCount.setText("");
		tfPersonMinCount.setText("");
		tfMaxPrice.setText("");
		tfMinPrice.setText("");
		hasSearch();
	}

	public void hasSearch() { // 머리 식히고 집가서 하자
		contentsPanel.removeAll();
		int minPrice = 0;
		int maxPrice = Integer.MAX_VALUE;
		int minPersonCount = 0;
		int maxPersonCount = Integer.MAX_VALUE;
		try {
			minPrice = tfMinPrice.getText().isEmpty() ? 0 : Integer.parseInt(tfMinPrice.getText());
			maxPrice = tfMaxPrice.getText().isEmpty() ? Integer.MAX_VALUE : Integer.parseInt(tfMaxPrice.getText());
			minPersonCount = tfPersonMinCount.getText().isEmpty() ? 0 : Integer.parseInt(tfPersonMinCount.getText());
			maxPersonCount = tfPersonMaxCount.getText().isEmpty() ? Integer.MAX_VALUE
					: Integer.parseInt(tfPersonMaxCount.getText());
		} catch (NumberFormatException e1) {
			errorMessage("경고 : 수용인원과 홀사용료는 숫자만 입력 가능합니다.", "경고");
			return;
		}

		if (minPrice > maxPrice || minPersonCount > maxPersonCount) {
			errorMessage("숫자를 올바르게 입력해주세요.", "경고");
			return;
		}

		String region = cbRegion.getSelectedItem().equals("전체") ? "" : (String) cbRegion.getSelectedItem();
		String weddingType = cbWeddingType.getSelectedItem().equals("전체") ? ""
				: (String) cbWeddingType.getSelectedItem();
		String mealType = cbMealType.getSelectedItem().equals("전체") ? "" : (String) cbMealType.getSelectedItem();

		try (PreparedStatement pst = connection.prepareStatement(
				"select wh.wno,wname,group_concat(tyname) as wtype ,mname, wh.wadd, wh.wpeople,wh.wprice from weddinghall as wh \r\n"
						+ "left outer join w_ty as wty on wh.wNo = wty.wNo \r\n"
						+ "left outer join weddingtype as wdt on wty.tyNo = wdt.tyNo\r\n"
						+ "left outer join w_m as wm on wm.wNo = wh.wNo\r\n"
						+ "left outer join mealtype as mt on wm.mNo = mt.mNo where (wadd like ? and tyname like ? and mname like ?) and (wpeople >= "
						+ minPersonCount + " and wpeople <= " + maxPersonCount + ") and (wprice >= " + minPrice
						+ " and wprice <= " + maxPrice + ")  group by wname;")) {

			pst.setObject(1, "%" + region + "%");
			pst.setObject(2, "%" + weddingType + "%");
			pst.setObject(3, "%" + mealType + "%");
			ResultSet rs = pst.executeQuery();

			int row = 1;
			int cnt = 0;
			while (rs.next()) {
				contentsPanel.add(new WeddingContent(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getInt(6), rs.getInt(7)));
				cnt++;
			}

			row = cnt / 3;
			if (cnt % 3 != 0) {
				row++;
			}

			contentsPanel.setPreferredSize(new Dimension(660, (row * 250) + 30));
			revalidate();
			repaint();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void changeSearchLabel(int no) {
		lbSearchResult.setText(no + "건이 검색되었습니다.");
	}

	public static void main(String[] args) {
		new HallSearchForm().setVisible(true);
	}

	public void insertComboData() {
		for (String element : new String[] { "전체", "노원구", "송파구", "강남구", "중구", "마포구", "서초구", "영등포구", "종로구" }) {
			cbRegion.addItem(element);
		}

		for (String element : new String[] { "전체", "일반웨딩홀", "강당", "하우스", "호텔웨딩홀", "야외예식", "컨벤션", "레스토랑", "회관", "성당",
				"교회" }) {
			cbWeddingType.addItem(element);
		}

		for (String element : new String[] { "전체", "양식", "뷔페", "한식" }) {
			cbMealType.addItem(element);
		}
	}

	class WeddingContent extends JPanel {
		public WeddingContent(int weddingNo, String name, String weddingType, String mealType, String addr, int person,
				int price) {
			setLayout(new FlowLayout(FlowLayout.LEFT));

			setPreferredSize(new Dimension(215, 250));
			add(new JLabel(getImage("./datafiles/호텔이미지/" + name + "/" + name + "1.jpg", 220, 80)));
			add(createComponent(new JLabel("name:" + name), 200, 20));
			add(createComponent(new JLabel("address:" + addr), 200, 20));
			add(createComponent(new JLabel("weddingType:" + weddingType), 200, 20));
			add(createComponent(new JLabel("mealType:" + mealType), 200, 20));
			add(createComponent(new JLabel("person:" + person), 200, 20));
			add(createComponent(new JLabel("price:" + price), 200, 20));
			
			addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					super.mousePressed(e);
					
				}
			});
		}
	}
}
