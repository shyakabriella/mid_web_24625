package org.example.web;

import org.example.model.Book;
import org.example.util.BookDataUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/bookDropdown")
public class BookDropdownServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Book> books = BookDataUtil.fetchAllBooks();
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        out.println("<select name='bookId'>");
        for (Book book : books) {
            out.println("<option value='" + book.getId() + "'>" + book.getTitle() + "</option>");
        }
        out.println("</select>");
        out.close();
    }
}
