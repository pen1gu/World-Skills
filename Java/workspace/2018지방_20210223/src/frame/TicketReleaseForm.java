package frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class TicketReleaseForm extends BaseFrame {

	JLabel lbAmount = createLabel(new JLabel(), new Font("굴림", 1, 16));
	JTextField tfCurrentProductName = new JTextField();
	JTextField tfCount = new JTextField();

	int frow = 0;

	DefaultTableModel model = new DefaultTableModel(new String[] { "상품번호", "품명", "수량", "금액" }, 0) {
		@Override
		public boolean isCellEditable(int row, int column) {
			frow = row;
			return false;
		};
	};

	int enabledCount = 0;
	JTable table = new JTable(model);
	int kindNo = 0;
	ArrayList<MenuButton> buttonList = new ArrayList<MenuButton>();
	ArrayList<Integer> enabledList = new ArrayList<Integer>();
	int currentBtnNo = 0;

	public TicketReleaseForm(int kindNo) {
		super(1000, 380, "결제");

		this.kindNo = kindNo;
		String kind = "";
		if (1 == kindNo) {
			kind = "한식";
		} else if (2 == kindNo) {
			kind = "중식";
		} else if (3 == kindNo) {
			kind = "일식";
		} else if (4 == kindNo) {
			kind = "양식";
		}

		add(createComponent(createLabel(new JLabel(kind, 0), new Font("굴림", Font.BOLD, 22)), 0, 50),
				BorderLayout.NORTH);

		JPanel westPanel = createComponent(new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0)), 550, 0);
		JPanel eastPanel = createComponent(new JPanel(new FlowLayout()), 450, 0);
		JScrollPane scrollPane = createComponent(new JScrollPane(table), 410, 170);

		///// west

		try (PreparedStatement pst = connection
				.prepareStatement("select * from meal where cuisineNo = ? and todayMeal = 1")) {
			pst.setObject(1, kindNo);
			ResultSet rs = pst.executeQuery();

			int count = 0;
			while (rs.next()) {
				buttonList.add(new MenuButton(rs.getInt(1), rs.getString(3), rs.getInt(4), rs.getInt(5), count));
				westPanel.add(buttonList.get(count));
				count++;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		///// east
		eastPanel.add(createComponent(createLabel(new JLabel("총결제금액:"), new Font("굴림", Font.BOLD, 18)), 330, 30));
		eastPanel.add(createComponent(lbAmount, 70, 30));

		eastPanel.add(scrollPane);
		eastPanel.add(new JLabel("선택품명:"));
		eastPanel.add(createComponent(tfCurrentProductName, 250, 20));
		eastPanel.add(new JLabel("수량:"));
		eastPanel.add(createComponent(tfCount, 60, 20));
		eastPanel.add(createComponent(createButton("입력", e -> insertProduct()), 100, 35));
		eastPanel.add(createComponent(createButton("결제", e -> clickBuy()), 100, 35));
		eastPanel.add(createComponent(createButton("닫기", e -> openFrame(new TicketForm())), 100, 35));

		tfCurrentProductName.setEnabled(false);

		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent e) {
				if (e.getClickCount() == 2) {
					for (int i = 0; i < buttonList.size(); i++) {
						if (buttonList.get(i).productNo == (int) model.getValueAt(frow, 0)) {
							buttonList.get(i).setEnabled(true);
						}
					}
					model.removeRow(frow);
					lbAmount.setText(String.format("%,d원", getAmount()));
				}
			};
		});

		add(westPanel, BorderLayout.WEST);
		add(eastPanel, BorderLayout.EAST);
	}

	private int getAmount() {
		int amount = 0;
		for (int i = 0; i < model.getRowCount(); i++) {
			amount += (int) model.getValueAt(i, 3);
		}
		return amount;
	}

	private void insertProduct() {
		MenuButton button = buttonList.get(currentBtnNo);
		int count;
		try {
			if (tfCurrentProductName.getText().isEmpty()) {
				throw new NumberFormatException();
			}
			count = Integer.parseInt(tfCount.getText());
		} catch (NumberFormatException e1) {
			errorMsg("수량을 입력해주세요.");
			return;
		}

		if (button.maxCount - count < 0) {
			errorMsg("조리가능수량이 부족합니다.");
			return;
		}
		model.addRow(new Object[] { button.productNo, tfCurrentProductName.getText(), count, count * button.price });
		lbAmount.setText(String.format("%,d원", getAmount()));

		tfCurrentProductName.setText("");
		tfCount.setText("");

	}

	private void clickBuy() {
		if (model.getRowCount() == 0) {
			errorMsg("메뉴를 선택해 주세요.");
			return;
		}

		try {
			ResultSet rs = statement.executeQuery("select memberNo from member;");
			while (rs.next()) {

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//TODO: 확인 패널 개발
//		int yesNo = JOptionPane.showMessageDialog(parentComponent, message, title, messageType);

		// 맞았다는 가정하에

		String currentTime = "";
		String[] menuArr = new String[buttonList.size()];
		int[] mealNoArr = new int[buttonList.size()];
		int[] mealCount = new int[buttonList.size()];
		int[] menuPriceArr = new int[buttonList.size()];

		try {
			ResultSet rs = statement.executeQuery("select now()");
			rs.next();
			currentTime = rs.getString(1);
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (int i = 0; i < model.getRowCount(); i++) {
			try (PreparedStatement pst = connection.prepareStatement("insert into orderlist values(0,?,?,?,?,?,?)")) {
				pst.setObject(1, kindNo);
				pst.setObject(2, model.getValueAt(i, 0));
				pst.setObject(3, "10010"); // cb selected
				pst.setObject(4, model.getValueAt(i, 2));
				pst.setObject(5, model.getValueAt(i, 3));
				pst.setObject(6, currentTime);

				pst.execute();
			} catch (Exception e) {
				e.printStackTrace();
			}
			mealNoArr[i] = (int) model.getValueAt(i, 0);
			menuArr[i] = (String) model.getValueAt(i, 1);
			mealCount[i] = (int) model.getValueAt(i, 2);
			menuPriceArr[i] = (int) model.getValueAt(i, 3);
		}
		informationMsg("결제가 완료되었습니다.\n식권을 출력합니다.");
		openFrame(new TicketListFrame(mealCount, kindNo, menuArr, mealNoArr, menuPriceArr));
	}

	private class MenuButton extends JButton {

		int maxCount, productNo, price, index;
		String name;

		public MenuButton(int productNo, String name, int price, int maxCount, int index) {
			this.maxCount = maxCount;
			this.price = price;
			this.productNo = productNo;
			this.name = name;
			this.index = index;

			setPreferredSize(new Dimension(100, 50));
			setText(name + "(" + price + ")");
			setMargin(new Insets(0, 0, 0, 0));

			addActionListener(e -> insertInformation());
		}

		private void insertInformation() {
			currentBtnNo = index;
			tfCurrentProductName.setText(name);
			this.setEnabled(false);
		}
	}

}
