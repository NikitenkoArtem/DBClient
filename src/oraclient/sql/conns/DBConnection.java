package oraclient.sql.conns;

import java.io.BufferedReader;
import java.io.File;

import java.io.FileInputStream;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.tree.DefaultMutableTreeNode;

import oraclient.io.LoadClass;
import oraclient.io.LoadDriver;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbcp2.ConnectionFactory;
import org.apache.commons.dbcp2.DriverManagerConnectionFactory;
import org.apache.commons.dbcp2.PoolableConnectionFactory;
import org.apache.commons.dbcp2.PoolingDataSource;
import org.apache.commons.pool2.impl.GenericObjectPool;

public class DBConnection {
//    private static Map<Connection, String> connections;
    private static List<Connection> connections;
    private static Connection connection;
    private int connectionCount;
    private static BasicDataSource basicDataSource;

    public DBConnection() {
        if (connections == null) {
            new LoadDriver("lib/ojdbc7.jar", "oracle.jdbc.OracleDriver");
            LoadClass load = new LoadClass();
            load.addClass(new File(load.jarFilePath("lib/commons-dbcp2-2.1.1.jar")));
            load.addClass(new File(load.jarFilePath("lib/commons-pool2-2.4.2.jar")));
            init();
        }
    }

    private void init() {
//        connections = new HashMap<>();
        connections = new ArrayList<>();
        basicDataSource = new BasicDataSource();
    }

    //    public void putConnection(Connection conn, boolean connected) {
    //        connections.put(conn, dbUsers);
    //    }

    public Connection find(String userName) {
//        for (Map.Entry<Connection, String> entry : connections.entrySet()) {
//            if (userName.equals(entry.getValue())) {
//                return entry.getKey();
//            }
//        }
        try {
        } finally {
            for (Iterator<Connection> it = connections.iterator(); it.hasNext();) {
                try {
                    if (userName.equals(it.next().getMetaData().getUserName())) {
                        return it.next();
                    }
                } catch (SQLException e) {
                }
            }
        }
        return null;
    }

    private void createConnectionPool() {
//        GenericObjectPool genericObjectPool = new GenericObjectPool(null);
//        ConnectionFactory connectionFactory = new DriverManagerConnectionFactory("connectURI",null);
//        PoolableConnectionFactory poolableConnectionFactory = new PoolableConnectionFactory(connectionFactory, null);
//        PoolingDataSource dataSource = new PoolingDataSource(genericObjectPool);
    }

    public static Connection getConnection(String url, String user, String password) throws SQLException {
        //        basicDataSource.setUsername(username);
        //        basicDataSource.setPassword(password);
        //        basicDataSource.setUrl(url);
//        basicDataSource.getConnection();
        connection = DriverManager.getConnection(url, user, password);
        connections.add(connection);
//        connections.put(connection, connection.getMetaData().getUserName());
        //        conn.setAutoCommit(false);
        return connection;
    }

    public static List<Connection> getConnections() {
        return connections;
    }

//    public static Map<Connection, String> getConnections() {
//        return connections;
//    }

    public Statement exec(Connection conn, JComponent text) throws SQLException {
        //        stmt = conn.createStatement();
        Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        //        stmt.execute(((JTextArea) text).getText());
        stmt.executeQuery("select * from departments");
//                stmt.executeQuery("select * from dba_users");
        //        stmt.executeUpdate("drop table test");
        //        stmt.execute("create table test(id int, name varchar2(5), price int)");
        //        stmt.executeUpdate("insert into test values(1, 'hello', 12345)");
        //        stmt.executeQuery("select * from test");
        return stmt;
    }

    public void getResultSet(Statement stmt, JComponent table) throws SQLException {
        try (ResultSet rs = stmt.getResultSet()) {
            ResultSetMetaData meta = rs.getMetaData();
            DefaultTableModel tableModel = new DefaultTableModel(0, 0);
            Map<Object, Object> resultSet = new HashMap<>();
            while (rs.next()) {
                for (int i = 1; i <= meta.getColumnCount(); i++) {
                    Object object = rs.getObject(i);
                    if (object != null) {
                        object = object.getClass().cast(object);
                    }
                    resultSet.put(meta.getColumnLabel(i), object);
                }
                if (tableModel.getColumnCount() == 0) {
                    addTableColumn(tableModel, resultSet);
                }
                addTableRow(tableModel, resultSet);
            }
            ((JTable) table).setModel(tableModel);
        }
    }

    private void addTableColumn(final DefaultTableModel tableModel, final Map<Object, Object> resultSet) {
        Set<Object> keys = resultSet.keySet();
        for (Object col : keys.toArray()) {
            tableModel.addColumn(col);
        }
    }

    private void addTableRow(final DefaultTableModel tableModel, Map<Object, Object> resultSet) {
        tableModel.addRow(resultSet.values().toArray());
    }

    private void getDBUsers(final Connection conn, final DefaultMutableTreeNode treeNode) throws SQLException {
        ResultSet rs = conn.getMetaData().getSchemas();
        while (rs.next()) {
            treeNode.add(new DefaultMutableTreeNode(rs.getString(1)));
        }
    }

    private void getDBProcedures(final Connection conn, final DefaultMutableTreeNode treeNode) throws SQLException {
        DatabaseMetaData dbMeta = conn.getMetaData();
        String username = dbMeta.getUserName();
        ResultSet rs = dbMeta.getProcedures(null, username, null);
        while (rs.next()) {
            treeNode.add(new DefaultMutableTreeNode(rs.getString(3)));
        }
    }

    private void getDBFunctions(final Connection conn, final DefaultMutableTreeNode treeNode) throws SQLException {
        DatabaseMetaData dbMeta = conn.getMetaData();
        String username = dbMeta.getUserName();
        ResultSet rs = dbMeta.getFunctions(null, username, null);
        while (rs.next()) {
            treeNode.add(new DefaultMutableTreeNode(rs.getString(3)));
        }
    }

    private void getDBEntities(final Connection conn, final DefaultMutableTreeNode treeNode,
                               String type) throws SQLException {
        DatabaseMetaData dbMeta = conn.getMetaData();
        String username = dbMeta.getUserName();
        ResultSet rs = dbMeta.getTables(null, username, null, new String[] { type });
        while (rs.next()) {
            treeNode.add(new DefaultMutableTreeNode(rs.getString(3)));
        }
    }

    public static TableModel getDBProperties(final Connection conn) {
        DefaultTableModel model = null;
        try {
            DatabaseMetaData meta = conn.getMetaData();
            Properties prop = new Properties();
            try (BufferedReader buf = new BufferedReader(new InputStreamReader(
                    DBConnection.class.getResourceAsStream("/resources/db.properties"), "UTF8"))) {
                prop.load(buf);
            } catch (IOException e) {
            }
            int jdbcMinorVersion = meta.getJDBCMinorVersion();
            int jdbcMajorVersion = meta.getJDBCMajorVersion();
            String jdbcVersion = String.format("%d.%d", jdbcMajorVersion, jdbcMinorVersion);
            model = new DefaultTableModel(
                new Object[][] {
                    { prop.getProperty("DatabaseProductName"), meta.getDatabaseProductName() },
                    { prop.getProperty("DatabaseProductVersion"), meta.getDatabaseProductVersion() },
                    { prop.getProperty("DefaultTransactionIsolation"), meta.getDefaultTransactionIsolation() },
                    { prop.getProperty("DriverName"), meta.getDriverName() },
                    { prop.getProperty("DriverVersion"), meta.getDriverVersion() },
                    { prop.getProperty("JDBCVersion"), jdbcVersion },
                    { prop.getProperty("MaxBinaryLiteralLength"), meta.getMaxBinaryLiteralLength() },
                    { prop.getProperty("MaxCharLiteralLength"), meta.getMaxCharLiteralLength() },
                    { prop.getProperty("MaxColumnNameLength"), meta.getMaxColumnNameLength() },
                    { prop.getProperty("MaxColumnsInIndex"), meta.getMaxColumnsInIndex() },
                    { prop.getProperty("MaxColumnsInTable"), meta.getMaxColumnsInTable() },
                    { prop.getProperty("MaxProcedureNameLength"), meta.getMaxProcedureNameLength() },
                    { prop.getProperty("MaxTableNameLength"), meta.getMaxTableNameLength() },
                    { prop.getProperty("MaxUserNameLength"), meta.getMaxUserNameLength() },
                    { prop.getProperty("SQLKeywords"), meta.getSQLKeywords() },
                    { prop.getProperty("NumericFunctions"), meta.getNumericFunctions() },
                    { prop.getProperty("StringFunctions"), meta.getStringFunctions() },
                    { prop.getProperty("TimeDateFunctions"), meta.getTimeDateFunctions() },
                    { prop.getProperty("URL"), meta.getURL() }
                },
                new String[] { "Свойство", "Значение" });
        } catch (SQLException e) {
        }
        return model;
    }

    public void close() {
        try {
        } finally {
            for (Iterator<Connection> it = connections.iterator(); it.hasNext();) {
                try {
                    it.next().close();
                } catch (SQLException e) {
                }
            }
            connections.clear();
        }
    }

    public void close(Connection conn) throws SQLException {
        try {
        } finally {
            if (conn != null) {
                conn.close();
                connections.remove(conn);
            }
        }
    }

    public DefaultMutableTreeNode getDatabaseStructure(final Connection conn) throws SQLException {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode();
        DefaultMutableTreeNode tables = new DefaultMutableTreeNode("Таблицы");
        getDBEntities(conn, tables, "TABLE");
        root.add(tables);
        DefaultMutableTreeNode views = new DefaultMutableTreeNode("Представления");
        getDBEntities(conn, views, "VIEW");
        root.add(views);
        DefaultMutableTreeNode procedures = new DefaultMutableTreeNode("Процедуры");
        getDBProcedures(conn, procedures);
        root.add(procedures);
        DefaultMutableTreeNode functions = new DefaultMutableTreeNode("Функции");
        getDBFunctions(conn, functions);
        root.add(functions);
        DefaultMutableTreeNode triggers = new DefaultMutableTreeNode("Триггеры");
        getDBEntities(conn, triggers, "TRIGGER");
        root.add(triggers);
        DefaultMutableTreeNode users = new DefaultMutableTreeNode("Пользователи");
        getDBUsers(conn, users);
        root.add(users);
        return root;
    }
}
