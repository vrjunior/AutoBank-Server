<%@ page import="us.guihouse.autobank.servlets.collaborator.JSessionAuthentication" %><%--
  Created by IntelliJ IDEA.
  User: aluno
  Date: 29/11/16
  Time: 18:39
  To change this template use File | Settings | File Templates.
--%>
<%@taglib prefix="auto" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"  %>
<%
    JSessionAuthentication authentication = new JSessionAuthentication();
    authentication.authenticate(request, response);
%>

<auto:admin-template>
    <jsp:body>
        <h1>hu3 hu3 guilherme ruby fanboy</h1>
    </jsp:body>
</auto:admin-template>

