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
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.table.DefaultTableModel;

import javax.swing.tree.DefaultMutableTreeNode;

import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

import oraclient.component.ClientArea;

import oraclient.view.ConnectionDialog;
import oraclient.view.FrontEndForm;

import org.apache.commons.dbcp2.BasicDataSource;

public class DBConnection {
    private static Map<Connection, Boolean> connections;
    private static Connection connection;
//    private static Statement stmt;
//    private static String url = "jdbc:oracle:thin:@localhost:1521:xe";
//    private static String username = "hr";
//    private static String password = "hr";
//    private static String url = "jdbc:oracle:thin:@localhost:1521:xe";
//    private static String username = "sys as sysdba";
//    private static String password = "root";
    //    private static String url;
    //    private static String username;
    //    private static String password;
    private Map<Object, Object> result = new HashMap<>();
    private final String lineSeparator = System.getProperty("line.separator");
    private int connectionCount;
//    private final static BasicDataSource basicDataSource = new BasicDataSource();

    public DBConnection() {
        if (connections == null) {
            init();
        }
    }

    private void init() {
        connections = new HashMap<>();
    }

    public void putConnection(Connection conn, boolean connected) {
        connections.put(conn, connected);
    }

    public Connection find(Connection key) {
        for (Map.Entry<Connection, Boolean> entry : connections.entrySet()) {
            if (key == entry.getKey()) {
                return entry.getKey();
            }
        }
        return null;
    }

    public Connection find(boolean value) {
//        if (textAreas.containsValue(value)) {
            for (Map.Entry<Connection, Boolean> entry : connections.entrySet()) {
                if (value == entry.getValue()) {
                    return entry.getKey();
                }
            }
//        }
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
        //            JOptionPane.showMessageDialog(getClass(), e);
        //        DriverManager.registerDriver(new OracleDriver());
//        basicDataSource.setUsername(username);
//        basicDataSource.setPassword(password);
//        basicDataSource.setUrl(url);
        connection = DriverManager.getConnection(url, user, password);
//        connection = DriverManager.getConnection(DBConnection.url, DBConnection.username, DBConnection.password);
        connections.put(connection, true);
        //        conn.setAutoCommit(false);
        return connection;
    }

    public static Map<Connection, Boolean> getConnections() {
        return connections;
    }

    public Statement exec(Connection conn, JComponent text) throws SQLException {
//        stmt = conn.createStatement();
//        stmt.execute(sqlFile.getText());
        Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stmt.executeQuery("select * from departments");
//        stmt.executeUpdate("drop table test");
//        stmt.execute("create table test(id int, name varchar2(5), price int)");
//        stmt.executeUpdate("insert into test values(1, 'hello', 12345)");
//        stmt.executeQuery("select * from test");
        //            stmt.execute(area.getTextAreas().iterator().next().getText());
        return stmt;
    }

    public void getResultSet(Statement stmt, JComponent console, JComponent table) {
        try {
            ResultSet rs = stmt.getResultSet();
            ResultSetMetaData meta = rs.getMetaData();
            DefaultTableModel tableModel = new DefaultTableModel(0, 0);
            getColumnLabels(console, meta);
            while (rs.next()) {
                for (int i = 1; i <= meta.getColumnCount(); i++) {
                    result.put(meta.getColumnLabel(i), rs.getObject(i));
                }
                if (tableModel.getColumnCount() == 0) {
                    addTableColumn(tableModel, result);
                }
                addTableRow(tableModel);
                println(console);
            }
            ((JTable) table).setModel(tableModel);
            showWarnings(console, stmt);
            ((JTextArea) console).append(lineSeparator);
        } catch (SQLException e) {
            e.printStackTrace();
            showExceptions(console, e);
        }
    }

    private void addTableColumn(DefaultTableModel tableModel, Map<Object, Object> map) {
        Set<Object> keys = map.keySet();
        for (Object col : keys.toArray()) {
            tableModel.addColumn(col);
        }
    }

    private void addTableRow(DefaultTableModel tableModel) {
        tableModel.addRow(result.values().toArray());
    }

    private void getColumnLabels(JComponent console, ResultSetMetaData meta) throws SQLException {
        for (int i = 1; i <= meta.getColumnCount(); i++) {
            ((JTextArea) console).append(meta.getColumnLabel(i) + "  ");
        }
        //        ((JTextArea) console).append(Arrays.toString(result.keySet().toArray()));
        ((JTextArea) console).append(lineSeparator);
    }

    private void println(JComponent console) {
        ((JTextArea) console).append(Arrays.toString(result.values().toArray()) + lineSeparator);
    }

    private void showWarnings(JComponent console, Statement stat) throws SQLException {
        SQLWarning warning = stat.getWarnings();
        while (warning != null) {
            ((JTextArea) console).append(warning.getCause() + lineSeparator);
            warning.getNextWarning();
        }
    }

    private void showExceptions(JComponent console, SQLException ex) {
        ((JTextArea) console).append("Код ошибки: " + ex.getErrorCode() + lineSeparator);
        ((JTextArea) console).append("Сообщение: " + ex.getMessage() + lineSeparator);
        ((JTextArea) console).append("Причина: " + ex.getCause() + lineSeparator);
    }
    
    private void getDBUsers(Connection conn, DefaultMutableTreeNode treeNode) throws SQLException {        
        ResultSet rs = conn.getMetaData().getSchemas();
        while (rs.next()) {
            treeNode.add(new DefaultMutableTreeNode(rs.getString(1)));
        }
    }
    
    private void getDBProcedures(Connection conn, DefaultMutableTreeNode treeNode) throws SQLException {
        DatabaseMetaData dbMeta = conn.getMetaData();
        String username = dbMeta.getUserName();
        ResultSet rs = dbMeta.getProcedures(null, username, null);
        while (rs.next()) {
            treeNode.add(new DefaultMutableTreeNode(rs.getString(3)));
        }
    }
    
    private void getDBFunctions(Connection conn, DefaultMutableTreeNode treeNode) throws SQLException {
        DatabaseMetaData dbMeta = conn.getMetaData();
        String username = dbMeta.getUserName();
        ResultSet rs = dbMeta.getFunctions(null, username, null);
        while (rs.next()) {
            treeNode.add(new DefaultMutableTreeNode(rs.getString(3)));
        }
    }
    
    private void getDBEntities(Connection conn, DefaultMutableTreeNode treeNode, String type) throws SQLException {
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
            if(connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                }
    //                    connected = false;
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
    }

    public void getDatabaseStructure(JTree dbStructure, final DefaultMutableTreeNode treeNode) throws SQLException {
        DefaultMutableTreeNode tables = new DefaultMutableTreeNode("Таблицы");
        getDBEntities(connection, tables, "TABLE");
        treeNode.add(tables);
        DefaultMutableTreeNode views = new DefaultMutableTreeNode("Представления");
        getDBEntities(connection, views, "VIEW");
        treeNode.add(views);
        DefaultMutableTreeNode procedures = new DefaultMutableTreeNode("Процедуры");
        getDBProcedures(connection, procedures);
        treeNode.add(procedures);
        DefaultMutableTreeNode functions = new DefaultMutableTreeNode("Функции");
        getDBFunctions(connection, functions);
        treeNode.add(functions);
        DefaultMutableTreeNode triggers = new DefaultMutableTreeNode("Триггеры");
        getDBEntities(connection, triggers, "TRIGGER");
        treeNode.add(triggers);
        DefaultMutableTreeNode users = new DefaultMutableTreeNode("Пользователи");
        getDBUsers(connection, users);
        treeNode.add(users);
//        dbStructure.setModel(new DefaultTreeModel(treeNode));
    }
}
