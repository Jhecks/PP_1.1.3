package jm.task.core.jdbc.service;

import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;

import java.util.List;

public class UserServiceImpl implements UserService {
//    private final UserDaoJDBCImpl database = new UserDaoJDBCImpl();
    private final UserDaoHibernateImpl database = new UserDaoHibernateImpl();

    public void createUsersTable() {
        database.createUsersTable();
    }

    public void dropUsersTable() {
        database.dropUsersTable();
    }

    public void saveUser(String name, String lastName, byte age) {
        database.saveUser(name, lastName, age);
    }

    public void removeUserById(long id) {
        database.removeUserById(id);
    }

    public List<User> getAllUsers() {
        return database.getAllUsers();
    }

    public void cleanUsersTable() {
        database.cleanUsersTable();
    }
}
