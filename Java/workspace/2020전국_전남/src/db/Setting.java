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
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		execute("drop database if exists `Projectlibre`");

		execute("CREATE SCHEMA IF NOT EXISTS `Projectlibre` DEFAULT CHARACTER SET utf8 ;");

		execute("CREATE TABLE IF NOT EXISTS `Projectlibre`.`project` (\r\n"
				+ "  `p_num` INT(11) NOT NULL AUTO_INCREMENT,\r\n" + "  `p_title` VARCHAR(20) NULL DEFAULT NULL,\r\n"
				+ "  `p_name` VARCHAR(20) NULL DEFAULT NULL,\r\n" + "  `p_odate` DATE NULL DEFAULT NULL,\r\n"
				+ "  `p_edate` DATE NULL DEFAULT NULL,\r\n" + "  `p_note` VARCHAR(50) NULL DEFAULT NULL,\r\n"
				+ "  PRIMARY KEY (`p_num`))\r\n" + "ENGINE = InnoDB\r\n" + "DEFAULT CHARACTER SET = utf8;");

		execute("CREATE TABLE IF NOT EXISTS `Projectlibre`.`member` (\r\n"
				+ "  `no` INT(11) NOT NULL AUTO_INCREMENT,\r\n" + "  `p_num` INT(11) NULL DEFAULT NULL,\r\n"
				+ "  `id` VARCHAR(20) NULL DEFAULT NULL,\r\n" + "  `pw` VARCHAR(20) NULL DEFAULT NULL,\r\n"
				+ "  `name` VARCHAR(20) NULL DEFAULT NULL,\r\n" + "  `note` VARCHAR(50) NULL DEFAULT NULL,\r\n"
				+ "  PRIMARY KEY (`no`),\r\n" + "  INDEX `fk_member_p_num_idx` (`p_num` ASC) VISIBLE,\r\n"
				+ "  CONSTRAINT `fk_member_p_num`\r\n" + "    FOREIGN KEY (`p_num`)\r\n"
				+ "    REFERENCES `Projectlibre`.`project` (`p_num`)\r\n" + "    ON DELETE CASCADE\r\n"
				+ "    ON UPDATE CASCADE)\r\n" + "ENGINE = InnoDB\r\n" + "DEFAULT CHARACTER SET = utf8;");

		execute("CREATE TABLE IF NOT EXISTS `Projectlibre`.`job` (\r\n"
				+ "  `j_no` INT(11) NOT NULL AUTO_INCREMENT,\r\n" + "  `p_num` INT(11) NULL DEFAULT NULL,\r\n"
				+ "  `no` INT(11) NULL DEFAULT NULL,\r\n" + "  `j_period` INT(11) NULL DEFAULT NULL,\r\n"
				+ "  `j_percent` INT(11) NULL DEFAULT NULL,\r\n" + "  PRIMARY KEY (`j_no`),\r\n"
				+ "  INDEX `fk_job_p_num_idx` (`p_num` ASC) VISIBLE,\r\n"
				+ "  INDEX `fk_job_no_idx` (`no` ASC) VISIBLE,\r\n" + "  CONSTRAINT `fk_job_p_num`\r\n"
				+ "    FOREIGN KEY (`p_num`)\r\n" + "    REFERENCES `Projectlibre`.`project` (`p_num`)\r\n"
				+ "    ON DELETE CASCADE\r\n" + "    ON UPDATE CASCADE,\r\n" + "  CONSTRAINT `fk_job_no`\r\n"
				+ "    FOREIGN KEY (`no`)\r\n" + "    REFERENCES `Projectlibre`.`member` (`no`)\r\n"
				+ "    ON DELETE CASCADE\r\n" + "    ON UPDATE CASCADE)\r\n" + "ENGINE = InnoDB\r\n"
				+ "DEFAULT CHARACTER SET = utf8;");

		execute("CREATE TABLE IF NOT EXISTS `Projectlibre`.`detail` (\r\n"
				+ "  `d_no` INT(11) NOT NULL AUTO_INCREMENT,\r\n" + "  `j_no` INT(11) NULL DEFAULT NULL,\r\n"
				+ "  `d_odate` DATE NULL DEFAULT NULL,\r\n" + "  `d_edate` DATE NULL DEFAULT NULL,\r\n"
				+ "  `d_note` VARCHAR(20) NULL DEFAULT NULL,\r\n" + "  PRIMARY KEY (`d_no`)\r\n" + " )"
				+ "ENGINE = InnoDB\r\n" + "DEFAULT CHARACTER SET = utf8;");

		execute("CREATE TABLE IF NOT EXISTS `Projectlibre`.`time_sc` (\r\n"
				+ "  `t_no` INT(11) NOT NULL AUTO_INCREMENT,\r\n" + "  `d_no` INT(11) NULL DEFAULT NULL,\r\n"
				+ "  `t_sc` VARCHAR(500) NULL DEFAULT NULL,\r\n" + "  PRIMARY KEY (`t_no`),\r\n"
				+ "  INDEX `fk_time_d_no_idx` (`d_no` ASC) VISIBLE,\r\n" + "  CONSTRAINT `fk_time_d_no`\r\n"
				+ "    FOREIGN KEY (`d_no`)\r\n" + "    REFERENCES `Projectlibre`.`detail` (`d_no`)\r\n" + "   )\r\n"
				+ "ENGINE = InnoDB\r\n" + "DEFAULT CHARACTER SET = utf8;");

		execute("use Projectlibre;");
		execute("drop user if exists 'user'@'%'");
		execute("create user 'user'@'%' identified by '1234'");
		execute("grant select, insert, delete, update on Projectlibre.* to 'user'@'%'");
		execute("flush privileges");

		execute("set global local_infile = 1");

		for (String element : new String[] { "project", "member", "job", "detail", "time_sc" }) {
			execute("load data local infile './지급자료/" + element + ".txt' into table " + element
					+ " fields terminated by '\t' lines terminated by '\n' ignore 1 lines");
		}

		System.out.println("완성되었습니다.");
	}

	public static void execute(String sql) {
		try {
			statement.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(sql);
		}
	}
}
