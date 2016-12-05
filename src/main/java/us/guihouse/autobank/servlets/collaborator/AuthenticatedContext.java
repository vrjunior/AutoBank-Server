package us.guihouse.autobank.servlets.collaborator;

import us.guihouse.autobank.models.collaborator.Collaborator;
import us.guihouse.autobank.repositories.CollaboratorRepository;
import us.guihouse.autobank.repositories.RepositoryManager;
import us.guihouse.autobank.servlets.Context;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;

/**
 * Created by guilherme on 05/12/16.
 */
public class AuthenticatedContext implements Context {
    private Collaborator collaborator;
    private Context base;

    public AuthenticatedContext(Context context) {
        base = context;
    }

    public Collaborator getCollaborator() {
        return collaborator;
    }

    public void setCollaborator(Collaborator collaborator) {
        this.collaborator = collaborator;
    }

    @Override
    public HttpServletRequest getRequest() {
        return base.getRequest();
    }

    @Override
    public HttpServletResponse getResponse() {
        return base.getResponse();
    }

    @Override
    public Connection getConnection() {
        return base.getConnection();
    }

    @Override
    public HttpSession getSession() {
        return base.getSession();
    }

    @Override
    public RepositoryManager getRepositoryManager() {
        return base.getRepositoryManager();
    }

    @Override
    public void forward(String destination) throws ServletException, IOException {
        base.forward(destination);
    }

    @Override
    public void forward(String destination, HashMap<String, Object> params) throws ServletException, IOException {
        base.forward(destination, params);
    }

    @Override
    public void rejectMethod() {
        base.rejectMethod();
    }
}
