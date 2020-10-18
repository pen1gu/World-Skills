package setting;

import javax.swing.*;
import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginFrame extends BaseFrame {

    JTextField idField = createComponent(new JTextField(), 60, 10, 150, 20);
    JPasswordField pwField = createComponent(new JPasswordField(), 60, 40, 150, 20);

    LoginFrame() {
        super(300, 190, "로그인");
        add(createLabel(new JLabel("STARBOX", 0), new Font("맑은고딕", 1, 28)), BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(null);
        centerPanel.add(createComponent(new JLabel("ID:", 4), 0, 10, 55, 20));
        centerPanel.add(createComponent(new JLabel("PW:", 4), 0, 40, 55, 20));

        centerPanel.add(idField);
        centerPanel.add(pwField);

        centerPanel.add(createComponent(createButtonWithOutMargin("로그인", e -> clickSumbit()), 215, 0, 60, 60));
        add(centerPanel);

        JPanel southPanel = createComponent(new JPanel(new FlowLayout()), 300, 40);
        southPanel.add(createButton("회원가입", e -> openFrame(new SignUpFrame())));
        southPanel.add(createButton("종료", e -> dispose()));

        add(southPanel, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        new LoginFrame().setVisible(true);
    }

    public void clickSumbit() {
        if (idField.getText().isEmpty() || pwField.getText().isEmpty()) {
            errorMessage("빈칸이 존재합니다.");
            return;
        }

        if ("admin".equals(idField.getText()) && "1234".equals(pwField.getText())){
            openFrame(new AdminFrame());
            return;
        }

        try (PreparedStatement pst = connection.prepareStatement("select * from user where u_id = ? and u_pw = ?")) {
            pst.setObject(1, idField.getText());
            pst.setObject(2, pwField.getText());

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                userNo = rs.getInt(1);
                userName = rs.getString(4);
                userPoint = rs.getInt(6);
                userGrade = rs.getString(7);
                openFrame(new MainFrame());
            } else {
                errorMessage("회원정보가 틀립니다. 다시 입력해주세요.");
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
