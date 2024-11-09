package org.example;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;
import org.example.util.HibernateUtil;
import org.hibernate.SessionFactory;
import org.example.web.RegistrationServlet;

public class Main {
    public static void main(String[] args) throws Exception {
        Server server = new Server(8081);

        WebAppContext ctx = new WebAppContext();
        ctx.setContextPath("/");
        ctx.setResourceBase("src/main/webapp");
        ctx.setWelcomeFiles(new String[]{"index.html"});

        ctx.addServlet(new ServletHolder(new RegistrationServlet()), "/register");

        server.setHandler(ctx);

        try {
            server.start();
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
