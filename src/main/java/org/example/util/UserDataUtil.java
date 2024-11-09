package org.example.util;

import org.example.model.User;
import org.hibernate.Session;
import org.hibernate.query.Query;
import java.util.List;

public class UserDataUtil {

    public static List<User> fetchUsers() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Query<User> query = session.createQuery("FROM User", User.class);
            return query.list();
        } finally {
            session.close();
        }
    }
}
