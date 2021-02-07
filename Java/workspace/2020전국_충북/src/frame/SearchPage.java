package frame;

import java.awt.BorderLayout;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import model.ConnectionManager;
import model.ProductFiles;
import static frame.BaseFrame.*;

public class SearchPage extends JPanel {

	JPanel serachContentsPanel = new JPanel();
	ProductFiles pf = new ProductFiles();

	JCheckBox[] checkBox = new JCheckBox[29];

	ConnectionManager cm = new ConnectionManager();

	JPanel southPanel = new JPanel();

	public SearchPage() {
		setLayout(new BorderLayout());
		JScrollPane scrollPane = new JScrollPane();

		JPanel northPanel = createComponent(new JPanel(), 800, 200);

		add(scrollPane);
	}

	class ResultPanel extends JPanel {
		public ResultPanel() {

		}

		public void buy() {

		}
	}

	public void search() {
		ArrayList<String> checkList = new ArrayList<String>();
		StringBuilder builder = new StringBuilder();

		Object[] objects = new Object[10];
		for (int i = 0; i < checkBox.length; i++)
			if (!checkBox[i].getText().isEmpty())
				checkList.add(checkBox[i].getText());

		builder.append("select * from product where description like '%" + checkList.get(0) + "%'");

		for (int i = 1; i < checkList.size(); i++)
			builder.append(" and description like '%" + checkList.get(i) + "%'");

		cm.connect();

		try {
			ResultSet rs = cm.select(builder.toString());

			int i = 0;
			while (rs.next()) {
				System.out.println(++i);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		southPanel.add(new ResultPanel());
	}
}
