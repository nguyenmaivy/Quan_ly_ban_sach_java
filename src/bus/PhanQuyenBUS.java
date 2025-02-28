/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bus;

import dto.nhomQuyenDTO;
import dao.PhanQuyenDAO;
import java.util.ArrayList;

/**
 *
 * @author MZI
 */
public class PhanQuyenBUS {

    PhanQuyenDAO pqdao = new PhanQuyenDAO();

    public ArrayList<nhomQuyenDTO> getALL() {
        return pqdao.getALL();
    }

    public String addPQ(nhomQuyenDTO nq) {
        if (pqdao.add(nq)) {
            return "Thêm nhóm quyền thành công" + nq.getMaNhomQuyen();
        }
        return "Thêm thất bại";
    }

    public String deletePQ(String maNhomQuyen) {
        if (pqdao.delete(maNhomQuyen)) {
            return "Xóa nhà cung cấp thành công";
        }
        return "Xóa nhà cung cấp thất bại";
    }

    public String updatePQ(nhomQuyenDTO nq) {
        if (pqdao.update(nq)) {
            return "Cập nhật nhóm quyền thành công";
        }
        return "Cập nhật nhóm quyền thất bại";
    }

    public ArrayList<nhomQuyenDTO> search(String searchContent) {
        return pqdao.search(searchContent);
    }

}
