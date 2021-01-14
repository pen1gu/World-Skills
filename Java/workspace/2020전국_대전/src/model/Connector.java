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
			connection = DriverManager.getConnection("jdbc:mysql://localhost/wedding?serverTimezone=UTC", "user",
					"1234");
			statement = connection.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static ResultSet getSqlResult(String sql, Object... objects) {
		ResultSet rs = null;

		try (PreparedStatement pst = connection.prepareStatement(sql)) {
			for (int i = 0; i < objects.length; i++)
				pst.setObject(i + 1, objects[i]);

			rs = pst.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return rs;
	}

	public static void withoutSqlResult(String sql, Object... objects) {
		try (PreparedStatement pst = connection.prepareStatement(sql)) {
			for (int i = 0; i < objects.length; i++)
				pst.setObject(i + 1, objects[i]);

			pst.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
