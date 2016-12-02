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
<html>
<head>
    <title>Dashboard - AutoBank</title>
</head>
<body>
    <auto:menu-header>

    </auto:menu-header>
    <!-- MDL -->
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons"/>
    <link rel="stylesheet" href="https://code.getmdl.io/1.2.1/material.indigo-blue.min.css" />
    <script defer src="https://code.getmdl.io/1.2.1/material.min.js"></script>
    <!-- JQUERY -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>
</body>
</html>
