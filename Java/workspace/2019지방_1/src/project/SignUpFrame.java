package project;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class SignUpFrame extends BaseFrame {
	
	JTextField tfId = createComponent(new JTextField(), 200, 20);
	JTextField tfName = createComponent(new JTextField(), 200, 20);
	JTextField tfPw = createComponent(new JTextField(), 200, 20);

	JComboBox<Integer> cbYear = new JComboBox<Integer>();
	JComboBox<Integer> cbMonth = new JComboBox<Integer>();
	JComboBox<Integer> cbDay = new JComboBox<Integer>();

	public SignUpFrame() {
		super(320, 220, "회원가입");
		addComponents();
		setComponents();
	}

	@Override
	public void addComponents() {
		add(createComponent(new JLabel("이름", 4), 60, 20));
		add(tfId);
		add(createComponent(new JLabel("아이디", 4), 60, 20));
		add(tfName);
		add(createComponent(new JLabel("비밀번호", 4), 60, 20));
		add(tfPw);

		add(new JLabel("생년월일"));
		add(cbYear);
		add(new JLabel("년"));
		add(cbMonth);
		add(new JLabel("월"));
		add(cbDay);
		add(new JLabel("일"));

		add(createButton("가입완료", e -> clickSubmit()));
		add(createButton("취소", e -> openFrame(new LoginFrame())));
	}

	@Override
	public void setComponents() {
		setLayout(new FlowLayout(FlowLayout.CENTER, 5, 13));

		Calendar calendar = Calendar.getInstance();

		cbYear.addItem(null);
		for (int i = 1900; i <= calendar.get(Calendar.YEAR); i++) {
			cbYear.addItem(i);
		}

		cbMonth.addItem(null);
		for (int i = 1; i <= 12; i++) {
			cbMonth.addItem(i);
		}

		cbYear.addActionListener(this::changeDate);
		cbMonth.addActionListener(this::changeDate);
	}

	public static void main(String[] args) {
		new SignUpFrame().setVisible(true);
	}

	private void changeDate(ActionEvent e) {
		if (cbYear.getSelectedItem() != null && cbMonth.getSelectedItem() != null) {
			Calendar calendar = Calendar.getInstance();
			calendar.set((int) cbYear.getSelectedItem(), cbMonth.getSelectedIndex(), 0);
			for (int i = 1; i <= calendar.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
				cbDay.addItem(i);
			}
		}
	}

	private void clickSubmit() {
		if (cbDay.getSelectedItem() == null || tfId.getText().isEmpty() || tfPw.getText().isEmpty()
				|| tfName.getText().isEmpty()) {
			errorMessage("빈칸이 존재합니다");
			return;
		}

		try (PreparedStatement pst = connection.prepareStatement("select * from user where u_id = ?")) {
			pst.setObject(1, tfId.getText());

			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				errorMessage("아이디가 중복되었습니다.");
				tfId.setText("");
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		try (PreparedStatement pst = connection.prepareStatement("insert into user values(0,?,?,?,?,0,'일반')")) {
			pst.setObject(1, tfId.getText());
			pst.setObject(2, tfPw.getText());
			pst.setObject(3, tfName.getText());
			pst.setObject(4, String.format("%d-%02d-%02d", cbYear.getSelectedItem(), cbMonth.getSelectedItem(),
					cbDay.getSelectedItem()));

			pst.execute();
			
			informationMessage("가입이 완료되었습니다");
			openFrame(new LoginFrame());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
