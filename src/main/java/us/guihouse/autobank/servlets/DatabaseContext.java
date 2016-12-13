package us.guihouse.autobank.servlets;

import us.guihouse.autobank.repositories.CollaboratorRepository;
import us.guihouse.autobank.repositories.ConnectionManager;
import us.guihouse.autobank.repositories.RepositoryManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Contexto generico com os métodos de database implementados.
 * Created by guilherme on 05/12/16.
 */
public class DatabaseContext implements Context {
    private HttpServletRequest request;
    private HttpServletResponse response;
    private ConnectionManager connectionManager;
    private Connection connection;
    private RepositoryManager repositoryManager;
    private boolean rejected;
    private String responseBody;

    public DatabaseContext(HttpServletRequest request, HttpServletResponse response, ConnectionManager connectionManager) {
        this.request = request;
        this.response = response;
        this.connectionManager = connectionManager;
    }

    public HttpServletRequest getRequest() {
        return  this.request;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public Connection getConnection() {
        if (connection == null) {
            connection = connectionManager.getConnection();
        }

        return connection;
    }

    public HttpSession getSession() {
        return request.getSession();
    }

    @Override
    public RepositoryManager getRepositoryManager() {
        if (repositoryManager == null) {
            repositoryManager = new RepositoryManager(getConnection());
        }

        return repositoryManager;
    }

    @Override
    public void forward(String destination) throws ServletException, IOException {
        request.getRequestDispatcher(destination).forward(request,response);
    }

    @Override
    public void forward(String destination, HashMap<String, Object> params) throws ServletException, IOException {
        for (Map.Entry<String,Object> param : params.entrySet()) {
            request.setAttribute(param.getKey(), param.getValue());
        }

        forward(destination);
    }

    public void rejectMethod() {
        rejected = true;
    }

    @Override
    public String getLastPathPart() {
        String[] pathInfo = request.getPathInfo().split("/");

        if (pathInfo.length < 1) {
            return "";
        }

        return pathInfo[pathInfo.length - 1];
    }

//    private String getResponseBody() throws IOException {
//        if (responseBody == null) {
//            StringBuilder builder = new StringBuilder();
//            BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
//            String line;
//
//            while ((line = reader.readLine()) != null) {
//                builder.append(line);
//            }
//
//            responseBody = builder.toString();
//        }
//
//        return responseBody;
//    }

    @Override
    public String getBodyParam(String param) {
        return request.getParameter(param);
    }

    public void closeConnectionIfNeeded() {
        if (connection != null) {
            connectionManager.closeConnection(connection);
            connection = null;
            repositoryManager = null;
        }
    }

    public boolean isRejected() {
        return rejected;
    }
}
