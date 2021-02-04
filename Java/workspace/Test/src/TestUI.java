import java.awt.BorderLayout;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

public class TestUI extends JFrame {

	DefaultTableModel model = new DefaultTableModel(
			new String[] { "좌측창가", "예약", "좌측통로", "예약", "통lo", "우측창가", "예약", "우측통로", "예약" }, 0);

	JTable table = new JTable(model);

	public TestUI() {
		setSize(500, 500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		JScrollPane scrollPane = new JScrollPane(table);

		ArrayList<Object> list = new ArrayList<Object>();
		list.add(1);
		list.add(8);
		list.add(11);
		list.add(13);
		list.add(15);
		list.add(20);

		int numCnt = 0, colCnt = 1;
		for (int i = 1; i <= 5; i++) {
			Object[] objects = new Object[9];
			for (int j = 1; j <= 9; j++) {
				if (j != 5) { // 통로를 제외했을 때
					if (colCnt % 2 == 1) { // 니 컬럼 번호 통로일 때는 ++ 안함
						objects[j - 1] = numCnt + 1; // +1 해두고 0부터 시작한 이유가 list index랑 맞추기 위해서
						numCnt++;
					} else if (colCnt % 2 == 0) {
						for (int k = 0; k < list.size(); k++) {
							if (numCnt == (int) list.get(k)) { // 리스트랑 값 비교
								objects[j - 1] = "O";
								list.remove(k); // 효율적이기 위해 지워주기
								break; // 남은 거 중에서 같은건 없으니 중지 thank you ㅃㅇ
							}
						}
					}
					colCnt++;
				} else
					objects[j - 1] = "-";
			}
			model.addRow(objects); // 한 row씩 추가
		}
		add(scrollPane); //
	}
//	public void test() {
//		if (j % 2 == 1) {
//			objects[j - 1] = cnt;
//			cnt++;
//		}
//	}

	public static void main(String[] args) {
		new TestUI().setVisible(true);
	}

	public void checkReservation() {
		model.setRowCount(0); // 이걸로 현재 테이블 데이터 초기화
//		return "";
	}
}
