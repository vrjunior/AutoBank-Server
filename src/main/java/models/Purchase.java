package models;

import java.math.BigDecimal;
import java.sql.Date;

/**
 * Created by valmir.massoni on 11/11/2016.
 */
public class Purchase {
    private Long id;
    private Date processDate;
    private String establishmentName;
    private String categoryName;
    private int installmentsAmount;
    private BigDecimal installmentValue;
    private int currentInstallmente;
}
