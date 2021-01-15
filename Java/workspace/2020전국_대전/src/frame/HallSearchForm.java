package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

public class HallSearchForm extends BaseFrame {

	JPanel contentsPanel = new JPanel(new FlowLayout());
	JComboBox<String> cbRegion = createComponent(new JComboBox<String>(), 70, 60, 120, 25);
	JComboBox<String> cbWeddingType = createComponent(new JComboBox<String>(), 70, 95, 120, 25);
	JComboBox<String> cbMealType = createComponent(new JComboBox<String>(), 70, 130, 120, 25);
	JTextField tfPersonMinCount = createComponent(new JTextField(), 70, 165, 60, 25);
	JTextField tfPersonMaxCount = createComponent(new JTextField(), 140, 165, 60, 25);
	JTextField tfPriceMinCount = createComponent(new JTextField(), 70, 190, 60, 25);
	JTextField tfPriceMaxCount = createComponent(new JTextField(), 140, 190, 60, 25);
	JLabel lbSearchResult = createComponent(new JLabel(), 70, 225, 200, 25);

	public HallSearchForm() {
		super(1000, 400, "검색");
		JPanel westPanel = createComponent(new JPanel(null), 300, 0);
		JPanel centerPanel = new JPanel(new BorderLayout());
		JScrollPane scrollPane = createComponent(new JScrollPane(contentsPanel), 700, 400);

		int cnt = 0;
		for (String txt : new String[] { "지역:", "예식형태:", "수용인원:", "식사종류:", "홀사용료:" }) {
			westPanel.add(createComponent(new JLabel(txt, 4), 0, 35 * cnt + 60, 60, 20));
			cnt++;
		}

		westPanel.add(cbRegion);
		westPanel.add(cbWeddingType);
		westPanel.add(cbMealType);
		westPanel.add(tfPersonMinCount);
		westPanel.add(createComponent(new JLabel("~"), 132, 165, 10, 20));
		westPanel.add(createComponent(new JLabel("~"), 132, 190, 10, 20));
		westPanel.add(tfPersonMaxCount);
		westPanel.add(lbSearchResult);

		JPanel west_inner = createComponent(new JPanel(new FlowLayout()), 0, 270, 300, 30);
		west_inner.add(createButton("초기화", e -> resetSearch()));
		west_inner.add(createButton("검색", e -> hasSearch()));
		west_inner.add(createButton("닫기", e -> openFrame(new MainForm())));

		centerPanel.add(scrollPane);

		add(westPanel, BorderLayout.WEST);
		add(centerPanel);
	}

	public void resetSearch() {
		cbRegion.setSelectedIndex(0);
		cbWeddingType.setSelectedIndex(0);
		cbMealType.setSelectedIndex(0);
		tfPersonMaxCount.setText("");
		tfPersonMinCount.setText("");
		tfPriceMaxCount.setText("");
		tfPriceMinCount.setText("");
	}

	public void hasSearch() { // 머리 식히고 집가서 하자
		try (PreparedStatement pst = connection.prepareStatement(
				"select wh.wno,wname,group_concat(tyname) as wtype ,mname, wh.wadd, wh.wpeople,wh.wprice from weddinghall as wh \r\n"
						+ "left outer join w_ty as wty on wh.wNo = wty.wNo \r\n"
						+ "left outer join weddingtype as wdt on wty.tyNo = wdt.tyNo\r\n"
						+ "left outer join w_m as wm on wm.wNo = wh.wNo\r\n"
						+ "left outer join mealtype as mt on wm.mNo = mt.mNo where (wadd like ? and tyname like ? and wadd like ?) and (wpeople > ? and wpeople < ?) and wprice like ?  group by wname, mname;")) {

			pst.setObject(1, cbRegion.getSelectedItem());
			pst.setObject(2, cbWeddingType.getSelectedItem());
			pst.setObject(3, cbMealType.getSelectedItem());
			pst.setObject(4, tfPersonMinCount.getText());
			pst.setObject(5, tfPersonMaxCount.getText());
			pst.setObject(6, tfPriceMinCount.getText());
			pst.setObject(7, tfPriceMaxCount.getText());

			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				contentsPanel.add(new WeddingContent(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6), rs.getInt(7)));
			}
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

	class WeddingContent extends JPanel {
		public WeddingContent(int weddingNo, String name, String addr, String weddingType, String mealType, int person,
				int price) {
			setSize(220, 250);
			setBorder(new LineBorder(Color.black));
		}
	}
}
