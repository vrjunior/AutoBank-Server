package models.auxiliar;

import models.ClosedBill;
import models.OpenBill;

import java.util.ArrayList;

/**
 * Created by valmir.massoni on 17/11/2016.
 */
public class Bills {
    private ArrayList<ClosedBill> closedBills;
    private ArrayList<OpenBill> openBills;

    public ArrayList<ClosedBill> getClosedBills() {
        return closedBills;
    }

    public ArrayList<OpenBill> getOpenBills() {
        return openBills;
    }

    public void setClosedBills(ArrayList<ClosedBill> closedBills) {
        this.closedBills = closedBills;
    }

    public void setOpenBills(ArrayList<OpenBill> openBills) {
        this.openBills = openBills;
    }
}
