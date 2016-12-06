package us.guihouse.autobank.models.client;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;

/**
 * Created by valmir.massoni on 19/10/2016.
 */
public class Card {
    private static final int CARD_DIGITS = 16;
    private Long id;
    private String cardNumber;
    private String cvv;
    private Date emission;
    private Date validThrow;
    private BigDecimal limit;
    private BigDecimal interestRate;
    private Integer closingDay;
    private Boolean isActive;
    private BigDecimal availableValue;
    private BigDecimal usedValue;
    private Long clientId;

    public static Card fromResultSet(ResultSet rs) throws SQLException {
        Card currentCard = new Card();
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
        currentCard.setClientId(rs.getLong("CLIENT_ID"));

        if (usedValue.compareTo(BigDecimal.ZERO) > 0) {
            currentCard.setUsedValue(usedValue);
        } else {
            // Payments + Reversals > Installments + Interests
            // So, the client is creditor
            currentCard.setUsedValue(BigDecimal.ZERO);
        }

        currentCard.setAvailableValue(limit.subtract(usedValue));
        return currentCard;
    }

    private static String generateNumber() {
        StringBuilder builder = new StringBuilder();

        while(builder.length() < CARD_DIGITS) {
            builder.append(Math.round(Math.random() * 1000000));
        }

        return builder.substring(0, CARD_DIGITS);
    }

    private static String generateCvv(Card c) {
        StringBuilder builder = new StringBuilder();
        return builder.append(c.cardNumber.charAt(0))
                .append(c.cardNumber.charAt(4))
                .append(c.cardNumber.charAt(8)).toString();
    }

    public static Card generateNewCard(Card oldCard) {
        LocalDateTime now = LocalDateTime.now();

        Card generated = new Card();
        generated.cardNumber = generateNumber();
        generated.cvv = generateCvv(generated);
        generated.emission = Date.valueOf(now.toLocalDate());
        generated.validThrow = Date.valueOf(now.plusYears(5).toLocalDate());
        generated.limit = oldCard.limit;
        generated.interestRate = oldCard.interestRate;
        generated.closingDay = oldCard.closingDay;

        return generated;
    }

    public Long getId() {
        return id;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getCvv() {
        return cvv;
    }

    public Date getEmission() {
        return emission;
    }

    public Date getValidThrow() {
        return validThrow;
    }

    public BigDecimal getLimit() {
        return limit;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public Integer getClosingDay() {
        return closingDay;
    }

    public Boolean isActive() {
        return isActive;
    }

    public BigDecimal getAvailableValue() {
        return availableValue;
    }

    public BigDecimal getUsedValue() {
        return usedValue;
    }

    public String getParcialCardNumber() {
        if (cardNumber.length() > 4) {
            return cardNumber.substring(cardNumber.length() - 4);
        }

        return "";
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public void setEmission(Date emission) {
        this.emission = emission;
    }

    public void setValidThrow(Date validThrow) {
        this.validThrow = validThrow;
    }

    public void setLimit(BigDecimal limit) {
        this.limit = limit;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    public void setClosingDay(Integer closingDay) {
        this.closingDay = closingDay;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public void setAvailableValue(BigDecimal availableValue) {
        this.availableValue = availableValue;
    }

    public void setUsedValue(BigDecimal usedValue) {
        this.usedValue = usedValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Card card = (Card) o;

        if (!id.equals(card.id)) return false;
        if (!cardNumber.equals(card.cardNumber)) return false;
        if (!cvv.equals(card.cvv)) return false;
        return emission.equals(card.emission);

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + cardNumber.hashCode();
        result = 31 * result + cvv.hashCode();
        result = 31 * result + emission.hashCode();
        return result;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
    }
}
