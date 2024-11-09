package org.example.util;

import org.hibernate.cfg.Configuration;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.SessionFactory;

public class HibernateUtil {
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            Configuration configuration = new Configuration();

            // Hibernate settings equivalent to hibernate.cfg.xml's properties
            configuration.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
            configuration.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/auca_library_db");
            configuration.setProperty("hibernate.connection.username", "root");
            configuration.setProperty("hibernate.connection.password", "");
            configuration.setProperty("dialect", "org.hibernate.dialect.MySQL5Dialect");
            configuration.setProperty("show_sql", "true");
            configuration.setProperty("hibernate.hbm2ddl.auto", "update");

            // Add annotated classes
            configuration.addAnnotatedClass(org.example.model.Location.class);
            configuration.addAnnotatedClass(org.example.model.Person.class);
            configuration.addAnnotatedClass(org.example.model.User.class);
            configuration.addAnnotatedClass(org.example.model.Book.class);
            configuration.addAnnotatedClass(org.example.model.Borrower.class);
            configuration.addAnnotatedClass(org.example.model.Membership.class);
            configuration.addAnnotatedClass(org.example.model.MembershipType.class);
            configuration.addAnnotatedClass(org.example.model.Shelf.class);
            configuration.addAnnotatedClass(org.example.model.Room.class);

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();

            return configuration.buildSessionFactory(serviceRegistry);
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
