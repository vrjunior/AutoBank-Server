package us.guihouse.autobank.servlets.collaborator.cancellation;

import us.guihouse.autobank.models.collaborator.CardLostOrStolen;
import us.guihouse.autobank.other.IdParser;
import us.guihouse.autobank.repositories.CardLostOrStolenRepository;
import us.guihouse.autobank.servlets.collaborator.AuthenticatedContext;
import us.guihouse.autobank.servlets.collaborator.AuthenticatedServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * Created by guilherme on 05/12/16.
 */
@WebServlet("web/cancellation/*")
public class ViewCancellationServlet extends AuthenticatedServlet {
    @Override
    protected void doGet(AuthenticatedContext context) throws ServletException, IOException, SQLException {
        Long id = IdParser.safeParse(context.getLastPathPart());
        CardLostOrStolenRepository repo = context.getRepositoryManager().getCardLostOrStolenRepo();
        CardLostOrStolen reason = repo.getReasonById(id);

        if (reason == null) {
            context.getResponse().sendError(404);
            return;
        }

        HashMap<String, Object> params = new HashMap<>();
        params.put("reason", reason);
        context.forward("/WEB-INF/tags/show-card-lost-stolen", params);
    }

    @Override
    protected void doPost(AuthenticatedContext context) throws ServletException, IOException {
        context.rejectMethod();
    }
}
