package frame;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.sql.PreparedStatement;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

import model.UpdateMenu;

public class MenuAddForm extends BaseFrame {

	JButton btnSubmit;
	JComboBox<String> cbMenu = new JComboBox<String>();
	JTextField tfMenuName = new JTextField();
	JComboBox<Integer> cbPrice = new JComboBox<Integer>();
	JComboBox<Integer> cbAmount = new JComboBox<Integer>();

	public MenuAddForm(String caption) {
		super(400, 290, caption);
		setLayout(new GridLayout(0, 2));

		initialize();

		if (caption.equals("신규 메뉴 등록")) {
			setTitle(caption);
			btnSubmit = createButton("등록", this::clickSubmit);
		} else if (caption.equals("메뉴 수정")) {
			setTitle(caption);
			btnSubmit = createButton("등록", this::clickUpdate);

			cbMenu.setSelectedItem(UpdateMenu.kind);
			tfMenuName.setText(UpdateMenu.menuName);
			cbPrice.setSelectedIndex((UpdateMenu.price / 500) - 1);
			cbAmount.setSelectedIndex(UpdateMenu.amount - 1);
		}

		add(new JLabel("종류"));
		add(cbMenu);
		add(new JLabel("메뉴명"));
		add(tfMenuName);
		add(new JLabel("가격"));
		add(cbPrice);
		add(new JLabel("조리가능수량"));
		add(cbAmount);

		add(btnSubmit);
		add(createButton("닫기", e -> openFrame(new ManagementForm())));

	}

	public void initialize() {
		for (String element : new String[] { "한식", "중식", "일식", "양식" })
			cbMenu.addItem(element);

		for (int i = 1000; i <= 12000; i++)
			if (i % 500 == 0)
				cbPrice.addItem(i);

		for (int i = 0; i <= 50; i++)
			cbAmount.addItem(i);
	}

	public void clickSubmit(ActionEvent e) {
		String menuName = tfMenuName.getText().trim();
		if (menuName.isEmpty()) {
			errorMsg("메뉴명을 확인해주세요.");
			return;
		}

		try (PreparedStatement pst = connection.prepareStatement("insert into meal values(0,?,?,?,?,0)")) {
			pst.setObject(1, cbMenu.getSelectedIndex() + 1);
			pst.setObject(2, menuName);
			pst.setObject(3, cbPrice.getSelectedItem());
			pst.setObject(4, cbAmount.getSelectedItem());

			pst.execute();
			informationMsg("메뉴가 등록되었습니다.");
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}

	public void clickUpdate(ActionEvent e) {
		try (PreparedStatement pst = connection.prepareStatement(
				"update meal set cuisineNo = ?, mealName = ?, price = ?, maxCount = ? where mealNo = ?")) {
			pst.setObject(1, cbMenu.getSelectedIndex() + 1);
			pst.setObject(2, tfMenuName.getText());
			pst.setObject(3, cbPrice.getSelectedItem());
			pst.setObject(4, cbAmount.getSelectedItem());
			pst.setObject(5, UpdateMenu.mealNo);

			pst.executeUpdate();

			informationMsg("메뉴가 수정되었습니다.");
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}
}
