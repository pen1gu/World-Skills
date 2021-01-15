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
	public ResultSet rs = null;

	public void connect() {
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost/wedding?serverTimezone=UTC", "user",
					"1234");
			statement = connection.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void close() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void select(String sql) {
		try {
			this.rs = statement.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
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

	public void getSqlResults(String sql, Object... objects) { // object 사용하는 부분은 보류
		try (PreparedStatement pst = connection.prepareStatement(sql)){
			for (int i = 0; i < objects.length; i++) {
				pst.setObject(i + 1, objects[i]);
			}
			this.rs = pst.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
