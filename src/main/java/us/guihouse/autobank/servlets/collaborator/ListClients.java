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
        String search = this.getSearch(context);
        ClientOrdenation clientOrdenation = this.getOrdenation(context);
        collaboratorRepository.getClients(search, clientOrdenation, pager);

        HashMap<String, Object> params = new HashMap<>();
        params.put("pager", pager);
        params.put("search", search);
        if(clientOrdenation != null) {
            params.put("desc", clientOrdenation.getDirection());
            params.put("ord", clientOrdenation.getOrdenation().ordinal());
        }
        context.forward("/WEB-INF/tags/list-clients.jsp", params);
    }

    @Override
    protected void doPost(AuthenticatedContext context) throws ServletException, IOException {
        context.rejectMethod();
    }

    private String getSearch(AuthenticatedContext context) {
        return context.getRequest().getParameter(Constants.SEARCH_PARAM);
    }
    private ClientOrdenation getOrdenation(AuthenticatedContext context) {
        String param = context.getRequest().getParameter(Constants.ORDENATION_PARAM);
        int ordenation;
        if(param != null) {
            ordenation = Integer.parseInt(param);
            boolean direction = ("1").equals(context.getRequest().getParameter(Constants.DIRECTION_PARAM))? true: false;
            return new ClientOrdenation(ClientOrdenation.ClientOrder.values()[ordenation], direction);
        }
        return null;
    }
}
