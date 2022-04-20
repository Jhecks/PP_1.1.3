package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private final String TABLE_NAME = "users_db.users";
    Util.connectionType connectionType = Util.connectionType.Hibernate;
    public UserDaoHibernateImpl() {}


    @Override
    public void createUsersTable() {
        String command = String.format("CREATE TABLE IF NOT EXISTS %s (" +
                "`id` BIGINT NOT NULL AUTO_INCREMENT, " +
                "`name` VARCHAR(255) NOT NULL, " +
                "`lastName` VARCHAR(255) NOT NULL, " +
                "`age` TINYINT NOT NULL, " +
                "PRIMARY KEY (`id`))", TABLE_NAME);
        try (Util util = new Util(connectionType)) {
            Session session = util.getSession();
            session.beginTransaction();
            session.createSQLQuery(command).executeUpdate();
        } catch (Exception e) {
            //
        }

    }

    @Override
    public void dropUsersTable() {
        String command = String.format("DROP TABLE IF EXISTS %s;", TABLE_NAME);
        try (Util util = new Util(connectionType)) {
            Session session = util.getSession();
            session.beginTransaction();
            session.createSQLQuery(command).executeUpdate();
        } catch (Exception e) {
            //
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Util util = new Util(connectionType)) {
            Session session = util.getSession();
            Transaction transaction = session.beginTransaction();
            session.save(new User(name, lastName, age));
            transaction.commit();
        } catch (Exception e) {
            //
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Util util = new Util(connectionType)) {
            Session session = util.getSession();
            Transaction transaction = session.beginTransaction();
            session.delete(session.get(User.class, id));
            transaction.commit();
        } catch (Exception e) {
            //
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> result = new ArrayList<>();
        try (Util util = new Util(connectionType)) {
            Session session = util.getSession();
            Transaction transaction = session.beginTransaction();
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<User> cq = cb.createQuery(User.class);
            Root<User> rootEntry = cq.from(User.class);
            CriteriaQuery<User> all = cq.select(rootEntry);
            TypedQuery<User> allQuery = session.createQuery(all);
            result = allQuery.getResultList();
            transaction.commit();
        } catch (Exception e) {
            //
        }
        return result;
    }

    @Override
    public void cleanUsersTable() {
        String command = String.format("TRUNCATE TABLE %s;", TABLE_NAME);
        try (Util util = new Util(connectionType)) {
            Session session = util.getSession();
            session.beginTransaction();
            session.createSQLQuery(command).executeUpdate();
        } catch (Exception e) {
            //
        }
    }
}
