/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplikasiPenjualan;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.table.DefaultTableModel;
import java.sql.Statement;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
/**
 *
 * @author M.Subhan Ramdhani
 */
public class FormPenjualan extends javax.swing.JFrame {

    /**
     * Creates new form FormTransaksiPenjualan
     */
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
            String sql = "SELECT * FROM penjualan ORDER BY NoInv DESC";
            ResultSet r = s.executeQuery(sql);
            if (r.next()) {
                String NoInv = r.getString("NoInv").substring(3);
                String INV = "" +(Integer.parseInt(NoInv)+1);
                String Nol = "";
                
                if(INV.length()==1)
                {Nol = "000";}
                else if(INV.length()==2)
                {Nol = "00";}
                else if(INV.length()==3)
                {Nol = "0";}
                else if(INV.length()==4)
                {Nol = "";}
                txNoInvoice.setText("INV" + Nol + INV);
            } else {
                txNoInvoice.setText("INV0001");
            }
            r.close();
            s.close();
            clear();
            utama();
            kosong();
            txIDCust.requestFocus();
        } catch (Exception e) {
            System.out.println("autonumber error");
        }
    }
    
    
        private void terakhir(){
        try {
            Connection c = koneksi.getKoneksi();
            Statement s = c.createStatement();
            String sql = "SELECT * FROM penjualan ORDER BY NoInv DESC";
            ResultSet r = s.executeQuery(sql);
            if (r.next()) {
                String NoInvoice = r.getString("NoInv");
                txNoInvoice.setText(NoInvoice);
                loadTransaksi();
            } else {
                txNoInvoice.setText("INV0001");
            }
            r.close();
            s.close();
        } catch (Exception e) {
            System.out.println("data terakhir error " + e);
        }
        loadTransaksi();
    }
    
    public void loadData(){
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.addRow(new Object[]{
            txIDBarang.getText(),
            txNamaBarang.getText(),
            txJumlah.getText(),
            txHarga.getText(),
            txSubTotal.getText()
        });
    }
    
    private void loadTransaksi(){
               try {
            Connection c = koneksi.getKoneksi();
            Statement s = c.createStatement();
            String sql = "SELECT * FROM penjualan Where noInv = '" +  txNoInvoice.getText() + "'" ;
            System.out.print(sql);
            ResultSet r = s.executeQuery(sql);
            if (r.next()){
                txNoInvoice.setText(r.getString("noInv"));
                txTanggal.setText(r.getString("tanggal"));
                txIDCust.setText(r.getString("IDCust"));
                txSubTotal.setText(r.getString("totalJual"));
            } else {
                JOptionPane.showMessageDialog(this, "Tidak ada data");   
            }
       } catch (Exception e){
           JOptionPane.showMessageDialog(this,"Error"+ e);
       }
       idCustCek();
       loadDataPenjualan();
    }
    
      private void loadDataPenjualan(){
//        Object [] Baris = { "ID Barang","Nama Barang", "Jumlah", " Harga","Total"};
//        model = new DefaultTableModel(null, Baris);
//        jTable1.setModel(model);
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        
        String sql = "SELECT * FROM rincianpenjualan Where noInv = '" +  txNoInvoice.getText() + "'" ;
        try{
            Connection conn = new koneksi().getKoneksi();
            java.sql.Statement stat = conn.createStatement();
            ResultSet hasil = stat.executeQuery(sql);
            while (hasil.next()){
                String a = hasil.getString("IDBrg");
                String b = hasil.getString("NamaBrg");
                String c = hasil.getString("jumlah");
                String d = hasil.getString("harga");
                String e = hasil.getString("total");
                
                String [] data= {a,b,c,d,e};
                model.addRow(data);
                totalBiaya();
            }
        } catch (Exception e){
             System.out.println("Error"+e);
        }
        }
    public void kosong(){
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        
        while (model.getRowCount()>0) {
            model.removeRow(0);
        }
    }
    
    public void utama(){
        //txNoInvoice.setText("");
        txIDBarang.setText("");
        txNamaBarang.setText("");
        txHarga.setText("");
        txJumlah.setText("");
       // autonumber();
    }
    
    public void clear(){
        txIDCust.setText("");
        txNamaCust.setText("");
        txSubTotal.setText("0");
        txPpn.setText("0");
        txOngkir.setText("0");
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
        
        txSubTotal.setText(String.valueOf(total));
        
        loadData();
        totalBiaya();
        clear2();
        txIDBarang.requestFocus();
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
    public void stokBarangCek(){
        try {
            Connection c = koneksi.getKoneksi();
            Statement s = c.createStatement();
            String sql = "SELECT * FROM stok WHERE IDBRg = \"" +txIDBarang.getText()+"\"";
            
            ResultSet r = s.executeQuery(sql);
            r.next();
            int stok = r.getInt("Stok");
            int jual = Integer.parseInt(txJumlah.getText());
            if(stok < jual){
                JOptionPane.showMessageDialog(null,"Stok barang tidak cukup !!");
                txJumlah.setText("");
                txJumlah.requestFocus();
            } else{
                txHarga.requestFocus();
            }
        }catch (Exception e){
            System.out.println("IDbarang Cek Error : "+e);
        }
    }
    
    public void idCustCek(){
        try {
            Connection c = koneksi.getKoneksi();
            Statement s = c.createStatement();
            String sql = "SELECT * FROM customer WHERE IDCust = \"" +txIDCust.getText()+"\"";
            
            ResultSet r = s.executeQuery(sql);
            if(r.next()){
                String nama = r.getString("namaCust");
                txNamaCust.setText(nama);
                txIDBarang.requestFocus();
            } else {
                txIDCust.setText("");
                txIDCust.requestFocus();
                JOptionPane.showMessageDialog(null,"ID Customer Salah !!");
                
            }
            
        }catch (Exception e){
            System.out.println("IDCust Cek Error : "+e);
        }
    }
    
    public FormPenjualan() {
        initComponents();
        terakhir();
        
        
        Date date = new Date();
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
        
        txTanggal.setText(s.format(date));
        Locale locale = new Locale ("id","ID");
        Locale.setDefault(locale);
        
        //autonumber();
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
                    String stokUpdate = Integer.toString(stokAwal-jumlah);
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
                }
                
            }
            
        } catch(Exception e) {
            System.out.println("Update Stok  Error "+ e);
        }
    }
    
        public void tambahPemasukan(){
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
            System.out.println("autonumber pemasukan kas error");
        }    
            
        
        String Tanggal = txTanggal.getText();
        String Ket = "Penjualan " + txNoInvoice.getText();
        String Jumlah = txSubTotal.getText();

        try {
            Connection c = koneksi.getKoneksi();
            String sql = "INSERT INTO aruskas VALUES (?, ?, ?, ?, ?)";
            PreparedStatement p = c.prepareStatement(sql);
            p.setString(1, NoTr);
            p.setString(2, Tanggal);
            p.setString(3, Ket);
            p.setString(4, Jumlah);
            p.setString(5,"0");
            p.executeUpdate();
            p.close();
        } catch (Exception e) {
            System.out.println("Terjadi Kesalahan Menambah Data");
            System.out.println("Error : " +e);
            JOptionPane.showMessageDialog(null, "Gagal menambah data !!");
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
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txNoInvoice = new javax.swing.JTextField();
        txIDCust = new javax.swing.JTextField();
        txNamaCust = new javax.swing.JTextField();
        btn_CariCust = new javax.swing.JButton();
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
        panelTotal = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        txSubTotal = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txPpn = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txOngkir = new javax.swing.JTextField();
        txTotal = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        btnSimpan = new javax.swing.JButton();
        btnAdd = new javax.swing.JButton();
        btnPrev = new javax.swing.JButton();
        btnSimpan3 = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(204, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelJudul.setBackground(new java.awt.Color(255, 255, 255));
        panelJudul.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        Judul.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        Judul.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/jual.png"))); // NOI18N
        Judul.setText("  Transaksi Penjualan");

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

        jPanel1.add(panelJudul, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1060, -1));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("No. Invoice        :");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(34, 169, -1, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setText("ID Customer       :");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(34, 224, -1, -1));

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel4.setText("Nama Customer  :");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(34, 279, -1, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel5.setText("Tanggal   :");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(645, 169, -1, -1));

        txNoInvoice.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txNoInvoice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txNoInvoiceActionPerformed(evt);
            }
        });
        jPanel1.add(txNoInvoice, new org.netbeans.lib.awtextra.AbsoluteConstraints(193, 166, 148, -1));

        txIDCust.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txIDCust.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txIDCustActionPerformed(evt);
            }
        });
        jPanel1.add(txIDCust, new org.netbeans.lib.awtextra.AbsoluteConstraints(197, 221, 205, -1));

        txNamaCust.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txNamaCust.setEnabled(false);
        jPanel1.add(txNamaCust, new org.netbeans.lib.awtextra.AbsoluteConstraints(196, 276, 205, -1));

        btn_CariCust.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cari-icon.png"))); // NOI18N
        btn_CariCust.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_CariCustActionPerformed(evt);
            }
        });
        jPanel1.add(btn_CariCust, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 210, 40, 40));

        txTanggal.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txTanggal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txTanggalActionPerformed(evt);
            }
        });
        jPanel1.add(txTanggal, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 166, 205, -1));

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
                        .addComponent(txIDBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_CariBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 74, Short.MAX_VALUE)
                .addGroup(panelInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txNamaBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(18, 18, 18)
                .addGroup(panelInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txJumlah))
                .addGap(18, 18, 18)
                .addGroup(panelInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelInputLayout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panelInputLayout.createSequentialGroup()
                        .addComponent(txHarga, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnTambah)
                        .addGap(42, 42, 42))))
        );
        panelInputLayout.setVerticalGroup(
            panelInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInputLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txIDBarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txNamaBarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txJumlah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txHarga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnTambah))
                    .addComponent(btn_CariBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34))
        );

        jPanel1.add(panelInput, new org.netbeans.lib.awtextra.AbsoluteConstraints(17, 335, 969, 125));

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

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(17, 478, 769, 209));

        btnHapus.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/hapus-icon.png"))); // NOI18N
        btnHapus.setText("Hapus  ");
        btnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusActionPerformed(evt);
            }
        });
        jPanel1.add(btnHapus, new org.netbeans.lib.awtextra.AbsoluteConstraints(804, 478, -1, -1));

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

        jPanel1.add(panelTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 700, 800, 100));

        btnSimpan.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/simpan-icon.png"))); // NOI18N
        btnSimpan.setText("Simpan ");
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });
        jPanel1.add(btnSimpan, new org.netbeans.lib.awtextra.AbsoluteConstraints(804, 630, -1, -1));

        btnAdd.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnAdd.setText("+");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });
        jPanel1.add(btnAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(213, 101, 88, -1));

        btnPrev.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnPrev.setText("<=");
        btnPrev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrevActionPerformed(evt);
            }
        });
        jPanel1.add(btnPrev, new org.netbeans.lib.awtextra.AbsoluteConstraints(34, 101, 77, -1));

        btnSimpan3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnSimpan3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/print-icon.png"))); // NOI18N
        btnSimpan3.setText("Print");
        btnSimpan3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpan3ActionPerformed(evt);
            }
        });
        jPanel1.add(btnSimpan3, new org.netbeans.lib.awtextra.AbsoluteConstraints(359, 101, -1, -1));

        btnNext.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnNext.setText("=>");
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });
        jPanel1.add(btnNext, new org.netbeans.lib.awtextra.AbsoluteConstraints(128, 101, 76, -1));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cari-icon.png"))); // NOI18N
        jButton1.setText("Cari");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(371, 162, -1, 30));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 834, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
        // TODO add your handling code here:
        if(txNamaBarang.getText() == ""){
        idBarangCek();
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
        
        String noInv = txNoInvoice.getText();
        String tanggal = txTanggal.getText();
        String idCustomer = txIDCust.getText();
        String totalBeli = txSubTotal.getText();
        
        try {
            Connection c = koneksi.getKoneksi();
            String sql = "INSERT INTO penjualan VALUES (?, ?, ?, ?)";
            PreparedStatement p = c.prepareStatement(sql);
            p.setString(1, noInv);
            p.setString(2, tanggal);
            p.setString(3, idCustomer);
            p.setString(4, totalBeli);
            p.executeUpdate();
            p.close();
            JOptionPane.showMessageDialog(null,"Transaksi Tersimpan");
        } catch (Exception e) {
            System.out.println("simpan penjualan error" + e);
        }
        
        try {
            Connection c = koneksi.getKoneksi();
            int baris = jTable1.getRowCount();
            
            for (int i = 0; i < baris; i++) {
                String idBarang = jTable1.getValueAt(i, 0).toString();
                String namaBarang = jTable1.getValueAt(i, 1).toString();
                String jumlah = jTable1.getValueAt(i, 2).toString();
                String harga = jTable1.getValueAt(i, 3).toString();
                String total = jTable1.getValueAt(i, 4).toString();
                
                
                String sql = "INSERT INTO rincianpenjualan VALUES(?,?,?,?,?,?)";
                PreparedStatement p = c.prepareStatement(sql);
                p.setString(1, noInv);
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
       tambahPemasukan();
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void txIDBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txIDBarangActionPerformed
        // TODO add your handling code here:
        idBarangCek();
    }//GEN-LAST:event_txIDBarangActionPerformed

    private void txHargaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txHargaActionPerformed
        // TODO add your handling code here:
        if(txNamaBarang.getText() == ""){
        idBarangCek();
        txIDBarang.setText("");
        txIDBarang.requestFocus();
        } else{
            tambahTransaksi();
        }
    }//GEN-LAST:event_txHargaActionPerformed

    private void txJumlahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txJumlahActionPerformed
        // TODO add your handling code here:
        stokBarangCek();
    }//GEN-LAST:event_txJumlahActionPerformed

    private void txIDCustActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txIDCustActionPerformed
        // TODO add your handling code here:
        idCustCek();
    }//GEN-LAST:event_txIDCustActionPerformed

    private void txNoInvoiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txNoInvoiceActionPerformed
        // TODO add your handling code here:
        txIDCust.requestFocus();
    }//GEN-LAST:event_txNoInvoiceActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        // TODO add your handling code here:
        autonumber();
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevActionPerformed
        // TODO add your handling code here:
         String NoInv = txNoInvoice.getText().substring(3);
                String INV = "" +(Integer.parseInt(NoInv)-1);
                String Nol = "";
                
                if(INV.length()==1)
                {Nol = "000";}
                else if(INV.length()==2)
                {Nol = "00";}
                else if(INV.length()==3)
                {Nol = "0";}
                else if(INV.length()==4)
                {Nol = "";}
                txNoInvoice.setText("INV" + Nol + INV);
        
                loadTransaksi();
    }//GEN-LAST:event_btnPrevActionPerformed

    private void btnSimpan3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpan3ActionPerformed
        // TODO add your handling code here:
             try {
              
              //String namaFile =  "src/aplikasiPenjualan/data/Stok.jasper";
              Connection conn = new koneksi().getKoneksi();
              HashMap parameter = new HashMap();
              parameter.put("inv",txNoInvoice.getText());
              //File report_file = new File (namaFile);
              InputStream file = getClass().getResourceAsStream("/aplikasiPenjualan/data/Invoice.jrxml");
              JasperDesign jasperDesign = JRXmlLoader.load(file);
              JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
              //JasperReport jasperReport =(JasperReport) JRLoader.loadObject(report_file.getPath());
              JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameter,conn);
               //JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,parameter,conn);
              JasperViewer.viewReport(jasperPrint,false);
          }catch (Exception e){
              JOptionPane.showMessageDialog(null, e.getMessage());
           
        }
    }//GEN-LAST:event_btnSimpan3ActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        // TODO add your handling code here:
                String NoInvoice = txNoInvoice.getText().substring(3);
                String INV = "" +(Integer.parseInt(NoInvoice)+1);
                String Nol = "";
                
                if(INV.length()==1)
                {Nol = "000";}
                else if(INV.length()==2)
                {Nol = "00";}
                else if(INV.length()==3)
                {Nol = "0";}
                else if(INV.length()==4)
                {Nol = "";}
                txNoInvoice.setText("INV" + Nol + INV);
                loadTransaksi();
    }//GEN-LAST:event_btnNextActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        loadTransaksi();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txTanggalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txTanggalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txTanggalActionPerformed

    private void btn_CariBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_CariBarangActionPerformed
        // TODO add your handling code here:
        form = "penjualan";
        FormPembelian.form = "penjualan";
        new ListDataBarang().setVisible(true);
    }//GEN-LAST:event_btn_CariBarangActionPerformed

    private void btn_CariCustActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_CariCustActionPerformed
        // TODO add your handling code here:
        new ListDataCust().setVisible(true);
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
            java.util.logging.Logger.getLogger(FormPenjualan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormPenjualan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormPenjualan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormPenjualan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormPenjualan().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Judul;
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPrev;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JButton btnSimpan3;
    private javax.swing.JButton btnTambah;
    private javax.swing.JButton btn_CariBarang;
    private javax.swing.JButton btn_CariCust;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JPanel panelInput;
    private javax.swing.JPanel panelJudul;
    private javax.swing.JPanel panelTotal;
    public static javax.swing.JTextField txHarga;
    public static javax.swing.JTextField txIDBarang;
    public static javax.swing.JTextField txIDCust;
    public static javax.swing.JTextField txJumlah;
    public static javax.swing.JTextField txNamaBarang;
    public static javax.swing.JTextField txNamaCust;
    private javax.swing.JTextField txNoInvoice;
    private javax.swing.JTextField txOngkir;
    private javax.swing.JTextField txPpn;
    private javax.swing.JTextField txSubTotal;
    private javax.swing.JTextField txTanggal;
    private javax.swing.JTextField txTotal;
    // End of variables declaration//GEN-END:variables
}
