import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class KhachHangDAO {
    
    public List<KhachHang> getAll() {
        List<KhachHang> list = new ArrayList<>();
        String sql = "SELECT * FROM KhachHang";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new KhachHang(
                        rs.getInt("MaKH"), rs.getString("HoTen"), rs.getString("Email"),
                        rs.getString("SoDienThoai"), rs.getString("DiaChi"), rs.getString("NgayDangKy")
                ));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    public boolean them(KhachHang kh) {
        String sql = "INSERT INTO KhachHang (HoTen, Email, SoDienThoai, DiaChi) VALUES (?, ?, ?, ?)";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, kh.getHoTen());
            ps.setString(2, kh.getEmail());
            ps.setString(3, kh.getSoDienThoai());
            ps.setString(4, kh.getDiaChi());
            return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    public boolean sua(KhachHang kh) {
        String sql = "UPDATE KhachHang SET HoTen=?, Email=?, SoDienThoai=?, DiaChi=? WHERE MaKH=?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, kh.getHoTen());
            ps.setString(2, kh.getEmail());
            ps.setString(3, kh.getSoDienThoai());
            ps.setString(4, kh.getDiaChi());
            ps.setInt(5, kh.getMaKH());
            return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    public boolean xoa(int maKH) {
        String sql = "DELETE FROM KhachHang WHERE MaKH=?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, maKH);
            return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    public List<KhachHang> timKiem(String keyword) {
        List<KhachHang> list = new ArrayList<>();
        String sql = "SELECT * FROM KhachHang WHERE HoTen LIKE ? OR SoDienThoai LIKE ?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, "%" + keyword + "%");
            ps.setString(2, "%" + keyword + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new KhachHang(
                        rs.getInt("MaKH"), rs.getString("HoTen"), rs.getString("Email"),
                        rs.getString("SoDienThoai"), rs.getString("DiaChi"), rs.getString("NgayDangKy")
                ));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }
}