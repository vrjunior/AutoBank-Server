package us.guihouse.autobank.repositories;

import us.guihouse.autobank.models.client.Client;
import us.guihouse.autobank.models.client.ClosedBill;
import us.guihouse.autobank.models.client.OpenBill;

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
    Client currentClient;

    public BillRepository(Connection conn, Client currentClient) {
        this.conn = conn;
        this.currentClient = currentClient;
    }

    public ArrayList<ClosedBill> getClosedBills() {
        ArrayList<ClosedBill> closedBills = new ArrayList<ClosedBill>();
        StringBuilder sqlSelectClosedBills = new StringBuilder();
        sqlSelectClosedBills.append("SELECT CLOSED_BILLS.ID, MONTH, YEAR, PAYMENT_DEADLINE, TOTAL_VALUE, MIN_VALUE, ")
                .append("(COALESCE(SUM(PAYMENTS.PAYMENT_VALUE), 0)) AS PAID_VALUE ")
                .append("FROM CLOSED_BILLS, BILLS ")

                .append("LEFT JOIN PAYMENT_OF ")
                .append("ON PAYMENT_OF.BILL_ID = BILLS.ID ")

                .append("LEFT JOIN PAYMENTS ")
                .append("ON PAYMENT_OF.PAYMENT_ID = PAYMENTS.ID ")


                .append("WHERE CLOSED_BILLS.ID = BILLS.ID ")
                .append("AND CLIENT_ID = ? ")
                .append("GROUP BY CLOSED_BILLS.ID, MONTH, YEAR, PAYMENT_DEADLINE, TOTAL_VALUE, MIN_VALUE ")
                .append("ORDER BY YEAR, MONTH DESC ");


        try {
            PreparedStatement preparedStatement = this.conn.prepareStatement(sqlSelectClosedBills.toString());
            preparedStatement.setLong(1, this.currentClient.getId());
            ResultSet rs = preparedStatement.executeQuery();
            ClosedBill currentClosedBill;
            while(rs.next()) {
                currentClosedBill = new ClosedBill();
                currentClosedBill.setId(rs.getLong("ID"));
                currentClosedBill.setMonth(rs.getInt("MONTH"));
                currentClosedBill.setYear(rs.getInt("YEAR"));
                currentClosedBill.setPaymentDeadline(rs.getDate("PAYMENT_DEADLINE"));
                currentClosedBill.setTotalValue(rs.getBigDecimal("TOTAL_VALUE"));
                currentClosedBill.setMinValue(rs.getBigDecimal("MIN_VALUE"));
                currentClosedBill.setPaidValue(rs.getBigDecimal("PAID_VALUE"));

                closedBills.add(currentClosedBill);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return closedBills;
    }

    public ArrayList<OpenBill> getOpenBills() {
        ArrayList<OpenBill> openBills = new ArrayList<OpenBill>();
        StringBuilder sqlSelectOpenBills = new StringBuilder();
        sqlSelectOpenBills.append("SELECT BILLS.ID, BILLS.MONTH, BILLS.YEAR, BILLS.PAYMENT_DEADLINE, ( ")
                .append("(COALESCE (SUM(INSTALLMENTS.INSTALLMENT_VALUE), 0))+ ")
                .append("(COALESCE (SUM(INTEREST_RATES.INTEREST_RATE_VALUE), 0))- ")
                .append("(COALESCE (SUM(REVERSALS.REVERSAL_VALUE), 0)) ) AS PARTIAL_VALUE ")
                .append("FROM BILLS ")
                .append("LEFT JOIN INSTALLMENTS ")
                .append("ON BILLS.ID = INSTALLMENTS.BILL_ID ")
                .append("LEFT JOIN INTEREST_RATES ")
                .append("ON BILLS.ID = INTEREST_RATES.BILL_ID ")
                .append("LEFT JOIN REVERSALS ")
                .append("ON BILLS.ID = REVERSALS.BILL_ID ")
                .append("WHERE BILLS.CLIENT_ID = ? ")
                .append("AND BILLS.ID NOT IN ( ")
                .append("SELECT CLOSED_BILLS.ID FROM CLOSED_BILLS ) ")
                .append("GROUP BY BILLS.ID, BILLS.MONTH, BILLS.YEAR, BILLS.PAYMENT_DEADLINE ")
                .append("ORDER BY BILLS.YEAR, BILLS.MONTH DESC ");
        try {
            PreparedStatement preparedStatement = this.conn.prepareStatement(sqlSelectOpenBills.toString());
            preparedStatement.setLong(1, this.currentClient.getId());
            ResultSet rs = preparedStatement.executeQuery();
            OpenBill currentBill;
            while(rs.next()) {
                currentBill = new OpenBill();
                currentBill.setId(rs.getLong("ID"));
                currentBill.setMonth(rs.getInt("MONTH"));
                currentBill.setYear(rs.getInt("YEAR"));
                currentBill.setPaymentDeadline(rs.getDate("PAYMENT_DEADLINE"));
                currentBill.setPartialValue(rs.getBigDecimal("PARTIAL_VALUE"));

                openBills.add(currentBill);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return openBills;
    }



}
