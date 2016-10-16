package servlets;

import com.google.gson.Gson;
import models.Token;
import repositories.ClientRepository;
import repositories.ConnectionManager;

import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

/**
 * Created by vrjunior on 15/10/16.
 */
@WebServlet(urlPatterns = "/LoginServlet")
public class LoginServlet extends javax.servlet.http.HttpServlet {

    private Connection conn;
    private ClientRepository clientRepository;

    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        String emailOrCpf = request.getParameter("email");
        String password = request.getParameter("password");
        Token currentToken;

        ConnectionManager connectionManager = new ConnectionManager();
        conn = connectionManager.getConnection();
        clientRepository = new ClientRepository(conn);
        currentToken = clientRepository.performLogin(emailOrCpf, password);
        if(currentToken == null) {
            response.setStatus(401);
            return;
        }
        response.setHeader("Content-Type", "application/json");
        Gson gson = new Gson();
        PrintWriter out = response.getWriter();
        out.print(gson.toJson(currentToken.getToken()));

    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        response.sendRedirect("etc/error.jsp");
    }
}
