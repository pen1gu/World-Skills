package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Connector {
	static Connection connection;
	static Statement statement;

	static {
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost/airdatabase_1?serverTimezone=UTC", "user",
					"1234");
			statement = connection.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static ResultSet getSqlResult(String sql, Object... objects) {
		ResultSet rs = null;
		try {
			int cnt = 1;
			PreparedStatement pst = connection.prepareStatement(sql);
			for (Object object : objects) {
				pst.setObject(cnt, object);
				cnt++;
			}
			rs = pst.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}

	public static void withoutSqlResult(String sql, Object... objects) {
		try {
			int cnt = 1;
			PreparedStatement pst = connection.prepareStatement(sql);
			for (Object object : objects) {
				pst.setObject(cnt, object);
				cnt++;
			}
			pst.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
