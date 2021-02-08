package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class ConnectionManager {
	Connection connection = null;
	Statement statement = null;
	PreparedStatement preparedStatement = null;

	public void connect() {
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost/Projectlibre?serverTimezone=UTC", "user",
					"1234");
			statement = connection.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void close() {
		try {
			if (connection != null) {
				connection.close();
			}
			if (statement != null) {
				statement.close();
			}
			if (preparedStatement != null) {
				preparedStatement.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
