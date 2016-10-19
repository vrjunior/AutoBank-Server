package models;

/**
 * Created by valmir.massoni on 19/10/2016.
 */
public class ClosedBill extends Bill {
    private Double totalValue;
    private Double minValue;

    public Double getTotalValue() {
        return totalValue;
    }

    public Double getMinValue() {
        return minValue;
    }

    public void setTotalValue(Double totalValue) {
        this.totalValue = totalValue;
    }

    public void setMinValue(Double minValue) {
        this.minValue = minValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        ClosedBill that = (ClosedBill) o;

        if (!totalValue.equals(that.totalValue)) return false;
        return minValue.equals(that.minValue);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + totalValue.hashCode();
        result = 31 * result + minValue.hashCode();
        return result;
    }
}
