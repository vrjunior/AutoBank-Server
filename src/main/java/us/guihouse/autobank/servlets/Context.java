package us.guihouse.autobank.servlets;

import us.guihouse.autobank.repositories.RepositoryManager;

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
public interface Context {
    HttpServletRequest getRequest();
    HttpServletResponse getResponse();
    Connection getConnection();
    HttpSession getSession();
    RepositoryManager getRepositoryManager();
    void forward(String destination) throws ServletException, IOException;
    void forward(String destination, HashMap<String, Object> params) throws ServletException, IOException;
    void rejectMethod();
    String getLastPathPart();
    String getBodyParam(String param);
}
