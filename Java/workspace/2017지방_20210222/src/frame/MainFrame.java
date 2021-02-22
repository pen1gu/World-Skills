package frame;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

import model.TableData;

public class MainFrame extends BaseFrame {

	static JToggleButton[] toggleButtons = new JToggleButton[5];
	public static CardLayout card = new CardLayout();
	public static JPanel centerPanel = new JPanel(card);

	TableData tableData = new TableData();

	public MainFrame() {
		super(800, 600, "고속버스예메 프로그램");
		JPanel northPanel = new JPanel(new BorderLayout());
		JPanel north_south = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));

		northPanel.add(createLabel(new JLabel("고속버스예메 프로그램", JLabel.CENTER), new Font("맑은 고딕", 1, 22)),
				BorderLayout.NORTH);
		northPanel.add(north_south);

		north_south.add(toggleButtons[0] = createToggleButton("배차차량조회", e -> card.show(centerPanel, "carLU")));
		north_south.add(toggleButtons[1] = createToggleButton("예약신청", e -> card.show(centerPanel, "apply")));
		north_south.add(toggleButtons[2] = createToggleButton("차량좌석조회", e -> card.show(centerPanel, "seat")));
		north_south.add(toggleButtons[3] = createToggleButton("승차권발매", e -> card.show(centerPanel, "ticket")));
		north_south.add(toggleButtons[4] = createToggleButton("예약조회", e -> card.show(centerPanel, "reservation")));

		centerPanel.setBorder(BorderFactory.createMatteBorder(10, 0, 0, 0, Color.black));

		centerPanel.add("main", new JPanel());
		centerPanel.add("carLU", new CarLookUpPanel());
		centerPanel.add("apply", new ReservationApplyPanel(tableData));
		centerPanel.add("seat", new SeatLookUpPanel(tableData));
		centerPanel.add("ticket", new TicketReleasePanel());
		centerPanel.add("reservation", new ReservationLookUpPanel());

		for (int i = 0; i < toggleButtons.length; i++) {
			int cnt = i;
			toggleButtons[i].addActionListener(e -> refreshButtons(cnt));
		}

		add(northPanel, BorderLayout.NORTH);
		add(centerPanel);
	}

	public static void refreshButtons(int index) {
		try {
			for (int i = 0; i < 5; i++)
				toggleButtons[i].setSelected(false);

			toggleButtons[index].setSelected(true);
		} catch (IndexOutOfBoundsException e) {
			return;
		}
	}

	public static void main(String[] args) {
		new MainFrame().setVisible(true);
	}

	public static void openMain(int index) {
		refreshButtons(index);
		MainFrame.card.show(MainFrame.centerPanel, "main");
	}
}
