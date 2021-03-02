package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Setting {

	static Connection connection;
	static Statement statement;

	static {
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost?&serverTimezone=UTC&allowLoadLocalInfile=true",
					"root", "1234");
			statement = connection.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {

		// ��� ����
		execute("drop database if exists `hospital`;");
		execute("CREATE SCHEMA IF NOT EXISTS `hospital` DEFAULT CHARACTER SET utf8 ;");

		// ���̺�
		execute("CREATE TABLE IF NOT EXISTS `hospital`.`examination` (\r\n"
				+ "  `e_no` INT(11) NOT NULL AUTO_INCREMENT,\r\n" + "  `e_name` VARCHAR(10) NULL DEFAULT NULL,\r\n"
				+ "  PRIMARY KEY (`e_no`))\r\n" + "ENGINE = InnoDB\r\n" + "DEFAULT CHARACTER SET = utf8;");

		execute("CREATE TABLE IF NOT EXISTS `hospital`.`Patient` (\r\n"
				+ "  `p_no` INT(11) NOT NULL AUTO_INCREMENT,\r\n" + "  `p_name` VARCHAR(10) NULL DEFAULT NULL,\r\n"
				+ "  `p_id` VARCHAR(15) NULL DEFAULT NULL,\r\n" + "  `p_pw` VARCHAR(10) NULL DEFAULT NULL,\r\n"
				+ "  `p_bd` DATE NULL DEFAULT NULL,\r\n" + "  PRIMARY KEY (`p_no`))\r\n" + "ENGINE = InnoDB\r\n"
				+ "DEFAULT CHARACTER SET = utf8;");

		execute("CREATE TABLE IF NOT EXISTS `hospital`.`doctor` (\r\n" + "  `d_no` INT(11) NOT NULL AUTO_INCREMENT,\r\n"
				+ "  `d_section` VARCHAR(10) NULL DEFAULT NULL,\r\n" + "  `d_name` VARCHAR(15) NULL DEFAULT NULL,\r\n"
				+ "  `d_day` VARCHAR(1) NULL DEFAULT NULL,\r\n" + "  `d_time` VARCHAR(2) NULL DEFAULT NULL,\r\n"
				+ "  PRIMARY KEY (`d_no`))\r\n" + "ENGINE = InnoDB\r\n" + "DEFAULT CHARACTER SET = utf8;");

		execute("CREATE TABLE IF NOT EXISTS `hospital`.`reservation` (\r\n"
				+ "  `r_no` INT(11) NOT NULL AUTO_INCREMENT,\r\n" + "  `p_no` INT(11) NULL DEFAULT NULL,\r\n"
				+ "  `d_no` INT(11) NULL DEFAULT NULL,\r\n" + "  `r_section` VARCHAR(10) NULL DEFAULT NULL,\r\n"
				+ "  `r_date` VARCHAR(14) NULL DEFAULT NULL,\r\n" + "  `r_time` VARCHAR(10) NULL DEFAULT NULL,\r\n"
				+ "  `e_no` INT(11) NULL DEFAULT NULL,\r\n" + "  PRIMARY KEY (`r_no`),\r\n"
				+ "  INDEX `rp_no_idx` (`p_no` ASC) VISIBLE,\r\n" + "  INDEX `rd_no_idx` (`d_no` ASC) INVISIBLE,\r\n"
				+ "  INDEX `re_no_idx` (`e_no` ASC) VISIBLE,\r\n" + "  CONSTRAINT `rp_no_fk`\r\n"
				+ "    FOREIGN KEY (`p_no`)\r\n" + "    REFERENCES `hospital`.`Patient` (`p_no`)\r\n"
				+ "    ON DELETE CASCADE\r\n" + "    ON UPDATE CASCADE,\r\n" + "  CONSTRAINT `rd_no_fk`\r\n"
				+ "    FOREIGN KEY (`d_no`)\r\n" + "    REFERENCES `hospital`.`doctor` (`d_no`)\r\n"
				+ "    ON DELETE CASCADE\r\n" + "    ON UPDATE CASCADE,\r\n" + "  CONSTRAINT `re_no_fk`\r\n"
				+ "    FOREIGN KEY (`e_no`)\r\n" + "    REFERENCES `hospital`.`examination` (`e_no`)\r\n"
				+ "    ON DELETE CASCADE\r\n" + "    ON UPDATE CASCADE)\r\n" + "ENGINE = InnoDB\r\n"
				+ "DEFAULT CHARACTER SET = utf8;");

		execute("CREATE TABLE IF NOT EXISTS `hospital`.`sickroom` (\r\n"
				+ "  `s_no` INT(11) NOT NULL AUTO_INCREMENT,\r\n" + "  `s_people` INT(11) NULL DEFAULT NULL,\r\n"
				+ "  `s_room` INT(11) NULL DEFAULT NULL,\r\n" + "  `s_roomno` VARCHAR(20) NULL DEFAULT NULL,\r\n"
				+ "  PRIMARY KEY (`s_no`))\r\n" + "ENGINE = InnoDB\r\n" + "DEFAULT CHARACTER SET = utf8;");

		execute("CREATE TABLE IF NOT EXISTS `hospital`.`hospitalization` (\r\n"
				+ "  `h_no` INT(11) NOT NULL AUTO_INCREMENT,\r\n" + "  `p_no` INT(11) NULL DEFAULT NULL,\r\n"
				+ "  `s_no` INT(11) NULL DEFAULT NULL,\r\n" + "  `h_bedno` INT(11) NULL DEFAULT NULL,\r\n"
				+ "  `h_sday` VARCHAR(14) NULL DEFAULT NULL,\r\n" + "  `h_fday` VARCHAR(14) NULL DEFAULT NULL,\r\n"
				+ "  `h_meal` INT(11) NULL DEFAULT NULL,\r\n" + "  `h_amount` INT(11) NULL DEFAULT NULL,\r\n"
				+ "  PRIMARY KEY (`h_no`),\r\n" + "  INDEX `hp_no_idx` (`p_no` ASC) INVISIBLE,\r\n"
				+ "  INDEX `hs_no_idx` (`s_no` ASC) VISIBLE,\r\n" + "  CONSTRAINT `hp_no_fk`\r\n"
				+ "    FOREIGN KEY (`p_no`)\r\n" + "    REFERENCES `hospital`.`Patient` (`p_no`)\r\n"
				+ "    ON DELETE CASCADE\r\n" + "    ON UPDATE CASCADE,\r\n" + "  CONSTRAINT `hs_no_fk`\r\n"
				+ "    FOREIGN KEY (`s_no`)\r\n" + "    REFERENCES `hospital`.`sickroom` (`s_no`)\r\n"
				+ "    ON DELETE CASCADE\r\n" + "    ON UPDATE CASCADE)\r\n" + "ENGINE = InnoDB\r\n"
				+ "DEFAULT CHARACTER SET = utf8;");

		execute("use hospital");

		// ���� ����
		execute("drop user if exists 'user'@'%';");
		execute("create user 'user'@'%' identified by '1234';");
		execute("grant select, delete, insert, update on `hospital`.* to 'user'@'%';");
		execute("flush privileges;");
		execute("set global local_infile=1");

		String[] list = "patient,doctor,examination,reservation,sickroom,hospitalization".split(",");

		for (int i = 0; i < list.length; i++) {
			execute("load data local infile './/Datafiles/" + list[i] + ".txt' into table " + list[i]
					+ " fields terminated  by '\t'" + " lines terminated  by '\n'" + " ignore 1 lines");
		}

		System.out.println("완성");
	}

	private static void execute(String sql) {
		try {
			statement.execute(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}