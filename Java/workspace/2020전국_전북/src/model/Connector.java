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
			connection = DriverManager.getConnection("jdbc:mysql://localhost/airdatabase_1?serverTimezone=UTC","user","1234");
			statement = connection.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public ResultSet getSqlResult(String sql) {
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			rs = pst.executeQuery(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}
}
