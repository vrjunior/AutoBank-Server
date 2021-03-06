package us.guihouse.autobank.repositories;

import us.guihouse.autobank.models.collaborator.CardLostOrStolen;
import us.guihouse.autobank.other.Pager;

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

    public void getReasons(Pager<CardLostOrStolen> pager) throws SQLException {
        CardLostOrStolen currentReason;

        StringBuilder sqlSelectCard = new StringBuilder();
        sqlSelectCard.append("SELECT REASONS.CARD_ID, CARDS.NUM, CARDS.EMISSION, CARDS.VALID_THROW, REASONS.CLIENT_COMMENT, ")
                .append("CLIENTS.NAME, CLIENTS.CPF, REASONS.CREATED_AT ")
                .append("FROM CARDS_FLAGGED_LOST_STOLEN REASONS ")
                .append("INNER JOIN CARDS ON REASONS.CARD_ID = CARDS.ID ")
                .append("INNER JOIN CLIENTS ON CARDS.CLIENT_ID = CLIENTS.ID ")
                .append("WHERE REASONS.CLOSED = ?")
                .append("ORDER BY REASONS.CREATED_AT ASC ")
                .append("OFFSET ? ROWS FETCH NEXT ? ROWS ONLY");

        PreparedStatement preparedStatement = this.conn.prepareStatement(sqlSelectCard.toString());
        preparedStatement.setBoolean(1, false);
        preparedStatement.setLong(2, pager.getOffset());
        preparedStatement.setLong(3, pager.getPerPage());
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

        pager.setRecords(list);
        pager.setTotalCount(getTotalReasons());
    }

    public CardLostOrStolen getReasonById(Long id) throws SQLException {
        CardLostOrStolen currentReason = null;

        StringBuilder sqlSelectCard = new StringBuilder();
        sqlSelectCard.append("SELECT REASONS.CARD_ID, CARDS.NUM, CARDS.EMISSION, CARDS.VALID_THROW, REASONS.CLIENT_COMMENT, ")
                .append("CLIENTS.NAME, CLIENTS.CPF, REASONS.CREATED_AT ")
                .append("FROM CARDS_FLAGGED_LOST_STOLEN REASONS ")
                .append("INNER JOIN CARDS ON REASONS.CARD_ID = CARDS.ID ")
                .append("INNER JOIN CLIENTS ON CARDS.CLIENT_ID = CLIENTS.ID ")
                .append("WHERE REASONS.CARD_ID = ?");

        PreparedStatement preparedStatement = this.conn.prepareStatement(sqlSelectCard.toString());
        preparedStatement.setLong(1, id);
        ResultSet rs = preparedStatement.executeQuery();

        if (rs.next()) {
            currentReason = new CardLostOrStolen();
            currentReason.setId(rs.getLong(1));
            currentReason.setCardNumber(rs.getString(2));
            currentReason.setCardEmission(rs.getDate(3));
            currentReason.setCardExpiration(rs.getDate(4));
            currentReason.setComment(rs.getString(5));
            currentReason.setClientName(rs.getString(6));
            currentReason.setClientCpf(rs.getString(7));
            currentReason.setCreatedAt(rs.getTimestamp(8));
        }

        return currentReason;
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

    public void closeReasonById(Long id) throws SQLException {
        StringBuilder sql = new StringBuilder("UPDATE CARDS_FLAGGED_LOST_STOLEN ");
        sql.append("SET CLOSED = ? ")
           .append("WHERE CARD_ID = ?");

        PreparedStatement statement = this.conn.prepareStatement(sql.toString());
        statement.setBoolean(1, true);
        statement.setLong(2, id);
        statement.executeUpdate();
    }
}
