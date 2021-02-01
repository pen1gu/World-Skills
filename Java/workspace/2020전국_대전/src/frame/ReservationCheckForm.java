package frame;

public class ReservationCheckForm extends BaseFrame {

	public ReservationCheckForm() {
		super(600, 800, "예약확인");
		for (String lbTxt : new String[] {"웨딩홀명","주소","수용인원","홀사용료","예식형태","식사종류","식사비용"}) {

		}
	}

	public static void main(String[] args) {
		new ReservationCheckForm().setVisible(true);
	}
}
