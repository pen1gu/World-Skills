package frame;

import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JLabel;

public class MainFrame extends BaseFrame {
	public MainFrame() {
		super(300, 400, "", 2);

		setLayout(new GridLayout(0, 1));
		add(setlabel(new JLabel(userName + "환자", 0), new Font("굴림", 1, 25)));
		add(setBtn("진료예약", e -> openFrame(new ReservationFrame())));
		add(setBtn("입퇴원신청", e -> compare()));
		add(setBtn("진료예약현황", e -> openFrame(new ReservationStatusFrame())));
		add(setBtn("진료과별 분석", e -> openFrame(new AnalysisFrame())));
		add(setBtn("종료", e -> openFrame(new LoginFrame())));
	}

	public void compare() {
		try (var pst = CM.con.prepareStatement("select * from hospitalization where p_no = ? order by h_no desc limit 1;")) {
			pst.setObject(1, userNo);
			var rs = pst.executeQuery();
			if (rs.next()) {
				if (rs.getString(6).length() == 0) {
					openFrame(new BillFrame());
				} else {
					openFrame(new AdmissionFrame());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
