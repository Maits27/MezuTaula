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

<%  ArrayList<MessageInfo> messageList = (ArrayList) request.getAttribute("mezu"); %>

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

    </body>

</html>
