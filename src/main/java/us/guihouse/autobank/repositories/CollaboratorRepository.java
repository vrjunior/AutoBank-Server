package us.guihouse.autobank.repositories;

import java.sql.Connection;

/**
 * Created by vrjunior on 25/11/16.
 */
public class CollaboratorRepository {
    private Connection conn;

    public CollaboratorRepository(Connection conn) {
        this.conn = conn;
    }

    public boolean validateCollaboratorById(Long id) {
        return true;
    }
}
