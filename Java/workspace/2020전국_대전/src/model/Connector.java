package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
<<<<<<< Updated upstream
=======
import java.util.ArrayList;
>>>>>>> Stashed changes

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

<<<<<<< Updated upstream
	public static ResultSet getSqlResult(String sql, Object... objects) {
		ResultSet rs = null;

		try (PreparedStatement pst = connection.prepareStatement(sql)) {
			for (int i = 0; i < objects.length; i++)
				pst.setObject(i + 1, objects[i]);

			rs = pst.executeQuery();
=======
	public static ResultSet getSqlResult(String sql, Object... objects) { // 데이터 넣고 받아야할 때 ex) select
		ResultSet rs = null;
		try (PreparedStatement pst = connection.prepareStatement(sql)) {
			for (int i = 0; i < objects.length; i++) {
				pst.setObject(i + 1, objects[i]);
			}

			rs = pst.executeQuery();
			
>>>>>>> Stashed changes
		} catch (Exception e) {
			e.printStackTrace();
		}

		return rs;
	}

<<<<<<< Updated upstream
	public static void withoutSqlResult(String sql, Object... objects) {
		try (PreparedStatement pst = connection.prepareStatement(sql)) {
			for (int i = 0; i < objects.length; i++)
				pst.setObject(i + 1, objects[i]);

			pst.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
=======
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
		ArrayList<ArrayList<Object>> list = getSqlResults("select * from weddinghall", 5);

		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
			System.out.println();
>>>>>>> Stashed changes
		}
	}
}
