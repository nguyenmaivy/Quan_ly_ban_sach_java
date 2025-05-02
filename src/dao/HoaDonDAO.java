package dao;

import Config.Constant;
import dto.HoaDonDTO;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class HoaDonDAO {

    private Constant constant = new Constant(); // Sử dụng class Constant để quản lý kết nối
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter DISPLAY_DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public boolean themHoaDon(HoaDonDTO hoaDon) throws SQLException {
        String sql = "INSERT INTO HOADON (SoHD, NgayBan, MaNV, TrangThai) VALUES (?, ?, ?, ?)";
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = constant.getConnection(); // Lấy kết nối từ Constant
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, hoaDon.getSoHD());
            pstmt.setDate(2, Date.valueOf(hoaDon.getNgayBan()));
            pstmt.setString(3, hoaDon.getMaNV());
            pstmt.setInt(4, hoaDon.getTrangThai());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi thêm hóa đơn: " + e.getMessage());
            throw e;
        } finally {
            constant.closeConnection(); // Đóng kết nối thông qua Constant
        }
    }

    public HoaDonDTO getByMaHD(String soHD) throws SQLException {
        String sql = "SELECT hd.SoHD, hd.NgayBan, hd.MaNV, hd.TrangThai, nv.TenNV "
                + "FROM HOADON hd "
                + "LEFT JOIN NHANVIEN nv ON hd.MaNV = nv.MaNV "
                + "WHERE hd.SoHD = ?";
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = constant.getConnection(); // Lấy kết nối từ Constant
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, soHD);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                HoaDonDTO hoaDon = new HoaDonDTO();
                hoaDon.setSoHD(rs.getString("SoHD"));
                hoaDon.setNgayBan(rs.getDate("NgayBan").toLocalDate());
                hoaDon.setMaNV(rs.getString("MaNV"));
                hoaDon.setTrangThai(rs.getInt("TrangThai"));
                hoaDon.setTenNV(rs.getString("TenNV"));
                return hoaDon;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi lấy hóa đơn theo mã: " + e.getMessage());
            throw e;
        } finally {
            constant.closeConnection(); // Đóng kết nối thông qua Constant
        }
        return null;
    }

    public boolean capNhatTrangThaiHoaDon(String soHD, int trangThai) throws SQLException {
        String sql = "UPDATE HOADON SET TrangThai = ? WHERE SoHD = ?";
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = constant.getConnection(); // Lấy kết nối từ Constant
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, trangThai);
            pstmt.setString(2, soHD);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi cập nhật trạng thái hóa đơn: " + e.getMessage());
            throw e;
        } finally {
            constant.closeConnection(); // Đóng kết nối thông qua Constant
        }
    }

    public boolean xoaHoaDon(String soHD) throws SQLException {
        String sql = "DELETE FROM HOADON WHERE SoHD = ?";
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = constant.getConnection(); // Lấy kết nối từ Constant
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, soHD);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi xóa hóa đơn: " + e.getMessage());
            throw e;
        } finally {
            constant.closeConnection(); // Đóng kết nối thông qua Constant
        }
    }

    public boolean capNhatHoaDon(HoaDonDTO hoaDon) throws SQLException {
        String sql = "UPDATE HOADON SET NgayBan = ?, MaNV = ?, TrangThai = ? WHERE SoHD = ?";
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = constant.getConnection(); // Lấy kết nối từ Constant
            pstmt = con.prepareStatement(sql);
            pstmt.setDate(1, Date.valueOf(hoaDon.getNgayBan()));
            pstmt.setString(2, hoaDon.getMaNV());
            pstmt.setInt(3, hoaDon.getTrangThai());
            pstmt.setString(4, hoaDon.getSoHD());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi cập nhật hóa đơn: " + e.getMessage());
            throw e;
        } finally {
            constant.closeConnection(); // Đóng kết nối thông qua Constant
        }
    }

    public List<HoaDonDTO> layDanhSachHoaDon() throws SQLException {
    String sql = "SELECT hd.SoHD, hd.NgayBan, hd.MaNV, hd.TrangThai, nv.TenNV "
               + "FROM HOADON hd "
               + "LEFT JOIN NHANVIEN nv ON hd.MaNV = nv.MaNV";
    List<HoaDonDTO> danhSachHoaDon = new ArrayList<>();
    Connection con = null;
    Statement stmt = null;
    ResultSet rs = null;

    try {
        if (constant.openConnection()) { // ✅ Mở kết nối trước
            con = constant.getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                HoaDonDTO hoaDon = new HoaDonDTO();
                hoaDon.setSoHD(rs.getString("SoHD"));
                hoaDon.setNgayBan(rs.getDate("NgayBan").toLocalDate());
                hoaDon.setMaNV(rs.getString("MaNV"));
                hoaDon.setTrangThai(rs.getInt("TrangThai"));
                hoaDon.setTenNV(rs.getString("TenNV"));
                danhSachHoaDon.add(hoaDon);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Không thể mở kết nối đến CSDL!");
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Lỗi khi lấy danh sách hóa đơn: " + e.getMessage());
        throw e;
    } finally {
        constant.closeConnection(); // ✅ Luôn đóng kết nối sau khi xong
    }

    return danhSachHoaDon;
}

}

