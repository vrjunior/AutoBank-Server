package servlets;

import com.google.gson.JsonObject;

import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;

/**
 * Created by valmir.massoni on 16/11/2016.
 */
public class FinantialStatementsServlet extends TokenAuthentication {
    protected void process(Connection conn, JsonObject jsonBody, HttpServletResponse resp) {

    }
}
