package frame;

import javax.sound.sampled.Line;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class BuyListForm extends BaseFrame {

    static int width = 800, height = 600;
    JPanel inner_scroll_panel = createComponent(new JPanel(), width - 30, height - 50);

    BuyListForm() {
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

        try (PreparedStatement pst = connection.prepareStatement("select * from purchase as pu inner join product as p on pu.p_no = p.p_no where u_no = ?;")) {
            pst.setObject(1, userNo);

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                JPanel jp;
                inner_scroll_panel.add(jp = createComponent(new JPanel(new GridLayout(0, 1)), width, 40));
                jp.setBorder(new LineBorder(Color.black));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        add(jScrollPane);
        add(southpanel, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        new BuyListForm().setVisible(true);
    }
}
