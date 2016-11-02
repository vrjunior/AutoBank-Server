package servlets;

import com.google.gson.JsonObject;
import models.Client;
import repositories.ClientRepository;
import repositories.ConnectionManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by valmir.massoni on 18/10/2016.
 */
public abstract class TokenAuthentication extends JsonServlet {

    private ClientRepository clientRepository;
    protected Client currentClient;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
        ConnectionManager connectionManager = new ConnectionManager();
        Connection conn = connectionManager.getConnection();
        String currentToken = req.getHeader("Authorization");
        clientRepository = new ClientRepository(conn);

        try {
            currentClient = clientRepository.getClientByTolken(currentToken);
            JsonObject parsed = this.parseToJson(req.getInputStream());
            this.process(conn, parsed, resp);
        } catch (ClientRepository.NoAuthentication noAuthentication) {
            noAuthentication.printStackTrace();
            resp.setStatus(401);
        }
        connectionManager.closeConnection(conn);
    }

    protected abstract void process(Connection conn, JsonObject jsonBody, HttpServletResponse resp);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
        resp.sendRedirect("etc/error.jsp");
    }

}
