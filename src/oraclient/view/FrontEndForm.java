
package oraclient.view;


import java.awt.Component;
import java.awt.event.KeyEvent;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.DefaultEditorKit;
import javax.swing.undo.UndoManager;

import oraclient.component.ClientArea;
import oraclient.component.ClientUndoManager;

import oraclient.io.ClientOutputStream;
import oraclient.io.NewFile;

import oraclient.sql.conns.DBConnection;
import oraclient.sql.drivers.LoadDrivers;


/**
 *
 * @author Price
 */
public class FrontEndForm extends javax.swing.JFrame {

    private ClientArea area;
    private DBConnection oracle;
    private Connection conn;
    private NewFile file;
    private ClientUndoManager undoMgr;

    /** Creates new form Main */
    public FrontEndForm() {
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    private void initComponents() {//GEN-BEGIN:initComponents

        tabPane = new javax.swing.JTabbedPane();
        outputArea = new javax.swing.JTabbedPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        console = new javax.swing.JTextArea();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTree2 = new javax.swing.JTree();
        MainMenu = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        connectMenuItem = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        newFileMenuItem = new javax.swing.JMenuItem();
        openFileMenuItem = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        closeFileMenuItem = new javax.swing.JMenuItem();
        closeAllMenuItem = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        saveFileMenuItem = new javax.swing.JMenuItem();
        saveAsMenuItem = new javax.swing.JMenuItem();
        saveAllMenuItem = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        exitMenuItem = new javax.swing.JMenuItem();
        editMenu = new javax.swing.JMenu();
        undoMenuItem = new javax.swing.JMenuItem();
        redoMenuItem = new javax.swing.JMenuItem();
        jSeparator6 = new javax.swing.JPopupMenu.Separator();
        cutMenuItem = new javax.swing.JMenuItem(new DefaultEditorKit.CutAction());
        copyMenuItem = new javax.swing.JMenuItem(new DefaultEditorKit.CopyAction());
        pasteMenuItem = new javax.swing.JMenuItem(new DefaultEditorKit.PasteAction());
        runMenu = new javax.swing.JMenu();
        runScriptMenuItem = new javax.swing.JMenuItem();
        reportMenu = new javax.swing.JMenu();
        sessionMenu = new javax.swing.JMenu();
        getSessionsMenuItem = new javax.swing.JMenuItem();
        closeSessionsMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("DBClient");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setMinimumSize(new java.awt.Dimension(560, 420));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        tabPane.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        outputArea.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        console.setColumns(20);
        console.setRows(5);
        jScrollPane2.setViewportView(console);

        outputArea.addTab("tab2", jScrollPane2);

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(table);

        outputArea.addTab("tab2", jScrollPane1);

        jTabbedPane2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        jScrollPane4.setViewportView(jTree2);

        jTabbedPane2.addTab("tab1", jScrollPane4);

        fileMenu.setText("����");

        connectMenuItem.setText("�����������...");
        connectMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                connectMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(connectMenuItem);
        fileMenu.add(jSeparator1);

        newFileMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        newFileMenuItem.setText("�����...");
        newFileMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newFileMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(newFileMenuItem);

        openFileMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        openFileMenuItem.setText("�������...");
        openFileMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openFileMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(openFileMenuItem);
        fileMenu.add(jSeparator2);

        closeFileMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_W, java.awt.event.InputEvent.CTRL_MASK));
        closeFileMenuItem.setText("�������");
        closeFileMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeFileMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(closeFileMenuItem);

        closeAllMenuItem.setText("������� ���");
        closeAllMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeAllMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(closeAllMenuItem);
        fileMenu.add(jSeparator3);

        saveFileMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        saveFileMenuItem.setText("���������");
        saveFileMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveFileMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(saveFileMenuItem);

        saveAsMenuItem.setText("��������� ���...");
        saveAsMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveAsMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(saveAsMenuItem);

        saveAllMenuItem.setText("��������� ���");
        saveAllMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveAllMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(saveAllMenuItem);
        fileMenu.add(jSeparator4);

        exitMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.ALT_MASK));
        exitMenuItem.setText("�����");
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(exitMenuItem);

        MainMenu.add(fileMenu);

        editMenu.setText("��������������");

        undoMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Z, java.awt.event.InputEvent.CTRL_MASK));
        undoMenuItem.setText("��������");
        undoMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                undoMenuItemActionPerformed(evt);
            }
        });
        editMenu.add(undoMenuItem);

        redoMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Y, java.awt.event.InputEvent.CTRL_MASK));
        redoMenuItem.setText("�������");
        redoMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                redoMenuItemActionPerformed(evt);
            }
        });
        editMenu.add(redoMenuItem);
        editMenu.add(jSeparator6);

        cutMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.CTRL_MASK));
        cutMenuItem.setMnemonic(KeyEvent.VK_T);
        cutMenuItem.setText("��������");
        editMenu.add(cutMenuItem);

        copyMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_MASK));
        copyMenuItem.setMnemonic(KeyEvent.VK_C);
        copyMenuItem.setText("����������");
        copyMenuItem.setToolTipText("");
        editMenu.add(copyMenuItem);

        pasteMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, java.awt.event.InputEvent.CTRL_MASK));
        pasteMenuItem.setMnemonic(KeyEvent.VK_P);
        pasteMenuItem.setText("��������");
        editMenu.add(pasteMenuItem);

        MainMenu.add(editMenu);

        runMenu.setText("���������");

        runScriptMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F5, 0));
        runScriptMenuItem.setText("��������� ��������");
        runScriptMenuItem.setEnabled(false);
        runScriptMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                runScriptMenuItemActionPerformed(evt);
            }
        });
        runMenu.add(runScriptMenuItem);

        MainMenu.add(runMenu);

        reportMenu.setText("�����");
        MainMenu.add(reportMenu);

        sessionMenu.setText("������");

        getSessionsMenuItem.setText("������ ������");
        sessionMenu.add(getSessionsMenuItem);

        closeSessionsMenuItem.setText("��������� ��� ������");
        sessionMenu.add(closeSessionsMenuItem);

        MainMenu.add(sessionMenu);

        setJMenuBar(MainMenu);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(outputArea, javax.swing.GroupLayout.DEFAULT_SIZE, 485, Short.MAX_VALUE)
                    .addComponent(tabPane)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(tabPane)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(outputArea, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jTabbedPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 354, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }//GEN-END:initComponents

    
    private void newFileMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newFileMenuItemActionPerformed
        String filePath = fileLocation();
        //        dialog.getOkButton().addActionListener(new ActionListener(){
        //            @Override
        //            public void actionPerformed(ActionEvent e) {
        //                newFile(filePath);
        //            }
        //        });
        newFile(filePath);
        runScriptMenuItem.setEnabled(true);
    }//GEN-LAST:event_newFileMenuItemActionPerformed

    private String fileLocation() {
        FileJDialog dialog = new FileJDialog();
        dialog.setVisible(true);
        String filePath = dialog.getFileLocation().getText() + dialog.getFileName().getText();
        return filePath;
    }

    private void newFile(String filePath) {
        file = new NewFile(filePath);
        area = new ClientArea();
        JTextArea textArea = new JTextArea();
        area.addJTextArea(textArea, file);
        undoMgr = new ClientUndoManager();
        undoMgr.addUndoManager(textArea, new UndoManager());
        tabPane.addTab(file.getAbsolutePath(), add(area.find(file.getAbsoluteFile())));
    }

    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
        formWindowClosing(null);
    }//GEN-LAST:event_exitMenuItemActionPerformed

    private void saveFileMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveFileMenuItemActionPerformed
        String title = tabPane.getTitleAt(tabPane.getSelectedIndex());
        if(!file.isSaved(file.find(title))) {
            file.save(file.find(title), area.find(file.find(title)));
        }
    }//GEN-LAST:event_saveFileMenuItemActionPerformed

    private void closeFileMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeFileMenuItemActionPerformed
        saveFileMenuItemActionPerformed(evt);
        tabPane.remove(tabPane.getSelectedIndex());
    }//GEN-LAST:event_closeFileMenuItemActionPerformed

    private void connectMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_connectMenuItemActionPerformed
        new LoadDrivers("oracle.jdbc.OracleDriver");
        oracle = new DBConnection();
        try {
            conn = DBConnection.getConnection();
            oracle.getDBName(conn, this);
            Statement stmt = oracle.exec(conn, null);
            oracle.getResultSet(stmt, console, table);
        } catch (SQLException e) {
            e.printStackTrace();
        }
            
    }//GEN-LAST:event_connectMenuItemActionPerformed

    private void undoMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_undoMenuItemActionPerformed
        Component component = tabPane.getComponentAt(tabPane.getSelectedIndex());
//        Component component = tabPane.getTabComponentAt(tabPane.getSelectedIndex());
        System.out.println(component);
        System.out.println("----------------------------------------------------------------");
        System.out.println(undoMgr.getUndoMgrs().toString());
        System.out.println("----------------------------------------------------------------");
//        if(undoMgr.find((JTextArea) component).canUndo()) {
            undoMgr.find((JTextArea) component).undo();
//        }
        
//        UndoManager undo = undoMgr.find((JTextArea) component);
//        if(undo.canUndo()) {
//            undo.undo();
//        }
    }//GEN-LAST:event_undoMenuItemActionPerformed

    private void redoMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_redoMenuItemActionPerformed
        UndoManager undo = undoMgr.find((JTextArea) tabPane.getTabComponentAt(tabPane.getSelectedIndex()));
        if(undo.canRedo()) {
            undo.redo();
        }
    }//GEN-LAST:event_redoMenuItemActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        saveAllMenuItemActionPerformed(null);
        if (oracle != null) {
            oracle.close();
        }
        System.exit(0);
    }//GEN-LAST:event_formWindowClosing

    private void runScriptMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_runScriptMenuItemActionPerformed
        //        if(!connected)
        //connectActionPerformed(evt);
        //            oracle.exec(oracle.getConn(), area.getTextAreas().iterator().next());
        
//        try (Connection conn = DBConnection.getConnection()) {
//        Statement stmt = null;
        try {
            oracle.getDBName(conn, this);
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        //            stmt.executeQuery("select * from departments");
//            stmt.execute(area.getTextAreas().iterator().next().getText());
            oracle.getResultSet(stmt, console, table);
        } catch (SQLException e) {
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
            }
//            if(stmt != null) {
//                try {
//                    stmt.close();
//                } catch (SQLException e) {
//                }
//            }
        }
    }//GEN-LAST:event_runScriptMenuItemActionPerformed

    private void openFileMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openFileMenuItemActionPerformed
        FileJDialog dialog = new FileJDialog();
        dialog.setVisible(true);
        String filePath = dialog.getFileLocation().getText();
        file.openFile(filePath);
    }//GEN-LAST:event_openFileMenuItemActionPerformed

    private void closeAllMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeAllMenuItemActionPerformed
        saveAllMenuItemActionPerformed(evt);
        tabPane.removeAll();
    }//GEN-LAST:event_closeAllMenuItemActionPerformed

    private void saveAsMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveAsMenuItemActionPerformed
        String title = tabPane.getTitleAt(tabPane.getSelectedIndex());
        File f = new File(fileLocation());
        file.saveAs(f, area.find(file.find(title)));
    }//GEN-LAST:event_saveAsMenuItemActionPerformed

    private void saveAllMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveAllMenuItemActionPerformed
        if (!file.isSavedAll()) {
//            file.saveAll(tabPane, area.getTextAreas());
        }
    }//GEN-LAST:event_saveAllMenuItemActionPerformed
  
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrontEndForm.class.getName()).log(java.util.logging.Level.SEVERE, null,
                                                                                 ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrontEndForm.class.getName()).log(java.util.logging.Level.SEVERE, null,
                                                                                 ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrontEndForm.class.getName()).log(java.util.logging.Level.SEVERE, null,
                                                                                 ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrontEndForm.class.getName()).log(java.util.logging.Level.SEVERE, null,
                                                                                 ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrontEndForm().setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuBar MainMenu;
    private javax.swing.JMenuItem closeAllMenuItem;
    private javax.swing.JMenuItem closeFileMenuItem;
    private javax.swing.JMenuItem closeSessionsMenuItem;
    private javax.swing.JMenuItem connectMenuItem;
    private javax.swing.JTextArea console;
    private javax.swing.JMenuItem copyMenuItem;
    private javax.swing.JMenuItem cutMenuItem;
    private javax.swing.JMenu editMenu;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenuItem getSessionsMenuItem;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JPopupMenu.Separator jSeparator6;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTree jTree2;
    private javax.swing.JMenuItem newFileMenuItem;
    private javax.swing.JMenuItem openFileMenuItem;
    private javax.swing.JTabbedPane outputArea;
    private javax.swing.JMenuItem pasteMenuItem;
    private javax.swing.JMenuItem redoMenuItem;
    private javax.swing.JMenu reportMenu;
    private javax.swing.JMenu runMenu;
    private javax.swing.JMenuItem runScriptMenuItem;
    private javax.swing.JMenuItem saveAllMenuItem;
    private javax.swing.JMenuItem saveAsMenuItem;
    private javax.swing.JMenuItem saveFileMenuItem;
    private javax.swing.JMenu sessionMenu;
    private javax.swing.JTabbedPane tabPane;
    private javax.swing.JTable table;
    private javax.swing.JMenuItem undoMenuItem;
    // End of variables declaration//GEN-END:variables
}
