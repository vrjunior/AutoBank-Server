package repositories;

import models.Client;
import models.Purchase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by valmir.massoni on 16/11/2016.
 */
public class FinantialStatementRepository {
    private Connection conn;

    public FinantialStatementRepository(Connection conn) {
        this.conn = conn;
    }

    public ArrayList<Purchase> getPurchases(Client client, Long idBill) {
        //TODO: CHECK IF THIS BILL BELONGS TO CLIENT BEFORE RUN THIS QUERY
        ArrayList<Purchase> purchases = new ArrayList<Purchase>();
        StringBuilder sqlSelect = new StringBuilder();
        sqlSelect.append("SELECT PURCHASES.ID, PURCHASES.PURCHASE_VALUE, ESTABLISHMENTS.NAME AS ESTABLISHMENT_NAME, ")
                .append("CATEGORIES.NAME AS CATEGORY, INSTALLMENTS.INSTALLMENT_VALUE INSTALLMENTS.SEQUENTIAL, ")
                .append("COUNT(ALL_INSTALLMENTS.ID) AS AMOUNT_INSTALLMENTS ")
                .append("FROM INSTALLMENTS ")
                .append("INNER JOIN PURCHASES ")
                .append("ON INSTALLMENTS.PURCHASE_ID = PURCHASES.ID ")
                .append("INNER JOIN INSTALLMENTS ALL_INSTALLMENTS ")
                .append("ON ALL_INSTALLMENTS.PURCHASE_ID = PURCHASES.ID ")
                .append("INNER JOIN ESTABLISHMENTS ")
                .append("ON ESTABLISHMENTS.ID = PURCHASES.ESTABLISHMENT_ID ")
                .append("INNER JOIN CATEGORIES ")
                .append("ON CATEGORIES.ID = ESTABLISHMENTS.CATEGORY_ID ")
                .append("WHERE INSTALLMENTS.BILL_ID = ? ")
                .append("GROUP BY INSTALLMENTS.ID, INSTALLMENTS.INSTALLMENT_VALUE, PURCHASES.ID, PURCHASES.PURCHASE_VALUE, ")
                .append("ESTABLISHMENTS.NAME, CATEGORIES.NAME, INSTALLMENTS.SEQUENTIAL ");

        try {
            PreparedStatement ps = this.conn.prepareStatement(sqlSelect.toString());
            ps.setLong(1, idBill);
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

                purchases.add(currentPurchase);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return purchases;
    }

}
