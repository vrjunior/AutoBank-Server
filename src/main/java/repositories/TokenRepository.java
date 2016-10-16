package repositories;

import models.Token;

import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by vrjunior on 15/10/16.
 */
public class TokenRepository {
    private Connection conn;

    public TokenRepository(Connection conn) {
        this.conn = conn;
    }

    private String generateTokenString(){
        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[20];
        random.nextBytes(bytes);
        return bytes.toString();
    }

    public Token createToken(Integer clientId) {
        Token tokenResult = new Token();
        Integer id = null;
        java.util.Date creationDate = new Date(System.currentTimeMillis());
        String strToken = this.generateTokenString();
        StringBuilder insertTokenSql = new StringBuilder();

        insertTokenSql.append("INSERT INTO TOKENS(CLIENT_ID, TOKEN, CREATION_DATE)")
        .append("VALUES(?, ?, ?)");

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(insertTokenSql.toString());
            preparedStatement.setInt(1, clientId);
            preparedStatement.setString(2, strToken);
            preparedStatement.setDate(3, new Date(creationDate.getTime()));
            id =preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        tokenResult.setId(id);
        tokenResult.setToken(strToken);
        tokenResult.setCreationDate(creationDate);

        return tokenResult;
    }

}
