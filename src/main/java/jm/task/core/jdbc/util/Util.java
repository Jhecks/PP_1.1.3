package jm.task.core.jdbc.util;

import java.sql.*;

public class Util {
    public static Statement makeConnection() {
        try {
            // Connecting private data is in separate file Config, that is in gitIgnore
            Connection connection = DriverManager.getConnection(
                    Config.DB_URL,Config.USER,Config.PASS);
            return connection.createStatement();
        } catch (Exception e) {
            throw new DatabaseConnectionException("Database connection error");
        }
    }

    public static class DatabaseConnectionException extends RuntimeException {
        DatabaseConnectionException(String message) {
            super(message);
        }
    }
}
