package models;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by valmir.massoni on 18/10/2016.
 */
public class Bill {
    private Long id;
    private Integer month;
    private Integer year;
    private Date paymentDeadline;
    private BigDecimal parcialValue;

    public Long getId() {
        return id;
    }

    public Integer getMonth() {
        return month;
    }

    public Integer getYear() {
        return year;
    }

    public Date getPaymentDeadline() {
        return paymentDeadline;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public void setPaymentDeadline(Date paymentDeadline) {
        this.paymentDeadline = paymentDeadline;
    }

    public BigDecimal getParcialValue() {
        return parcialValue;
    }

    public void setParcialValue(BigDecimal parcialValue) {
        this.parcialValue = parcialValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Bill bill = (Bill) o;

        if (!id.equals(bill.id)) return false;
        if (!month.equals(bill.month)) return false;
        return year.equals(bill.year);

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + month.hashCode();
        result = 31 * result + year.hashCode();
        return result;
    }
}
