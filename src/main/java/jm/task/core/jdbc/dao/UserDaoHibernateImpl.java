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


 /*   Configuration cfg = new Configuration()
            .addAnnotatedClass(User.class)

            .setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect")
            .setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver")
            .setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/mydb")
            .setProperty("hibernate.connection.username", "root")
            .setProperty("hibernate.connection.password", "root")
            .setProperty("hibernate.hbm2ddl", "create")
            .setProperty("hibernate.show_sql", "true");


    SessionFactory sf = cfg.buildSessionFactory();

*/
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
            String sql = "DROP TABLE IF EXISTS users";

            Query query = session.createSQLQuery(sql).addEntity(User.class);
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
//            String sav = "INSERT INTO users (name, lastName, age ) VALUES (?, ?, ?)";
//            Query query = session.createSQLQuery(sav).addEntity(User.class);
 //           query.executeUpdate();
            session.getTransaction().commit();
        } catch (Throwable e) {
            e.printStackTrace();
        }

    }

    @Override
    public void removeUserById(long id) {
        try (Session session = sf.getCurrentSession()) {
            session.beginTransaction();
            String remove = "TRUNCATE TABLE users ";

            Query query = session.createSQLQuery(remove).addEntity(User.class);
            query.executeUpdate();

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
            String clean = "DELETE FROM users";

            Query query = session.createSQLQuery(clean).addEntity(User.class);
            query.executeUpdate();

            session.getTransaction().commit();
        } catch (Throwable e) {
            e.printStackTrace();
        }

    }
}
