package bus;

import dao.HoaDonDAO;
import dto.HoaDonDTO;
import java.sql.SQLException;

public class HoaDonBUS {

    private HoaDonDAO hoaDonDAO;

    public HoaDonBUS() {
        hoaDonDAO = new HoaDonDAO();
    }

    public boolean themHoaDon(HoaDonDTO hoaDon) throws SQLException {
        // Thêm logic nghiệp vụ (ví dụ: kiểm tra dữ liệu)
        return hoaDonDAO.themHoaDon(hoaDon);
    }

    public HoaDonDTO layHoaDon(String soHD) throws SQLException {
        // Thêm logic nghiệp vụ
        return hoaDonDAO.layHoaDon(soHD);
    }

    public boolean capNhatTrangThaiHoaDon(String soHD, int trangThai) throws SQLException {
        // Thêm logic nghiệp vụ (ví dụ: kiểm tra trạng thái hợp lệ)
        return hoaDonDAO.capNhatTrangThaiHoaDon(soHD, trangThai);
    }

    // Thêm các phương thức khác nếu cần
}