package us.guihouse.autobank.models;

import java.util.Date;

/**
 * Created by vrjunior on 15/10/16.
 */
public class Token {
    private Long id;
    private String token;
    private Date creationDate;

    public Long getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Token token1 = (Token) o;

        if (!id.equals(token1.id)) return false;
        return token.equals(token1.token);

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + token.hashCode();
        return result;
    }
}
