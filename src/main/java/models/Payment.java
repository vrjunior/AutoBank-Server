package models;

import java.math.BigDecimal;

/**
 * Created by valmir.massoni on 16/11/2016.
 */
public class Payment extends FinantialStatement {
    private BigDecimal paymentValue;

    public BigDecimal getPaymentValue() {
        return paymentValue;
    }

    public void setPaymentValue(BigDecimal paymentValue) {
        this.paymentValue = paymentValue;
    }
}
