package us.guihouse.autobank.repositories;

import us.guihouse.autobank.models.client.*;

import java.sql.*;
import java.util.ArrayList;
/**
 * Created by valmir.massoni on 16/11/2016.
 */
public class FinantialStatementRepository {
    private Connection conn;
    private Client client;
    private Long billId;

    public FinantialStatementRepository(Connection conn, Client client, Long billId) {
        this.conn = conn;
        this.client = client;
        this.billId = billId;
    }

    public ArrayList<Purchase> getPurchases() {
        ArrayList<Purchase> purchases = new ArrayList<Purchase>();
        StringBuilder sqlSelect = new StringBuilder();
        sqlSelect.append("SELECT PURCHASES.ID, PURCHASES.PURCHASE_VALUE, ESTABLISHMENTS.NAME AS ESTABLISHMENT_NAME, ")
                .append("FINANTIAL_STATEMENTS.PROCESS_DATE AS PROCESS_DATE, ")
                .append("CATEGORIES.NAME AS CATEGORY, INSTALLMENTS.INSTALLMENT_VALUE, INSTALLMENTS.SEQUENTIAL, ")
                .append("COUNT(ALL_INSTALLMENTS.ID) AS AMOUNT_INSTALLMENTS ")
                .append("FROM INSTALLMENTS ")
                .append("INNER JOIN PURCHASES ")
                .append("ON INSTALLMENTS.PURCHASE_ID = PURCHASES.ID ")
                .append("INNER JOIN FINANTIAL_STATEMENTS ")
                .append("ON FINANTIAL_STATEMENTS.ID = PURCHASES.ID ")
                .append("INNER JOIN INSTALLMENTS ALL_INSTALLMENTS ")
                .append("ON ALL_INSTALLMENTS.PURCHASE_ID = PURCHASES.ID ")
                .append("INNER JOIN ESTABLISHMENTS ")
                .append("ON ESTABLISHMENTS.ID = PURCHASES.ESTABLISHMENT_ID ")
                .append("INNER JOIN CATEGORIES ")
                .append("ON CATEGORIES.ID = ESTABLISHMENTS.CATEGORY_ID ")
                .append("INNER JOIN BILLS ")
                .append("ON BILLS.ID = INSTALLMENTS.BILL_ID ")
                .append("WHERE INSTALLMENTS.BILL_ID = ? ")
                .append("AND BILLS.CLIENT_ID = ? ")
                .append("GROUP BY PURCHASES.ID, PURCHASES.PURCHASE_VALUE, ESTABLISHMENTS.NAME, PROCESS_DATE, ")
                .append("INSTALLMENTS.INSTALLMENT_VALUE, CATEGORIES.NAME, INSTALLMENTS.SEQUENTIAL ");

        try {
            PreparedStatement ps = this.conn.prepareStatement(sqlSelect.toString());
            ps.setLong(1, this.billId);
            ps.setLong(2, this.client.getId());
            ResultSet rs = ps.executeQuery();

            Purchase currentPurchase;
            while(rs.next()) {
                currentPurchase = new Purchase();
                currentPurchase.setId(rs.getLong("ID"));
                currentPurchase.setPurchaseValue(rs.getBigDecimal("PURCHASE_VALUE"));
                currentPurchase.setEstablishmentName(rs.getString("ESTABLISHMENT_NAME"));
                currentPurchase.setCategoryName(rs.getString("CATEGORY"));
                currentPurchase.setInstallmentValue(rs.getBigDecimal("INSTALLMENT_VALUE"));
                currentPurchase.setInstallmentSequential(rs.getInt("SEQUENTIAL"));
                currentPurchase.setInstallmentsAmount(rs.getInt("AMOUNT_INSTALLMENTS"));
                currentPurchase.setProcessDate(rs.getDate("PROCESS_DATE"));

                purchases.add(currentPurchase);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return purchases;
    }

    public ArrayList<Payment> getPayments() {
        ArrayList<Payment> payments = new ArrayList<Payment>();
        StringBuilder sqlPayments = new StringBuilder();
        sqlPayments.append("SELECT PAYMENTS.ID, PAYMENTS.PAYMENT_VALUE, FINANTIAL_STATEMENTS.PROCESS_DATE ")
                .append("FROM PAYMENTS ")
                .append("INNER JOIN FINANTIAL_STATEMENTS ")
                .append("ON FINANTIAL_STATEMENTS.ID = PAYMENTS.ID ")
                .append("INNER JOIN BILLS ")
                .append("ON BILLS.ID = PAYMENTS.BILL_ID ")
                .append("WHERE BILL_ID = ? ")
                .append("AND BILLS.CLIENT_ID = ? ")
                .append("ORDER BY FINANTIAL_STATEMENTS.PROCESS_DATE DESC ");

        try {
            PreparedStatement ps = this.conn.prepareStatement(sqlPayments.toString());
            ps.setLong(1, this.billId);
            ps.setLong(2, this.client.getId());
            ResultSet rs = ps.executeQuery();
            Payment currentPayment;
            while(rs.next()) {
                currentPayment = new Payment();
                currentPayment.setId(rs.getLong("ID"));
                currentPayment.setPaymentValue(rs.getBigDecimal("PAYMENT_VALUE"));
                currentPayment.setProcessDate(rs.getDate("PROCESS_DATE"));

                payments.add(currentPayment);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }

        return payments;
    }

    public ArrayList<Reversal> getReversals() {
        ArrayList<Reversal> reversals = new ArrayList<Reversal>();
        StringBuilder sqlReversals = new StringBuilder();
        sqlReversals.append("SELECT REVERSALS.ID, REVERSALS.REVERSAL_VALUE, FINANTIAL_STATEMENTS.PROCESS_DATE ")
                .append("FROM REVERSALS ")
                .append("INNER JOIN FINANTIAL_STATEMENTS ")
                .append("ON FINANTIAL_STATEMENTS.ID = REVERSALS.ID ")
                .append("INNER JOIN BILLS ")
                .append("ON BILLS.ID = REVERSALS.BILL_ID ")
                .append("WHERE BILL_ID = ? ")
                .append("AND BILLS.CLIENT_ID = ? ")
                .append("ORDER BY FINANTIAL_STATEMENTS.PROCESS_DATE DESC ");

        try {
            PreparedStatement ps = this.conn.prepareStatement(sqlReversals.toString());
            ps.setLong(1, this.billId);
            ps.setLong(2, this.client.getId());
            ResultSet rs = ps.executeQuery();
            Reversal currentReversal;
            while(rs.next()) {
                currentReversal = new Reversal();
                currentReversal.setId(rs.getLong("ID"));
                currentReversal.setReversalValue(rs.getBigDecimal("REVERSAL_VALUE"));
                currentReversal.setProcessDate(rs.getDate("PROCESS_DATE"));

                reversals.add(currentReversal);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }

        return reversals;
    }

    public ArrayList<InterestRate> getInterestRates() {
        ArrayList<InterestRate> interestRates = new ArrayList<InterestRate>();
        StringBuilder sqlInterestRates = new StringBuilder();
        sqlInterestRates.append("SELECT INTEREST_RATES.ID, INTEREST_RATES.INTEREST_RATE_VALUE, FINANTIAL_STATEMENTS.PROCESS_DATE ")
                .append("FROM INTEREST_RATES ")
                .append("INNER JOIN FINANTIAL_STATEMENTS ")
                .append("ON FINANTIAL_STATEMENTS.ID = INTEREST_RATES.ID ")
                .append("INNER JOIN BILLS ")
                .append("ON BILLS.ID = INTEREST_RATES.BILL_ID ")
                .append("WHERE BILL_ID = ? ")
                .append("AND BILLS.CLIENT_ID = ? ")
                .append("ORDER BY FINANTIAL_STATEMENTS.PROCESS_DATE DESC ");

        try {
            PreparedStatement ps = this.conn.prepareStatement(sqlInterestRates.toString());
            ps.setLong(1, this.billId);
            ps.setLong(2, this.client.getId());
            ResultSet rs = ps.executeQuery();
            InterestRate currentInterestRate;
            while(rs.next()) {
                currentInterestRate = new InterestRate();
                currentInterestRate.setId(rs.getLong("ID"));
                currentInterestRate.setInterestRateValue(rs.getBigDecimal("INTEREST_RATE_VALUE"));
                currentInterestRate.setProcessDate(rs.getDate("PROCESS_DATE"));

                interestRates.add(currentInterestRate);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }

        return interestRates;
    }

}