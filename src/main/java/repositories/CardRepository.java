package repositories;

import models.Card;
import models.Client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by valmir.massoni on 19/10/2016.
 */
public class CardRepository {
    private Connection conn;

    public CardRepository(Connection conn) {
        this.conn = conn;
    }

    public ArrayList<Card> getCardByClientId(Long clientId) {
        ArrayList<Card> cards = new ArrayList<Card>();
        Card currentCard;
        StringBuilder sqlSelectCard = new StringBuilder();
        sqlSelectCard.append("SELECT ID, NUM, CVV, EMISSION, VALID_THROW, LIMIT_VALUE, INTEREST_RATE, CLOSING_DAY, ACTIVE ")
                .append("FROM CARDS ")
                .append("WHERE CLIENT_ID = ? ");

        try {
            PreparedStatement preparedStatement = this.conn.prepareStatement(sqlSelectCard.toString());
            preparedStatement.setLong(1, clientId);
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()) {
                currentCard = new Card();
                currentCard.setId(rs.getLong("ID"));
                currentCard.setCardNumber(rs.getString("NUM"));
                currentCard.setCvv(rs.getString("CVV"));
                currentCard.setEmission(rs.getDate("EMISSION"));
                currentCard.setValidThrow(rs.getDate("VALID_THROW"));
                currentCard.setLimit(rs.getBigDecimal("LIMIT_VALUE"));
                currentCard.setInterestRate(rs.getBigDecimal("INREREST_RATE"));
                currentCard.setClosingDay(rs.getInt("CLOSING_DAY"));
                currentCard.setActive(rs.getBoolean("ACTIVE"));
                cards.add(currentCard);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cards;
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
