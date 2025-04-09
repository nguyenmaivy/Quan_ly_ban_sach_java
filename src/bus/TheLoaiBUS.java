/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bus;

import dao.TheLoaiDAO;
import dto.TheLoaiDTO;
import java.util.ArrayList;

/**
 *
 * @author leduc
 */
public class TheLoaiBUS {
    TheLoaiDAO theloaiDAO = new TheLoaiDAO();
    ArrayList<TheLoaiDTO> listTheLoai = theloaiDAO.getALL();
    
    public ArrayList<TheLoaiDTO> getAllTheLoai(){
        return theloaiDAO.getALL();
    }
    
    public String addTheLoai(TheLoaiDTO theloai){
        if(theloaiDAO.has(theloai.getMaLoai()))
            return "Mã thể loại đã tồn tại";
        if(theloaiDAO.add(theloai))
            return "Thêm thể loại thành công";
        return "Thêm thể loại thất bại";
    }
    
    public String deleteTheLoai(String matl){
        if(theloaiDAO.delete(matl))
            return "Xóa thể loại thành công";
        return "Xóa thể loại thất bại";
    }
    
    public String updateTheLoai(TheLoaiDTO tl){
        if(theloaiDAO.update(tl))
            return "Cập nhật thể loại thành công";
        return "Cập thể loại thất bại";
    }
  
    public TheLoaiDTO getByID(String maLoai) {
    	return theloaiDAO.getByID(maLoai);
    }
    
    
    public ArrayList<TheLoaiDTO> search(String searchContent) {
        return theloaiDAO.search(searchContent);
    }
    public String getTenTL(String maTL){
        listTheLoai = theloaiDAO.getALL();
        for (TheLoaiDTO theLoaiDTO : listTheLoai) {
            if (theLoaiDTO.getMaLoai().equals(maTL)) {
                return theLoaiDTO.getTenLoai();
            }
        }
        return "Không rõ";
    }
    
}
