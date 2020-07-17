package frame;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.logging.Logger;

public class ReservationFrame extends BaseFrame {

    JComboBox<String> cbStartStation = new JComboBox<String>();
    JComboBox<String> cbArriveStation = new JComboBox<String>();

    JComboBox<Integer>[] cbPeople_info = new JComboBox[3];

    JTextField tfDate = createComponenet(new JTextField(), 140, 30);
    HashMap<String, Integer> key = new HashMap<>();

    static int width = 600, height = 330;
    private final static Logger log = Logger.getGlobal();

    public ReservationFrame() {
        super(width, height, "승차권 예약");
        setLayout(new FlowLayout());

        JPanel wrappanel = createComponenet(new JPanel(new BorderLayout()), width, height);
        JPanel northpanel = createComponenet(new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 10)), width, height - 160);
        JPanel centerpanel = createComponenet(new JPanel(new FlowLayout(FlowLayout.LEFT)), width, 60);
        JPanel center_inner = createComponenet(new JPanel(new GridLayout(2, 1, 10, 0)), width - 100, 60);

        wrappanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 10));

        northpanel.add(createComponenet(new JLabel("출발역"), 60, 20));
        northpanel.add(createComponenet(cbStartStation, 200, 30));
        northpanel.add(createComponenet(new JLabel("도착역", 0), 60, 50));
        northpanel.add(createComponenet(cbArriveStation, 200, 30));
        northpanel.add(createComponenet(new JLabel("날짜"), 60, 50));
        northpanel.add(tfDate);
        tfDate.setEditable(false);
        northpanel.add(createButton("달력", e -> openFrame(new CalendarFrame(ReservationFrame.this))));

        centerpanel.add(createComponenet(new JLabel("인원정보"), 60, 20));

        center_inner.add(createComponenet(new JLabel("어른 (만 13세 이상)", 0), 140, 20));
        center_inner.add(createComponenet(new JLabel("어린이 (만 6~12세)", 0), 140, 20));
        center_inner.add(createComponenet(new JLabel("경로 (만 65세 이상)", 0), 140, 20));

        for (int i = 0; i < cbPeople_info.length; i++) {
            center_inner.add(createComponenet(cbPeople_info[i] = new JComboBox<Integer>(), 140, 30));
        }

        for (int i = 0; i <= 9; i++) {
            for (int j = 0; j < 3; j++) {
                cbPeople_info[j].addItem(i);
            }
        }

        centerpanel.add(center_inner);
        wrappanel.add(northpanel, BorderLayout.NORTH);
        wrappanel.add(centerpanel);
        centerpanel.add(createComponenet(createButton("조회하기", e -> clickInquiry()), width, 30), BorderLayout.SOUTH);
        add(wrappanel);
        setStationData();
    }

    public void clickInquiry() {
        if (cbStartStation.getSelectedIndex() == cbArriveStation.getSelectedIndex()) {
            errorMessage("출발역과 도착역이 동일합니다.");
            return;
        }

        if (tfDate.getText().isEmpty()) {
            errorMessage("날짜를 선택해주세요.");
            return;
        }

        int cnt = 0;
        for (int i = 0; i < cbPeople_info.length; i++) {
            if (cbPeople_info[i].getSelectedIndex() == 0) {
                cnt++;
                log.severe(cnt + "");
            }
        }

        if (cnt == 3) {
            errorMessage("승객 인원을 1명 이상 선택해주세요.");
            return;
        }

        openFrame(new TrainScheduleFrame(ReservationFrame.this));
    }

    public void setStationData() {
        try (ResultSet rs = statement.executeQuery("select * from station")) {
            while (rs.next()) {
                cbStartStation.addItem(rs.getString(2));
                cbArriveStation.addItem(rs.getString(2));
                key.put(rs.getString(2), rs.getInt(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new ReservationFrame().setVisible(true);
    }
}
