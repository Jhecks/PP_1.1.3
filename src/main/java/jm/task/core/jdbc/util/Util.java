package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.sql.*;
import java.util.Properties;

public class Util implements AutoCloseable {
    //JDBC
    private final Connection connection;

    // Hibernate
    private final Session session;

    public Util(connectionType type) {
        if (type.equals(connectionType.JDBC)) {
            try {
                // Connecting private data is in separate file Config, that is in gitIgnore (schema name is: users_db)
                connection = DriverManager.getConnection(
                        Config.DB_URL, Config.USER, Config.PASS);
            } catch (Exception e) {
                throw new DatabaseConnectionException("Database connection error");
            } finally {
                session = null;
            }
        } else {
            try {
                Configuration configuration = new Configuration();
                Properties settings = new Properties();
                settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
                settings.put(Environment.URL, Config.DB_URL);
                settings.put(Environment.USER, Config.USER);
                settings.put(Environment.PASS, Config.PASS);
                settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL8Dialect");
                settings.put(Environment.SHOW_SQL, "true");
                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
                configuration.setProperties(settings);
                configuration.addAnnotatedClass(User.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();
                SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
                session = sessionFactory.openSession();

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

    public Session getSession() {
        return session;
    }

    @Override
    public void close() throws Exception {
        if (connection != null) {
            connection.close();
        }
        if (session != null) {
            session.close();
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
