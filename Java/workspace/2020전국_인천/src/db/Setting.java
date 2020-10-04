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
            connection = DriverManager.getConnection("jdbc:mysql://localhost?serverTimezone=UTC&allowLoadLocalInfile=true", "root", "1234");
            statement = connection.createStatement();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void main(String[] args) {
        execute("drop database if exists `market`");
        execute("CREATE SCHEMA IF NOT EXISTS `market` DEFAULT CHARACTER SET utf8 ;");
        execute("CREATE TABLE IF NOT EXISTS `market`.`user` (\n" +
                "  `u_no` INT(11) NOT NULL AUTO_INCREMENT,\n" +
                "  `u_id` VARCHAR(20) NULL DEFAULT NULL,\n" +
                "  `u_pw` VARCHAR(20) NULL DEFAULT NULL,\n" +
                "  `u_address` VARCHAR(50) NULL DEFAULT NULL,\n" +
                "  `u_name` VARCHAR(15) NULL DEFAULT NULL,\n" +
                "  `u_phone` VARCHAR(45) NULL DEFAULT NULL,\n" +
                "  `u_age` VARCHAR(20) NULL DEFAULT NULL,\n" +
                "  PRIMARY KEY (`u_no`))\n" +
                "ENGINE = InnoDB\n" +
                "DEFAULT CHARACTER SET = utf8;");
        execute("CREATE TABLE IF NOT EXISTS `market`.`category` (\n" +
                "  `c_no` INT(11) NOT NULL AUTO_INCREMENT,\n" +
                "  `c_name` VARCHAR(10) NULL DEFAULT NULL,\n" +
                "  PRIMARY KEY (`c_no`))\n" +
                "ENGINE = InnoDB\n" +
                "DEFAULT CHARACTER SET = utf8;");
        execute("CREATE TABLE IF NOT EXISTS `market`.`product` (\n" +
                "  `p_no` INT(11) NOT NULL AUTO_INCREMENT,\n" +
                "  `c_no` INT(11) NULL DEFAULT NULL,\n" +
                "  `p_name` VARCHAR(45) NULL DEFAULT NULL,\n" +
                "  `p_price` INT(11) NULL DEFAULT NULL,\n" +
                "  `p_stock` INT(11) NULL DEFAULT NULL,\n" +
                "  `p_explanation` VARCHAR(150) NULL DEFAULT NULL,\n" +
                "  PRIMARY KEY (`p_no`),\n" +
                "  INDEX `fk_pr_c_no_idx` (`c_no` ASC) VISIBLE,\n" +
                "  CONSTRAINT `fk_pr_c_no`\n" +
                "    FOREIGN KEY (`c_no`)\n" +
                "    REFERENCES `market`.`category` (`c_no`)\n" +
                "    ON DELETE CASCADE\n" +
                "    ON UPDATE CASCADE)\n" +
                "ENGINE = InnoDB\n" +
                "DEFAULT CHARACTER SET = utf8;");
        execute("CREATE TABLE IF NOT EXISTS `market`.`purchase` (\n" +
                "  `pu_no` INT(11) NOT NULL AUTO_INCREMENT,\n" +
                "  `p_no` INT(11) NULL DEFAULT NULL,\n" +
                "  `pu_price` INT(11) NULL DEFAULT NULL,\n" +
                "  `pu_count` INT(11) NULL DEFAULT NULL,\n" +
                "  `pu_total` INT(11) NULL DEFAULT NULL,\n" +
                "  `u_no` INT(11) NULL DEFAULT NULL,\n" +
                "  PRIMARY KEY (`pu_no`),\n" +
                "  INDEX `fk_pu_c_no_idx` (`p_no` ASC) VISIBLE,\n" +
                "  INDEX `fk_pu_u_no_idx` (`u_no` ASC) VISIBLE,\n" +
                "  CONSTRAINT `fk_pu_c_no`\n" +
                "    FOREIGN KEY (`p_no`)\n" +
                "    REFERENCES `market`.`product` (`p_no`)\n" +
                "    ON DELETE CASCADE\n" +
                "    ON UPDATE CASCADE,\n" +
                "  CONSTRAINT `fk_pu_u_no`\n" +
                "    FOREIGN KEY (`u_no`)\n" +
                "    REFERENCES `market`.`user` (`u_no`)\n" +
                "    ON DELETE CASCADE\n" +
                "    ON UPDATE CASCADE)\n" +
                "ENGINE = InnoDB\n" +
                "DEFAULT CHARACTER SET = utf8;");
        execute("use market");
        execute("drop user if exists 'user'@'%'");
        execute("create user 'user'@'%' identified by '1234'");
        execute("grant select, insert, delete, update on market.* to 'user'@'%'");
        execute("flush privileges");

        execute("set global local_infile = 1");

        for (String text : new String[]{"user", "category", "product", "purchase"}) {
            execute("load data local infile './datafiles/" + text + ".txt' into table " + text + " fields terminated by '\t' lines terminated by '\n' ignore 1 lines");
        }

        System.out.println("complete");
    }

    public static void execute(String sql) {
        try {
            statement.execute(sql);
        } catch (Exception throwables) {
            throwables.printStackTrace();
        }
    }
}
