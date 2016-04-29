package oraclient.sql.conns;

import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;

import java.sql.Struct;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import java.util.concurrent.Executor;

import javax.swing.JEditorPane;
import javax.swing.JOptionPane;

import javax.swing.JTextArea;

import oraclient.view.ConnectionDialog;
import oraclient.view.FrontEndForm;


public class OracleConnection {
    private static Connection conn;
    private static Statement stmt;
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
    
    public void getResultSet(Statement stat, JEditorPane console) {
        try {
            ResultSet rs = stat.getResultSet();
            ResultSetMetaData meta = rs.getMetaData();
            for (int i = 1; i <= meta.getColumnCount(); i++) {
                String label = meta.getColumnLabel(i);
                console.setText(label);
                while (rs.next()) {
                    console.setText(rs.getString(i));
                }
                System.out.println();
            }
        } catch (SQLException e) {
        } catch (Exception e) {
        }
    }
}
