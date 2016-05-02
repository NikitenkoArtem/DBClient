package oraclient.sql.conns;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
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

import oraclient.view.ConnectionDialog;
import oraclient.view.FrontEndForm;


public class DBConnection {
    private static Connection connection;
    private static Statement stmt;
//    private static String url = "jdbc:oracle:thin:@localhost:1521:xe";
//    private static String username = "hr"; //"scott";
//    private static String password = "hr"; //"tiger";
    private static String url;
    private static String username;
    private static String password;
    private Map<Object, Object> result = new HashMap<>();
    private boolean connected = false;

    public static Connection getConnection() throws SQLException {
                ConnectionDialog dialog = new ConnectionDialog();
                dialog.setVisible(true);
                url = dialog.getUrl().getText();
                username = dialog.getUsrname().getText();
                password = dialog.getPwd().getText();
        //            JOptionPane.showMessageDialog(getClass(), e);
        //        DriverManager.registerDriver(new OracleDriver());
        connection = DriverManager.getConnection(url, username, password);
        //        conn.setAutoCommit(false);
        return connection;
    }

    public Connection getConn() {
        return connection;
    }

    public void getDBName(Connection conn, FrontEndForm form) throws SQLException {
        form.setTitle(form.getTitle() + "   " + conn.getMetaData().getDatabaseProductName() + ":" +
                      conn.getMetaData().getUserName());
    }

    public void exec(Connection conn, JTextArea sqlFile) throws SQLException {
        stmt = conn.createStatement();
        stmt.execute(sqlFile.getText());
    }

    public void getResultSet(Statement stat, JComponent console, JComponent table) {
        try {
            DatabaseMetaData dbMeta = connection.getMetaData();
            ResultSet rs = stat.getResultSet();
            ResultSetMetaData meta = rs.getMetaData();
            DefaultTableModel tableModel = new DefaultTableModel(0, 0);
            while (rs.next()) {
                for (int i = 1; i <= meta.getColumnCount(); i++) {
                    result.put(meta.getColumnLabel(i), rs.getObject(i));                    
                }
                
                System.out.println(result.values().toString());
                if (tableModel.getColumnCount() == 0) {
                    addTableColumn(tableModel, result);
                }
                addTableRow(tableModel);                
                getQueryResult(console, table);
            }
            
            ((JTable) table).setModel(tableModel);
        } catch (SQLException e) {
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

    private void getColumnLabels() {
        Object[] keys = new Object[result.size()];
        int index = 0;
        for (Map.Entry<Object, Object> entry : result.entrySet()) {
            keys[index] = entry.getKey();
            index++;
        }
//        return keys;
    }

    private void getQueryResult(JComponent console, JComponent table) {
//        if (((JTextArea) console).getText() != "") {
//            ((JTextArea) console).append(Arrays.toString(result.keySet().toArray()) + "\n");
//        }
        ((JTextArea) console).append(Arrays.toString(result.values().toArray()) + "\n");
//        Object[] values = result.values().toArray();
//        for (Object value : values) {
//            if (value != null) {
//                ((JTextArea) console).append(value.toString() + "\t");        
//            }
//        }
    }
}
