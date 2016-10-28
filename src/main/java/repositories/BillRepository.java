package repositories;

import models.Bill;
import models.Client;
import models.ClosedBill;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by valmir.massoni on 19/10/2016.
 */
public class BillRepository {
    private Connection conn;

    public BillRepository(Connection conn) {
        this.conn = conn;
    }

    public ArrayList<ClosedBill> getClosedBills() {
        ArrayList<ClosedBill> closedBills = new ArrayList<ClosedBill>();
        StringBuilder sqlSelectClosedBillls = new StringBuilder();
        sqlSelectClosedBillls.append("SELECT ID, MONTH, YEAR, PAYMENT_DEADLINE, TOTAL_VALUE, MIN_VALUE ");
            //TODO TERMINAR


        try {
            PreparedStatement preparedStatement = this.conn.prepareStatement(sqlSelectClosedBillls.toString());
           // preparedStatement.setLong(1, this.currentClient.getId());
            ResultSet rs = preparedStatement.executeQuery();
            ClosedBill currentClosedBill;
            while(rs.next()) {
                currentClosedBill = new ClosedBill();
                currentClosedBill.setId(rs.getLong("ID"));
                currentClosedBill.setMonth(rs.getInt("MONTH"));
                currentClosedBill.setYear(rs.getInt("YEAR"));
                currentClosedBill.setPaymentDeadline(rs.getDate("PAYMENT_DEADLINE"));
                currentClosedBill.setTotalValue(rs.getBigDecimal("TOTAL_VALYE"));
                currentClosedBill.setMinValue(rs.getBigDecimal("MIN_VALUE"));

                closedBills.add(currentClosedBill);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return closedBills;
    }

    public ArrayList<Bill> getOpenBills() {
        ArrayList<Bill> openBills = new ArrayList<Bill>();
        StringBuilder sqlSelectOpenBills = new StringBuilder();
        sqlSelectOpenBills.append("SELECT ID, MONTH, YEAR, PAYMENT_DEADLINE ");
        //TODO TERMINAR

        return openBills;
    }

}
