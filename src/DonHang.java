public class DonHang {
    private int maDH;
    private int maKH;
    private String tenKH;
    private String ngayDat;
    private String trangThai;
    private double tongTien;

    public DonHang(int maDH, int maKH, String tenKH, String ngayDat, String trangThai, double tongTien) {
        this.maDH = maDH;
        this.maKH = maKH;
        this.tenKH = tenKH;
        this.ngayDat = ngayDat;
        this.trangThai = trangThai;
        this.tongTien = tongTien;
    }

    public int getMaDH() { return maDH; }
    public int getMaKH() { return maKH; }
    public String getTenKH() { return tenKH; }
    public String getNgayDat() { return ngayDat; }
    public String getTrangThai() { return trangThai; }
    public double getTongTien() { return tongTien; }
}