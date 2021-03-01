package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class CalendarForm extends BaseFrame {
	int curMonth = 0;
	int curYear = 0;
	JLabel curDate = new JLabel();
	JPanel cp = new JPanel(new GridLayout(7, 7));

	Calendar cal = Calendar.getInstance();
	JButton lb = setBtn("◀", this::change);
	JButton rb = setBtn("▶", this::change);

	ReservationFrame rf;
	String[] str = "일,월,화,수,목,금,토".split(",");

	public CalendarForm(ReservationFrame rf) {
		super(300, 350, "기간선택", 2);
		this.rf = rf;

		setLayout(new BorderLayout());
		var np = new JPanel(new FlowLayout());
		np.add(lb);
		np.add(curDate);
		np.add(rb);

		curYear = cal.get(Calendar.YEAR);
		curMonth = cal.get(Calendar.MONTH) + 1;

		add(np, BorderLayout.NORTH);
		add(cp, BorderLayout.CENTER);
		
		changeDate();
	}

	public void changeDate() {
		cp.removeAll();
		curDate.setText(String.format("%d년 %d월", curYear, curMonth));
		cal.set(curYear, curMonth - 1, 1);
		for (int i = 0; i < str.length; i++) {
			JLabel jl;
			cp.add(jl = new JLabel(str[i], 0));
			if (i == 0) {
				jl.setForeground(Color.red);
			}
			if (i == 6) {
				jl.setForeground(Color.blue);
			}
		}

		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		int cnt = 0;
		for (int i = 0; i < 42; i++) {
			if (i < w) {
				cp.add(new JLabel(" "));
			} else if (i < (cal.getActualMaximum(Calendar.DAY_OF_MONTH)) + w) {
				JButton btn;
				SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
				cnt = (i + 1) - w;
				int num = cnt;
				String ps = String.format("%d-%02d-%02d", curYear, curMonth, cnt);
				if (i % 7 == 6) {
					cp.add(btn = setBtnM("" + cnt, e -> clickSubmit(num, ps), Color.blue));
				} else if (i % 7 == 0) {
					cp.add(btn = setBtnM("" + cnt, e -> clickSubmit(num, ps), Color.red));
				} else
					cp.add(btn = setBtnM(cnt + "", e -> clickSubmit(num, ps), Color.black));
				btn.setBorder(new LineBorder(Color.black));

				try {
					Date day1 = sim.parse(ps);
					Date day2 = sim.parse(sim.format(new Date()));
					int compare = day1.compareTo(day2);
					if (compare < 0) {
						btn.setEnabled(false);
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
			} else if (cnt >= w) {
				cp.add(new JLabel(" "));
			}
		}
		repaint();
		revalidate();
	}

	public void change(ActionEvent e) {
		if (e.getSource() == lb) {
			curMonth--;
			if (curMonth < 1) {
				curYear--;
				curMonth = 12;
			}
		}
		if (e.getSource() == rb) {
			curMonth++;
			if (curMonth > 12) {
				curYear++;
				curMonth = 1;
			}
		}
		changeDate();
	}

	public void clickSubmit(int i, String ps) {
		try {
			cal.set(curYear, curMonth - 1, i - 1);
			ResultSet rs = CM.stmt
					.executeQuery("select d_day from doctor where d_name = '" + rf.cb[1].getSelectedItem()
							+ "' and d_day = '" + str[(cal.get(Calendar.DAY_OF_WEEK) % 7)] + "';");
			if (rs.next()) {
				rf.tf[2].setText(ps);
				rf.setTable();
				dispose();
				rf.setVisible(true);
			} else
				eMsg("해당날짜에 해당 교수님 진료는 없습니다.");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

}