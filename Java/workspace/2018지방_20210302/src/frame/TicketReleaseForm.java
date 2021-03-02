package frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import model.MenuInfo;

public class TicketReleaseForm extends BaseFrame {

//	MenuInfo menuInfo = new MenuInfo();

	DefaultTableModel model = new DefaultTableModel(new String[] { "상품번호", "품명", "수량", "금액" }, 0) {
		public boolean isCellEditable(int row, int column) {
			return false;
		};
	};
	JTable table = new JTable(model);

	JLabel lbAmount = new JLabel("", 4);
	JTextField tfMenuName = createComponent(new JTextField(), 250, 20);
	JTextField tfCount = createComponent(new JTextField(), 40, 20);

	MenuButton currentButton;
	ArrayList<MenuButton> enabledList = new ArrayList<MenuButton>();
	int kind = 0;

	public TicketReleaseForm(int kind) {
		super(1000, 450, "결제");
		this.kind = kind;

		String kindValue = "";
		switch (kind) {
		case 1: {
			kindValue = "한식";
			break;
		}
		case 2: {
			kindValue = "중식";
			break;
		}
		case 3: {
			kindValue = "일식";
			break;
		}
		case 4: {
			kindValue = "양식";
			break;
		}
		}
		add(createComponent(createLabel(new JLabel(kindValue, 0), new Font("굴림", 1, 26)), 1000, 50),
				BorderLayout.NORTH);

		JPanel westPanel = createComponent(new JPanel(new GridLayout(0, 5)), 550, 400);
		JPanel eastPanel = createComponent(new JPanel(new FlowLayout()), 450, 400);

		westPanel.setBorder(BorderFactory.createEmptyBorder(40, 10, 150, 10));
		try (PreparedStatement pst = connection
				.prepareStatement("select * from meal where cuisineNo = ? and maxCount <> 0 and todayMeal = 1")) {
			pst.setObject(1, kind);

			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				westPanel.add(new MenuButton(rs.getInt(1), rs.getString(3), rs.getInt(4), rs.getInt(5), kind));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// region: eastPanel
		JScrollPane scrollPane = createComponent(new JScrollPane(table), 400, 200);

		tfMenuName.setEnabled(false);
		eastPanel.add(createComponent(createLabel(new JLabel("총결제금액:"), new Font("굴림", 1, 18)), 300, 30));
		eastPanel.add(createComponent(createLabel(lbAmount, new Font("굴림", 1, 16)), 100, 30));

		eastPanel.add(scrollPane);

		eastPanel.add(new JLabel("선택품명:"));
		eastPanel.add(tfMenuName);
		eastPanel.add(new JLabel("수량:"));
		eastPanel.add(tfCount);

		eastPanel.add(createComponent(createButton("입력", e -> insertMenu()), 100, 35));
		eastPanel.add(createComponent(createButton("결제", e -> buyProduct()), 100, 35));
		eastPanel.add(createComponent(createButton("닫기", e -> openFrame(new UserForm())), 100, 35));

		table.addMouseListener(new MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent e) {
				if (e.getClickCount() == 2) {
					enabledList.get(table.getSelectedRow()).setEnabled(true);
					enabledList.remove(table.getSelectedRow());
					model.removeRow(table.getSelectedRow());

					int priceAmount = 0;
					for (int i = 0; i < model.getRowCount(); i++) {
						priceAmount += Integer.parseInt(model.getValueAt(i, 3).toString());
					}

					lbAmount.setText(String.format("%,d원", priceAmount));
				}
			};
		});

		add(westPanel, BorderLayout.WEST);
		add(eastPanel, BorderLayout.EAST);
		lbAmount.setText("0원");
	}

	public void insertMenu() {

		if (tfMenuName.getText().isEmpty()) {
			return;
		}
		int count = 0;
		try {
			count = Integer.parseInt(tfCount.getText());
			if (count <= 0)
				new Throwable();

		} catch (Exception e) {
			errorMsg("수량을 입력해주세요.");
			return;
		}

		if (currentButton.count < count) {
			errorMsg("조리가능 수량이 부족합니다.");
			return;
		}

		model.addRow(
				new Object[] { currentButton.productNo, tfMenuName.getText(), count, count * currentButton.price });

		int priceAmount = 0;
		for (int i = 0; i < model.getRowCount(); i++) {
			priceAmount += Integer.parseInt(model.getValueAt(i, 3).toString());
		}

		lbAmount.setText(String.format("%,d원", priceAmount));

		currentButton.setEnabled(false);
		enabledList.add(currentButton);

		tfMenuName.setText("");
		tfCount.setText("");
	}

	private class MenuButton extends JButton {

		String name;
		int count, productNo, price, kind;

		public MenuButton(int productNo, String name, int price, int count, int kind) {
			this.name = name;
			this.count = count;
			this.price = price;
			this.productNo = productNo;
			this.kind = kind;

			setLayout(new GridBagLayout());
			setPreferredSize(new Dimension(80, 40));

			add(new JLabel(name + "(" + price + ")"));

			addActionListener(e -> selectMenu());
		}

		public void selectMenu() {
			currentButton = this;
			tfMenuName.setText(name);
		}
	}

	public void buyProduct() {
		// model add

		if (model.getRowCount() < 1) {
			errorMsg("메뉴를 선택해 주세요.");
			return;
		}

		ArrayList<MenuInfo> info = new ArrayList<MenuInfo>();

		for (int i = 0; i < model.getRowCount(); i++) {
			MenuInfo menuInfo = new MenuInfo();

			menuInfo.productNo = (int) model.getValueAt(i, 0);
			menuInfo.name = (String) model.getValueAt(i, 1);
			menuInfo.count = (int) model.getValueAt(i, 2);
			menuInfo.price = (int) model.getValueAt(i, 3);
			menuInfo.kind = (int) currentButton.kind;

			info.add(menuInfo);
		}

		// TODO: jpanel to joptionpane
		JPanel panel = createComponent(new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0)), 200, 40);
		JComboBox<Integer> cbUserNo = createComponent(new JComboBox<Integer>(), 100, 20);
		JTextField tfPw = createComponent(new JTextField(), 100, 20);

		try {
			ResultSet rs = statement.executeQuery("select memberNo from member;");
			while (rs.next()) {
				cbUserNo.addItem(rs.getInt(1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		panel.add(createComponent(new JLabel("사원번호"), 90, 20));
		panel.add(cbUserNo);
		panel.add(createComponent(new JLabel("패스워드"), 90, 20));
		panel.add(tfPw);

		int yesNo = JOptionPane.showConfirmDialog(null, panel, "결제자 인증", JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE);

		if (yesNo != JOptionPane.YES_OPTION) {
			return;
		} else {
			try (PreparedStatement pst = connection
					.prepareStatement("select * from member where memberNo = ? and passwd = ?")) {
				pst.setObject(1, cbUserNo.getSelectedItem());
				pst.setObject(2, tfPw.getText());

				ResultSet rs = pst.executeQuery();
				if (!rs.next()) {
					errorMsg("패스워드가 일치하지 않습니다.");
					return;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		userNo = (int) cbUserNo.getSelectedItem();

		for (int i = 0; i < info.size(); i++) {
			try (PreparedStatement pst = connection
					.prepareStatement("insert into orderlist values(0,?,?,?,?,?,now())")) {
				pst.setObject(1, kind);
				pst.setObject(2, info.get(i).productNo);
				pst.setObject(3, userNo);
				pst.setObject(4, info.get(i).count);
				pst.setObject(5, info.get(i).price);

				pst.execute();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		infoMsg("결제가 완료되었습니다.\n식권을 출력합니다.");

		new TicketListForm(info).setVisible(true);
	}
}
