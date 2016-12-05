package us.guihouse.autobank.repositories;

import java.sql.Connection;

/**
 * Created by guilherme on 05/12/16.
 */
public class CardLostOrStolenRepository {
    private Connection conn;

    public CardLostOrStolenRepository(Connection conn) {
        this.conn = conn;
    }


}
