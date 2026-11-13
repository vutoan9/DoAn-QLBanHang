import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class FormKhachHang extends javax.swing.JFrame {
    
    private KhachHangDAO dao = new KhachHangDAO();
    private DefaultTableModel model;

    private JTextField txtMaKH, txtHoTen, txtEmail, txtSDT, txtDiaChi, txtTimKiem;
    private JTable table;

    public FormKhachHang() {
        setTitle("Quản Lý Khách Hàng");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new java.awt.BorderLayout(10, 10));

        // 1. Panel Nhập Liệu
        JPanel panelInput = new JPanel(new java.awt.GridLayout(3, 4, 10, 10));
        panelInput.setBorder(BorderFactory.createTitledBorder("Thông tin khách hàng"));
        
        panelInput.add(new JLabel("Mã KH (Tự động):")); txtMaKH = new JTextField(); txtMaKH.setEditable(false); panelInput.add(txtMaKH);
        panelInput.add(new JLabel("Họ Tên:")); txtHoTen = new JTextField(); panelInput.add(txtHoTen);
        panelInput.add(new JLabel("Email:")); txtEmail = new JTextField(); panelInput.add(txtEmail);
        panelInput.add(new JLabel("Số điện thoại:")); txtSDT = new JTextField(); panelInput.add(txtSDT);
        panelInput.add(new JLabel("Địa chỉ:")); txtDiaChi = new JTextField(); panelInput.add(txtDiaChi);
        panelInput.add(new JLabel("Tìm kiếm (Tên/SĐT):")); txtTimKiem = new JTextField(); panelInput.add(txtTimKiem);

        add(panelInput, java.awt.BorderLayout.NORTH);

        // 2. Bảng hiển thị
        model = new DefaultTableModel(new String[]{"Mã KH", "Họ Tên", "Email", "Số ĐT", "Địa Chỉ", "Ngày ĐK"}, 0);
        table = new JTable(model);
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
        loadData();

        table.getSelectionModel().addListSelectionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                txtMaKH.setText(model.getValueAt(row, 0).toString());
                txtHoTen.setText(model.getValueAt(row, 1).toString());
                txtEmail.setText(model.getValueAt(row, 2).toString());
                txtSDT.setText(model.getValueAt(row, 3).toString());
                txtDiaChi.setText(model.getValueAt(row, 4).toString());
            }
        });

        btnThem.addActionListener(e -> {
            if (txtHoTen.getText().isEmpty() || txtEmail.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Tên và Email không được để trống!");
                return;
            }
            KhachHang kh = new KhachHang(0, txtHoTen.getText(), txtEmail.getText(), txtSDT.getText(), txtDiaChi.getText(), null);
            if (dao.them(kh)) {
                JOptionPane.showMessageDialog(this, "Thêm thành công!");
                loadData();
            }
        });

        btnSua.addActionListener(e -> {
            if (txtMaKH.getText().isEmpty()) return;
            KhachHang kh = new KhachHang(Integer.parseInt(txtMaKH.getText()), txtHoTen.getText(), txtEmail.getText(), txtSDT.getText(), txtDiaChi.getText(), null);
            if (dao.sua(kh)) {
                JOptionPane.showMessageDialog(this, "Sửa thành công!");
                loadData();
            }
        });

        btnXoa.addActionListener(e -> {
            if (txtMaKH.getText().isEmpty()) return;
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                if (dao.xoa(Integer.parseInt(txtMaKH.getText()))) {
                    JOptionPane.showMessageDialog(this, "Xóa thành công!");
                    loadData();
                }
            }
        });

        btnTim.addActionListener(e -> {
            model.setRowCount(0);
            List<KhachHang> list = dao.timKiem(txtTimKiem.getText());
            for (KhachHang kh : list) {
                model.addRow(new Object[]{kh.getMaKH(), kh.getHoTen(), kh.getEmail(), kh.getSoDienThoai(), kh.getDiaChi(), kh.getNgayDangKy()});
            }
        });

        btnLamMoi.addActionListener(e -> {
            loadData();
            txtMaKH.setText(""); txtHoTen.setText(""); txtEmail.setText(""); 
            txtSDT.setText(""); txtDiaChi.setText(""); txtTimKiem.setText("");
        });
    }

    private void loadData() {
        model.setRowCount(0);
        List<KhachHang> list = dao.getAll();
        for (KhachHang kh : list) {
            model.addRow(new Object[]{kh.getMaKH(), kh.getHoTen(), kh.getEmail(), kh.getSoDienThoai(), kh.getDiaChi(), kh.getNgayDangKy()});
        }
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> new FormKhachHang().setVisible(true));
    }
}