package org.example.util;

import org.example.model.Book;
import org.hibernate.Session;
import org.hibernate.query.Query;
import java.util.List;

/**
 * A utility class to fetch book data from the database.
 */
public class BookDataUtil {

    /**
     * Fetches all books from the database using a session from the session factory.
     * @return a List of Book objects.
     */
    public static List<Book> fetchAllBooks() {
        // Open a session using the static method from HibernateUtil
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            // Create a Hibernate Query (HQL)
            Query<Book> query = session.createQuery("FROM Book", Book.class);
            return query.list(); // Execute the query and return the list of Book objects
        } finally {
            if (session != null) {
                session.close(); // Ensure the session is closed after the operation
            }
        }
    }
}
