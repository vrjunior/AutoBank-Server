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

    public class NoAuthentication extends Exception {}

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

    public Client getClientByTolken(String tolken) throws NoAuthentication {
        StringBuilder sqlSelectClient =  new StringBuilder();
        Client currentClient = new Client();
        sqlSelectClient.append("SELECT ID, NAME, EMAIL, CPF, BIRTHDAY ")
                .append("FROM CLIENTS")
                .append("WHERE ID = (")
                .append("SELECT ID FROM TOLKENS")
                .append("WHERE TOLKEN = ?)");

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sqlSelectClient.toString());
            preparedStatement.setString(1, tolken);
            ResultSet rs = preparedStatement.executeQuery();
            if(!rs.next()) {
                throw new NoAuthentication();
            }
            currentClient.setId(rs.getLong("ID"));
            currentClient.setName(rs.getString("NAME"));
            currentClient.setEmail(rs.getString("EMAIL"));
            currentClient.setCpf(rs.getString("CPF"));
            currentClient.setBirthday(rs.getDate("BIRTHDAY"));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return currentClient;
    }
}
