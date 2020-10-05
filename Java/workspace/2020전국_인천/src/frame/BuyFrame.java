package frame;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class BuyFrame extends BaseFrame {

    static int width = 600, height = 400;

    JTextField tfProductName = createComponent(new JTextField(), 230, 20, 150, 30);
    JTextField tfPrice = createComponent(new JTextField(), 230, 60, 150, 30);
    JTextField tfAmount = createComponent(new JTextField(), 230, 100, 150, 30);


    BuyFrame(String lb_path_name, String price, int cnt) {
        super("구매", width, height);
        JPanel centerpanel = createComponent(new JPanel(null), width, height - 80);
        JPanel southpanel = createComponent(new JPanel(), 60 * cnt, 80);

        JLabel lbImg = createComponent(new JLabel(getImage("./datafiles/이미지폴더/" + lb_path_name + ".jpg", 120, 120)), 10, 20, 120, 120);
        lbImg.setBorder(new LineBorder(Color.black));

        centerpanel.add(lbImg);
        centerpanel.add(createComponent(new JLabel("제품명", 0), 130, 20, 100, 30));
        centerpanel.add(createComponent(new JLabel("가격", 0), 130, 60, 100, 30));
        centerpanel.add(createComponent(new JLabel("수량", 0), 130, 100, 100, 30));

        tfProductName.setEnabled(false);
        tfProductName.setText(lb_path_name);

        tfPrice.setEnabled(false);
        tfPrice.setText(price);


        centerpanel.add(tfProductName);
        centerpanel.add(tfPrice);
        centerpanel.add(tfAmount);

        JLabel product_description = createComponent(new JLabel("상품 설명"), 10, 145, 80, 20);
        JTextArea taProduct = createComponent(new JTextArea(), 10, 165, 300, 100);

        taProduct.setBorder(new LineBorder(Color.black));

        centerpanel.add(createComponent(createButton("구매하기", e -> clickBuy()), 340, 235, 100, 30));
        centerpanel.add(createComponent(createButton("취소하기", e -> dispose()), 460, 235, 100, 30));
        centerpanel.add(product_description);
        centerpanel.add(taProduct);

        for (int i = 0; i < cnt; i++) {
            southpanel.add(new MainFrame.ProductPanel(product_list.get(i), 80, 60));
        }
        add(centerpanel);
    }

    public void clickBuy() {
    }
}
