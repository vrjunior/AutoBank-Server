package us.guihouse.autobank.servlets.collaborator.cancellation;

import us.guihouse.autobank.servlets.collaborator.AuthenticatedContext;
import us.guihouse.autobank.servlets.collaborator.AuthenticatedServlet;

/**
 * Created by guilherme on 05/12/16.
 */
public class ListCancellationsServlet extends AuthenticatedServlet {
    @Override
    protected void doGet(AuthenticatedContext context) {

    }

    @Override
    protected void doPost(AuthenticatedContext context) {
        context.rejectMethod();
    }
}
