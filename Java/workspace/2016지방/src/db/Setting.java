package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

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
		execute("drop database if exists `CompanySetting`");
		execute("CREATE SCHEMA IF NOT EXISTS `CompanySetting` DEFAULT CHARACTER SET utf8 ;");

		execute("CREATE TABLE IF NOT EXISTS `CompanySetting`.`admin` (\r\n" + "  `name` VARCHAR(20) NOT NULL,\r\n"
				+ "  `passwd` VARCHAR(20) NULL DEFAULT NULL,\r\n" + "  `position` VARCHAR(20) NULL DEFAULT NULL,\r\n"
				+ "  `jumin` CHAR(14) NULL DEFAULT NULL,\r\n" + "  `inputDate` DATE NULL DEFAULT NULL,\r\n"
				+ "  PRIMARY KEY (`name`))\r\n" + "ENGINE = InnoDB\r\n" + "DEFAULT CHARACTER SET = utf8;");

		execute("CREATE TABLE IF NOT EXISTS `CompanySetting`.`customer` (\r\n" + "  `code` CHAR(7) NOT NULL,\r\n"
				+ "  `name` VARCHAR(20) NULL DEFAULT NULL,\r\n" + "  `birth` DATE NULL DEFAULT NULL,\r\n"
				+ "  `tel` VARCHAR(20) NULL DEFAULT NULL,\r\n" + "  `address` VARCHAR(100) NULL DEFAULT NULL,\r\n"
				+ "  `company` VARCHAR(20) NULL DEFAULT NULL,\r\n" + "  PRIMARY KEY (`code`))\r\n"
				+ "ENGINE = InnoDB\r\n" + "DEFAULT CHARACTER SET = utf8;");

		execute("CREATE TABLE IF NOT EXISTS `CompanySetting`.`contract` (\r\n"
				+ "  `customerCode` CHAR(7) NOT NULL,\r\n" + "  `contractName` VARCHAR(20) NULL DEFAULT NULL,\r\n"
				+ "  `regPrice` INT(11) NULL DEFAULT NULL,\r\n" + "  `regDate` DATE NULL DEFAULT NULL,\r\n"
				+ "  `monthPrice` INT(11) NULL DEFAULT NULL,\r\n" + "  `adminName` VARCHAR(20) NULL DEFAULT NULL,\r\n"
				+ "  PRIMARY KEY (`customerCode`))\r\n" + "ENGINE = InnoDB\r\n" + "DEFAULT CHARACTER SET = utf8;");

		execute("use CompanySetting;");
		execute("drop user if exists 'user'@'%'");
		execute("create user 'user'@'%' identified by '1234'");
		execute("grant select, insert, delete, update on `CompanySetting`.* to 'user'@'%';");
		execute("flush privileges");

		execute("set global local_infile=1");

		for (String table : new String[] { "admin", "contract", "customer" }) {
			execute("load data local infile './제공파일/" + table + ".txt' into table " + table
					+ " fields terminated by '\t' lines terminated by '\n' ignore 1 lines");
		}
		System.out.println("완성");
	}

	public static void execute(String sql) {
		try {
			statement.execute(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
