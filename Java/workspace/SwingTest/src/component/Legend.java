package component;

import static frame.BaseFrame.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class Legend extends JPanel {

	public JPanel innerPanel;

	public Legend(String title, int width, int height) {
		setLayout(null);
		setPreferredSize(new Dimension(width, height));
		JLabel lbTitle = createLabel(new JLabel(title), new Font("맑은고딕", 1, 18), Color.black);
		JPanel legend = createComponent(new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 30)), 0, 20, width,
				height - 25);

		innerPanel = createComponent(new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 0)), width-20, height - 80);

		legend.setBorder(new LineBorder(Color.black));
		legend.add(innerPanel);

		add(createComponent(lbTitle, 180, 5, 100, 30)); // 동적으로 수정
		lbTitle.setOpaque(true);
		add(legend);
	}
}
