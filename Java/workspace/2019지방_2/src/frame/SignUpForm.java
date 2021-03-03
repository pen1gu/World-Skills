package frame;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class SignUpForm extends BaseFrame {

	JTextField tfName = createComponent(new JTextField(), 200, 20);
	JTextField tfId = createComponent(new JTextField(), 200, 20);
	JTextField tfPw = createComponent(new JTextField(), 200, 20);

	JComboBox<Integer> cbYear = new JComboBox<Integer>();
	JComboBox<Integer> cbMonth = new JComboBox<Integer>();
	JComboBox<Integer> cbDay = new JComboBox<Integer>();

	public SignUpForm() {
		super(300, 220, "회원가입");
		setLayout(new FlowLayout(FlowLayout.CENTER, 5, 10));

		add(createComponent(new JLabel("이름", 4), 60, 20));
		add(tfName);
		add(createComponent(new JLabel("아이디", 4), 60, 20));
		add(tfId);
		add(createComponent(new JLabel("비밀번호", 4), 60, 20));
		add(tfPw);

		add(new JLabel("생년월일"));
		add(cbYear);
		add(new JLabel("년"));
		add(cbMonth);
		add(new JLabel("월"));
		add(cbDay);
		add(new JLabel("일"));

		cbYear.addItem(null);
		cbYear.addActionListener(this::changeDate);
		LocalDate date = LocalDate.now();
		for (int i = 1900; i <= date.getYear(); i++) {
			cbYear.addItem(i);
		}

		cbMonth.addItem(null);
		cbMonth.addActionListener(this::changeDate);
		for (int i = 1; i <= 12; i++) {
			cbMonth.addItem(i);
		}

		add(createButton("가입완료", e -> clickSubmit()));
		add(createButton("취소", e -> openFrame(new LoginForm())));
	}

	public void changeDate(ActionEvent e) {
		cbDay.removeAllItems();
		if (cbMonth.getSelectedItem() != null && cbYear.getSelectedItem() != null) {
			LocalDate date = LocalDate.of((Integer) cbYear.getSelectedItem(), (Integer) cbMonth.getSelectedItem(), 1);
			for (int i = 1; i <= date.lengthOfMonth(); i++) {
				cbDay.addItem(i);
			}
		}
	}

	public void clickSubmit() {
		String id = tfId.getText();
		String pw = tfPw.getText();
		String name = tfName.getText();

		if (id.isEmpty() || pw.isEmpty() || name.isEmpty() || cbMonth.getSelectedItem() == null
				|| cbYear.getSelectedItem() == null) {
			errorMsg("누락된 항목이 있습니다.");
			return;
		}

		try (PreparedStatement pst = connection.prepareStatement("select u_id from user where u_id = ?")) {
			pst.setObject(1, id);

			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				errorMsg("아이디가 중복되었습니다.");
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		try (PreparedStatement pst = connection.prepareStatement("insert into user values(0,?,?,?,?,0,'일반')")) {
			pst.setObject(1, id);
			pst.setObject(2, pw);
			pst.setObject(3, name);
			pst.setObject(4, String.format("%d-%02d-%02d", cbYear.getSelectedItem(), cbMonth.getSelectedItem(),
					cbDay.getSelectedItem()));

			pst.execute();

			infoMsg("가입완료 되었습니다.");
			openFrame(new LoginForm());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
