package test;

import com.google.gson.Gson;
import helper.db.MySQLdb;
import helper.info.MessageInfo;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

//@WebServlet(name = "TestServlet", value = "/TestServlet")
public class TestServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("---> TestServlet ---> doGet() metodoan sartzen");

        response.setHeader("Cache-Control", "no-cache"); //TODO es necesario?
        String type = request.getParameter("type");

        try {
            MySQLdb db = new MySQLdb();
            if (type.equals("registerUser")){
                System.out.println("---> TestServlet ---> register user egiten");

                String emaila = request.getParameter("email");
                String pasahitza = request.getParameter("password");
                String erabiltzailea = request.getParameter("username");

                db.setUserInfo(emaila, pasahitza,erabiltzailea);

                response.setContentType("text/plain");
                PrintWriter http_out = response.getWriter();
                http_out.println("Datu gordeketa ondo burutu da");

            }else if (type.equals("getUsername")){
                System.out.println("---> TestServlet ---> get username egiten");

                String emaila = request.getParameter("email"); //TODO ponia email
                String pasahitza = request.getParameter("password");

                String username = db.getUsername(emaila, pasahitza);

                response.setContentType("text/plain");
                PrintWriter http_out = response.getWriter();
                http_out.println(username);
            }else if (type.equals("registerMessage")){
                System.out.println("---> TestServlet ---> register message egiten");

                String erabiltzailea = request.getParameter("username");
                String mezua = request.getParameter("message");

                db.setMessageInfo(mezua, erabiltzailea);

                response.setContentType("text/plain");
                PrintWriter http_out = response.getWriter();
                http_out.println("Datu gordeketa ondo burutu da");
            }else if(type.equals("getAllMessages")){
                System.out.println("---> TestServlet ---> get all messages egiten");

                ArrayList<MessageInfo> mezuak = db.getAllMessages();
                if(request.getParameter("format").equals("json")){
                    System.out.println("---> TestServlet ---> get all messages egiten (JSON)");
                    String json = new Gson().toJson(mezuak);

                    response.setContentType("application/json");
                    PrintWriter http_out = response.getWriter();
                    http_out.println(json);
                }else if(request.getParameter("format").equals("html")){
                    System.out.println("---> TestServlet ---> get all messages egiten (HTML)");
                    request.setAttribute("mezu", mezuak);

                    RequestDispatcher rd = request.getRequestDispatcher("/jsp/mezuak.jsp");
                    rd.forward(request, response); //pasatu kontrola jsp-ra baina servlet-a bere exekuzioa amaitzen du
                }
                System.out.println("<--- TestServlet <--- doGet() metodotik ateratzen");
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("---> TestServlet ---> doPost() metodoan sartzen");
        doGet(request, response);
    }
}
