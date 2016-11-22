package us.guihouse.autobank.models;

import java.util.Date;

/**
 * Created by vrjunior on 15/10/16.
 */
public class Client {
    private Long id;
    private String name;
    private String email;
    private String cpf;
    private String password;
    private Date birthday;
    private Card card;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getCpf() {
        return cpf;
    }

    public String getPassword() {
        return password;
    }

    public Date getBirthday() {
        return birthday;
    }

    public Card getCard() {
        return card;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public void setCard(Card card) {
        this.card = card;
    }

}
