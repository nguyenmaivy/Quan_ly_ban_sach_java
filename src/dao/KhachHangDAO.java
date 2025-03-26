package dao;

import dto.KhachHangDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class KhachHangDAO {

    private Connection getConnection() throws SQLException {
        // Thay thế thông tin kết nối của bạn
        String url = "jdbc:mysql://localhost:3306/quanlybansach";
        String user = "root";
        String password = "";
        return DriverManager.getConnection(url, user, password);
    }

    public void themKhachHang(KhachHangDTO khachHang) throws SQLException {
        String sql = "INSERT INTO KHACHHANG (TenKH, SDT, DiaChi, TrangThai) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, khachHang.getTenKH());
            pstmt.setString(2, khachHang.getSdt());
            pstmt.setString(3, khachHang.getDiaChi());
            pstmt.setInt(4, khachHang.getTrangThai());
            pstmt.executeUpdate();
        }
    }

    public KhachHangDTO layKhachHang(int maKH) throws SQLException {
        String sql = "SELECT * FROM KHACHHANG WHERE MaKH = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, maKH);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new KhachHangDTO(
                        rs.getString("MaKH"),
                        rs.getString("TenKH"),
                        rs.getString("SDT"),
                        rs.getString("DiaChi"),
                        rs.getInt("TrangThai")
                );
            }
        }
        return null;
    }

    public void capNhatKhachHang(KhachHangDTO khachHang) throws SQLException {
        String sql = "UPDATE KHACHHANG SET TenKH = ?, SDT = ?, DiaChi = ?, TrangThai = ? WHERE MaKH = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, khachHang.getTenKH());
            pstmt.setString(2, khachHang.getSdt());
            pstmt.setString(3, khachHang.getDiaChi());
            pstmt.setInt(4, khachHang.getTrangThai());
            pstmt.setString(5, khachHang.getMaKH());
            pstmt.executeUpdate();
        }
    }

    public void xoaKhachHang(int maKH) throws SQLException {
        String sql = "UPDATE KHACHHANG SET TrangThai = 'Inactive' WHERE MaKH = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, maKH);
            pstmt.executeUpdate();
        }
    }

    public List<KhachHangDTO> layDanhSachKhachHang() throws SQLException {
        String sql = "SELECT * FROM KHACHHANG";
        List<KhachHangDTO> danhSachKhachHang = new ArrayList<>();
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                danhSachKhachHang.add(new KhachHangDTO(
                        rs.getString("MaKH"),
                        rs.getString("TenKH"),
                        rs.getString("SDT"),
                        rs.getString("DiaChi"),
                        rs.getInt("TrangThai")
                ));
            }
        }
        return danhSachKhachHang;
    }
}
