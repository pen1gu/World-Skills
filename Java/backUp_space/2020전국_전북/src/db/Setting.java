package db;

import javax.swing.*;
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
        execute("drop database if exists airdatabase");
        execute("CREATE SCHEMA IF NOT EXISTS `airdatabase` DEFAULT CHARACTER SET utf8 ;");

        execute("CREATE TABLE IF NOT EXISTS `airdatabase`.`Passenger` (\n" +
                "  `Passenger_Code` INT(11) NOT NULL AUTO_INCREMENT,\n" +
                "  `id` VARCHAR(30) NULL DEFAULT NULL,\n" +
                "  `Pw` VARCHAR(50) NULL DEFAULT NULL,\n" +
                "  `Name` VARCHAR(30) NULL DEFAULT NULL,\n" +
                "  `Jumin` VARCHAR(20) NULL DEFAULT NULL,\n" +
                "  `Email` VARCHAR(50) NULL DEFAULT NULL,\n" +
                "  PRIMARY KEY (`Passenger_Code`))\n" +
                "ENGINE = InnoDB\n" +
                "DEFAULT CHARACTER SET = utf8;");
        execute("CREATE TABLE IF NOT EXISTS `airdatabase`.`AirInfo` (\n" +
                "  `Air_Code` INT(11) NOT NULL AUTO_INCREMENT,\n" +
                "  `Rend` VARCHAR(10) NULL DEFAULT NULL,\n" +
                "  `Country` VARCHAR(30) NULL DEFAULT NULL,\n" +
                "  `Departure` VARCHAR(30) NULL DEFAULT NULL,\n" +
                "  `Arrive` VARCHAR(30) NULL DEFAULT NULL,\n" +
                "  `Air` VARCHAR(20) NULL DEFAULT NULL,\n" +
                "  `Type` VARCHAR(20) NULL DEFAULT NULL,\n" +
                "  `StartTime` TIME NULL DEFAULT NULL,\n" +
                "  `LastTime` TIME NULL DEFAULT NULL,\n" +
                "  `Fare` INT(11) NULL DEFAULT NULL,\n" +
                "  PRIMARY KEY (`Air_Code`))\n" +
                "ENGINE = InnoDB\n" +
                "DEFAULT CHARACTER SET = utf8;");
        execute("CREATE TABLE IF NOT EXISTS `airdatabase`.`Reservation` (\n" +
                "  `Preservation_Code` INT(11) NOT NULL AUTO_INCREMENT,\n" +
                "  `DepartureDate` DATE NULL DEFAULT NULL,\n" +
                "  `Passenger_Code` INT(11) NULL DEFAULT NULL,\n" +
                "  `ID` VARCHAR(50) NULL DEFAULT NULL,\n" +
                "  `Name` VARCHAR(30) NULL DEFAULT NULL,\n" +
                "  `Rend` VARCHAR(20) NULL DEFAULT NULL,\n" +
                "  `Country` VARCHAR(50) NULL DEFAULT NULL,\n" +
                "  `Departure` VARCHAR(50) NULL DEFAULT NULL,\n" +
                "  `Arrive` VARCHAR(50) NULL DEFAULT NULL,\n" +
                "  `Air` VARCHAR(50) NULL DEFAULT NULL,\n" +
                "  `Type` VARCHAR(50) NULL DEFAULT NULL,\n" +
                "  `StartTime` TIME NULL DEFAULT NULL,\n" +
                "  `LastTime` TIME NULL DEFAULT NULL,\n" +
                "  `Count` INT(11) NULL DEFAULT NULL,\n" +
                "  `Fare` INT(11) NULL DEFAULT NULL,\n" +
                "  PRIMARY KEY (`Preservation_Code`))\n" +
                "ENGINE = InnoDB\n" +
                "DEFAULT CHARACTER SET = utf8;");

        execute("use airdatabase");
        execute("drop user if exists 'user'@'%'");
        execute("create user 'user'@'%' identified by '1234'");
        execute("GRANT SELECT, DELETE, UPDATE, INSERT ON airdatabase.* to 'user'@'%'");
        execute("flush privileges");
        execute("set global local_infile=1");


        for (String txt : new String[]{"Passenger", "AirInfo", "Reservation"}) {
            execute("load data local infile './datafiles/data/" + txt + ".txt' into table " + txt + " fields terminated by '\t' lines terminated by '\n' ignore 1 lines");
        }

        System.out.println("complete");
    }

    public static void execute(String sql) {
        try {
            statement.execute(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
