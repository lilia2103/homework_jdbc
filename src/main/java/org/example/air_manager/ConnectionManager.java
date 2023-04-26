package org.example.air_manager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class ConnectionManager {
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "lilia200321";
    private static Connection connection;

    private static ConnectionManager ConnectionManager;

    private ConnectionManager(){
    }

    public static ConnectionManager getInstance() {
        if (ConnectionManager == null)
        {
            ConnectionManager = new ConnectionManager();
        }
        return ConnectionManager;
    }

    public static Connection getConnection() {
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            return connection;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void closeConnection() {
        if (connection == null) {
            throw new NullPointerException("Connection is null:");
        }
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

