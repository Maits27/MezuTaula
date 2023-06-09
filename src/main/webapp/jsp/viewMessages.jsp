<%--
  Created by IntelliJ IDEA.
  User: cvzcaoio
  Date: 2022-03-17
  Time: 9:23 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@ page session="false"%> <!--Defektuzko sesioa irekitzea ekidin-->

<%@ page import="java.util.*,helper.info.*" %>

<%  ArrayList<MessageInfo> messageList = (ArrayList) request.getAttribute("mezu");

    HttpSession session = request.getSession();
    String username = (String) session.getAttribute("username");
    ServletContext context = request.getServletContext();
    HashMap<String, String> userList = (HashMap<String, String>) context.getAttribute("loggedin_users"); %>

<html>

    <head>
        <title>View Messages</title>
        <link href="/MezuTaula/css/styleSheet.css" rel="stylesheet" />
    </head>

    <body>
        <section>
            <font color="#f5f5dc">Zure erabiltzailea <%=username%> da.</font>
        </section>
        <section>
            <font color="#f5f5dc">Active users:</font>
            <%for(Map.Entry<String, String> entry : userList.entrySet()) {%>
                <font color="#a9a9a9"><%= entry.getKey() %></font>
            <% }%>
        </section>
        <section>
            <font color="#f5f5dc">Aktiboak dauden erabiltzaileen zerrenda: </font>
            <table id="Active users">
                <tr>
                    <th>Username</th>
                </tr>
                <%
                    for(Map.Entry<String, String> entry : userList.entrySet()) {%>
                <tr>
                    <td><%= entry.getKey() %></td>
                </tr>
                <% }%>
            </table>
        </section>

        <section>
            <table id="MezuTaula">
                <tr>
                    <th>Username</th>
                    <th>Message</th>
                </tr>
                <% for(int i = 0; i < messageList.size(); i++) {
                    MessageInfo messageInfo = messageList.get(i); %>
                    <tr>
                        <td><%= messageInfo.getUsername() %></td>
                        <td><%= messageInfo.getMessage() %></td>
                    </tr>
                <% } %>
            </table>
        </section>


        <br>
        <div style="text-align: center">
            <button><a href="/MezuTaula/servlet/Main?logout=true">Log Out</a></button>
        </div>
    </body>

</html>
