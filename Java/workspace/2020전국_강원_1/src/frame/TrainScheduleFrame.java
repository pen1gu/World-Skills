package frame;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

public class TrainScheduleFrame extends BaseFrame {

    static int width = 800, height = 600;
    ReservationFrame reservationFrame;
    JPanel centerpanel = new JPanel(new GridLayout(0, 1));
    static final Logger LOG = Logger.getGlobal();
    TrainScheduleFrame(ReservationFrame reservationFrame) {
        super(width, height, "열차 시간표");
        this.reservationFrame = reservationFrame;

        add(createLabel(new JLabel("● " + reservationFrame.cbStartStation.getSelectedItem() + " ▶ " + reservationFrame.cbArriveStation.getSelectedItem() + " (" + reservationFrame.tfDate.getText() + ")"), new Font("굴림", 1, 20)), BorderLayout.NORTH);
        JPanel centerpanel_top = createComponenet(new JPanel(new GridLayout(1, 0)), width, 40);
        JScrollPane jScrollPane = createComponenet(new JScrollPane(centerpanel), width, height - 100);

        String[] list = "열차,출발역,도착역,소요시간,운행시간,운임요금,일반실,특실".split(",");
        for (int i = 0; i < list.length; i++) {
            JLabel label;
            centerpanel_top.add(label = new JLabel(list[i], 0));
            label.setBackground(Color.lightGray);
            label.setOpaque(true);
            label.setBorder(new LineBorder(Color.black));
        }

        try (PreparedStatement pst = connection.prepareStatement("select t1.station_no, t2.station_no, t1.train, s1.name as dep, t1.time, s2.name as arrv, t2.time from timetable t1 inner join station s1 on t1.station_no=s1.no, timetable t2 inner join station s2 on s2.no = t2.station_no where t1.train=t2.train and t1.station_no <> t2.station_no and s1.name = ? and s2.name = ? and t1.time > curtime();")) {
            pst.setObject(1, reservationFrame.cbStartStation.getSelectedItem());
            pst.setObject(2, reservationFrame.cbArriveStation.getSelectedItem());
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                jScrollPane.add(new TrainDataColumn());
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        jScrollPane.setBorder(new LineBorder(Color.blue));
        add(centerpanel_top, BorderLayout.CENTER);

        add(jScrollPane, BorderLayout.SOUTH);
    }

    private class TrainDataColumn extends JPanel {
        int seatNo = 0;

        TrainDataColumn() {
            setLayout(new GridLayout(1, 0));
            setSize(width, 60);
            setStationColumn(reservationFrame.cbStartStation.getSelectedIndex() + 1);
            setStationColumn(reservationFrame.cbArriveStation.getSelectedIndex() + 1);
            add(createButton("보기", e -> openFrame(new RaceTimeInqueryFrame())));
            add(createButton("보기", e -> openFrame(new FaresInqueryFrame())));
            add(createButton("좌석선택", e -> openFrame(new SeatSelectionFrame())));
            add(createButton("좌석선택", e -> openFrame(new SeatSelectionFrame())));
        }

        public void setStationColumn(int station) {
            try (PreparedStatement pst = connection.prepareStatement("select * from station as s1 inner join timetable as t1 on s1.no = t1.station_no where s1.no = ?;")) {
                pst.setObject(1, station);
                ResultSet rs = pst.executeQuery();
                while (rs.next()) {
//                    LOG.severe(rs.getInt(3)+"");
                    seatNo = rs.getInt(3);
                    add(new JLabel(rs.getString(5)));
                    add(new JLabel(rs.getString(2) + "\n" + rs.getString(7), 0));
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
