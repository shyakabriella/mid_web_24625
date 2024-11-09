package org.example.web;

import org.example.model.Borrower;
import org.example.model.Book;
import org.example.model.User;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

@WebServlet("/registerBorrower")
public class BorrowerBookServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        super.init();
        System.out.println("BorrowerBookServlet initialized successfully");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Fetch all users and books
            req.setAttribute("users", fetchEntities(session, User.class));
            req.setAttribute("books", fetchEntities(session, Book.class));
        } // Session auto-closed here
        req.getRequestDispatcher("/borrowerForm.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                Borrower newBorrower = createBorrower(req, session, sdf);
                session.save(newBorrower);
                transaction.commit();
                req.setAttribute("message", "New borrowing record added successfully.");
            } catch (Exception e) {
                if (transaction != null) transaction.rollback();
                req.setAttribute("error", "Error during borrowing registration: " + e.getMessage());
            }
        } // Session and transaction are handled cleanly
        req.getRequestDispatcher("/borrowerForm.jsp").forward(req, resp);
    }

    private <T> List<T> fetchEntities(Session session, Class<T> entityType) {
        Query<T> query = session.createQuery("FROM " + entityType.getName(), entityType);
        return query.list();
    }

    private Borrower createBorrower(HttpServletRequest req, Session session, SimpleDateFormat sdf) throws Exception {
        Long userId = Long.parseLong(req.getParameter("userId"));
        Long bookId = Long.parseLong(req.getParameter("bookId"));

        User user = session.get(User.class, userId);
        Book book = session.get(Book.class, bookId);
        if (user == null || book == null) throw new Exception("User or Book not found.");

        Borrower borrower = new Borrower();
        borrower.setUser(user);
        borrower.setBook(book);
        borrower.setBorrowDate(sdf.parse(req.getParameter("borrowDate")));
        if (!req.getParameter("returnDate").isEmpty()) {
            borrower.setReturnDate(sdf.parse(req.getParameter("returnDate")));
        }
        return borrower;
    }
}
