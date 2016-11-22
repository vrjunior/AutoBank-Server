package us.guihouse.autobank.repositories;

import us.guihouse.autobank.models.Card;
import us.guihouse.autobank.models.Client;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by valmir.massoni on 19/10/2016.
 */
public class CardRepository {
    private static final String USED_VALUE_QUERY = "SELECT ((" +
                "COALESCE(SUM(IR.INTEREST_RATE_VALUE), 0) + " +
                "COALESCE(SUM(INSTALLMENTS.INSTALLMENT_VALUE), 0)" +
            ") - (" +
                "COALESCE(SUM(PAYMENTS.PAYMENT_VALUE), 0) + " +
                "COALESCE(SUM(REVERSALS.REVERSAL_VALUE), 0)" +
            ")) FROM BILLS " +
            "LEFT JOIN PAYMENTS ON BILLS.ID = PAYMENTS.BILL_ID " +
            "LEFT JOIN REVERSALS ON BILLS.ID = REVERSALS.BILL_ID " +
            "LEFT JOIN INTEREST_RATES IR ON BILLS.ID = IR.BILL_ID " +
            "LEFT JOIN INSTALLMENTS ON BILLS.ID = INSTALLMENTS.BILL_ID " +
            "WHERE BILLS.CLIENT_ID = CARDS.CLIENT_ID";

    private Connection conn;

    public CardRepository(Connection conn) {
        this.conn = conn;
    }

    public Card getActiveCardByClientId(Long clientId) throws SQLException {
        Card currentCard = new Card();
        StringBuilder sqlSelectCard = new StringBuilder();
        sqlSelectCard.append("SELECT ID, NUM, CVV, EMISSION, VALID_THROW, LIMIT_VALUE, INTEREST_RATE, CLOSING_DAY, ACTIVE, ")
                .append("(").append(USED_VALUE_QUERY).append(") AS USED_VALUE ")
                .append("FROM CARDS ")
                .append("WHERE CARDS.CLIENT_ID = ? AND CARDS.ACTIVE = ?");


        PreparedStatement preparedStatement = this.conn.prepareStatement(sqlSelectCard.toString());
        preparedStatement.setLong(1, clientId);
        preparedStatement.setBoolean(2, true);
        ResultSet rs = preparedStatement.executeQuery();

        if(rs.next()) {
            BigDecimal usedValue = rs.getBigDecimal("USED_VALUE");
            BigDecimal limit = rs.getBigDecimal("LIMIT_VALUE");

            currentCard.setId(rs.getLong("ID"));
            currentCard.setCardNumber(rs.getString("NUM"));
            currentCard.setCvv(rs.getString("CVV"));
            currentCard.setEmission(rs.getDate("EMISSION"));
            currentCard.setValidThrow(rs.getDate("VALID_THROW"));
            currentCard.setLimit(limit);
            currentCard.setInterestRate(rs.getBigDecimal("INTEREST_RATE"));
            currentCard.setClosingDay(rs.getInt("CLOSING_DAY"));
            currentCard.setActive(rs.getBoolean("ACTIVE"));

            if (usedValue.compareTo(BigDecimal.ZERO) > 0) {
                currentCard.setUsedValue(usedValue);
            } else {
                // Payments + Reversals > Installments + Interests
                // So, the client is creditor
                currentCard.setUsedValue(BigDecimal.ZERO);
            }

            currentCard.setAvailableValue(limit.subtract(usedValue));
        }

        return currentCard;
    }

    public boolean informCardLost(Client client, Long cardId, String clientComment) {
        StringBuilder sqlInsertLost = new StringBuilder();
        StringBuilder sqlDesactiveCard = new StringBuilder();
        boolean result;

        sqlInsertLost.append("INSERT INTO CARDS_FLAGGED_LOST_STOLEN(CARD_ID, CLIENT_COMMENT) ")
                .append("VALUES(( SELECT ID FROM CARDS ")
                .append("WHERE CLIENT_ID = ? ")
                .append("AND ID = ? ), ? ) ");

        sqlDesactiveCard.append("UPDATE CARDS ")
                .append("SET ACTIVE = 0 ")
                .append("WHERE ID = ? ")
                .append("AND CLIENT_ID = ? ");

        try {
            this.conn.setAutoCommit(false); //Begin transaction

            PreparedStatement ps = conn.prepareStatement(sqlInsertLost.toString());
            ps.setLong(1, client.getId());
            ps.setLong(2, cardId);
            ps.setString(3, clientComment);
            ps.executeUpdate();

            ps = conn.prepareStatement(sqlDesactiveCard.toString());
            ps.setLong(1, cardId);
            ps.setLong(2, client.getId());
            ps.executeUpdate();

            this.conn.commit(); //commit if the runs is okay
            result = true;
        }catch(Exception e) {
            try {
                this.conn.rollback(); //if something went wrong, a rollback is executed
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            result = false;
        }
        finally {
            try {
                this.conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return result;
    }
}
