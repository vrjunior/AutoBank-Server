package us.guihouse.autobank.repositories;

import us.guihouse.autobank.models.client.Card;
import us.guihouse.autobank.models.client.Client;

import java.math.BigDecimal;
import java.sql.*;
import java.time.Instant;

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
        Card currentCard = null;

        StringBuilder sqlSelectCard = new StringBuilder();
        sqlSelectCard.append("SELECT ID, CLIENT_ID, NUM, CVV, EMISSION, VALID_THROW, LIMIT_VALUE, INTEREST_RATE, CLOSING_DAY, ACTIVE, ")
                .append("(").append(USED_VALUE_QUERY).append(") AS USED_VALUE ")
                .append("FROM CARDS ")
                .append("WHERE CARDS.CLIENT_ID = ? AND CARDS.ACTIVE = ?");


        PreparedStatement preparedStatement = this.conn.prepareStatement(sqlSelectCard.toString());
        preparedStatement.setLong(1, clientId);
        preparedStatement.setBoolean(2, true);
        ResultSet rs = preparedStatement.executeQuery();

        if(rs.next()) {
            currentCard = Card.fromResultSet(rs);
        }

        return currentCard;
    }

    public void saveNewCard(Long clientId, Card newCard) throws SQLException {
        StringBuilder sql = new StringBuilder("INSERT INTO CARDS (");
        sql.append("CLIENT_ID, ")
                .append("NUM, ")
                .append("CVV, ")
                .append("EMISSION, ")
                .append("VALID_THROW, ")
                .append("LIMIT_VALUE, ")
                .append("INTEREST_RATE, ")
                .append("CLOSING_DAY, ")
                .append("ACTIVE) ");
        sql.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");

        PreparedStatement statement = this.conn.prepareStatement(sql.toString());
        statement.setLong(1, clientId);
        statement.setString(2, newCard.getCardNumber());
        statement.setString(3, newCard.getCvv());
        statement.setDate(4, newCard.getEmission());
        statement.setDate(5, newCard.getValidThrow());
        statement.setBigDecimal(6, newCard.getLimit());
        statement.setBigDecimal(7, newCard.getInterestRate());
        statement.setInt(8, newCard.getClosingDay());
        statement.setBoolean(9, true);

        statement.executeUpdate();
    }

    public boolean informCardLost(Client client, Long cardId, String clientComment) {
        StringBuilder sqlInsertLost = new StringBuilder();
        StringBuilder sqlDeactivateCard = new StringBuilder();
        boolean result;

        sqlInsertLost.append("INSERT INTO CARDS_FLAGGED_LOST_STOLEN(CARD_ID, CLIENT_COMMENT, CREATED_AT) ")
                .append("VALUES(( SELECT ID FROM CARDS ")
                .append("WHERE CLIENT_ID = ? ")
                .append("AND ID = ? ), ?, ? ) ");

        sqlDeactivateCard.append("UPDATE CARDS ")
                .append("SET ACTIVE = 0 ")
                .append("WHERE ID = ? ")
                .append("AND CLIENT_ID = ? ");

        try {
            this.conn.setAutoCommit(false); //Begin transaction

            PreparedStatement ps = conn.prepareStatement(sqlInsertLost.toString());
            ps.setLong(1, client.getId());
            ps.setLong(2, cardId);
            ps.setString(3, clientComment);
            ps.setTimestamp(4, Timestamp.from(Instant.now()));
            result = ps.executeUpdate() > 0;

            ps = conn.prepareStatement(sqlDeactivateCard.toString());
            ps.setLong(1, cardId);
            ps.setLong(2, client.getId());
            result &= ps.executeUpdate() > 0;

            if (result) {
                this.conn.commit(); //commit if the runs is okay
            } else {
                this.conn.rollback();
            }
        } catch(Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();

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

    public Card getCardById(Long cardId) throws SQLException {
        Card currentCard = null;

        StringBuilder sqlSelectCard = new StringBuilder();
        sqlSelectCard.append("SELECT ID, CLIENT_ID, NUM, CVV, EMISSION, VALID_THROW, LIMIT_VALUE, INTEREST_RATE, CLOSING_DAY, ACTIVE, ")
                .append("(").append(USED_VALUE_QUERY).append(") AS USED_VALUE ")
                .append("FROM CARDS ")
                .append("WHERE CARDS.ID = ?");


        PreparedStatement preparedStatement = this.conn.prepareStatement(sqlSelectCard.toString());
        preparedStatement.setLong(1, cardId);
        ResultSet rs = preparedStatement.executeQuery();

        if(rs.next()) {
            currentCard = Card.fromResultSet(rs);
        }

        return currentCard;
    }

    public void cancelCardById(Long cardId) throws SQLException {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE CARDS SET CANCELLED = ? WHERE ID = ?");
        PreparedStatement preparedStatement = this.conn.prepareStatement(sql.toString());
        preparedStatement.setBoolean(1, true);
        preparedStatement.setLong(2, cardId);
        preparedStatement.executeUpdate();
    }
}
