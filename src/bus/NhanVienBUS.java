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
        if (nvDAO.has(nv.getMaNV())) {
            return "Mã nhân viên đã tồn tại";
        }
        if (nvDAO.add(nv)) {
            return "Thêm nhân viên thành công";
        }
        return "Thêm thất bại";
    }

    public String deleteNhanVien(String manv) {
        if (nvDAO.delete(manv)) {
            return "Xóa nhân viên thành công";
        }
        return "Xóa nhân viên thất bại";
    }

    public String updateNhanVien(NhanVienDTO nv) {
        if (nvDAO.update(nv)) {
            return "Cập nhật nhân viên thành công";
        }
        return "Cập nhật nhân viên thất bại";
    }

    public ArrayList<NhanVienDTO> search(String searchContent) {
        return nvDAO.search(searchContent);
    }

    public NhanVienDTO getByID(String manv) {
        return nvDAO.getByID(manv);
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
