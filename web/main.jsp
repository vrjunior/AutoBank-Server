<%@ page import="us.guihouse.autobank.servlets.collaborator.JSessionAuthentication" %><%--
  Created by IntelliJ IDEA.
  User: aluno
  Date: 29/11/16
  Time: 18:39
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"  %>
<%
    JSessionAuthentication authentication = new JSessionAuthentication();
    authentication.authenticate(request, response);
%>
<html>
<head>
    <title>Dashboard - AutoBank</title>
    <!-- MDL -->
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons"/>
    <link rel="stylesheet" href="https://code.getmdl.io/1.2.1/material.indigo-blue.min.css" />
    <script defer src="https://code.getmdl.io/1.2.1/material.min.js"></script>
</head>
<body>
    <div class="mdl-layout mdl-js-layout mdl-layout--fixed-header">
        <header class="mdl-layout__header">
            <div class="mdl-layout__header-row">
                <!-- Title -->
                <span class="mdl-layout-title">AutoBank</span>
            </div>
            <!-- Tabs -->
            <div class="mdl-layout__tab-bar mdl-js-ripple-effect">
                <a href="#scroll-tab-1" class="mdl-layout__tab is-active">Clientes</a>
                <a href="#scroll-tab-2" class="mdl-layout__tab">Solicitações de cancelamento</a>
                <a href="#scroll-tab-3" class="mdl-layout__tab">Tab 3</a>
                <a href="#scroll-tab-4" class="mdl-layout__tab">Tab 4</a>
                <a href="#scroll-tab-5" class="mdl-layout__tab">Tab 5</a>
                <a href="#scroll-tab-6" class="mdl-layout__tab">Tab 6</a>
            </div>
        </header>
        <main class="mdl-layout__content">
            <section class="mdl-layout__tab-panel is-active" id="scroll-tab-1">
                <div class="page-content"><!-- Your content goes here --></div>
            </section>
            <section class="mdl-layout__tab-panel" id="scroll-tab-2">
                <div class="page-content"><!-- Your content goes here --></div>
            </section>
            <section class="mdl-layout__tab-panel" id="scroll-tab-3">
                <div class="page-content"><!-- Your content goes here --></div>
            </section>
            <section class="mdl-layout__tab-panel" id="scroll-tab-4">
                <div class="page-content"><!-- Your content goes here --></div>
            </section>
            <section class="mdl-layout__tab-panel" id="scroll-tab-5">
                <div class="page-content"><!-- Your content goes here --></div>
            </section>
            <section class="mdl-layout__tab-panel" id="scroll-tab-6">
                <div class="page-content"><!-- Your content goes here --></div>
            </section>
        </main>
    </div>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>

</body>
</html>
