package setting;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

public class MenuEditFrame extends BaseFrame {

	JComboBox<String> cbGroup = new JComboBox<>("전체,음료,푸드,상품".split(","));
	JTextField tfSearch = new JTextField(20);
	MenuPanel menuPanel = new MenuPanel("사진선택");
	DefaultTableModel model = new DefaultTableModel("n,분류,메뉴명,찾기".split(","), 0);
	JTable table = new JTable(model);

	public MenuEditFrame() {
		super(710, 280, "메뉴 수정");

		var northPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		var centerPanel = new JPanel(null);

		var scrollPane = createComponent(new JScrollPane(table), 0, 0, 370, 200);

		table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				selectTableRow();
			}
		});

		table.removeColumn(table.getColumn("n"));
		table.getColumnModel().getColumn(1).setPreferredWidth(180);
		table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);

		northPanel.add(new JLabel("검색"));
		northPanel.add(cbGroup);
		northPanel.add(tfSearch);
		northPanel.add(createButton("찾기", this::clickSearch));

		centerPanel.add(scrollPane);

		var menuFieldsPanel = createComponent(new JPanel(new BorderLayout()), 375, 0, 360, 180);
		var menuFieldsSouthPanel = new JPanel();

		menuFieldsSouthPanel.add(createButton("삭제", this::clickDelete));
		menuFieldsSouthPanel.add(createButton("수정", this::clickUpdate));
		menuFieldsSouthPanel.add(createButton("취소", e -> openFrame(new AdminMenuFrame())));

		menuFieldsPanel.add(menuPanel);
		menuFieldsPanel.add(menuFieldsSouthPanel, "South");

		centerPanel.add(menuFieldsPanel);

		add(northPanel, "North");
		add(centerPanel);

		clickSearch(null);
	}

	private void clear() {
		menuPanel.cbGroup.setSelectedItem("");
		menuPanel.tfMenu.setText("");
		menuPanel.tfPrice.setText("");
		menuPanel.path = null;
		menuPanel.lbImg.setIcon(null);
	}

	private void clickDelete(ActionEvent e) {
		int row = table.getSelectedRow();

		if (row == -1) {
			errorMessage("빈칸이 존재합니다.");
			return;
		}

		try {
			statement.execute("DELETE FROM menu WHERE m_no = " + model.getValueAt(row, 0));

			Files.delete(Paths.get(menuPanel.path));

			informationMessage("삭제되었습니다.");

			clear();
			clickSearch(null);

		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	private void clickUpdate(ActionEvent e) {
		int row = table.getSelectedRow();

		if (row == -1) {
			errorMessage("수정할 메뉴를 선택해주세요.");
			return;
		}

		String menuName = menuPanel.tfMenu.getText();

		if (menuName.isEmpty() || menuPanel.tfPrice.getText().isEmpty()
				|| menuPanel.cbGroup.getSelectedItem().equals("") || menuPanel.path == null) {
			errorMessage("빈칸이 존재합니다.");
			return;
		}

		int price = 0;

		try {
			price = Integer.parseInt(menuPanel.tfPrice.getText());
		} catch (NumberFormatException e1) {
			errorMessage("가격을 다시 입력해주세요.");
			return;
		}

		try (var pst = connection
				.prepareStatement("SELECT * FROM menu WHERE m_name = ? AND m_no != " + model.getValueAt(row, 0))) {

			pst.setObject(1, menuName);

			var rs = pst.executeQuery();
			if (rs.next()) {
				errorMessage("이미 존재하는 메뉴명입니다.");
				return;
			}

		} catch (SQLException e1) {

			e1.printStackTrace();
		}

		try (var pst = connection
				.prepareStatement("UPDATE menu SET m_group = ?, m_name = ?, m_price = ? WHERE m_no = ?")) {

			pst.setObject(1, menuPanel.cbGroup.getSelectedItem());
			pst.setObject(2, menuName);
			pst.setObject(3, price);
			pst.setObject(4, model.getValueAt(row, 0));

			pst.execute();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		try {
			Files.copy(Paths.get(menuPanel.path), Paths.get("./DataFiles/이미지/" + menuName + ".jpg"),
					StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		informationMessage("수정되었습니다.");

		clear();
		clickSearch(null);

	}

	private void selectTableRow() {
		int row = table.getSelectedRow();

		if (row == -1) {
			return;
		}

		menuPanel.cbGroup.setSelectedItem(model.getValueAt(row, 1));
		menuPanel.tfMenu.setText((String) model.getValueAt(row, 2));
		menuPanel.tfPrice.setText(model.getValueAt(row, 3).toString());
		menuPanel.path = "./DataFiles/이미지/" + model.getValueAt(row, 2) + ".jpg";
		menuPanel.lbImg.setIcon(getImage(menuPanel.path, 100, 100));
	}

	private void clickSearch(ActionEvent e) {

		model.setRowCount(0);

		try (var pst = connection.prepareStatement("SELECT * FROM menu WHERE m_group LIKE ? AND m_name LIKE ?")) {

			if (cbGroup.getSelectedIndex() == 0) {
				pst.setObject(1, "%");
			} else {
				pst.setObject(1, cbGroup.getSelectedItem());
			}

			pst.setObject(2, "%" + tfSearch.getText() + "%");

			var rs = pst.executeQuery();

			while (rs.next()) {
				model.addRow(new Object[] { rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4) });
			}

		} catch (SQLException e1) {
			e1.printStackTrace();
		}

	}

	public static void main(String[] args) {
		new MenuEditFrame().setVisible(true);
	}

}
