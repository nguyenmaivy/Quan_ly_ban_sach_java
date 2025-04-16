/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bus;

/**
 *
 * @author LAPTOP
 */


import dao.TacGiaSachDAO;
import dto.TacGiaSachDTO;
import java.util.ArrayList;

public class TacGiaSachBUS {
    TacGiaSachDAO tgsDAO = new TacGiaSachDAO();

    public ArrayList<TacGiaSachDTO> getAllTacGiaSach() {
        return tgsDAO.getALL();
    }

    public String addTacGiaSach(TacGiaSachDTO tgs) {
        if (tgsDAO.has(tgs.getMaTG())) {
            return "Mã tác giả đã tồn tại";
        }
        if (tgsDAO.add(tgs)) {
            return "Thêm tác giả sách thành công";
        }
        return "Thêm thất bại";
    }

    public String deleteTacGiaSach(String maTG) {
        if (tgsDAO.delete(maTG)) {
            return "Xóa tác giả sách thành công";
        }
        return "Xóa tác giả sách thất bại";
    }

    public String updateTacGiaSach(TacGiaSachDTO tgs) {
        if (tgsDAO.update(tgs)) {
            return "Cập nhật tác giả sách thành công";
        }
        return "Cập nhật tác giả sách thất bại";
    }

    public TacGiaSachDTO getByID(String maTG) {
        return tgsDAO.getByID(maTG);
    }

}
