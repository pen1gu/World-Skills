package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javax.swing.JOptionPane;

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
		execute("drop database if exists `wedding`");
		execute("CREATE SCHEMA IF NOT EXISTS `wedding` DEFAULT CHARACTER SET utf8 ;");
		execute("CREATE TABLE IF NOT EXISTS `wedding`.`weddinghall` (\r\n"
				+ "  `wNo` INT(11) NOT NULL AUTO_INCREMENT,\r\n" + "  `wname` VARCHAR(20) NULL DEFAULT NULL,\r\n"
				+ "  `wadd` VARCHAR(50) NULL DEFAULT NULL,\r\n" + "  `wpeople` INT(11) NULL DEFAULT NULL,\r\n"
				+ "  `wprice` INT(11) NULL DEFAULT NULL,\r\n" + "  PRIMARY KEY (`wNo`))\r\n" + "ENGINE = InnoDB\r\n"
				+ "DEFAULT CHARACTER SET = utf8;");

		execute("CREATE TABLE IF NOT EXISTS `wedding`.`weddingtype` (\r\n"
				+ "  `tyNo` INT(11) NOT NULL AUTO_INCREMENT,\r\n" + "  `tyname` VARCHAR(15) NULL DEFAULT NULL,\r\n"
				+ "  PRIMARY KEY (`tyNo`))\r\n" + "ENGINE = InnoDB\r\n" + "DEFAULT CHARACTER SET = utf8;");

		execute("CREATE TABLE IF NOT EXISTS `wedding`.`mealtype` (\r\n" + "  `mNo` INT(11) NOT NULL AUTO_INCREMENT,\r\n"
				+ "  `mname` VARCHAR(5) NULL DEFAULT NULL,\r\n" + "  `mprice` INT(11) NULL DEFAULT NULL,\r\n"
				+ "  PRIMARY KEY (`mNo`))\r\n" + "ENGINE = InnoDB\r\n" + "DEFAULT CHARACTER SET = utf8;");

		execute("CREATE TABLE IF NOT EXISTS `wedding`.`reservation` (\r\n"
				+ "  `rNo` INT(11) NOT NULL AUTO_INCREMENT,\r\n" + "  `wNo` INT(11) NULL DEFAULT NULL,\r\n"
				+ "  `rpeople` INT(11) NULL DEFAULT NULL,\r\n" + "  `tyNo` INT(11) NULL DEFAULT NULL,\r\n"
				+ "  `mNo` INT(11) NULL DEFAULT NULL,\r\n" + "  `album` INT(11) NULL DEFAULT NULL,\r\n"
				+ "  `letter` INT(11) NULL DEFAULT NULL,\r\n" + "  `dress` INT(11) NULL DEFAULT NULL,\r\n"
				+ "  `rdate` INT(11) NULL DEFAULT NULL,\r\n" + "  `pay` INT(11) NULL DEFAULT NULL,\r\n"
				+ "  PRIMARY KEY (`rNo`),\r\n" + "  INDEX `r_wNo_fk_idx` (`wNo` ASC) VISIBLE,\r\n"
				+ "  INDEX `r_tyNo_fk_idx` (`tyNo` ASC) VISIBLE,\r\n"
				+ "  INDEX `r_mNo_fk_idx` (`mNo` ASC) VISIBLE,\r\n" + "  CONSTRAINT `r_wNo_fk`\r\n"
				+ "    FOREIGN KEY (`wNo`)\r\n" + "    REFERENCES `wedding`.`weddinghall` (`wNo`)\r\n"
				+ "    ON DELETE CASCADE\r\n" + "    ON UPDATE CASCADE,\r\n" + "  CONSTRAINT `r_tyNo_fk`\r\n"
				+ "    FOREIGN KEY (`tyNo`)\r\n" + "    REFERENCES `wedding`.`weddingtype` (`tyNo`)\r\n"
				+ "    ON DELETE CASCADE\r\n" + "    ON UPDATE CASCADE,\r\n" + "  CONSTRAINT `r_mNo_fk`\r\n"
				+ "    FOREIGN KEY (`mNo`)\r\n" + "    REFERENCES `wedding`.`mealtype` (`mNo`)\r\n"
				+ "    ON DELETE CASCADE\r\n" + "    ON UPDATE CASCADE)\r\n" + "ENGINE = InnoDB\r\n"
				+ "DEFAULT CHARACTER SET = utf8;");

		execute("CREATE TABLE IF NOT EXISTS `wedding`.`w_ty` (\r\n" + "  `wNo` INT(11) NULL DEFAULT NULL,\r\n"
				+ "`tyNo` INT(11) NULL DEFAULT NULL)\r\n" + "ENGINE = InnoDB\r\n" + "DEFAULT CHARACTER SET = utf8;");

		execute("CREATE TABLE IF NOT EXISTS `wedding`.`w_m` (\r\n" + "  `wNo` INT(11) NULL DEFAULT NULL,\r\n"
				+ "  `mNo` INT(11) NULL DEFAULT NULL)\r\n" + "ENGINE = InnoDB\r\n" + "DEFAULT CHARACTER SET = utf8;");
		execute("CREATE TABLE IF NOT EXISTS `wedding`.`w_ty` (\r\n"
				+ "  `wNo` INT(11) NULL DEFAULT NULL,\r\n"
				+ "  `mNo` INT(11) NULL DEFAULT NULL,\r\n"
				+ "  `tyNo` INT(11) NULL DEFAULT NULL) \r\n"
				 +" ENGINE = InnoDB\r\n" + "DEFAULT CHARACTER SET = utf8;");

		execute("CREATE TABLE IF NOT EXISTS `wedding`.`w_m` (\r\n" 
				+ "  `wNo` INT(11) NULL DEFAULT NULL,\r\n"
				+ "  `mNo` INT(11) NULL DEFAULT NULL)\r\n" 
				+ "ENGINE = InnoDB\r\n" + "DEFAULT CHARACTER SET = utf8;");

		execute("use wedding;");
		execute("drop user if exists 'user'@'%'");
		execute("create user 'user'@'%' identified by '1234'");
		execute("grant select, insert, update, delete on `wedding`.* to 'user'@'%'");
		execute("flush privileges");

		execute("set global local_infile=1");

		for (String table : new String[] { "mealtype", "weddingtype", "w_m", "w_ty", "weddinghall", "reservation" }) {
			execute("load data local infile './datafiles/" + table + ".txt' into table " + table
					+ " fields terminated by '\t' lines terminated by '\n' ignore 1 lines");
		}

		JOptionPane.showMessageDialog(null, "완성");

	}

	public static void execute(String sql) {
		try {
			statement.execute(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
