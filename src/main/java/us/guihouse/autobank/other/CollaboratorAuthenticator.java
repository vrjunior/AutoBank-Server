package us.guihouse.autobank.other;

import us.guihouse.autobank.models.collaborator.Collaborator;
import us.guihouse.autobank.repositories.CollaboratorRepository;
import us.guihouse.autobank.repositories.ConnectionManager;
import us.guihouse.autobank.servlets.collaborator.AuthenticatedContext;

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
public class CollaboratorAuthenticator {
    public boolean authenticate(AuthenticatedContext context) throws IOException {


        return false;
    }
}
