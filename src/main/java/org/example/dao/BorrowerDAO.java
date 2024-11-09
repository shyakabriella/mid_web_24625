package org.example.dao;

import org.example.model.Book;
import org.example.model.Borrower;
import org.example.model.User;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.util.List;
import java.util.Collections;

public class BorrowerDAO {

    // Save or update a borrower
    public void saveOrUpdateBorrower(Borrower borrower) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.saveOrUpdate(borrower);
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                System.err.println("Error during saveOrUpdate Borrower: " + e.getMessage());
                throw e; // Re-throw to handle or log by caller
            }
        } catch (Exception e) {
            System.err.println("Error opening session for saveOrUpdate Borrower: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Find a borrower by ID
    public Borrower findBorrowerById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Borrower.class, id);
        } catch (Exception e) {
            System.err.println("Error finding borrower by ID: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    // Find an active borrower by a specific book
    public Borrower findBorrowerByBook(Book book) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Borrower> query = session.createQuery("FROM Borrower b WHERE b.book = :book AND b.returnDate IS NULL", Borrower.class);
            query.setParameter("book", book);
            return query.uniqueResult();
        } catch (Exception e) {
            System.err.println("Error finding borrower by book: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    // Delete a borrower
    public void deleteBorrower(Borrower borrower) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.delete(borrower);
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                System.err.println("Error deleting borrower: " + e.getMessage());
                throw e;
            }
        } catch (Exception e) {
            System.err.println("Error opening session for deleting borrower: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // List all borrowers
    public List<Borrower> findAllBorrowers() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Borrower", Borrower.class).getResultList();
        } catch (Exception e) {
            System.err.println("Error retrieving all borrowers: " + e.getMessage());
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    // Method to find all borrowers associated with a specific user
    public List<Borrower> findBorrowersByUser(User user) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Borrower> query = session.createQuery("FROM Borrower b WHERE b.user = :user", Borrower.class);
            query.setParameter("user", user);
            return query.list();
        } catch (Exception e) {
            System.err.println("Error finding borrowers by user: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
