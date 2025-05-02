package bus;

import dao.KhachHangDAO;
import dto.KhachHangDTO;

import java.util.ArrayList;

public class KhachHangBUS {

    private final KhachHangDAO khachHangDAO = new KhachHangDAO();

    public ArrayList<KhachHangDTO> getAllKhachHang() {
        return khachHangDAO.getAllKhachHang();
    }

    public String addKhachHang(KhachHangDTO kh) {
        if (khachHangDAO.getByMaKH(kh.getMaKH()) != null) {
            return "Mã khách hàng đã tồn tại!";
        }

        boolean success = khachHangDAO.addKhachHang(kh);
        return success ? "Thêm khách hàng thành công!" : "Thêm khách hàng thất bại!";
    }

    public String updateKhachHang(KhachHangDTO kh) {
        boolean success = khachHangDAO.updateKhachHang(kh);
        return success ? "Cập nhật khách hàng thành công!" : "Cập nhật khách hàng thất bại!";
    }

    public String deleteKhachHang(String maKH) {
        boolean success = khachHangDAO.deleteKhachHang(maKH);
        return success ? "Xóa khách hàng thành công!" : "Xóa khách hàng thất bại!";
    }

    public KhachHangDTO getByMaKH(String maKH) {
        return khachHangDAO.getByMaKH(maKH);
    }

    public KhachHangDTO getById(String maKH) {
        return khachHangDAO.getByMaKH(maKH); // Gọi phương thức đã được triển khai
    }
}
