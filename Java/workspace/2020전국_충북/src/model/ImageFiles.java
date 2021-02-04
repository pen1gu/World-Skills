package model;

import java.sql.ResultSet;
import java.util.ArrayList;

public class ImageFiles {
	ConnectionManager cm;

	public ArrayList<byte[]> list = new ArrayList<byte[]>();

	public ImageFiles() {

		cm = new ConnectionManager();
		cm.connect();

		try {
			ResultSet rs = cm.select("select * from image order by serial;");
			while (rs.next()) {
				list.add(rs.getBytes(3));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		cm.close();
	}
}
