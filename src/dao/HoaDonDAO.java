package dao;

import Config.Constant;
import dto.HoaDonDTO;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class HoaDonDAO {

    private Constant constant = new Constant();
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter DISPLAY_DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public boolean themHoaDon(HoaDonDTO hoaDon) throws SQLException {
        String sql = "INSERT INTO HOADON (SoHD, NgayBan, MaNV, TrangThai) VALUES (?, ?, ?, ?)";
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            constant.openConnection(); // Mở kết nối
            con = constant.getConnection();
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
            constant.closeConnection(); // Đóng kết nối
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
            constant.openConnection(); // Mở kết nối
            con = constant.getConnection();
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
            constant.closeConnection(); // Đóng kết nối
        }
        return null;
    }

    public boolean capNhatTrangThaiHoaDon(String soHD, int trangThai) throws SQLException {
        String sql = "UPDATE HOADON SET TrangThai = ? WHERE SoHD = ?";
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            constant.openConnection(); // Mở kết nối
            con = constant.getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, trangThai);
            pstmt.setString(2, soHD);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi cập nhật trạng thái hóa đơn: " + e.getMessage());
            throw e;
        } finally {
            constant.closeConnection(); // Đóng kết nối
        }
    }

    public boolean xoaHoaDon(String soHD) throws SQLException {
        String sql = "DELETE FROM HOADON WHERE SoHD = ?";
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            constant.openConnection(); // Mở kết nối
            con = constant.getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, soHD);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi xóa hóa đơn: " + e.getMessage());
            throw e;
        } finally {
            constant.closeConnection(); // Đóng kết nối
        }
    }

    public boolean capNhatHoaDon(HoaDonDTO hoaDon) throws SQLException {
        String sql = "UPDATE HOADON SET NgayBan = ?, MaNV = ?, TrangThai = ? WHERE SoHD = ?";
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            constant.openConnection(); // Mở kết nối
            con = constant.getConnection();
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
            constant.closeConnection(); // Đóng kết nối
        }
    }
    
    public String getLastSoHD() throws SQLException {
        String sql = "SELECT TOP 1 SoHD FROM HoaDon ORDER BY SoHD DESC";
        try (Connection conn = new Constant().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                return rs.getString("SoHD");
            }
        } finally {
            new Constant().closeConnection();
        }
        return null;
    }
    
    public List<Object[]> layChiTietHoaDon(String soHD) throws SQLException {
        List<Object[]> danhSachChiTiet = new ArrayList<>();
        String sql = "SELECT MaSach, SoHD, SoLuongBan, GiaBan FROM ChiTietHoaDon WHERE SoHD = ?";
        try (Connection conn = new Constant().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, soHD);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                danhSachChiTiet.add(new Object[]{
                        rs.getString("MaSach"),
                        rs.getString("SoHD"),
                        rs.getInt("SoLuongBan"),
                        rs.getDouble("GiaBan")
                });
            }
        } finally {
            new Constant().closeConnection();
        }
        return danhSachChiTiet;
    }
    
    public String themHoaDonReturnID(HoaDonDTO hoaDon) throws SQLException {
        String generatedSoHD = null;
        String sql = "INSERT INTO HoaDon (NgayBan, MaNV, TrangThai) VALUES (?, ?, ?)";
        try (Connection conn = new Constant().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setDate(1, java.sql.Date.valueOf(hoaDon.getNgayBan()));
            pstmt.setString(2, hoaDon.getMaNV());
            pstmt.setInt(3, hoaDon.getTrangThai());
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        generatedSoHD = generatedKeys.getString(1); // Lấy SoHD tự động tạo
                    }
                }
            }
        } finally {
            new Constant().closeConnection();
        }
        return generatedSoHD;
    }
    
    public boolean themNhieuChiTietHoaDon(List<Object[]> danhSachChiTiet) throws SQLException {
        String sql = "INSERT INTO ChiTietHoaDon (MaSach, SoHD, SoLuongBan, GiaBan) VALUES (?, ?, ?, ?)";
        int[] result = null;
        try (Connection conn = new Constant().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            for (Object[] chiTiet : danhSachChiTiet) {
                pstmt.setString(1, (String) chiTiet[0]);
                pstmt.setString(2, (String) chiTiet[1]);
                pstmt.setInt(3, (Integer) chiTiet[2]);
                pstmt.setDouble(4, (Double) chiTiet[3]);
                pstmt.addBatch();
            }
            result = pstmt.executeBatch();
        } finally {
            new Constant().closeConnection();
        }
        return result != null && result.length == danhSachChiTiet.size();
    }

    public List<HoaDonDTO> layDanhSachHoaDon() throws SQLException {
        List<HoaDonDTO> danhSach = new ArrayList<>();
        String sql = "SELECT hd.SoHD, hd.NgayBan, hd.MaNV, hd.TrangThai, nv.TenNV "
                     + "FROM HOADON hd "
                     + "LEFT JOIN NHANVIEN nv ON hd.MaNV = nv.MaNV";
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            constant.openConnection(); // Mở kết nối
            con = constant.getConnection();
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                HoaDonDTO hoaDon = new HoaDonDTO();
                hoaDon.setSoHD(rs.getString("SoHD"));
                hoaDon.setNgayBan(rs.getDate("NgayBan").toLocalDate());
                hoaDon.setMaNV(rs.getString("MaNV"));
                hoaDon.setTrangThai(rs.getInt("TrangThai"));
                hoaDon.setTenNV(rs.getString("TenNV"));
                danhSach.add(hoaDon);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi lấy danh sách hóa đơn: " + e.getMessage());
            throw e;
        } finally {
            constant.closeConnection(); // Đóng kết nối
        }
        return danhSach;
    }
}