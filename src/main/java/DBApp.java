import DAO.Author;
import DAO.Book;
import DAO.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.beans.Transient;
import java.util.*;

public class DBApp {

    private SessionFactory sessionFactory;

    public DBApp() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        MetadataSources sources = new MetadataSources(registry);
        sources.addAnnotatedClass(User.class);
        Metadata metadata = sources.getMetadataBuilder().build();
        sessionFactory = metadata.getSessionFactoryBuilder().build();

        // sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    public void insertUser() {

        List<User> list = new ArrayList<>();
        User user = new User("Sergius", "o@r.com");
        User user2 = new User("Zoran", "o@r.com");
        User user3 = new User("Cosmin", "op@r.com");
        list.add(user);
        list.add(user2);
        list.add(user3);

        for (User u : list) {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            session.persist(u);
            transaction.commit();
            session.close();
        }

    }

    public void updateUser() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        User user = session.find(User.class, 3);

        System.out.println(user.getAge());
        transaction.commit();
        session.close();


    }

    public void deleteUser() {

        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        User user = session.find(User.class, 1);
        session.delete(user);
        transaction.commit();
        session.close();

    }

    public void testMerge() {//delete the old one and change the name parameter
        Session session = sessionFactory.openSession();
        DAO.User user = session.find(User.class, 2);
        session.clear();
        Transaction transaction = session.beginTransaction();
        user.setName("Ciocan");
        session.merge(user);
        transaction.commit();
        session.close();
    }

    public void checkOneToOne() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        User user = session.find(User.class, 3);
        System.out.println("Address city : " + user.getAddress().getCity());
        transaction.commit();
        session.close();
    }

    public void checkOneToMany() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        User user = session.find(User.class, 2);

        System.out.println();
        System.out.println();
        for (Book book : user.getBooks()) {
            System.out.println("Book title : " + book.getTitle());
        }
        transaction.commit();
        session.close();

    }

    public void checkManyToMany() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        User user = session.find(User.class, 2);

        System.out.println();
        System.out.println();
        for (Book book : user.getBooks()) {
            System.out.println(book.getTitle());
            System.out.println();
            for (Author author : book.getAuthors()) {
                System.out.println(author.getName());
            }
        }

        transaction.commit();
        session.close();
    }
}

