package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginFrame extends BaseFrame {

	final static int w = 320;
	final static int h = 190;

	JTextField idf = setComp(new JTextField(), 65, 5, 150, 20);
	JPasswordField pwf = setComp(new JPasswordField(), 65, 35, 150, 20);

	public LoginFrame() {
		super(320, 190, "로그인", 2);
		
		var cp = setComp(new JPanel(null), 320, 100);
		var bp = setComp(new JPanel(new FlowLayout(FlowLayout.RIGHT)), w-100, 40);

		add(setComp(setlabel(new JLabel("병원예약시스템", 0), new Font("굴림", 1, 24)), 0, 40), BorderLayout.NORTH);

		cp.add(setComp(setlabel(new JLabel("ID :", 4), new Font("맑은고딕", 1, 12)), 0, 5, 60, 20));
		cp.add(setComp(setlabel(new JLabel("PW :", 4), new Font("맑은고딕", 1, 12)), 0,35, 60, 20));
		cp.add(idf);
		cp.add(pwf);
		cp.add(setComp(setBtnM("로그인", e->clickLogin(),Color.black),220,0, 70, 70));
		
		
		bp.add(setBtn("회원가입", e->openFrame(new SignUPFrame())));
		bp.add(setBtn("종료", e->dispose()));
		bp.setBorder(BorderFactory.createEmptyBorder(0,0,0,15));
		
		add(cp, BorderLayout.CENTER);      
		add(bp,BorderLayout.SOUTH);
	}
	
	public void clickLogin() {
		String id = idf.getText();
		String pw = pwf.getText();
		
		if (id.isEmpty() || pw.isEmpty()) {
			eMsg("빈칸이 존재합니다");
			return;
		}
		
		try (var pst = CM.con.prepareStatement("select * from Patient where p_id = ? and p_pw = ?")){
			pst.setObject(1, id);
			pst.setObject(2, pw);
			var rs = pst.executeQuery();
			if (rs.next()) {
				userName=rs.getString(2);
				userNo = rs.getInt(1);
				openFrame(new MainFrame());
			}else {
				eMsg("회원정보가 틀립니다. 다시입력해주세요.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new LoginFrame().setVisible(true);
	}
}
