package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class PercentageTest extends BaseFrame {
	private static final long serialVersionUID = 1L;

	JTextArea _messageArea = createComponent(new JTextArea(), 250, 290);
	int _headCnt = 0, _tailCnt = 0;
	JLabel _headLabel = createComponent(new JLabel("앞: "), 50, 20);
	JLabel _tailLabel = createComponent(new JLabel("뒷: "), 50, 20);

	public PercentageTest() {
		super(300, 500, "확률");

		JPanel centerPanel = createComponent(new JPanel(), 280, 300);
		JPanel southPanel = new JPanel(new FlowLayout());
		JScrollPane scrollPane = createComponent(new JScrollPane(_messageArea), 280, 300);

		add(createLabel(new JLabel("동전 계산기", 0), new Font("맑은고딕", 1, 24), Color.black), BorderLayout.NORTH);

		_messageArea.setEditable(false);

		centerPanel.add(scrollPane);
		southPanel.add(_headLabel);
		southPanel.add(_tailLabel);

		southPanel.add(createComponent(createButton("확률", e -> clickPercentage()), 30, 30));

		add(centerPanel);
		add(southPanel, BorderLayout.SOUTH);
	}

	public void clickPercentage() {
		String percentageString = "";
		String appendValue = "";
		for (int i = 0; i < 5; i++) {
			int randomValue = (int) (Math.random() * 2);
			if (randomValue == 1) {
				percentageString = "뒷";
				_headCnt++;
			} else {
				percentageString = "앞";
				_tailCnt++;
			}
			appendValue += percentageString + " ";
		}
		_headLabel.setText("앞: " + _headCnt);
		_tailLabel.setText("뒷: " + _tailCnt);
		_messageArea.append(appendValue + "\n");
	}

	public static void main(String[] args) {
		new PercentageTest().setVisible(true);
	}
}
