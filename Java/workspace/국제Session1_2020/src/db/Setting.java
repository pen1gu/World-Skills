package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javax.swing.JFrame;

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
		execute("drop database if exists `Session1`");
		execute("CREATE SCHEMA IF NOT EXISTS `Session1` DEFAULT CHARACTER SET utf8 ;");
		execute("CREATE TABLE IF NOT EXISTS `Session1`.`member` (\r\n" + "  `No` INT(11) NOT NULL AUTO_INCREMENT,\r\n"
				+ "  `id` VARCHAR(10) NULL DEFAULT NULL,\r\n" + "  `pw` VARCHAR(10) NULL DEFAULT NULL,\r\n"
				+ "  `name` VARCHAR(10) NULL DEFAULT NULL,\r\n" + "  `birthday` DATE NULL DEFAULT NULL,\r\n"
				+ "  `gender` VARCHAR(4) NULL DEFAULT NULL,\r\n" + "  `phone` VARCHAR(20) NULL DEFAULT NULL,\r\n"
				+ "  `membership` VARCHAR(8) NULL DEFAULT NULL,\r\n" + "  `point` INT(11) NULL DEFAULT NULL,\r\n"
				+ "  `class` VARCHAR(20) NULL DEFAULT NULL,\r\n" + "  `amount` INT(11) NULL DEFAULT NULL,\r\n"
				+ "  PRIMARY KEY (`No`))\r\n" + "ENGINE = InnoDB\r\n" + "DEFAULT CHARACTER SET = utf8;");

		execute("CREATE TABLE IF NOT EXISTS `Session1`.`music` (\r\n" + "  `No` INT(11) NOT NULL AUTO_INCREMENT,\r\n"
				+ "  `title` VARCHAR(30) NULL DEFAULT NULL,\r\n" + "  `singer` VARCHAR(20) NULL DEFAULT NULL,\r\n"
				+ "  `genre` VARCHAR(10) NULL DEFAULT NULL,\r\n" + "  `date` DATE NULL DEFAULT NULL,\r\n"
				+ "  PRIMARY KEY (`No`))\r\n" + "ENGINE = InnoDB\r\n" + "DEFAULT CHARACTER SET = utf8;");

		execute("CREATE TABLE IF NOT EXISTS `Session1`.`listen` (\r\n" + "  `No` INT(11) NOT NULL AUTO_INCREMENT,\r\n"
				+ "  `music_no` INT(11) NULL DEFAULT NULL,\r\n" + "  `member_no` INT(11) NULL DEFAULT NULL,\r\n"
				+ "  `rating` VARCHAR(30) NULL DEFAULT NULL,\r\n" + "  PRIMARY KEY (`No`),\r\n"
				+ "  INDEX `fk_listen_music_no_idx` (`music_no` ASC) VISIBLE,\r\n"
				+ "  INDEX `fk_listen_member_no_idx` (`member_no` ASC) VISIBLE,\r\n"
				+ "  CONSTRAINT `fk_listen_music_no`\r\n" + "    FOREIGN KEY (`music_no`)\r\n"
				+ "    REFERENCES `Session1`.`music` (`No`)\r\n" + "    ON DELETE CASCADE\r\n"
				+ "    ON UPDATE CASCADE,\r\n" + "  CONSTRAINT `fk_listen_member_no`\r\n"
				+ "    FOREIGN KEY (`member_no`)\r\n" + "    REFERENCES `Session1`.`member` (`No`)\r\n"
				+ "    ON DELETE CASCADE\r\n" + "    ON UPDATE CASCADE)\r\n" + "ENGINE = InnoDB\r\n"
				+ "DEFAULT CHARACTER SET = utf8;\r\n");

		execute("CREATE TABLE IF NOT EXISTS `Session1`.`buy` (\r\n" + "  `No` INT(11) NOT NULL,\r\n"
				+ "  `music_no` INT(11) NULL DEFAULT NULL,\r\n" + "  `member_no` INT(11) NULL DEFAULT NULL,\r\n"
				+ "  `DATE` DATE NULL DEFAULT NULL,\r\n" + "  `amount` INT(11) NULL DEFAULT NULL,\r\n"
				+ "  PRIMARY KEY (`No`),\r\n" + "  INDEX `fk_buy_music_no_idx` (`music_no` ASC) VISIBLE,\r\n"
				+ "  INDEX `fk_buy_member_no_idx` (`member_no` ASC) VISIBLE,\r\n" + "  CONSTRAINT `fk_buy_music_no`\r\n"
				+ "    FOREIGN KEY (`music_no`)\r\n" + "    REFERENCES `Session1`.`music` (`No`)\r\n"
				+ "    ON DELETE CASCADE\r\n" + "    ON UPDATE CASCADE,\r\n" + "  CONSTRAINT `fk_buy_member_no`\r\n"
				+ "    FOREIGN KEY (`member_no`)\r\n" + "    REFERENCES `Session1`.`member` (`No`)\r\n"
				+ "    ON DELETE CASCADE\r\n" + "    ON UPDATE CASCADE)\r\n" + "ENGINE = InnoDB\r\n"
				+ "DEFAULT CHARACTER SET = utf8;");

		execute("use Session1;");
		execute("drop user if exists 'user'@'%';");
		execute("create user 'user'@'%' identified by '1234';");
		execute("grant select, update, delete, insert on Session1.* to 'user'@'%';");
		execute("flush privileges;");

		execute("set global local_infile = 1");

		for (String table : new String[] { "member", "music", "listen", "buy" }) {
			execute("load data local infile './Datafiles/" + table + ".txt' into table " + table
					+ " fields terminated by '\t' lines terminated by '\n' ignore 1 lines");
		}
		System.out.println("완료");
	}

	public static void execute(String sql) {
		try {
			statement.execute(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
