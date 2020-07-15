package frame;

import java.awt.GridLayout;

public class MainFrame extends BaseFrame {
    public MainFrame() {
        super(300, 300, "����");
        setLayout(new GridLayout(0, 1));

        add(createButton("승차권 예약", e -> openFrame(new ReservationFrame())));
        add(createButton("승차권 취소", e -> openFrame(new ReservationCancelFrame())));
        add(createButton("로그아웃", e -> openFrame(new LoginFrame())));
        add(createButton("종료", e -> dispose()));
    }

    public static void main(String[] args) {
        new MainFrame().setVisible(true);
    }
}
