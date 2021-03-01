package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

public class ReservationFrame extends BaseFrame {

	final static int w = 500;
	final static int h = 500;

	JTextField[] tf = new JTextField[4];
	JComboBox<String>[] cb = new JComboBox[2];
	JComboBox<String> cbExam = new JComboBox<String>("선택안함,CT검사,MRI검사,UBT검사,X-RAY검사,초음파검사".split(","));

	DefaultTableModel model = new DefaultTableModel("진료과,의사,진료날짜,시간,시간대".split(","), 0);
	JTable tb = new JTable(model);
	JScrollPane jsc = setComp(new JScrollPane(tb), w - 50, 200);

	ReservationFrame rf;
	List<String> arr = new ArrayList<String>();

	JLabel lbImg = setComp(new JLabel(), 100, 100);

	public ReservationFrame() {
		super(w, h, "진료예약", 2);
		rf = this;
		var np = setComp(new JPanel(new GridLayout(3, 4)), w, 90);
		var cp = new JPanel(new FlowLayout());
		var cp_s = setComp(new JPanel(new FlowLayout(FlowLayout.LEFT)), w, 105);
		var sp = new JPanel(new FlowLayout(FlowLayout.RIGHT));

		String[] list = "환자번호,환자명,진료과,의사,날짜,진료이력".split(",");

		for (int i = 0; i < 6; i++) {
			np.add(setlabel(new JLabel(list[i], 0), new Font("굴림", 1, 11)));
			if (i < 2) {// i가 2보다 작을 때
				np.add(tf[i] = new JTextField());
				tf[i].setHorizontalAlignment((int) CENTER_ALIGNMENT);
				tf[i].setEnabled(false);
			} else if (i < 4) {// i가 4보다 작을 때
				np.add(cb[i - 2] = new JComboBox());
			} else {// i가 4보다 클 때
				np.add(tf[i - 2] = new JTextField());
				tf[i - 2].setHorizontalAlignment((int) CENTER_ALIGNMENT);
				tf[i - 2].setEnabled(false);
			}
		}

		cb[0].addActionListener(e -> changeDoctor());

		tf[0].setText(userNo + "");
		tf[1].setText(userName);

		String[] dept = "내과,정형외과,안과,치과".split(",");
		for (int i = 0; i < dept.length; i++) {
			cb[0].addItem(dept[i]);
		}

		tf[2].addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				super.mousePressed(e);
				new CalendarForm(rf).setVisible(true);
				rf.setVisible(false);
			}
		});

		tb.getColumn("진료과").setPreferredWidth(30);
		tb.getColumn("시간대").setPreferredWidth(30);
		jsc.setBorder(new LineBorder(Color.black));
		lbImg.setBorder(new LineBorder(Color.black));

		cbExam.addActionListener(e -> changeImg());

		cp.add(jsc);
		cp.add(cp_s);
		cp_s.add(setComp(new JLabel("검사", 4), 40, 20));
		cp_s.add(cbExam);
		cp_s.add(lbImg);
		sp.add(setBtn("예약", e -> getDate()));
		sp.add(setBtn("닫기", e -> openFrame(new MainFrame())));
		
		add(np, BorderLayout.NORTH);
		add(cp, BorderLayout.CENTER);
		add(sp, BorderLayout.SOUTH);

		changeDoctor();
		checkUp();
		setTable();
		changeImg();
	}

	public static void main(String[] args) {
		new ReservationFrame().setVisible(true);
	}

	public void setTable() {
		model.setRowCount(0);

		if (tf[2].getText().isEmpty()) {
			return;
		}
		String str[] = "일,월,화,수,목,금,토".split(",");
		String[] split = tf[2].getText().split("-");

		Calendar cal = Calendar.getInstance();
		cal.set(toInt(split[0]), toInt(split[1]) - 1, toInt(split[2]));
		int week = cal.get(Calendar.DAY_OF_WEEK) % 7;
		try (ResultSet rs = CM.stmt.executeQuery("select * from doctor where d_name ='" + cb[1].getSelectedItem()
				+ "' and d_day = '" + str[week - 1] + "'")) {
			arr.clear();
			String[] str2 = new String[7];
			while (rs.next()) {
				if (rs.getString(5).equals("오전")) {
					str2 = "9:00,9:30,10:00,10:30,11:00,11:30,12:00".split(",");
				} else if (rs.getString(5).equals("오후")) {
					str2 = "14:00,14:30,15:00,15:30,16:00,16:30,17:00".split(",");
				}

				for (int j = 0; j < 7; j++) {
					arr.add(rs.getString(1));
					model.addRow(new Object[] { rs.getString(2), rs.getString(3), tf[2].getText(), str2[j],
							rs.getString(5) });
				}
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	public void getDate() {// 예약할 시간을 선택하는 엄청난 메서드
		if (tb.getSelectedRow() == -1) {
			eMsg("예약할 시간을 선택해주세요.");
			return;
		}
		if (getResult(" r_section = '" + cb[0].getSelectedItem() + "' and p_no = '" + userNo + "'",
				"같은날짜에 같은 과 진료를 볼 수 없습니다.") == false) {
			return;
		}
		if (getResult(" r_time = '" + tb.getValueAt(tb.getSelectedRow(), 3) + "' and p_no = '" + userNo + "'",
				"같은날짜에 같은 시간 진료를 볼 수 없습니다.") == false) {
			return;
		}
		if (getResult(" d_no ='" + arr.get(tb.getSelectedRow()) + "' and r_time ='"
				+ tb.getValueAt(tb.getSelectedRow(), 3) + "'", "이미 예약되어있는 시간대입니다.") == false) {
			return;
		}
		try {
			CM.stmt.execute("insert into reservation values(0,'" + userNo + "','" + arr.get(tb.getSelectedRow()) + "','"
					+ cb[0].getSelectedItem() + "','" + tf[2].getText() + "','" + tb.getValueAt(tb.getSelectedRow(), 3)
					+ "','" + (cbExam.getSelectedIndex() + 1) + "')");
		} catch (Exception e) {
			e.printStackTrace();
		}
		iMsg("예약되었습니다.");
//		openFrame(new MainFrame());
	}

	public void checkUp() { // 진료를 했는지 안했는지를 알아보는 엄청난 메서드
		try (var pst = CM.con.prepareStatement(
				"select  * from reservation where p_no = ? and date(r_date) <= curdate() and time(r_time) <= curtime();")) {
			pst.setObject(1, userNo);
			var rs = pst.executeQuery();
			if (rs.next()) {
				tf[3].setText("재진");
			} else {
				tf[3].setText("초진");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean getResult(String sql, String err) { // 쿼리 재사용성을 고려한 엄청난 메서드
		try (var rs = CM.stmt
				.executeQuery("select * from reservation where r_date = '" + tf[2].getText() + "' and " + sql)) {
			if (rs.next()) {
				eMsg(err);
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	public void changeDoctor() {
		cb[1].removeAllItems();
		try (var pst = CM.con.prepareStatement("select * from doctor where d_section = ? group by d_name;")) {
			pst.setObject(1, cb[0].getSelectedItem());
			var rs = pst.executeQuery();
			while (rs.next()) {
				cb[1].addItem(rs.getString(3));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void changeImg() {
		lbImg.setIcon(getImage("./Datafiles/이미지/" + cbExam.getSelectedItem() + ".jpg", 90, 90));
	}
}
