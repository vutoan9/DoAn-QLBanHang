import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {

    public Main() {
        setTitle("Hệ Thống Quản Lý Bán Hàng");
        setSize(1000, 700); // Kích thước to ra để chứa bảng
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Tạo các Tab
        JTabbedPane tabbedPane = new JTabbedPane();

        // Nhúng các Form vào từng Tab
        // Lưu ý: Phải đảm bảo các class Form này đã được tạo và không bị lỗi đỏ
        tabbedPane.addTab("Quản Lý Sản Phẩm", new FormSanPham().getContentPane());
        tabbedPane.addTab("Quản Lý Khách Hàng", new FormKhachHang().getContentPane());
        tabbedPane.addTab("Quản Lý Đơn Hàng", new FormDonHang().getContentPane());

        // Thêm Tab vào cửa sổ chính
        add(tabbedPane);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {}

        java.awt.EventQueue.invokeLater(() -> new Main().setVisible(true));
    }
}