package us.guihouse.autobank.models.collaborator;

import us.guihouse.autobank.models.client.Card;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * Created by guilherme on 05/12/16.
 */
public class CardLostOrStolen {
    private Long id;
    private String clientName;
    private String clientCpf;
    private String cardNumber;
    private Date cardExpiration;
    private Date cardEmission;
    private String comment;
    private Timestamp createdAt;

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientCpf() {
        return clientCpf;
    }

    public void setClientCpf(String clientCpf) {
        this.clientCpf = clientCpf;
    }

    public Date getCardExpiration() {
        return cardExpiration;
    }

    public void setCardExpiration(Date cardExpiration) {
        this.cardExpiration = cardExpiration;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCardEmission() {
        return cardEmission;
    }

    public void setCardEmission(Date cardEmission) {
        this.cardEmission = cardEmission;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
