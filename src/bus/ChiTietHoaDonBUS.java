package bus;

import dao.ChiTietHoaDonDAO;
import dto.ChiTietHoaDonDTO;
import java.sql.SQLException;
import java.util.List;

public class ChiTietHoaDonBUS {
    private ChiTietHoaDonDAO chiTietHoaDonDAO;

    public ChiTietHoaDonBUS() {
        chiTietHoaDonDAO = new ChiTietHoaDonDAO();
    }

    public boolean themChiTietHoaDon(ChiTietHoaDonDTO chiTiet) throws SQLException {
        // Thêm logic nghiệp vụ (ví dụ: kiểm tra dữ liệu)
        return chiTietHoaDonDAO.themChiTietHoaDon(chiTiet);
    }

    public List<ChiTietHoaDonDTO> layChiTietHoaDon(String soHD) throws SQLException {
        // Thêm logic nghiệp vụ
        return chiTietHoaDonDAO.layChiTietHoaDon(soHD);
    }

    // Thêm các phương thức khác nếu cần
}