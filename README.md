# Đồ án Java - Quản Lý Bán Hàng

- Ngôn ngữ: Java (Swing)
- Cơ sở dữ liệu: MySQL
- IDE: Apache NetBeans

## Hướng dẫn cài đặt và chạy:
1. Mở XAMPP, khởi động MySQL.
2. Mở phpMyAdmin (`http://localhost/phpmyadmin`), tạo database `ql_banhang` và Import file `ql_banhang.sql` (đã có trong thư mục gốc của project).
3. Mở project bằng NetBeans.
4. Chuột phải vào mục **Libraries** -> Chọn **Add JAR/Folder** -> Trỏ đến file `dist/lib/mysql-connector-j-26.10.0.jar`.
5. Mở file `Main.java` và bấm Run.

**Tài khoản kết nối MySQL mặc định:**
- Host: `localhost:3306`
- Database: `ql_banhang`
- User: `root`
- Password: 

## Chức năng đã hoàn thành:
- [x] Quản lý Sản phẩm (Thêm, Sửa, Xóa, Tìm kiếm).
- [x] Quản lý Khách hàng (Thêm, Sửa, Xóa, Tìm kiếm).
- [x] Quản lý Đơn hàng (Tạo đơn, thêm chi tiết sản phẩm, tự động tính tổng tiền và trừ tồn kho).
- [x] Giao diện Menu chính (JTabbedPane) gộp 3 form.
