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
		execute("drop database if exists computer");
		execute("CREATE SCHEMA IF NOT EXISTS `computer` DEFAULT CHARACTER SET utf8 ;");

		execute("CREATE TABLE IF NOT EXISTS `computer`.`user` (\r\n" + "  `Serial` INT(11) NOT NULL AUTO_INCREMENT,\r\n"
				+ "  `id` VARCHAR(20) NULL DEFAULT NULL,\r\n" + "  `pw` VARCHAR(20) NULL DEFAULT NULL,\r\n"
				+ "  `name` VARCHAR(20) NULL DEFAULT NULL,\r\n" + "  `phone` VARCHAR(15) NULL DEFAULT NULL,\r\n"
				+ "  `address` VARCHAR(50) NULL DEFAULT NULL,\r\n" + "  `email` VARCHAR(50) NULL DEFAULT NULL,\r\n"
				+ "  `favorite` INT(11) NULL DEFAULT NULL,\r\n" + "  PRIMARY KEY (`Serial`))\r\n"
				+ "ENGINE = InnoDB\r\n" + "DEFAULT CHARACTER SET = utf8;");
		execute("CREATE TABLE IF NOT EXISTS `computer`.`seller` (\r\n"
				+ "  `serial` INT(11) NOT NULL AUTO_INCREMENT,\r\n" + "  `id` VARCHAR(20) NULL DEFAULT NULL,\r\n"
				+ "  `pw` VARCHAR(30) NULL DEFAULT NULL,\r\n" + "  `name` VARCHAR(20) NULL DEFAULT NULL,\r\n"
				+ "  `address` VARCHAR(50) NULL DEFAULT NULL,\r\n" + "  PRIMARY KEY (`serial`))\r\n"
				+ "ENGINE = InnoDB\r\n" + "DEFAULT CHARACTER SET = utf8;");

		execute("CREATE TABLE IF NOT EXISTS `computer`.`category` (\r\n"
				+ "  `serial` INT(11) NOT NULL AUTO_INCREMENT,\r\n" + "  `name` VARCHAR(10) NULL DEFAULT NULL,\r\n"
				+ "  PRIMARY KEY (`serial`))\r\n" + "ENGINE = InnoDB\r\n" + "DEFAULT CHARACTER SET = utf8;");

		execute("CREATE TABLE IF NOT EXISTS `computer`.`orderlist` (\r\n"
				+ "  `serial` INT(11) NOT NULL AUTO_INCREMENT,\r\n" + "  `user` INT(11) NULL DEFAULT NULL,\r\n"
				+ "  `seller` INT(11) NULL DEFAULT NULL,\r\n" + "  `product` INT(11) NULL DEFAULT NULL,\r\n"
				+ "  `price` INT(11) NULL DEFAULT NULL,\r\n" + "  `quantity` INT(11) NULL DEFAULT NULL,\r\n"
				+ "  `shipping` TINYINT(1) NULL DEFAULT NULL,\r\n" + "  PRIMARY KEY (`serial`))\r\n"
				+ "ENGINE = InnoDB\r\n" + "DEFAULT CHARACTER SET = utf8;");

		execute("CREATE TABLE IF NOT EXISTS `computer`.`product` (\r\n"
				+ "  `serial` INT(11) NOT NULL AUTO_INCREMENT,\r\n" + "  `name` VARCHAR(80) NULL DEFAULT NULL,\r\n"
				+ "  `description` VARCHAR(500) NULL DEFAULT NULL,\r\n" + "  `date` DATE NULL DEFAULT NULL,\r\n"
				+ "  `category` INT(11) NULL DEFAULT NULL,\r\n" + "  `thumb` INT(11) NULL DEFAULT NULL,\r\n"
				+ "  `info` INT(11) NULL DEFAULT NULL,\r\n" + "  PRIMARY KEY (`serial`))\r\n" + "ENGINE = InnoDB\r\n"
				+ "DEFAULT CHARACTER SET = utf8;");

		execute("CREATE TABLE IF NOT EXISTS `computer`.`orderlist` (\r\n"
				+ "  `serial` INT(11) NOT NULL AUTO_INCREMENT,\r\n" + "  `user` INT(11) NULL DEFAULT NULL,\r\n"
				+ "  `seller` INT(11) NULL DEFAULT NULL,\r\n" + "  `product` INT(11) NULL DEFAULT NULL,\r\n"
				+ "  `price` INT(11) NULL DEFAULT NULL,\r\n" + "  `quantity` INT(11) NULL DEFAULT NULL,\r\n"
				+ "  `shipping` TINYINT(1) NULL DEFAULT NULL,\r\n" + "  PRIMARY KEY (`serial`))\r\n"
				+ "ENGINE = InnoDB\r\n" + "DEFAULT CHARACTER SET = utf8;");

		execute("CREATE TABLE IF NOT EXISTS `computer`.`ad` (\r\n" + "  `serial` INT(11) NOT NULL AUTO_INCREMENT,\r\n"
				+ "  `name` VARCHAR(50) NULL DEFAULT NULL,\r\n" + "  `seller` INT(11) NULL DEFAULT NULL,\r\n"
				+ "  `image` INT(11) NULL DEFAULT NULL,\r\n" + "  PRIMARY KEY (`serial`))\r\n" + "ENGINE = InnoDB\r\n"
				+ "DEFAULT CHARACTER SET = utf8;");

		execute("CREATE TABLE IF NOT EXISTS `computer`.`review` (\r\n"
				+ "  `serial` INT(11) NOT NULL AUTO_INCREMENT,\r\n" + "  `user` INT(11) NULL DEFAULT NULL,\r\n"
				+ "  `product` INT(11) NULL DEFAULT NULL,\r\n" + "  `seller` INT(11) NULL DEFAULT NULL,\r\n"
				+ "  `rating` INT(11) NULL DEFAULT NULL,\r\n" + "  PRIMARY KEY (`serial`))\r\n" + "ENGINE = InnoDB\r\n"
				+ "DEFAULT CHARACTER SET = utf8;");

		execute("CREATE TABLE IF NOT EXISTS `computer`.`stock` (\r\n"
				+ "  `serial` INT(11) NOT NULL AUTO_INCREMENT,\r\n" + "  `seller` INT(11) NULL DEFAULT NULL,\r\n"
				+ "  `product` INT(11) NULL DEFAULT NULL,\r\n" + "  `count` INT(11) NULL DEFAULT NULL,\r\n"
				+ "  `price` INT(11) NULL DEFAULT NULL,\r\n" + "  PRIMARY KEY (`serial`))\r\n" + "ENGINE = InnoDB\r\n"
				+ "DEFAULT CHARACTER SET = utf8;");

		execute("CREATE TABLE IF NOT EXISTS `computer`.`image` (\r\n"
				+ "  `serial` INT(11) NOT NULL AUTO_INCREMENT,\r\n"
				+ "  `description` VARCHAR(40) NULL DEFAULT NULL,\r\n" + "  `image` LONGBLOB NULL DEFAULT NULL,\r\n"
				+ "  PRIMARY KEY (`serial`))\r\n" + "ENGINE = InnoDB\r\n" + "DEFAULT CHARACTER SET = utf8;");

		execute("use computer;");
		execute("drop user if exists 'user'@'%'");
		execute("create user 'user'@'%' identified by '1234';");
		execute("grant select, insert, delete, update on computer.* to 'user'@'%'");
		execute("flush privileges");

		execute("set global local_infile=1");

		for (String element : new String[] { "ad", "category", "image", "orderlist", "product", "review", "seller",
				"stock", "user" }) {
			execute("load data local infile './지급자료/" + element + ".txt' into table " + element
					+ " fields terminated by '\t' lines terminated by '\n' ignore 1 lines");
		}
		
		System.out.println("complete");
	}

	public static void execute(String sql) {
		try {
			statement.execute(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
