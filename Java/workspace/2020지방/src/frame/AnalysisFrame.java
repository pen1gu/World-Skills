package frame;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class AnalysisFrame extends BaseFrame {
	Color[] list = { Color.red, Color.orange, Color.yellow };

	DefaultTableModel model = new DefaultTableModel("진료과,진료건수".split(","), 0);
	JTable tb = new JTable(model);
	String[] arr = "내과,정형외과,안과,치과".split(",");
	String result = "내과";

	public AnalysisFrame() {
		super(700, 400, "통계", 2);
		
		var jsc = setComp(new JScrollPane(tb), 200, 0);
		var wp = new JPanel(new BorderLayout());
		wp.add(jsc);
		var cp = new JPanel(new BorderLayout());

		for (int i = 0; i < 2; i++) {
			tb.getColumnModel().getColumn(i).setCellRenderer(crend);
		}

		int cnt = 0;
		try (var rs = CM.stmt.executeQuery("select count(*) from reservation group by r_section;")) {
			while (rs.next()) {
				model.addRow(new Object[] { arr[cnt], rs.getInt(1) });
				cnt++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		tb.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				super.mousePressed(e);
				int row = tb.getSelectedRow();
				result = model.getValueAt(row, 0).toString();
				repaint();
			}
		});

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				super.windowClosing(e);
				new MainFrame().setVisible(true);
			}
		});
		cp.add(new DrawChart());
		add(cp, BorderLayout.CENTER);
		add(wp, BorderLayout.WEST);
		repaint();
	}

	private class DrawChart extends JPanel {
		
		@Override
		protected void paintComponent(Graphics g) {
			Graphics2D g2d = (Graphics2D) g;
			
			g2d.setStroke(new BasicStroke(3));
			g2d.setColor(Color.black);
			g2d.setFont(new Font("맑은고딕", 1, 13));

			int h = 0;
			int cnt = 0;

			try (var pst = CM.con.prepareStatement(
					"select count(*) as c ,d_name from reservation as r inner join doctor as d on r.d_no = d.d_no where r_section = ? group by d_name order by c desc;")) {
				pst.setObject(1, result);

				var rs = pst.executeQuery();
				double max = -876543;
				while (rs.next()) {
					if (max < rs.getInt(1)) {
						max = rs.getInt(1);
					}

					h = (int) ((double) (rs.getInt(1) / max) * 280);

					g2d.drawString(rs.getString(1), 93 + cnt * 130, 30 + (280 - h));
					g2d.setColor(list[cnt]);
					g2d.fillRect(80 + cnt * 130, 40 + (280 - h), 40, h);
					g2d.setColor(Color.black);
					g2d.drawRect(80 + cnt * 130, 40 + (280 - h), 40, h);
					g2d.drawString(rs.getString(2), 80 + cnt * 130, 340);
					cnt++;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
