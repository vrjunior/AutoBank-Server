package us.guihouse.autobank.servlets;

import us.guihouse.autobank.repositories.ConnectionManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by guilherme on 05/12/16.
 */
public abstract class DatabaseServlet extends javax.servlet.http.HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DatabaseContext context = getContext(req, resp);

        try {
            doGet(context);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            context.getResponse().sendError(500);
        }

        if (context.isRejected()) {
            super.doGet(req, resp);
        }

        context.closeConnectionIfNeeded();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DatabaseContext context = getContext(req, resp);

        try {
            doPost(context);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            context.getResponse().sendError(500);
        }

        if (context.isRejected()) {
            super.doPost(req, resp);
        }

        context.closeConnectionIfNeeded();
    }

    protected abstract void doGet(Context context) throws IOException, ServletException, SQLException;
    protected abstract void doPost(Context context) throws IOException, ServletException, SQLException;

    private DatabaseContext getContext(final HttpServletRequest req, final HttpServletResponse resp) {
        ConnectionManager connectionManager = new ConnectionManager();
        return new DatabaseContext(req, resp, connectionManager);
    }
}
