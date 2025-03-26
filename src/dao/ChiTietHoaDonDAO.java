package dao;

import Config.Constant;
import dto.ChiTietHoaDonDTO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ChiTietHoaDonDAO {
    private Connection getConnection() throws SQLException {
        Constant jdbc = new Constant();
        return null;
    }

    public boolean themChiTietHoaDon(ChiTietHoaDonDTO chiTiet) throws SQLException {
        String sql = "INSERT INTO CHITIETHOADON (MaSach, SoHD, SoLuongBan, GiaBan) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, chiTiet.getMaSach());
            pstmt.setString(2, chiTiet.getSoHD());
            pstmt.setInt(3, chiTiet.getSoLuongBan());
            pstmt.setInt(4, chiTiet.getGiaBan());
            return pstmt.executeUpdate() > 0;
        }
    }

    public List<ChiTietHoaDonDTO> layChiTietHoaDon(String soHD) throws SQLException {
        String sql = "SELECT * FROM CHITIETHOADON WHERE SoHD = ?";
        List<ChiTietHoaDonDTO> danhSachChiTiet = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, soHD);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                ChiTietHoaDonDTO chiTiet = new ChiTietHoaDonDTO();
                chiTiet.setMaSach(rs.getString("MaSach"));
                chiTiet.setSoHD(rs.getString("SoHD"));
                chiTiet.setSoLuongBan(rs.getInt("SoLuongBan"));
                chiTiet.setGiaBan(rs.getInt("GiaBan"));
                danhSachChiTiet.add(chiTiet);
            }
        }
        return danhSachChiTiet;
    }

    // Thêm các phương thức khác nếu cần (ví dụ: cập nhật chi tiết hóa đơn, xóa chi tiết hóa đơn)
}