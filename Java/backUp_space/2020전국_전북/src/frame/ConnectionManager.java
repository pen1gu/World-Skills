package frame;

import java.sql.*;

public class ConnectionManager {
    static Connection connection;
    static Statement statement;

    static{
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost/airdatabase?serverTimeZone=UTC","user","1234");
            statement = connection.createStatement();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void execute(String sql){
        try {
            statement.execute(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
