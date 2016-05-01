package oraclient.sql.conns;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

import oraclient.view.FrontEndForm;


public class DBConnection {
    private static Connection connection;
    private static Statement stmt;
    private static String url = "jdbc:oracle:thin:@localhost:1521:xe";
    private static String username = "hr";//"scott";
    private static String password = "hr";//"tiger";
    private Map<Object, Object> result = new HashMap<>();
    private Map<String, String> values = new HashMap<>();
//    private List<String> column = new ArrayList<>();
//    private List<String> values = new ArrayList<>();
    private boolean connected = false;

    public static Connection getConnection() throws SQLException {
//        ConnectionDialog connDialog = new ConnectionDialog();
//        url = connDialog.getUrl().getText();
//        username = connDialog.getUsrname().getText();
//        password = connDialog.getPwd().getText();
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
        ArrayList<String> columnNames = new ArrayList<>();
        ArrayList<String> data = new ArrayList<>();
        try {
            DatabaseMetaData dbMeta = connection.getMetaData();
            ResultSet rs = stat.getResultSet();
            ResultSetMetaData meta = rs.getMetaData();        
            DefaultTableModel tableModel = new DefaultTableModel(0, 0);
            while (rs.next()) {
                for (int i = 1; i <= meta.getColumnCount(); i++) {
//                    column.add(meta.getColumnLabel(i));
//                    values.add(rs.getString(i));
//                    column.add(meta.getColumnLabel(i));
                    result.put(meta.getColumnLabel(i), rs.getObject(i));
//                    showResultSet(console, table);                    
                }
                ((JTextArea)console).append(Arrays.toString(getValues()) + '\n');
//                ((JTextArea)console).append(Arrays.toString(getValues()) + '\n');
                tableModel.addRow(result.values().toArray());
//                System.out.println(Arrays.toString(getColumns()));
//                System.out.println(Arrays.toString(getValues()));
//                ((JTextArea)console).append(result.toString() + '\n');
//                console.setText(column.toString() + "\n" + values.toString());
            }
//            tableModel.addColumn(Arrays.toString(getColumns()));
//            tableModel.addColumn(Arrays.toString(result.keySet().toArray()));
            
//            for (Object column : columns) {
//                ((JTextArea)console).append(Arrays.toString(columns));// + '\t');
//            }
//            for (Object column : getColumns(meta)) {
//                tableModel.addColumn(column);
//            }
//            Object[] array = result.entrySet().toArray();
//            Object[] keys = result.keySet().toArray();
//            Object[][] value = new Object[][] { result.values().toArray() };
//            for (Object key : getColumns()) {
//                tableModel.addColumn(key, getValues());
//            }
//            for (Object val : getValues()) {
//                tableModel.addRow(val);
//            }
//            tableModel.addRow(getValues());
//            for (Object row : array) {
//                tableModel.addRow(row);
//            }
//            tableModel.addRow(getValues());
//                tableModel.addColumn(result.entrySet());
//                Object[] rowData = new Object[]{result.entrySet()};
//                tableModel.addRow(rowData);
//                for (Map.Entry<Object, Object> entry : result.entrySet()) {
//                    System.out.println(entry.getKey() + " = " + entry.getValue());
//                }
//                ((JTable)table).setModel(new DefaultTableModel(data, columnNames));
                ((JTable)table).setModel(tableModel);
//                column.clear();
//                values.clear();
//            }
        } catch (SQLException e) {
        }
    }
    
    private Object[] getColumns() throws SQLException {
        Object[] keys = new Object[result.size()];
        int index = 0;
        for (Map.Entry<Object, Object> entry : result.entrySet()) {
            keys[index] = entry.getKey();
            index++;
        }
        return keys;
    }
    
    private Object[] getValues() {
        Object[] values = new Object[result.size()];
        int index = 0;
        for (Map.Entry<Object, Object> entry : result.entrySet()) {
            values[index] = entry.getValue();
            index++;
        }
        return values;
    }

    private void showResultSet(JComponent console, JComponent table) {
//        for (Map.Entry<Object, Object> entry : result.entrySet()) {
//            console.append(meta.getColumnLabel(i).toString() + "\n" + rs.getString(i).toString());
//            ((JTable)table).setColumnName(entry.getKey());
//        }
//        ((JTextArea)console).append(result.toString() + '\n');
    }
}
