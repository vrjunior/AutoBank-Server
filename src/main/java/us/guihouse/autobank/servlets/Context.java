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
 * Context genério das classes, para reutilizar a conexão com o banco, repositoryManager e session.
 * Created by guilherme on 05/12/16.
 */
public interface Context {
    HttpServletRequest getRequest();
    HttpServletResponse getResponse();
    Connection getConnection();
    HttpSession getSession();
    RepositoryManager getRepositoryManager();
    /**
     * Redireciona para outra url.
     */
    void forward(String destination) throws ServletException, IOException;
    /**
     * Redireciona para outra url enviando parâmetros via GET.
     */
    void forward(String destination, HashMap<String, Object> params) throws ServletException, IOException;
    /**
     * Rejeita a request, chama a class super da servlet e mostra mensagem de verbo http não suportado.
     */
    void rejectMethod();
    String getLastPathPart();
    String getBodyParam(String param);
}
