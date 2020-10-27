package setting;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;

import javax.swing.JPanel;

public class MenuAddFrame extends BaseFrame {

	MenuPanel menuPanel = new MenuPanel("사진등록");

	public MenuAddFrame() {
		super(330, 220, "메뉴 수정");

		var southPanel = new JPanel();

		add(menuPanel);

		southPanel.add(createButton("등록", this::clickSubmit));
		southPanel.add(createButton("취소", e -> {
			dispose();
			new AdminMenuFrame().setVisible(true);
		}));

		add(southPanel, "South");
	}

	private void clickSubmit(ActionEvent e) {

		if (menuPanel.cbGroup.getSelectedIndex() == 0 || menuPanel.tfMenu.getText().length() == 0
				|| menuPanel.tfPrice.getText().length() == 0 || menuPanel.path == null) {
			errorMessage("빈칸이 존재합니다.");
			return;
		}

		try {
			int price = Integer.parseInt(menuPanel.tfPrice.getText());
			String menuName = menuPanel.tfMenu.getText();

			try (var pst = connection.prepareStatement("SELECT * FROM menu WHERE m_name = ?")) {

				pst.setObject(1, menuName);

				var rs = pst.executeQuery();

				if (rs.next()) {
					errorMessage("이미 존재하는 메뉴명입니다.");
					return;
				}

			} catch (SQLException e1) {
				e1.printStackTrace();
			}

			try (var pst = connection.prepareStatement("INSERT INTO menu VALUES(0, ?, ?, ?)")) {
				pst.setObject(1, menuPanel.cbGroup.getSelectedItem());
				pst.setObject(2, menuName);
				pst.setObject(3, price);

				pst.execute();

				Files.copy(Paths.get(menuPanel.path), Paths.get("./DataFiles/이미지/" + menuName + ".jpg"),
						StandardCopyOption.REPLACE_EXISTING);

				informationMessage("메뉴가 등록되었습니다.");
			} catch (SQLException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} catch (NumberFormatException e1) {
			errorMessage("가격은 숫자로 입력해주세요.");
		}

	}

	public static void main(String[] args) {
		new MenuAddFrame().setVisible(true);
	}

}
