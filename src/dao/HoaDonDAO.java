package dao;



import Config.Constant;
import dto.HoaDonDTO;
import java.sql.*;
import java.time.LocalDate;

public class HoaDonDAO {

    private Connection getConnection() throws SQLException {
        Constant jdbc = new Constant();
        return null;
    }

    public boolean themHoaDon(HoaDonDTO hoaDon) throws SQLException {
        String sql = "INSERT INTO HOADON (SoHD, NgayBan, MaNV, TrangThai) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, hoaDon.getSoHD());
            pstmt.setDate(2, Date.valueOf(hoaDon.getNgayBan()));
            pstmt.setString(3, hoaDon.getMaNV());
            pstmt.setInt(4, hoaDon.getTrangThai());
            return pstmt.executeUpdate() > 0;
        }
    }

    public HoaDonDTO layHoaDon(String soHD) throws SQLException {
        String sql = "SELECT * FROM HOADON WHERE SoHD = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, soHD);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                HoaDonDTO hoaDon = new HoaDonDTO();
                hoaDon.setSoHD(rs.getString("SoHD"));
                hoaDon.setNgayBan(rs.getDate("NgayBan").toLocalDate());
                hoaDon.setMaNV(rs.getString("MaNV"));
                hoaDon.setTrangThai(rs.getInt("TrangThai"));
                return hoaDon;
            }
        }
        return null;
    }

    public boolean capNhatTrangThaiHoaDon(String soHD, int trangThai) throws SQLException {
        String sql = "UPDATE HOADON SET TrangThai = ? WHERE SoHD = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, trangThai);
            pstmt.setString(2, soHD);
            return pstmt.executeUpdate() > 0;
        }
    }

    // Thêm các phương thức khác nếu cần (ví dụ: lấy danh sách hóa đơn, xóa hóa đơn,...)
}