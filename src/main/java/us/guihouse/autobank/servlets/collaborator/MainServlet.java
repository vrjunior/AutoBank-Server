package us.guihouse.autobank.servlets.collaborator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;

/**
 * Created by guilherme on 05/12/16.
 */
@WebServlet("/main.jsp")
public class MainServlet extends AuthenticatedServlet {
    @Override
    protected void doGet(AuthenticatedContext context) throws ServletException, IOException {
        context.forward("/WEB-INF/tags/main.jsp");
    }

    @Override
    protected void doPost(AuthenticatedContext context) {
        context.rejectMethod();
    }
}
