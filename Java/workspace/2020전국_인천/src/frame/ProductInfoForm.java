package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

public class ProductInfoForm extends BaseFrame {

	JTextField tfProduct = new JTextField();
	JTextField tfPrice = new JTextField();
	JTextField tfCount = new JTextField();

	JTextArea taExplanation = new JTextArea(50, 6);

	public ProductInfoForm(String name, int price, String explanation, int count, String category) {
		super(600, 400, "구매");
		setLayout(null);
		taExplanation.setBorder(new LineBorder(Color.black));
		JLabel lbImage = createComponent(new JLabel(getImage(120, 120, "./지급자료/이미지폴더/" + name + ".jpg")), 10, 10, 120,
				120);
		lbImage.setBorder(new LineBorder(Color.black));
		add(lbImage);

		add(createComponent(new JLabel("제품명", 0), 160, 10, 40, 30));
		add(createComponent(new JLabel("가격", 0), 160, 60, 40, 30));
		add(createComponent(new JLabel("수량", 0), 160, 110, 40, 30));

		add(createComponent(tfProduct, 210, 10, 160, 30));
		add(createComponent(tfPrice, 210, 60, 160, 30));
		add(createComponent(tfCount, 210, 110, 160, 30));

		tfProduct.setText(name);
		tfPrice.setText(price + "");

		tfProduct.setEnabled(false);
		tfPrice.setEnabled(false);

		add(createComponent(new JLabel("상품 설명"), 10, 130, 60, 20));
		add(createComponent(taExplanation, 10, 160, 300, 80));
		taExplanation.setLineWrap(true);
		taExplanation.append(explanation);

		add(createComponent(createButton("구매하기", e -> buyProduct()), 330, 210, 100, 30));
		add(createComponent(createButton("취소하기", e -> openFrame(new ProductForm())), 450, 210, 100, 30));

		JPanel southPanel = createComponent(new JPanel(new BorderLayout()), 0, 240, 600, 160);
		JPanel south_center = createComponent(new JPanel(null), 600, 140);

		southPanel.add(new JLabel("같은  카테고리 목록", 2), BorderLayout.NORTH);
		southPanel.add(south_center);

		ArrayList<ProductPanel> list = new ArrayList<ProductPanel>();
		try (var pst = connection.prepareStatement(
				"select * from product as p inner join category as c on c.c_no = p.c_no where c.c_name = ?")) {
			pst.setObject(1, category);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				list.add(new ProductPanel(100, 120, rs.getString(3), rs.getInt(4), rs.getString(6), rs.getInt(5),
						category));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (int i = 0; i < list.size(); i++) {
			south_center.add(createComponent(list.get(i), i * 100, 0, 100, 120));
		}

		new Thread(() -> {
			while (true) {
				try {
					Thread.sleep(1000);
					for (int i = 0; i < 100; i++) {
						for (int j = 0; j < list.size(); j++) {
							list.get(j).setLocation(list.get(j).location().x - 1, 0);
							if (list.get(j).getLocation().x <= -100) {
								list.get(j).setLocation(list.size() * 100 - 100, 0);
							}
						}
						Thread.sleep(3);
					}
				} catch (InterruptedException e1) {
					e1.printStackTrace();
					System.exit(0);
				}

			}
		}).start();

		add(southPanel);
	}

	public void buyProduct() {
		try {
			Integer.parseInt(tfCount.getText());
		} catch (Exception e) {
			errorMessage("숫자로 입력해주세요.", "Error");
			return;
		}

		int yesNo = JOptionPane.showConfirmDialog(null, "총 가격이 " + tfPrice.getText() + "원 입니다.\n결제하시겠습니까?", "결제",
				JOptionPane.YES_NO_OPTION);
		if (yesNo != 1) {

		}

		try (var pst = connection.prepareStatement("")) {
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
