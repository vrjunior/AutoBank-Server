package us.guihouse.autobank.servlets.collaborator;

import us.guihouse.autobank.models.client.Client;
import us.guihouse.autobank.models.collaborator.ClientOrdenation;
import us.guihouse.autobank.other.Pager;
import us.guihouse.autobank.repositories.CollaboratorRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * Created by valmir.massoni on 06/12/2016.
 */
@WebServlet(urlPatterns = "/web/list-clients")
public class ListClients extends AuthenticatedServlet {
    @Override
    protected void doGet(AuthenticatedContext context) throws ServletException, IOException, SQLException {
        CollaboratorRepository collaboratorRepository = context.getRepositoryManager().getCollaboratorRepository();
        Pager<Client> pager = Pager.getPager(context);
        collaboratorRepository.getClients(pager);

        HashMap<String, Object> params = new HashMap<>();
        params.put("pager", pager);
        context.forward("/WEB-INF/tags/list-clients.jsp", params);
    }

    @Override
    protected void doPost(AuthenticatedContext context) throws ServletException, IOException {
        context.rejectMethod();
    }

    private String getSearch(AuthenticatedContext context) {
        return context.getRequest().getParameter("search");
    }
    private ClientOrdenation getOrdenation(AuthenticatedContext context) {
        int filter = Integer.parseInt(context.getRequest().getParameter("filter"));
        boolean order = Boolean.parseBoolean(context.getRequest().getParameter("ordernation"));
        return new ClientOrdenation(ClientOrdenation.ClientOrder.values()[filter], order);
    }
}
