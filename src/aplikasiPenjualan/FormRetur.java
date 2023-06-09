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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author m.subhan ramdhani
 */
public class FormRetur extends javax.swing.JFrame {
    public static String form;
    /**
     * Creates new form FormRetur
     */
    public FormRetur() {
        initComponents();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        Date date = new Date();
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
        
        txTanggal.setText(s.format(date));
        Locale locale = new Locale ("id","ID");
        Locale.setDefault(locale);
        
//        loadData();
        autonumber();
        
        

    }
    
    private void autonumber(){
        try {
            Connection c = koneksi.getKoneksi();
            Statement s = c.createStatement();
            String sql = "SELECT * FROM retur ORDER BY kode DESC";
            ResultSet r = s.executeQuery(sql);
            if (r.next()) {
                String kode = r.getString("kode").substring(2);
                String RT = "" +(Integer.parseInt(kode)+1);
                String Nol = "";
                
                if(RT.length()==1)
                {Nol = "000";}
                else if(RT.length()==2)
                {Nol = "00";}
                else if(RT.length()==3)
                {Nol = "0";}
                else if(RT.length()==4)
                {Nol = "";}
                txKode.setText("RT" + Nol + RT);
            } else {
                txKode.setText("RT0001");
            }
            r.close();
            s.close();
            clear();
        } catch (Exception e) {
            System.out.println("autonumber error");
        }

    }
    
//    public void loadData(){
//    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
//    model.getDataVector().removeAllElements();
//    model.fireTableDataChanged();
//
//    try {
//        Connection c = koneksi.getKoneksi();
//        Statement s = c.createStatement();
//
//        String sql = "SELECT * FROM retur ;";
//        ResultSet r = s.executeQuery(sql);
//
//        while (r.next()) {
//            Object[] o = new Object[5];
//            o [0] = r.getString("kode");
//            o [1] = r.getString("tanggal");
//            o [2] = r.getString("customer");
//            o [3] = r.getString("IDBrg");
//            o [4] = r.getString("jumlah");
//
//            model.addRow(o);
//        }
//        r.close();
//        s.close();
//    } catch (Exception e) {
//        System.out.println("terjadi kesalahan load data");
//        System.out.println("Error : "+e);
//    }
//    }
    
    public void clear(){
        txIdBrg.setText("");
        txNamaBrg.setText("");
        txJumlah.setText("");
        txKet.setText("");
    }
    
  
        public void idBarangCek(){
        try {
            Connection c = koneksi.getKoneksi();
            Statement s = c.createStatement();
            String sql = "SELECT * FROM barang WHERE IDBrg = \"" +txIdBrg.getText()+"\"";
            
            ResultSet r = s.executeQuery(sql);
            if(r.next()){
                String nama = r.getString("namaBrg");
                txNamaBrg.setText(nama);
                txJumlah.requestFocus();
            } else {
                JOptionPane.showMessageDialog(null,"ID Barang Salah !!");
            }
            
        }catch (Exception e){
            System.out.println("IDbarang Cek Error : "+e);
        }
    }
        
        public void barangmasuk(){
        String kode = txKode.getText();
        String tanggal = txTanggal.getText();
        String idbarang = txIdBrg.getText();
        String jumlah = txJumlah.getText();
        String ket = txKet.getText();
        
        try {
            Connection c = koneksi.getKoneksi();
            String sql = "INSERT INTO retur VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement p = c.prepareStatement(sql);
            p.setString(1, kode);
            p.setString(2, tanggal);
            p.setString(3, idbarang);
            p.setString(4, jumlah);
            p.setString(5, "0");
            p.setString(6, ket);
            p.executeUpdate();
            p.close();
            TabelStok.btnLoad.doClick();
            JOptionPane.showMessageDialog(null,"Data Tersimpan");
        } catch (Exception e) {
            System.out.println(" error" + e);
        }
        clear();
        autonumber();
        }
        
        public void barangkeluar(){
        String kode = txKode.getText();
        String tanggal = txTanggal.getText();
        String idbarang = txIdBrg.getText();
        String jumlah = txJumlah.getText();
        String ket = txKet.getText();
        
        try {
            Connection c = koneksi.getKoneksi();
            String sql = "INSERT INTO retur VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement p = c.prepareStatement(sql);
            p.setString(1, kode);
            p.setString(2, tanggal);
            p.setString(3, idbarang);
            p.setString(4, "0");
            p.setString(5, jumlah);
            p.setString(6, ket);
            p.executeUpdate();
            p.close();
            TabelStok.btnLoad.doClick();
            JOptionPane.showMessageDialog(null,"Data Tersimpan");
        } catch (Exception e) {
            System.out.println(" error" + e);
        }
//        loadData();
        clear();
        autonumber();
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
        panelJudul = new javax.swing.JPanel();
        Judul = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txKode = new javax.swing.JTextField();
        txTanggal = new javax.swing.JTextField();
        btnSimpan = new javax.swing.JButton();
        txIdBrg = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txNamaBrg = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txJumlah = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        txKet = new javax.swing.JTextField();
        btn_CariBarang = new javax.swing.JButton();
        cbStok = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(204, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        panelJudul.setBackground(new java.awt.Color(255, 255, 255));
        panelJudul.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        Judul.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        Judul.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/barang-icon.png"))); // NOI18N
        Judul.setText("  Penyesuaian Stok");

        javax.swing.GroupLayout panelJudulLayout = new javax.swing.GroupLayout(panelJudul);
        panelJudul.setLayout(panelJudulLayout);
        panelJudulLayout.setHorizontalGroup(
            panelJudulLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelJudulLayout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(Judul)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelJudulLayout.setVerticalGroup(
            panelJudulLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Judul)
        );

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("Kode Retur         :");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel5.setText("Tanggal             :");

        txKode.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txKode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txKodeActionPerformed(evt);
            }
        });

        txTanggal.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txTanggal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txTanggalActionPerformed(evt);
            }
        });

        btnSimpan.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/simpan-icon.png"))); // NOI18N
        btnSimpan.setText("Simpan ");
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });

        txIdBrg.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txIdBrg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txIdBrgActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel13.setText("ID Barang          :");

        txNamaBrg.setBackground(new java.awt.Color(204, 255, 255));
        txNamaBrg.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txNamaBrg.setBorder(null);
        txNamaBrg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txNamaBrgActionPerformed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel14.setText("Nama Barang      :");

        txJumlah.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txJumlah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txJumlahActionPerformed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel15.setText("Jumlah              :");

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel16.setText("Keterangan        :");

        txKet.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txKet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txKetActionPerformed(evt);
            }
        });

        btn_CariBarang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cari-icon.png"))); // NOI18N
        btn_CariBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_CariBarangActionPerformed(evt);
            }
        });

        cbStok.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        cbStok.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Barang Masuk", "Barang Keluar" }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelJudul, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(txTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txIdBrg, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel14)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txNamaBrg, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel15)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(txJumlah, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(btnSimpan)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel16)
                                        .addGap(18, 18, 18)
                                        .addComponent(txKet, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel1)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(txKode, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(cbStok, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_CariBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(70, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(panelJudul, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txKode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel13)
                        .addComponent(txIdBrg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btn_CariBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(txNamaBrg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addComponent(cbStok, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(txJumlah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(txKet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addComponent(btnSimpan)
                .addGap(352, 352, 352))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 1, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 502, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txJumlahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txJumlahActionPerformed
        // TODO add your handling code here:
        btnSimpan.requestFocus();
    }//GEN-LAST:event_txJumlahActionPerformed

    private void txNamaBrgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txNamaBrgActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txNamaBrgActionPerformed

    private void txIdBrgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txIdBrgActionPerformed
        // TODO add your handling code here:
        idBarangCek();
    }//GEN-LAST:event_txIdBrgActionPerformed

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        // TODO add your handling code here:
        String jenis = cbStok.getSelectedItem().toString();
        
        if (jenis.equals("Barang Masuk")){
            barangmasuk();
        } else if (jenis.equals("Barang Keluar")){
            barangkeluar();
        }
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void txTanggalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txTanggalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txTanggalActionPerformed

    private void txKodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txKodeActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_txKodeActionPerformed

    private void txKetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txKetActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txKetActionPerformed

    private void btn_CariBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_CariBarangActionPerformed
        // TODO add your handling code here:
        form = "retur";
        FormPenjualan.form = "retur";
        new ListDataBarang().setVisible(true);
    }//GEN-LAST:event_btn_CariBarangActionPerformed

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
            java.util.logging.Logger.getLogger(FormRetur.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormRetur.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormRetur.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormRetur.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormRetur().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Judul;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JButton btn_CariBarang;
    private javax.swing.JComboBox<String> cbStok;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel panelJudul;
    public static javax.swing.JTextField txIdBrg;
    private javax.swing.JTextField txJumlah;
    private javax.swing.JTextField txKet;
    private javax.swing.JTextField txKode;
    public static javax.swing.JTextField txNamaBrg;
    private javax.swing.JTextField txTanggal;
    // End of variables declaration//GEN-END:variables
}
