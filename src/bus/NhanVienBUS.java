/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bus;

import dao.NhanVienDAO;
import dto.NhanVienDTO;
import java.util.ArrayList;

/**
 *
 * @author leduc
 */
public class NhanVienBUS {

    NhanVienDAO nvDAO = new NhanVienDAO();
    ArrayList<NhanVienDTO> listNV = nvDAO.getALL();

    public ArrayList<NhanVienDTO> getAllNhanVien() {
        return nvDAO.getALL();
    }

    public String addNhanVien(NhanVienDTO nv) {
        if (nv.getTenNV().trim().isEmpty() || nv.getDiaChi().trim().isEmpty() || nv.getSdt().trim().isEmpty()) {
            return "Vui lòng nhập đầy đủ thông tin!";
        }

        // Kiểm tra định dạng số điện thoại
        if (!nv.getSdt().matches("^0\\d{9}$")) {
            return "Số điện thoại không hợp lệ! Phải 10 chữ số và bắt đầu bằng 0.";
        }

        // Kiểm tra số điện thoại đã tồn tại chưa (khi thêm mới thì truyền null)
        if (nvDAO.isPhoneNumberExists(nv.getSdt(), null)) {
            return "Số điện thoại đã tồn tại. Vui lòng nhập số khác!";
        }

        if (nvDAO.add(nv)) {
            return "Thêm nhân viên thành công!";
        }

        return "Thêm nhân viên thất bại!";
    }

    public String deleteNhanVien(String manv) {
        if (nvDAO.delete(manv)) {
            return "Xóa nhân viên thành công";
        }
        return "Xóa nhân viên thất bại";
    }

    public String updateNhanVien(NhanVienDTO nv) {
        if (nv == null || nv.getMaNV() == null || nv.getMaNV().isEmpty()) {
            return "Mã nhân viên không hợp lệ!";
        }

        // Kiểm tra định dạng số điện thoại
        if (!nv.getSdt().matches("^0\\d{9}$")) {
            return "Số điện thoại không hợp lệ! Phải có 10 chữ số và bắt đầu bằng 0.";
        }

        // Kiểm tra trùng số điện thoại (loại trừ chính mã NV đang sửa)
        if (nvDAO.isPhoneNumberExists(nv.getSdt(), nv.getMaNV())) {
            return "Số điện thoại đã tồn tại trong hệ thống!";
        }

        boolean isUpdated = nvDAO.update(nv);

        if (isUpdated) {
            return "Cập nhật nhân viên thành công!";
        } else {
            return "Cập nhật nhân viên thất bại!";
        }
    }

    public ArrayList<NhanVienDTO> search(String searchContent) {
        return nvDAO.search(searchContent);
    }

    public NhanVienDTO getByID(String manv) {
        return nvDAO.getByID(manv);
    }

    public NhanVienDTO getByName(String tenNV) {
        return null;
    }

    public String getNextMaNV() {
        NhanVienDAO dao = new NhanVienDAO();
        return dao.NextMaNXB();
    }

    public String getTenNVByMa(String maNV) {
        for (NhanVienDTO nv : listNV) {
            if (nv.getMaNV().equals(maNV)) {
                return nv.getTenNV();
            }
        }
        return "Không rõ";
    }

}
