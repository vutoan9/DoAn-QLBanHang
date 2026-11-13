public class SanPham {
    private int maSP;
    private String tenSP;
    private double donGia;
    private int soLuongTon;
    private String trangThai;

    public SanPham(int maSP, String tenSP, double donGia, int soLuongTon, String trangThai) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.donGia = donGia;
        this.soLuongTon = soLuongTon;
        this.trangThai = trangThai;
    }
    
    // Getter và Setter
    public int getMaSP() { return maSP; }
    public String getTenSP() { return tenSP; }
    public double getDonGia() { return donGia; }
    public int getSoLuongTon() { return soLuongTon; }
    public String getTrangThai() { return trangThai; }
}