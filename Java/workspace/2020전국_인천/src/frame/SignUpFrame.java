package frame;

import javax.swing.*;
import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SignUpFrame extends BaseFrame {
    static final int width = 300, height = 400;
    JTextField[] textfield = new JTextField[5];
    JPasswordField[] passwordFields = new JPasswordField[2];
    boolean checkIdOverlap = false;

    SignUpFrame() {
        super("title", width, height);
        JPanel westpanel = createComponent(new JPanel(new GridLayout(0, 1)), 80, height - 40);
        JPanel centerpanel = createComponent(new JPanel(null), width - 80, height - 40);
        JPanel southpanel = createComponent(new JPanel(new FlowLayout(FlowLayout.RIGHT)), width, 40);
        JPanel eastpanel = createComponent(new JPanel(null), 80, height - 40);

        for (String text : new String[]{"이름", "아이디", "비밀번호", "비밀번호 체크", "전화번호", "생년월일", "주소"}) {
            JLabel label;
            westpanel.add(label = createComponent(new JLabel(text, 2), 100, 20));
            label.setVerticalAlignment(JLabel.TOP);
        }

        int pcnt = 0;
        int tcnt = 0;
        for (int i = 0; i < 7; i++) {
            if (i == 2 || i == 3) {
                centerpanel.add(createComponent(passwordFields[pcnt] = new JPasswordField(), 0, i * 45 + 5, 120, 20));
                pcnt++;
            } else {
                centerpanel.add(createComponent(textfield[tcnt] = new JTextField(), 0, i * 45 + 5, 120, 20));
                tcnt++;
            }
        }

        southpanel.add(createButton("회원가입", e -> clickSubmit()));
        southpanel.add(createButton("취소", e -> openFrame(new LoginFrame())));

        eastpanel.add(createComponent(createButtonWithOutMargin("중복확인", e -> checkOverlap()), 0, 50, 80, 25));
        add(centerpanel);
        add(westpanel, BorderLayout.WEST);
        add(eastpanel, BorderLayout.EAST);
        add(southpanel, BorderLayout.SOUTH);
    }

    public void clickSubmit() {
        boolean check = false;
        for (int i = 0; i < 5; i++) if (!textfield[i].getText().isEmpty()) check = true;
        for (int i = 0; i < 2; i++) if (!passwordFields[i].getText().isEmpty()) check = true;

        if (check == false) {
            errorMessage("빈칸이 있습니다.");
            return;
        }

        if (checkIdOverlap == false) {
            errorMessage("중복확인을 해주세요.");
            return;
        }

        if (!passwordFields[0].getText().equals(passwordFields[1].getText())) {
            errorMessage("비밀번호를 확인해주세요.");
            return;
        }

        try (PreparedStatement pst = connection.prepareStatement("insert into user values(0,?,?,?,?,?,?)")) {
            pst.setObject(1, textfield[1].getText());
            pst.setObject(2, passwordFields[0].getText());
            pst.setObject(3, textfield[4].getText());
            pst.setObject(4, textfield[0].getText());
            pst.setObject(5, textfield[2].getText());
            pst.setObject(6, textfield[3].getText());

            pst.execute();
            informationMessage("회원가입이 완료되었습니다.");
            openFrame(new LoginFrame());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void checkOverlap() {
        if (textfield[1].getText().isEmpty()) {
            errorMessage("아이디를 입력하세요.");
            return;
        }

        try (PreparedStatement pst = connection.prepareStatement("select * from user where u_id = ?")) {
            pst.setObject(1, textfield[1].getText());
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                errorMessage("아이디가 중복되었습니다.");
                return;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        checkIdOverlap = true;
        informationMessage("사용 가능한 아이디 입니다.");
    }

    public static void main(String[] args) {
        new SignUpFrame().setVisible(true);
    }
}
