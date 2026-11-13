import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DonHangDAO {
    
    // Lấy danh sách đơn hàng (Kết hợp bảng KhachHang để lấy tên)
    public List<DonHang> getAll() {
        List<DonHang> list = new ArrayList<>();
        String sql = "SELECT dh.MaDH, dh.MaKH, kh.HoTen, dh.NgayDat, dh.TrangThai, dh.TongTien " +
                     "FROM DonHang dh JOIN KhachHang kh ON dh.MaKH = kh.MaKH ORDER BY dh.MaDH DESC";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new DonHang(
                        rs.getInt("MaDH"), rs.getInt("MaKH"), rs.getString("HoTen"),
                        rs.getString("NgayDat"), rs.getString("TrangThai"), rs.getDouble("TongTien")
                ));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    // 1. Tạo đơn hàng mới (Trạng thái mặc định: Chờ xử lý)
    public boolean taoDonHang(int maKH) {
        String sql = "INSERT INTO DonHang (MaKH, TrangThai) VALUES (?, 'Chờ xử lý')";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, maKH);
            return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    // 2. Lấy Mã Đơn Hàng vừa mới tạo (để thêm chi tiết)
    public int getLastInsertedID() {
        int id = 0;
        String sql = "SELECT MAX(MaDH) AS MaxID FROM DonHang";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) id = rs.getInt("MaxID");
        } catch (Exception e) { e.printStackTrace(); }
        return id;
    }

    // 3. Thêm sản phẩm vào chi tiết đơn hàng (Trigger SQL sẽ tự động trừ kho)
    public boolean themChiTiet(int maDH, int maSP, int soLuong, double donGia) {
        String sql = "INSERT INTO ChiTietDonHang (MaDH, MaSP, SoLuong, DonGiaBan) VALUES (?, ?, ?, ?)";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, maDH);
            ps.setInt(2, maSP);
            ps.setInt(3, soLuong);
            ps.setDouble(4, donGia);
            return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    // 4. Cập nhật lại Tổng Tiền cho Đơn Hàng (Gọi sau khi thêm chi tiết)
    public void capNhatTongTien(int maDH) {
        String sql = "UPDATE DonHang dh SET TongTien = (" +
                     "  SELECT IFNULL(SUM(SoLuong * DonGiaBan), 0) " +
                     "  FROM ChiTietDonHang ct WHERE ct.MaDH = dh.MaDH" +
                     ") WHERE dh.MaDH = ?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, maDH);
            ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }
    
    // 5. Lấy danh sách sản phẩm (Để đưa vào ComboBox)
    public List<Object[]> getDanhSachSanPham() {
        List<Object[]> list = new ArrayList<>();
        String sql = "SELECT MaSP, TenSP, DonGia, SoLuongTon FROM SanPham WHERE TrangThai = 'Đang kinh doanh'";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Object[]{rs.getInt("MaSP"), rs.getString("TenSP"), rs.getDouble("DonGia"), rs.getInt("SoLuongTon")});
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }
}