package jm.task.core.jdbc.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class Util implements AutoCloseable {
    //JDBC
    private final Connection connection;

    // Hibernate
    private final SessionFactory sessionFactory;

    public Util(connectionType type) {
        if (type.equals(connectionType.JDBC)) {
            try {
                // Connecting private data is in separate file Config, that is in gitIgnore (schema name is: users_db)
                System.out.println("JDBC connecting");
                connection = DriverManager.getConnection(
                        Config.DB_URL, Config.USER, Config.PASS);
            } catch (Exception e) {
                throw new DatabaseConnectionException("Database connection error");
            } finally {
                sessionFactory = null;
            }
        } else {
            try {
                System.out.println("Hibernate setup");
                StandardServiceRegistryBuilder registryBuilder = new StandardServiceRegistryBuilder();

                Map<String, String> settings = new HashMap<>();
                settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
                settings.put(Environment.URL, Config.DB_URL);
                settings.put(Environment.USER, Config.USER);
                settings.put(Environment.PASS, Config.PASS);
                settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");
                // Apply settings
                registryBuilder.applySettings(settings);

                // Create registry
                StandardServiceRegistry registry = registryBuilder.build();

                // Create MetadataSources
                MetadataSources sources = new MetadataSources(registry);

                // Create Metadata
                Metadata metadata = sources.getMetadataBuilder().build();

                // Create SessionFactory
                sessionFactory = metadata.getSessionFactoryBuilder().build();

            } catch (Exception e) {
                throw new DatabaseConnectionException("Database hibernate connection error");
            } finally {
                connection = null;
            }
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    @Override
    public void close() throws Exception {
        if (connection != null) {
            connection.close();
            System.out.println("JDBC connection closed");
        }
        if (sessionFactory != null) {
            sessionFactory.close();
            System.out.println("Hibernate connection closed");
        }
    }

    public static class DatabaseConnectionException extends RuntimeException {
        DatabaseConnectionException(String message) {
            super(message);
        }
    }

    public enum connectionType {
        JDBC,
        Hibernate
    }
}
