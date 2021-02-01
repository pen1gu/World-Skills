package db;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		execute("drop database if exists `parking`");
		execute("CREATE SCHEMA IF NOT EXISTS `parking` DEFAULT CHARACTER SET utf8 ;");

		execute("CREATE TABLE IF NOT EXISTS `parking`.`parking` (\r\n" + "  `p_no` INT(11) NOT NULL AUTO_INCREMENT,\r\n"
				+ "  `p_carno` INT(11) NULL DEFAULT NULL,\r\n" + "  PRIMARY KEY (`p_no`))\r\n" + "ENGINE = InnoDB\r\n"
				+ "DEFAULT CHARACTER SET = utf8;");

		execute("CREATE TABLE IF NOT EXISTS `parking`.`price` (\r\n" + "  `pr_no` INT(11) NOT NULL AUTO_INCREMENT,\r\n"
				+ "  `p_no` INT(11) NULL DEFAULT NULL,\r\n" + "  `I_no` INT(11) NULL DEFAULT NULL,\r\n"
				+ "  `in_time` VARCHAR(30) NULL DEFAULT NULL,\r\n" + "  `out_time` VARCHAR(30) NULL DEFAULT NULL,\r\n"
				+ "  `price` INT(11) NULL DEFAULT NULL,\r\n" + "  PRIMARY KEY (`pr_no`),\r\n"
				+ "  INDEX `fk_price_i_no_idx` (`p_no` ASC) VISIBLE,\r\n" + "  CONSTRAINT `fk_price_i_no`\r\n"
				+ "    FOREIGN KEY (`p_no`)\r\n" + "    REFERENCES `parking`.`parking` (`p_no`)\r\n"
				+ "    ON DELETE CASCADE\r\n" + "    ON UPDATE CASCADE)\r\n" + "ENGINE = InnoDB\r\n"
				+ "DEFAULT CHARACTER SET = utf8;");

		execute("CREATE TABLE IF NOT EXISTS `parking`.`time_event` (\r\n" + "  `time` DATETIME NOT NULL)\r\n"
				+ "ENGINE = InnoDB\r\n" + "DEFAULT CHARACTER SET = utf8;");

		execute("CREATE TABLE IF NOT EXISTS `parking`.`image` (\r\n" + "  `no` INT(11) NOT NULL AUTO_INCREMENT,\r\n"
				+ "  `image_car` LONGBLOB NULL DEFAULT NULL,\r\n" + "  `car_color` INT(11) NULL DEFAULT NULL,\r\n"
				+ "  PRIMARY KEY (`no`))\r\n" + "ENGINE = InnoDB\r\n" + "DEFAULT CHARACTER SET = utf8;");

		execute("CREATE TABLE IF NOT EXISTS `parking`.`car` (\r\n" + "  `car_no` INT(11) NOT NULL AUTO_INCREMENT,\r\n"
				+ "  `p_no` INT(11) NULL DEFAULT NULL,\r\n" + "  `car_kind` INT(11) NULL DEFAULT NULL,\r\n"
				+ "  `car_color` INT(11) NULL DEFAULT NULL,\r\n" + "  PRIMARY KEY (`car_no`),\r\n"
				+ "  INDEX `fk_car_p_no_idx` (`p_no` ASC) VISIBLE,\r\n"
				+ "  INDEX `fk_car_color_idx` (`car_color` ASC) VISIBLE,\r\n" + "  CONSTRAINT `fk_car_p_no`\r\n"
				+ "    FOREIGN KEY (`p_no`)\r\n" + "    REFERENCES `parking`.`parking` (`p_no`)\r\n"
				+ "    ON DELETE CASCADE\r\n" + "    ON UPDATE CASCADE,\r\n" + "  CONSTRAINT `fk_car_color`\r\n"
				+ "    FOREIGN KEY (`car_color`)\r\n" + "    REFERENCES `parking`.`image` (`no`)\r\n"
				+ "    ON DELETE NO ACTION\r\n" + "    ON UPDATE NO ACTION)\r\n" + "ENGINE = InnoDB\r\n"
				+ "DEFAULT CHARACTER SET = utf8;");

		execute("use parking;");
		execute("drop user if exists 'user'@'%';");
		execute("create user 'user'@'%' identified by '1234'");
		execute("grant select, insert, delete, update on parking.* to 'user'@'%';");
		execute("flush privileges;");

		execute("set global local_infile = 1");

		for (String fileName : new String[] { "승용차", "승합차", "오토바이", "장애인", "화물차" }) {
			for (int i = 1; i <= 3; i++) {
				File file = new File("./지급자료/image/" + fileName + i + ".png");
				try (PreparedStatement pstmt = connection.prepareStatement("insert into image values(0,?,?)")) {
					InputStream is = new FileInputStream(file);

					pstmt.setBinaryStream(1, is);
					pstmt.setInt(2, i);

					pstmt.execute();
				} catch (FileNotFoundException | SQLException e) {
					e.printStackTrace();
				}
			}
		}

		for (String element : new String[] { "parking", "price", "car" }) {
			execute("load data local infile './지급자료/data/" + element + ".txt' into table `" + element
					+ "` fields terminated by '\t' lines terminated by '\n' ignore 1 lines");
		}

		System.out.println("완성");
	}// 저기 옆에

	public static void execute(String sql) {
		try {
			statement.execute(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
