package us.guihouse.autobank.servlets.collaborator.cancellation;

import us.guihouse.autobank.models.collaborator.CardLostOrStolen;
import us.guihouse.autobank.other.PageParser;
import us.guihouse.autobank.repositories.CardLostOrStolenRepository;
import us.guihouse.autobank.servlets.collaborator.AuthenticatedContext;
import us.guihouse.autobank.servlets.collaborator.AuthenticatedServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by guilherme on 05/12/16.
 */
@WebServlet("cancellations")
public class ListCancellationsServlet extends AuthenticatedServlet {
    @Override
    protected void doGet(AuthenticatedContext context) throws SQLException, ServletException, IOException {
        Long page = PageParser.getPage(context);
        CardLostOrStolenRepository repo = context.getRepositoryManager().getCardLostOrStolenRepo();
        ArrayList<CardLostOrStolen> reasons = repo.getReasons(page);
        Long total = repo.getTotalReasons();
        HashMap<String, Object> params = new HashMap<>();
        params.put("reasons", reasons);
        params.put("total", total);
        context.forward("/WEB-INF/tags/list-card-lost.jsp", params);
    }

    @Override
    protected void doPost(AuthenticatedContext context) {
        context.rejectMethod();
    }
}
