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
    Client currentClient;

    public BillRepository(Connection conn, Client currentClient) {
        this.conn = conn;
        this.currentClient = currentClient;
    }

    public ArrayList<ClosedBill> getClosedBills() {
        ArrayList<ClosedBill> closedBills = new ArrayList<ClosedBill>();
        StringBuilder sqlSelectClosedBills = new StringBuilder();
        sqlSelectClosedBills.append("SELECT CLOSED_BILLS.ID, MONTH, YEAR, PAYMENT_DEADLINE, TOTAL_VALUE, MIN_VALUE ")
                .append("FROM CLOSED_BILLS, BILLS ")
                .append("WHERE CLOSED_BILLS.ID = BILLS.ID ")
                .append("AND CLIENT_ID = ?");


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
        sqlSelectOpenBills.append("SELECT ID, MONTH, YEAR, PAYMENT_DEADLINE, ")
                .append("SUM(INSTALLMENT_VALUE) + SUM(INTEREST_RATE_VALUE) - SUM(REVERSAL_VALUE)  AS PARTIAL_VALUE ")
                .append("FROM BILLS, INSTALLMENTS, INTEREST_RATES, REVERSALS ")
                .append("WHERE CLIENT_ID = ? ")
                .append("AND BILLS.ID = INSTALLMENTS.BILL_ID ")
                .append("AND BILLS.ID = INTEREST_RATES.BILL_ID ")
                .append("AND BILLS.ID = RERVERSALS.BILL_ID ")


                /*
                SELECT BILLS.ID, BILLS.MONTH, BILLS.YEAR, BILLS.PAYMENT_DEADLINE,
                (
                    (COALESCE (SUM(INSTALLMENTS.INSTALLMENT_VALUE), 0))+
                    (COALESCE (SUM(INTEREST_RATES.INTEREST_RATE_VALUE), 0))-
                    (COALESCE (SUM(REVERSALS.REVERSAL_VALUE), 0))
                ) AS PARTIAL_VALUE
                FROM BILLS
                LEFT JOIN INSTALLMENTS
                  ON BILLS.ID = INSTALLMENTS.BILL_ID
                LEFT JOIN INTEREST_RATES
                  ON BILLS.ID = INTEREST_RATES.BILL_ID
                LEFT JOIN REVERSALS
                  ON BILLS.ID = REVERSALS.BILL_ID
                WHERE BILLS.CLIENT_ID = 21
                GROUP BY BILLS.ID, BILLS.MONTH, BILLS.YEAR, BILLS.PAYMENT_DEADLINE;
                 */

                .append("MINUS ")
                .append("SELECT CLOSED_BILLS.ID, MONTH, YEAR, PAYMENT_DEADLINE ")
                .append("FROM CLOSED_BILLS, BILLS ")
                .append("WHERE CLOSED_BILLS.ID = BILLS.ID ")
                .append("AND CLIENT_ID = ?");

        try {
            PreparedStatement preparedStatement = this.conn.prepareStatement(sqlSelectOpenBills.toString());
            preparedStatement.setLong(1, this.currentClient.getId());
            preparedStatement.setLong(2, this.currentClient.getId());
            ResultSet rs = preparedStatement.executeQuery();
            Bill currentBill;
            while(rs.next()) {
                currentBill = new ClosedBill();
                currentBill.setId(rs.getLong("ID"));
                currentBill.setMonth(rs.getInt("MONTH"));
                currentBill.setYear(rs.getInt("YEAR"));
                currentBill.setPaymentDeadline(rs.getDate("PAYMENT_DEADLINE"));

                openBills.add(currentBill);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return openBills;
    }

}
