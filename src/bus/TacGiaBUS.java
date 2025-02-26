/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bus;

import dao.TacGiaDAO;
import dto.TacGiaDTO;
import java.util.ArrayList;

/**
 *
 * @author MZI
 */
public class TacGiaBUS {

    TacGiaDAO tacGiaDAO = new TacGiaDAO();

    public ArrayList<TacGiaDTO> getAllTacGia() {
        return tacGiaDAO.getALL();
    }

    public String addTacGia(TacGiaDTO tacgia) {
        if (tacGiaDAO.has(tacgia.getMaTG())) {
            return "Mã tác giả đã tồn tại";
        }
        if (tacGiaDAO.add(tacgia)) {
            return "Thêm tác giả thành công";
        }
        return "Thêm thất bại";
    }

    public String updateTacGia(TacGiaDTO tacgia) {
        if (tacGiaDAO.update(tacgia)) {
            return "Cập nhật tác giả thành công";
        }
        return "Cập nhật tác giả thất bại";
    }

    public String deleteTacGia(String tacgia) {
        if (tacGiaDAO.delete(tacgia)) {
            return "Xóa tác giả thành công";
        }
        return "Xóa tác giả thất bại";
    }

    public TacGiaDTO getByName(String tacgia) {
        return tacGiaDAO.getByName(tacgia);
    }

}
