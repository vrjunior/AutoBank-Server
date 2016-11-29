<%@ page import="us.guihouse.autobank.repositories.ConnectionManager" %>
<%@ page import="us.guihouse.autobank.repositories.CollaboratorRepository" %>
<%@ page import="us.guihouse.autobank.models.collaborator.Collaborator" %>
<%--
  Created by IntelliJ IDEA.
  User: vrjunior
  Date: 11/10/16
  Time: 21:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%
    session = request.getSession();
    Long idCollaborator = (Long) session.getAttribute("collaboratorId");
    if(idCollaborator != null) {
        ConnectionManager connectionManager = new ConnectionManager();
        CollaboratorRepository collaboratorRepository = new CollaboratorRepository(connectionManager.getConnection());

        try {
            collaboratorRepository.validateCollaboratorById(idCollaborator);
            response.sendRedirect("/AutoBank/main.jsp");
        }
        catch (CollaboratorRepository.NoAuthentication e) {}
    }
%>
<html>
  <head>
    <title>AutoBank Admin - Login</title>
    <link rel="stylesheet" href="/AutoBank/assets/css/login.css"/>
    <!-- MDL -->
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons"/>
    <link rel="stylesheet" href="https://code.getmdl.io/1.2.1/material.indigo-blue.min.css" />
    <script defer src="https://code.getmdl.io/1.2.1/material.min.js"></script>
  </head>
  <body>

    <section id="loginSection">
      <form action="/AutoBank/web/login-collaborator" method="POST">
        <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
          <input class="mdl-textfield__input" type="email" id="email" name="email">
          <label class="mdl-textfield__label" for="email">Email</label>
        </div>
        <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
          <input class="mdl-textfield__input" type="password" id="password" name="password">
          <label class="mdl-textfield__label" for="email">Password</label>
        </div>
          <button class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--accent" id="btnLogin">
            Login
          </button>
        </div>
      </form>
    </section>
  </body>
</html>
