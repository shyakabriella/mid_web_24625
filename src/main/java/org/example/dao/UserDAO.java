package org.example.dao;

import org.example.model.Membership;
import org.example.model.User;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class UserDAO {

    /**
     * Saves or updates a user in the database.
     *
     * @param user the user to save
     * @return true if the operation was successful, false otherwise
     */
    public boolean saveUser(User user) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
//            Membership membership= new Membership();
//            membership.setId(1L);
//            user.setMembership(membership);
            transaction = session.beginTransaction();
            session.saveOrUpdate(user);
            transaction.commit();
            System.out.println("User saved successfully.");
            return true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.err.println("Error saving user: " + e.getMessage());
            e.printStackTrace(); // Log the full stack trace for debugging
            return false;
        }
    }

    /**
     * Finds a user by their username.
     *
     * @param username the username to search for
     * @return the User object if found, or null if no user is found
     */
    public User findByUsername(String username) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM User WHERE username = :username", User.class)
                    .setParameter("username", username)
                    .uniqueResult();
        } catch (Exception e) {
            System.err.println("Error finding user by username: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
