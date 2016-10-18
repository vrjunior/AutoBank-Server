package servlets;

import models.Client;
import repositories.ClientRepository;
import repositories.ConnectionManager;

import java.sql.Connection;

/**
 * Created by valmir.massoni on 18/10/2016.
 */
public abstract class TolkenAuthentication {

    private ClientRepository clientRepository;
    public static Connection conn;
    public static Client currentClient;

    protected void authenticateTolken(String tolken) throws ClientRepository.NoAuthentication {
        if(conn == null) {
            conn = new ConnectionManager().getConnection();
        }
        clientRepository = new ClientRepository(this.conn);
        currentClient = clientRepository.getClientByTolken(tolken);
    }
}
