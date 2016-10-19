package repositories;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by vrjunior on 14/10/16.
 */
public class ConnectionManager {
    private static final String driverName = "oracle.jdbc.driver.OracleDriver";
    private static final String connectionUrl = "jdbc:oracle:thin:@autobank.cmb74zcd1bt9.us-west-2.rds.amazonaws.com:1521:orcl";
    private static final String sid = "ORCL";
    private static final String user = "autobank_db";
    private static final String password = "+h]4c#sL<fBNk`s5b5\\94ykR";

    private BasicDataSource ds;

    public ConnectionManager() {
        ds = new BasicDataSource();
        ds.setDriverClassName(driverName);
        ds.setUrl(connectionUrl);
        ds.setUsername(user);
        ds.setPassword(password);
    }

    public Connection getConnection() {
        Connection conn = null;
        try {
            conn = ds.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public void closeConnection(Connection conn) {
        try {

            conn.close();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
