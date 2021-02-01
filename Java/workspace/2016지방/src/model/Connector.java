package model;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class Connector {
	static Connection connection;
	static Statement statement;
	static PreparedStatement pst;

	static {
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost/wedding?serverTimezone=UTC", "user",
					"1234");
			statement = connection.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static ResultSet getSqlResult(String sql, Object... objects) { // 데이터 넣고 받아야할 때 ex) select
		ResultSet rs = null;
		try (PreparedStatement pst = connection.prepareStatement(sql)) {
			for (int i = 0; i < objects.length; i++) {
				pst.setObject(i + 1, objects[i]);
			}

			rs = pst.executeQuery();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return rs;
	}

	public static ResultSet getSqlResults(String sql, Object... objects) {
		ResultSet rs = null;
		try {
			pst = connection.prepareStatement(sql);
			for (int i = 0; i < objects.length; i++) {
				pst.setObject(i+1, objects[i]);
			}
			rs = pst.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}

	public static ArrayList<ArrayList<Object>> getSqlResults(String sql, int colCount, Object... objects) {
		ArrayList<ArrayList<Object>> returnList = new ArrayList<ArrayList<Object>>();
		ArrayList<Object> valueList;

		try (PreparedStatement pst = connection.prepareStatement(sql)) {
			for (int i = 0; i < objects.length; i++) {
				pst.setObject(i + 1, objects[i]);
			}

			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				valueList = new ArrayList<Object>();
				for (int i = 1; i <= colCount; i++) {
					valueList.add(rs.getObject(i));
				}
				returnList.add(valueList);
			}

			return returnList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void main(String[] args) {
		CallableStatement callableStatement = connection.prepareCall(sql);
	}
}
