/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplikasiPenjualan;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.NumberFormat;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author msubh
 */
public class BukuKas extends javax.swing.JFrame {
    
    public void loadData(){
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        txSaldoAwal.setText("0");
        try {
            Connection c = koneksi.getKoneksi();
            Statement s = c.createStatement();
            NumberFormat num=NumberFormat.getInstance();
             txSaldoAwal.setText("Rp. " + String.valueOf(num.format(0)));
            String sql = "SELECT No , tanggal , ket , uangMasuk , UangKeluar ,\n" +
                         "SUM(uangMasuk - UangKeluar) OVER (ORDER BY tanggal ASC, No ASC) \n" +
                         "AS saldo\n" +
                         "FROM aruskas;";
            String saldoakhir = "SELECT SUM(uangMasuk-UangKeluar) AS saldoakhir FROM aruskas";
            ResultSet a = s.executeQuery(saldoakhir);
            if (a.next()){
            int saldo = a.getInt("saldoakhir");
            txSaldoAkhir.setText("Rp. " + num.format(saldo));
            }
            a.close();
            
            ResultSet r = s.executeQuery(sql);

            
            while (r.next()) {
                Object[] o = new Object[6];
                o [0] = r.getString("No");
                o [1] = r.getString("tanggal");
                o [2] = r.getString("ket");
                o [3] = r.getString("uangMasuk");
                o [4] = r.getString("uangKeluar");
                o [5] = r.getString("saldo");
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
            
        DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("MM");
        String bulan = monthFormatter.format(Month.of(jMonthChooser1.getMonth() + 1));
        int bulanterakhir = Integer.parseInt(bulan) - 1;
        NumberFormat num=NumberFormat.getInstance();
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();       
        
        int saldoAwal = 0;
        try {
            Connection c = koneksi.getKoneksi();
            Statement s = c.createStatement();
            String saldoawal = "SELECT SUM(uangMasuk-UangKeluar) AS saldoawal FROM aruskas "
                    + "where month(tanggal) between '01' AND '" + bulanterakhir +"' "  ;
            ResultSet b = s.executeQuery(saldoawal);
            if (b.next()){
               saldoAwal = b.getInt("saldoawal");
               txSaldoAwal.setText("Rp. " + num.format(saldoAwal));
            }
            b.close();
            
            
            
            
            String saldoakhir = "SELECT SUM(uangMasuk-UangKeluar) AS saldoakhir FROM aruskas ";
            ResultSet a = s.executeQuery(saldoakhir);
            if (a.next()){
                int saldo = a.getInt("saldoakhir");
                txSaldoAkhir.setText("Rp. " + num.format(saldo));
            } 
            a.close();
            String sql = "SELECT No , tanggal , ket , uangMasuk , UangKeluar ,\n" +
                         "SUM(uangMasuk - UangKeluar) OVER (ORDER BY tanggal ASC, No ASC) \n" +
                         "AS saldo\n" +
                         "FROM aruskas " +
                         "WHERE month(tanggal) = '"+ bulan +"' ";   
            ResultSet r = s.executeQuery(sql);
            
            while (r.next()) {
                int saldoTransaksi = r.getInt("saldo");
                
                Object[] o = new Object[6];
                o [0] = r.getString("No");
                o [1] = r.getString("tanggal");
                o [2] = r.getString("ket");
                o [3] = r.getString("uangMasuk");
                o [4] = r.getString("uangKeluar");
                o [5] = saldoAwal + saldoTransaksi;
                model.addRow(o);
            }
            r.close();
            s.close();
            
        } catch (Exception e) {
            System.out.println("terjadi kesalahan load data");
            System.out.println("Error : "+e);
            
        }
    }

    /**
     * Creates new form BukuKas
     */
    public BukuKas() {
        initComponents();
        loadData();
        Locale locale = new Locale ("id","ID");
        Locale.setDefault(locale);
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
        panelJudul4 = new javax.swing.JPanel();
        Judul4 = new javax.swing.JLabel();
        jMonthChooser1 = new com.toedter.calendar.JMonthChooser();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        txSaldoAwal = new javax.swing.JTextField();
        txSaldoAkhir = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(204, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        panelJudul4.setBackground(new java.awt.Color(255, 255, 255));
        panelJudul4.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        Judul4.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        Judul4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/jual.png"))); // NOI18N
        Judul4.setText("  Buku Kas");

        javax.swing.GroupLayout panelJudul4Layout = new javax.swing.GroupLayout(panelJudul4);
        panelJudul4.setLayout(panelJudul4Layout);
        panelJudul4Layout.setHorizontalGroup(
            panelJudul4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelJudul4Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(Judul4, javax.swing.GroupLayout.PREFERRED_SIZE, 418, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelJudul4Layout.setVerticalGroup(
            panelJudul4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Judul4)
        );

        jMonthChooser1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jButton1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/small-icon/date-to-24.png"))); // NOI18N
        jButton1.setText("Tampilkan");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTable1.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "No. Transaksi", "Tanggal", "Keterangan ", "Pemasukan", "Pengeluaran", "Saldo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true
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

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("Saldo Awal :");

        txSaldoAwal.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txSaldoAwal.setText(" ");

        txSaldoAkhir.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txSaldoAkhir.setText(" ");
        txSaldoAkhir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txSaldoAkhirActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setText("Saldo Akhir :");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelJudul4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jMonthChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addComponent(jButton1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 941, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(35, 35, 35)
                                .addComponent(txSaldoAwal, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(35, 35, 35)
                                .addComponent(txSaldoAkhir, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(panelJudul4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jMonthChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(51, 51, 51)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txSaldoAwal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 470, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txSaldoAkhir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
       cariData();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_jTable1MouseClicked

    private void txSaldoAkhirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txSaldoAkhirActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txSaldoAkhirActionPerformed

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
            java.util.logging.Logger.getLogger(BukuKas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BukuKas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BukuKas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BukuKas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BukuKas().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Judul;
    private javax.swing.JLabel Judul1;
    private javax.swing.JLabel Judul2;
    private javax.swing.JLabel Judul3;
    private javax.swing.JLabel Judul4;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private com.toedter.calendar.JMonthChooser jMonthChooser1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JTable jTable1;
    private javax.swing.JPanel panelJudul;
    private javax.swing.JPanel panelJudul1;
    private javax.swing.JPanel panelJudul2;
    private javax.swing.JPanel panelJudul3;
    private javax.swing.JPanel panelJudul4;
    private javax.swing.JTextField txSaldoAkhir;
    private javax.swing.JTextField txSaldoAwal;
    // End of variables declaration//GEN-END:variables
}
