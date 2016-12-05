package us.guihouse.autobank.repositories;

import us.guihouse.autobank.models.CardLostOrStolen;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by guilherme on 05/12/16.
 */
public class CardLostOrStolenRepository {
    private Connection conn;

    public CardLostOrStolenRepository(Connection conn) {
        this.conn = conn;
    }

    public ArrayList<CardLostOrStolen> getReasons(Long page) throws SQLException {
        CardLostOrStolen currentReason;

        StringBuilder sqlSelectCard = new StringBuilder();
        sqlSelectCard.append("SELECT REASONS.CARD_ID, CARDS.NUM, CARDS.EMISSION, CARDS.VALID_THROW, REASONS.CLIENT_COMMENT, ")
                .append("CLIENTS.NAME, CLIENTS.CPF, REASONS.CREATED_AT ")
                .append("FROM CARDS_FLAGGED_LOST_STOLEN REASONS ")
                .append("INNER JOIN CARDS ON REASONS.CARD_ID = CARDS.ID ")
                .append("INNER JOIN CLIENTS ON CARDS.CLIENT_ID = CLIENTS.ID ")
                .append("ORDER BY REASONS.CREATED_AT ASC ")
                .append("OFFSET ? ROWS FETCH NEXT ? ROWS ONLY");

        PreparedStatement preparedStatement = this.conn.prepareStatement(sqlSelectCard.toString());
        preparedStatement.setLong(1, page);
        preparedStatement.setLong(2, 15);
        ResultSet rs = preparedStatement.executeQuery();

        ArrayList<CardLostOrStolen> list = new ArrayList<>();

        while (rs.next()) {
            currentReason = new CardLostOrStolen();
            currentReason.setId(rs.getLong(1));
            currentReason.setCardNumber(rs.getString(2));
            currentReason.setCardEmission(rs.getDate(3));
            currentReason.setCardExpiration(rs.getDate(4));
            currentReason.setComment(rs.getString(5));
            currentReason.setClientName(rs.getString(6));
            currentReason.setClientCpf(rs.getString(7));
            currentReason.setCreatedAt(rs.getTimestamp(8));

            list.add(currentReason);
        }

        return list;
    }

    public Long getTotalReasons() throws SQLException {
        StringBuilder sqlSelectCard = new StringBuilder();
        sqlSelectCard.append("SELECT COUNT(*) ")
                .append("FROM CARDS_FLAGGED_LOST_STOLEN REASONS ")
                .append("INNER JOIN CARDS ON REASONS.CARD_ID = CARDS.ID ")
                .append("INNER JOIN CLIENTS ON CARDS.CLIENT_ID = CLIENTS.ID ")
                .append("WHERE CARDS.ACTIVE = ?");


        PreparedStatement preparedStatement = this.conn.prepareStatement(sqlSelectCard.toString());
        preparedStatement.setBoolean(1, true);
        ResultSet rs = preparedStatement.executeQuery();
        Long count = 0L;

        if (rs.next()) {
            count = rs.getLong(1);
        }

        return count;
    }
}
