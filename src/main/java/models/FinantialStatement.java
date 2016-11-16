package models;

import java.sql.Date;

/**
 * Created by valmir.massoni on 16/11/2016.
 */
public abstract class FinantialStatement {
    private Long id;
    private Date processDate;

    public Long getId() {
        return id;
    }

    public Date getProcessDate() {
        return processDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setProcessDate(Date processDate) {
        this.processDate = processDate;
    }
}
