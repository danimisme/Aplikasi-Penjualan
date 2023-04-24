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
            txTotal.getText()
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
                txTotal.setText(r.getString("totalJual"));
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
        String Jumlah = txTotal.getText();

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
        btnAdd = new javax.swing.JButton();
        btnPrev = new javax.swing.JButton();
        btnSimpan3 = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(204, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

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

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("No. Invoice        :");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setText("ID Customer       :");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel4.setText("Nama Customer  :");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel5.setText("Tanggal   :");

        txNoInvoice.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txNoInvoice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txNoInvoiceActionPerformed(evt);
            }
        });

        txIDCust.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txIDCust.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txIDCustActionPerformed(evt);
            }
        });

        txNamaCust.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txNamaCust.setEnabled(false);

        txTanggal.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txTanggal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txTanggalActionPerformed(evt);
            }
        });

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

        javax.swing.GroupLayout panelInputLayout = new javax.swing.GroupLayout(panelInput);
        panelInput.setLayout(panelInputLayout);
        panelInputLayout.setHorizontalGroup(
            panelInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInputLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(panelInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(txIDBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txNamaBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                        .addComponent(txHarga, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
                        .addComponent(btnTambah)
                        .addGap(42, 42, 42))))
        );
        panelInputLayout.setVerticalGroup(
            panelInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInputLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel6)
                        .addComponent(jLabel8)
                        .addComponent(jLabel9)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txIDBarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txNamaBarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txJumlah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txHarga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTambah))
                .addGap(34, 34, 34))
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
                .addGap(29, 29, 29)
                .addComponent(jLabel10)
                .addGap(27, 27, 27)
                .addComponent(txTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(31, Short.MAX_VALUE))
        );
        panelTotalLayout.setVerticalGroup(
            panelTotalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTotalLayout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(panelTotalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(47, Short.MAX_VALUE))
        );

        btnSimpan.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/simpan-icon.png"))); // NOI18N
        btnSimpan.setText("Simpan ");
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });

        btnAdd.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnAdd.setText("+");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnPrev.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnPrev.setText("<=");
        btnPrev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrevActionPerformed(evt);
            }
        });

        btnSimpan3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnSimpan3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/print-icon.png"))); // NOI18N
        btnSimpan3.setText("Print");
        btnSimpan3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpan3ActionPerformed(evt);
            }
        });

        btnNext.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnNext.setText("=>");
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cari-icon.png"))); // NOI18N
        jButton1.setText("Cari");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelJudul, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(txNamaCust, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txIDCust, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txNoInvoice, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30)
                                .addComponent(jButton1))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnPrev, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(102, 102, 102)
                                        .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btnSimpan3))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(262, 262, 262)))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(txTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(46, 46, 46))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(panelJudul, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnSimpan3)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnNext)
                        .addComponent(btnPrev)
                        .addComponent(btnAdd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, Short.MAX_VALUE)
                        .addGap(2, 2, 2))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(jLabel5)
                        .addComponent(txNoInvoice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(23, 23, 23)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txIDCust, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txNamaCust, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addComponent(panelInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnHapus)
                        .addGap(103, 103, 103)
                        .addComponent(btnSimpan))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
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
        String totalBeli = txTotal.getText();
        
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
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
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
    private javax.swing.JTextField txHarga;
    private javax.swing.JTextField txIDBarang;
    private javax.swing.JTextField txIDCust;
    private javax.swing.JTextField txJumlah;
    private javax.swing.JTextField txNamaBarang;
    private javax.swing.JTextField txNamaCust;
    private javax.swing.JTextField txNoInvoice;
    private javax.swing.JTextField txTanggal;
    private javax.swing.JTextField txTotal;
    // End of variables declaration//GEN-END:variables
}
