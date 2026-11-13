public class KhachHang {
    private int maKH;
    private String hoTen;
    private String email;
    private String soDienThoai;
    private String diaChi;
    private String ngayDangKy;

    public KhachHang(int maKH, String hoTen, String email, String soDienThoai, String diaChi, String ngayDangKy) {
        this.maKH = maKH;
        this.hoTen = hoTen;
        this.email = email;
        this.soDienThoai = soDienThoai;
        this.diaChi = diaChi;
        this.ngayDangKy = ngayDangKy;
    }

    public int getMaKH() { return maKH; }
    public String getHoTen() { return hoTen; }
    public String getEmail() { return email; }
    public String getSoDienThoai() { return soDienThoai; }
    public String getDiaChi() { return diaChi; }
    public String getNgayDangKy() { return ngayDangKy; }
}