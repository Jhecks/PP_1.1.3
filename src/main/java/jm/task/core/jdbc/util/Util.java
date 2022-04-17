package jm.task.core.jdbc.util;

import java.sql.*;

public class Util implements AutoCloseable {
    private final Connection connection;
    private final Statement statement;

    public Util() {
        try {
            // Connecting private data is in separate file Config, that is in gitIgnore
            connection = DriverManager.getConnection(
                    Config.DB_URL, Config.USER, Config.PASS);
            statement = connection.createStatement();
            statement.executeUpdate("CREATE NEW SCHEMA users_database;");
        } catch (Exception e) {
            throw new DatabaseConnectionException("Database connection error");
        }
    }

    public Statement getConnection() {
        return statement;
    }

    @Override
    public void close() throws Exception {
        connection.close();
    }

    public static class DatabaseConnectionException extends RuntimeException {
        DatabaseConnectionException(String message) {
            super(message);
        }
    }
}
