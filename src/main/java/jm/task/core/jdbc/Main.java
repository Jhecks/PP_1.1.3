package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserServiceImpl database = new UserServiceImpl();
        database.createUsersTable();

        database.saveUser("Vasya", "Pupkin", (byte) 15);
        System.out.printf("User с именем – %s добавлен в базу данных\n", "Vasya");
        database.saveUser("Lesha", "Ivanov", (byte) 17);
        System.out.printf("User с именем – %s добавлен в базу данных\n", "Lesha");
        database.saveUser("Lev", "Tolstoy", (byte) 24);
        System.out.printf("User с именем – %s добавлен в базу данных\n", "Lev");
        database.saveUser("Ivan", "Grozny", (byte) 105);
        System.out.printf("User с именем – %s добавлен в базу данных\n", "Ivan");

        List<User> users = database.getAllUsers();
        users.forEach(System.out::println);
        database.cleanUsersTable();
        database.dropUsersTable();
    }
}
