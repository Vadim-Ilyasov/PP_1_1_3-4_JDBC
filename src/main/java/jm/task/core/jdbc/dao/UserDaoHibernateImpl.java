package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private  final SessionFactory sf = new Configuration().configure("hibernate.cfg.xml")
            .addAnnotatedClass(User.class).buildSessionFactory();

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try (Session session = sf.getCurrentSession()) {
            session.beginTransaction();
            String create = "CREATE TABLE IF NOT EXISTS `mydb`.`users`" +
                    "(id bigint not null auto_increment, " +
                    "name varchar(50), " +
                    "lastName varchar(50), " +
                    "age tinyint, " +
                    "PRIMARY KEY (id))";

            Query query = session.createSQLQuery(create).addEntity(User.class);
            query.executeUpdate();
            session.getTransaction().commit();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = sf.getCurrentSession()) {
            session.beginTransaction();
            Query query = session.createSQLQuery("DROP TABLE IF EXISTS users").addEntity(User.class);
            query.executeUpdate();
            session.getTransaction().commit();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {

        try (Session session = sf.getCurrentSession()) {
            session.beginTransaction();
            session.save(new User(name, lastName, age));
            session.getTransaction().commit();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = sf.getCurrentSession()) {
            session.beginTransaction();
            session.delete(session.get(User.class, id));
            session.getTransaction().commit();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> person = new ArrayList<>();
        try (Session session = sf.getCurrentSession()) {
            session.beginTransaction();
            person = session.createQuery("from User order by name").list();
            session.getTransaction().commit();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return person;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = sf.getCurrentSession()) {
            session.beginTransaction();
            session.createSQLQuery("DELETE FROM users").addEntity(User.class).executeUpdate();
            session.getTransaction().commit();
        } catch (Throwable e) {
            e.printStackTrace();
        }

    }
}
