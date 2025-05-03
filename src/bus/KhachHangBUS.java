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
    
    public ArrayList<KhachHangDTO> searchKhachHang(String keyword) {
        ArrayList<KhachHangDTO> all = getAllKhachHang(); // Gọi tất cả khách hàng
        ArrayList<KhachHangDTO> result = new ArrayList<>();
        for (KhachHangDTO kh : all) {
            if (kh.getMaKH().toLowerCase().contains(keyword.toLowerCase())
             || kh.getTenKH().toLowerCase().contains(keyword.toLowerCase())
             || kh.getSdt().toLowerCase().contains(keyword.toLowerCase())
             || kh.getDiaChi().toLowerCase().contains(keyword.toLowerCase())) {
                result.add(kh);
            }
        }
        return result;
    }

    
    public String generateMaKH() {
        ArrayList<KhachHangDTO> list = khachHangDAO.getAllKhachHang();
        int max = 0;
        for (KhachHangDTO kh : list) {
            try {
                String numPart = kh.getMaKH().replaceAll("[^0-9]", "");
                int number = Integer.parseInt(numPart);
                if (number > max) {
                    max = number;
                }
            } catch (NumberFormatException ignored) {}
        }
        return String.format("C%03d", max + 1); 
    }

}
