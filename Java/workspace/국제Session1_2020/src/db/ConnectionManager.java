package db;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ConnectionManager {
	public Connection connection;
	public Statement statement;

	public ConnectionManager() {
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost/Session1?serverTimezone=UTC", "user",
					"1234");
			statement = connection.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public <T> T getSQLResult(Class<T> cls, String SQL, Object... objects) { // reflection, generic class
		PreparedStatement preparedStatement = null; // 변수 선언

		try {
			preparedStatement = connection.prepareStatement(SQL); // 객체 호출

			for (int i = 0; i < objects.length; i++) {
				preparedStatement.setObject(i + 1, objects[i]); // 오브젝트 삽입 ex) select * from ? -> select * from value
			}

			T result = cls.getDeclaredConstructor().newInstance(); // 제네릭 타입으로 클래스 인스턴스 (해당 클래스에서 선언된 모든 생성자의 정보를

			try (ResultSet rs = preparedStatement.executeQuery()) { // 쿼리 실행
				if (rs.next()) {
					setValues(result, rs);
					return result;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	private static void setValues(Object obj, ResultSet rs) {
		Field[] fields = obj.getClass().getDeclaredFields();

		for (Field f : fields) {
			try {
				Object value;
				if (f.getName().equals("classType"))
					value = rs.getString("class");
				else
					value = rs.getObject(f.getName());

				if (f.getType() == int.class) {
					f.setInt(obj, Integer.parseInt(value.toString()));
				} else {
					f.set(obj, value);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public <T> ArrayList<T> getSQLResults(Class<T> cls, String SQL, Object... objects) {
		PreparedStatement preparedStatement = null;

		try {
			preparedStatement = connection.prepareStatement(SQL);

			for (int i = 0; i < objects.length; i++) { // ex) a,b,c,d,e
				preparedStatement.setObject(i + 1, objects[i]); // 1a 2b 3c 4d 5e

				ArrayList<T> result = new ArrayList<T>();

				try (ResultSet rs = preparedStatement.executeQuery()) {
					while (rs.next()) {
						T item = cls.getDeclaredConstructor().newInstance();
						setValues(item, rs);

						result.add(item);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public <T> ArrayList<T> getSQLResultsWithOutObject(String sql) {
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement(sql);
			List<T> list = new ArrayList<T>();
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
