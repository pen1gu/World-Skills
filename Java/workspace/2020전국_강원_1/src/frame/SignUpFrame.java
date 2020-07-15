package frame;

import java.awt.FlowLayout;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.regex.Pattern;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class SignUpFrame extends BaseFrame {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    JTextField tfId = createComponenet(new JTextField(), 200, 20);
    JTextField tfPw = createComponenet(new JTextField(), 200, 20);
    JTextField tfName = createComponenet(new JTextField(), 200, 20);
    JTextField tfPhone = createComponenet(new JTextField(), 200, 20);

    JComboBox<Integer> cbYear = new JComboBox<Integer>();
    JComboBox<Integer> cbMonth = new JComboBox<Integer>();
    JComboBox<Integer> cbDay = new JComboBox<Integer>();

    public SignUpFrame() {
        super(300, 250, "회원가입");
        setLayout(new FlowLayout(FlowLayout.CENTER, 5, 10));

        add(createComponenet(new JLabel("이름", 4), 60, 25));
        add(tfName);
        add(createComponenet(new JLabel("아이디", 4), 60, 25));
        add(tfId);
        add(createComponenet(new JLabel("비밀번호", 4), 60, 25));
        add(tfPw);

        add(new JLabel("생년월일"));
        add(cbYear);
        add(new JLabel("년"));
        add(cbMonth);
        add(new JLabel("월"));
        add(cbDay);
        add(new JLabel("일"));

        Calendar calendar = Calendar.getInstance();

        cbYear.addItem(null);
        for (int i = 1940; i <= calendar.get(Calendar.YEAR); i++) {
            cbYear.addItem(i);
        }

        cbMonth.addItem(null);
        for (int i = 1; i <= 12; i++) {
            cbMonth.addItem(i);
        }

        cbYear.addActionListener(e -> changeDate());
        cbMonth.addActionListener(e -> changeDate());

        add(createComponenet(new JLabel("전화번호", 4), 60, 20));
        add(tfPhone);

        add(createButton("회원가입", e -> clickSubmit()));
        add(createButton("닫기", e -> openFrame(new LoginFrame())));
    }

    public void clickSubmit() {
        if (tfId.getText().isEmpty() || tfPw.getText().isEmpty() || tfName.getText().isEmpty()
                || cbDay.getSelectedItem() == null) {
            errorMessage("빈칸을 체워주세요.");
            return;
        }

        try (PreparedStatement pst = connection.prepareStatement("select * from user where id = ?")) {
            pst.setObject(1, tfId.getText());
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                errorMessage("아이디가 중복되었습니다.");
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!Pattern.compile("(?=.*[A-z])(?=.*[^A-z\\d])(?=.*[\\d]).{6,}").matcher(tfPw.getText()).find()) {
            errorMessage("비밀번호 형식이 일치하지 않습니다.");
            return;
        }

        try (PreparedStatement pst = connection.prepareStatement("select * from user where phone = ?")) {
            pst.setObject(1, tfPhone.getText());
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                errorMessage("전화번호가 중복되었습니다.");
                return;
            }

            Integer.parseInt(tfPhone.getText());
        } catch (SQLException e1) {
            e1.printStackTrace();
        } catch (NumberFormatException e2) {
            errorMessage("숫자만 입력해주세요.");
            return;
        }

        try (PreparedStatement pst = connection.prepareStatement("insert into user values(0,?,?,?,?,?)")) {
            pst.setObject(1, tfId.getText());
            pst.setObject(2, tfPw.getText());
            pst.setObject(3, tfName.getText());
            pst.setObject(4, String.format("%d-%02d-%02d", cbYear.getSelectedItem(), cbMonth.getSelectedItem(),
                    cbDay.getSelectedItem()));
            pst.setObject(5, phone(tfPhone.getText()));

            pst.execute();

            informationMessage("가입이 완료되었습니다.");
            openFrame(new LoginFrame());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void changeDate() {
        cbDay.removeAllItems();
        if (cbYear.getSelectedItem() != null && cbMonth.getSelectedItem() != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.set((int) cbYear.getSelectedItem(), cbMonth.getSelectedIndex() - 1, 1);
            for (int i = 1; i <= calendar.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
                cbDay.addItem(i);
            }
        }
    }

    public static String phone(String text) {
        if (text == null) {
            return "";
        }
        if (text.length() == 8) {
            return text.replaceFirst("^([0-9]{4})([0-9]{4})$", "$1-$2");
        } else if (text.length() == 12) {
            return text.replaceFirst("(^[0-9]{4})([0-9]{4})([0-9]{4})$", "$1-$2-$3");
        }
        return text.replaceFirst("(^02|[0-9]{3})([0-9]{3,4})([0-9]{4})$", "$1-$2-$3");
    }
}
