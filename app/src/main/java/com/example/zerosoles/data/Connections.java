package com.example.zerosoles.data;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class Connections {
    private Connections() {}

    private static final String IP = "34.87.152.213";
    private static final String PORT = "3306";
    private static final String USERNAME = "sa";
    private static final String PASSWORD = "12345678";
    private static final String DATABASE = "shoeStore";

    private static final String DEFAULT_CONNECTION_STRING = "jdbc:mysql://" + IP + ":" + PORT + "/" + DATABASE;

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            String err = e.getMessage();
            Log.e("Connections", err != null ? err : e.toString());
        }
    }

    public static Connection getDefaultConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DEFAULT_CONNECTION_STRING, USERNAME, PASSWORD);
        } catch (SQLException e) {
            String err = e.getMessage();
            Log.e("Connections", err != null ? err : e.toString());
        }
        return conn;
    }
}
