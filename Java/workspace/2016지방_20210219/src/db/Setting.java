package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Setting {

	static Connection connection;
	static Statement statement;

	static {
		try {
			connection = DriverManager.getConnection(
					"jdbc:mysql://localhost?serverTimezone=UTC&allowLoadLocalInfile=true", "root", "1234");
			statement = connection.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		execute("drop database if exists `company_1`");
		execute("CREATE SCHEMA IF NOT EXISTS `company_1` DEFAULT CHARACTER SET utf8 ;");

		execute("CREATE TABLE IF NOT EXISTS `company_1`.`admin` (\r\n" + "  `name` VARCHAR(20) NOT NULL,\r\n"
				+ "  `passwd` VARCHAR(20) NULL DEFAULT NULL,\r\n" + "  `position` VARCHAR(20) NULL DEFAULT NULL,\r\n"
				+ "  `jumin` CHAR(14) NULL DEFAULT NULL,\r\n" + "  `inputDate` DATE NULL DEFAULT NULL,\r\n"
				+ "  PRIMARY KEY (`name`))\r\n" + "ENGINE = InnoDB\r\n" + "DEFAULT CHARACTER SET = utf8;");

		execute("CREATE TABLE IF NOT EXISTS `company_1`.`customer` (\r\n" + "  `code` CHAR(7) NOT NULL,\r\n"
				+ "  `name` VARCHAR(20) NULL DEFAULT NULL,\r\n" + "  `birth` DATE NULL DEFAULT NULL,\r\n"
				+ "  `tel` VARCHAR(20) NULL DEFAULT NULL,\r\n" + "  `address` VARCHAR(100) NULL DEFAULT NULL,\r\n"
				+ "  `company` VARCHAR(20) NULL DEFAULT NULL,\r\n" + "  PRIMARY KEY (`code`))\r\n"
				+ "ENGINE = InnoDB\r\n" + "DEFAULT CHARACTER SET = utf8;");
		execute("CREATE TABLE IF NOT EXISTS `company_1`.`contract` (\r\n" + "  `customerCode` CHAR(7) NOT NULL,\r\n"
				+ "  `contractName` VARCHAR(20) NULL DEFAULT NULL,\r\n" + "  `regPrice` INT(11) NULL DEFAULT NULL,\r\n"
				+ "  `regDate` DATE NULL DEFAULT NULL,\r\n" + "  `monthPrice` INT(11) NULL DEFAULT NULL,\r\n"
				+ "  `adminName` VARCHAR(20) NULL DEFAULT NULL)\r\n" + "ENGINE = InnoDB\r\n"
				+ "DEFAULT CHARACTER SET = utf8;");

		execute("use company_1;");
		execute("drop user if exists 'user'@'%'");
		execute("create user 'user'@'%' identified by '1234'");
		execute("grant select, insert, delete, update on `company_1`.* to 'user'@'%'");
		execute("flush privileges");

		execute("set global local_infile=1");

		for (String element : new String[] { "admin", "contract", "customer" }) {
			execute("load data local infile './지급자료/" + element + ".txt' into table " + element
					+ " fields terminated by '\t' lines terminated by '\n' ignore 1 lines");
		}

		System.out.println("완료");
	}

	public static void execute(String sql) {
		try {
			statement.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
