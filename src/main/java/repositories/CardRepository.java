package repositories;

import models.Card;

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
}
