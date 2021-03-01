package setting;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;

public class SignUpFrame extends BaseFrame {
    JTextField nameField = createComponent(new JTextField(), 200, 20);
    JTextField idField = createComponent(new JTextField(), 200, 20);
    JTextField pwField = createComponent(new JTextField(), 200, 20);

    JComboBox<Integer> cbYear = new JComboBox<>();
    JComboBox<Integer> cbMonth = new JComboBox<>();
    JComboBox<Integer> cbDay = new JComboBox<>();

    SignUpFrame() {
        super(300, 210, "회원가입");
        setLayout(new FlowLayout(FlowLayout.CENTER, 5, 10));
        add(createComponent(new JLabel("이름", 4), 60, 20));
        add(nameField);
        add(createComponent(new JLabel("아이디", 4), 60, 20));
        add(idField);
        add(createComponent(new JLabel("비밀번호", 4), 60, 20));
        add(pwField);

        add(new JLabel("생년월일"));
        add(cbYear);
        add(new JLabel("년"));
        add(cbMonth);
        add(new JLabel("월"));
        add(cbDay);
        add(new JLabel("일"));

        cbYear.addActionListener(this::changeDate);
        cbMonth.addActionListener(this::changeDate);

        Calendar calendar = Calendar.getInstance();
        cbYear.addItem(null);
        for (int i = 1900; i <= calendar.get(Calendar.YEAR); i++) {
            cbYear.addItem(i);
        }

        cbMonth.addItem(null);
        for (int i = 1; i <= 12; i++) {
            cbMonth.addItem(i);
        }

        add(createButton("가입 완료", e -> clickSubmit()));
        add(createButton("취소", e -> openFrame(new LoginFrame())));
    }

    public static void main(String[] args) {
        new SignUpFrame().setVisible(true);
    }

    public void changeDate(ActionEvent event) {
        cbDay.removeAllItems();
        if (cbYear.getSelectedItem() != null && cbMonth.getSelectedItem() != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.set((int) cbYear.getSelectedItem(), cbMonth.getSelectedIndex(), 0);
            for (int i = 1; i <= calendar.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
                cbDay.addItem(i);
            }
        }
    }

    public void clickSubmit() {
        if (idField.getText().isEmpty() || pwField.getText().isEmpty() || nameField.getText().isEmpty() || cbDay.getSelectedItem() == null) {
            errorMessage("누락된 항목이 있습니다.");
            return;
        }

        try (PreparedStatement pst = connection.prepareStatement("select * from user where u_id = ?")) {
            pst.setObject(1, idField.getText());

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                errorMessage("아이디가 중복되었습니다.");
                idField.setText("");
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try (PreparedStatement pst = connection.prepareStatement("insert into user values(0,?,?,?,?,0,'일반')")) {
            pst.setObject(1, idField.getText());
            pst.setObject(2, pwField.getText());
            pst.setObject(3, nameField.getText());
            pst.setObject(4, String.format("%d-%02d-%02d", cbYear.getSelectedItem(), cbMonth.getSelectedItem(), cbDay.getSelectedItem()));

            pst.execute();

            informationMessage("가입완료 되었습니다.");
            openFrame(new LoginFrame());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
