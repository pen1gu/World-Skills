package frame;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public class MainFrame extends BaseFrame {
    JTextField tfName = new JTextField(10);
    JTextField tfMaxPrice = new JTextField(10);
    JTextField tfMinPrice = new JTextField(10);
    String current_category = "채소";

    Map<String, Integer> category_num_map = new HashMap<>();

    static int width = 900, height = 600;
    JPanel inner_scroll = createComponent(new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0)), 590, 400);

    DefaultTableModel model = new DefaultTableModel("상품번호,상품 카테고리,상품 이름,상품 가격,상품 재고,상품 설명".split(","), 0);
    JTable tb_product_data = createComponent(new JTable(model),width-250,140);

    MainFrame() {
        super("상품목록", width, height);
        JPanel northpanel = createComponent(new JPanel(new FlowLayout(FlowLayout.LEFT)), width, 30);
        JPanel westpanel = createComponent(new JPanel(null), 220, height);

        JPanel west_inner_north = new JPanel(new FlowLayout());
        JPanel west_inner_center = new JPanel(new GridLayout(0, 1));
        JPanel west_inner_south = new JPanel(new FlowLayout());

        JPanel centerpanel = createComponent(new JPanel(new BorderLayout()), width - 150, 570);
        JPanel center_south = createComponent(new JPanel(), width - 150, 140);

        northpanel.add(createLabel(new JLabel("유저 : " + userName, 2), new Font("Gothic", 1, 20)));

        //west panel ui
        westpanel.add(createComponent(west_inner_north, 20, 20, 200, 120));
        westpanel.add(createComponent(west_inner_center, 0, 130, 200, 360));
        westpanel.add(createComponent(west_inner_south, 0, 490, 200, 30));

        west_inner_north.add(createComponent(new JLabel("이름", 2), 70, 20));
        west_inner_north.add(tfName);
        west_inner_north.add(createComponent(new JLabel("최대 가격", 2), 70, 20));
        west_inner_north.add(tfMaxPrice);
        west_inner_north.add(createComponent(new JLabel("최저 가격", 2), 70, 20));
        west_inner_north.add(tfMinPrice);
        west_inner_north.add(createComponent(createButton("검색", e -> clickSearch()), 190, 35));

        west_inner_center.add(createLabel(new JLabel("카테고리"), new Font("Gothic", 1, 22)));

        west_inner_south.add(createComponent(createButtonWithOutMargin("인기상품", e -> openFrame(new PopularFrame())), 80, 25));
        west_inner_south.add(createComponent(createButtonWithOutMargin("구매목록", e -> openFrame(new OrderListFrame())), 80, 25));


        int i = 1;
        for (String text : new String[]{"정육", "과일", "채소", "해산물", "가공식품", "유제품", "생활용품", "주방용품"}) {
            JLabel label;
            west_inner_center.add(label = new JLabel(text, 2));
            category_num_map.put(text, i);
            addMouseListener(label);
            i++;
        }
        // centerpanel ui

        JScrollPane jScrollPane = createComponent(new JScrollPane(inner_scroll), 620, 410);
        centerpanel.add(jScrollPane);
        centerpanel.add(center_south, BorderLayout.SOUTH);

        JScrollPane tb_scroll = createComponent(new JScrollPane(tb_product_data), width-240,140);
        center_south.add(tb_scroll);

        add(centerpanel);
        add(westpanel, BorderLayout.WEST);
        add(northpanel, BorderLayout.NORTH);
        clickSearch();
    }

    public void addMouseListener(JLabel label) {
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                current_category = label.getText();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                label.setForeground(Color.red);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                label.setForeground(Color.black);
            }
        });
    }

    public void clickSearch() {
        inner_scroll.removeAll();

        try (PreparedStatement pst = connection.prepareStatement("select * from product where c_no = ? and p_price >= ? and p_price <= ?")) {
            pst.setObject(1, category_num_map.get(current_category));
            pst.setObject(2, !tfMinPrice.getText().isEmpty() ? tfMinPrice.getText() : 0);
            pst.setObject(3, !tfMaxPrice.getText().isEmpty() ? tfMaxPrice.getText() : Integer.MAX_VALUE);

            ResultSet rs = pst.executeQuery();

            int cnt = 0;
            Object[] objects;
            while (rs.next()) {
                inner_scroll.add(new ProductPanel(rs.getString(3)));
                model.addRow(new Object[]{
                        rs.getObject(1), rs.getObject(2), rs.getObject(3), rs.getObject(4), rs.getObject(5), rs.getObject(6)
                });
                cnt++;
            }
            int rows = cnt / 3;

            if (cnt % 3 != 0) {
                rows++;
            }


            inner_scroll.setPreferredSize(new Dimension(610, rows * 200));
            inner_scroll.revalidate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new MainFrame().setVisible(true);
    }

    class ProductPanel extends JPanel {
        ProductPanel(String name) {
            setLayout(new BorderLayout());
            setPreferredSize(new Dimension(200, 190));
            add(createComponent(new JLabel(getImage("./datafiles/이미지폴더/" + name + ".jpg", 200, 170)), 200, 170));
            add(createComponent(new JLabel(name, 0), 200, 20), BorderLayout.SOUTH);
            setBorder(new LineBorder(Color.black));
        }

        public void clickImage() {

        }
    }

    public void changeTable() {
        try (PreparedStatement pst = connection.prepareStatement("select * from product where ")) {
//            pst.setObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
