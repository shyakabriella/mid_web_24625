package org.example.service;

import org.example.dao.UserDAO;
import org.example.model.User;
import org.example.model.Membership;
import org.mindrot.jbcrypt.BCrypt;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.example.util.HibernateUtil;

public class UserService {
    private UserDAO userDAO = new UserDAO();

    public Membership getMembershipByType(String type) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Membership WHERE name = :typeName", Membership.class)
                    .setParameter("typeName", type)
                    .uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean registerUser(User user) {
        System.out.println("Starting user registration process.");

        // Hash the password
        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        user.setPassword(hashedPassword);

        // Try saving the user
        boolean result = userDAO.saveUser(user);
        System.out.println("User registration result: " + result);

        if (result) {
            System.out.println("User registered successfully with ID: " + user.getId());
        } else {
            System.err.println("User registration failed.");
        }

        return result;
    }


    public User authenticate(String username, String password) {
        User user = userDAO.findByUsername(username);
        if (user != null && BCrypt.checkpw(password, user.getPassword())) {
            return user;
        }
        return null;
    }
}
