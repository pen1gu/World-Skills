package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

public class AdmissionFrame extends BaseFrame {

	JTextField tfAdm = new JTextField();
	JComboBox<Integer> cbFloor = new JComboBox<Integer>();

	public static JTextField tfRoom = setComp(new JTextField(), 50, 20);
	public static JTextField tfNo = setComp(new JTextField(), 50, 20);

	JPanel cp_in = setComp(new JPanel(new FlowLayout()), w - 30, h - 130);

	static int w = 1000, h = 350;

	public AdmissionFrame() {
		super(w, h, "입원", 2);
		setLayout(new BorderLayout());
		var np = new JPanel(new GridLayout(1, 0));
		np.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
		np.add(new JLabel("입원날짜", 0));
		np.add(tfAdm);
		np.add(new JLabel("층", 0));
		np.add(cbFloor);
		np.add(setBtn("검색", e -> changeFloor()));

		tfAdm.setHorizontalAlignment(0);
		tfAdm.setEnabled(false);

		for (int i = 1; i <= 6; i++) {
			cbFloor.addItem(i);
		}

		var cp = new JPanel(new FlowLayout());
		cp_in.setBorder(new LineBorder(Color.black));

		var sp = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		sp.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
		sp.add(new JLabel("호실"));
		sp.add(tfRoom);
		sp.add(new JLabel("침대번호"));
		sp.add(tfNo);
		sp.add(setBtn("예약", e -> reserve()));

		cp.add(cp_in);
		add(np, BorderLayout.NORTH);
		add(cp);
		add(sp, BorderLayout.SOUTH);
		
		addWindowListener(new WindowAdapter() {
			public void windowClosed(WindowEvent e) {
				new MainFrame().setVisible(true);
			};
		});
		setDate();
		changeFloor();
	}

	public void changeFloor() {
		cp_in.removeAll();
		int idx = cbFloor.getSelectedIndex() + 1;
		int w = 0;
		if (idx < 3) {
			w = 1000;
			for (int i = 1; i <= 10; i++) {
				cp_in.add(new House((100 * idx) + i));
			}
		} else if (idx < 5) {
			w = 800;
			for (int i = 1; i <= 8; i++) {
				cp_in.add(new House((100 * idx) + i));
			}
		} else if (idx > 4) {
			w = 620;
			for (int i = 1; i <= 6; i++) {
				cp_in.add(new House((100 * idx) + i));
			}
		}
		AdmissionFrame.this.setSize(w, h);
		AdmissionFrame.this.cp_in.setPreferredSize(new Dimension(w - 30, h - 130));

		setLocationRelativeTo(null);
		AdmissionFrame.this.revalidate();
		repaint();
		
	}

	public void setDate() {
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		tfAdm.setText(f.format(cal.getTime()));
	}

	public static void main(String[] args) {
		new AdmissionFrame().setVisible(true);
	}

	public void reserve() {
		String room = tfRoom.getText();
		String no = tfNo.getText();

		if (room.isEmpty() || no.isEmpty()) {
			eMsg("침대를 선택해주세요.");
			return;
		}

		try (var pst = CM.con.prepareStatement("insert into hospitalization values(0,?,?,?,curdate(),'',0,0);")) {
			pst.setObject(1, userNo);
			pst.setObject(2, getBedNo());
			pst.setObject(3, no);
			pst.execute();

			iMsg("예약되었습니다.");
			openFrame(new MainFrame());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int getBedNo() {
		int floor = toInt(tfRoom.getText()) / 100;
		int ho = toInt(tfRoom.getText()) % 100;
		
		int[] arr = {10,10,8,8,6,6};
		
		int result= 0;
		
		for (int i = 0; i < floor; i++) {
			result += arr[i];
		}
		return result - (arr[floor]-ho);
	}

	private class House extends JPanel {
		int no = 0;

		public House(int no) {
			this.no = no;
			setPreferredSize(new Dimension(91, h - 140));
			setLayout(new BorderLayout());

			add(new JLabel(no + "호", 0), BorderLayout.NORTH);
			add(setBtn("", e -> clickHouse()));
		}

		public void clickHouse() {
			openFrame(new BedroomFrame(no));
		}
	}
}
