/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplikasiPenjualan;

import java.io.InputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Locale;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Dhani
 */
public class Menu extends javax.swing.JFrame {
    
    private String judul;
    private String id_group = "";
    
    /**
     * Creates new form Menu
     */
    public Menu() {
        initComponents();
        setTitle("Toko Nirwana");
        setExtendedState(MAXIMIZED_BOTH);
        Locale locale = new Locale ("id","ID");
        Locale.setDefault(locale);
        id_group = Login.id_group;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDesktopPane1 = new javax.swing.JDesktopPane();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenu7 = new javax.swing.JMenu();
        jMenuItem11 = new javax.swing.JMenuItem();
        jMenuItem12 = new javax.swing.JMenuItem();
        jMenuItem13 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenuItem10 = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenuItem8 = new javax.swing.JMenuItem();
        jMenuItem9 = new javax.swing.JMenuItem();
        jMenuItem14 = new javax.swing.JMenuItem();
        jMenu5 = new javax.swing.JMenu();
        jMenu6 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jDesktopPane1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.black, java.awt.Color.gray));

        javax.swing.GroupLayout jDesktopPane1Layout = new javax.swing.GroupLayout(jDesktopPane1);
        jDesktopPane1.setLayout(jDesktopPane1Layout);
        jDesktopPane1Layout.setHorizontalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1244, Short.MAX_VALUE)
        );
        jDesktopPane1Layout.setVerticalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 681, Short.MAX_VALUE)
        );

        jMenu1.setBackground(new java.awt.Color(255, 255, 255));
        jMenu1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.black, java.awt.Color.gray));
        jMenu1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/small-icon/beli-icon-small.png"))); // NOI18N
        jMenu1.setText(" Pembelian   ");
        jMenu1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N

        jMenuItem1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jMenuItem1.setText("Transaksi Pembelian");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jMenuItem2.setText("Data Supplier");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuBar1.add(jMenu1);

        jMenu2.setBackground(new java.awt.Color(255, 255, 255));
        jMenu2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.black, java.awt.Color.gray));
        jMenu2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/small-icon/jual-small.png"))); // NOI18N
        jMenu2.setText("  Penjualan  ");
        jMenu2.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N

        jMenuItem3.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jMenuItem3.setText("Transaksi Penjualan");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem3);

        jMenuItem4.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jMenuItem4.setText("Data Customer");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem4);

        jMenuBar1.add(jMenu2);

        jMenu7.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.black, java.awt.Color.gray));
        jMenu7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/small-icon/money-2-24.png"))); // NOI18N
        jMenu7.setText("  Keuangan   ");
        jMenu7.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jMenu7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu7ActionPerformed(evt);
            }
        });

        jMenuItem11.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jMenuItem11.setText("Pemasukan Kas");
        jMenuItem11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem11ActionPerformed(evt);
            }
        });
        jMenu7.add(jMenuItem11);

        jMenuItem12.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jMenuItem12.setText("Pengeluaran Kas");
        jMenuItem12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem12ActionPerformed(evt);
            }
        });
        jMenu7.add(jMenuItem12);

        jMenuItem13.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jMenuItem13.setText("Buku Kas");
        jMenuItem13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem13ActionPerformed(evt);
            }
        });
        jMenu7.add(jMenuItem13);

        jMenuBar1.add(jMenu7);

        jMenu3.setBackground(new java.awt.Color(255, 255, 255));
        jMenu3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.black, java.awt.Color.gray));
        jMenu3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/small-icon/product-24.png"))); // NOI18N
        jMenu3.setText("  Inventory  ");
        jMenu3.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N

        jMenuItem5.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jMenuItem5.setText("Data Barang");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem5);

        jMenuItem6.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jMenuItem6.setText("Stok Barang");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem6);

        jMenuItem10.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jMenuItem10.setText("Retur Barang");
        jMenuItem10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem10ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem10);

        jMenuBar1.add(jMenu3);

        jMenu4.setBackground(new java.awt.Color(255, 255, 255));
        jMenu4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.black, java.awt.Color.gray));
        jMenu4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/small-icon/report-24 (1).png"))); // NOI18N
        jMenu4.setText("  Laporan  ");
        jMenu4.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N

        jMenuItem7.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jMenuItem7.setText("Laporan Penjualan");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem7);

        jMenuItem8.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jMenuItem8.setText("Laporan Pembelian");
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem8);

        jMenuItem9.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jMenuItem9.setText("Laporan Stok Barang");
        jMenuItem9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem9ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem9);

        jMenuItem14.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jMenuItem14.setText("Laporan Rekap Tahunan");
        jMenuItem14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem14ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem14);

        jMenuBar1.add(jMenu4);

        jMenu5.setBackground(new java.awt.Color(255, 255, 255));
        jMenu5.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.black, java.awt.Color.gray));
        jMenu5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/small-icon/group-24.png"))); // NOI18N
        jMenu5.setText("   Pegawai  ");
        jMenu5.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jMenu5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenu5MouseClicked(evt);
            }
        });
        jMenuBar1.add(jMenu5);

        jMenu6.setBackground(new java.awt.Color(255, 255, 255));
        jMenu6.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.black, java.awt.Color.gray));
        jMenu6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/small-icon/logout-24.png"))); // NOI18N
        jMenu6.setText("  Logout   ");
        jMenu6.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jMenu6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenu6MouseClicked(evt);
            }
        });
        jMenu6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMenu6KeyPressed(evt);
            }
        });
        jMenuBar1.add(jMenu6);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jDesktopPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jDesktopPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
          if (id_group == null || id_group.matches("System Admin|Manager|CS")) {
            jDesktopPane1.removeAll();
            FormPembelian a = new FormPembelian();
            JInternalFrame internalframe = new JInternalFrame(judul, false, true, true);
            internalframe.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            internalframe.setContentPane(a.getContentPane());
            internalframe.setLocation((jDesktopPane1.getWidth() - a.getWidth()) / 2, (jDesktopPane1.getHeight() - a.getHeight()) / 2);
            internalframe.pack();

            a.setLocationRelativeTo(this);
            internalframe.setVisible(true);
            jDesktopPane1.add(internalframe);
        } else {
            JOptionPane.showMessageDialog(null, "akses dibatasi.");
        }
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        // TODO add your handling code here:
                if (id_group == null || id_group.matches("System Admin|Manager|CS")) {
            jDesktopPane1.removeAll();
            FormPenjualan a = new FormPenjualan();
            JInternalFrame internalframe = new JInternalFrame(judul, false, true, true);
            internalframe.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            internalframe.setContentPane(a.getContentPane());
            internalframe.setLocation((jDesktopPane1.getWidth() - a.getWidth()) / 2, (jDesktopPane1.getHeight() - a.getHeight()) / 2);
            internalframe.pack();

            a.setLocationRelativeTo(this);
            internalframe.setVisible(true);
            jDesktopPane1.add(internalframe);
        } else {
            JOptionPane.showMessageDialog(null, "akses dibatasi.");
        }
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
            if (id_group == null || id_group.matches("System Admin|Manager|CS")) {
            jDesktopPane1.removeAll();
            DataSupplier a = new DataSupplier();
            JInternalFrame internalframe = new JInternalFrame(judul, false, true, true);
            internalframe.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            internalframe.setContentPane(a.getContentPane());
            internalframe.setLocation((jDesktopPane1.getWidth() - a.getWidth()) / 2, (jDesktopPane1.getHeight() - a.getHeight()) / 2);
            internalframe.pack();

            a.setLocationRelativeTo(this);
            internalframe.setVisible(true);
            jDesktopPane1.add(internalframe);
        } else {
            JOptionPane.showMessageDialog(null, "akses dibatasi.");
        }
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        // TODO add your handling code here:
        if (id_group == null || id_group.matches("System Admin|Manager|CS")) {
            jDesktopPane1.removeAll();
            DataCustomer a = new DataCustomer();
            JInternalFrame internalframe = new JInternalFrame(judul, false, true, true);
            internalframe.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            internalframe.setContentPane(a.getContentPane());
            internalframe.setLocation((jDesktopPane1.getWidth() - a.getWidth()) / 2, (jDesktopPane1.getHeight() - a.getHeight()) / 2);
            internalframe.pack();

            a.setLocationRelativeTo(this);
            internalframe.setVisible(true);
            jDesktopPane1.add(internalframe);
        } else {
            JOptionPane.showMessageDialog(null, "akses dibatasi.");
        }
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        // TODO add your handling code here:
        if (id_group == null || id_group.matches("System Admin|Manager|Staff Gudang")) {
            jDesktopPane1.removeAll();
            DataBarang a = new DataBarang();
            JInternalFrame internalframe = new JInternalFrame(judul, false, true, true);
            internalframe.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            internalframe.setContentPane(a.getContentPane());
            internalframe.setLocation((jDesktopPane1.getWidth() - a.getWidth()) / 2, (jDesktopPane1.getHeight() - a.getHeight()) / 2);
            internalframe.pack();

            a.setLocationRelativeTo(this);
            internalframe.setVisible(true);
            jDesktopPane1.add(internalframe);
        } else {
            JOptionPane.showMessageDialog(null, "akses dibatasi.");
        }
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        // TODO add your handling code here:
        if (id_group == null || id_group.matches("System Admin|Manager|Staff Gudang")) {
            jDesktopPane1.removeAll();
            TabelStok a = new TabelStok();
            JInternalFrame internalframe = new JInternalFrame(judul, false, true, true);
            internalframe.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            internalframe.setContentPane(a.getContentPane());
            internalframe.setLocation((jDesktopPane1.getWidth() - a.getWidth()) / 2, (jDesktopPane1.getHeight() - a.getHeight()) / 2);
            internalframe.pack();

            a.setLocationRelativeTo(this);
            internalframe.setVisible(true);
            jDesktopPane1.add(internalframe);
        } else {
            JOptionPane.showMessageDialog(null, "akses dibatasi.");
        }
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void jMenu5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenu5MouseClicked
        // TODO add your handling code here:
        if (id_group == null || id_group.matches("System Admin|Manager")) {
            jDesktopPane1.removeAll();
            Pegawai a = new Pegawai();
            JInternalFrame internalframe = new JInternalFrame(judul, false, true, true);
            internalframe.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            internalframe.setContentPane(a.getContentPane());
            internalframe.setLocation((jDesktopPane1.getWidth() - a.getWidth()) / 2, (jDesktopPane1.getHeight() - a.getHeight()) / 2);
            internalframe.pack();

            a.setLocationRelativeTo(this);
            internalframe.setVisible(true);
            jDesktopPane1.add(internalframe);
        } else {
            JOptionPane.showMessageDialog(null, "akses dibatasi.");
        }

    }//GEN-LAST:event_jMenu5MouseClicked

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        // TODO add your handling code here:
        jDesktopPane1.removeAll();
        LaporanPenjualan a = new LaporanPenjualan();
        JInternalFrame internalframe = new JInternalFrame(judul,false,true,true);
        internalframe.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        internalframe.setContentPane(a.getContentPane());
        internalframe.setLocation((jDesktopPane1.getWidth()-a.getWidth())/2, (jDesktopPane1.getHeight()-a.getHeight())/2);
        internalframe.pack();
        
        a.setLocationRelativeTo(this);
        internalframe.setVisible(true);
        jDesktopPane1.add(internalframe);
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void jMenuItem9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem9ActionPerformed
        // TODO add your handling code here:
        try {
              
              //String namaFile =  "src/aplikasiPenjualan/data/Stok.jasper";
              Connection conn = new koneksi().getKoneksi();
              HashMap parameter = new HashMap();
              
              //File report_file = new File (namaFile);
              InputStream file = getClass().getResourceAsStream("/aplikasiPenjualan/data/Stok.jrxml");
              JasperDesign jasperDesign = JRXmlLoader.load(file);
              JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
              //JasperReport jasperReport =(JasperReport) JRLoader.loadObject(report_file.getPath());
              JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameter,conn);
               //JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,parameter,conn);
              JasperViewer.viewReport(jasperPrint,false);
          }catch (Exception e){
              JOptionPane.showMessageDialog(null, e.getMessage());
           
        }
    }//GEN-LAST:event_jMenuItem9ActionPerformed

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
        // TODO add your handling code here:
        jDesktopPane1.removeAll();
        LaporanPembelian a = new LaporanPembelian();
        JInternalFrame internalframe = new JInternalFrame(judul,false,true,true);
        internalframe.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        internalframe.setContentPane(a.getContentPane());
        internalframe.setLocation((jDesktopPane1.getWidth()-a.getWidth())/2, (jDesktopPane1.getHeight()-a.getHeight())/2);
        internalframe.pack();
        
        a.setLocationRelativeTo(this);
        internalframe.setVisible(true);
        jDesktopPane1.add(internalframe);
    }//GEN-LAST:event_jMenuItem8ActionPerformed

    private void jMenu6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenu6MouseClicked
        // TODO add your handling code here:
        //int ok = JOptionPane.showConfirmDialog(null, "Anda ingin logout ?","Konfirmasi Dialog", JOptionPane.YES_NO_OPTION);
         Object[] options = {"Ya", "Tidak"};
        int n = JOptionPane.showOptionDialog(null,
        "Anda ingin keluar ?",
        "Konfirmasi",
        JOptionPane.YES_NO_CANCEL_OPTION,
        JOptionPane.QUESTION_MESSAGE,
        null,
        options,
        options[1]);
        if(n==0){
        
        jDesktopPane1.removeAll();
        this.dispose();
        Login a = new Login();
        a.setVisible(true);
        }
    }//GEN-LAST:event_jMenu6MouseClicked

    private void jMenu6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMenu6KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenu6KeyPressed

    private void jMenuItem10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem10ActionPerformed
        // TODO add your handling code here:
            if (id_group == null || id_group.matches("System Admin|Manager|Staff Gudang")) {
            jDesktopPane1.removeAll();
            FormRetur a = new FormRetur();
            JInternalFrame internalframe = new JInternalFrame(judul, false, true, true);
            internalframe.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            internalframe.setContentPane(a.getContentPane());
            internalframe.setLocation((jDesktopPane1.getWidth() - a.getWidth()) / 2, (jDesktopPane1.getHeight() - a.getHeight()) / 2);
            internalframe.pack();

            a.setLocationRelativeTo(this);
            internalframe.setVisible(true);
            jDesktopPane1.add(internalframe);
        } else {
            JOptionPane.showMessageDialog(null, "akses dibatasi.");
        }
    }//GEN-LAST:event_jMenuItem10ActionPerformed

    private void jMenuItem11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem11ActionPerformed
        // TODO add your handling code here:
        if (id_group == null || id_group.matches("System Admin|Manager|Staff Keuangan")) {
            jDesktopPane1.removeAll();
            PemasukanKas a = new PemasukanKas();
            JInternalFrame internalframe = new JInternalFrame(judul, false, true, true);
            internalframe.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            internalframe.setContentPane(a.getContentPane());
            internalframe.setLocation((jDesktopPane1.getWidth() - a.getWidth()) / 2, (jDesktopPane1.getHeight() - a.getHeight()) / 2);
            internalframe.pack();

            a.setLocationRelativeTo(this);
            internalframe.setVisible(true);
            jDesktopPane1.add(internalframe);
        } else {
            JOptionPane.showMessageDialog(null, "akses dibatasi.");
        }
    }//GEN-LAST:event_jMenuItem11ActionPerformed

    private void jMenuItem12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem12ActionPerformed
        // TODO add your handling code here:
            if (id_group == null || id_group.matches("System Admin|Manager|Staff Keuangan")) {
            jDesktopPane1.removeAll();
            PengeluaranKas a = new PengeluaranKas();
            JInternalFrame internalframe = new JInternalFrame(judul, false, true, true);
            internalframe.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            internalframe.setContentPane(a.getContentPane());
            internalframe.setLocation((jDesktopPane1.getWidth() - a.getWidth()) / 2, (jDesktopPane1.getHeight() - a.getHeight()) / 2);
            internalframe.pack();

            a.setLocationRelativeTo(this);
            internalframe.setVisible(true);
            jDesktopPane1.add(internalframe);
        } else {
            JOptionPane.showMessageDialog(null, "akses dibatasi.");
        }
    }//GEN-LAST:event_jMenuItem12ActionPerformed

    private void jMenuItem13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem13ActionPerformed
        // TODO add your handling code here:
            if (id_group == null || id_group.matches("System Admin|Manager|Staff Keuangan")) {
            jDesktopPane1.removeAll();
            BukuKas a = new BukuKas();
            JInternalFrame internalframe = new JInternalFrame(judul, false, true, true);
            internalframe.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            internalframe.setContentPane(a.getContentPane());
            internalframe.setLocation((jDesktopPane1.getWidth() - a.getWidth()) / 2, (jDesktopPane1.getHeight() - a.getHeight()) / 2);
            internalframe.pack();

            a.setLocationRelativeTo(this);
            internalframe.setVisible(true);
            jDesktopPane1.add(internalframe);
        } else {
            JOptionPane.showMessageDialog(null, "akses dibatasi.");
        }
    }//GEN-LAST:event_jMenuItem13ActionPerformed

    private void jMenuItem14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem14ActionPerformed
        // TODO add your handling code here:
                    if (id_group == null || id_group.matches("System Admin|Manager")) {
            jDesktopPane1.removeAll();
            LaporanRekapTahunan a = new LaporanRekapTahunan();
            JInternalFrame internalframe = new JInternalFrame(judul, false, true, true);
            internalframe.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            internalframe.setContentPane(a.getContentPane());
            internalframe.setLocation((jDesktopPane1.getWidth() - a.getWidth()) / 2, (jDesktopPane1.getHeight() - a.getHeight()) / 2);
            internalframe.pack();

            a.setLocationRelativeTo(this);
            internalframe.setVisible(true);
            jDesktopPane1.add(internalframe);
        } else {
            JOptionPane.showMessageDialog(null, "akses dibatasi.");
        }
    }//GEN-LAST:event_jMenuItem14ActionPerformed

    private void jMenu7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenu7ActionPerformed

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
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Menu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenu jMenu7;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem10;
    private javax.swing.JMenuItem jMenuItem11;
    private javax.swing.JMenuItem jMenuItem12;
    private javax.swing.JMenuItem jMenuItem13;
    private javax.swing.JMenuItem jMenuItem14;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    // End of variables declaration//GEN-END:variables
}
