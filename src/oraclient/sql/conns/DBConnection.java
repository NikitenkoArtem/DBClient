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
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

import oraclient.component.ClientArea;

import oraclient.view.ConnectionDialog;
import oraclient.view.FrontEndForm;

import org.apache.commons.dbcp2.BasicDataSource;

public class DBConnection {
    private static Map<Integer, Connection> connections;
    private static Connection connection;
//    private static Statement stmt;
    private static String url = "jdbc:oracle:thin:@localhost:1521:xe";
    private static String username = "hr";
    private static String password = "hr";
    //    private static String url;
    //    private static String username;
    //    private static String password;
    private Map<Object, Object> result = new HashMap<>();
    private final String lineSeparator = System.getProperty("line.separator");
//    private final static BasicDataSource basicDataSource = new BasicDataSource();

    public DBConnection() {
        if (connections == null) {
            init();
        }
    }

    private void init() {
        connections = new HashMap<>();
    }

    public void putConnection(int id, Connection conn) {
        connections.put(id, conn);
    }

    public Connection find(int key) {
        for (Map.Entry<Integer, Connection> entry : connections.entrySet()) {
            if (key == entry.getKey()) {
                return entry.getValue();
            }
        }
        return null;
    }

    public int find(Connection value) {
//        if (textAreas.containsValue(value)) {
            for (Map.Entry<Integer, Connection> entry : connections.entrySet()) {
                if (value == entry.getValue()) {
                    return entry.getKey();
                }
            }
//        }
        return -1;
    }
    
    private void createConnectionPool() {
//        ConnectionFactory connectionFactory = new DriverManagerConnectionFactory("connectURI",null);
//        PoolableConnectionFactory poolableConnectionFactory = new PoolableConnectionFactory(connectionFactory, null);
////        ObjectPool objectPool = new GenericObjectPool(poolableConnectionFactory);
//        ObjectPool objectPool = new GenericObjectPool();
//        PoolingDataSource dataSource = new PoolingDataSource(objectPool);
    }
    
    public static Connection getConnection() throws SQLException {
        //                ConnectionDialog dialog = new ConnectionDialog();
        //                dialog.setVisible(true);
        //                url = dialog.getUrl().getText();
        //                username = dialog.getUsrname().getText();
//                        password = dialog.getPwd().getPassword().toString();
        //            JOptionPane.showMessageDialog(getClass(), e);
        //        DriverManager.registerDriver(new OracleDriver());
//        basicDataSource.setUsername(username);
//        basicDataSource.setPassword(password);
//        basicDataSource.setUrl(url);
        connection = DriverManager.getConnection(url, username, password);
        connections.put(0, connection);
        //        conn.setAutoCommit(false);
        return connection;
    }

    public static Map<Integer, Connection> getConnections() {
        return connections;
    }

    public void getDBName(Connection conn, FrontEndForm form) throws SQLException {
        form.setTitle(form.getTitle() + "   " + conn.getMetaData().getDatabaseProductName() + ":" +
                      conn.getMetaData().getUserName());
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
            getDBMetaData();
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
        Iterator<Throwable> it = ex.iterator();
        while (it.hasNext()) {
            ((JTextArea) console).append(ex.getSQLState() + lineSeparator);
            ((JTextArea) console).append(ex.getErrorCode() + lineSeparator);
        }
    }

    private void getDBMetaData() throws SQLException {
        DatabaseMetaData dbMeta = connection.getMetaData();        
        ResultSet catalogs = dbMeta.getCatalogs();
//        String functions = dbMeta.getSystemFunctions();
//        ResultSet rs = dbMeta.getSchemas();
//        ResultSetMetaData meta = rs.getMetaData();
        //        dbMeta.getSchemas(catalogs, schemaPattern)
//        System.out.println(catalogs);
//        System.out.println(functions);
        System.out.println(catalogs.toString());
        while (catalogs.next()) {
//            for (int i = 1; i <= meta.getColumnCount(); i++) {
            System.out.println("TABLE_CAT = " + catalogs.getString("TABLE_CAT"));
//            System.out.println(catalogs.getString(2));
//            }
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
}
