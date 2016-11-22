package us.guihouse.autobank.servlets;

import com.google.gson.JsonObject;
import us.guihouse.autobank.repositories.CardRepository;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

/**
 * Created by valmir.massoni on 18/11/2016.
 */
@WebServlet(urlPatterns = "/CardLostOrStolen")
public class CardLostServlet extends TokenAuthentication {

    protected void process(Connection conn, JsonObject jsonBody, HttpServletResponse resp) {
        CardRepository cardRepository = new CardRepository(conn);

        Long cardId = jsonBody.get("cardId").getAsLong();
        String clientComment = jsonBody.get("clientComment").getAsString();

        Boolean result = cardRepository.informCardLost(this.currentClient, cardId, clientComment);

        resp.setHeader("Content-Type", "application/json");
        try {
            PrintWriter out = resp.getWriter();
            out.print(this.getGson().toJson(result, Boolean.class));
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
