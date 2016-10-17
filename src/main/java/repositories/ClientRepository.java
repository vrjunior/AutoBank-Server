package repositories;

import models.Client;
import models.Token;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by vrjunior on 15/10/16.
 */
public class ClientRepository {
    private Connection conn;

    public ClientRepository(Connection conn) {
        this.conn = conn;
    }

    public Token performLogin(String emailOrCpf, String password) {
        StringBuilder sqlSelectClient = new StringBuilder();
        TokenRepository tokenRepository = new TokenRepository(this.conn);
        sqlSelectClient.append("SELECT ID, PASSWORD FROM CLIENTS ")
                .append("WHERE EMAIL = ? ")
                .append("OR CPF = ? ");

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sqlSelectClient.toString());
            preparedStatement.setString(1, emailOrCpf);
            preparedStatement.setString(2, emailOrCpf);
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()) {
                if(password.equals(rs.getString("PASSWORD"))) {
                    return tokenRepository.createToken(rs.getInt("ID"));
                }
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
