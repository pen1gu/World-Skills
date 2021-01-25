package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import frame.HallSearchForm.WeddingContent;

public class ReservationForm extends BaseFrame {

	JPanel imgPanel = new JPanel(new BorderLayout());
	JLabel lbImg = createComponent(new JLabel(), 460, 230);// panel로 변경
	JPanel imgList = new JPanel(new GridLayout(1, 0));

	JTextField[] textFields = new JTextField[7];
	JComboBox<String>[] comboBoxs = new JComboBox[2];

	int imgCnt = 1;
	int fileCount;
	WeddingContent content;

	List<Object> reservationList;

	public ReservationForm(WeddingContent content) {
		super(600, 750, "예약");
		this.content = content;

		fileCount = new File("./datafiles/호텔이미지/" + content.name).list().length;
		JPanel centerPanel = createComponent(new JPanel(), 600, 750);
		JPanel centerInner = createComponent(new JPanel(new BorderLayout()), 460, 680);
		JPanel centerInner_south = new JPanel(new GridLayout(0, 1));

		centerPanel.add(createComponent(createButton("<", this::changeHallImage), 50, 80));
		centerPanel.add(centerInner);
		centerPanel.add(createComponent(createButton(">", this::changeHallImage), 50, 80));

		centerInner.add(imgPanel, BorderLayout.NORTH);
		imgPanel.setBorder(new LineBorder(Color.red));
		imgPanel.add(lbImg);
		imgPanel.add(imgList, BorderLayout.SOUTH);

		centerInner.add(imgPanel, BorderLayout.NORTH);
		centerInner.add(centerInner_south);

		String[] txtArr = { "웨딩홀명", "주소", "수용인원", "홀사용료", "예식형태", "식사종류", "식사비용", "인원수", "날짜" };

		int cnt = 0;
		JPanel[] panels = new JPanel[10];
		for (int i = 0; i < 9; i++) {
			centerInner_south.add(panels[i] = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 10)));
			panels[i].add(createComponent(new JLabel(txtArr[i]), 80, 20));

			if (i == 4 || i == 5) {
				panels[i].add(createComponent(comboBoxs[cnt] = new JComboBox<String>(), 350, 20));
				cnt++;
				continue;
			}
			panels[i].add(createComponent(textFields[i - cnt] = new JTextField(), 350, 20));
		}
		panels[9] = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panels[9].add(createComponent(createButton("청첩장 선택", e -> selectImage()), 100, 30));
		centerInner_south.add(panels[9]);
		add(centerPanel);
		add(createButton("예약하기", e -> haveBooking()), BorderLayout.SOUTH);

		reservationList = new ArrayList<Object>();
		try {
			ResultSet rs = statement.executeQuery(("select rNo from reservation order by rNo;"));
			while (rs.next()) {
				reservationList.add(rs.next());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		insertImageList();
		setImage();
	}

	public void insertImageList() {
		for (int i = 1; i <= fileCount; i++) {
			JLabel label;
			imgList.add(label = new JLabel(getImage(
					"./datafiles/호텔이미지/" + content.name + "/" + (content.name) + (i) + ".jpg", 460 / fileCount, 50)));
			label.setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
	}

	public void selectImage() {

	}

	public void changeHallImage(ActionEvent e) {
		JButton button = (JButton) e.getSource();
		if (button.getText().contentEquals("<")) {
			imgCnt = --imgCnt < 1 ? fileCount : imgCnt;
		} else {
			imgCnt = ++imgCnt > fileCount ? 1 : imgCnt;
		}
		System.out.println(imgCnt);
		setImage();
	}

	public void setImage() {
		lbImg.setIcon(
				getImage("./datafiles/호텔이미지/" + content.name + "/" + (content.name) + (imgCnt) + ".jpg", 460, 230));
	}

	public void haveBooking() {
		int peopleCnt = 0;
		try {
			peopleCnt = Integer.parseInt(textFields[5].getText());
		} catch (NumberFormatException e) {
			errorMessage("인원수를 바르게 입력해주세요.", "경고");
			return;
		}

		if (textFields[6].getText().isEmpty()) {
			errorMessage("날짜를 입력해주세요.", "경고");
			return;
		}
		int reservationNum = 0;
		for (int i = 0; i < reservationList.size(); i++) {
			 reservationNum = (int) (Math.random() * 89999 + 10000);
			if (reservationList.contains(reservationNum)) break;
		}

		try (PreparedStatement pst = connection.prepareStatement("insert into reservation values(?,?,?,?,?,?,?,?,?,?);")){
			pst.setObject(1, reservationNum);
			pst.setObject(2, content.weddingNo);
			pst.setObject(3, textFields[1].getText());
			pst.setObject(4, reservationNum);
			pst.setObject(5, reservationNum);
			pst.setObject(6, reservationNum);
			pst.setObject(7, reservationNum);
			pst.setObject(8, reservationNum);
			pst.setObject(9, reservationNum);
			pst.setObject(10, reservationNum);
			
			pst.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
		informationMessage("예약이 완료되었습니다.\n예약번호는 " + reservationNum + "", "예약완료");
	}
}
