package us.guihouse.autobank.servlets.collaborator;

import us.guihouse.autobank.models.client.Card;
import us.guihouse.autobank.models.client.Client;
import us.guihouse.autobank.other.IdParser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * Created by guilherme on 08/12/16.
 */

@WebServlet("/web/client/*")
public class ViewClientServlet extends AuthenticatedServlet {
    @Override
    protected void doGet(AuthenticatedContext context) throws ServletException, IOException, SQLException {
        Long id = IdParser.safeParse(context.getLastPathPart());

        if (id == null) {
            context.getResponse().sendError(404);
            return;
        }

        Client client = context.getRepositoryManager().getCollaboratorRepository().getClientById(id);

        if (client == null) {
            context.getResponse().sendError(404);
            return;
        }

        Card activeCard = context.getRepositoryManager().getCardRepository().getActiveCardByClientId(client.getId());

        HashMap<String, Object> params = new HashMap<>();
        params.put("client", client);
        params.put("activeCard", activeCard);
        context.forward("/WEB-INF/tags/show-client.jsp", params);
    }

    @Override
    protected void doPost(AuthenticatedContext context) throws ServletException, IOException, SQLException {
        context.rejectMethod();
    }
}
