package aplikasiPenjualan;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import aplikasiPenjualan.koneksi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author M. Subhan Ramdhani
 */
public class FormPembelian extends javax.swing.JFrame {
    
    String Tanggal;
    private DefaultTableModel model;
    
    public static String form;
    
    public void totalBiaya(){
        int jumlahBaris = jTable1.getRowCount();
        int subTotalBiaya = 0;
        int ppn = 0;
        int ongkir = Integer.parseInt(txOngkir.getText()) ;
        int total = 0;
        int jumlahBarang, hargaBarang;
        for (int i = 0; i < jumlahBaris; i++) {
            jumlahBarang = Integer.parseInt(jTable1.getValueAt(i, 2).toString());
            hargaBarang = Integer.parseInt(jTable1.getValueAt(i, 3).toString());
            subTotalBiaya = subTotalBiaya + (jumlahBarang * hargaBarang);
            ppn = (int) (subTotalBiaya*0.10) ;
            total = subTotalBiaya + ppn + ongkir;
        }
        txSubTotal.setText(String.valueOf(subTotalBiaya));
        txPpn.setText(String.valueOf(ppn));
        txTotal.setText(String.valueOf(total)); 
    }
    
    private void autonumber(){
        try {
            Connection c = koneksi.getKoneksi();
            Statement s = c.createStatement();
            String sql = "SELECT * FROM pembelian ORDER BY NoBL DESC";
            ResultSet r = s.executeQuery(sql);
            if (r.next()) {
                String NoBL = r.getString("NoBL").substring(2);
                String BL = "" +(Integer.parseInt(NoBL)+1);
                String Nol = "";
                
                if(BL.length()==1)
                {Nol = "000";}
                else if(BL.length()==2)
                {Nol = "00";}
                else if(BL.length()==3)
                {Nol = "0";}
                else if(BL.length()==4)
                {Nol = "";}
                txNoBL.setText("BL" + Nol + BL);
            } else {
                txNoBL.setText("BL0001");
            }
            r.close();
            s.close();
        } catch (Exception e) {
            System.out.println("autonumber error");
        }
    }
    
     public void loadData(){
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.addRow(new Object[]{
            txIDBarang.getText(),
            txNamaBarang.getText(),
            txJumlah.getText(),
            txHarga.getText(),
            txTotal.getText()
        });
    }
    
    public void kosong(){
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        
        while (model.getRowCount()>0) {
            model.removeRow(0);
        }
    }
    
    public void utama(){
        txNoBL.setText("");
        txIDBarang.setText("");
        txNamaBarang.setText("");
        txHarga.setText("");
        txJumlah.setText("");
        autonumber();
    }
    
    public void clear(){
        txIDSupp.setText("");
        txNamaSupp.setText("");
        txTotal.setText("0");
    }
    
    public void clear2(){
        txIDBarang.setText("");
        txNamaBarang.setText("");
        txHarga.setText("");
        txJumlah.setText("");
    }
    
    public void tambahTransaksi(){
        int jumlah, harga, total;
        
        jumlah = Integer.valueOf(txJumlah.getText());
        harga = Integer.valueOf(txHarga.getText());
        total = jumlah * harga;
        
        txTotal.setText(String.valueOf(total));
        
        loadData();
        totalBiaya();
        clear2();
        txIDBarang.requestFocus();
    }
    
    public void updateStok(){
        try {
            Connection c = koneksi.getKoneksi();
            int baris = jTable1.getRowCount();
            
            for(int i = 0 ; i < baris ; i ++){
                int jumlah = Integer.parseInt(jTable1.getValueAt(i, 2).toString());
                String idbarang = jTable1.getValueAt(i, 0).toString();
                Statement s = c.createStatement();
                
                String sql = "SELECT * FROM stok where  IDBrg = \"" +idbarang+ "\" ";
                ResultSet r = s.executeQuery(sql);
                
                if (r.next()){
                    String id = r.getString("IDBrg");
                    int stokAwal = r.getInt("Stok");
                    String stokUpdate = Integer.toString(stokAwal+jumlah);
                    String update ="UPDATE stok SET Stok = ? Where IDBrg = ? ";
                    PreparedStatement p = c.prepareStatement(update);
                    p.setString(1, stokUpdate);
                    p.setString(2, id);
                    p.executeUpdate();
                    p.close();
                } else {
                    String stokUpdate = Integer.toString(0+jumlah);
                    String update = "INSERT INTO stok Values (?,?)";
                    PreparedStatement p = c.prepareStatement(update);
                    p.setString(1, idbarang);
                    p.setString(2, stokUpdate);
                    p.executeUpdate();
                    p.close();
                    System.out.println(idbarang);
                    System.out.println(stokUpdate);
                }
                
            }
            
        } catch(Exception e) {
            System.out.println("Update Stok  Error "+ e);
        }
    }
        public void idBarangCek(){
        try {
            Connection c = koneksi.getKoneksi();
            Statement s = c.createStatement();
            String sql = "SELECT * FROM barang WHERE IDBrg = \"" +txIDBarang.getText()+"\"";
            
            ResultSet r = s.executeQuery(sql);
            if(r.next()){
                String nama = r.getString("namaBrg");
                txNamaBarang.setText(nama);
                txJumlah.requestFocus();
            } else {
                JOptionPane.showMessageDialog(null,"ID Barang Salah !!");
            }
            
        }catch (Exception e){
            System.out.println("IDbarang Cek Error : "+e);
        }
    }
    
        public void idSuppCek(){
        try {
            Connection c = koneksi.getKoneksi();
            Statement s = c.createStatement();
            String sql = "SELECT * FROM supplier WHERE IDSupp = \"" +txIDSupp.getText()+"\"";
            
            ResultSet r = s.executeQuery(sql);
            if(r.next()){
                String nama = r.getString("namaSupp");
                txNamaSupp.setText(nama);
                txIDBarang.requestFocus();
            } else {
                JOptionPane.showMessageDialog(null,"ID Supplier Salah !!");
            }
            
        }catch (Exception e){
            System.out.println("IDCust Cek Error : "+e);
        }
    }
        
    public void tambahPengeluaran(){
            String NoTr= "ERROR";
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
                NoTr = "TR" + Nol + TR;
            } else {
                NoTr =  "TR0001";
            } 
         } catch (Exception e) {
            System.out.println("autonumber kas error");
        }    
            
        
        String Tanggal = txTanggal.getText();
        String Ket = "Pembelian " + txNoBL.getText();
        String Jumlah = txTotal.getText();

        try {
            Connection c = koneksi.getKoneksi();
            String sql = "INSERT INTO aruskas VALUES (?, ?, ?, ?, ?)";
            PreparedStatement p = c.prepareStatement(sql);
            p.setString(1, NoTr);
            p.setString(2, Tanggal);
            p.setString(3, Ket);
            p.setString(4, "0");
            p.setString(5,Jumlah);
            p.executeUpdate();
            p.close();
        } catch (Exception e) {
            System.out.println("Terjadi Kesalahan Menambah Data");
            System.out.println("Error : " +e);
            JOptionPane.showMessageDialog(null, "Gagal menambah data !!");
        }
    }
    
    /**
     * Creates new form FormPembelian
     */
    public FormPembelian() {
        initComponents();
        
        model = new DefaultTableModel();
        
        jTable1.setModel(model);
        
        model.addColumn("ID Barang");
        model.addColumn("Nama Barang");
        model.addColumn("Jumlah");
        model.addColumn("Harga");
        model.addColumn("Total");
        
        Date date = new Date();
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
        
        txTanggal.setText(s.format(date));
        
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
        noBL = new javax.swing.JLabel();
        idSupp = new javax.swing.JLabel();
        namaSupp = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txNoBL = new javax.swing.JTextField();
        txIDSupp = new javax.swing.JTextField();
        txNamaSupp = new javax.swing.JTextField();
        txTanggal = new javax.swing.JTextField();
        panelInput = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txIDBarang = new javax.swing.JTextField();
        txNamaBarang = new javax.swing.JTextField();
        txJumlah = new javax.swing.JTextField();
        txHarga = new javax.swing.JTextField();
        btnTambah = new javax.swing.JButton();
        btn_CariBarang = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        btnHapus = new javax.swing.JButton();
        btnSimpan = new javax.swing.JButton();
        btn_CariCust = new javax.swing.JButton();
        panelTotal = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        txSubTotal = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txPpn = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txOngkir = new javax.swing.JTextField();
        txTotal = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(204, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelJudul.setBackground(new java.awt.Color(255, 255, 255));
        panelJudul.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        Judul.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        Judul.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/beli-icon.png"))); // NOI18N
        Judul.setText("  Transaksi Pembelian");

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

        jPanel1.add(panelJudul, new org.netbeans.lib.awtextra.AbsoluteConstraints(2, 2, 996, -1));

        noBL.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        noBL.setText("No. Pembelian     :");
        jPanel1.add(noBL, new org.netbeans.lib.awtextra.AbsoluteConstraints(34, 116, -1, -1));

        idSupp.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        idSupp.setText("ID Supplier         :");
        jPanel1.add(idSupp, new org.netbeans.lib.awtextra.AbsoluteConstraints(34, 166, -1, -1));

        namaSupp.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        namaSupp.setText("Nama Supplier    :");
        jPanel1.add(namaSupp, new org.netbeans.lib.awtextra.AbsoluteConstraints(34, 219, -1, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel5.setText("Tanggal   :");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(642, 116, -1, -1));

        txNoBL.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txNoBL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txNoBLActionPerformed(evt);
            }
        });
        jPanel1.add(txNoBL, new org.netbeans.lib.awtextra.AbsoluteConstraints(198, 113, 205, -1));

        txIDSupp.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txIDSupp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txIDSuppActionPerformed(evt);
            }
        });
        jPanel1.add(txIDSupp, new org.netbeans.lib.awtextra.AbsoluteConstraints(197, 163, 205, -1));

        txNamaSupp.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txNamaSupp.setEnabled(false);
        jPanel1.add(txNamaSupp, new org.netbeans.lib.awtextra.AbsoluteConstraints(196, 216, 205, -1));

        txTanggal.setBackground(new java.awt.Color(204, 255, 255));
        txTanggal.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jPanel1.add(txTanggal, new org.netbeans.lib.awtextra.AbsoluteConstraints(747, 113, 205, -1));

        panelInput.setBackground(new java.awt.Color(204, 255, 255));
        panelInput.setBorder(javax.swing.BorderFactory.createCompoundBorder(null, javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.lightGray, java.awt.Color.black)));

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel6.setText("ID Barang");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel7.setText("Nama Barang");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel8.setText("Jumlah");

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel9.setText("Harga");

        txIDBarang.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txIDBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txIDBarangActionPerformed(evt);
            }
        });

        txNamaBarang.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txNamaBarang.setEnabled(false);

        txJumlah.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txJumlah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txJumlahActionPerformed(evt);
            }
        });

        txHarga.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txHarga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txHargaActionPerformed(evt);
            }
        });
        txHarga.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txHargaKeyPressed(evt);
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

        btn_CariBarang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cari-icon.png"))); // NOI18N
        btn_CariBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_CariBarangActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelInputLayout = new javax.swing.GroupLayout(panelInput);
        panelInput.setLayout(panelInputLayout);
        panelInputLayout.setHorizontalGroup(
            panelInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInputLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(panelInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addGroup(panelInputLayout.createSequentialGroup()
                        .addComponent(txIDBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_CariBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(27, 27, 27)
                .addGroup(panelInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txNamaBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txJumlah, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addGap(18, 18, 18)
                .addGroup(panelInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelInputLayout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panelInputLayout.createSequentialGroup()
                        .addComponent(txHarga, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                        .addComponent(btnTambah)
                        .addGap(42, 42, 42))))
        );
        panelInputLayout.setVerticalGroup(
            panelInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInputLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txIDBarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txNamaBarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txJumlah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txHarga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnTambah))
                    .addComponent(btn_CariBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(34, Short.MAX_VALUE))
        );

        jPanel1.add(panelInput, new org.netbeans.lib.awtextra.AbsoluteConstraints(17, 275, -1, -1));

        jTable1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID Barang", "Nama Barang", "Jumlah", "Harga", "Total"
            }
        ));
        jTable1.setRowHeight(24);
        jScrollPane1.setViewportView(jTable1);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(17, 418, 769, 209));

        btnHapus.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/hapus-icon.png"))); // NOI18N
        btnHapus.setText("Hapus  ");
        btnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusActionPerformed(evt);
            }
        });
        jPanel1.add(btnHapus, new org.netbeans.lib.awtextra.AbsoluteConstraints(804, 418, -1, -1));

        btnSimpan.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/simpan-icon.png"))); // NOI18N
        btnSimpan.setText("Simpan ");
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });
        jPanel1.add(btnSimpan, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 580, -1, -1));

        btn_CariCust.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cari-icon.png"))); // NOI18N
        btn_CariCust.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_CariCustActionPerformed(evt);
            }
        });
        jPanel1.add(btn_CariCust, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 150, 40, 40));

        panelTotal.setBackground(new java.awt.Color(204, 255, 255));
        panelTotal.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.white, java.awt.Color.black));
        panelTotal.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel10.setText("Sub Total         :");
        panelTotal.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(31, 21, -1, -1));

        txSubTotal.setBackground(new java.awt.Color(204, 255, 255));
        txSubTotal.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        panelTotal.add(txSubTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(194, 18, 165, -1));

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel12.setText("Ppn                 :");
        panelTotal.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(29, 58, 140, -1));

        txPpn.setBackground(new java.awt.Color(204, 255, 255));
        txPpn.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        panelTotal.add(txPpn, new org.netbeans.lib.awtextra.AbsoluteConstraints(194, 55, 165, -1));

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel13.setText("Ongkos Kirim    :");
        panelTotal.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 20, 140, -1));

        txOngkir.setBackground(new java.awt.Color(204, 255, 255));
        txOngkir.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txOngkir.setText("0");
        txOngkir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txOngkirActionPerformed(evt);
            }
        });
        panelTotal.add(txOngkir, new org.netbeans.lib.awtextra.AbsoluteConstraints(555, 20, 200, -1));

        txTotal.setBackground(new java.awt.Color(204, 255, 255));
        txTotal.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        panelTotal.add(txTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(555, 60, 200, -1));

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel14.setText("Total               :");
        panelTotal.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 60, 140, -1));

        jPanel1.add(panelTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 650, 800, 100));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 800, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txHargaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txHargaKeyPressed
        // TODO add your handling code here:
        int kode = evt.getKeyCode();
        if(kode==evt.VK_ENTER){
            tambahTransaksi();
        }
    }//GEN-LAST:event_txHargaKeyPressed

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
        // TODO add your handling code here:
        idBarangCek();
        if(txNamaBarang.getText() == ""){
        txIDBarang.setText("");
        txIDBarang.requestFocus();
        } else{
            tambahTransaksi();
        }
    }//GEN-LAST:event_btnTambahActionPerformed

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        // TODO add your handling code here:
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        int row = jTable1.getSelectedRow();
        model.removeRow(row);
        totalBiaya();
    }//GEN-LAST:event_btnHapusActionPerformed

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        // TODO add your handling code here:
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();

        String noBL = txNoBL.getText();
        String tanggal = txTanggal.getText();
        String idSupp = txIDSupp.getText();
        String totalBeli = txTotal.getText();

        try {
            Connection c = koneksi.getKoneksi();
            String sql = "INSERT INTO pembelian VALUES (?, ?, ?, ?)";
            PreparedStatement p = c.prepareStatement(sql);
            p.setString(1, noBL);
            p.setString(2, tanggal);
            p.setString(3, idSupp);
            p.setString(4, totalBeli);
            p.executeUpdate();
            p.close();
        } catch (Exception e) {
            System.out.println("simpan pembelian error" + e);
        }
        
        JOptionPane.showMessageDialog(this, "Data Pembelian Tersimpan !");

        try {
            Connection c = koneksi.getKoneksi();
            int baris = jTable1.getRowCount();

            for (int i = 0; i < baris; i++) {
                String idBarang = jTable1.getValueAt(i, 0).toString();
                String namaBarang = jTable1.getValueAt(i, 1).toString();
                String jumlah = jTable1.getValueAt(i, 2).toString();
                String harga = jTable1.getValueAt(i, 3).toString();
                String total = jTable1.getValueAt(i, 4).toString();

                String sql = "INSERT INTO rincianpembelian VALUES(?,?,?,?,?,?)";
                PreparedStatement p = c.prepareStatement(sql);
                p.setString(1, noBL);
                p.setString(2, idBarang);
                p.setString(3, namaBarang);
                p.setString(4, jumlah);
                p.setString(5, harga);
                p.setString(6, total);
                p.executeUpdate();
                p.close();
            }
        } catch (Exception e) {
            System.out.println("Gagal menyimpan rincian penjualan" + e);
        }
        tambahPengeluaran();
        clear();
        utama();
        autonumber();
        kosong();
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void txIDBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txIDBarangActionPerformed
        // TODO add your handling code here:
        idBarangCek();
    }//GEN-LAST:event_txIDBarangActionPerformed

    private void txNoBLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txNoBLActionPerformed
        // TODO add your handling code here:
        txIDSupp.requestFocus();
    }//GEN-LAST:event_txNoBLActionPerformed

    private void txHargaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txHargaActionPerformed
        // TODO add your handling code here:
            if( txNamaBarang.getText() == ""){
            idBarangCek();
            txIDBarang.setText("");
            txIDBarang.requestFocus();
        } else{
            tambahTransaksi();
        }
    }//GEN-LAST:event_txHargaActionPerformed

    private void txIDSuppActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txIDSuppActionPerformed
        // TODO add your handling code here:
        idSuppCek();
    }//GEN-LAST:event_txIDSuppActionPerformed

    private void txJumlahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txJumlahActionPerformed
        // TODO add your handling code here:
        txHarga.requestFocus();
    }//GEN-LAST:event_txJumlahActionPerformed

    private void btn_CariBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_CariBarangActionPerformed
        // TODO add your handling code here:
        form = "pembelian";
        FormPenjualan.form = "pembelian";
        new ListDataBarang().setVisible(true);

    }//GEN-LAST:event_btn_CariBarangActionPerformed

    private void btn_CariCustActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_CariCustActionPerformed
        // TODO add your handling code here:
        new ListDataSupp().setVisible(true);
    }//GEN-LAST:event_btn_CariCustActionPerformed

    private void txOngkirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txOngkirActionPerformed
        // TODO add your handling code here:
        totalBiaya();
    }//GEN-LAST:event_txOngkirActionPerformed

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
            java.util.logging.Logger.getLogger(FormPembelian.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormPembelian.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormPembelian.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormPembelian.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormPembelian().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Judul;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JButton btnTambah;
    private javax.swing.JButton btn_CariBarang;
    private javax.swing.JButton btn_CariCust;
    private javax.swing.JLabel idSupp;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel namaSupp;
    private javax.swing.JLabel noBL;
    private javax.swing.JPanel panelInput;
    private javax.swing.JPanel panelJudul;
    private javax.swing.JPanel panelTotal;
    public static javax.swing.JTextField txHarga;
    public static javax.swing.JTextField txIDBarang;
    public static javax.swing.JTextField txIDSupp;
    public static javax.swing.JTextField txJumlah;
    public static javax.swing.JTextField txNamaBarang;
    public static javax.swing.JTextField txNamaSupp;
    private javax.swing.JTextField txNoBL;
    private javax.swing.JTextField txOngkir;
    private javax.swing.JTextField txPpn;
    private javax.swing.JTextField txSubTotal;
    private javax.swing.JTextField txTanggal;
    private javax.swing.JTextField txTotal;
    // End of variables declaration//GEN-END:variables
}
