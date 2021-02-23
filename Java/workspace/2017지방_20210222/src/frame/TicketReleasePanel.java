package frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import static frame.BaseFrame.*;

public class TicketReleasePanel extends JPanel {

	DefaultTableModel model = new DefaultTableModel(new String[] { "출발지", "→→→", "도착지" }, 0);
	JTable table = new JTable(model);
	JScrollPane scrollPane = createComponent(new JScrollPane(table), 690, 300);

	JTextField tfStartDate = new JTextField(9);
	JTextField tfPrimary = new JTextField(9);
	JTextField tfCarNum = new JTextField(9);
	JTextField tfUserId = new JTextField(9);

	JPanel centerPanel = new JPanel(new FlowLayout());

	public TicketReleasePanel() {
		setPreferredSize(new Dimension(800, 520));
		setLayout(new BorderLayout());

		JPanel northPanel = createComponent(new JPanel(), 800, 50);

		northPanel.add(new JLabel("출발일자"));
		northPanel.add(tfStartDate);
		northPanel.add(new JLabel("차량번호"));
		northPanel.add(tfPrimary);
		northPanel.add(new JLabel("버스번호"));
		northPanel.add(tfCarNum);
		northPanel.add(new JLabel("회원ID"));
		northPanel.add(tfUserId);
		northPanel.add(createComponent(createButtonWithoutMargin("조회", this::clickLookUp), 50, 20));
		centerPanel.add(scrollPane);
		scrollPane.setVisible(false);
		add(northPanel, BorderLayout.NORTH);
		add(centerPanel);
	}

	public void clickLookUp(ActionEvent e) {
		String carNum = tfCarNum.getText();
		String startDate = tfStartDate.getText();
		String primary = tfPrimary.getText();
		String userId = tfUserId.getText();

		if (carNum.isEmpty() || startDate.isEmpty() || primary.isEmpty() || userId.isEmpty()) {
			JOptionPane.showMessageDialog(null, "발권할 승차권의 데이터를 입력해주시기 바랍니다.", "웹 페이지 메시지", JOptionPane.WARNING_MESSAGE);
			return;
		}

		model.setRowCount(0);
		scrollPane.setVisible(true);

		try (PreparedStatement pst = connection.prepareStatement(
				"select bDeparture, bArrival, tt.bPrice,bDate,bNumber2 from tbl_ticket as tt inner join tbl_bus as tb on tt.bNumber = tb.bNumber where bDate = date_format(?,'%Y-%m-%d') and tt.bNumber = ? and bNumber2 = ? and cID = ?;")) {

			pst.setObject(1, tfStartDate.getText());
			pst.setObject(2, tfPrimary.getText());
			pst.setObject(3, tfCarNum.getText());
			pst.setObject(4, tfUserId.getText());

			ResultSet rs = pst.executeQuery();

			// TODO: 업데이트

			int aToward = 1, bToward = 2;
			if (tfCarNum.getText().equals("2호차")) {
				aToward = 2;
				bToward = 1;
			}

			int amount = 0;

			String startStation = "", endStation = "";
			String ticketDate = "";

			while (rs.next()) {
				amount += rs.getInt(3);
				startStation = rs.getString(aToward);
				endStation = rs.getString(bToward);
				ticketDate = rs.getString(4);
			}

			int sellPrice = tfUserId.getText().equals("비회원") ? 0 : amount / 10;

			model.addRow(new Object[] { startStation, "", endStation });
			model.addRow(new Object[] { "", "", "" });
			model.addRow(new Object[] { "운행요금", "할인요금", "영수액" });
			model.addRow(new Object[] { amount, sellPrice, amount - sellPrice });
			model.addRow(new Object[] { "", "", "" });
			model.addRow(new Object[] { "발권날짜", "", "출발일자" });
			model.addRow(new Object[] { ticketDate, "", startDate });

			for (int i = 0; i < 3; i++) {
				table.getColumnModel().getColumn(i).setCellRenderer(centerRender);
			}

			for (int i = 0; i < model.getRowCount(); i++) {
				table.setRowHeight(30);
			}

			if (amount == 0) {
				JOptionPane.showMessageDialog(null, "조회한 정보가 존재하지 않습니다.", "웹 페이지 메시지", JOptionPane.WARNING_MESSAGE);
				scrollPane.setVisible(false);
				return;
			}

			JOptionPane.showMessageDialog(null, "승차권이 정상적으로 발권되었습니다.", "웹 페이지 메시지", JOptionPane.WARNING_MESSAGE);

			centerPanel.revalidate();
			centerPanel.repaint();
		} catch (Exception e1) {
			e1.printStackTrace();
		}

	}
}
