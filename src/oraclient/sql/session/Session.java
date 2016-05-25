package oraclient.sql.session;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import java.util.Iterator;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import javax.swing.table.TableModel;

import oraclient.sql.conns.DBConnection;

public class Session {
    public Session() {        
    }
    
    public TableModel getSessionInfo() {
        List<Connection> conns = DBConnection.getConnections();
        if (conns != null) {
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("База данных");
            model.addColumn("Пользователь");
            for (Iterator<Connection> it = conns.iterator(); it.hasNext();) {
                try {
                    DatabaseMetaData meta = it.next().getMetaData();
                    String dbName = meta.getDatabaseProductName();
                    String userName = meta.getUserName();
                    model.addRow(new Object[] {dbName, userName});
                } catch (SQLException e) {
                }
            }
            return model;
        }
        return null;
    }
}
