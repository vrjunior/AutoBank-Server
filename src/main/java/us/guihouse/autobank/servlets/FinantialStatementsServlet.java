package us.guihouse.autobank.servlets;

import com.google.gson.JsonObject;
import us.guihouse.autobank.models.auxiliar.FinantialStatements;
import us.guihouse.autobank.repositories.FinantialStatementRepository;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

/**
 * Created by valmir.massoni on 16/11/2016.
 */
@WebServlet(urlPatterns = "/FinantialStatements")
public class FinantialStatementsServlet extends TokenAuthentication {
    protected void process(Connection conn, JsonObject jsonBody, HttpServletResponse resp) {
        Long billId = jsonBody.get("billId").getAsLong();

        FinantialStatementRepository fsRepository = new FinantialStatementRepository(conn, this.currentClient, billId);
        FinantialStatements finantialStatements = new FinantialStatements();

        finantialStatements.setPurchases(fsRepository.getPurchases());
        finantialStatements.setInterestRates(fsRepository.getInterestRates());
        finantialStatements.setPayments(fsRepository.getPayments());
        finantialStatements.setReversals(fsRepository.getReversals());

        resp.setHeader("Content-Type", "application/json");

        try {
            PrintWriter out = resp.getWriter();
            out.print(getGson().toJson(finantialStatements, FinantialStatements.class));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
