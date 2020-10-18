package frame;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class NewsFrame extends BaseFrame {

    static int width = 400, height = 500;
    JLabel lbNo = createComponent(new JLabel("순번", 0), 60, 20);
    JLabel lbTitle = createComponent(new JLabel("제목", 0), 160, 20);
    JLabel lbDate = createComponent(new JLabel("날짜", 0), 80, 20);

    NewsFrame() {
        super(width, height, "학교소식");
        add(createComponent(createLabel(new JLabel("학교소식", 0), new Font("맑은고딕", 1, 28)), width, 40), BorderLayout.NORTH);
        JPanel centerpanel = new JPanel(new BorderLayout());
        JPanel centerpanel_south = new JPanel();

        lbNo.setBorder(new LineBorder(Color.black));
        lbNo.setBackground(Color.white);

        centerpanel.add(lbNo);
        centerpanel.add(lbTitle);
        centerpanel.add(lbDate);

        JPanel scrollpanel = createComponent(new JPanel(new FlowLayout()), width, height - 200);
        JScrollPane jsc = createComponent(new JScrollPane(scrollpanel), width, height - 200);



    }

    public void clickSearch(){
        try (PreparedStatement pst = connection.prepareStatement("select * from news")){

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private class NewsColumn extends JPanel{

    }

    public static void main(String[] args) {
        new NewsFrame().setVisible(true);
    }
}
