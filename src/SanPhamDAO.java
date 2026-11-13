import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SanPhamDAO {
    // Lấy danh sách
    public List<SanPham> getAll() {
        List<SanPham> list = new ArrayList<>();
        String sql = "SELECT * FROM SanPham";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new SanPham(
                        rs.getInt("MaSP"), 
                        rs.getString("TenSP"), 
                        rs.getDouble("DonGia"), // Lấy double bình thường
                        rs.getInt("SoLuongTon"), 
                        rs.getString("TrangThai")
                ));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    // Tìm kiếm
    public List<SanPham> timKiem(String keyword) {
        List<SanPham> list = new ArrayList<>();
        String sql = "SELECT * FROM SanPham WHERE TenSP LIKE ?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, "%" + keyword + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new SanPham(
                        rs.getInt("MaSP"), 
                        rs.getString("TenSP"), 
                        rs.getDouble("DonGia"), 
                        rs.getInt("SoLuongTon"), 
                        rs.getString("TrangThai")
                ));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    public boolean them(SanPham sp) {
        String sql = "INSERT INTO SanPham (TenSP, MaDM, DonGia, SoLuongTon, TrangThai) VALUES (?, 1, ?, ?, ?)";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, sp.getTenSP());
            ps.setDouble(2, sp.getDonGia());
            ps.setInt(3, sp.getSoLuongTon());
            ps.setString(4, sp.getTrangThai());
            return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    public boolean sua(SanPham sp) {
        String sql = "UPDATE SanPham SET TenSP=?, DonGia=?, SoLuongTon=?, TrangThai=? WHERE MaSP=?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, sp.getTenSP());
            ps.setDouble(2, sp.getDonGia());
            ps.setInt(3, sp.getSoLuongTon());
            ps.setString(4, sp.getTrangThai());
            ps.setInt(5, sp.getMaSP());
            return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    public boolean xoa(int maSP) {
        String sql = "DELETE FROM SanPham WHERE MaSP=?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, maSP);
            return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }
}