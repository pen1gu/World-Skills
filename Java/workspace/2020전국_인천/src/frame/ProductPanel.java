package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import static frame.BaseFrame.*;

public class ProductPanel extends JPanel {

	String name, explanation;
	int price, count;

	public ProductPanel(int width, int height, String name, int price, String explanation, int count, String category) {

		this.name = name;
		this.explanation = explanation;
		this.price = price;
		this.count = count;

		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(width, height));
		setBorder(new LineBorder(Color.black));
		add(new JLabel(getImage(width, height - 20, "./지급자료/이미지폴더/" + name + ".jpg")));
		add(new JLabel(name, 0), BorderLayout.SOUTH);

		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				super.mousePressed(e);
				new ProductInfoForm(name, price, explanation, count, category).setVisible(true);
			}
		});
	}
}
