package model;

import java.sql.ResultSet;
import java.util.ArrayList;

public class ProductFiles {

	ConnectionManager cm;

	public ArrayList<String> list = new ArrayList<String>();

	public ProductFiles() {
		cm = new ConnectionManager();
		cm.connect();
		try {
			ResultSet rs = cm.select("select * from product;");
			while (rs.next()) {
				list.add(rs.getString(1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		cm.close();
	}
}
