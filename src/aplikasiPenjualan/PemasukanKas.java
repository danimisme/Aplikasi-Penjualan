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
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author msubh
 */
public class PemasukanKas extends javax.swing.JFrame {
private DefaultTableModel model;
    /**
     * Creates new form PemasukanKas
     */
    public PemasukanKas() {
        initComponents();
        Locale locale = new Locale ("id","ID");
        Locale.setDefault(locale);
        autonumber();
        loadData();
        total();
    }
    public void autonumber(){
        try {
            Connection c = koneksi.getKoneksi();
            Statement s = c.createStatement();
            String sql = "SELECT * FROM aruskas ORDER BY No DESC";
            ResultSet r = s.executeQuery(sql);
            if (r.next()) {
                String No = r.getString("No").substring(2);
                String TR = "" +(Integer.parseInt(No)+1);
                String Nol = "";
                
                if(TR.length()==1)
                {Nol = "000";}
                else if(TR.length()==2)
                {Nol = "00";}
                else if(TR.length()==3)
                {Nol = "0";}
                else if(TR.length()==4)
                {Nol = "";}
                txNoTr.setText("TR" + Nol + TR);
            } else {
                txNoTr.setText("TR0001");
            }
            r.close();
            s.close();
            java.util.Date date = new java.util.Date();
            SimpleDateFormat tgl = new SimpleDateFormat("yyyy-MM-dd");
            txTanggal.setText(tgl.format(date));
            txKet.requestFocus();
        } catch (Exception e) {
            System.out.println("autonumber error");
        }
    }
    
    public void clear(){
        txNoTr.setText("");
        txTanggal.setText("");
        txKet.setText("");
        txJumlah.setText("");
        autonumber();
        txKet.requestFocus();
    }
    public void tambah(){
        String No = txNoTr.getText();
        String Tanggal = txTanggal.getText();
        String Ket = txKet.getText();
        String Jumlah = txJumlah.getText();

        try {
            Connection c = koneksi.getKoneksi();
            String sql = "INSERT INTO aruskas VALUES (?, ?, ?, ?, ?)";
            PreparedStatement p = c.prepareStatement(sql);
            p.setString(1, No);
            p.setString(2, Tanggal);
            p.setString(3, Ket);
            p.setString(4, Jumlah);
            p.setString(5,"0");
            p.executeUpdate();
            p.close();
            JOptionPane.showMessageDialog(null, "Data Tersimpan");
            loadData();
        } catch (Exception e) {
            System.out.println("Terjadi Kesalahan Menambah Data");
            System.out.println("Error : " +e);
            JOptionPane.showMessageDialog(null, "Gagal menambah data !!");
        }
    }
     public void edit(){
        String No = txNoTr.getText();
        String Tanggal = txTanggal.getText();
        String Ket = txKet.getText();
        String Jumlah = txJumlah.getText();
        
        
        try {
            Connection c = koneksi.getKoneksi();
            String sql = "UPDATE aruskas SET tanggal = ?, Ket = ?, uangMasuk = ? WHERE No = ? ";
            PreparedStatement p = c.prepareStatement(sql);
            p.setString(1, Tanggal);
            p.setString(2, Ket);
            p.setString(3, Jumlah);
            p.setString(4, No);
            p.executeUpdate();
            p.close();
            loadData();
            JOptionPane.showMessageDialog(null, "Data Telah di Ubah, Silahkan Load Data !!");
        } catch (Exception e) {
            System.out.println("Gagal mengedit Data !");
            System.out.println("Error : " +e);
        }
    }
    
        public void total(){
        int jumlahBaris = jTable1.getRowCount();
        int total = 0;
        int nominal;
        for (int i = 0; i < jumlahBaris; i++) {
            nominal = Integer.parseInt(jTable1.getValueAt(i, 3).toString());
            total = total + nominal;
        }
        NumberFormat num=NumberFormat.getInstance();
        txTotal.setText("Rp. " + String.valueOf(num.format(total)));
    }
    public void loadData(){
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        try {
            Connection c = koneksi.getKoneksi();
            Statement s = c.createStatement();
            
            String sql = "SELECT * FROM aruskas WHERE uangMasuk != '0' ORDER BY No ";
            ResultSet r = s.executeQuery(sql);
            
            while (r.next()) {
                Object[] o = new Object[4];
                o [0] = r.getString("No");
                o [1] = r.getString("tanggal");
                o [2] = r.getString("ket");
                o [3] = r.getString("uangMasuk");
                
                model.addRow(o);
            }
            r.close();
            s.close();
        } catch (Exception e) {
            System.out.println("terjadi kesalahan load data");
            System.out.println("Error : "+e);
        }
    }
        public void cariData(){
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("MM");
        String bulan = monthFormatter.format(Month.of(jMonthChooser1.getMonth() + 1));
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        try {
            Connection c = koneksi.getKoneksi();
            Statement s = c.createStatement();
            
            String sql = "SELECT * FROM aruskas where month(tanggal) = '"+bulan+"' ORDER BY No";
            ResultSet r = s.executeQuery(sql);
            
            while (r.next()) {
                Object[] o = new Object[4];
                o [0] = r.getString("No");
                o [1] = r.getString("tanggal");
                o [2] = r.getString("ket");
                o [3] = r.getString("uangMasuk");
                
                model.addRow(o);
            }
            System.out.println(sql);
            r.close();
            s.close();
        } catch (Exception e) {
            System.out.println("terjadi kesalahan load data");
            System.out.println("Error : "+e);
        }
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
        PanelInput = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txNoTr = new javax.swing.JTextField();
        txTanggal = new javax.swing.JTextField();
        txJumlah = new javax.swing.JTextField();
        txKet = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jMonthChooser1 = new com.toedter.calendar.JMonthChooser();
        jButton1 = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnTambah = new javax.swing.JButton();
        btnBatal = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txTotal = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(204, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        panelJudul.setBackground(new java.awt.Color(255, 255, 255));
        panelJudul.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        Judul.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        Judul.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/jual.png"))); // NOI18N
        Judul.setText("  Pemasukan Kas");

        javax.swing.GroupLayout panelJudulLayout = new javax.swing.GroupLayout(panelJudul);
        panelJudul.setLayout(panelJudulLayout);
        panelJudulLayout.setHorizontalGroup(
            panelJudulLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelJudulLayout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(Judul)
                .addContainerGap(579, Short.MAX_VALUE))
        );
        panelJudulLayout.setVerticalGroup(
            panelJudulLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Judul)
        );

        PanelInput.setBackground(new java.awt.Color(204, 255, 255));
        PanelInput.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Pemasukan Kas", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Verdana", 1, 18))); // NOI18N
        PanelInput.setEnabled(false);
        PanelInput.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Verdana", 0, 18)); // NOI18N
        jLabel3.setText("No. Transaksi ");
        PanelInput.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, -1, -1));

        jLabel4.setFont(new java.awt.Font("Verdana", 0, 18)); // NOI18N
        jLabel4.setText("Tanggal");
        PanelInput.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 90, -1, -1));

        jLabel5.setFont(new java.awt.Font("Verdana", 0, 18)); // NOI18N
        jLabel5.setText("Keterangan ");
        PanelInput.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 130, -1, -1));

        jLabel6.setFont(new java.awt.Font("Verdana", 0, 18)); // NOI18N
        jLabel6.setText("Jumlah Uang");
        PanelInput.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 168, 140, 30));

        txNoTr.setBackground(new java.awt.Color(204, 255, 255));
        txNoTr.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        txNoTr.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        PanelInput.add(txNoTr, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 50, 150, 30));

        txTanggal.setBackground(new java.awt.Color(204, 255, 255));
        txTanggal.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        txTanggal.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txTanggal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txTanggalActionPerformed(evt);
            }
        });
        PanelInput.add(txTanggal, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 90, 150, 30));

        txJumlah.setBackground(new java.awt.Color(204, 255, 255));
        txJumlah.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        txJumlah.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        PanelInput.add(txJumlah, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 170, 270, 30));

        txKet.setBackground(new java.awt.Color(204, 255, 255));
        txKet.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        txKet.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txKet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txKetActionPerformed(evt);
            }
        });
        PanelInput.add(txKet, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 130, 640, 30));

        jTable1.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "No. Transaksi", "Tanggal", "Keterangan ", "Nominal"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setRowHeight(26);
        jTable1.setRowMargin(2);
        jTable1.setSelectionBackground(new java.awt.Color(54, 33, 89));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jMonthChooser1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jButton1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/small-icon/date-to-24.png"))); // NOI18N
        jButton1.setText("Tampilkan");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        btnHapus.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/hapus-icon.png"))); // NOI18N
        btnHapus.setText("Hapus  ");
        btnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusActionPerformed(evt);
            }
        });

        btnEdit.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/edit-icon.png"))); // NOI18N
        btnEdit.setText("Edit");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        btnTambah.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnTambah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/tambah-icon.png"))); // NOI18N
        btnTambah.setText("Tambah");
        btnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahActionPerformed(evt);
            }
        });

        btnBatal.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnBatal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cancel-red-icon.png"))); // NOI18N
        btnBatal.setText("Batal");
        btnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBatalActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Verdana", 0, 18)); // NOI18N
        jLabel1.setText("Total Pemasukan :");

        txTotal.setBackground(new java.awt.Color(204, 255, 255));
        txTotal.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        txTotal.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelJudul, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(39, 39, 39)
                .addComponent(txTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(83, 83, 83))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jMonthChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnTambah)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnHapus)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBatal))
                    .addComponent(jScrollPane1)
                    .addComponent(PanelInput, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(panelJudul, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addComponent(PanelInput, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnTambah)
                        .addComponent(btnHapus)
                        .addComponent(btnEdit)
                        .addComponent(btnBatal))
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jMonthChooser1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(28, 28, 28)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addContainerGap(54, Short.MAX_VALUE))
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

    private void txTanggalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txTanggalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txTanggalActionPerformed

    private void txKetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txKetActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txKetActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
        
        btnTambah.setEnabled(false);
        btnEdit.setEnabled(true);
        btnHapus.setEnabled(true);
        btnBatal.setEnabled(true);

        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        int i = jTable1.getSelectedRow();
        if (i == -1) {
            return;
        }
        String no = (String) model.getValueAt(i, 0);
        String tanggal = (String) model.getValueAt(i, 1);
        String ket = (String) model.getValueAt(i, 2);
        String jumlah = (String) model.getValueAt(i, 3);


        txNoTr.setText(no);
        txTanggal.setText(tanggal);
        txKet.setText(ket);
        txJumlah.setText(jumlah);
        txKet.requestFocus();
    }//GEN-LAST:event_jTable1MouseClicked

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        // TODO add your handling code here:
        int i = jTable1.getSelectedRow();
        if (i == -1) {
            return;
        }

        String id = (String) jTable1.getValueAt(i, 0);

        int pernyataan = JOptionPane.showConfirmDialog(null, "Yakin Data Akan Dihapus","Konfirmasi", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (pernyataan== JOptionPane.OK_OPTION) {
            try {
                Connection c = koneksi.getKoneksi();
                String sql = "DELETE FROM aruskas WHERE No = ?";
                PreparedStatement p = c.prepareStatement(sql);
                p.setString(1, id);
                p.executeUpdate();
                p.close();
            } catch (Exception e) {
                System.out.println("Terjadi Kesalahan");
                System.out.println("Error : "+ e);
            }finally{
                btnTambah.setEnabled(true);
                btnEdit.setEnabled(false);
                btnHapus.setEnabled(false);
                btnBatal.setEnabled(false);
                loadData();

            }
        }
        if (pernyataan== JOptionPane.CANCEL_OPTION) {

        }
    }//GEN-LAST:event_btnHapusActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        // TODO add your handling code here:
        edit();
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
        // TODO add your handling code here:
        tambah();
    }//GEN-LAST:event_btnTambahActionPerformed

    private void btnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatalActionPerformed
        // TODO add your handling code here:
        loadData();
        clear();
        btnTambah.setEnabled(true);
        btnEdit.setEnabled(false);
        btnHapus.setEnabled(false);
        btnBatal.setEnabled(false);
    }//GEN-LAST:event_btnBatalActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        cariData();
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(PemasukanKas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PemasukanKas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PemasukanKas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PemasukanKas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PemasukanKas().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Judul;
    private javax.swing.JPanel PanelInput;
    private javax.swing.JButton btnBatal;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnTambah;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private com.toedter.calendar.JMonthChooser jMonthChooser1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JTable jTable1;
    private javax.swing.JPanel panelJudul;
    public javax.swing.JTextField txJumlah;
    public javax.swing.JTextField txKet;
    public javax.swing.JTextField txNoTr;
    public javax.swing.JTextField txTanggal;
    public javax.swing.JTextField txTotal;
    // End of variables declaration//GEN-END:variables
}
