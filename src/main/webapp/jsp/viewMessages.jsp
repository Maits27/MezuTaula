<%--
  Created by IntelliJ IDEA.
  User: cvzcaoio
  Date: 2022-03-17
  Time: 9:23 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>

<%@ page import="java.util.*,helper.info.*" %>

<%  ArrayList<MessageInfo> messageList = (ArrayList) request.getAttribute("mezu");
    ServletContext context = request.getServletContext();
    HashMap<String, String> userList = (HashMap<String, String>) context.getAttribute("loggedin_users"); %>

<html>

    <head>
        <title>View Messages</title>
        <link href="/MezuTaula/css/styleSheet.css" rel="stylesheet" />
    </head>

    <body>

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

        <section>
            <table id="Users">
                <tr>
                    <th>Username</th>
                </tr>
                <% if(userList!=null){
                    for(int i = 0; i < userList.size(); i++) {
                        String userInfo = userList.get(session.getId()); %>
                <tr>
                    <td><%= userInfo %></td>
                </tr>
                <% }
                } %>
            </table>
        </section>

        <br>
        <div style="text-align: center">
            <button><a href="/MezuTaula/servlet/Main?logout=true">Log Out</a></button>
        </div>
    </body>

</html>
