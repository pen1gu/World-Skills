package setting;

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
		execute("drop database if exists `market`");
		execute("CREATE SCHEMA IF NOT EXISTS `market` DEFAULT CHARACTER SET utf8 ;");
		execute("CREATE TABLE IF NOT EXISTS `market`.`category` (\r\n" + "  `c_name` VARCHAR(10) NULL DEFAULT NULL,\r\n"
				+ "  `c_no` INT(11) NOT NULL AUTO_INCREMENT,\r\n" + "  PRIMARY KEY (`c_no`))\r\n"
				+ "ENGINE = InnoDB\r\n" + "DEFAULT CHARACTER SET = utf8;");

		execute("CREATE TABLE IF NOT EXISTS `market`.`product` (\r\n" + "  `p_no` INT(11) NOT NULL AUTO_INCREMENT,\r\n"
				+ "  `c_no` INT(11) NULL DEFAULT NULL,\r\n" + "  `p_name` VARCHAR(20) NULL DEFAULT NULL,\r\n"
				+ "  `p_price` INT(11) NULL DEFAULT NULL,\r\n" + "  `p_stock` INT(11) NULL DEFAULT NULL,\r\n"
				+ "  `p_explanation` VARCHAR(150) NULL DEFAULT NULL,\r\n" + "  PRIMARY KEY (`p_no`),\r\n"
				+ "  INDEX `fk_p_c_no_idx` (`c_no` ASC) VISIBLE,\r\n" + "  CONSTRAINT `fk_p_c_no`\r\n"
				+ "    FOREIGN KEY (`c_no`)\r\n" + "    REFERENCES `market`.`category` (`c_no`)\r\n"
				+ "    ON DELETE CASCADE\r\n" + "    ON UPDATE CASCADE)\r\n" + "ENGINE = InnoDB\r\n"
				+ "DEFAULT CHARACTER SET = utf8;");
		execute("CREATE TABLE IF NOT EXISTS `market`.`purchase` (\r\n"
				+ "  `pu_no` INT(11) NOT NULL AUTO_INCREMENT,\r\n" + "  `p_no` INT(11) NULL DEFAULT NULL,\r\n"
				+ "  `pu_price` INT(11) NULL DEFAULT NULL,\r\n" + "  `Pu_total` INT(11) NULL DEFAULT NULL,\r\n"
				+ "  `u_no` INT(11) NULL DEFAULT NULL,\r\n" + "  PRIMARY KEY (`pu_no`),\r\n"
				+ "  INDEX `fk_pu_p_no_idx` (`p_no` ASC) VISIBLE,\r\n" + "  CONSTRAINT `fk_pu_p_no`\r\n"
				+ "    FOREIGN KEY (`p_no`)\r\n" + "    REFERENCES `market`.`product` (`p_no`)\r\n"
				+ "    ON DELETE CASCADE\r\n" + "    ON UPDATE CASCADE)\r\n" + "ENGINE = InnoDB\r\n"
				+ "DEFAULT CHARACTER SET = utf8;");

		execute("CREATE TABLE IF NOT EXISTS `market`.`user` (\r\n" + "  `u_no` INT(11) NOT NULL AUTO_INCREMENT,\r\n"
				+ "  `u_id` VARCHAR(20) NULL DEFAULT NULL,\r\n" + "  `u_pw` VARCHAR(20) NULL DEFAULT NULL,\r\n"
				+ "  `u_address` VARCHAR(50) NULL DEFAULT NULL,\r\n" + "  `u_name` VARCHAR(15) NULL DEFAULT NULL,\r\n"
				+ "  `u_phone` VARCHAR(20) NULL DEFAULT NULL,\r\n" + "  `u_age` VARCHAR(20) NULL DEFAULT NULL,\r\n"
				+ "  PRIMARY KEY (`u_no`),\r\n" + "  CONSTRAINT `fk_u_p_no`\r\n" + "    FOREIGN KEY (`u_no`)\r\n"
				+ "    REFERENCES `market`.`purchase` (`pu_no`)\r\n" + "    ON DELETE CASCADE\r\n"
				+ "    ON UPDATE CASCADE)\r\n" + "ENGINE = InnoDB\r\n" + "DEFAULT CHARACTER SET = utf8;");

		execute("use market");
		execute("drop user if exists 'user'@'%'");
		execute("create user 'user'@'%' identified by '1234'");
		execute("grant select, insert, delete, update on market.* to 'user'@'%'");
		execute("flush privileges");

		execute("set global local_infile=1");

		for (String element : new String[] { "category", "product", "purchase", "user" }) { // cascade 설정으로 인해 이 순서대로
																							// 넣어야함
			execute("load data local infile './지급자료/" + element + ".txt' into table " + element
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
