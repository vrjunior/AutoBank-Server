package us.guihouse.autobank.models.auxiliar;

import us.guihouse.autobank.models.InterestRate;
import us.guihouse.autobank.models.Payment;
import us.guihouse.autobank.models.Purchase;
import us.guihouse.autobank.models.Reversal;

import java.util.ArrayList;

/**
 * Created by valmir.massoni on 17/11/2016.
 */
public class FinantialStatements {
    private ArrayList<Purchase> purchases;
    private ArrayList<InterestRate> interestRates;
    private ArrayList<Payment> payments;
    private ArrayList<Reversal> reversals;

    public ArrayList<Purchase> getPurchases() {
        return purchases;
    }

    public ArrayList<InterestRate> getInterestRates() {
        return interestRates;
    }

    public ArrayList<Payment> getPayments() {
        return payments;
    }

    public ArrayList<Reversal> getReversals() {
        return reversals;
    }

    public void setPurchases(ArrayList<Purchase> purchases) {
        this.purchases = purchases;
    }

    public void setInterestRates(ArrayList<InterestRate> interestRates) {
        this.interestRates = interestRates;
    }

    public void setPayments(ArrayList<Payment> payments) {
        this.payments = payments;
    }

    public void setReversals(ArrayList<Reversal> reversals) {
        this.reversals = reversals;
    }
}
