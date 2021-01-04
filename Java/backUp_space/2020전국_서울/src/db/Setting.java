package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class Setting {

    static Connection connection;
    static Statement statement;

    static {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost?serverTimezone=UTC&allowLoadLocalInfile=true", "root", "1234");
            statement = connection.createStatement();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void main(String[] args) {
        execute("drop database if exists `reminder`");
        execute("CREATE SCHEMA IF NOT EXISTS `reminder` DEFAULT CHARACTER SET utf8 ;");
        execute("CREATE TABLE IF NOT EXISTS `reminder`.`member` (\n" +
                "  `M_no` INT(11) NOT NULL AUTO_INCREMENT,\n" +
                "  `M_id` VARCHAR(20) NULL DEFAULT NULL,\n" +
                "  `M_pw` VARCHAR(20) NULL DEFAULT NULL,\n" +
                "  `M_name` VARCHAR(10) NULL DEFAULT NULL,\n" +
                "  `M_phone` VARCHAR(20) NULL DEFAULT NULL,\n" +
                "  PRIMARY KEY (`M_no`))\n" +
                "ENGINE = InnoDB\n" +
                "DEFAULT CHARACTER SET = utf8;");
        execute("CREATE TABLE IF NOT EXISTS `reminder`.`news` (\n" +
                "  `N_no` INT(11) NOT NULL AUTO_INCREMENT,\n" +
                "  `N_title` VARCHAR(20) NULL DEFAULT NULL,\n" +
                "  `N_content` VARCHAR(1100) NULL DEFAULT NULL,\n" +
                "  `N_date` VARCHAR(20) NULL DEFAULT NULL,\n" +
                "  PRIMARY KEY (`N_no`))\n" +
                "ENGINE = InnoDB\n" +
                "DEFAULT CHARACTER SET = utf8;");
        execute("CREATE TABLE IF NOT EXISTS `reminder`.`notice` (\n" +
                "  `Nt_no` INT(11) NOT NULL AUTO_INCREMENT,\n" +
                "  `N_no` INT(11) NULL DEFAULT NULL,\n" +
                "  PRIMARY KEY (`Nt_no`),\n" +
                "  INDEX `nt_n_no_fk_idx` (`N_no` ASC) VISIBLE,\n" +
                "  CONSTRAINT `nt_n_no_fk`\n" +
                "    FOREIGN KEY (`N_no`)\n" +
                "    REFERENCES `reminder`.`news` (`N_no`)\n" +
                "    ON DELETE CASCADE\n" +
                "    ON UPDATE CASCADE)\n" +
                "ENGINE = InnoDB\n" +
                "DEFAULT CHARACTER SET = utf8;");
        execute("CREATE TABLE IF NOT EXISTS `reminder`.`reply` (\n" +
                "  `R_no` INT(11) NOT NULL AUTO_INCREMENT,\n" +
                "  `R_title` VARCHAR(20) NULL DEFAULT NULL,\n" +
                "  `R_content` VARCHAR(1100) NULL DEFAULT NULL,\n" +
                "  `R_sday` VARCHAR(20) NULL DEFAULT NULL,\n" +
                "  `R_eday` VARCHAR(20) NULL DEFAULT NULL,\n" +
                "  `R_terms` VARCHAR(400) NULL DEFAULT NULL,\n" +
                "  PRIMARY KEY (`R_no`))\n" +
                "ENGINE = InnoDB\n" +
                "DEFAULT CHARACTER SET = utf8;");

        execute("CREATE TABLE IF NOT EXISTS `reminder`.`reply_result` (\n" +
                "  `Re_no` INT(11) NOT NULL AUTO_INCREMENT,\n" +
                "  `M_no` INT(11) NULL DEFAULT NULL,\n" +
                "  `R_no` INT(11) NULL DEFAULT NULL,\n" +
                "  `Re_whether` INT(11) NULL DEFAULT NULL,\n" +
                "  PRIMARY KEY (`Re_no`),\n" +
                "  INDEX `rr_m_no_fk_idx` (`M_no` ASC) VISIBLE,\n" +
                "  INDEX `rr_r_no_fk_idx` (`R_no` ASC) VISIBLE,\n" +
                "  CONSTRAINT `rr_m_no_fk`\n" +
                "    FOREIGN KEY (`M_no`)\n" +
                "    REFERENCES `reminder`.`member` (`M_no`)\n" +
                "    ON DELETE CASCADE\n" +
                "    ON UPDATE CASCADE,\n" +
                "  CONSTRAINT `rr_r_no_fk`\n" +
                "    FOREIGN KEY (`R_no`)\n" +
                "    REFERENCES `reminder`.`reply` (`R_no`)\n" +
                "    ON DELETE CASCADE\n" +
                "    ON UPDATE CASCADE)\n" +
                "ENGINE = InnoDB\n" +
                "DEFAULT CHARACTER SET = utf8;\n");
        execute("CREATE TABLE IF NOT EXISTS `reminder`.`community` (\n" +
                "  `C_no` INT(11) NOT NULL AUTO_INCREMENT,\n" +
                "  `C_title` VARCHAR(20) NULL DEFAULT NULL,\n" +
                "  `C_content` VARCHAR(200) NULL DEFAULT NULL,\n" +
                "  `M_no` INT(11) NULL DEFAULT NULL,\n" +
                "  `anonymous` INT(11) NULL DEFAULT NULL,\n" +
                "  `classfication` INT(11) NULL DEFAULT NULL,\n" +
                "  PRIMARY KEY (`C_no`),\n" +
                "  INDEX `c_m_no_fk_idx` (`M_no` ASC) VISIBLE,\n" +
                "  CONSTRAINT `c_m_no_fk`\n" +
                "    FOREIGN KEY (`M_no`)\n" +
                "    REFERENCES `reminder`.`member` (`M_no`)\n" +
                "    ON DELETE CASCADE\n" +
                "    ON UPDATE CASCADE)\n" +
                "ENGINE = InnoDB\n" +
                "DEFAULT CHARACTER SET = utf8;");

        execute("CREATE TABLE IF NOT EXISTS `reminder`.`comment` (\n" +
                "  `Co_no` INT(11) NOT NULL AUTO_INCREMENT,\n" +
                "  `Co_content` VARCHAR(200) NULL DEFAULT NULL,\n" +
                "  `C_no` INT(11) NULL DEFAULT NULL,\n" +
                "  `M_no` INT(11) NULL DEFAULT NULL,\n" +
                "  PRIMARY KEY (`Co_no`),\n" +
                "  INDEX `cm_c_no_fk_idx` (`C_no` ASC) VISIBLE,\n" +
                "  INDEX `cm_m_no_fk_idx` (`M_no` ASC) VISIBLE,\n" +
                "  CONSTRAINT `cm_c_no_fk`\n" +
                "    FOREIGN KEY (`C_no`)\n" +
                "    REFERENCES `reminder`.`community` (`C_no`)\n" +
                "    ON DELETE CASCADE\n" +
                "    ON UPDATE CASCADE,\n" +
                "  CONSTRAINT `cm_m_no_fk`\n" +
                "    FOREIGN KEY (`M_no`)\n" +
                "    REFERENCES `reminder`.`member` (`M_no`)\n" +
                "    ON DELETE CASCADE\n" +
                "    ON UPDATE CASCADE)\n" +
                "ENGINE = InnoDB\n" +
                "DEFAULT CHARACTER SET = utf8;");

        execute("use reminder");
        execute("drop user if exists 'user'@'%'");
        execute("create user 'user'@'%' identified by '1234'");
        execute("grant select, update, delete, insert on reminder.* to 'user'@'%'");
        execute("flush privileges;");

        execute("set global local_infile = 1;");

        String[] list = "member,news,notice,reply,reply_result,community,comment".split(",");
        for (int i = 0; i < list.length; i++) {
            execute("load data local infile './/2과제 지급자료/" + list[i] + ".txt' into table `" + list[i]
                    + "` fields terminated  by '\t' lines terminated  by '\n' ignore 1 lines");
        }
        JOptionPane.showMessageDialog(null, "완성되었습니다.");
    }

    public static void execute(String sql) {
        try {
            statement.execute(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
