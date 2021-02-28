package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.Year;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import model.UpdateMenu;

public class MenuMagamentForm extends BaseFrame {

	DefaultTableModel model = new DefaultTableModel(
			new String[] { "n", "", "mealName", "price", "maxCount", "todayMeal" }, 0);
	JTable table = new JTable(model);

	JComboBox<String> cbKind = new JComboBox<String>();

	JCheckBox checkBox = new JCheckBox();
	JButton btnAllSelect;

	DefaultTableCellRenderer dcr = new DefaultTableCellRenderer() {
		public Component getTableCellRendererComponent // 셀렌더러
		(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			JCheckBox box = new JCheckBox();
			box.setSelected(((Boolean) value).booleanValue());
			box.setBackground(Color.white);
			box.setHorizontalAlignment(JLabel.CENTER);
			return box;
		}
	};

	public MenuMagamentForm() {
		super(600, 600, "메뉴 관리");

		JPanel northPanel = createComponent(new JPanel(), 800, 35);
		JPanel centerPanel = new JPanel();

		northPanel.add(btnAllSelect = createButton("모두 선택", e -> selectAllItems()));
		northPanel.add(new JLabel("종류:"));
		northPanel.add(cbKind);
		northPanel.add(createButton("검색", e -> clickSearch()));
		northPanel.add(createButton("수정", e -> clickUpdate()));
		northPanel.add(createButton("삭제", e -> clickDelete()));
		northPanel.add(createButton("오늘의 메뉴 선정", e -> setTodayMenu()));
		northPanel.add(createButton("닫기", e -> openFrame(new ManagementForm())));

		table.removeColumn(table.getColumn("n"));

		table.getColumn("").setCellEditor(new DefaultCellEditor(checkBox));
		table.getColumn("").setCellRenderer(dcr);

		table.getColumnModel().getColumn(0).setPreferredWidth(5);

		for (String element : new String[] { "한식", "중식", "일식", "양식" }) {
			cbKind.addItem(element);
		}

		for (int i = 1; i < 5; i++) {
			table.getColumnModel().getColumn(i).setCellRenderer(centerRender);
		}

		checkBox.addItemListener((ItemEvent e) -> {
			checkEmptyBox();
		});

		JScrollPane scrollPane = createComponent(new JScrollPane(table), 600, 500);

		centerPanel.add(scrollPane);

		add(northPanel, BorderLayout.NORTH);
		add(centerPanel);

		clickSearch();
	}

	public void selectAllItems() {
		for (int i = 0; i < model.getRowCount(); i++) {
			table.getModel().setValueAt(true, i, 1);
		}
		btnAllSelect.setEnabled(false);
	}

	public void clickSearch() {
		model.setRowCount(0);
		try (PreparedStatement pst = connection.prepareStatement("select * from meal where cuisineNo = ?")) {
			pst.setObject(1, cbKind.getSelectedIndex() + 1);

			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				model.addRow(new Object[] { rs.getInt(1), false, rs.getObject(3), rs.getObject(4), rs.getObject(5),
						rs.getInt(6) == 1 ? "Y" : "N", });
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void clickUpdate() {
		int i = 0;

		int selected = -1;
		int cnt = 0;
		for (i = 0; i < table.getRowCount(); i++) {
			if (checkBox.isSelected()) {
				selected = i;
				cnt++;
			}
			System.out.println(checkBox.isSelected());
		}

		if (selected == -1) {
			errorMsg("수정할 메뉴를 선택해주세요.");
			return;
		}

		if (cnt > 1) {
			errorMsg("하나씩 수정가능합니다.");
			return;
		}

		UpdateMenu.kind = (String) cbKind.getSelectedItem();
		UpdateMenu.menuName = (String) table.getValueAt(selected, 1);
		UpdateMenu.price = (int) table.getValueAt(selected, 2);
		UpdateMenu.amount = (int) table.getValueAt(selected, 3);

		openFrame(new MenuAddForm("메뉴 수정"));
	}

	public void clickDelete() {

	}

	public void setTodayMenu() {

	}

	public void checkEmptyBox() {
		for (int i = 0; i < table.getRowCount(); i++) {
			if (!checkBox.isSelected()) {
				btnAllSelect.setEnabled(true);
				return;
			}
		}
		btnAllSelect.setEnabled(false);
	}
}
