package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class Setting {

	static Connection connection;
	static Statement statement;

	static {
		try {
			connection = DriverManager.getConnection(
					"jdbc:mysql://localhost?serverTimezone=UTC&allowLoadLocalInfile=true", "root", "1234");
			statement = connection.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		execute("drop database if exists `airdatabase_1`");
		execute("CREATE SCHEMA IF NOT EXISTS `airdatabase_1` DEFAULT CHARACTER SET utf8 ;");

		execute("CREATE TABLE IF NOT EXISTS `airdatabase_1`.`Passenger` (\r\n"
				+ "  `Passenger_Code` INT(11) NOT NULL AUTO_INCREMENT,\r\n"
				+ "  `Id` VARCHAR(30) NULL DEFAULT NULL,\r\n" + "  `Pw` VARCHAR(50) NULL DEFAULT NULL,\r\n"
				+ "  `Name` VARCHAR(30) NULL DEFAULT NULL,\r\n" + "  `Jumin` VARCHAR(20) NULL DEFAULT NULL,\r\n"
				+ "  `Email` VARCHAR(50) NULL DEFAULT NULL,\r\n" + "  PRIMARY KEY (`Passenger_Code`))\r\n"
				+ "ENGINE = InnoDB\r\n" + "DEFAULT CHARACTER SET = utf8;");

		execute("CREATE TABLE IF NOT EXISTS `airdatabase_1`.`AirInfo` (\r\n"
				+ "  `Air_Code` INT(11) NOT NULL AUTO_INCREMENT,\r\n" + "  `Rend` VARCHAR(10) NULL DEFAULT NULL,\r\n"
				+ "  `Country` VARCHAR(30) NULL DEFAULT NULL,\r\n" + "  `Departure` VARCHAR(30) NULL DEFAULT NULL,\r\n"
				+ "  `Arrive` VARCHAR(30) NULL DEFAULT NULL,\r\n" + "  `Air` VARCHAR(20) NULL DEFAULT NULL,\r\n"
				+ "  `Type` VARCHAR(20) NULL DEFAULT NULL,\r\n" + "  `StartTime` TIME NULL DEFAULT NULL,\r\n"
				+ "  `LastTime` TIME NULL DEFAULT NULL,\r\n" + "  `Fare` INT(11) NULL DEFAULT NULL,\r\n"
				+ "  PRIMARY KEY (`Air_Code`))\r\n" + "ENGINE = InnoDB\r\n" + "DEFAULT CHARACTER SET = utf8;");

		execute("CREATE TABLE IF NOT EXISTS `airdatabase_1`.`Reservation` (\r\n"
				+ "  `Preservation` INT(11) NOT NULL AUTO_INCREMENT,\r\n"
				+ "  `DepartureDate` DATE NULL DEFAULT NULL,\r\n" + "  `Passenger_Code` INT(11) NULL DEFAULT NULL,\r\n"
				+ "  `ID` VARCHAR(50) NULL DEFAULT NULL,\r\n" + "  `Name` VARCHAR(30) NULL DEFAULT NULL,\r\n"
				+ "  `Rend` VARCHAR(20) NULL DEFAULT NULL,\r\n" + "  `Country` VARCHAR(50) NULL DEFAULT NULL,\r\n"
				+ "  `Departure` VARCHAR(50) NULL DEFAULT NULL,\r\n" + "  `Arrive` VARCHAR(50) NULL DEFAULT NULL,\r\n"
				+ "  `Air` VARCHAR(50) NULL DEFAULT NULL,\r\n" + "  `Type` VARCHAR(50) NULL DEFAULT NULL,\r\n"
				+ "  `StartTime` TIME NULL DEFAULT NULL,\r\n" + "  `LastTime` TIME NULL DEFAULT NULL,\r\n"
				+ "  `Count` INT(11) NULL DEFAULT NULL,\r\n" + "  `Fare` INT(11) NULL DEFAULT NULL,\r\n"
				+ "  PRIMARY KEY (`Preservation`))\r\n" + "ENGINE = InnoDB\r\n" + "DEFAULT CHARACTER SET = utf8;");

		execute("use airdatabase_1");
		execute("drop user if exists 'user'@'%'");
		execute("create user 'user'@'%' identified by '1234'");
		execute("grant select, insert, delete, update on `airdatabase_1`.* to 'user'@'%'");
		execute("flush privileges");

		execute("set global local_infile = 1");

		for (String table : new String[] { "AirInfo", "Passenger", "Reservation" }) {
			execute("load data local infile './datafiles/data/" + table + ".txt' into table " + table
					+ " fields terminated by '\t' lines terminated by '\n' ignore 1 lines");
		}
		
		JOptionPane.showMessageDialog(null, "완성");
	}

	public static void execute(String sql) {
		try {
			statement.execute(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
