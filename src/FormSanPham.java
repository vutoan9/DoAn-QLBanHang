import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class FormSanPham extends javax.swing.JFrame {
    java.text.DecimalFormat df = new java.text.DecimalFormat("#,##0");
    private SanPhamDAO dao = new SanPhamDAO();
    private DefaultTableModel model;

    // Các component
    private JTextField txtMaSP, txtTenSP, txtDonGia, txtSoLuong, txtTrangThai, txtTimKiem;
    private JTable table;

    public FormSanPham() {
        setTitle("Quản Lý Sản Phẩm");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new java.awt.BorderLayout(10, 10));

        // 1. Panel Nhập Liệu
        JPanel panelInput = new JPanel(new java.awt.GridLayout(3, 4, 10, 10));
        panelInput.setBorder(BorderFactory.createTitledBorder("Thông tin sản phẩm"));
        
        panelInput.add(new JLabel("Mã SP (Tự động):")); txtMaSP = new JTextField(); txtMaSP.setEditable(false); panelInput.add(txtMaSP);
        panelInput.add(new JLabel("Tên SP:")); txtTenSP = new JTextField(); panelInput.add(txtTenSP);
        panelInput.add(new JLabel("Đơn giá:")); txtDonGia = new JTextField(); panelInput.add(txtDonGia);
        panelInput.add(new JLabel("Số lượng tồn:")); txtSoLuong = new JTextField(); panelInput.add(txtSoLuong);
        panelInput.add(new JLabel("Trạng thái:")); txtTrangThai = new JTextField("Đang kinh doanh"); panelInput.add(txtTrangThai);
        panelInput.add(new JLabel("Tìm kiếm (Tên):")); txtTimKiem = new JTextField(); panelInput.add(txtTimKiem);

        add(panelInput, java.awt.BorderLayout.NORTH);

        // 2. Bảng hiển thị
        model = new DefaultTableModel(new String[]{"Mã SP", "Tên SP", "Đơn giá", "Số lượng tồn", "Trạng thái"}, 0);
        table = new JTable(model);
        // Chỉnh độ rộng cột: Mã SP, Tên SP, Đơn giá, Số lượng, Trạng thái
        table.getColumnModel().getColumn(0).setPreferredWidth(50);  // Mã SP
        table.getColumnModel().getColumn(1).setPreferredWidth(300); // Tên SP (cho rộng ra)
        table.getColumnModel().getColumn(2).setPreferredWidth(120); // Đơn giá
        table.getColumnModel().getColumn(3).setPreferredWidth(80);  // Số lượng
        table.getColumnModel().getColumn(4).setPreferredWidth(120); // Trạng thái
        add(new JScrollPane(table), java.awt.BorderLayout.CENTER);

        // 3. Panel Nút bấm
        JPanel panelBtn = new JPanel();
        JButton btnThem = new JButton("Thêm");
        JButton btnSua = new JButton("Sửa");
        JButton btnXoa = new JButton("Xóa");
        JButton btnTim = new JButton("Tìm");
        JButton btnLamMoi = new JButton("Làm mới");

        panelBtn.add(btnThem); panelBtn.add(btnSua); panelBtn.add(btnXoa); 
        panelBtn.add(btnTim); panelBtn.add(btnLamMoi);
        add(panelBtn, java.awt.BorderLayout.SOUTH);

        // ====== XỬ LÝ SỰ KIỆN ======
        
        // Load dữ liệu khi mở form
        loadData();

        // Chọn dòng trên bảng -> hiển thị lên textbox
                // Chọn dòng trên bảng -> hiển thị lên textbox
        table.getSelectionModel().addListSelectionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                txtMaSP.setText(model.getValueAt(row, 0).toString());
                txtTenSP.setText(model.getValueAt(row, 1).toString());
                
                // THÊM DẤU PHẨY BỎ ĐI Ở ĐÂY ĐỂ TEXTBOX HIỂN THỊ SỐ BÌNH THƯỜNG
                String giaString = model.getValueAt(row, 2).toString().replace(",", ""); 
                txtDonGia.setText(giaString);
                
                txtSoLuong.setText(model.getValueAt(row, 3).toString());
                txtTrangThai.setText(model.getValueAt(row, 4).toString());
            }
        });

        // Nút Thêm
        btnThem.addActionListener(e -> {
            try {
                SanPham sp = new SanPham(0, txtTenSP.getText(), 
                        Double.parseDouble(txtDonGia.getText()), 
                        Integer.parseInt(txtSoLuong.getText()), txtTrangThai.getText());
                if (dao.them(sp)) {
                    JOptionPane.showMessageDialog(this, "Thêm thành công!");
                    loadData();
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi nhập liệu! Kiểm tra lại giá/số lượng.");
            }
        });

        // Nút Sửa
        btnSua.addActionListener(e -> {
            try {
                if (txtMaSP.getText().isEmpty()) return;
                SanPham sp = new SanPham(Integer.parseInt(txtMaSP.getText()), txtTenSP.getText(), 
                        Double.parseDouble(txtDonGia.getText()), 
                        Integer.parseInt(txtSoLuong.getText()), txtTrangThai.getText());
                if (dao.sua(sp)) {
                    JOptionPane.showMessageDialog(this, "Sửa thành công!");
                    loadData();
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi nhập liệu!");
            }
        });

        // Nút Xóa
        btnXoa.addActionListener(e -> {
            if (txtMaSP.getText().isEmpty()) return;
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                if (dao.xoa(Integer.parseInt(txtMaSP.getText()))) {
                    JOptionPane.showMessageDialog(this, "Xóa thành công!");
                    loadData();
                }
            }
        });

        // Nút Tìm kiếm
        btnTim.addActionListener(e -> {
            model.setRowCount(0);
            List<SanPham> list = dao.timKiem(txtTimKiem.getText());
            for (SanPham sp : list) {
                model.addRow(new Object[]{sp.getMaSP(), sp.getTenSP(), sp.getDonGia(), sp.getSoLuongTon(), sp.getTrangThai()});
            }
        });

        // Nút Làm mới
        btnLamMoi.addActionListener(e -> {
            loadData();
            txtMaSP.setText(""); txtTenSP.setText(""); txtDonGia.setText(""); 
            txtSoLuong.setText(""); txtTimKiem.setText("");
        });
    }

    private void loadData() {
        model.setRowCount(0);
        List<SanPham> list = dao.getAll();
        for (SanPham sp : list) {
        model.addRow(new Object[]{sp.getMaSP(), sp.getTenSP(), df.format(sp.getDonGia()), sp.getSoLuongTon(), sp.getTrangThai()});        }
    }

    // Hàm main để chạy trực tiếp form này
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> new FormSanPham().setVisible(true));
    }
}