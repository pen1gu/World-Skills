package frame;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import static frame.BaseFrame.*;

public class ContentsPanel extends JPanel {

	public ContentsPanel() {// rebuild because admin form

	}

	class WeddingContent extends JPanel {
		public WeddingContent(int weddingNo, String name, String weddingType, String mealType, String addr, int person,
				int price) {
			setLayout(new FlowLayout(FlowLayout.LEFT));

			setPreferredSize(new Dimension(215, 250));
			add(new JLabel(getImage("./datafiles/호텔이미지/" + name + "/" + name + "1.jpg", 220, 80)));
			add(createComponent(new JLabel("name:" + name), 200, 20));
			add(createComponent(new JLabel("address:" + addr), 200, 20));
			add(createComponent(new JLabel("weddingType:" + weddingType), 200, 20));
			add(createComponent(new JLabel("mealType:" + mealType), 200, 20));
			add(createComponent(new JLabel("person:" + person), 200, 20));
			add(createComponent(new JLabel("price:" + price), 200, 20));
		}
	}
}
