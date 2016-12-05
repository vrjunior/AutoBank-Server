package us.guihouse.autobank.repositories;

import java.sql.Connection;

/**
 * Created by guilherme on 05/12/16.
 */
public class RepositoryManager {
    private Connection connection;

    public RepositoryManager(Connection connection) {
        this.connection = connection;
    }

    public CollaboratorRepository getCollaboratorRepository() {
        return new CollaboratorRepository(connection);
    }

    public CardLostOrStolenRepository getCardLostOrStolenRepo() {
        return new CardLostOrStolenRepository(connection);
    }
}
