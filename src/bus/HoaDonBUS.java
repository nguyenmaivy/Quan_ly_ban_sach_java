package bus;

import dao.HoaDonDAO;
import dto.HoaDonDTO;
import java.sql.SQLException;
import java.util.List;

public class HoaDonBUS {

    public static String getSoHD() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private HoaDonDAO hoaDonDAO;

    public HoaDonBUS() {
        hoaDonDAO = new HoaDonDAO();
    }

    public boolean themHoaDon(HoaDonDTO hoaDon) throws SQLException {
        // kiểm tra dữ liệu
        if (hoaDon.getSoHD() == null || hoaDon.getSoHD().isEmpty()){
            throw new IllegalArgumentException("Mã hóa đơn không được để trống.");
        }
        if (hoaDon.getNgayBan() == null) {
            throw new IllegalArgumentException("Ngày bán không được để trống.");
        }
        if (hoaDon.getMaNV() == null) {
            throw new IllegalArgumentException("Mã Nhân viên không được để trống");
        }
        if (hoaDon.getTrangThai() < 0) {
            throw new IllegalArgumentException("Trạng thái không hợp lệ");
        }
        return hoaDonDAO.themHoaDon(hoaDon);
    }

    public boolean layHoaDon(String soHD) throws SQLException {
        // Thêm logic nghiệp vụ
        if (soHD == null || soHD.isEmpty()) {
            throw new IllegalArgumentException("Mã hóa đơn không được để trống.");
        }
        return hoaDonDAO.layHoaDon(soHD);
    }

    public boolean capNhatTrangThaiHoaDon(String soHD, int trangThai) throws SQLException {
        // Thêm logic nghiệp vụ (ví dụ: kiểm tra trạng thái hợp lệ)
        if (soHD == null || soHD.isEmpty()) {
            throw new IllegalArgumentException("Mã hóa đơn không được để trống.");
        }
        if (trangThai < 0) {
            throw new IllegalArgumentException("Trạng thái không hợp lệ.");
        }
        return hoaDonDAO.capNhatTrangThaiHoaDon(soHD, trangThai);
    }

     public boolean xoaHoaDon(String soHD) throws SQLException {
        // Logic nghiệp vụ: Kiểm tra mã hóa đơn
        if (soHD == null || soHD.isEmpty()) {
            throw new IllegalArgumentException("Mã hóa đơn không được để trống.");
        }

        // Gọi DAO để xóa hóa đơn
        return hoaDonDAO.xoaHoaDon(soHD);
    }

     public boolean capNhatHoaDon(HoaDonDTO hoaDon) throws SQLException {
        // Logic nghiệp vụ: Kiểm tra dữ liệu đầu vào
        if (hoaDon.getSoHD() == null || hoaDon.getSoHD().isEmpty()) {
            throw new IllegalArgumentException("Mã hóa đơn không được để trống.");
        }
        if (hoaDon.getNgayBan() == null) {
            throw new IllegalArgumentException("Ngày bán không được để trống.");
        }
        if (hoaDon.getMaNV() == null || hoaDon.getMaNV().isEmpty()) {
            throw new IllegalArgumentException("Mã nhân viên không được để trống.");
        }
        if (hoaDon.getTrangThai() < 0) {
            throw new IllegalArgumentException("Trạng thái không hợp lệ.");
        }

        // Gọi DAO để cập nhật hóa đơn
        return hoaDonDAO.capNhatHoaDon(hoaDon);
    }
    public List<HoaDonDTO> layDanhSachHoaDon() throws SQLException {
        try {
        return hoaDonDAO.layDanhSachHoaDon();
    } catch (SQLException e) {
        // Xử lý ngoại lệ (ví dụ: ghi log, hiển thị thông báo lỗi)
        throw new SQLException("Lỗi khi lấy danh sách hóa đơn: " + e.getMessage());
    }
    }


}