package oraclient.sql.conns;

import com.sun.org.apache.xml.internal.utils.ObjectPool;

import java.io.File;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.table.DefaultTableModel;

import javax.swing.tree.DefaultMutableTreeNode;

import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

import oraclient.component.ClientArea;

import oraclient.sql.driver.LoadDriver;

import oraclient.view.ConnectionDialog;
import oraclient.view.FrontEndForm;

import org.apache.commons.dbcp2.BasicDataSource;

public class DBConnection {
    private static Map<Connection, String> connections;
    private static Connection connection;
//    private static Statement stmt;
    private final String lineSeparator = System.getProperty("line.separator");
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
//        stmt.executeQuery("select * from departments");
        stmt.executeQuery("select * from dba_users");
//        stmt.executeUpdate("drop table test");
//        stmt.execute("create table test(id int, name varchar2(5), price int)");
//        stmt.executeUpdate("insert into test values(1, 'hello', 12345)");
//        stmt.executeQuery("select * from test");
        return stmt;
    }

    public void getResultSet(Statement stmt, JComponent console, JComponent table) throws SQLException {
        ResultSet rs = stmt.getResultSet();
        ResultSetMetaData meta = rs.getMetaData();
        DefaultTableModel tableModel = new DefaultTableModel(0, 0);
        getColumnLabels(console, meta);
        Map<Object, Object> resultSet = new HashMap<>();
        while (rs.next()) {
            for (int i = 1; i <= meta.getColumnCount(); i++) {
                resultSet.put(meta.getColumnLabel(i), rs.getObject(i));
                meta.getColumnDisplaySize(i);
            }
            
            if (tableModel.getColumnCount() == 0) {
                addTableColumn(tableModel, resultSet);
            }
            addTableRow(tableModel, resultSet);
            println(console, resultSet);
        }
        ((JTable) table).setModel(tableModel);
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

    private void getColumnLabels(final JComponent console, ResultSetMetaData meta) throws SQLException {
        for (int i = 1; i <= meta.getColumnCount(); i++) {
            ((JTextArea) console).append(meta.getColumnLabel(i) + "  ");
        }
        //        ((JTextArea) console).append(Arrays.toString(result.keySet().toArray()));
        ((JTextArea) console).append(lineSeparator);
    }

    private void println(final JComponent console, final Map<Object, Object> resultSet) {
        ((JTextArea) console).append(Arrays.toString(resultSet.values().toArray()) + lineSeparator);
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
    
    private void getDBEntities(final Connection conn, final DefaultMutableTreeNode treeNode, String type) throws SQLException {
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
//                    JOptionPane.showMessageDialog(getClass(), e);
                }
            }
        }
    }
    
    public void close(Connection conn) {
        try {
        } finally {
            if(conn != null) {
                try {
                    conn.close();
                    connections.remove(conn);
                } catch (SQLException e) {
                }
            }
        }
    //            try {
    //                if(stmt != null)
    //                    stmt.close();
    //            } catch (Exception e) {
    //                e.printStackTrace();
    //            }
    //            try {
    //                if(rs != null)
    //                    rs.close();
    //            } catch (Exception e) {
    //                e.printStackTrace();
    //            }        
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
