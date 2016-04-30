package oraclient.sql.conns;

import java.sql.Array;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import javax.swing.JEditorPane;
import javax.swing.JTextArea;

import oracle.jdbc.OracleDriver;

import oraclient.view.ConnectionDialog;
import oraclient.view.FrontEndForm;


public class DBConnection {
    private static Connection conn;
    private static Statement stmt;
    private static String url = "jdbc:oracle:thin:@localhost:1521:xe";
    private static String username = "hr";//"scott";
    private static String password = "hr";//"tiger";
//    private Map<String, String> result;
    private List<String> column = new ArrayList<>();
    private List<String> values = new ArrayList<>();
    private boolean connected = false;

    public static Connection getConnection() throws SQLException {
//        ConnectionDialog connDialog = new ConnectionDialog();
//        url = connDialog.getUrl().getText();
//        username = connDialog.getUsrname().getText();
//        password = connDialog.getPwd().getText();
//            JOptionPane.showMessageDialog(getClass(), e);
        DriverManager.registerDriver(new OracleDriver());
        conn = DriverManager.getConnection(url, username, password);
//        conn.setAutoCommit(false);
        return conn;
    }

    public Connection getConn() {
        return conn;
    }

    public void getDBName(Connection conn, FrontEndForm form) throws SQLException {
        form.setTitle(form.getTitle() + "   " + conn.getMetaData().getDatabaseProductName() + ":" +
                      conn.getMetaData().getUserName());
    }

    public void exec(Connection conn, JTextArea sqlFile) throws SQLException {
        stmt = conn.createStatement();
        stmt.execute(sqlFile.getText());
    }
    
    public void getResultSet(Statement stat, JTextArea console) {
        try {
            DatabaseMetaData dbMeta = conn.getMetaData();
            ResultSet rs = stat.getResultSet();
            ResultSetMetaData meta = rs.getMetaData();        
            int count = meta.getColumnCount();
            while (rs.next()) {                
                for (int i = 1; i <= count; i++) {
                    column.add(meta.getColumnLabel(i));
                    values.add(rs.getString(i));                    
//                    console.append(meta.getColumnLabel(i).toString() + "\n" + rs.getString(i).toString());
                }
                console.setText(column.toString() + "\n" + values.toString());
                column.clear();
//                values.clear();
            }
        } catch (SQLException e) {
        }
    }
}
