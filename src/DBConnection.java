import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    public static Connection getConnection() {
        // Tên DB là ql_banhang (viết thường như trên phpMyAdmin)
        String url = "jdbc:mysql://localhost:3306/ql_banhang?useSSL=false&serverTimezone=UTC&characterEncoding=UTF-8";
        String user = "root";
        String password = ""; 

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Hàm main để test kết nối
    public static void main(String[] args) {
        if (getConnection() != null) {
            System.out.println("KẾT NỐI DATABASE THÀNH CÔNG!");
        } else {
            System.out.println("KẾT NỐI THẤT BẠI! Kiểm tra lại XAMPP hoặc tên DB.");
        }
    }
}