package repositories;

import models.Token;

import java.security.SecureRandom;
import java.sql.*;

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
        byte bytes[] = new byte[52];
        random.nextBytes(bytes);
        return bytes.toString();
    }

    public Token createToken(Long clientId) {
        Token tokenResult = new Token();
        Long id = null;
        java.util.Date creationDate = new Date(System.currentTimeMillis());
        String strToken = this.generateTokenString();
        StringBuilder insertTokenSql = new StringBuilder();

        insertTokenSql.append("INSERT INTO TOKENS(CLIENT_ID, TOKEN, CREATION_DATE)")
        .append("VALUES(?, ?, ?)");

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(insertTokenSql.toString(), Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, clientId);
            preparedStatement.setString(2, strToken);
            preparedStatement.setDate(3, new Date(creationDate.getTime()));
            preparedStatement.executeUpdate();


            //getting last id inserted
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if(rs.next()) {
                id = rs.getLong(1);
            }
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        tokenResult.setId(id);
        tokenResult.setToken(strToken);
        tokenResult.setCreationDate(creationDate);

        return tokenResult;
    }

}
