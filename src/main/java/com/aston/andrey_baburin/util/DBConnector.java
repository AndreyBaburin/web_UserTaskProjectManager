package com.aston.andrey_baburin.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {

    private final String URL = "jdbc:postgresql://localhost:6541/manager_db";
    private final String NAME = "postgres";
    private final  String PASSWORD = "12345";

    private String dnURL;
    private String dbUserName;
    private String dbPassword;

    public DBConnector() {
        this.dnURL = URL;
        this.dbUserName = NAME;
        this.dbPassword = PASSWORD;
    }

    public DBConnector(String dnURL, String dbUserName, String dbPassword) {
        this.dnURL = dnURL;
        this.dbUserName = dbUserName;
        this.dbPassword = dbPassword;
    }

    public Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(dnURL, dbUserName, dbPassword);
        } catch (RuntimeException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

}
