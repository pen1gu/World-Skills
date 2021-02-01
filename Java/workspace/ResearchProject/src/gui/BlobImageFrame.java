package gui;

import java.awt.Color;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JLabel;
import javax.swing.border.LineBorder;

public class BlobImageFrame extends BaseFrame {
	public BlobImageFrame() {
		super(1000, 1000, "");
		setLayout(null);

		try (PreparedStatement pst = connection.prepareStatement("select * from Image")) {
			ResultSet rs = pst.executeQuery();

			int n = 0;
			while (rs.next()) {
				byte[] image = null;
				image = rs.getBytes(2);
				JLabel lbImg = createComponent(new JLabel(getImage(60, 60, image)), 60 * n, 100, 60, 60);
				lbImg.setBorder(new LineBorder(Color.black));
				add(lbImg);
				n++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new BlobImageFrame().setVisible(true);
	}
}
