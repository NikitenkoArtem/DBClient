
package oraclient.view;

import java.sql.Connection;

import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import java.util.Map;

import javax.swing.JButton;

import javax.swing.table.DefaultTableModel;

import oraclient.sql.conns.DBConnection;

/**
 *
 * @author Price
 */
public class SessionJDialog extends javax.swing.JDialog {

    /** Creates new form SessionJDialog */
    public SessionJDialog(java.awt.Frame parent, boolean modal, DBConnection conn) {
        super(parent, modal);
        initComponents();
        if (conn != null) {
            Map<Integer, Connection> conns = conn.getConnections();
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("���� ������");
            model.addColumn("������������");
            for (Map.Entry<Integer, Connection> entry : conns.entrySet()) {
                try {
                    DatabaseMetaData meta = entry.getValue().getMetaData();
                    String dbName = meta.getDatabaseProductName();
                    String userName = meta.getUserName();
                    model.addRow(new Object[] {dbName, userName});
                } catch (SQLException e) {
                }
            }
            sessionTable.setModel(model);
        }
    }

    public JButton getTerminateButton() {
        return terminateButton;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    private void initComponents() {//GEN-BEGIN:initComponents

        jScrollPane = new javax.swing.JScrollPane();
        sessionTable = new javax.swing.JTable();
        terminateButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("������");
        setModal(true);
        setResizable(false);

        sessionTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane.setViewportView(sessionTable);

        terminateButton.setText("��������� ��� ������");
        terminateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                terminateButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 241, Short.MAX_VALUE)
                        .addComponent(terminateButton)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(terminateButton)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }//GEN-END:initComponents

    private void terminateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_terminateButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_terminateButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane;
    private javax.swing.JTable sessionTable;
    private javax.swing.JButton terminateButton;
    // End of variables declaration//GEN-END:variables

}
