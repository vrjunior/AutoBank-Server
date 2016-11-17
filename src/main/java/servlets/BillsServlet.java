package servlets;

import com.google.gson.JsonObject;
import models.ClosedBill;
import models.OpenBill;
import models.auxiliar.Bills;
import repositories.BillRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;

/**
 * Created by valmir.massoni on 18/10/2016.
 */
@WebServlet(urlPatterns = "/BillsServlet")
public class BillsServlet extends TokenAuthentication{

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }
    protected void process(Connection conn, JsonObject jsonBody, HttpServletResponse resp) {
        resp.setHeader("Content-Type", "application/json");

        BillRepository billRepository = new BillRepository(conn, currentClient);

        Bills bills = new Bills();

        bills.setClosedBills(billRepository.getClosedBills());
        bills.setOpenBills(billRepository.getOpenBills());

        try {
            PrintWriter out = resp.getWriter();
            out.println(getGson().toJson(bills, Bills.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
