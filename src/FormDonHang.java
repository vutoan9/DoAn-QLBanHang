import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class FormDonHang extends javax.swing.JFrame {
    
    private DonHangDAO dao = new DonHangDAO();
    private DefaultTableModel modelDonHang, modelSanPham;
    private JTable tableDonHang, tableSanPham;
    private JComboBox<String> cboSanPham;
    private JTextField txtMaKH, txtSoLuong;
    private List<Object[]> listSP;
    private java.text.DecimalFormat df = new java.text.DecimalFormat("#,##0");

    public FormDonHang() {
        setTitle("Quản Lý Đơn Hàng & Hóa Đơn");
        setSize(1000, 650);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new java.awt.BorderLayout(10, 10));

        // ===== PANEL TRÊN: TẠO ĐƠN HÀNG MỚI =====
        JPanel panelTop = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));
        panelTop.setBorder(BorderFactory.createTitledBorder("1. Tạo đơn hàng mới"));
        panelTop.add(new JLabel("Nhập Mã KH:"));
        txtMaKH = new JTextField(5);
        panelTop.add(txtMaKH);
        JButton btnTaoDon = new JButton("Tạo Đơn Hàng");
        panelTop.add(btnTaoDon);
        add(panelTop, java.awt.BorderLayout.NORTH);

        // ===== PANEL GIỮA: CHIA LÀM 2 BẢNG =====
        JPanel panelCenter = new JPanel(new java.awt.GridLayout(2, 1, 10, 10));

        // Bảng 1: Danh sách đơn hàng
        modelDonHang = new DefaultTableModel(new String[]{"Mã ĐH", "Mã KH", "Tên KH", "Ngày Đặt", "Trạng Thái", "Tổng Tiền"}, 0);
        tableDonHang = new JTable(modelDonHang);
        tableDonHang.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        JPanel p1 = new JPanel(new java.awt.BorderLayout());
        p1.setBorder(BorderFactory.createTitledBorder("Danh sách đơn hàng (Click để chọn)"));
        p1.add(new JScrollPane(tableDonHang), java.awt.BorderLayout.CENTER);
        panelCenter.add(p1);

        // Bảng 2: Chi tiết sản phẩm trong đơn
        modelSanPham = new DefaultTableModel(new String[]{"Mã SP", "Tên Sản Phẩm", "Đơn Giá", "Số Lượng Tồn"}, 0);
        tableSanPham = new JTable(modelSanPham);
        tableSanPham.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        JPanel p2 = new JPanel(new java.awt.BorderLayout());
        p2.setBorder(BorderFactory.createTitledBorder("2. Chọn sản phẩm để thêm vào đơn đang chọn"));
        p2.add(new JScrollPane(tableSanPham), java.awt.BorderLayout.CENTER);
        panelCenter.add(p2);

        add(panelCenter, java.awt.BorderLayout.CENTER);

        // ===== PANEL DƯỚI: THÊM CHI TIẾT =====
        JPanel panelBottom = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));
        panelBottom.setBorder(BorderFactory.createTitledBorder("3. Thêm sản phẩm vào đơn hàng đang chọn"));
        
        panelBottom.add(new JLabel("Chọn SP:"));
        cboSanPham = new JComboBox<>();
        cboSanPham.setPreferredSize(new java.awt.Dimension(250, 25));
        panelBottom.add(cboSanPham);
        
        panelBottom.add(new JLabel("Số lượng:"));
        txtSoLuong = new JTextField(5);
        panelBottom.add(txtSoLuong);
        
        JButton btnThemSP = new JButton("Thêm Vào Đơn");
        panelBottom.add(btnThemSP);
        
        JButton btnLamMoi = new JButton("Làm mới Form");
        panelBottom.add(btnLamMoi);
        
        add(panelBottom, java.awt.BorderLayout.SOUTH);

        // ===== KHỞI TẠO DỮ LIỆU =====
        loadDataDonHang();
        loadDataSanPham();
        
        // Đổ dữ liệu vào ComboBox
        for (Object[] sp : listSP) {
            cboSanPham.addItem(sp[1] + " (Tồn: " + sp[3] + ")");
        }

        // ===== SỰ KIỆN =====
        btnTaoDon.addActionListener(e -> {
            try {
                int maKH = Integer.parseInt(txtMaKH.getText());
                if (dao.taoDonHang(maKH)) {
                    JOptionPane.showMessageDialog(this, "Tạo đơn hàng thành công! Hãy chọn đơn vừa tạo để thêm SP.");
                    loadDataDonHang();
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Mã KH không hợp lệ!");
            }
        });

        btnLamMoi.addActionListener(e -> {
            loadDataDonHang();
            loadDataSanPham();
        });

        btnThemSP.addActionListener(e -> {
            int rowDH = tableDonHang.getSelectedRow();
            if (rowDH < 0) {
                JOptionPane.showMessageDialog(this, "Vui lòng click chọn 1 Đơn Hàng ở bảng trên trước!");
                return;
            }
            
            int maDH = Integer.parseInt(modelDonHang.getValueAt(rowDH, 0).toString());
            String trangThai = modelDonHang.getValueAt(rowDH, 4).toString();
            if (!trangThai.equals("Chờ xử lý")) {
                JOptionPane.showMessageDialog(this, "Chỉ có thể thêm sản phẩm vào đơn 'Chờ xử lý'!");
                return;
            }

            int indexSP = cboSanPham.getSelectedIndex();
            if (indexSP < 0) return;
            
            Object[] spChon = listSP.get(indexSP);
            int maSP = (int) spChon[0];
            double donGia = (double) spChon[2];
            int tonKho = (int) spChon[3];

            try {
                int soLuong = Integer.parseInt(txtSoLuong.getText());
                if (soLuong <= 0) throw new Exception();
                if (soLuong > tonKho) {
                    JOptionPane.showMessageDialog(this, "Số lượng vượt quá tồn kho (" + tonKho + ")!");
                    return;
                }

                // 1. Thêm chi tiết (SQL Trigger sẽ tự động trừ kho)
                if (dao.themChiTiet(maDH, maSP, soLuong, donGia)) {
                    // 2. Cập nhật lại tổng tiền
                    dao.capNhatTongTien(maDH);
                    JOptionPane.showMessageDialog(this, "Thêm sản phẩm thành công!");
                    
                    // Cập nhật lại giao diện
                    loadDataDonHang();
                    loadDataSanPham();
                    txtSoLuong.setText("");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Số lượng không hợp lệ!");
            }
        });
    }

    private void loadDataDonHang() {
        modelDonHang.setRowCount(0);
        List<DonHang> list = dao.getAll();
        for (DonHang dh : list) {
            modelDonHang.addRow(new Object[]{
                dh.getMaDH(), dh.getMaKH(), dh.getTenKH(), dh.getNgayDat(), dh.getTrangThai(), df.format(dh.getTongTien())
            });
        }
    }

    private void loadDataSanPham() {
        modelSanPham.setRowCount(0);
        listSP = dao.getDanhSachSanPham();
        for (Object[] sp : listSP) {
            modelSanPham.addRow(new Object[]{sp[0], sp[1], df.format(sp[2]), sp[3]});
        }
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> new FormDonHang().setVisible(true));
    }
}