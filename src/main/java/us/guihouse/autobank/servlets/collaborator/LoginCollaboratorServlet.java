package us.guihouse.autobank.servlets.collaborator;

import us.guihouse.autobank.models.collaborator.Collaborator;
import us.guihouse.autobank.repositories.CollaboratorRepository;
import us.guihouse.autobank.repositories.ConnectionManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by vrjunior on 25/11/16.
 */
@WebServlet(urlPatterns = "/web/login-collaborator")
public class LoginCollaboratorServlet extends javax.servlet.http.HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        Collaborator collaborator;

        ConnectionManager connectionManager = new ConnectionManager();
        CollaboratorRepository collaboratorRepository = new CollaboratorRepository(connectionManager.getConnection());
        try {
            collaborator = collaboratorRepository.performLogin(email, password);
            HttpSession session = req.getSession();
            session.setAttribute("collaboratorId", collaborator.getId());
        } catch (CollaboratorRepository.NoAuthentication noAuthentication) {
            resp.setStatus(501);
            return;
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }
}
