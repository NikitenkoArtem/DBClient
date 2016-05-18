package oraclient.sql.conns;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;

import oraclient.sql.driver.LoadDriver;

public class DBConnection {
    private static Map<Connection, String> connections;
    private static Connection connection;
    //    private static Statement stmt;
    private int connectionCount;
    //    private final static BasicDataSource basicDataSource = new BasicDataSource();

    public DBConnection() {
        if (connections == null) {
            new LoadDriver("oracle.jdbc.OracleDriver");
            init();
        }
    }

    private void init() {
        connections = new HashMap<>();
    }

    //    public void putConnection(Connection conn, boolean connected) {
    //        connections.put(conn, dbUsers);
    //    }

    public Connection find(String userName) {
        for (Map.Entry<Connection, String> entry : connections.entrySet()) {
            if (userName.equals(entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }

    private void createConnectionPool() {
        //        ConnectionFactory connectionFactory = new DriverManagerConnectionFactory("connectURI",null);
        //        PoolableConnectionFactory poolableConnectionFactory = new PoolableConnectionFactory(connectionFactory, null);
        ////        ObjectPool objectPool = new GenericObjectPool(poolableConnectionFactory);
        //        ObjectPool objectPool = new GenericObjectPool();
        //        PoolingDataSource dataSource = new PoolingDataSource(objectPool);
    }

    public static Connection getConnection(String url, String user, String password) throws SQLException {
        //        basicDataSource.setUsername(username);
        //        basicDataSource.setPassword(password);
        //        basicDataSource.setUrl(url);
        connection = DriverManager.getConnection(url, user, password);
        connections.put(connection, connection.getMetaData().getUserName());
        //        conn.setAutoCommit(false);
        return connection;
    }

    public static Map<Connection, String> getConnections() {
        return connections;
    }

    public Statement exec(Connection conn, JComponent text) throws SQLException {
        //        stmt = conn.createStatement();
        Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        //        stmt.execute(((JTextArea) text).getText());
        stmt.executeQuery("select * from departments");
        //        stmt.executeQuery("select * from dba_users");
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
                    resultSet.put(meta.getColumnLabel(i), rs.getObject(i));
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

    public void close() {
        try {
        } finally {
            for (Map.Entry<Connection, String> entry : connections.entrySet()) {
                try {
                    entry.getKey().close();
                } catch (SQLException e) {
                }
            }
        }
    }

    public void close(Connection conn) {
        try {
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                    connections.remove(conn);
                } catch (SQLException e) {
                }
            }
        }
    }

    public void getDatabaseStructure(final Connection conn, final DefaultMutableTreeNode treeNode) throws SQLException {
        DefaultMutableTreeNode tables = new DefaultMutableTreeNode("Таблицы");
        getDBEntities(conn, tables, "TABLE");
        treeNode.add(tables);
        DefaultMutableTreeNode views = new DefaultMutableTreeNode("Представления");
        getDBEntities(conn, views, "VIEW");
        treeNode.add(views);
        DefaultMutableTreeNode procedures = new DefaultMutableTreeNode("Процедуры");
        getDBProcedures(conn, procedures);
        treeNode.add(procedures);
        DefaultMutableTreeNode functions = new DefaultMutableTreeNode("Функции");
        getDBFunctions(conn, functions);
        treeNode.add(functions);
        DefaultMutableTreeNode triggers = new DefaultMutableTreeNode("Триггеры");
        getDBEntities(conn, triggers, "TRIGGER");
        treeNode.add(triggers);
        DefaultMutableTreeNode users = new DefaultMutableTreeNode("Пользователи");
        getDBUsers(conn, users);
        treeNode.add(users);
    }
}
