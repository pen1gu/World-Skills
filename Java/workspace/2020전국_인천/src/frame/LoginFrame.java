package frame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginFrame extends BaseFrame {

    static final int width = 320, heigth = 170;

    JTextField tfId = createComponent(new JTextField(),70,20,160,20);
    JPasswordField tfPw = createComponent(new JPasswordField(),70,40,160,20);
    JLabel lbSignUp = createComponent(new JLabel("회원가입",2),50,30);
    JButton btnLogin = createComponent(createButtonWithOutMargin("로그인",e->clickLogin()),240,0,60,60);

    LoginFrame() {
        super("로그인",width,heigth);
        add(createLabel(new JLabel("기능마켓", 0), new Font("Gothic", 1, 30)), BorderLayout.NORTH);

        JPanel centerpanel = createComponent(new JPanel(null), width,80);
        centerpanel.add(createComponent(new JLabel("아이디",4),0,20,60,20));
        centerpanel.add(tfId);
        centerpanel.add(createComponent(new JLabel("비밀번호",4),0,40,60,20));
        centerpanel.add(tfPw);
        centerpanel.add(btnLogin);
        add(centerpanel);


        lbSignUp.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                openFrame(new SignUpFrame());
            }
        });
        JPanel southpanel = createComponent(new JPanel(new FlowLayout(FlowLayout.RIGHT)),width,30);
        southpanel.add(lbSignUp);

        add(southpanel,BorderLayout.SOUTH);
    }
    
    public void clickLogin(){
        if (tfId.getText().isEmpty() || tfPw.getText().isEmpty()){
            errorMessage("빈칸이 있습니다.");
            return;
        }

        if (tfId.getText().equals("admin") && tfPw.getText().equals("123")){
            informationMessage("관리자로 로그인되었습니다.");
            userName = "관리자";
            openFrame(new AdminFrame());
        }
        try(PreparedStatement pst = connection.prepareStatement("select * from user where u_id = ? and u_pw = ?")){
            pst.setObject(1,tfId.getText());
            pst.setObject(2,tfPw.getText());
            ResultSet rs = pst.executeQuery();
            if (rs.next()){
                informationMessage(rs.getString(5)+"님 환영합니다.");
                userNo = rs.getInt(1);
                userName = rs.getString(5);
                openFrame(new MainFrame());
            }else{
                errorMessage("아이디나 비밀번호가 틀렸습니다.");
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new LoginFrame().setVisible(true);
    }
}
