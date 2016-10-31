package servlets;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import models.Bill;
import models.ClosedBill;
import repositories.BillRepository;
import repositories.CardRepository;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;

/**
 * Created by valmir.massoni on 18/10/2016.
 */
public class BillsServlet extends TokenAuthentication{

    protected void process(Connection conn, JsonObject jsonBody, HttpServletResponse resp) {
        resp.setHeader("Content-Type", "application/json");

        CardRepository cardRepository = new CardRepository(conn);
        BillRepository billRepository = new BillRepository(conn, currentClient);

        //ArrayList<Card> cards = cardRepository.getCardByClientId(this.currentClient.getId());
        String json;
        Gson gson = new Gson();

        ArrayList<ClosedBill> closedBills = new ArrayList<ClosedBill>();
        ArrayList<Bill> bills = new ArrayList<Bill>();

        //getting bills
        closedBills.addAll(billRepository.getClosedBills());
        bills.addAll(billRepository.getOpenBills());

        //parsing to json
        json = gson.toJson(closedBills, ClosedBill.class);
        json += gson.toJson(bills, Bill.class);

        try {
            PrintWriter out = resp.getWriter();
            out.println(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
