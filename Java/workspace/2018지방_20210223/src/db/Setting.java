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
		execute("drop database if exists `meal`");
		execute("CREATE SCHEMA IF NOT EXISTS `meal` DEFAULT CHARACTER SET utf8 ;");
		execute("CREATE TABLE IF NOT EXISTS `meal`.`member` (\r\n" + "  `memberNo` INT(11) NOT NULL AUTO_INCREMENT,\r\n"
				+ "  `memberName` VARCHAR(20) NULL DEFAULT NULL,\r\n" + "  `passwd` VARCHAR(4) NULL DEFAULT NULL,\r\n"
				+ "  PRIMARY KEY (`memberNo`))\r\n" + "ENGINE = InnoDB\r\n" + "DEFAULT CHARACTER SET = utf8;");
		execute("CREATE TABLE IF NOT EXISTS `meal`.`cuisine` (\r\n"
				+ "  `cuisineNo` INT(11) NOT NULL AUTO_INCREMENT,\r\n"
				+ "  `cuisineName` VARCHAR(10) NULL DEFAULT NULL,\r\n" + "  PRIMARY KEY (`cuisineNo`))\r\n"
				+ "ENGINE = InnoDB\r\n" + "DEFAULT CHARACTER SET = utf8;");
		execute("CREATE TABLE IF NOT EXISTS `meal`.`meal` (\r\n" + "  `mealNo` INT(11) NOT NULL AUTO_INCREMENT,\r\n"
				+ "  `cuisineNo` INT(11) NULL DEFAULT NULL,\r\n" + "  `mealName` VARCHAR(20) NULL DEFAULT NULL,\r\n"
				+ "  `price` INT(11) NULL DEFAULT NULL,\r\n" + "  `maxCount` INT(11) NULL DEFAULT NULL,\r\n"
				+ "  `todayMeal` TINYINT(1) NULL DEFAULT NULL,\r\n" + "  PRIMARY KEY (`mealNo`),\r\n"
				+ "  INDEX `fk_meal_cuisineNo_idx` (`cuisineNo` ASC) VISIBLE,\r\n"
				+ "  CONSTRAINT `fk_meal_cuisineNo`\r\n" + "    FOREIGN KEY (`cuisineNo`)\r\n"
				+ "    REFERENCES `meal`.`cuisine` (`cuisineNo`)\r\n" + "    ON DELETE CASCADE\r\n"
				+ "    ON UPDATE CASCADE)\r\n" + "ENGINE = InnoDB\r\n" + "DEFAULT CHARACTER SET = utf8;");
		execute("CREATE TABLE IF NOT EXISTS `meal`.`orderlist` (\r\n"
				+ "  `orderNo` INT(11) NOT NULL AUTO_INCREMENT,\r\n" + "  `cuisineNo` INT(11) NULL DEFAULT NULL,\r\n"
				+ "  `mealNo` INT(11) NULL DEFAULT NULL,\r\n" + "  `memberNo` INT(11) NULL DEFAULT NULL,\r\n"
				+ "  `orderCount` INT(11) NULL DEFAULT NULL,\r\n" + "  `amount` INT(11) NULL DEFAULT NULL,\r\n"
				+ "  `orderDate` DATETIME NULL DEFAULT NULL,\r\n" + "  PRIMARY KEY (`orderNo`),\r\n"
				+ "  INDEX `fk_order_mealNo_idx` (`mealNo` ASC) VISIBLE,\r\n"
				+ "  INDEX `fk_order_cuisineNo_idx` (`cuisineNo` ASC) VISIBLE,\r\n"
				+ "  INDEX `fk_order_memberNo_idx` (`memberNo` ASC) VISIBLE,\r\n" + "  CONSTRAINT `fk_order_mealNo`\r\n"
				+ "    FOREIGN KEY (`mealNo`)\r\n" + "    REFERENCES `meal`.`meal` (`mealNo`)\r\n"
				+ "    ON DELETE CASCADE\r\n" + "    ON UPDATE CASCADE,\r\n" + "  CONSTRAINT `fk_order_cuisineNo`\r\n"
				+ "    FOREIGN KEY (`cuisineNo`)\r\n" + "    REFERENCES `meal`.`cuisine` (`cuisineNo`)\r\n"
				+ "    ON DELETE CASCADE\r\n" + "    ON UPDATE CASCADE,\r\n" + "  CONSTRAINT `fk_order_memberNo`\r\n"
				+ "    FOREIGN KEY (`memberNo`)\r\n" + "    REFERENCES `meal`.`member` (`memberNo`)\r\n"
				+ "    ON DELETE CASCADE\r\n" + "    ON UPDATE CASCADE)\r\n" + "ENGINE = InnoDB\r\n"
				+ "DEFAULT CHARACTER SET = utf8;");

		execute("use meal;");
		execute("drop user if exists 'user'@'%';");
		execute("create user 'user'@'%' identified by '1234';");
		execute("grant select, insert, delete, update on meal.* to 'user'@'%';");
		execute("flush privileges;");

		execute("set global local_infile = 1;");

		for (String element : new String[] { "member", "cuisine", "meal", "orderlist" }) {
			execute("load data local infile './지급자료/Datafiles/" + element + ".txt' into table " + element
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
