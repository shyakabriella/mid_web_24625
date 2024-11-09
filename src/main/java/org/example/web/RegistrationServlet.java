package org.example.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;

import org.example.model.User;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.mindrot.jbcrypt.BCrypt;

@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        super.init();
        System.out.println("RegistrationServlet initialized successfully");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Set response content type to HTML
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        // Print the HTML form for registration
        out.println("<!DOCTYPE html>");
        out.println("<html lang=\"en\">");
        out.println("<head>");
        out.println("    <meta charset=\"UTF-8\">");
        out.println("    <title>Lib_Man_</title>");
        out.println("    <script src=\"https://cdn.tailwindcss.com\"></script>");
        out.println("</head>");
        out.println("<body>");
        out.println("<div class=\"font-[sans-serif] bg-white md:h-screen\">");
        out.println("    <div class=\"grid md:grid-cols-2 items-center gap-8 h-full\">");
        out.println("        <div class=\"max-md:order-1 p-4 bg-gray-50 h-full\">");
        out.println("            <img src=\"https://readymadeui.com/signin-image.webp\" class=\"lg:max-w-[90%] w-full h-full object-contain block mx-auto\" alt=\"login-image\" />");
        out.println("        </div>");
        out.println("        <div class=\"flex items-center p-6 h-full w-full\">");
        out.println("            <form class=\"max-w-lg w-full mx-auto\" action=\"/register\" method=\"POST\">");
        out.println("                <div class=\"mb-12\">");
        out.println("                    <h3 class=\"text-blue-500 md:text-3xl text-2xl font-extrabold max-md:text-center\">Create an account</h3>");
        out.println("                </div>");
        out.println("                <div>");
        out.println("                    <label class=\"text-gray-800 text-xs block mb-2\">Full Name</label>");
        out.println("                    <input name=\"name\" type=\"text\" required class=\"w-full bg-transparent text-sm border-b border-gray-300 focus:border-blue-500 px-2 py-3 outline-none\" placeholder=\"Enter name\" />");
        out.println("                </div>");
        out.println("                <div class=\"mt-6\">");
        out.println("                    <label class=\"text-gray-800 text-xs block mb-2\">Username</label>");
        out.println("                    <input name=\"username\" type=\"text\" required class=\"w-full bg-transparent text-sm border-b border-gray-300 focus:border-blue-500 px-2 py-3 outline-none\" placeholder=\"Enter username\" />");
        out.println("                </div>");
        out.println("                <div class=\"mt-6\">");
        out.println("                    <label class=\"text-gray-800 text-xs block mb-2\">Email</label>");
        out.println("                    <input name=\"email\" type=\"email\" required class=\"w-full bg-transparent text-sm border-b border-gray-300 focus:border-blue-500 px-2 py-3 outline-none\" placeholder=\"Enter email\" />");
        out.println("                </div>");
        out.println("                <div class=\"mt-6\">");
        out.println("                    <label class=\"text-gray-800 text-xs block mb-2\">Password</label>");
        out.println("                    <input name=\"password\" type=\"password\" required class=\"w-full bg-transparent text-sm border-b border-gray-300 focus:border-blue-500 px-2 py-3 outline-none\" placeholder=\"Enter password\" />");
        out.println("                </div>");
        out.println("                <div class=\"flex items-center mt-6\">");
        out.println("                    <input id=\"remember-me\" name=\"remember-me\" type=\"checkbox\" class=\"h-4 w-4 shrink-0 rounded\" />");
        out.println("                    <label for=\"remember-me\" class=\"ml-3 block text-sm text-gray-800\">");
        out.println("                        I accept the <a href=\"#\" class=\"text-blue-500 font-semibold hover:underline ml-1\">Terms and Conditions</a>");
        out.println("                    </label>");
        out.println("                </div>");
        out.println("                <div class=\"mt-12\">");
        out.println("                    <button type=\"submit\" class=\"w-full py-3 px-6 text-sm tracking-wider font-semibold rounded-md bg-blue-600 hover:bg-blue-700 text-white focus:outline-none\">");
        out.println("                        Create an account");
        out.println("                    </button>");
        out.println("                    <p class=\"text-sm mt-6 text-gray-800\">Already have an account? <a href=\"/\" class=\"text-blue-500 font-semibold hover:underline ml-1\">Login here</a></p>");
        out.println("                </div>");
        out.println("            </form>");
        out.println("        </div>");
        out.println("    </div>");
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Set response content type to HTML
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            User newUser = new User();
            newUser.setName(req.getParameter("name"));
            newUser.setUsername(req.getParameter("username"));
            newUser.setEmail(req.getParameter("email"));
            String hashedPassword = BCrypt.hashpw(req.getParameter("password"), BCrypt.gensalt());
            newUser.setPassword(hashedPassword);
            newUser.setRole("USER"); // Default role set to "USER"

            session.save(newUser);
            transaction.commit();

            out.println("<h1>Registration Successful</h1>");
            out.println("<p>Thank you, " + newUser.getName() + ", you are now registered.</p>");

        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            out.println("<h1>Registration Failed</h1>");
            out.println("<p>Error: " + e.getMessage() + "</p>");
            e.printStackTrace();
        } finally {
            session.close();
        }

        out.println("<a href='/register'>Back to registration</a>");
        out.close();
    }

}
