package oraclient.sql.conns;

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

import oraclient.view.ConnectionDialog;
import oraclient.view.FrontEndForm;


public class DBConnection {
    private static Connection connection;
//    private static Statement stmt;
    private static String url = "jdbc:oracle:thin:@localhost:1521:xe";
    private static String username = "hr";
    private static String password = "hr";
    //    private static String url;
    //    private static String username;
    //    private static String password;
    private Map<Object, Object> result = new HashMap<>();
    private boolean connected = false;

    public static Connection getConnection() throws SQLException {
        //                ConnectionDialog dialog = new ConnectionDialog();
        //                dialog.setVisible(true);
        //                url = dialog.getUrl().getText();
        //                username = dialog.getUsrname().getText();
        //                password = dialog.getPwd().getText();
        //            JOptionPane.showMessageDialog(getClass(), e);
        //        DriverManager.registerDriver(new OracleDriver());
        connection = DriverManager.getConnection(url, username, password);
        //        conn.setAutoCommit(false);
        return connection;
    }

    public void getDBName(Connection conn, FrontEndForm form) throws SQLException {
        form.setTitle(form.getTitle() + "   " + conn.getMetaData().getDatabaseProductName() + ":" +
                      conn.getMetaData().getUserName());
    }

    public Statement exec(Connection conn, JComponent text) throws SQLException {
//        stmt = conn.createStatement();
//        stmt.execute(sqlFile.getText());
        Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        //            stmt.executeQuery("select * from departments");
        stmt.executeUpdate("drop table test");
        stmt.execute("create table test(id int, name varchar2(5), price int)");
        stmt.executeUpdate("insert into test values(1, 'hello', 12345)");
        stmt.executeQuery("select * from test");
        //            stmt.execute(area.getTextAreas().iterator().next().getText());
        return stmt;
    }

    public void getResultSet(Statement stmt, JComponent console, JComponent table) {
        try {
            //            getDBMetaData();
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
            ((JTextArea) console).append("\n");
        } catch (SQLException e) {            
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
        ((JTextArea) console).append("\n");
    }

    private void println(JComponent console) {
        ((JTextArea) console).append(Arrays.toString(result.values().toArray()) + "\n");
    }

    private void showWarnings(JComponent console, Statement stat) throws SQLException {
        SQLWarning warning = stat.getWarnings();
        while (warning != null) {
            ((JTextArea) console).append(warning.getCause() + "\n");
            warning.getNextWarning();
        }
    }

    private void showExceptions(JComponent console, SQLException ex) {
        Iterator<Throwable> it = ex.iterator();
        while (it.hasNext()) {
            ((JTextArea) console).append(ex.getSQLState() + "\n");
            ((JTextArea) console).append(ex.getErrorCode() + "\n");
        }
    }

    private void getDBMetaData() throws SQLException {
        DatabaseMetaData dbMeta = connection.getMetaData();
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
