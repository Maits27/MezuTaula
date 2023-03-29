package nirepaketea;

import helper.db.MySQLdb;
import helper.info.MessageInfo;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

//@WebServlet(name = "MainServlet", value = "/MainServlet")
public class MainServlet extends HttpServlet {

    private String mezu;
    private MySQLdb mySQLdb;

    public void init() throws ServletException{
        mezu=" ";
        mySQLdb = new MySQLdb();
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("---> MainServlet ---> doGet() metodoan sartzen");

        String logout =request.getParameter("logout");
        System.out.println("---> MainServlet ---> Logout: "+ logout);
        response.setHeader("Cache-Control", "no-cache");

        if(logout!=null){
            if(logout.equals("true")){
                System.out.println("---> MainServlet ---> User log out: redirecting to login form");
                HttpSession session = request.getSession(false);
                session.invalidate(); //saioa amaitu

                boolean log_out=true;
                request.setAttribute("log_out", log_out);
                RequestDispatcher rd= request.getRequestDispatcher("/jsp/loginForm.jsp");
                rd.forward(request, response);
            }
        }else{
            if(request.getSession(false) == null){
                System.out.println("---> MainServlet ---> User is not logged in: redirecting to login form");
                RequestDispatcher rd = request.getRequestDispatcher("/jsp/loginForm.jsp");
                rd.forward(request, response);
            }else{
                System.out.println("---> MainServlet ---> User is logged: redirecting to viewMessages.jsp");
                ArrayList<MessageInfo> messageList = mySQLdb.getAllMessages();
                request.setAttribute("mezu", messageList);

                System.out.print("Getting loggedin userlist from context: ");
                ServletContext context = request.getServletContext(); // testuingurua (saio guztiek atzipena dute)
                HashMap<String, String> loggedinUsers = (HashMap) context.getAttribute("loggedin_users");

                request.setAttribute("user_list", loggedinUsers);

                RequestDispatcher rd = request.getRequestDispatcher("/jsp/viewMessages.jsp");
                rd.forward(request, response);
            }
        }

        System.out.println("<--- MainServlet <--- doGet() metodotik ateratzen");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
