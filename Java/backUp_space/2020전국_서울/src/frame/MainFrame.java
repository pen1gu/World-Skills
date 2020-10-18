package frame;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends BaseFrame {

    JLabel lbLoginStatus = new JLabel();
    JButton loginButton = createButton("로그인", e -> openFrame(new LoginFrame()));

    static final int WIDTH = 400, HEIGHT = 400;
    JButton[] btn = new JButton[4];
    JLabel lbMainImage = new JLabel();
    ImageIcon[] ImageIcon = new ImageIcon[3];

    AlphaComposite alphaComposite;

    MainFrame() {
        super(WIDTH, HEIGHT, "Main");
        setLayout(new BorderLayout());

        JPanel northpanel = new JPanel(new BorderLayout());
        JPanel northpanel_north = createComponent(new JPanel(new FlowLayout()), WIDTH, 50);
        JPanel northpanel_south = createComponent(new JPanel(null), WIDTH, 30);

        JPanel centerpanel = new JPanel(new BorderLayout());
        JPanel centerpanel_north = createComponent(new JPanel(new GridLayout(1, 0, 1, 0)), WIDTH, 30);
        JPanel centerpanel_center = new JPanel();

        northpanel_north.add(new JLabel(getImage(50, 50, ".//2과제 지급자료/image/학교.png")));
        northpanel_north.add(createLabel(new JLabel("학교알리미", 0), new Font("San Serif", 1, 24)));
        northpanel_south.add(createComponent(lbLoginStatus, 0, 0, 200, 30));
        northpanel_south.add(createComponent(loginButton, WIDTH - 100, 0, 70, 25));

        loginButton.setBackground(new Color(0, 125, 190));

        northpanel.add(northpanel_north, BorderLayout.NORTH);
        northpanel.add(northpanel_south, BorderLayout.SOUTH);

        centerpanel_north.add(btn[0] = createButton("소식", e -> openFrame(new NewsFrame())));
        centerpanel_north.add(btn[1] = createButton("회신", e -> openFrame(new ReplyFrame())));
        centerpanel_north.add(btn[2] = createButton("대나무숲", e -> openFrame(new BambooGrooveFrame())));
        centerpanel_north.add(btn[3] = createButton("종료", e -> dispose()));

        for (int i = 0; i < btn.length; i++) {
            btn[i].setBackground(new Color(0, 125, 190));
        }

        new Thread() {
            public void run() {
                while (true) {
                    try {
                        for (int i = 1; i <= 3; i++) {
                            sleep(2000);
                            ImageIcon[i] = getImage(WIDTH, HEIGHT - 160, ".//2과제 지급자료/image/main/" + i + ".jpg");
                        }
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        }.start();


        centerpanel.add(centerpanel_north, BorderLayout.NORTH);
        centerpanel.add(lbMainImage);

        add(northpanel, BorderLayout.NORTH);

        add(centerpanel);

        loginCheck();
    }

    public void loginCheck() {
        if (loginCheck == true) {
            lbLoginStatus.setText(userName + "님 환영합니다.");
        } else {
            lbLoginStatus.setText("로그인을 해주세요.");
        }
    }

    private class ImageAlpha {
        Image image;

        int x;
        int y;
        int width;
        int height;

        int alpha;
    }

    public static void main(String[] args) {
        new MainFrame().setVisible(true);
    }
}
