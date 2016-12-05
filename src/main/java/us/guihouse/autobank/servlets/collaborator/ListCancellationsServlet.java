package us.guihouse.autobank.servlets.collaborator;

import us.guihouse.autobank.models.collaborator.CardLostOrStolen;
import us.guihouse.autobank.other.Pager;
import us.guihouse.autobank.repositories.CardLostOrStolenRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

@WebServlet("/web/cancellations")
public class ListCancellationsServlet extends AuthenticatedServlet {
    @Override
    protected void doGet(AuthenticatedContext context) throws SQLException, ServletException, IOException {
        CardLostOrStolenRepository repo = context.getRepositoryManager().getCardLostOrStolenRepo();
        Pager<CardLostOrStolen> pager = Pager.getPager(context);
        repo.getReasons(pager);

        HashMap<String, Object> params = new HashMap<>();
        params.put("pager", pager);
        context.forward("/WEB-INF/tags/list-card-lost.jsp", params);
    }

    @Override
    protected void doPost(AuthenticatedContext context) {
        context.rejectMethod();
    }
}
