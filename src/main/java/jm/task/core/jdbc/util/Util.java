package jm.task.core.jdbc.util;

import java.sql.*;

public class Util implements AutoCloseable {
    private final Connection connection;

    public Util() {
        try {
            // Connecting private data is in separate file Config, that is in gitIgnore (schema name is: users_db)
            connection = DriverManager.getConnection(
                    Config.DB_URL, Config.USER, Config.PASS);
        } catch (Exception e) {
            throw new DatabaseConnectionException("Database connection error");
        }
    }

    public Connection getConnection() {
        return connection;

    }

    @Override
    public void close() throws Exception {
        if (connection != null)
            connection.close();
    }

    public static class DatabaseConnectionException extends RuntimeException {
        DatabaseConnectionException(String message) {
            super(message);
        }
    }
}
