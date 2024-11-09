package org.example.listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import org.hibernate.SessionFactory;
import org.example.util.HibernateUtil;

@WebListener
public class AppContextListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory(); // Ensure this method initializes the SessionFactory
        if (sessionFactory != null) {
            System.out.println("Hibernate SessionFactory Configured successfully");
        } else {
            System.out.println("Error configuring Hibernate SessionFactory");
        }
    }


}
