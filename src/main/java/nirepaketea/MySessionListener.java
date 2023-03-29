package nirepaketea;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.util.HashMap;
import java.util.Map;

//@WebListener
public class MySessionListener implements ServletContextListener, HttpSessionListener, HttpSessionAttributeListener {

    private SimpleDateFormat data_formatua;

    public MySessionListener() {
        data_formatua = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
        data_formatua.setTimeZone(TimeZone.getTimeZone("Europe/Madrid"));
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        /* This method is called when the servlet context is initialized(when the Web application is deployed). */
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        /* This method is called when the servlet Context is undeployed or Application Server shuts down. */
    }

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        /* Session is created. */
        System.out.println(" ---> MySessionListener ---> A session is being created at");
        Date data = new Date();
        System.out.println(data_formatua.format(data));
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        /* Session is destroyed. */
        System.out.println("---> MySessionListener ---> A session is being destroyed at");
        Date data = new Date();

        HttpSession session = se.getSession();
        String sessionID = session.getId();
        System.out.println("\tGetting user sessionID: " + sessionID);

        ServletContext context = session.getServletContext();
        HashMap<String, String> loggedinUsers = (HashMap) context.getAttribute("loggedin_users");
        System.out.println("\tLoggedin users: " + loggedinUsers.toString());

        for(Map.Entry<String, String> entry : loggedinUsers.entrySet()) {
            if(entry.getValue().equals(session.getAttribute("username"))){
                loggedinUsers.remove(entry.getKey());
                System.out.println("\tRemoving " + entry.getValue() + " from loggedin users");
                context.setAttribute("loggedin_users", loggedinUsers);
                System.out.println("\tLoggedin users: " + loggedinUsers.toString());
                break;
            }
        }
        System.out.println(data_formatua.format(data));
    }

    @Override
    public void attributeAdded(HttpSessionBindingEvent sbe) {
        /* This method is called when an attribute is added to a session. */
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent sbe) {
        /* This method is called when an attribute is removed from a session. */
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent sbe) {
        /* This method is called when an attribute is replaced in a session. */
    }
}
