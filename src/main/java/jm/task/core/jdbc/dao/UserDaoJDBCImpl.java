package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final String TABLE_NAME = "users_db.users";
    private final Util.connectionType connectionType = Util.connectionType.JDBC;

    public UserDaoJDBCImpl() {}

    public void createUsersTable() {
        //String formatting in case we want to change table name
        String command = String.format("CREATE TABLE IF NOT EXISTS %s (" +
                "`id` BIGINT NOT NULL AUTO_INCREMENT, " +
                "`name` VARCHAR(255) NOT NULL, " +
                "`lastName` VARCHAR(255) NOT NULL, " +
                "`age` TINYINT NOT NULL, " +
                "PRIMARY KEY (`id`))", TABLE_NAME);

        try (Util util = new Util(connectionType)) {
            Connection connection = util.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(command);
            preparedStatement.execute();

        } catch (SQLTimeoutException e) {
            System.err.println("Timeout exception was caught");
            System.err.println(Arrays.toString(e.getStackTrace()));
        } catch (SQLException e) {
            System.err.println(Arrays.toString(e.getStackTrace()));
        } catch (Util.DatabaseConnectionException e) {
            System.err.println("Connection in Util class failed");
        } catch (Exception e) {
            System.err.println("Unable to connect to database");
        }
    }

    public void dropUsersTable() {
        String command = String.format("DROP TABLE IF EXISTS %s;", TABLE_NAME);

        try (Util util = new Util(connectionType)) {
            Connection connection = util.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(command);
            preparedStatement.execute();
        } catch (SQLTimeoutException e) {
            System.err.println("Timeout exception was caught");
            System.err.println(Arrays.toString(e.getStackTrace()));
        } catch (SQLException e) {
            System.err.println(Arrays.toString(e.getStackTrace()));
        } catch (Util.DatabaseConnectionException e) {
            System.err.println("Connection in Util class failed");
        } catch (Exception e) {
            System.err.println("Unable to connect to database");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String command = String.format("INSERT INTO %s (name, lastName, age) VALUES (?, ?, ?)", TABLE_NAME);

        try (Util util = new Util(connectionType)) {
            Connection connection = util.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(command);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setInt(3, age);
            preparedStatement.execute();
        } catch (SQLTimeoutException e) {
            System.err.println("Timeout exception was caught");
            System.err.println(Arrays.toString(e.getStackTrace()));
        } catch (SQLException e) {
            System.err.println(Arrays.toString(e.getStackTrace()));
        } catch (Util.DatabaseConnectionException e) {
            System.err.println("Connection in Util class failed");
        } catch (Exception e) {
            System.err.println("Unable to connect to database");
        }
    }

    public void removeUserById(long id) {
        String command = String.format("DELETE FROM %s WHERE id=?;", TABLE_NAME);

        try (Util util = new Util(connectionType)) {
            Connection connection = util.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(command);
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
        } catch (SQLTimeoutException e) {
            System.err.println("Timeout exception was caught");
            System.err.println(Arrays.toString(e.getStackTrace()));
        } catch (SQLException e) {
            System.err.println(Arrays.toString(e.getStackTrace()));
        } catch (Util.DatabaseConnectionException e) {
            System.err.println("Connection in Util class failed");
        } catch (Exception e) {
            System.err.println("Unable to connect to database");
        }
    }

    public List<User> getAllUsers() {
        String command = String.format("SELECT * FROM %s;", TABLE_NAME);
        List<User> result = new ArrayList<>();

        try (Util util = new Util(connectionType)) {
            Connection connection = util.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(command);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = new User(resultSet.getString("name"),
                        resultSet.getString("lastName"), resultSet.getByte("age"));
                user.setId(resultSet.getLong("id"));
                result.add(user);
            }
        } catch (SQLTimeoutException e) {
            System.err.println("Timeout exception was caught");
            System.err.println(Arrays.toString(e.getStackTrace()));
        } catch (SQLException e) {
            System.err.println(Arrays.toString(e.getStackTrace()));
        } catch (Util.DatabaseConnectionException e) {
            System.err.println("Connection in Util class failed");
        } catch (Exception e) {
            System.err.println("Unable to connect to database");
        }
        return result;
    }

    public void cleanUsersTable() {
        String command = String.format("TRUNCATE TABLE %s;", TABLE_NAME);

        try (Util util = new Util(connectionType)) {
            Connection connection = util.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(command);
            preparedStatement.execute();
        } catch (SQLTimeoutException e) {
            System.err.println("Timeout exception was caught");
            System.err.println(Arrays.toString(e.getStackTrace()));
        } catch (SQLException e) {
            System.err.println(Arrays.toString(e.getStackTrace()));
        } catch (Util.DatabaseConnectionException e) {
            System.err.println("Connection in Util class failed");
        } catch (Exception e) {
            System.err.println("Unable to connect to database");
        }
    }
}
