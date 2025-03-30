/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bus;

import dao.SachDAO;
import dto.SachDTO;
import java.util.ArrayList;

/**
 *
 * @author leduc
 */
public class SachBUS {
    
    SachDAO sachDAO = new SachDAO();
    
    public ArrayList<SachDTO> getAllSach(){
        return sachDAO.getALL();
    }
    
    public String addSach(SachDTO sach){
        if(sachDAO.has(sach.getId()))
            return "Mã sách đã tồn tại";
        if(sachDAO.add(sach))
            return "Thêm sách thành công";
        return "Thêm sách thất bại";
    }
    
    public String deleteSach(String idsach){
        if(sachDAO.delete(idsach))
            return "Xóa sách thành công";
        return "Xóa sách thất bại";
    }
    
    public String updateNhaXuatBan(SachDTO sach){
        if(sachDAO.update(sach))
            return "Cập nhật sách thành công";
        return "Cập sách thất bại";
    }
  
    public SachDTO getByID(String id) {
    	return sachDAO.getByID(id);
    }
    
    public ArrayList<SachDTO> search(String searchContent) {
        return sachDAO.search(searchContent);
    }
}
