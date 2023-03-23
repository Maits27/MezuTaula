<%@ page import="java.util.Date" %><%--
  Created by IntelliJ IDEA.
  User: Maitane
  Date: 07/03/2023
  Time: 16:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*" %>
<%@ page session="false"%> <!--Defektuzko sesioa irekitzea ekidin-->
<html>
<head>
    <title>Login Form</title>
    <link href="/MezuTaula/css/styleSheet.css" rel="stylesheet" />
</head>

<header>
    <h1>A webapp to share short messages</h1>
    <h3>Log in form</h3>
</header>

<body>
    <h3>Sartu zure erabiltzailea eta pasahitza:</h3>

    <% Object log_out_aux =request.getAttribute("log_out");
    String ondo = (String) request.getAttribute("txarto");
        if(ondo!=null){%>
            <p> <%=ondo%> </p>
        <% } else if (log_out_aux!=null){
            if((boolean) log_out_aux){%>
                <h5>LOG OUT!!!</h5>
        <%}
        }%>
    <section>
        <form method="POST" action="/MezuTaula/servlet/Login">
            Emaila: <input name="email" type="text" placeholder="Emaila..."/>
            Pasahitza: <input name="password" type="password" placeholder="Pasahitza...">
            <button>Send</button>
        </form>
    </section>
    <footer>
        Server Date: <%=new Date().toString()%> <!--Zerbitzarian idazten da jsp eskatzen denean-->
        <script src="/MezuTaula/js/clientDate.js"></script>
    </footer>
</body>
</html>
