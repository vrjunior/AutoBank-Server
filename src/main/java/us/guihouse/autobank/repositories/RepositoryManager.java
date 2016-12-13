package us.guihouse.autobank.repositories;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Classe gerenciadora da conexão com o banco de dados
 * É responsável por criar e gerenciar a conexão com banco e outros repositórios que precisam da conexão.
 * Created by guilherme on 05/12/16.
 * @author Guilherme Otranto
 * @author Valmir Massonir Jr.
 */
public class RepositoryManager {
    private Connection connection;

    public RepositoryManager(Connection connection) {
        this.connection = connection;
    }

    /**
     * Método para obter uma instancia de collaboratorRepository
     * @return nova instancia de collaboratorRepository
     */
    public CollaboratorRepository getCollaboratorRepository() {
        return new CollaboratorRepository(connection);
    }

    /**
     * Método para obter uma instancia de CardLostOrStolenRepository
     * @return nova instancia de CardLostOrStolenRepository
     */
    public CardLostOrStolenRepository getCardLostOrStolenRepo() {
        return new CardLostOrStolenRepository(connection);
    }

    /**
     * Método para obter uma instancia de CardRepository
     * @return nova instancia de CardRepository
     */
    public CardRepository getCardRepository() {
        return new CardRepository(connection);
    }

    /**
     *  Método para persistir(commit) as últimas mudanças no banco.
     */
    public void commit() throws SQLException {
        connection.commit();
    }

    /**
     *  Método para voltar o estado do banco para antes das mudanças feitas no banco.
     */
    public void rollback() throws SQLException {
        connection.rollback();
    }
}
