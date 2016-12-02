package us.guihouse.autobank.repositories;

import org.mindrot.jbcrypt.BCrypt;
import us.guihouse.autobank.models.client.Client;
import us.guihouse.autobank.models.collaborator.ClientOrdenation;
import us.guihouse.autobank.models.collaborator.Collaborator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by vrjunior on 25/11/16.
 */
public class CollaboratorRepository {
    public static class NoAuthentication extends Exception {}

    private Connection conn;
    public CollaboratorRepository(Connection conn) {
        this.conn = conn;
    }

    public Collaborator validateCollaboratorById(Long id) throws NoAuthentication {
        Collaborator currentCollaborator = new Collaborator();
        StringBuilder selectCollaborator  = new StringBuilder();
        selectCollaborator.append("SELECT ID, NAME, CPF, EMAIL, PASSWORD ")
                .append("FROM COLLABORATORS ")
                .append("WHERE ID = ? ");

        try {
            PreparedStatement ps = this.conn.prepareStatement(selectCollaborator.toString());
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if(!rs.next())
                throw new NoAuthentication();

            currentCollaborator.setId(rs.getLong("ID"));
            currentCollaborator.setName(rs.getString("NAME"));
            currentCollaborator.setCpf(rs.getString("CPF"));
            currentCollaborator.setEmail(rs.getString("EMAIL"));
            currentCollaborator.setPassword(rs.getString("PASSWORD"));

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return currentCollaborator;
    }

    public Collaborator performLogin(String email, String password) throws NoAuthentication {
        Collaborator collaborator = new Collaborator();

        StringBuilder selectCollaborator  = new StringBuilder();
        selectCollaborator.append("SELECT ID, NAME, CPF, EMAIL, PASSWORD ")
                .append("FROM COLLABORATORS ")
                .append("WHERE EMAIL = ? ");

        try {
            PreparedStatement ps = this.conn.prepareStatement(selectCollaborator.toString());
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if(!rs.next()) {
                throw new NoAuthentication();
            }
            collaborator.setId(rs.getLong("ID"));
            collaborator.setName(rs.getString("NAME"));
            collaborator.setCpf(rs.getString("CPF"));
            collaborator.setEmail(rs.getString("EMAIL"));
            collaborator.setPassword(rs.getString("PASSWORD"));

            if(!(BCrypt.checkpw(password, collaborator.getPassword()))) {
                throw new NoAuthentication();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return collaborator;
    }

    public ArrayList<Client> getClients(String search, ClientOrdenation clientOrdenation) {
        StringBuilder selectClient = new StringBuilder();
        ArrayList<Client> clients = new ArrayList<>();
        selectClient.append("SELECT ID, NAME, EMAIL, CPF, BIRTHDAY ");
        selectClient.append("FROM CLIENTS ");
        if(search != null) {
            selectClient.append("WHERE (NAME LIKE ? ")
                    .append("OR EMAIL LIKE ? ")
                    .append("OR CPF LIKE ?) ");
        }
        if(clientOrdenation != null) {
            switch (clientOrdenation.getOrdenation()) {
                case CPF:
                    selectClient.append("ORDER BY CPF ");
                    break;
                case NAME:
                    selectClient.append("ORDER BY NAME ");
                    break;
                case EMAIL:
                    selectClient.append("ORDER BY EMAIL ");
                    break;
                default:
                    selectClient.append("ORDER BY EMAIL ID ");
                    break;
            }
            if(clientOrdenation.getDirection()) {
                selectClient.append(" DESC ");
            }
        }

        try {
            PreparedStatement ps = this.conn.prepareCall(selectClient.toString());
            if(search != null) {
                ps.setString(1, search);
                ps.setString(2, search);
                ps.setString(3, search);

            }
            ResultSet rs = ps.executeQuery();
            Client client;
            while(rs.next()) {
                client = new Client();
                client.setId(rs.getLong("ID"));
                client.setName(rs.getString("NAME"));
                client.setEmail(rs.getString("EMAIL"));
                client.setCpf(rs.getString("CPF"));
                client.setBirthday(rs.getDate("BIRTHDAY"));

                clients.add(client);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clients;
    }
    public ArrayList<Client> getClients() {
        return this.getClients(null, null);
    }
}
