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
    
    public void totalBiaya(){
        int jumlahBaris = jTable1.getRowCount();
        int totalBiaya = 0;
        int jumlahBarang, hargaBarang;
        for (int i = 0; i < jumlahBaris; i++) {
            jumlahBarang = Integer.parseInt(jTable1.getValueAt(i, 2).toString());
            hargaBarang = Integer.parseInt(jTable1.getValueAt(i, 3).toString());
            totalBiaya = totalBiaya + (jumlahBarang * hargaBarang);
        }
        txTotal.setText(String.valueOf(totalBiaya));
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
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        btnHapus = new javax.swing.JButton();
        panelTotal = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        txTotal = new javax.swing.JTextField();
        btnSimpan = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(204, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

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

        noBL.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        noBL.setText("No. Pembelian     :");

        idSupp.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        idSupp.setText("ID Supplier         :");

        namaSupp.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        namaSupp.setText("Nama Supplier    :");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel5.setText("Tanggal   :");

        txNoBL.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txNoBL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txNoBLActionPerformed(evt);
            }
        });

        txIDSupp.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txIDSupp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txIDSuppActionPerformed(evt);
            }
        });

        txNamaSupp.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txNamaSupp.setEnabled(false);

        txTanggal.setBackground(new java.awt.Color(204, 255, 255));
        txTanggal.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

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

        javax.swing.GroupLayout panelInputLayout = new javax.swing.GroupLayout(panelInput);
        panelInput.setLayout(panelInputLayout);
        panelInputLayout.setHorizontalGroup(
            panelInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInputLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(panelInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(txIDBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(txNamaBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                .addGroup(panelInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txIDBarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txNamaBarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txJumlah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txHarga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTambah))
                .addContainerGap(34, Short.MAX_VALUE))
        );

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

        btnHapus.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/hapus-icon.png"))); // NOI18N
        btnHapus.setText("Hapus  ");
        btnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusActionPerformed(evt);
            }
        });

        panelTotal.setBackground(new java.awt.Color(204, 255, 255));
        panelTotal.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.white, java.awt.Color.black));

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel10.setText("Total         :");

        txTotal.setBackground(new java.awt.Color(204, 255, 255));
        txTotal.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txTotal.setEnabled(false);

        javax.swing.GroupLayout panelTotalLayout = new javax.swing.GroupLayout(panelTotal);
        panelTotal.setLayout(panelTotalLayout);
        panelTotalLayout.setHorizontalGroup(
            panelTotalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTotalLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel10)
                .addGap(27, 27, 27)
                .addComponent(txTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(32, Short.MAX_VALUE))
        );
        panelTotalLayout.setVerticalGroup(
            panelTotalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTotalLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(panelTotalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnSimpan.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/simpan-icon.png"))); // NOI18N
        btnSimpan.setText("Simpan ");
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelJudul, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(idSupp)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txIDSupp, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(namaSupp)
                        .addGap(18, 18, 18)
                        .addComponent(txNamaSupp, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(noBL)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txNoBL, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(txTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(46, 46, 46))))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelInput, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 769, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnHapus)
                    .addComponent(btnSimpan))
                .addContainerGap(53, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(panelJudul, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(noBL)
                    .addComponent(jLabel5)
                    .addComponent(txNoBL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(idSupp)
                            .addComponent(txIDSupp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(25, 25, 25)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(namaSupp)
                            .addComponent(txNamaSupp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(31, 31, 31)
                        .addComponent(panelInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnHapus)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(33, 33, 33)
                        .addComponent(btnSimpan))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(panelTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(98, 98, 98))
        );

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
    private javax.swing.JLabel idSupp;
    private javax.swing.JLabel jLabel10;
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
    private javax.swing.JTextField txHarga;
    private javax.swing.JTextField txIDBarang;
    private javax.swing.JTextField txIDSupp;
    private javax.swing.JTextField txJumlah;
    private javax.swing.JTextField txNamaBarang;
    private javax.swing.JTextField txNamaSupp;
    private javax.swing.JTextField txNoBL;
    private javax.swing.JTextField txTanggal;
    private javax.swing.JTextField txTotal;
    // End of variables declaration//GEN-END:variables
}
