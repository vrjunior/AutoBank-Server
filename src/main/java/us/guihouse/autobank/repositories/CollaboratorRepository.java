package us.guihouse.autobank.repositories;

import org.mindrot.jbcrypt.BCrypt;
import us.guihouse.autobank.models.collaborator.Collaborator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
        selectCollaborator.append("SELECT NAME ")
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

            if(!BCrypt.checkpw(password, collaborator.getPassword())) {
                throw new NoAuthentication();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return collaborator;
    }
}
