package dbclient.swing.tree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.Enumeration;
import java.util.Properties;

import javax.swing.JTree;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.tree.DefaultMutableTreeNode;

import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;

import dbclient.sql.DBConnection;

public class DBMutableTreeNode extends DefaultMutableTreeNode {
    public DBMutableTreeNode() {
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

    public DefaultMutableTreeNode getDatabaseStructure(final Connection conn) throws SQLException {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode();
        DefaultMutableTreeNode tables = new DefaultMutableTreeNode("Таблицы");
        getDBEntities(conn, tables, "TABLE");
        root.add(tables);
        DefaultMutableTreeNode views = new DefaultMutableTreeNode("Представления");
        getDBEntities(conn, views, "VIEW");
        root.add(views);
        DefaultMutableTreeNode procedures = new DefaultMutableTreeNode("Процедуры");
        getDBProcedures(conn, procedures);
        root.add(procedures);
        DefaultMutableTreeNode functions = new DefaultMutableTreeNode("Функции");
        getDBFunctions(conn, functions);
        root.add(functions);
        DefaultMutableTreeNode triggers = new DefaultMutableTreeNode("Триггеры");
        getDBEntities(conn, triggers, "TRIGGER");
        root.add(triggers);
        DefaultMutableTreeNode users = new DefaultMutableTreeNode("Пользователи");
        getDBUsers(conn, users);
        root.add(users);
        return root;
    }

    public MutableTreeNode findNode(DefaultMutableTreeNode root, String nodeName) {
        Enumeration breadth = root.breadthFirstEnumeration();
        while (breadth.hasMoreElements()) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) breadth.nextElement();
            if (node.getUserObject().toString().equalsIgnoreCase(nodeName)) {
                return node;
            }
        }
        return null;
    }

    public void addNode(final Connection conn, final JTree tree, String nodeName) throws SQLException {
        if (conn != null) {
            DefaultTreeModel treeModel = (DefaultTreeModel) tree.getModel();
            DefaultMutableTreeNode root = (DefaultMutableTreeNode) treeModel.getRoot();
            DefaultMutableTreeNode dbStructure = getDatabaseStructure(conn);
            dbStructure.setUserObject(nodeName);
            root.add(dbStructure);
            treeModel.reload(root);
        } else {
            throw new SQLException("Соединение отсутсвует");
        }
    }

    public void removeNode(final JTree tree, String nodeName) {
        if (nodeName != null) {
            DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();
            MutableTreeNode found = findNode(root, nodeName);
            if (found != null) {
                model.removeNodeFromParent(found);
            }
            model.reload(root);
        }
    }
    
    public void removeDBTree(final JTree tree) {
        DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();
        root.removeAllChildren();
        model.reload(root);
    }
}
