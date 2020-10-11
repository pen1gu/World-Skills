package frame;

import javax.swing.*;
import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PopularFrame extends BaseFrame {

    Color[] colors = {Color.red, Color.orange, Color.yellow, Color.green, Color.pink};
    JComboBox<String> cbProduct = new JComboBox<>();

    PopularFrame() {
        super("", 400, 500);
        JPanel northpanel = new JPanel();
        northpanel.add(createLabel(new JLabel("Best 상품", 0), new Font("맑은고딕", 1, 30)));
        northpanel.add(cbProduct);

        for (String text : new String[]{"정육", "과일", "채소", "해산물", "가공식품", "유제품", "생활용품", "주방용품"}) {
            cbProduct.addItem(text);
        }

        add(new DrawPanel());
        cbProduct.addActionListener(e -> new DrawPanel());

        add(northpanel, BorderLayout.NORTH);
    }

    public static void main(String[] args) {
        new PopularFrame().setVisible(true);
    }

    private class DrawPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setStroke(new BasicStroke(2));
            g2d.setFont(new Font("돋움", 1, 11));
            g2d.drawLine(0, 360, 400, 360);

            try (PreparedStatement pst = connection.prepareStatement("select p_name,sum(pu_count) from purchase as pu inner join product as p on pu.p_no = p.p_no where c_no = ? group by p.p_no order by pu_count desc limit 5")) {
                pst.setObject(1, cbProduct.getSelectedIndex() + 1);

                ResultSet rs = pst.executeQuery();

                int height, cnt = 0, max = -87654321;
                while (rs.next()) {
                    if (max < rs.getInt(2)) {
                        max = rs.getInt(2);
                    }
                    height = (int) (float) (max / rs.getInt(2)) * 410;

                    g2d.setColor(Color.black);
                    g2d.drawString(rs.getString(2) + "개", cnt * 70 + 30, 450 - height);

                    g2d.setColor(colors[cnt]);
                    g2d.drawRect(cnt * 70 + 30, (450 - height), 30, (450 - (450 - height)));
                    g2d.fillRect(cnt * 70 + 30, (450 - height), 30, (450 - (450 - height)));

                    cnt++;
                }

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }
    }
}
