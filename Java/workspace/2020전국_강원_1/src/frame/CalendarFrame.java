package frame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Calendar;

public class CalendarFrame extends BaseFrame {
    ReservationFrame reservationFrame;
    JButton leftButton = createButton("◀", this::changeDate);
    JButton rightButton = createButton("▶", this::changeDate);

    int year, month = 0;
    JPanel centerpanel = new JPanel(new GridLayout(7,7));
    JLabel lbDate = new JLabel();

	String[] str = "일,월,화,수,목,금,토".split(",");

    public CalendarFrame(ReservationFrame reservationFrame) {
        super(300, 320, "기간선택");
		Calendar calendar = Calendar.getInstance();
		year = calendar.get(Calendar.YEAR);
		month = calendar.get(Calendar.MONTH)+1;

		JPanel northpanel = new JPanel(new FlowLayout());

		northpanel.add(leftButton);
		northpanel.add(lbDate);
		northpanel.add(rightButton);

		add(northpanel,BorderLayout.NORTH);
		setCalendar();
    }

    public void changeDate(ActionEvent e) {
		lbDate.removeAll();
        if (e.getSource() == leftButton) {
        	month--;
            if (month < 1) {
                year--;
                month = 12;
            }
        } else if (e.getSource() == rightButton) {
        	month++;
            if (month > 12) {
                year++;
                month = 1;
            }
        }
        setCalendar();
    }

    public void setCalendar(){
		lbDate.setText(year +"년"+month+"월");
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month-1, 1);
		for (int i = 0; i < 7; i++) {
			JLabel label;
			centerpanel.add(label = new JLabel(str[i]));
			if (i == 0) label.setForeground(Color.red);
			else if (i == 6) label.setForeground(Color.blue);
		}
		int week = calendar.get(Calendar.DAY_OF_WEEK);

		for (int i = 0; i < 42; i++) {
			if (i < week){
				centerpanel.add(new JLabel());
			}else if (i < calendar.getActualMaximum(Calendar.DAY_OF_MONTH) + week){
				JButton button;
				int cnt = i + 1- week;
				String date = String.format("%d-%02d-%02d",year, month,cnt);
				if (i % 7 == 0) button.setForeground(Color.red);
				else if(i % 7 ==6) button.setForeground(Color.blue);

				centerpanel.add(button = createButton(i+"", e->clickSubmit()));
			}
		}
	}

	public void clickSubmit(){

	}
}
