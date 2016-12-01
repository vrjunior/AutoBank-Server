package us.guihouse.autobank.servlets.collaborator;

import us.guihouse.autobank.models.collaborator.Collaborator;
import us.guihouse.autobank.repositories.CollaboratorRepository;
import us.guihouse.autobank.repositories.ConnectionManager;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;

/**
 * Created by vrjunior on 25/11/16.
 */
public class JSessionAuthentication {
    protected Collaborator collaborator;
    protected ConnectionManager connectionManager;
    protected Connection conn;

    public void authenticate (HttpServletRequest req, HttpServletResponse resp) throws IOException {
        connectionManager = new ConnectionManager();
        HttpSession session = req.getSession();
        Long id = (Long) session.getAttribute("collaboratorId");
        if(id == null) {
            resp.setStatus(401);
            resp.sendError(401);
            return;
        }
        this.conn = this.connectionManager.getConnection();
        CollaboratorRepository collaboratorRepository = new CollaboratorRepository(this.conn);
        try {
            collaborator = collaboratorRepository.validateCollaboratorById(id);
        } catch (CollaboratorRepository.NoAuthentication noAuthentication) {
            resp.setStatus(401);
            resp.sendError(401);
            return;
        }
    }
}
