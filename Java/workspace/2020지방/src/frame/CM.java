package frame;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class CM {
	public static Connection con;
	public static Statement stmt;
	
	static {
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost/hospital?serverTimezone=UTC","user","1234");
			stmt = con.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static ResultSet getRs(String sql) throws Exception{
		return stmt.executeQuery(sql);
	}
	
	
}
