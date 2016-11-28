package us.guihouse.autobank.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by vrjunior on 25/11/16.
 */
public class CollaboratorRepository {
    private Connection conn;

    public CollaboratorRepository(Connection conn) {
        this.conn = conn;
    }

    public boolean validateCollaboratorById(Long id) {
        StringBuilder selectClient  = new StringBuilder();
        selectClient.append("SELECT NAME ")
                .append("FROM COLLABORATORS ")
                .append("WHERE ID = ? ");

        try {
            PreparedStatement ps = this.conn.prepareStatement(selectClient.toString());
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if(!rs.next())
                return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;
    }
}
