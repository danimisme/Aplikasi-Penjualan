-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 02, 2023 at 04:18 PM
-- Server version: 10.4.25-MariaDB
-- PHP Version: 7.4.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `nirwana`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE `admin` (
  `id` int(11) UNSIGNED NOT NULL,
  `full_name` varchar(100) NOT NULL,
  `user_login` varchar(20) NOT NULL,
  `email` varchar(200) NOT NULL,
  `group_user` varchar(64) NOT NULL,
  `password` varchar(64) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`id`, `full_name`, `user_login`, `email`, `group_user`, `password`) VALUES
(1, 'Muhammad Subhan R', 'admin', 'msubhanr53@gmail.com', 'System Admin', 'admin'),
(2, 'Eriel', 'eriel', 'admin', 'Staff Gudang', 'admin');

-- --------------------------------------------------------

--
-- Table structure for table `aruskas`
--

CREATE TABLE `aruskas` (
  `No` varchar(11) NOT NULL,
  `tanggal` date NOT NULL,
  `ket` text NOT NULL,
  `uangMasuk` float NOT NULL,
  `UangKeluar` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `aruskas`
--

INSERT INTO `aruskas` (`No`, `tanggal`, `ket`, `uangMasuk`, `UangKeluar`) VALUES
('TR0001', '2023-03-07', 'Setoran Modal', 50000000, 0),
('TR0002', '2023-03-07', 'Bonus Penjualan Canon', 1000000, 0),
('TR0003', '2023-04-11', 'Bonus Penjualan Epson', 20000000, 0),
('TR0004', '2023-04-14', 'Penjualan INV0004', 3000000, 0),
('TR0005', '2023-04-14', 'Penjualan INV0005', 4200000, 0),
('TR0006', '2023-04-24', 'Pembelian BL0008', 0, 7750000),
('TR0007', '2023-05-02', 'Penjualan INV0006', 4600000, 0),
('TR0008', '2023-05-03', 'Penjualan INV0007', 6600000, 0),
('TR0009', '2023-05-04', 'Pembelian BL0009', 10000000, 10000000),
('TR0010', '2023-05-02', 'Pembelian BL0010', 0, 4000000),
('TR0011', '2023-05-02', 'Upah kuli', 100000, 100000),
('TR0012', '2023-05-02', 'Penjualan INV0008', 6600000, 0),
('TR0013', '2023-05-02', 'Kelebihan Bayar Sewa', 1000000, 0);

-- --------------------------------------------------------

--
-- Table structure for table `barang`
--

CREATE TABLE `barang` (
  `IDBrg` varchar(10) NOT NULL,
  `namaBrg` varchar(25) NOT NULL,
  `jenis` varchar(10) NOT NULL,
  `merek` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `barang`
--

INSERT INTO `barang` (`IDBrg`, `namaBrg`, `jenis`, `merek`) VALUES
('PC/G2020', 'PRINTER CANON G2020', 'Printer', 'CANON'),
('PE/L3210', 'PRINTER EPSON L3210', 'Printer', 'EPSON'),
('PE/L3250', 'PRINTER EPSON L3250', 'Printer', 'EPSON'),
('PE/L1300', 'PRINTER EPSON L1300', 'Printer', 'EPSON'),
('PE/L1800', 'PRINTER EPSON L1800', 'Printer', 'EPSON'),
('PC/G1020', 'PRINTER CANON G1020', 'Printer', 'CANON'),
('PH/HP315', 'PRINTER HP 315', 'Printer', 'HP'),
('PH/HP415', 'PRINTER HP 415', 'Printer', 'HP'),
('PB/T220', 'PRINTER BROTHER T220', 'Printer', 'BROTHER'),
('PB/T420', 'PRINTER BROTHER T420', 'Printer', 'BROTHER');

-- --------------------------------------------------------

--
-- Table structure for table `customer`
--

CREATE TABLE `customer` (
  `IDCust` varchar(10) NOT NULL,
  `namaCust` varchar(25) NOT NULL,
  `alamat` varchar(50) NOT NULL,
  `noTelp` varchar(13) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `customer`
--

INSERT INTO `customer` (`IDCust`, `namaCust`, `alamat`, `noTelp`) VALUES
('CS0001', 'SUKSES MANDIRI', 'Mangga dua mall lt.4 blok a no 37', '021-38463829'),
('CS0002', 'ENTER COMPUTER', 'Mangga Dua Mall Lt. 3 No 33', '021-4348530'),
('CS0003', 'COMPUTER EXPRESS', 'Orion Dusit Lt. Dasar No 31', '021-342245323'),
('CS0004', 'MCC COMPUTER', 'Orion Dusit Lt. 1 Blok A no. 22', '021-43242342');

-- --------------------------------------------------------

--
-- Table structure for table `pembelian`
--

CREATE TABLE `pembelian` (
  `noBL` varchar(20) NOT NULL,
  `tanggal` date NOT NULL,
  `idSupp` varchar(10) NOT NULL,
  `totalBeli` decimal(10,0) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `pembelian`
--

INSERT INTO `pembelian` (`noBL`, `tanggal`, `idSupp`, `totalBeli`) VALUES
('BL0001', '2023-03-19', 'SP0001', '7000000'),
('BL0002', '2023-03-19', 'SP0002', '18200000'),
('BL0003', '2023-03-19', 'SP0003', '10500000'),
('BL0004', '2023-03-19', 'SP0002', '10425000'),
('BL0005', '2023-03-20', 'SP0002', '9300000'),
('BL0006', '2023-03-20', 'SP0003', '10500000'),
('BL0007', '2023-04-24', 'SP0003', '12250000'),
('BL0008', '2023-04-24', 'SP0004', '7750000'),
('BL0009', '2023-05-04', 'SP0003', '10000000'),
('BL0010', '2023-05-02', 'SP0001', '4000000');

-- --------------------------------------------------------

--
-- Table structure for table `penjualan`
--

CREATE TABLE `penjualan` (
  `noInv` varchar(15) NOT NULL,
  `tanggal` date NOT NULL,
  `idCust` varchar(10) NOT NULL,
  `totalJual` decimal(10,0) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `penjualan`
--

INSERT INTO `penjualan` (`noInv`, `tanggal`, `idCust`, `totalJual`) VALUES
('INV0001', '2022-09-22', 'CS0001', '2000000'),
('INV0002', '2023-03-19', 'CS0002', '1600000'),
('INV0003', '2023-04-14', 'CS0001', '4400000'),
('INV0004', '2023-04-14', 'CS0002', '3000000'),
('INV0005', '2023-04-14', 'CS0004', '4200000'),
('INV0006', '2023-05-02', 'CS0002', '4600000'),
('INV0007', '2023-05-03', 'CS0001', '6600000'),
('INV0008', '2023-05-02', 'CS0003', '6600000');

-- --------------------------------------------------------

--
-- Table structure for table `retur`
--

CREATE TABLE `retur` (
  `kode` varchar(10) NOT NULL,
  `tanggal` date NOT NULL,
  `customer` varchar(25) NOT NULL,
  `IDBrg` varchar(10) NOT NULL,
  `jumlah` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `retur`
--

INSERT INTO `retur` (`kode`, `tanggal`, `customer`, `IDBrg`, `jumlah`) VALUES
('RT0001', '2023-03-20', 'ENTER COMPUTER', 'PC/G1020', 1),
('RT0002', '2023-05-02', 'ENTER COMPUTER', 'PE/L3210', 1);

--
-- Triggers `retur`
--
DELIMITER $$
CREATE TRIGGER `retur` BEFORE INSERT ON `retur` FOR EACH ROW BEGIN

INSERT INTO stok SET IDBrg = NEW.IDBrg,
Stok = new.jumlah
ON DUPLICATE KEY UPDATE stok = Stok + new.jumlah;

END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `rincianpembelian`
--

CREATE TABLE `rincianpembelian` (
  `noBL` varchar(10) NOT NULL,
  `IDBrg` varchar(10) NOT NULL,
  `namaBrg` varchar(25) NOT NULL,
  `jumlah` int(5) NOT NULL,
  `harga` decimal(10,0) NOT NULL,
  `total` decimal(10,0) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `rincianpembelian`
--

INSERT INTO `rincianpembelian` (`noBL`, `IDBrg`, `namaBrg`, `jumlah`, `harga`, `total`) VALUES
('BL0001', 'PC/G2020', 'PRINTER CANON G2020', 5, '1990000', '9950000'),
('BL0002', 'PC/G2020', 'PRINTER CANON G2020', 2, '1990000', '3980000'),
('BL0003', 'PC/G1020', 'PRINTER CANON G1020', 5, '1400000', '7000000'),
('BL0001', 'PC/G1020', 'PRINTER CANON G1020', 5, '1400000', '7000000'),
('BL0002', 'PB/T220', 'PRINTER BROTHER T220', 10, '1820000', '18200000'),
('BL0003', 'PE/L3210', 'PRINTER EPSON L3210', 5, '2100000', '10500000'),
('BL0004', 'PB/T420', 'PRINTER BROTHER T420', 5, '2085000', '10425000'),
('BL0005', 'PB/T220', 'PRINTER BROTHER T220', 5, '1860000', '9300000'),
('BL0006', 'PE/L3210', 'PRINTER EPSON L3210', 5, '2100000', '10500000'),
('BL0007', 'PE/L3250', 'PRINTER EPSON L3250', 5, '2450000', '12250000'),
('BL0008', 'PH/HP315', 'PRINTER HP 315', 5, '1550000', '7750000'),
('BL0009', 'PE/L1300', 'PRINTER EPSON L1300', 2, '5000000', '10000000'),
('BL0010', 'PE/L3210', 'PRINTER EPSON L3210', 2, '2000000', '4000000');

--
-- Triggers `rincianpembelian`
--
DELIMITER $$
CREATE TRIGGER `TambahStok` BEFORE INSERT ON `rincianpembelian` FOR EACH ROW BEGIN

INSERT INTO stok SET IDBrg = NEW.IDBrg,
Stok = new.jumlah
ON DUPLICATE KEY UPDATE stok = Stok + new.jumlah;

END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `rincianpenjualan`
--

CREATE TABLE `rincianpenjualan` (
  `noInv` varchar(10) NOT NULL,
  `IDBrg` varchar(10) NOT NULL,
  `namaBrg` varchar(25) NOT NULL,
  `jumlah` int(5) NOT NULL,
  `harga` decimal(10,0) NOT NULL,
  `total` decimal(10,0) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `rincianpenjualan`
--

INSERT INTO `rincianpenjualan` (`noInv`, `IDBrg`, `namaBrg`, `jumlah`, `harga`, `total`) VALUES
('INV0001', 'PC/G2020', 'PRINTER CANON G2020', 1, '2000000', '2000000'),
('INV0002', 'PC/G1020', 'PRINTER CANON G1020', 1, '1600000', '1600000'),
('INV0003', 'PE/L3210', 'PRINTER EPSON L3210', 2, '2200000', '4400000'),
('INV0004', 'PC/G1020', 'PRINTER CANON G1020', 2, '1500000', '3000000'),
('INV0005', 'PB/T220', 'PRINTER BROTHER T220', 2, '2100000', '4200000'),
('INV0006', 'PE/L3210', 'PRINTER EPSON L3210', 2, '2300000', '4600000'),
('INV0007', 'PB/T220', 'PRINTER BROTHER T220', 3, '2200000', '6600000'),
('INV0008', 'PE/L3210', 'PRINTER EPSON L3210', 3, '2200000', '6600000');

--
-- Triggers `rincianpenjualan`
--
DELIMITER $$
CREATE TRIGGER `kurangStok` BEFORE INSERT ON `rincianpenjualan` FOR EACH ROW BEGIN
UPDATE stok SET Stok = stok - new.jumlah WHERE IDBrg = new.IDBrg;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `stok`
--

CREATE TABLE `stok` (
  `IDBrg` varchar(10) NOT NULL,
  `Stok` int(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `stok`
--

INSERT INTO `stok` (`IDBrg`, `Stok`) VALUES
('PB/T220', 5),
('PB/T420', 5),
('PC/G1020', 3),
('PE/L1300', 2),
('PE/L3210', 6),
('PE/L3250', 5),
('PH/HP315', 5);

-- --------------------------------------------------------

--
-- Table structure for table `supplier`
--

CREATE TABLE `supplier` (
  `IDSupp` varchar(10) NOT NULL,
  `namaSupp` varchar(25) NOT NULL,
  `alamat` varchar(50) NOT NULL,
  `noTelp` varchar(13) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `supplier`
--

INSERT INTO `supplier` (`IDSupp`, `namaSupp`, `alamat`, `noTelp`) VALUES
('SP0001', 'PT DATASCRIPT INDO', 'Ruko Grand Butik, Mangga dua selatan', '021-8364893'),
('SP0002', 'PT BROTHER INDONESIA', 'Kebon Jeruk, Jakarta Barat', '0219837281'),
('SP0003', 'PT PASIFIK DISTRIBUTOR', 'Jl. Gunung Sahari no.05', '02109729832'),
('SP0004', 'PT ECS Distribusi', 'Ruko Mangga Dua Square No E 11', '021-349203489');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `stok`
--
ALTER TABLE `stok`
  ADD PRIMARY KEY (`IDBrg`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `admin`
--
ALTER TABLE `admin`
  MODIFY `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
