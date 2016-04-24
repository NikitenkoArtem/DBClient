package oraclient.sql.conns;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.JOptionPane;

import javax.swing.JTextArea;

import oraclient.view.ConnectionDialog;
import oraclient.view.FrontEndForm;


public class OracleConnection implements Connections {
    private static Connection conn;
    private static Statement stmt;
    private static ResultSet rs;
    private static String url;
    private static String username;
    private static String password;
    private Map<Object, Object> result;
    private boolean connected = false;

    public static Connection getConnection() throws SQLException {
        conn = DriverManager.getConnection(url, username, password);
        conn.setAutoCommit(false);
        return conn;
    }

    public void getConn() {
        ConnectionDialog connDialog = new ConnectionDialog();
        try {
            url = connDialog.getUrl().getText();
            username = connDialog.getUsrname().getText();
            password = connDialog.getPwd().getText();
            conn = getConnection();
        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(getClass(), e);
        }
    }

    public void getOracleName(Connection conn, FrontEndForm form) throws SQLException {
        form.setTitle(form.getTitle() + "   " + conn.getMetaData().getDatabaseProductName() + ":" +
                      conn.getMetaData().getUserName());
    }

    public void exec(Connection conn, JTextArea sqlFile) throws SQLException {
        stmt = conn.createStatement();
        stmt.execute(sqlFile.getText());
    }
    
    public void getResultSet() {
        try {            
            rs = stmt.getResultSet();
            result = new TreeMap();
            while (rs.next()) {
//                result.put(rs);
            }
//            console.setText(result.toString());
        } catch (SQLException e) {
        } catch (Exception e) {
        }
    }
}
