-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th10 13, 2026 lúc 06:21 PM
-- Phiên bản máy phục vụ: 10.4.32-MariaDB
-- Phiên bản PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `ql_banhang`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `chitietdonhang`
--

CREATE TABLE `chitietdonhang` (
  `MaDH` int(11) NOT NULL,
  `MaSP` int(11) NOT NULL,
  `SoLuong` int(11) NOT NULL CHECK (`SoLuong` > 0),
  `DonGiaBan` double NOT NULL CHECK (`DonGiaBan` >= 0)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `chitietdonhang`
--

INSERT INTO `chitietdonhang` (`MaDH`, `MaSP`, `SoLuong`, `DonGiaBan`) VALUES
(1, 1, 1, 30000000),
(1, 5, 2, 5000000),
(2, 2, 1, 25000000),
(3, 3, 1, 35000000),
(3, 12, 1, 2500000),
(4, 4, 1, 28000000),
(5, 8, 1, 22000000),
(6, 6, 3, 200000),
(6, 7, 1, 500000),
(7, 9, 1, 18000000),
(8, 1, 1, 30000000),
(8, 11, 1, 38000000),
(9, 5, 1, 5000000),
(9, 12, 2, 2500000),
(10, 3, 1, 35000000),
(11, 10, 1, 42000000),
(12, 4, 1, 28000000),
(13, 2, 1, 25000000),
(13, 8, 1, 22000000),
(14, 6, 5, 200000),
(14, 7, 2, 500000),
(15, 1, 1, 30000000);

--
-- Bẫy `chitietdonhang`
--
DELIMITER $$
CREATE TRIGGER `trg_SuaChiTiet` AFTER UPDATE ON `chitietdonhang` FOR EACH ROW BEGIN
    UPDATE SanPham
    SET SoLuongTon = SoLuongTon + OLD.SoLuong - NEW.SoLuong
    WHERE MaSP = NEW.MaSP;
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `trg_ThemChiTiet` AFTER INSERT ON `chitietdonhang` FOR EACH ROW BEGIN
    UPDATE SanPham
    SET SoLuongTon = SoLuongTon - NEW.SoLuong
    WHERE MaSP = NEW.MaSP;
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `trg_XoaChiTiet` AFTER DELETE ON `chitietdonhang` FOR EACH ROW BEGIN
    UPDATE SanPham
    SET SoLuongTon = SoLuongTon + OLD.SoLuong
    WHERE MaSP = OLD.MaSP;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `danhmuc`
--

CREATE TABLE `danhmuc` (
  `MaDM` int(11) NOT NULL,
  `TenDM` varchar(100) NOT NULL,
  `MoTa` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `danhmuc`
--

INSERT INTO `danhmuc` (`MaDM`, `TenDM`, `MoTa`) VALUES
(1, 'Điện thoại', 'Các loại điện thoại thông minh'),
(2, 'Laptop', 'Máy tính xách tay'),
(3, 'Phụ kiện', 'Tai nghe, sạc, ốp lưng...'),
(4, 'Máy tính bảng', 'iPad, Samsung Tab...'),
(5, 'Đồng hồ thông minh', 'Apple Watch, Galaxy Watch...');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `donhang`
--

CREATE TABLE `donhang` (
  `MaDH` int(11) NOT NULL,
  `MaKH` int(11) NOT NULL,
  `NgayDat` datetime DEFAULT current_timestamp(),
  `TrangThai` varchar(50) DEFAULT 'Chờ xử lý',
  `TongTien` double DEFAULT 0 CHECK (`TongTien` >= 0)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `donhang`
--

INSERT INTO `donhang` (`MaDH`, `MaKH`, `NgayDat`, `TrangThai`, `TongTien`) VALUES
(1, 1, '2024-01-05 09:00:00', 'Hoàn tất', 40000000),
(2, 1, '2024-01-12 14:00:00', 'Hoàn tất', 25000000),
(3, 1, '2024-02-10 10:00:00', 'Hoàn tất', 37500000),
(4, 2, '2024-01-15 11:00:00', 'Hoàn tất', 28000000),
(5, 2, '2024-02-20 16:00:00', 'Chờ xử lý', 22000000),
(6, 3, '2024-01-20 08:30:00', 'Hoàn tất', 1100000),
(7, 3, '2024-03-05 13:00:00', 'Hoàn tất', 18000000),
(8, 4, '2024-02-01 10:00:00', 'Hoàn tất', 68000000),
(9, 5, '2024-02-15 15:00:00', 'Hoàn tất', 10000000),
(10, 5, '2024-03-10 09:00:00', 'Chờ xử lý', 35000000),
(11, 6, '2024-03-12 14:30:00', 'Hoàn tất', 42000000),
(12, 7, '2024-03-15 16:00:00', 'Đã hủy', 28000000),
(13, 8, '2024-03-18 10:00:00', 'Hoàn tất', 47000000),
(14, 9, '2024-03-20 11:00:00', 'Hoàn tất', 2000000),
(15, 10, '2024-03-22 09:00:00', 'Chờ xử lý', 30000000);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `khachhang`
--

CREATE TABLE `khachhang` (
  `MaKH` int(11) NOT NULL,
  `HoTen` varchar(100) NOT NULL,
  `Email` varchar(100) NOT NULL,
  `SoDienThoai` varchar(20) DEFAULT NULL,
  `DiaChi` varchar(255) DEFAULT NULL,
  `NgayDangKy` date DEFAULT curdate()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `khachhang`
--

INSERT INTO `khachhang` (`MaKH`, `HoTen`, `Email`, `SoDienThoai`, `DiaChi`, `NgayDangKy`) VALUES
(1, 'Nguyễn Văn A', 'a.nguyen@email.com', '0901234567', 'Hà Nội', '2023-12-01'),
(2, 'Trần Thị B', 'b.tran@email.com', '0912345678', 'TP. HCM', '2023-12-05'),
(3, 'Lê Văn C', 'c.le@email.com', '0923456789', 'Đà Nẵng', '2023-12-10'),
(4, 'Phạm Thị D', 'd.pham@email.com', '0934567890', 'Hải Phòng', '2023-12-15'),
(5, 'Hoàng Văn E', 'e.hoang@email.com', '0945678901', 'Cần Thơ', '2024-01-02'),
(6, 'Vũ Thị F', 'f.vu@email.com', '0956789012', 'Nha Trang', '2024-01-08'),
(7, 'Đặng Văn G', 'g.dang@email.com', '0967890123', 'Huế', '2024-01-20'),
(8, 'Bùi Thị H', 'h.bui@email.com', '0978901234', 'Quảng Ninh', '2024-02-01'),
(9, 'Ngô Văn I', 'i.ngo@email.com', '0989012345', 'Bắc Ninh', '2024-02-10'),
(10, 'Dương Thị K', 'k.duong@email.com', '0990123456', 'Thanh Hóa', '2024-03-01');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `sanpham`
--

CREATE TABLE `sanpham` (
  `MaSP` int(11) NOT NULL,
  `TenSP` varchar(200) NOT NULL,
  `MaDM` int(11) NOT NULL,
  `DonGia` double NOT NULL CHECK (`DonGia` >= 0),
  `SoLuongTon` int(11) NOT NULL DEFAULT 0 CHECK (`SoLuongTon` >= 0),
  `TrangThai` varchar(50) DEFAULT 'Đang kinh doanh'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `sanpham`
--

INSERT INTO `sanpham` (`MaSP`, `TenSP`, `MaDM`, `DonGia`, `SoLuongTon`, `TrangThai`) VALUES
(1, 'iPhone 15 Pro', 1, 30000000, 50, 'Đang kinh doanh'),
(2, 'Samsung Galaxy S24', 1, 25000000, 30, 'Đang kinh doanh'),
(3, 'Dell XPS 13', 2, 35000000, 20, 'Đang kinh doanh'),
(4, 'MacBook Air M3', 2, 28000000, 15, 'Đang kinh doanh'),
(5, 'Tai nghe AirPods Pro', 3, 5000000, 100, 'Đang kinh doanh'),
(6, 'Ốp lưng Silicon', 3, 200000, 500, 'Đang kinh doanh'),
(7, 'Sạc dự phòng 10000mAh', 3, 500000, 200, 'Ngừng kinh doanh'),
(8, 'Xiaomi 14 Ultra', 1, 22000000, 40, 'Đang kinh doanh'),
(9, 'OPPO Find X7', 1, 18000000, 25, 'Đang kinh doanh'),
(10, 'Asus ROG Zephyrus G14', 2, 42000000, 10, 'Đang kinh doanh'),
(11, 'Lenovo ThinkPad X1 Carbon', 2, 38000000, 12, 'Đang kinh doanh'),
(12, 'Chuột không dây Logitech MX Master 3S', 3, 2500000, 80, 'Đang kinh doanh');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `thanhtoan`
--

CREATE TABLE `thanhtoan` (
  `MaTT` int(11) NOT NULL,
  `MaDH` int(11) NOT NULL,
  `NgayThanhToan` datetime DEFAULT current_timestamp(),
  `SoTien` double NOT NULL CHECK (`SoTien` >= 0),
  `PhuongThuc` varchar(50) DEFAULT NULL,
  `TrangThai` varchar(50) DEFAULT 'Thành công'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `thanhtoan`
--

INSERT INTO `thanhtoan` (`MaTT`, `MaDH`, `NgayThanhToan`, `SoTien`, `PhuongThuc`, `TrangThai`) VALUES
(1, 1, '2024-01-05 09:10:00', 40000000, 'Chuyển khoản', 'Thành công'),
(2, 2, '2024-01-12 14:10:00', 25000000, 'Tiền mặt', 'Thành công'),
(3, 3, '2024-02-10 10:10:00', 37500000, 'Chuyển khoản', 'Thành công'),
(4, 4, '2024-01-15 11:10:00', 28000000, 'Tiền mặt', 'Thành công'),
(5, 5, '2024-02-20 16:10:00', 22000000, 'Chuyển khoản', 'Thất bại'),
(6, 6, '2024-01-20 08:40:00', 1100000, 'Tiền mặt', 'Thành công'),
(7, 7, '2024-03-05 13:10:00', 18000000, 'Chuyển khoản', 'Thành công'),
(8, 8, '2024-02-01 10:10:00', 68000000, 'Chuyển khoản', 'Thành công'),
(9, 9, '2024-02-15 15:10:00', 10000000, 'Tiền mặt', 'Thành công'),
(10, 10, '2024-03-10 09:10:00', 35000000, 'Chuyển khoản', 'Thất bại'),
(11, 11, '2024-03-12 14:40:00', 42000000, 'Chuyển khoản', 'Thành công'),
(12, 13, '2024-03-18 10:10:00', 47000000, 'Chuyển khoản', 'Thành công'),
(13, 14, '2024-03-20 11:10:00', 2000000, 'Tiền mặt', 'Thành công');

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `chitietdonhang`
--
ALTER TABLE `chitietdonhang`
  ADD PRIMARY KEY (`MaDH`,`MaSP`),
  ADD KEY `MaSP` (`MaSP`);

--
-- Chỉ mục cho bảng `danhmuc`
--
ALTER TABLE `danhmuc`
  ADD PRIMARY KEY (`MaDM`);

--
-- Chỉ mục cho bảng `donhang`
--
ALTER TABLE `donhang`
  ADD PRIMARY KEY (`MaDH`),
  ADD KEY `MaKH` (`MaKH`);

--
-- Chỉ mục cho bảng `khachhang`
--
ALTER TABLE `khachhang`
  ADD PRIMARY KEY (`MaKH`),
  ADD UNIQUE KEY `Email` (`Email`);

--
-- Chỉ mục cho bảng `sanpham`
--
ALTER TABLE `sanpham`
  ADD PRIMARY KEY (`MaSP`),
  ADD KEY `MaDM` (`MaDM`);

--
-- Chỉ mục cho bảng `thanhtoan`
--
ALTER TABLE `thanhtoan`
  ADD PRIMARY KEY (`MaTT`),
  ADD KEY `MaDH` (`MaDH`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `danhmuc`
--
ALTER TABLE `danhmuc`
  MODIFY `MaDM` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT cho bảng `donhang`
--
ALTER TABLE `donhang`
  MODIFY `MaDH` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT cho bảng `khachhang`
--
ALTER TABLE `khachhang`
  MODIFY `MaKH` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT cho bảng `sanpham`
--
ALTER TABLE `sanpham`
  MODIFY `MaSP` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT cho bảng `thanhtoan`
--
ALTER TABLE `thanhtoan`
  MODIFY `MaTT` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `chitietdonhang`
--
ALTER TABLE `chitietdonhang`
  ADD CONSTRAINT `chitietdonhang_ibfk_1` FOREIGN KEY (`MaDH`) REFERENCES `donhang` (`MaDH`),
  ADD CONSTRAINT `chitietdonhang_ibfk_2` FOREIGN KEY (`MaSP`) REFERENCES `sanpham` (`MaSP`);

--
-- Các ràng buộc cho bảng `donhang`
--
ALTER TABLE `donhang`
  ADD CONSTRAINT `donhang_ibfk_1` FOREIGN KEY (`MaKH`) REFERENCES `khachhang` (`MaKH`);

--
-- Các ràng buộc cho bảng `sanpham`
--
ALTER TABLE `sanpham`
  ADD CONSTRAINT `sanpham_ibfk_1` FOREIGN KEY (`MaDM`) REFERENCES `danhmuc` (`MaDM`);

--
-- Các ràng buộc cho bảng `thanhtoan`
--
ALTER TABLE `thanhtoan`
  ADD CONSTRAINT `thanhtoan_ibfk_1` FOREIGN KEY (`MaDH`) REFERENCES `donhang` (`MaDH`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
