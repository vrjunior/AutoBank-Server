package us.guihouse.autobank.servlets.collaborator;

import us.guihouse.autobank.repositories.CollaboratorRepository;
import us.guihouse.autobank.servlets.Context;
import us.guihouse.autobank.servlets.DatabaseServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by guilherme on 05/12/16.
 */
public abstract class AuthenticatedServlet extends DatabaseServlet {
    @Override
    protected void doGet(Context context) throws IOException, ServletException, SQLException {
        try {
            doGet(process(context));
        } catch (NotAuthenticated notAuthenticated) {
            context.getResponse().sendError(401);
        }
    }

    @Override
    protected void doPost(Context context) throws IOException, ServletException, SQLException {
        try {
            doPost(process(context));
        } catch (NotAuthenticated notAuthenticated) {
            context.getResponse().sendError(401);
        }
    }

    protected abstract void doGet(AuthenticatedContext context) throws ServletException, IOException, SQLException;
    protected abstract void doPost(AuthenticatedContext context) throws ServletException, IOException, SQLException;

    private class NotAuthenticated extends Exception {};

    private AuthenticatedContext process(Context context) throws NotAuthenticated {
        AuthenticatedContext authenticated = new AuthenticatedContext(context);

        HttpSession session = context.getSession();

        Long id = (Long) session.getAttribute("collaboratorId");

        if (id == null) {
            throw new NotAuthenticated();
        }

        CollaboratorRepository collaboratorRepository = context.getRepositoryManager().getCollaboratorRepository();

        try {
            authenticated.setCollaborator(collaboratorRepository.validateCollaboratorById(id));
            return authenticated;
        } catch (CollaboratorRepository.NoAuthentication noAuthentication) {
            throw new NotAuthenticated();
        }
    }
}
