package frame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ReservationStatusFrame extends BaseFrame {

	DefaultTableModel model = new DefaultTableModel("n,진료과,의사,진료날짜,시간".split(","), 0);
	JTable tb = new JTable(model);

	public ReservationStatusFrame() {
		super(500, 400, "진료예약현황", 2);
		add(setComp(setlabel(new JLabel(userName + "님 진료예약현황", 0), new Font("굴림", 1, 18)), 0, 40), BorderLayout.NORTH);
		var cp = new JPanel(new FlowLayout());
		var sp = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		var jsc = setComp(new JScrollPane(tb), 470, 270);

		tb.getColumn("진료날짜").setPreferredWidth(150);

		tb.removeColumn(tb.getColumn("n"));

		for (int i = 0; i < 4; i++) {
			tb.getColumnModel().getColumn(i).setCellRenderer(crend);
		}
		
		try (var pst = CM.con.prepareStatement(
				"select r_no,d.d_section, d.d_name,r.r_date, r.r_time from reservation as r inner join doctor as d on r.d_no = d.d_no where p_no = ? and date(r_date) >= curdate() and time(r_time) >= curtime();")) {
			pst.setObject(1, userNo);
			var rs = pst.executeQuery();
			while (rs.next()) {
				model.addRow(new Object[] { rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDate(4),
						rs.getString(5) });
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		sp.add(setBtn("예약취소", this::clickDelete));
		sp.add(setBtn("닫기", e -> openFrame(new MainFrame())));
		cp.add(jsc);
		add(sp, BorderLayout.SOUTH);
		add(cp);
	}

	private void clickDelete(ActionEvent e) {
		int row = tb.getSelectedRow();

		if (row == -1) {
			eMsg("삭제할 메뉴를 선택해주세요.");
			return;
		}

		try {
			CM.stmt.execute("DELETE FROM reservation WHERE r_no = " + model.getValueAt(row, 0));
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		model.removeRow(row);
		iMsg("취소되었습니다.");
	}
}
