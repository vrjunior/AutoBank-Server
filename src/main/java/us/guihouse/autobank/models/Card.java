package us.guihouse.autobank.models;

import java.math.BigDecimal;
import java.sql.Date;
import us.guihouse.autobank.other.gson.Exclude;

/**
 * Created by valmir.massoni on 19/10/2016.
 */
public class Card {
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
}
