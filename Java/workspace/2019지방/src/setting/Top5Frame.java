package setting;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

public class Top5Frame extends BaseFrame {
    static Color[] colorList = {Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.CYAN};
    JComboBox<String> cbGroup = new JComboBox<String>("음료,푸드,상품".split(","));

    class ChartCanvas extends JPanel {

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;

            g2d.setStroke(new BasicStroke(2));
            g2d.setFont(new Font("굴림", 1, 11));
            g2d.drawLine(70, 40, 70, 380);

            try (var rs = statement.executeQuery("SELECT menu.m_name, SUM(o_count) AS c FROM menu "
                    + "INNER JOIN orderlist ON menu.m_no = orderlist.m_no "
                    + "WHERE menu.m_group = '" + cbGroup.getSelectedItem() + "' "
                    + "GROUP BY menu.m_no "
                    + "ORDER BY c DESC "
                    + "LIMIT 5")) {

                int y = 0;
                int max = 0;

                while (rs.next()) {
                    if (max < rs.getInt(2)) {
                        max = rs.getInt(2);
                    }

                    int w = (int) (((float) rs.getInt(2) / max) * 310);

                    g2d.setColor(colorList[y]);
                    g2d.fillRect(70, 70 + (y * 65), w, 30);

                    g2d.setColor(Color.BLACK);
                    g2d.drawRect(70, 70 + (y * 65), w, 30);

                    g2d.drawString(String.format("%s-%d개", rs.getString(1), rs.getInt(2)), 75, 115 + (y * 65));

                    y++;
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public Top5Frame() {
        super(440, 500, "인기상품 Top5");

        var nPnl = new JPanel();

        cbGroup.addActionListener(e -> repaint());

        nPnl.add(cbGroup);
        nPnl.add(createLabel(new JLabel("인기상품 Top5"), new Font("굴림", 1, 20)));
        nPnl.setBackground(Color.LIGHT_GRAY);

        add(nPnl, "North");
        add(new ChartCanvas());
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                openFrame(new MainFrame());
            }
        });
    }

    public static void main(String[] args) {
        new Top5Frame().setVisible(true);
    }
}
