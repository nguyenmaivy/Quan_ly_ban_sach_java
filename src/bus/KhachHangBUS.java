package bus;

import dao.KhachHangDAO;
import dto.KhachHangDTO;
import java.sql.SQLException;
import java.util.List;

public class KhachHangBUS {

    private KhachHangDAO khachHangDAO;

    public KhachHangBUS() {
        khachHangDAO = new KhachHangDAO();
    }

    public void themKhachHang(KhachHangDTO khachHang) throws SQLException {

        khachHangDAO.themKhachHang(khachHang);
    }

    public KhachHangDTO layKhachHang(int maKH) throws SQLException {
        return khachHangDAO.layKhachHang(maKH);
    }

    public void capNhatKhachHang(KhachHangDTO khachHang) throws SQLException {

        khachHangDAO.capNhatKhachHang(khachHang);
    }

    public void xoaKhachHang(int maKH) throws SQLException {

        khachHangDAO.xoaKhachHang(maKH);
    }

    public List<KhachHangDTO> layDanhSachKhachHang() throws SQLException {
        return khachHangDAO.layDanhSachKhachHang();
    }
}
