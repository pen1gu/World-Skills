package frame;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class OrderListFrame extends BaseFrame {

    static int width = 800, height = 600;
    JPanel inner_scroll_panel = createComponent(new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0)), width - 30, height - 50);
    JLabel lbAmount = new JLabel();

    OrderListFrame() {
        super("구매리스트", width, height);
        JScrollPane jScrollPane = createComponent(new JScrollPane(inner_scroll_panel), width, height - 50);
        JPanel southpanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JPanel headerpanel = createComponent(new JPanel(new GridLayout(1, 0)), width - 30, 30);
        for (String text : new String[]{"상품번호", "상품명", "상품 가격", "주문 개수", "총 가격"}) {
            JLabel jl;
            headerpanel.add(jl = new JLabel(text, 0));
            jl.setBackground(Color.white);
            jl.setBorder(new LineBorder(Color.black));
        }
        inner_scroll_panel.add(headerpanel);

        try (PreparedStatement pst = connection.prepareStatement("select p.p_no,p_name,pu_price,pu_count from purchase as pu inner join product as p on pu.p_no = p.p_no where u_no = ?;")) {
            pst.setObject(1, userNo);

            ResultSet rs = pst.executeQuery();
            int cnt = 0;
            while (rs.next()) {
                int amount = rs.getInt(3) * 3;
                JPanel jp;
                inner_scroll_panel.add(jp = createComponent(new JPanel(new GridLayout(1, 0)), width - 35, 40));
                for (int i = 0; i < 4; i++) {
                    jp.add(new JLabel(rs.getObject(i + 1) + "", 0));
                }
                jp.add(new JLabel(String.format("%2d", amount),0));
                jp.setBorder(new LineBorder(Color.black));
                jp.setBackground(Color.white);
                cnt++;
            }

            inner_scroll_panel.setPreferredSize(new Dimension(width - 35, cnt * 40));
        } catch (Exception e) {
            e.printStackTrace();
        }

        southpanel.add(createButton("확인",e -> openFrame(new MainFrame())));
        add(jScrollPane);
        add(southpanel, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        new OrderListFrame().setVisible(true);
    }
}
