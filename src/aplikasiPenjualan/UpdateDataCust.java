/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplikasiPenjualan;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author Dhani
 */
public class UpdateDataCust extends javax.swing.JFrame {
    
/*    public void autonumber(){
        try {
            Connection c = koneksi.getKoneksi();
            Statement s = c.createStatement();
            String sql = "SELECT * FROM customer ORDER BY IDCustomer";
            ResultSet r = s.executeQuery(sql);
            if (r.next()) {
                String IDCust = r.getString("IDCustomer").substring(3);
                String CS = "" +(Integer.parseInt(IDCust)+1);
                String Nol = "";
                
                if(CS.length()==1)
                {Nol = "000";}
                else if(CS.length()==2)
                {Nol = "00";}
                else if(CS.length()==3)
                {Nol = "0";}
                else if(CS.length()==4)
                {Nol = "";}
                txIDCust.setText("CS" + Nol + CS);
                txNamaCust.requestFocus();
            } else {
                txIDCust.setText("CS0001");
                txNamaCust.requestFocus();
            }
            r.close();
            s.close();
        } catch (Exception e) {
            System.out.println("autonumber error");
        }
    } */
    
    public void autonumber(){
        try {
            Connection c = koneksi.getKoneksi();
            Statement s = c.createStatement();
            String IDCust = "CS0001";
            boolean lanjut = true;
            do {
                String sql = "SELECT * FROM customer WHERE IDCust = '"+IDCust+ "'" ;
                ResultSet r = s.executeQuery(sql);
                if (r.next()) {
                IDCust = r.getString("IDCust").substring(3);
                String CS = "" +(Integer.parseInt(IDCust)+1);
                String Nol = "";
                if(CS.length()==1)
                {Nol = "000";}
                else if(CS.length()==2)
                {Nol = "00";}
                else if(CS.length()==3)
                {Nol = "0";}
                else if(CS.length()==4)
                {Nol = "";}
                IDCust = "CS" + Nol + CS;
                lanjut = true ;
                } else {
                    lanjut = false;
                    txIDCust.setText(IDCust);
                    txNamaCust.requestFocus();   
                    r.close();
                    s.close();
                }
            } while(lanjut);
        } catch (Exception e) {
            System.out.println("autonumber error" + e);
        }
    }
    public void clear(){
        txNamaCust.setText("");
        txIDCust.setText("");
        txAlamat.setText("");
        txNoTelp.setText("");
        txIDCust.requestFocus();
    }
    public void edit(){
        String id = txIDCust.getText();
        String nama = txNamaCust.getText();
        String Alamat = txAlamat.getText();
        String telp = txNoTelp.getText();
        
        
        try {
            Connection c = koneksi.getKoneksi();
            String sql = "UPDATE customer SET namaCust = ?, alamat = ?, noTelp = ? WHERE IDCust = ? ";
            PreparedStatement p = c.prepareStatement(sql);
            p.setString(1, nama);
            p.setString(2, Alamat);
            p.setString(3, telp);
            p.setString(4, id);
            p.executeUpdate();
            p.close();
            JOptionPane.showMessageDialog(null, "Data Telah di Ubah, Silahkan Load Data !!");
        } catch (Exception e) {
            System.out.println("Gagal mengedit Data !");
            System.out.println("Error : " +e);
        }finally{
            DataBarang a = new DataBarang();
            a.loadData();
            this.dispose();
        }
    }
    
    public void tambah(){
        String id = txIDCust.getText();
        String nama = txNamaCust.getText();
        String Alamat = txAlamat.getText();
        String telp = txNoTelp.getText();

        try {
            Connection c = koneksi.getKoneksi();
            String sql = "INSERT INTO customer VALUES (?, ?, ?, ?)";
            PreparedStatement p = c.prepareStatement(sql);
            p.setString(1, id);
            p.setString(2, nama);
            p.setString(3, Alamat);
            p.setString(4, telp);
            p.executeUpdate();
            p.close();
            JOptionPane.showMessageDialog(null, "Data Tersimpan, Silahkan Load Data !!");
        } catch (Exception e) {
            System.out.println("Terjadi Kesalahan Menambah Data");
            System.out.println("Error : " +e);
            JOptionPane.showMessageDialog(null, "Gagal menambah data !!");
        }finally{
            clear();
            autonumber();
            DataBarang a = new DataBarang();
            a.loadData();
            
        }
    }
    /**
     * Creates new form UpdateDataCust
     */
    public UpdateDataCust() {
        initComponents();
        autonumber();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        btnSimpan.setVisible(false);
        btnTambah.setVisible(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        PanelInputDataBarang = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txIDCust = new javax.swing.JTextField();
        txNamaCust = new javax.swing.JTextField();
        txNoTelp = new javax.swing.JTextField();
        txAlamat = new javax.swing.JTextField();
        btnBatal = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        btnSimpan = new javax.swing.JButton();
        btnTambah = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 51, 51));

        PanelInputDataBarang.setBackground(new java.awt.Color(204, 255, 255));
        PanelInputDataBarang.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.black));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setText("ID Customer");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel4.setText("Nama Customer");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel5.setText("Alamat");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel6.setText("No Telp");

        txIDCust.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txIDCust.setEnabled(false);

        txNamaCust.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txNamaCust.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txNamaCustActionPerformed(evt);
            }
        });

        txNoTelp.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        txAlamat.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txAlamat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txAlamatActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PanelInputDataBarangLayout = new javax.swing.GroupLayout(PanelInputDataBarang);
        PanelInputDataBarang.setLayout(PanelInputDataBarangLayout);
        PanelInputDataBarangLayout.setHorizontalGroup(
            PanelInputDataBarangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelInputDataBarangLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(PanelInputDataBarangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(PanelInputDataBarangLayout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txNoTelp, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PanelInputDataBarangLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(62, 62, 62)
                        .addComponent(txIDCust, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PanelInputDataBarangLayout.createSequentialGroup()
                        .addGroup(PanelInputDataBarangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(PanelInputDataBarangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txNamaCust, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txAlamat, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        PanelInputDataBarangLayout.setVerticalGroup(
            PanelInputDataBarangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelInputDataBarangLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelInputDataBarangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txIDCust, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(PanelInputDataBarangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txNamaCust, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(PanelInputDataBarangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txAlamat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(PanelInputDataBarangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txNoTelp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnBatal.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnBatal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cancel-icon.png"))); // NOI18N
        btnBatal.setText("Tutup");
        btnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBatalActionPerformed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(255, 51, 51));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnSimpan.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/simpan-icon.png"))); // NOI18N
        btnSimpan.setText("Simpan ");
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });
        jPanel2.add(btnSimpan, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        btnTambah.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnTambah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/tambah-icon.png"))); // NOI18N
        btnTambah.setText("Tambah");
        btnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahActionPerformed(evt);
            }
        });
        jPanel2.add(btnTambah, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(PanelInputDataBarang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(250, 250, 250)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBatal)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(PanelInputDataBarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnBatal)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(55, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txNamaCustActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txNamaCustActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txNamaCustActionPerformed

    private void btnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatalActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnBatalActionPerformed

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        // TODO add your handling code here:
        edit();
        clear();
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
        // TODO add your handling code here:
        tambah();
    }//GEN-LAST:event_btnTambahActionPerformed

    private void txAlamatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txAlamatActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txAlamatActionPerformed

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
            java.util.logging.Logger.getLogger(UpdateDataCust.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UpdateDataCust.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UpdateDataCust.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UpdateDataCust.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new UpdateDataCust().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanelInputDataBarang;
    private javax.swing.JButton btnBatal;
    public javax.swing.JButton btnSimpan;
    public javax.swing.JButton btnTambah;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    public javax.swing.JTextField txAlamat;
    public javax.swing.JTextField txIDCust;
    public javax.swing.JTextField txNamaCust;
    public javax.swing.JTextField txNoTelp;
    // End of variables declaration//GEN-END:variables
}
