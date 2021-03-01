package frame;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class BedroomFrame extends BaseFrame {
	int house = 0;
	public BedroomFrame(int house) {
		super(300, 200, "Ä§´ë", 2);
		this.house = house;
		var cp = new JPanel(new GridLayout(1, 0));
		var sp = setComp(new JPanel(new GridLayout(1, 0)), 0, 100);

		add(setComp(setlabel(new JLabel(house + "È£", 0), new Font("¸¼Àº°íµñ", 1, 20)), 280, 40), BorderLayout.NORTH);
		add(cp, BorderLayout.CENTER);
		add(sp, BorderLayout.SOUTH);

		JButton[] btn = new JButton[house / 100];
		for (int i = 1; i <= (house / 100); i++) {
			int num=i;
			cp.add(new JLabel(i + "", 0));
			sp.add(btn[i - 1] = setBtn("", e -> clickButton(num)));
			
			addWindowListener(new WindowAdapter() {
				public void windowClosed(WindowEvent e) {
					new AdmissionFrame().setVisible(true);
				};
			});
		}

		try (var pst = CM.con.prepareStatement(
				"select * from hospitalization as h inner join sickroom as s on h.s_no = s.s_no where s.s_room = ? and date(h_sday) <= curdate() and date(h_fday) >= curdate();")) {
			pst.setObject(1, house);
			var rs = pst.executeQuery();
			
			while (rs.next()) {
				String h = rs.getString(12);
				List<String> list = Arrays.asList(h.split(","));
				for (int i = 0; i < list.size(); i++) {
					if (list.get(i).equals(rs.getString(4))) {
						btn[i].setEnabled(false);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void clickButton(int num) {
		openFrame(new AdmissionFrame());
		AdmissionFrame.tfRoom.setText(house + "");
		AdmissionFrame.tfNo.setText(num + "");
	}
}
