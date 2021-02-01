package frame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import model.Connector;

public class SearchWeddingForm extends BaseFrame {

	JPanel contentsPanel = new JPanel(new FlowLayout());

	SearchWeddingForm() {
		super(1000, 400, "검색");

		JPanel westPanel = new JPanel(null);

		JPanel centerPanel = new JPanel(new BorderLayout());

		JScrollPane scrollPane = createComponent(new JScrollPane(contentsPanel), 700, 400);

		centerPanel.add(scrollPane);

		add(westPanel, BorderLayout.WEST);
		add(centerPanel);
	}

	public static void main(String[] args) {
		new SearchWeddingForm().setVisible(true);
	}
	
	public void insertContents(){
//		ArrayList<ArrayList<Object>> list = Connector.getSqlResults("select * from", );
	}

	class WeddingContent extends JPanel {
		public WeddingContent(String name, String addr, String weddingType, String mealType, int personCount,
				int price) {

		}
	}
}
