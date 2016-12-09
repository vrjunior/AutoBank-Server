package us.guihouse.autobank.servlets.collaborator.cancellation;

import us.guihouse.autobank.models.client.Client;
import us.guihouse.autobank.other.IdParser;
import us.guihouse.autobank.servlets.collaborator.AuthenticatedContext;
import us.guihouse.autobank.servlets.collaborator.AuthenticatedServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by guilherme on 08/12/16.
 */
@WebServlet("/web/new-cancellation")
public class NewCancellationServlet extends AuthenticatedServlet {
    @Override
    protected void doGet(AuthenticatedContext context) throws ServletException, IOException, SQLException {
        context.rejectMethod();
    }

    @Override
    protected void doPost(AuthenticatedContext context) throws ServletException, IOException, SQLException {
        Long cardId = IdParser.safeParse(context.getBodyParam("cardId"));
        Long clientId = IdParser.safeParse(context.getBodyParam("clientId"));

        String reason = context.getBodyParam("reason");

        if (cardId == null || clientId == null) {
            context.getResponse().sendError(400);
            return;
        }

        Client client = context.getRepositoryManager().getCollaboratorRepository().getClientById(clientId);

        if (client == null) {
            context.getResponse().sendError(400);
            return;
        }

        boolean ok = context.getRepositoryManager().getCardRepository().informCardLost(client, cardId, reason);

        if (ok) {
            context.getResponse().sendRedirect(new StringBuilder("cancellation/").append(cardId).toString());
        } else {
            context.getResponse().sendError(400);
        }
    }
}
