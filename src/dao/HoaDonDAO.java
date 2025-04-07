package dao;



import Config.Constant;
import dto.HoaDonDTO;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HoaDonDAO {

    private  Constant constant = new Constant();

    public boolean themHoaDon(HoaDonDTO hoaDon) throws SQLException {
        String sql = "INSERT INTO HOADON (SoHD, NgayBan, MaNV, TrangThai) VALUES (?, ?, ?, ?)";
        try {
            if (constant.openConnection()) {
                try (PreparedStatement pstmt = constant.getConnection().prepareStatement(sql)) {
                    pstmt.setString(1, hoaDon.getSoHD());
                    pstmt.setDate(2, Date.valueOf(hoaDon.getNgayBan()));
                    pstmt.setString(3, hoaDon.getMaNV());
                    pstmt.setInt(4, hoaDon.getTrangThai());
                    return pstmt.executeUpdate() > 0;
                }
            } else {
                throw new SQLException("Không thể kết nối đến cơ sở dữ liệu.");
            }
        } finally {
            constant.closeConnection();
        }
    }

    public boolean layHoaDon(String soHD) throws SQLException {
        String sql = "INSERT INTO HOADON (SoHD, NgayBan, MaNV, TrangThai) VALUES (?, ?, ?, ?)";
        try {
            if (constant.openConnection()) {
                try (PreparedStatement pstmt = constant.getConnection().prepareStatement(sql)) {
//                    pstmt.setString(1, hoaDon.getSoHD());
//                    pstmt.setDate(2, Date.valueOf(hoaDon.getNgayBan()));
//                    pstmt.setString(3, hoaDon.getMaNV());
//                    pstmt.setInt(4, hoaDon.getTrangThai());
                    return pstmt.executeUpdate() > 0;
                }
            } else {
                throw new SQLException("Không thể kết nối đến cơ sở dữ liệu.");
            }
        } finally {
            constant.closeConnection();
        }
    }

    public boolean capNhatTrangThaiHoaDon(String soHD, int trangThai) throws SQLException {
        String sql = "UPDATE HOADON SET TrangThai = ? WHERE SoHD = ?";
        try {
            if (constant.openConnection()) {
                try (PreparedStatement pstmt = constant.getConnection().prepareStatement(sql)) {
                    pstmt.setInt(1, trangThai);
                    pstmt.setString(2, soHD);
                    return pstmt.executeUpdate() > 0;
                }
            } else {
                throw new SQLException("Không thể kết nối đến cơ sở dữ liệu.");
            }
        } finally {
            constant.closeConnection();
        }
    }
    
    public List<HoaDonDTO> layDanhSachHoaDon() throws SQLException {
        String sql = "SELECT * FROM HOADON";
            List<HoaDonDTO> danhSachHoaDon = new ArrayList<>();
            try {
                if (constant.openConnection()) {
                    try (Statement stmt = constant.getConnection().createStatement();
                         ResultSet rs = stmt.executeQuery(sql)) {
                        while (rs.next()) {
                            HoaDonDTO hoaDon = new HoaDonDTO();
                            hoaDon.setSoHD(rs.getString("SoHD"));
                            hoaDon.setNgayBan(rs.getDate("NgayBan").toLocalDate());
                            hoaDon.setMaNV(rs.getString("MaNV"));
                            hoaDon.setTrangThai(rs.getInt("TrangThai"));
                            danhSachHoaDon.add(hoaDon);
                        }
                    }
                } else {
                    throw new SQLException("Không thể kết nối đến cơ sở dữ liệu.");
                }
            } finally {
                constant.closeConnection();
            }
            return danhSachHoaDon;
    }

    public boolean xoaHoaDon(String soHD) throws SQLException {
        String sql = "DELETE FROM HOADON WHERE SoHD = ?";
        try {
            if (constant.openConnection()) {
                try (PreparedStatement pstmt = constant.getConnection().prepareStatement(sql)) {
                    pstmt.setString(1, soHD);
                    return pstmt.executeUpdate() > 0;
                }
            } else {
                throw new SQLException("Không thể kết nối đến cơ sở dữ liệu.");
            }
        } finally {
            constant.closeConnection();
        }
    }

    public boolean capNhatHoaDon(HoaDonDTO hoaDon) throws SQLException {
       String sql = "UPDATE HOADON SET NgayBan = ?, MaNV = ?, TrangThai = ? WHERE SoHD = ?";
        try {
            if (constant.openConnection()) {
                try (PreparedStatement pstmt = constant.getConnection().prepareStatement(sql)) {
                    pstmt.setDate(1, Date.valueOf(hoaDon.getNgayBan()));
                    pstmt.setString(2, hoaDon.getMaNV());
                    pstmt.setInt(3, hoaDon.getTrangThai());
                    pstmt.setString(4, hoaDon.getSoHD());
                    return pstmt.executeUpdate() > 0;
                }
            } else {
                throw new SQLException("Không thể kết nối đến cơ sở dữ liệu.");
            }
        } finally {
            constant.closeConnection();
        }
    }
}