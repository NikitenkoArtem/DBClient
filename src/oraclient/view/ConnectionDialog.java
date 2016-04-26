
package oraclient.view;


import java.sql.*;

import java.util.Enumeration;

import java.util.Locale;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JDialog;

import javax.swing.JPasswordField;
import javax.swing.JTextField;

import oracle.jdbc.OracleDriver;

import oraclient.sql.drivers.OracleDrivers;

/**
 *
 * @author Price
 */
public class ConnectionDialog extends JDialog {

    /** Creates new form FileDialog */
    public ConnectionDialog() {
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    private void initComponents() {//GEN-BEGIN:initComponents

        connect = new javax.swing.JButton();
        cancel = new javax.swing.JButton();
        usrname = new javax.swing.JTextField();
        usernameLbl = new javax.swing.JLabel();
        pwdLbl = new javax.swing.JLabel();
        pwd = new javax.swing.JPasswordField();
        urlLbl = new javax.swing.JLabel();
        url = new javax.swing.JTextField();

        setModal(true);
        setResizable(false);

        connect.setText("����������");
        connect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                connectActionPerformed(evt);
            }
        });

        cancel.setText("������");
        cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelActionPerformed(evt);
            }
        });

        usernameLbl.setText("��� ������������");

        pwdLbl.setText("������");

        urlLbl.setText("URL ����������");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(connect)
                        .addGap(7, 7, 7)
                        .addComponent(cancel))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(usernameLbl, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(pwdLbl, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(urlLbl, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(usrname, javax.swing.GroupLayout.DEFAULT_SIZE, 203, Short.MAX_VALUE)
                            .addComponent(pwd)
                            .addComponent(url))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(usernameLbl)
                    .addComponent(usrname))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pwdLbl)
                    .addComponent(pwd))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(urlLbl)
                    .addComponent(url))
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(connect)
                    .addComponent(cancel))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }//GEN-END:initComponents

    private void connectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_connectActionPerformed
        status = true;
        setVisible(false);
    }//GEN-LAST:event_connectActionPerformed

    private void cancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelActionPerformed
        status = false;
        setVisible(false);
    }//GEN-LAST:event_cancelActionPerformed

    public JPasswordField getPwd() {
        return pwd;
    }

    public JTextField getUrl() {
        return url;
    }

    public JTextField getUsrname() {
        return usrname;
    }

    public boolean isStatus() {
        return status;
    }
    private boolean status = false;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancel;
    private javax.swing.JButton connect;
    private javax.swing.JPasswordField pwd;
    private javax.swing.JLabel pwdLbl;
    private javax.swing.JTextField url;
    private javax.swing.JLabel urlLbl;
    private javax.swing.JLabel usernameLbl;
    private javax.swing.JTextField usrname;
    // End of variables declaration//GEN-END:variables

}
