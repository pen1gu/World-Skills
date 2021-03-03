package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

public class MainForm extends BaseFrame {

	JLabel lbUserName = createLabel(new JLabel(), new Font("맑은 고딕", 1, 22));
	JTextField tfMenuName = new JTextField();
	JTextField tfPrice = new JTextField();
	JTextField tfAmount = new JTextField();

	JComboBox<Integer> cbCount = new JComboBox<Integer>();
	JComboBox<String> cbSize = new JComboBox<String>();

	String currentFood = "음료";

	JPanel contentsPanel = createComponent(new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 20)), 550, 420);
	JLabel lbDetailImage = new JLabel();

	JPanel detailPanel = createComponent(new JPanel(null), 300, 240);

	public MainForm() {
		super(800, 600, "STARBOX");
		JPanel northPanel = createComponent(new JPanel(), 600, 30);
		JPanel north_south = new JPanel(new FlowLayout());
		JPanel westPanel = createComponent(new JPanel(), 55, 0);
		JPanel centerPanel = createComponent(new JPanel(null), 820, 460);

		detailPanel.setBorder(new LineBorder(Color.black));

		northPanel.add(lbUserName, BorderLayout.NORTH);
		northPanel.add(north_south, BorderLayout.SOUTH);
		north_south.add(createButton("구매내역", e -> openFrame(new OrderListForm())));
		north_south.add(createButton("장바구니", e -> openFrame(new ShoppingForm())));
		north_south.add(createButton("인기상품 Top5", e -> openFrame(new Top5Form())));
		north_south.add(createButton("Logout", e -> openFrame(new LoginForm())));

		westPanel.add(createButton("음료", e -> changeGruop("음료")));
		westPanel.add(createButton("푸드", e -> changeGruop("푸드")));
		westPanel.add(createButton("상품", e -> changeGruop("상품")));

		add(northPanel, BorderLayout.NORTH);
		add(westPanel, BorderLayout.WEST);
		add(centerPanel);
	}

	public void changeUserInfo() {
		lbUserName.setText(String.format("회원명 : %s/ 회원등급 : %s / 총 누적 포인트 : %d", userName, userGrade, userPoint));
	}

	public void changeGruop(String group) {
		currentFood = group;
		try (PreparedStatement pst = connection.prepareStatement("select * from menu where m_group = ?")) {
			pst.setObject(1, currentFood);

			ResultSet rs = pst.executeQuery();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new MainForm().setVisible(true);
	}

	public int getAmount() {
		return 1;
	}

	private class MenuPanel extends JPanel {

		public MenuPanel(int menuNo, String name, int price) {
			JLabel lbImage = new JLabel(getImage(180, 180, "./DataFiles/이미지/"));
			JLabel lbMenuName = new JLabel();

			setPreferredSize(new Dimension(180, 200));
			setLayout(new BorderLayout());
			setBorder(new LineBorder(Color.black));

			addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					super.mousePressed(e);

					lbDetailImage.setIcon(getImage(180, 180, ""));
					tfAmount.setText(price + "");
					tfPrice.setText(price + "");
					tfMenuName.setText(name);
					for (int i = 0; i < 50; i++) {
						cbCount.addItem(i);
					}

					cbSize.addItem("M");
					if (currentFood.equals("상품")) {
						cbSize.setEnabled(false);
					}
				}

			});

			add(lbImage);
			add(lbMenuName, BorderLayout.SOUTH);
		}
	}
}
