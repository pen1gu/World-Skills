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
        execute("drop database if exists `coffee`");
        execute("CREATE SCHEMA IF NOT EXISTS `coffee` DEFAULT CHARACTER SET utf8 ;");

        execute("CREATE TABLE IF NOT EXISTS `coffee`.`menu` (\n" +
                "  `m_no` INT(11) NOT NULL AUTO_INCREMENT,\n" +
                "  `m_group` VARCHAR(10) NULL DEFAULT NULL,\n" +
                "  `m_name` VARCHAR(30) NULL DEFAULT NULL,\n" +
                "  `m_price` INT(11) NULL DEFAULT NULL,\n" +
                "  PRIMARY KEY (`m_no`))\n" +
                "ENGINE = InnoDB\n" +
                "DEFAULT CHARACTER SET = utf8;");

        execute("CREATE TABLE IF NOT EXISTS `coffee`.`orderlist` (\n" +
                "  `o_no` INT(11) NOT NULL AUTO_INCREMENT,\n" +
                "  `o_date` DATE NULL DEFAULT NULL,\n" +
                "  `u_no` INT(11) NULL DEFAULT NULL,\n" +
                "  `m_no` INT(11) NULL DEFAULT NULL,\n" +
                "  `o_group` VARCHAR(10) NULL DEFAULT NULL,\n" +
                "  `o_size` VARCHAR(1) NULL DEFAULT NULL,\n" +
                "  `o_price` INT(11) NULL DEFAULT NULL,\n" +
                "  `o_count` INT(11) NULL DEFAULT NULL,\n" +
                "  `o_amount` INT(11) NULL DEFAULT NULL,\n" +
                "  PRIMARY KEY (`o_no`))\n" +
                "ENGINE = InnoDB\n" +
                "DEFAULT CHARACTER SET = utf8;");

        execute("CREATE TABLE IF NOT EXISTS `coffee`.`user` (\n" +
                "  `u_no` INT(11) NOT NULL AUTO_INCREMENT,\n" +
                "  `u_id` VARCHAR(20) NULL DEFAULT NULL,\n" +
                "  `u_pw` VARCHAR(4) NULL DEFAULT NULL,\n" +
                "  `u_name` VARCHAR(5) NULL DEFAULT NULL,\n" +
                "  `u_bd` VARCHAR(13) NULL DEFAULT NULL,\n" +
                "  `u_point` INT(11) NULL DEFAULT NULL,\n" +
                "  `u_grade` VARCHAR(10) NULL DEFAULT NULL,\n" +
                "  PRIMARY KEY (`u_no`))\n" +
                "ENGINE = InnoDB\n" +
                "DEFAULT CHARACTER SET = utf8;\n");

        execute("CREATE TABLE IF NOT EXISTS `coffee`.`shopping` (\n" +
                "  `s_no` INT(11) NOT NULL AUTO_INCREMENT,\n" +
                "  `m_no` INT(11) NULL DEFAULT NULL,\n" +
                "  `s_price` INT(11) NULL DEFAULT NULL,\n" +
                "  `s_count` INT(11) NULL DEFAULT NULL,\n" +
                "  `s_size` VARCHAR(1) NULL DEFAULT NULL,\n" +
                "  `s_amount` INT(11) NULL DEFAULT NULL,\n" +
                "  PRIMARY KEY (`s_no`))\n" +
                "ENGINE = InnoDB\n" +
                "DEFAULT CHARACTER SET = utf8;");

        execute("use coffee");
        execute("drop user if exists 'user'@'%'");
        execute("create user 'user'@'%' identified by '1234'");
        execute("grant select, insert, update, delete on coffee.* to 'user'@'%'");
        execute("flush privileges");

        execute("set global local_infile = 1");

        for (String text : new String[]{"menu", "orderlist", "user"}) {
            execute("load data local infile './DataFiles/" + text + ".txt'  into table " + text +
                    " fields terminated by '\t' lines terminated by '\n' ignore 1 lines");
        }

        System.out.println("완성");
    }

    public static void execute(String sql) {
        try {
            statement.execute(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
