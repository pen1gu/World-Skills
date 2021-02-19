package frame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.sql.PreparedStatement;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class InsertCustomerForm extends BaseFrame {

	JTextField[] fields = new JTextField[6];

	public InsertCustomerForm(String caption) {
		super(400, 260, "고객 " + caption);

		JPanel centerPanel = new JPanel(new GridLayout(0, 2));

		JPanel southPanel = createComponent(new JPanel(new FlowLayout()), 400, 30);

		String[] str = null;

		for (int i = 0; i < fields.length; i++) {
			fields[i] = new JTextField();
		}

		if (caption.equals("수정")) {
			str = new String[] { " 고객코드 :", " 고 객 명 :", " 생년월일(YYYY-MM-DD) :", " 연락처 :", " 주  소 :", " 회  사 :" };
			System.out.println(CustomerLookUpForm.customerInfo.getCode());
			fields[0].setText(CustomerLookUpForm.customerInfo.getCode());
			fields[1].setText(CustomerLookUpForm.customerInfo.getName());
			fields[2].setText(CustomerLookUpForm.customerInfo.getBirth());
			fields[3].setText(CustomerLookUpForm.customerInfo.getTelNumber());
			fields[4].setText(CustomerLookUpForm.customerInfo.getAddress());
			fields[5].setText(CustomerLookUpForm.customerInfo.getCompany());

			fields[1].setEnabled(false);
			southPanel.add(createButton("수정", this::clickSubmit));
		} else {
			str = new String[] { " 고객코드 :", " *고 객 명 :", " *생년월일(YYYY-MM-DD) :", " *연락처 :", " 주  소 :", " 회  사 :" };
			southPanel.add(createButton("추가", this::clickSubmit));
		}

		for (int i = 0; i < fields.length; i++) {
			centerPanel.add(new JLabel(str[i]));
			centerPanel.add(fields[i]);
		}
		southPanel.add(createButton("닫기", e -> dispose()));

		fields[2].addKeyListener(new KeyAdapter() {
			public void keyPressed(java.awt.event.KeyEvent e) {
				if (e.getKeyChar() == '\n') {
					getCustomerCode();
				}
			};
		});
		fields[0].setEnabled(false);

		add(centerPanel);
		add(southPanel, BorderLayout.SOUTH);
	}

	public void clickSubmit(ActionEvent e) {
		JButton button = (JButton) e.getSource();

		String name = fields[1].getText();
		String birth = fields[2].getText();
		String tel = fields[3].getText();

		if (name.isEmpty() || birth.isEmpty() || tel.isEmpty()) {
			errorMessage("필수항목(*)을 모두 입력하세요.", "고객등록 에러");
			return;
		}

		if (button.getText().equals("수정")) {
			try (PreparedStatement pst = connection.prepareStatement(
					"update customer set name = ?, birth = ?, tel = ?, address = ?, company = ? where code =  ?")) {
				pst.setObject(1, name);
				pst.setObject(2, birth);
				pst.setObject(3, tel);
				pst.setObject(4, fields[4].getText());
				pst.setObject(5, fields[5].getText());
				pst.setObject(6, fields[0].getText());

				pst.executeUpdate();
				informationMessage("고객수정이 완료되었습니다.", "메시지");
			} catch (Exception e1) {
				errorMessage("입력을 확인해주세요.", "고객수정 에러");
				return;
			}
		} else {
			try (PreparedStatement pst = connection.prepareStatement("insert into customer values(?,?,?,?,?,?)")) {
				pst.setObject(1, fields[0].getText());
				pst.setObject(2, name);
				pst.setObject(3, birth);
				pst.setObject(4, tel);
				pst.setObject(5, fields[4].getText());
				pst.setObject(6, fields[5].getText());

				pst.execute();
				informationMessage("고객추가가 완료되었습니다.", "메시지");
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}

	public void getCustomerCode() {
		LocalDate date = LocalDate.now();
		String[] userBirth = fields[2].getText().split("-");
		int amount = Integer.parseInt(userBirth[0]) + Integer.parseInt(userBirth[1]) + Integer.parseInt(userBirth[2]);
		String dateStr = Integer.toString(date.getYear());

		fields[0].setText("S" + dateStr.substring(2, 4) + "" + amount);
	}
}
