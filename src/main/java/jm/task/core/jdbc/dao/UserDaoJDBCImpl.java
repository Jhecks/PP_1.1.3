package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final String TABLE_NAME = "users_db.users";
    private Statement statement;

    public UserDaoJDBCImpl() {}

    public void createUsersTable() {
        String command = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ( " +
                "`id` BIGINT NOT NULL AUTO_INCREMENT, " +
                "`name` VARCHAR(255) NOT NULL, " +
                "`last_name` VARCHAR(255) NOT NULL, " +
                "`age` TINYINT NOT NULL, " +
                "PRIMARY KEY (`id`))";
        execute(command);
    }
    public void dropUsersTable() {
        String command = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
        execute(command);
    }

    public void saveUser(String name, String lastName, byte age) {
        String command = String.format("INSERT INTO %s (name, last_name, age) VALUES (\"%s\", \"%s\", %d)",
                TABLE_NAME, name, lastName, age);
        execute(command);
    }

    public void removeUserById(long id) {
        String command = String.format("DELETE FROM %s WHERE id=%d;", TABLE_NAME, id);
        execute(command);
    }

    public List<User> getAllUsers() {
        List<User> result = new ArrayList<>();
        try (Util util = new Util()) {
            statement = util.getConnection();
            String command = "SELECT * FROM " + TABLE_NAME + ";";
            ResultSet resultSet = statement.executeQuery(command);
            while (resultSet.next()) {
                User user = new User(resultSet.getString("name"),
                        resultSet.getString("last_name"), resultSet.getByte("age"));
                user.setId(resultSet.getLong("id"));
                result.add(user);
            }
        } catch (SQLTimeoutException e) {
            System.err.println("Timeout exception was caught");
            System.err.println(Arrays.toString(e.getStackTrace()));
        } catch (SQLException e) {
            System.err.println(Arrays.toString(e.getStackTrace()));
        } catch (Exception e) {
            System.err.println("Unable to connect to database");
        }
        return result;
    }

    public void cleanUsersTable() {
        String command = "TRUNCATE TABLE " + TABLE_NAME + ";";
        execute(command);
    }

    private void execute(String command) {
        try (Util util = new Util()) {
            statement = util.getConnection();
            statement.executeUpdate(command);
        } catch (SQLTimeoutException e) {
            System.err.println("Timeout exception was caught");
            System.err.println(Arrays.toString(e.getStackTrace()));
        } catch (SQLException e) {
            System.err.println(Arrays.toString(e.getStackTrace()));
        } catch (Exception e) {
            System.err.println("Unable to connect to database");
        }
    }
}
