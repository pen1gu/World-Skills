package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionManager { // connection manager는 다시 생각해보자.
	Connection connection = null;
	Statement statement = null;
	PreparedStatement pst = null;

	public void connect() {
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost/wedding?serverTimezone=UTC", "user",
					"1234");
			statement = connection.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void freeConnection() {
		try {
			if (statement != null)
				statement.close();
			if (pst != null)
				statement.close();
			if (connection != null)
				connection.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public ResultSet select(String sql) {
		ResultSet rs = null;
		try {
			rs = statement.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	public void update(String sql) {
		try {
			statement.executeUpdate(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void execute(String sql) {
		try {
			statement.execute(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ResultSet getSqlResults(String sql, Object... objects) { // object 사용하는 부분은 보류
		ResultSet rs = null;

		try {
			pst = connection.prepareStatement(sql);
			for (int i = 0; i < objects.length; i++) {
				pst.setObject(i + 1, objects[i]);
			}
			rs = pst.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}
}
