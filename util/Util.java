package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    // реализуйте настройку соеденения с БД
     private final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
     private final String DB_URL = "jdbc:mysql://localhost:3306/mydb";
     private final String DB_USERNAME = "root";
     private final String DB_PASSWORD = "root";

    public  Connection getConnection()  {
        Connection connection = null;
        try {
            Class.forName(DB_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            System.out.println("Ok!");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;

    }


}
