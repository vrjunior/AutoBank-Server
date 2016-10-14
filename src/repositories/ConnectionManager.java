package repositories;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by vrjunior on 14/10/16.
 */
public class ConnectionManager {
    private static final String driverName = "oracle.jdbc.driver.OracleDriver";
    private static final String connectionUrl = "autobank.cmb74zcd1bt9.us-west-2.rds.amazonaws.com:1521";
    private static final String sid = "ORCL";
    private static final String user = "autobank_db";
    private static final String password = "+h]4c#sL<fBNk`s5b5\\94ykR";

    private Connection conn = null;

    public ConnectionManager() {
        try {
            Class.forName(driverName);

        } catch(ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Connection createConnection() {
        try {
            conn = DriverManager.getConnection(connectionUrl, user, password);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return conn;
    }

    public void closeConnection() {
        try {
            this.conn.close();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
