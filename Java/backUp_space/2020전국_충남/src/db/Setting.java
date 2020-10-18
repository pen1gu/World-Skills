package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Setting {
	
	static Connection connection;
	static Statement statement;
	
	static {
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost?serverTimezone=UTC&allowLoadLocalInfile=true");
			statement = connection.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		execute("drop database if exists `parking`");
		execute("CREATE SCHEMA IF NOT EXISTS `parking` DEFAULT CHARACTER SET utf8 ;");
		
		execute("CREATE TABLE IF NOT EXISTS `parking`.`parking` (\r\n" + 
				"  `p_no` INT(11) NOT NULL AUTO_INCREMENT,\r\n" + 
				"  `p_carno` INT(11) NULL DEFAULT NULL,\r\n" + 
				"  PRIMARY KEY (`p_no`))\r\n" + 
				"ENGINE = InnoDB\r\n" + 
				"DEFAULT CHARACTER SET = utf8;");
		
		execute("CREATE TABLE IF NOT EXISTS `parking`.`price` (\r\n" + 
				"  `pr_no` INT(11) NOT NULL AUTO_INCREMENT,\r\n" + 
				"  `p_no` INT(11) NULL DEFAULT NULL,\r\n" + 
				"  `l_no` INT(11) NULL DEFAULT NULL,\r\n" + 
				"  `in_time` DATETIME NULL DEFAULT NULL,\r\n" + 
				"  `out_time` DATETIME NULL DEFAULT NULL,\r\n" + 
				"  `price` INT(11) NULL DEFAULT NULL,\r\n" + 
				"  PRIMARY KEY (`pr_no`))\r\n" + 
				"ENGINE = InnoDB\r\n" + 
				"DEFAULT CHARACTER SET = utf8;");
		
		execute("CREATE TABLE IF NOT EXISTS `parking`.`car` (\r\n" + 
				"  `car_no` INT(11) NOT NULL AUTO_INCREMENT,\r\n" + 
				"  `car_kind` INT(11) NULL DEFAULT NULL,\r\n" + 
				"  `image_no` INT(11) NULL DEFAULT NULL,\r\n" + 
				"  PRIMARY KEY (`car_no`))\r\n" + 
				"ENGINE = InnoDB\r\n" + 
				"DEFAULT CHARACTER SET = utf8;");
		
		execute("CREATE TABLE IF NOT EXISTS `parking`.`time_event` (\r\n" + 
				"  `time` DATETIME NULL DEFAULT NULL)\r\n" + 
				"ENGINE = InnoDB\r\n" + 
				"DEFAULT CHARACTER SET = utf8;");
		
		execute("CREATE TABLE IF NOT EXISTS `parking`.`image` (\r\n" + 
				"  `image_no` INT(11) NOT NULL AUTO_INCREMENT,\r\n" + 
				"  `image_car` LONGBLOB NULL DEFAULT NULL,\r\n" + 
				"  PRIMARY KEY (`image_no`))\r\n" + 
				"ENGINE = InnoDB\r\n" + 
				"DEFAULT CHARACTER SET = utf8;");
		
		execute("use parking");
		execute("drop user if exists 'user'@'%'");
		execute("create user 'user'@'%' identified by '1234'");
		execute("grant select, insert, update, delete on 'user'@'%'  ");
		execute("plush privileges");
		
		execute("set global local_infile=1");		
		
		String[] arr = "parking,price,car,time_event,image".split(",");
		for (int i = 0; i < arr.length; i++) {
			execute("");
		}
		
	}
	
	public static void execute(String sql) {
		try {
			statement.execute(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
