package us.guihouse.autobank.repositories;

import org.apache.commons.dbcp2.BasicDataSource;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;
import javax.sql.DataSource;
import java.sql.*;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

/**
 * Created by vrjunior on 14/10/16.
 */
public class ConnectionManager {
    private static final String driverName = "oracle.jdbc.driver.OracleDriver";
    private static final String connectionUrl = "jdbc:oracle:thin:@autobank.cmb74zcd1bt9.us-west-2.rds.amazonaws.com:1521:orcl";
    private static final String user = "autobank_db";
    private static final String password = "+h]4c#sL<fBNk`s5b5\\94ykR";

    private static BasicDataSource ds = null;

    private static BasicDataSource getDataSource() {
        if (ds == null) {
            System.out.println("Creating new DataSource");
            ds = new BasicDataSource();
            ds.setDriverClassName(driverName);
            ds.setUrl(connectionUrl);
            ds.setUsername(user);
            ds.setPassword(password);
        }

        return ds;
    }

    public Connection getConnection() {
        try {
            //The exact prefix here has a lot to do with clustering, etc., but if you are using one JBoss instance standalone, this works.
            String jndiPath = "java:/OracleDS";
            Connection conn = null;

            try {
                Context ctx = new InitialContext();
                DataSource ds = (DataSource) PortableRemoteObject.narrow(ctx.lookup(jndiPath), DataSource.class);
                if (ds != null) {
                    System.out.println("Connection provided by Java DataSource");
                    conn = ds.getConnection();
                }
            } catch (NamingException e) {
                e.printStackTrace();
            }

            if (conn == null) {
                System.out.println("Creating new Connection");
                conn = getDataSource().getConnection();
                System.out.println("Created new Connection");
            }

            return new ConnectionWrapper(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void closeConnection(Connection conn) {
        try {
            conn.close();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private class ConnectionWrapper implements Connection {
        private final Connection delegation;

        ConnectionWrapper(Connection connection) {
            this.delegation = connection;
        }

        private void logStatement(String s) {
            System.out.println(new StringBuilder("QUERY: ").append(s));
        }

        public Statement createStatement() throws SQLException {
            return delegation.createStatement();
        }

        public PreparedStatement prepareStatement(String sql) throws SQLException {
            logStatement(sql);
            return delegation.prepareStatement(sql);
        }

        public CallableStatement prepareCall(String sql) throws SQLException {
            logStatement(sql);
            return delegation.prepareCall(sql);
        }

        public String nativeSQL(String sql) throws SQLException {
            logStatement(sql);
            return delegation.nativeSQL(sql);
        }

        public void setAutoCommit(boolean autoCommit) throws SQLException {
            delegation.setAutoCommit(autoCommit);
        }

        public boolean getAutoCommit() throws SQLException {
            return delegation.getAutoCommit();
        }

        public void commit() throws SQLException {
            logStatement("COMMIT");
        }

        public void rollback() throws SQLException {
            logStatement("ROLLBACK");
        }

        public void close() throws SQLException {
            delegation.close();
        }

        public boolean isClosed() throws SQLException {
            return delegation.isClosed();
        }

        public DatabaseMetaData getMetaData() throws SQLException {
            return delegation.getMetaData();
        }

        public void setReadOnly(boolean readOnly) throws SQLException {
            delegation.setReadOnly(readOnly);
        }

        public boolean isReadOnly() throws SQLException {
            return delegation.isReadOnly();
        }

        public void setCatalog(String catalog) throws SQLException {
            delegation.setCatalog(catalog);
        }

        public String getCatalog() throws SQLException {
            return delegation.getCatalog();
        }

        public void setTransactionIsolation(int level) throws SQLException {
            delegation.setTransactionIsolation(level);
        }

        public int getTransactionIsolation() throws SQLException {
            return delegation.getTransactionIsolation();
        }

        public SQLWarning getWarnings() throws SQLException {
            return delegation.getWarnings();
        }

        public void clearWarnings() throws SQLException {
            delegation.clearWarnings();
        }

        public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
            return delegation.createStatement(resultSetType, resultSetConcurrency);
        }

        public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
            logStatement(sql);
            return delegation.prepareStatement(sql, resultSetType, resultSetConcurrency);
        }

        public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
            logStatement(sql);
            return delegation.prepareCall(sql, resultSetType, resultSetConcurrency);
        }

        public Map<String, Class<?>> getTypeMap() throws SQLException {
            return delegation.getTypeMap();
        }

        public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
            delegation.setTypeMap(map);
        }

        public void setHoldability(int holdability) throws SQLException {
            delegation.setHoldability(holdability);
        }

        public int getHoldability() throws SQLException {
            return delegation.getHoldability();
        }

        public Savepoint setSavepoint() throws SQLException {
            return delegation.setSavepoint();
        }

        public Savepoint setSavepoint(String name) throws SQLException {
            return delegation.setSavepoint(name);
        }

        public void rollback(Savepoint savepoint) throws SQLException {
            logStatement("ROLLBACK");
            delegation.rollback(savepoint);
        }

        public void releaseSavepoint(Savepoint savepoint) throws SQLException {
            logStatement("RELEASE");
            delegation.releaseSavepoint(savepoint);
        }

        public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
            return delegation.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability);
        }

        public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
            logStatement(sql);
            return delegation.prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
        }

        public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
            logStatement(sql);
            return delegation.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
        }

        public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
            logStatement(sql);
            return delegation.prepareStatement(sql, autoGeneratedKeys);
        }

        public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
            logStatement(sql);
            return delegation.prepareStatement(sql, columnIndexes);
        }

        public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
            logStatement(sql);
            return delegation.prepareStatement(sql, columnNames);
        }

        public Clob createClob() throws SQLException {
            return delegation.createClob();
        }

        public Blob createBlob() throws SQLException {
            return delegation.createBlob();
        }

        public NClob createNClob() throws SQLException {
            return delegation.createNClob();
        }

        public SQLXML createSQLXML() throws SQLException {
            return delegation.createSQLXML();
        }

        public boolean isValid(int timeout) throws SQLException {
            return delegation.isValid(timeout);
        }

        public void setClientInfo(String name, String value) throws SQLClientInfoException {
            delegation.setClientInfo(name, value);
        }

        public void setClientInfo(Properties properties) throws SQLClientInfoException {
            delegation.setClientInfo(properties);
        }

        public String getClientInfo(String name) throws SQLException {
            return delegation.getClientInfo(name);
        }

        public Properties getClientInfo() throws SQLException {
            return delegation.getClientInfo();
        }

        public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
            return delegation.createArrayOf(typeName, elements);
        }

        public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
            return delegation.createStruct(typeName, attributes);
        }

        public void setSchema(String schema) throws SQLException {
            delegation.setSchema(schema);
        }

        public String getSchema() throws SQLException {
            return delegation.getSchema();
        }

        public void abort(Executor executor) throws SQLException {
            logStatement("ABORT");
            delegation.abort(executor);
        }

        public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {
            delegation.setNetworkTimeout(executor, milliseconds);
        }

        public int getNetworkTimeout() throws SQLException {
            return delegation.getNetworkTimeout();
        }

        public <T> T unwrap(Class<T> iface) throws SQLException {
            return delegation.unwrap(iface);
        }

        public boolean isWrapperFor(Class<?> iface) throws SQLException {
            return delegation.isWrapperFor(iface);
        }
    }
}
