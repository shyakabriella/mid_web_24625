package org.example.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import org.example.model.User;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;
import javax.servlet.annotation.WebServlet;

@WebServlet("/userDropdownData")
public class UserDropdownServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<User> users = fetchUsers();
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        out.println("<select name='userId'>");
        for (User user : users) {
            out.println("<option value='" + user.getId() + "'>" + user.getName() + "</option>");
        }
        out.println("</select>");
        out.close();
    }

    private List<User> fetchUsers() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Query<User> query = session.createQuery("FROM User", User.class);
            return query.list();
        } finally {
            session.close();
        }
    }
}
