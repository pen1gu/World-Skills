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
        execute("drop database if exists `train`");
        execute("CREATE SCHEMA IF NOT EXISTS `train` DEFAULT CHARACTER SET utf8 ;");

        execute("CREATE TABLE IF NOT EXISTS `train`.`user` (\r\n" + "  `no` INT(11) NOT NULL AUTO_INCREMENT,\r\n"
                + "  `id` VARCHAR(20) NULL DEFAULT NULL,\r\n" + "  `pw` VARCHAR(20) NULL DEFAULT NULL,\r\n"
                + "  `name` VARCHAR(20) NULL DEFAULT NULL,\r\n" + "  `birth` DATE NULL DEFAULT NULL,\r\n"
                + "  `phone` VARCHAR(20) NULL DEFAULT NULL,\r\n" + "  PRIMARY KEY (`no`))\r\n" + "ENGINE = InnoDB\r\n"
                + "DEFAULT CHARACTER SET = utf8;");

        execute("CREATE TABLE IF NOT EXISTS `train`.`station` (\r\n" + "  `no` INT(11) NOT NULL AUTO_INCREMENT,\r\n"
                + "  `name` VARCHAR(20) NULL DEFAULT NULL,\r\n" + "  PRIMARY KEY (`no`))\r\n" + "ENGINE = InnoDB\r\n"
                + "DEFAULT CHARACTER SET = utf8;");

        execute("CREATE TABLE IF NOT EXISTS `train`.`route` (\r\n" + "  `no` INT(11) NOT NULL AUTO_INCREMENT,\r\n"
                + "  `name` VARCHAR(20) NULL DEFAULT NULL,\r\n" + "  `station_no` INT(11) NULL DEFAULT NULL,\r\n"
                + "  PRIMARY KEY (`no`),\r\n" + "  INDEX `route_station_no_idx` (`station_no` ASC) VISIBLE,\r\n"
                + "  CONSTRAINT `r_station_no_fk`\r\n" + "    FOREIGN KEY (`station_no`)\r\n"
                + "    REFERENCES `train`.`station` (`no`)\r\n" + "    ON DELETE CASCADE\r\n"
                + "    ON UPDATE CASCADE)\r\n" + "ENGINE = InnoDB\r\n" + "DEFAULT CHARACTER SET = utf8;");

        execute("CREATE TABLE IF NOT EXISTS `train`.`timetable` (\r\n" + "  `no` INT(11) NOT NULL AUTO_INCREMENT,\r\n"
                + "  `route` VARCHAR(20) NULL DEFAULT NULL,\r\n" + "  `train` INT(11) NULL DEFAULT NULL,\r\n"
                + "  `station_no` INT(11) NULL DEFAULT NULL,\r\n" + "  `time` TIME NULL DEFAULT NULL,\r\n"
                + "  PRIMARY KEY (`no`),\r\n" + "  INDEX `timetable_station_no_idx` (`station_no` ASC) VISIBLE,\r\n"
                + "  CONSTRAINT `timetable_station_no_fk`\r\n" + "    FOREIGN KEY (`station_no`)\r\n"
                + "    REFERENCES `train`.`station` (`no`)\r\n" + "    ON DELETE CASCADE\r\n"
                + "    ON UPDATE CASCADE)\r\n" + "ENGINE = InnoDB\r\n" + "DEFAULT CHARACTER SET = utf8;");

        execute("CREATE TABLE IF NOT EXISTS `train`.`fare` (\r\n" + "  `no` INT(11) NOT NULL AUTO_INCREMENT,\r\n"
                + "  `departure_no` INT(11) NULL DEFAULT NULL,\r\n" + "  `arrival_no` INT(11) NULL DEFAULT NULL,\r\n"
                + "  `room` VARCHAR(10) NULL DEFAULT NULL,\r\n" + "  `price` INT(11) NULL DEFAULT NULL,\r\n"
                + "  PRIMARY KEY (`no`),\r\n" + "  INDEX `fare_departure_no_idx` (`departure_no` ASC) INVISIBLE,\r\n"
                + "  INDEX `fare_arrival_no_idx` (`arrival_no` ASC) VISIBLE,\r\n"
                + "  CONSTRAINT `fare_departure_no_fk`\r\n" + "    FOREIGN KEY (`departure_no`)\r\n"
                + "    REFERENCES `train`.`station` (`no`)\r\n" + "    ON DELETE CASCADE\r\n"
                + "    ON UPDATE CASCADE,\r\n" + "  CONSTRAINT `fare_arrival_no_fk`\r\n"
                + "    FOREIGN KEY (`arrival_no`)\r\n" + "    REFERENCES `train`.`station` (`no`)\r\n"
                + "    ON DELETE CASCADE\r\n" + "    ON UPDATE CASCADE)\r\n" + "ENGINE = InnoDB\r\n"
                + "DEFAULT CHARACTER SET = utf8;");

        execute("CREATE TABLE IF NOT EXISTS `train`.`seat` (\r\n" + "  `no` INT(11) NOT NULL AUTO_INCREMENT,\r\n"
                + "  `room` INT(11) NULL DEFAULT NULL,\r\n" + "  `name` VARCHAR(20) NULL DEFAULT NULL,\r\n"
                + "  PRIMARY KEY (`no`))\r\n" + "ENGINE = InnoDB\r\n" + "DEFAULT CHARACTER SET = utf8;");

        execute("CREATE TABLE IF NOT EXISTS `train`.`reservation` (\r\n" + "  `no` INT(11) NOT NULL AUTO_INCREMENT,\r\n"
                + "  `user_no` INT(11) NULL DEFAULT NULL,\r\n" + "  `date` DATE NULL DEFAULT NULL,\r\n"
                + "  `dep_timetable_no` INT(11) NULL DEFAULT NULL,\r\n"
                + "  `arr_timetable_no` INT(11) NULL DEFAULT NULL,\r\n"
                + "  `seat_no` VARCHAR(255) NULL DEFAULT NULL,\r\n" + "  `price` INT(11) NULL DEFAULT NULL,\r\n"
                + "  PRIMARY KEY (`no`),\r\n" + "  INDEX `re_user_no_idx` (`user_no` ASC) VISIBLE,\r\n"
                + "  INDEX `re_dep_timetable_no_fk_idx` (`dep_timetable_no` ASC) VISIBLE,\r\n"
                + "  INDEX `re_arr_timetable_no_fk_idx` (`arr_timetable_no` ASC) VISIBLE,\r\n"
                + "  CONSTRAINT `re_dep_timetable_no_fk`\r\n" + "    FOREIGN KEY (`dep_timetable_no`)\r\n"
                + "    REFERENCES `train`.`timetable` (`no`)\r\n" + "    ON DELETE CASCADE\r\n"
                + "    ON UPDATE CASCADE,\r\n" + "  CONSTRAINT `re_arr_timetable_no_fk`\r\n"
                + "    FOREIGN KEY (`arr_timetable_no`)\r\n" + "    REFERENCES `train`.`timetable` (`no`)\r\n"
                + "    ON DELETE CASCADE\r\n" + "    ON UPDATE CASCADE,\r\n" + "  CONSTRAINT `re_user_no_fk`\r\n"
                + "    FOREIGN KEY (`user_no`)\r\n" + "    REFERENCES `train`.`user` (`no`)\r\n"
                + "    ON DELETE CASCADE\r\n" + "    ON UPDATE CASCADE)\r\n" + "ENGINE = InnoDB\r\n"
                + "DEFAULT CHARACTER SET = utf8;");

        execute("use train;");
        execute("drop user if exists 'user'@'%'");
        execute("create user 'user'@'%' identified by '1234'");
        execute("grant select, update, insert, delete on train.* to 'user'@'%'");
        execute("flush privileges");

        execute("set global local_infile=1");
        String[] list = "user,station,route,timetable,fare,seat".split(",");
        for (int i = 0; i < list.length; i++) {
            execute("load data local infile './/�����ڷ�/" + list[i] + ".txt' into table " + list[i]
                    + " fields terminated  by '\t' lines terminated  by '\n' ignore 1 lines");
        }

        JOptionPane.showInternalMessageDialog(null, "�Ϸ�");
    }

    public static void execute(String sql) {
        try {
            statement.execute(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
