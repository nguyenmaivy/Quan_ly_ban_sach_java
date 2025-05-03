/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bus;

import dao.KhoSachDAO;
import dto.KhoSachDTO;
import java.util.ArrayList;

/**
 *
 * @author leduc
 */
public class KhoSachBUS {

    KhoSachDAO ksDAO = new KhoSachDAO();
    ArrayList<KhoSachDTO> listkho = ksDAO.getALL();

    public ArrayList<KhoSachDTO> getALLKhoSach() {
        return ksDAO.getALL();
    }

    public String addKhoSach(KhoSachDTO ks) {
        if (ks.getTenKho().trim().isEmpty()
                || ks.getDiaChi().trim().isEmpty()
                || ks.getLoai().trim().isEmpty()) {
            return "Vui lòng nhập đầy đủ thông tin!";
        }

        if (ksDAO.has(ks.getMaKho())) {
            return "Mã kho đã tồn tại";
        }
        if (ksDAO.add(ks)) {
            return "Thêm sản phẩm vào kho thành công";
        }
        return "Thêm thất bại";
    }

    public String deleteKhoSach(String maks) {
        if (ksDAO.delete(maks)) {
            return "Xóa sản phẩm trong kho thành công";
        }
        return "Xóa sản phẩm thất bại";
    }

    public String updateKhoSach(KhoSachDTO ks) {
        if (ks == null || ks.getMaKho() == null || ks.getMaKho().isEmpty()) {
            return "Mã nhà Kho không hợp lệ!";
        }
        if (ksDAO.update(ks)) {
            return "Cập nhật kho sach thành công";
        }
        return "Cập nhật thất bại";
    }

    public KhoSachDTO getByID(String maKho) {
        return ksDAO.getByID(maKho);
    }

    public String getNextMaKho() {
        KhoSachDAO dao = new KhoSachDAO();
        return dao.nextMaKho();
    }

    public KhoSachDTO getByName(String tenKho) {
        return null;
    }

    public ArrayList<KhoSachDTO> search(String searchContent) {
        if (searchContent == null || searchContent.trim().isEmpty()) {
            // Nếu không có nội dung tìm kiếm, trả về toàn bộ danh sách
            return ksDAO.getALL();
        }
        return ksDAO.search(searchContent.trim());
    }

    public String getTenKhoByMa(String maKho) {
        for (KhoSachDTO kh : listkho) {
            if (kh.getMaKho().equals(maKho)) {
                return kh.getTenKho();
            }
        }
        return "Không rõ";
    }

}
