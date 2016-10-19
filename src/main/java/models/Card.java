package models;

import java.sql.Date;

/**
 * Created by valmir.massoni on 19/10/2016.
 */
public class Card {
    private Long id;
    private String cardNumber;
    private String cvv;
    private Date emission;
    private Date validThrow;
    private Float limit;
    private Float interestRate;
    private Integer closingDay;
    private Boolean isActive;

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

    public Float getLimit() {
        return limit;
    }

    public Float getInterestRate() {
        return interestRate;
    }

    public Integer getClosingDay() {
        return closingDay;
    }

    public Boolean isActive() {
        return isActive;
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

    public void setLimit(Float limit) {
        this.limit = limit;
    }

    public void setInterestRate(Float interestRate) {
        this.interestRate = interestRate;
    }

    public void setClosingDay(Integer closingDay) {
        this.closingDay = closingDay;
    }

    public void setActive(Boolean active) {
        isActive = active;
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
