package org.example.dao;

import org.example.model.Membership;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class MembershipDAO {

    // Method to find a membership by ID
    public Membership findMembershipById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Membership.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Method to save or update membership details
    public void saveOrUpdateMembership(Membership membership) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.saveOrUpdate(membership);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
