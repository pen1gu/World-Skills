package frame;

import static frame.BaseFrame.createButton;
import static frame.BaseFrame.createComponent;
import static frame.BaseFrame.errorMessage;

import java.awt.Color;
import java.awt.FlowLayout;
import java.util.regex.Pattern;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;

public class CreateNewProjectPage extends JDialog {

	JTextField tfProjectName = createComponent(new JTextField(), 430, 30);
	JTextField tfAdmin = createComponent(new JTextField(), 430, 30);
	JTextField tfStartDate = createComponent(new JTextField(), 160, 30);
	JTextField tfEndDate = createComponent(new JTextField(), 160, 30);

	JTextArea taNote = createComponent(new JTextArea(), 530, 60);

	public CreateNewProjectPage() {
		setSize(600, 290);
		setLayout(new FlowLayout());
		setTitle("새 프로젝트");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		add(createComponent(new JLabel("프로젝트 이름 :", JLabel.LEFT), 100, 30));
		add(tfProjectName);
		add(createComponent(new JLabel("관리자 :", JLabel.LEFT), 100, 30));
		add(tfAdmin);
		add(createComponent(new JLabel("시작일 :", JLabel.LEFT), 100, 30));
		add(tfStartDate);
		add(createComponent(new JLabel("종료일 :", JLabel.LEFT), 100, 30));
		add(tfEndDate);
		add(createComponent(new JLabel("노트 :", JLabel.LEFT), 530, 30));
		add(taNote);

		taNote.setBorder(new LineBorder(Color.black));

		add(createComponent(createButton("확인", e -> createProject()), 60, 30));
		add(createComponent(createButton("취소", e -> dispose()), 60, 30));
	}

	public void createProject() {
		if (tfProjectName.getText().isEmpty() || tfAdmin.getText().isEmpty() || tfStartDate.getText().isEmpty()
				|| tfEndDate.getText().isEmpty()) {
			errorMessage("공백이 존재합니다.", "Message");
			return;
		}

		if (Pattern.matches("[A-Za-z]", tfProjectName.getText())) {
			errorMessage("프로젝트 이름은 한글로 입력해주세요.", "Message");
			return;
		}
		
		
	}
}
