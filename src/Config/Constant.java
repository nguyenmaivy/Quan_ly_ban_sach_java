/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Config;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author MZI
 */

public class Constant {
    private static final String DB_URL = "jdbc:sqlserver://MAIVYMECHIDEP:1433;databaseName=quanlybansach;encrypt=false;";
    private static final String USERNAME = "sa";
    private static final String PASSWORD = "26031980";

    // Singleton an toàn với đa luồng
    private static volatile Constant instance;
    private Connection connection;

    // Constructor private để ngăn tạo đối tượng mới
    private Constant() {
        try {
            // Load SQL Server JDBC Driver
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            // Kết nối đến database
            connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            
//            DatabaseMetaData mtdt = connection.getMetaData();
            System.out.println("Kết nối database thành công!");
//            System.out.println(mtdt.getClass());
//            System.out.println(mtdt.getDatabaseProductVersion());
            
        } catch (ClassNotFoundException e) {
            System.err.println("Không tìm thấy driver SQL Server!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Lỗi kết nối database!");
            e.printStackTrace();
        }
    }

    // Triển khai Singleton Pattern an toàn với đa luồng
    public static Constant getInstance() {
        if (instance == null) {
            synchronized (Constant.class) {
                if (instance == null) {
                    instance = new Constant();
                }
            }
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    // Đóng kết nối
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Kết nối đã đóng!");
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi đóng kết nối!");
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
      getInstance();
       
    }
    
}

