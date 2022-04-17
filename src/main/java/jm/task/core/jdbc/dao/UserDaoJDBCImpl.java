package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final Statement statement = new Util().getConnection();
    private final String SCHEMA_NAME = "USERS";
    private final String TABLE_NAME = "USERS";

    public UserDaoJDBCImpl() {}

    public void createUsersTable() {
        String command = "CREATE SCHEMA " + SCHEMA_NAME + "; CREATE TABLE " + TABLE_NAME + " (" +
                "user_id BIGINT NOT NULL AUTO_INCREMENT, " +
                "user_name varchar(255) NOT NULL, " +
                "user_lastName varchar(255) NOT NULL, " +
                "age TINYINT NOT NULL, " +
                "PRIMARY KEY (user_id))";

        try {
            statement.executeUpdate(command);
        } catch (SQLException e) {
            //
        }
    }

    public void dropUsersTable() {
        String command = "USE " + SCHEMA_NAME + "; DROP TABLE IF EXISTS " + TABLE_NAME + ";";
        try {
            statement.executeUpdate(command);
        } catch (SQLException e) {
            //
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String command = "USE " + SCHEMA_NAME + "; INSERT INTO " + TABLE_NAME + " VALUES (" +
                name + ", " + lastName + ", " + age + ");";
        try {
            statement.executeUpdate(command);
        } catch (SQLException e) {
            //
        }
    }

    public void removeUserById(long id) {
        String command = "USE " + TABLE_NAME + "; DELETE FROM " + TABLE_NAME + " WHERE user_id=" + id + ";";
        try {
            statement.executeUpdate(command);
        } catch (SQLException e) {
            //
        }
    }

    public List<User> getAllUsers() {
        List<User> result = new ArrayList<>();
        try {
            String command = "SELECT * FROM " + TABLE_NAME + ";";
            ResultSet resultSet = statement.executeQuery(command);
            while (resultSet.next()) {
                long id = resultSet.getLong(1);
                String name = resultSet.getString("name");
                String lastName = resultSet.getString("lastname");
                byte age = resultSet.getByte(4);
                User user = new User(name, lastName, age);
                user.setId(id);
                result.add(user);
            }
        } catch (Exception e) {
            System.out.println("Something went wrong");
        }
        return result;
    }

    public void cleanUsersTable() {
        String command = "USE " + SCHEMA_NAME + "; DROP TABLE " + TABLE_NAME + ";";
        try {
            statement.executeUpdate(command);
        } catch (SQLException e) {
            //
        }
    }
}
