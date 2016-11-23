package us.guihouse.autobank.servlets;

import com.google.gson.JsonObject;
import us.guihouse.autobank.models.Card;
import us.guihouse.autobank.repositories.CardRepository;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by valmir.massoni on 18/10/2016.
 */
@WebServlet(urlPatterns = "/api/card")
public class CardServlet extends TokenAuthentication {

    protected void process(Connection conn, JsonObject jsonBody, HttpServletResponse resp) throws SQLException {
        resp.setHeader("Content-Type", "application/json");

        CardRepository cardRepository = new CardRepository(conn);
        Card card = cardRepository.getActiveCardByClientId(currentClient.getId());

        try {
            Writer out = resp.getWriter();
            getGson().toJson(card, out);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
