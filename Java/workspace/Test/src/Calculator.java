import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.Stack;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class Calculator extends JFrame {

	JTextField tfEquation = new JTextField(26);
	JTextField tfResult = new JTextField(30);
	Stack<String> stack = new Stack<String>();
	StringBuilder equationText;

	ScriptEngineManager manager = new ScriptEngineManager();
	ScriptEngine engine = manager.getEngineByName("js");

	public Calculator() {
		setSize(400, 300);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setTitle("계산기프레임");

		JPanel northPanel = new JPanel();
		JPanel centerPanel = new JPanel(new GridLayout(4, 3, 5, 5));
		JPanel southPanel = new JPanel();
		JButton btnRemove = new JButton("◀");

		northPanel.add(new JLabel("수식입력"));
		northPanel.add(tfEquation);
		northPanel.add(btnRemove);
		northPanel.setBackground(Color.gray);

		btnRemove.addActionListener(e -> removeText());

		for (String element : new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "CE", "계산", "+", "-",
				"*", "/" }) {
			JButton button;
			centerPanel.add(button = new JButton(element));
			if (element.equals("계산")) {
				button.addActionListener(this::calculate);
			} else if (element.equals("CE")) {
				button.addActionListener(e -> resetField());
			} else {
				button.addActionListener(this::insertEquation);
			}
		}

		southPanel.add(new JLabel("계산결과"));
		southPanel.add(tfResult);
		southPanel.setBackground(Color.yellow);

		add(northPanel, BorderLayout.NORTH);
		add(centerPanel);
		add(southPanel, BorderLayout.SOUTH);
	}

	public static void main(String[] args) {
		new Calculator().setVisible(true);
	}

	public void insertEquation(ActionEvent e) {
		JButton button = (JButton) e.getSource();
		stack.push(button.getText());
		updateField();
	}

	public void calculate(ActionEvent e) {
		try {
			tfResult.setText(engine.eval(equationText.toString()).toString());
//			tfResult.setText();
		} catch (ScriptException e1) {
			e1.printStackTrace();
		}
	}

	public void resetField() {
		tfEquation.setText("");
		stack.removeAllElements();
	}

	public void removeText() {
		stack.pop();
		updateField();
	}

	public void updateField() {
		equationText = new StringBuilder();
		for (String element : stack) {
			equationText.append(element);
		}
		tfEquation.setText(equationText.toString());
	}
}
