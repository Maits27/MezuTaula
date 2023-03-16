<%--
  Created by IntelliJ IDEA.
  User: Maitane
  Date: 14/03/2023
  Time: 20:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="helper.info.MessageInfo" %>
<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: Maitane
  Date: 12/03/2023
  Time: 17:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="false"%> <!--Gonbidatu saioa automatikoki sortu ez daiten -->

<html>
<head>
    <title>Title</title>
</head>
<body>
<ul>
    <% ArrayList<MessageInfo> mezuak = (ArrayList<MessageInfo>) request.getAttribute("mezu");
        if(mezuak!=null){
            for(int i =0; i<mezuak.size(); i++){ %>

    <li><%=mezuak.get(i).getUsername()%>: <%=mezuak.get(i).getMessage()%></li>

    <% }
    }else{%>
    <p> JSP-a zuzenean deitu duzu. Ez da atributua kargatu.</p>
    <%}%>
</ul>
</body>
</html>