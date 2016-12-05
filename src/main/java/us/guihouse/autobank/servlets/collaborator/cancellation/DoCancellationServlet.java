package us.guihouse.autobank.servlets.collaborator.cancellation;

import us.guihouse.autobank.repositories.CardLostOrStolenRepository;
import us.guihouse.autobank.servlets.collaborator.AuthenticatedContext;
import us.guihouse.autobank.servlets.collaborator.AuthenticatedServlet;

import javax.servlet.ServletException;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by guilherme on 05/12/16.
 */
public class DoCancellationServlet extends AuthenticatedServlet {
    @Override
    protected void doGet(AuthenticatedContext context) throws ServletException, IOException, SQLException {
        context.rejectMethod();
    }

    @Override
    protected void doPost(AuthenticatedContext context) throws ServletException, IOException {
        CardLostOrStolenRepository repo = context.getRepositoryManager().getCardLostOrStolenRepo();
        repo.removeReasonById()
    }
}
