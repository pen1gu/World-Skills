package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.time.LocalDate;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import model.AutoVehicle;
import model.CurrentCarInfo;

public class SelectParkVehicleForm extends BaseFrame {

	JComboBox<String> cbColorInfo = new JComboBox<String>(new String[] { "BLUE", "RED", "YELLOW" });
	MainForm main;
	CurrentCarInfo carInfo;

	JLabel lbCarImage = createComponent(new JLabel(), 80, 230);

	String path;

	public SelectParkVehicleForm(MainForm main, CurrentCarInfo carInfo) {
		super(600, 350, "입차차량정보");
		this.main = main;
		this.carInfo = carInfo;

		this.carInfo.setCarType("승용차"); // 테스트용

		JPanel centerPanel = new JPanel(new BorderLayout());
		JPanel southPanel = createComponent(new JPanel(new FlowLayout(FlowLayout.RIGHT)), 600, 50);

		JPanel center_west = createComponent(new JPanel(new FlowLayout()), 290, 0);
		JPanel center_east = createComponent(new JPanel(), 290, 0);

		center_west.setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.black), "입차 차량 정보", 0, 1));
		center_east.setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.black), "입차 차량 색상", 0, 1));

		createComponents(center_west, 220, 40, new JLabel("차량정보: " + carInfo.getCarType()),
				new JLabel("차량번호: " + carInfo.getCarNo()), new JLabel("입차시간: " + LocalDate.now()),
				new JLabel("입차번호: " + carInfo.getParkNo()));

		changeColor();

		cbColorInfo.addActionListener(e -> changeColor());

		center_east.add(createComponent(new JLabel("색상:"), 120, 30));
		center_east.add(cbColorInfo);
		center_east.add(lbCarImage);
		centerPanel.add(center_west, BorderLayout.WEST);
		centerPanel.add(center_east, BorderLayout.EAST);

		southPanel.add(createComponent(createButton("확인", e -> confirmCarInfo()), 80, 40));
		southPanel.add(createComponent(createButton("취소", e -> changeFrame(main)), 80, 40));

		add(centerPanel);
		add(southPanel, BorderLayout.SOUTH);
	}

	public void changeColor() {
		path = "./지급자료/image/" + carInfo.getCarType() + ((int) cbColorInfo.getSelectedIndex() + 1) + ".png";
		lbCarImage.setIcon(getImage(80, 180, path));
		cbColorInfo.setBackground(checkColor());
	}

	public void confirmCarInfo() {
		changeFrame(main);

		AutoVehicle autoVehicle = new AutoVehicle();
		carInfo.setFunction(autoVehicle, path);

		main.startPark();
		// 확인 goT을 때 넘겨야하는 것.
		// 선택한 자동차의 정보 -> 기본적으로 carInfo에 저장이 되어있다.
		// vehicle 함수 클래스는 움직임을 담은 클래스이기에
	}

	public Color checkColor() {
		Color color = null;
		switch ((String) cbColorInfo.getSelectedItem()) {
		case "BLUE": {
			color = Color.blue;
			break;
		}
		case "RED": {
			color = Color.red;
			break;
		}
		case "YELLOW": {
			color = Color.yellow;
			break;
		}
		default:
			new Throwable("색을 고를 수 없습니다.");
			return null;
		}
		return color;
	}

	public static void main(String[] args) {
		new SelectParkVehicleForm(new MainForm(), new CurrentCarInfo()).setVisible(true);
	}
}
