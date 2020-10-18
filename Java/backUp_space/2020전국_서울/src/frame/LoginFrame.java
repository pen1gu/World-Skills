package frame;

import javax.swing.*;
import javax.xml.transform.Result;
import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginFrame extends BaseFrame {

    JTextField tfId = createComponent(new JTextField(), 60, 10, 220, 20);
    JPasswordField tfPw = createComponent(new JPasswordField(), 60, 40, 220, 20);

    static final int WIDTH = 300, HEIGHT = 140;

    LoginFrame() {
        super(WIDTH, HEIGHT, "로그인");
        setLayout(new BorderLayout());

        JPanel centerpanel = new JPanel(null);
        centerpanel.setBackground(Color.white);

        centerpanel.add(createComponent(new JLabel("ID: ", 2), 10, 10, 80, 20));
        centerpanel.add(tfId);
        centerpanel.add(createComponent(new JLabel("PW: ", 2), 10, 40, 80, 20));
        centerpanel.add(tfPw);

        JButton btnLogin = createButton("로그인", e -> clickLogin());
        centerpanel.add(createComponent(btnLogin, 10, 70, WIDTH - 30, 25));
        btnLogin.setBackground(new Color(0, 125, 190));

        add(centerpanel);
    }

    public void clickLogin() {
        if (tfId.getText().isEmpty() || tfPw.getText().isEmpty()) {
            errorMessage("빈칸이 존재합니다.");
            return;
        }

        try (PreparedStatement pst = connection.prepareStatement("select * from M_id = ? and M_pw = ?")) {
            pst.setObject(1, tfId.getText());
            pst.setObject(2, tfPw.getText());

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                userName = rs.getString(4);
                userNo = rs.getInt(1);

                openFrame(new AdminFrame());
            } else {
                errorMessage("아이디 혹은 비밀번호가 일치하지 않습니다.");
                return;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public static void main(String[] args) {
        new LoginFrame().setVisible(true);
    }
}
