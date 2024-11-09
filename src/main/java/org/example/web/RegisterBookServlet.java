package org.example.web;

import org.example.model.Book;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/registerBook")
public class RegisterBookServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        super.init();
        System.out.println("RegisterBookServlet initialized successfully");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Redirect to the book.jsp page
        resp.sendRedirect("book.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Processing form data from the modal in book.jsp
        resp.setContentType("text/html");

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            Book newBook = new Book();
            newBook.setTitle(req.getParameter("title"));
            newBook.setAuthor(req.getParameter("author"));
            newBook.setIsbn(req.getParameter("isbn"));

            session.save(newBook);
            transaction.commit();

            req.setAttribute("message", "Book '" + newBook.getTitle() + "' by " + newBook.getAuthor() + " has been registered successfully.");
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            req.setAttribute("error", "Error during book registration: " + e.getMessage());
        } finally {
            session.close();
        }

        // Forward to a JSP page to display the message or error
        req.getRequestDispatcher("/book.jsp").forward(req, resp);
    }
}
