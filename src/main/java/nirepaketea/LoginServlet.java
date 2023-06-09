package nirepaketea;

import helper.db.MySQLdb;
import test.TestServlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;

//@WebServlet(name = "LoginServlet", value = "/LoginServlet")

/**
 * Zerbitzarian esaera kudeatu ahal da
 * Datuak sartu datu bat saioan eskuragarri saioan eskegi
 * saioan partekatu beharrean testuinguruan (CONTEXT) partekatuz beste erabiltzaileekin partekatu
 */

public class LoginServlet extends HttpServlet {

    private String mezu;
    private MySQLdb mySQLdb;

    public void init() throws ServletException{
        mezu=" ";
        mySQLdb = new MySQLdb();
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("---> LoginServlet ---> doGet() metodoan sartzen");

        response.setHeader("Cache-Control", "no-cache");

        System.out.println("---> LoginServlet ---> datuak lortu");
        String email = request.getParameter("email");
        String pasahitza = request.getParameter("password");

        if (email==null || pasahitza==null){

            System.out.println("<--- LoginServlet <--- JSP-ra berbidaltzen");
            RequestDispatcher rd = request.getRequestDispatcher("/jsp/loginForm.jsp");
            rd.forward(request, response);

        }else{

            String username = mySQLdb.getUsername(email, pasahitza);
            System.out.println("--->LoginServlet---> Retrieved data from db: "+username);

            if(username!=null){
                System.out.println("<--- LoginServlet <--- Log in ondo");
                HttpSession session= request.getSession(true);   //Saioa lortu:
                                                                    // True: ez bada existitzen sortu
                                                                    // False: ez bada existitzen EZ sortu
                String sessionID = session.getId();
                System.out.println("\tUser session for " + username + ": " + sessionID);
                session.setAttribute("username",  username); // saioari username atributu bezela gehitu

                //LOGEATUTAKO ERABILTZAILEEN ZERRENDA
                System.out.print("\t--->LoginServlet--->Getting loggedin userlist from servlet context: ");
                ServletContext context = request.getServletContext(); // testuingurua (saio guztiek atzipena dute)
                HashMap<String, String> loggedinUsers = (HashMap) context.getAttribute("loggedin_users");

                if(loggedinUsers == null) { // zerbitzaria abiarazi berri bada (ez erabiltzailerik logeatu)

                    System.out.println("--->LoginServlet---> Loggedin userlist from servlet context is empty");
                    loggedinUsers = new HashMap();
                    loggedinUsers.put(username, sessionID);

                } else { // zerbitzarian erabiltzaileak daude jada
                    if(!loggedinUsers.containsKey(username)) {
                        System.out.println("--->LoginServlet---> "+username + " is not in the list");
                        loggedinUsers.put(username,sessionID); //Puede ser al reves porq la sessionID no se deberia repetir

                    } else {System.out.println("--->LoginServlet---> "+username + " is already in the list");}
                }

                // zerrenda testuinguruan gehitu atributu bezela
                context.setAttribute("loggedin_users", loggedinUsers);
                System.out.println("---> LoginServlet---> Loggedin users: " + loggedinUsers.toString());

                System.out.println("---> LoginServlet ---> redirecting to MainServlet");
                RequestDispatcher rd = context.getNamedDispatcher("MainServlet"); //web.xml fitxategiko <servlet-name>
                rd.forward(request, response);

                /*response.setContentType("text/plain");
                PrintWriter http_out= response.getWriter();
                http_out.println("Login OK!");
                mezu="Login OK!";
                request.setAttribute("txarto", mezu);*/

            }else if(request.getSession(false)!=null) {

                System.out.println("---> LoginServlet ---> User already logged: redirecting to MainServlet");
                ServletContext context =request.getServletContext();
                RequestDispatcher rd= context.getNamedDispatcher("MainServlet");
                rd.forward(request, response);

            }else{

                mezu ="Login ERROR";
                request.setAttribute("txarto", mezu);
                System.out.println("<--- LoginServlet <--- pasahitza okerra JSP-ra berbidaltzen");
                RequestDispatcher rd = request.getRequestDispatcher("/jsp/loginForm.jsp");
                rd.forward(request, response); //pasatu kontrola jsp-ra baina servlet-a bere exekuzioa amaitzen du

            }
        }
        System.out.println("<--- LoginServlet <--- doGet() metodotik ateratzen");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
