package setting;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class OrderListFrame extends BaseFrame {
    OrderListFrame() {
        super(700, 350, "");

        add(createComponent(createLabel(new JLabel(userName + "회원님의 구매내역", 0), new Font("굴림", Font.BOLD, 20)), 0, 40),
                BorderLayout.NORTH);

        DefaultTableModel model = new DefaultTableModel("구매일자,메뉴명,가격,사이즈,수량,총금액".split(","), 0);
        JTable table = new JTable(model);
        JScrollPane jScrollPane = new JScrollPane(table);
        int amount = 0;

        table.getColumn("메뉴명").setPreferredWidth(200);

        try (var pst = connection.prepareStatement("select orderlist.*, menu.m_name from orderlist "
                + "inner join menu on menu.m_no = orderlist.m_no " + "where u_no = " + userNo)) {

            var rs = pst.executeQuery();

            while (rs.next()) {
                model.addRow(new Object[]{rs.getString(2), rs.getString(10), String.format("%,d", rs.getInt(7)),
                        rs.getString(6), rs.getString(8), String.format("%,d", rs.getInt(9))});
                amount += rs.getInt(9);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        add(jScrollPane, BorderLayout.CENTER);

        JPanel southPanel = new JPanel(new FlowLayout());
        southPanel.add(new JLabel("총 결제 금액"));

        JTextField tfAmount = createComponent(new JTextField(String.format("%,d", amount)), 200, 25);

        tfAmount.setHorizontalAlignment(4);
        tfAmount.setEditable(false);

        southPanel.add(tfAmount);
        southPanel.add(createButton("닫기", e -> openFrame(new MainFrame())));

        add(southPanel, BorderLayout.SOUTH);

    }

    public static void main(String[] args) {
        new OrderListFrame().setVisible(true);
    }
}
