/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplikasiPenjualan;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author M. Subhan Ramdhani
 */
public class koneksi {
    private static Connection koneksi;
    
    public static Connection getKoneksi(){
        if (koneksi==null){
            try{
                String url = "jdbc:mysql://localhost:3306/nirwana";
                String user = "root";
                String password = "";
                DriverManager.registerDriver(new com.mysql.jdbc.Driver());
                koneksi = DriverManager.getConnection(url, user, password);
                System.out.println("Berhasil terhubung ke database Toko Nirwana");
            } catch (Exception e){
                System.out.println("Gagal terhubung ke database Toko Nirwana");
                System.out.println("error :" + e);
            }
        } return koneksi;
    }
    
    public static void main (String[]args){
        getKoneksi();
    }
    
}
