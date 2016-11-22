package us.guihouse.autobank.repositories;

import us.guihouse.autobank.models.Client;
import us.guihouse.autobank.models.Token;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by vrjunior on 15/10/16.
 */
public class ClientRepository {
    private Connection conn;

    public static class NoAuthentication extends Exception {}

    public ClientRepository(Connection conn) {
        this.conn = conn;
    }

    public Token performLogin(String emailOrCpf, String password) {
        StringBuilder sqlSelectClient = new StringBuilder();
        TokenRepository tokenRepository = new TokenRepository(this.conn);
        Token tokenResult = null;
        sqlSelectClient.append("SELECT ID, PASSWORD FROM CLIENTS ")
                .append("WHERE EMAIL = ? ")
                .append("OR CPF = ? ");

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sqlSelectClient.toString());
            preparedStatement.setString(1, emailOrCpf);
            preparedStatement.setString(2, emailOrCpf);
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()) {
                if(BCrypt.checkpw(password, rs.getString("PASSWORD"))) {
                    tokenResult = tokenRepository.createToken(rs.getLong("ID"));
                }
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tokenResult;
    }

    public Client getClientByToken(String token) throws NoAuthentication, SQLException {
        StringBuilder sqlSelectClient =  new StringBuilder();
        Client currentClient = new Client();
        sqlSelectClient.append("SELECT ID, NAME, EMAIL, CPF, BIRTHDAY ")
                .append("FROM CLIENTS ")
                .append("WHERE ID = (")
                .append("SELECT CLIENT_ID FROM TOKENS ")
                .append("WHERE TOKEN = ?)");

        PreparedStatement preparedStatement = conn.prepareStatement(sqlSelectClient.toString());
        preparedStatement.setString(1, token);
        ResultSet rs = preparedStatement.executeQuery();
        if(!rs.next()) {
            throw new NoAuthentication();
        }
        currentClient.setId(rs.getLong("ID"));
        currentClient.setName(rs.getString("NAME"));
        currentClient.setEmail(rs.getString("EMAIL"));
        currentClient.setCpf(rs.getString("CPF"));
        currentClient.setBirthday(rs.getDate("BIRTHDAY"));

        return currentClient;
    }
}
