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
import org.hibernate.query.Query;
import org.mindrot.jbcrypt.BCrypt;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        super.init();
        System.out.println("LoginServlet initialized successfully");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Set response content type to HTML
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        // Display the login form
        out.println("<!DOCTYPE html>");
        out.println("<html lang='en'>");
        out.println("<head>");
        out.println("    <meta charset='UTF-8'>");
        out.println("    <title>Login</title>");
        out.println("    <script src='https://cdn.tailwindcss.com'></script>");
        out.println("</head>");
        out.println("<body>");
        out.println("<div class='font-[sans-serif] bg-white flex items-center justify-center md:h-screen p-4'>");
        out.println("    <div class='shadow-[0_2px_16px_-3px_rgba(6,81,237,0.3)] max-w-6xl max-md:max-w-lg rounded-md p-6'>");
        out.println("        <div class='grid md:grid-cols-2 items-center gap-8'>");
        out.println("            <div class='max-md:order-1 lg:min-w-[450px]'>");
        out.println("                <img src='https://readymadeui.com/signin-image.webp' class='lg:w-11/12 w-full object-cover' alt='login-image' />");
        out.println("            </div>");
        out.println("            <form class='md:max-w-md w-full mx-auto' action='/login' method='POST'>");
        out.println("                <div class='mb-12'>");
        out.println("                    <h3 class='text-4xl font-extrabold text-blue-600'>Sign in</h3>");
        out.println("                </div>");
        out.println("                <div>");
        out.println("                    <div class='relative flex items-center'>");
        out.println("                        <input name='username' type='text' required class='w-full text-sm border-b border-gray-300 focus:border-blue-600 px-2 py-3 outline-none' placeholder='Enter username' />");
        out.println("                    </div>");
        out.println("                </div>");
        out.println("                <div class='mt-8'>");
        out.println("                    <div class='relative flex items-center'>");
        out.println("                        <input name='password' type='password' required class='w-full text-sm border-b border-gray-300 focus:border-blue-600 px-2 py-3 outline-none' placeholder='Enter password' />");
        out.println("                    </div>");
        out.println("                </div>");
        out.println("                <div class='flex flex-wrap items-center justify-between gap-4 mt-6'>");
        out.println("                    <div class='flex items-center'>");
        out.println("                        <input id='remember-me' name='remember-me' type='checkbox' class='h-4 w-4 shrink-0 text-blue-600 focus:ring-blue-500 border-gray-300 rounded' />");
        out.println("                        <label for='remember-me' class='text-gray-800 ml-3 block text-sm'>Remember me</label>");
        out.println("                    </div>");
        out.println("                    <a href='javascript:void(0);' class='text-blue-600 font-semibold text-sm hover:underline'>Forgot Password?</a>");
        out.println("                </div>");
        out.println("                <div class='mt-12'>");
        out.println("                    <button type='submit' class='w-full shadow-xl py-2.5 px-5 text-sm font-semibold rounded-md text-white bg-blue-600 hover:bg-blue-700 focus:outline-none'>Sign in</button>");
        out.println("                    <p class='text-gray-800 text-sm text-center mt-6'>Don't have an account? <a href='register' class='text-blue-600 font-semibold hover:underline ml-1 whitespace-nowrap'>Register here</a></p>");
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
        // Process the login form
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            // Query to find the user with the provided username
            Query<User> query = session.createQuery("FROM User WHERE username = :username", User.class);
            query.setParameter("username", username);
            User user = query.uniqueResult();

            if (user != null && BCrypt.checkpw(password, user.getPassword())) {
                req.getSession().setAttribute("user", user);  // Storing user in session
                resp.sendRedirect("dashboard.jsp");  // Redirecting to the JSP
            } else {
                out.println("<h1>Login Failed</h1>");
                out.println("<p>Invalid username or password.</p>");
                out.println("<a href='/login'>Try Again</a>");
            }
        } catch (Exception e) {
            out.println("<h1>Error</h1>");
            out.println("<p>Error processing your login request: " + e.getMessage() + "</p>");
            e.printStackTrace();
            out.println("<a href='/login'>Try Again</a>");
        } finally {
            session.close();
        }

        out.close();
    }
}
