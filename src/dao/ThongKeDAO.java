/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import Config.Constant;
import dto.ThongKeDTO;
import java.sql.*;

/**
 *
 * @author MZI
 */
public class ThongKeDAO {

    private Constant dbConnection;

    public ThongKeDAO() {
        this.dbConnection = new Constant();
    }

    // Lấy kết nối từ Constant
    private Connection getConnection() throws SQLException {
        if (dbConnection.openConnection()) {
            return dbConnection.getConnection();
        } else {
            throw new SQLException("Không thể kết nối tới cơ sở dữ liệu.");
        }
    }

    // Lấy tất cả dữ liệu thống kê và trả về ThongKeDTO
    public ThongKeDTO getAllStats() {
        ThongKeDTO stats = new ThongKeDTO();

        // 1. Thống kê theo năm (số lượng sách bán và trung bình sách tồn)
        String query = "SELECT YEAR(hd.ngayBan) AS year, "
                + "SUM(ct.soLuongBan) AS soluongsach, "
                + "AVG(CAST(s.soLuong AS FLOAT)) AS AvgSach "
                + "FROM HoaDon hd "
                + "JOIN ChiTietHoaDon ct ON hd.soHD = ct.soHD "
                + "JOIN Sach s ON ct.maSach = s.id "
                + "GROUP BY YEAR(hd.ngayBan)";

        Connection conn = null;
        try {
            conn = getConnection();
            try ( PreparedStatement ps = conn.prepareStatement(query);  ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int year = rs.getInt("year");
                    int soluongsach = rs.getInt("soluongsach");
                    double avgSach = rs.getDouble("AvgSach");
                    stats.addYear(year, soluongsach, avgSach);
                }
            }

            // 2. Thống kê theo thể loại (doanh thu và tỷ lệ phần trăm)
            String thongkeDT = "SELECT tl.tenLoai, SUM(ct.soLuongBan * ct.giaBan) AS tongDT "
                    + "FROM ChiTietHoaDon ct "
                    + "JOIN Sach s ON ct.maSach = s.id "
                    + "JOIN TheLoai tl ON s.theLoai = tl.maLoai "
                    + "GROUP BY tl.tenLoai";

            String tongDoanhThu = "SELECT SUM(CAST(ct.soLuongBan AS BIGINT) * CAST(ct.giaBan AS BIGINT)) AS Tong "
                    + "FROM ChiTietHoaDon ct";

            // Lấy tổng doanh thu
            double tongDT = 0;
            try ( PreparedStatement ps = conn.prepareStatement(tongDoanhThu);  ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    tongDT = rs.getDouble("Tong");
                }
            }

            // Lấy doanh thu theo thể loại
            try ( PreparedStatement ps = conn.prepareStatement(thongkeDT);  ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String tenTL = rs.getString("tenLoai");
                    long doanhthu = rs.getLong("tongDT");
                    double ptram = (tongDT > 0) ? (doanhthu / tongDT) * 100 : 0;
                    stats.addDoanhthu(tenTL, doanhthu, ptram);
                }
            }
           
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                dbConnection.closeConnection();
            }
        }

        return stats;
    }
}
