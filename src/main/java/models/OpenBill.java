package models;

import java.math.BigDecimal;

/**
 * Created by valmir.massoni on 09/11/2016.
 */
public class OpenBill extends Bill {
    private BigDecimal partialValue;

    public BigDecimal getPartialValue() {
        return partialValue;
    }

    public void setPartialValue(BigDecimal partialValue) {
        this.partialValue = partialValue;
    }
}
