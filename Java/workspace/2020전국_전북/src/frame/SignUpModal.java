package frame;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.sql.ResultSet;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.Connector;
import model.UserData;

public class SignUpModal extends BaseModal {

	UserData ud;
	JTextField[] fields = new JTextField[7];

	public SignUpModal(UserData ud) {
		super(400, 450, "회원가입");
		this.ud = ud;
		setLayout(new GridLayout(0, 1));

		JPanel[] panels = new JPanel[6];
		String[] txtArray = { "아이디", "비밀번호", "이름", "주민등록번호", "Email" };
		String[] oper = { "-", "@" };

		int i = 0, checkCnt = 0, printCnt = 0;
		for (String txt : txtArray) {
			add(panels[i] = new JPanel(new FlowLayout(FlowLayout.LEFT)));
			panels[i].add(createComponent(new JLabel(txt), 80, 20));
			if (i > 2) {
				for (int j = i; j < i + 2; j++) {
					panels[i].add(fields[j] = new JTextField(10));
					if (checkCnt > 0) {
						continue;
					}
					panels[i].add(new JLabel(oper[printCnt]));
					checkCnt++;
				}
				checkCnt--;
				printCnt++;
				i++;
			} else {
				panels[i].add(fields[i] = new JTextField(10));
			}
			i++;
		}

		add(panels[5] = new JPanel(new FlowLayout(FlowLayout.RIGHT)));
		panels[5].add(createButton("가입하기", e -> hasLogin()));
		panels[5].add(createButton("닫기", e -> dispose()));
	}

	public void hasLogin() {
		for (int i = 0; i < fields.length; i++) {
			if (fields[i].getText().isEmpty()) {
				return;
			}
		}

		try {
			Connector.withoutSqlResult("insert into passenger values(0,?,?,?,?,?)", fields[0].getText(),
					fields[1].getText(), fields[2].getText(),
					String.format("%s-%s", fields[3].getText(), fields[4].getText()),
					String.format("%s@%s", fields[5].getText(), fields[6].getText()));

			informationMessage("회원가입이 완료되었습니다.");
			dispose();
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
