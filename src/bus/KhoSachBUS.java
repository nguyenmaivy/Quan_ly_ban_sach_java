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
   
   public ArrayList<KhoSachDTO> getALLKhoSach(){
       return ksDAO.getALL();
   }
   public String addKhoSach(KhoSachDTO ks){
        if(ksDAO.has(ks.getMaKho()))
            return "Mã kho đã tồn tại";
        if(ksDAO.add(ks))
            return "Thêm sản phẩm vào kho thành công";
        return "Thêm thất bại";
    }
    
    public String deleteKhoSach(String maks){
        if(ksDAO.delete(maks))
            return "Xóa sản phẩm trong kho thành công";
        return "Xóa sản phẩm thất bại";
    }
    
    public String updateKhoSach(KhoSachDTO ks){
        if(ksDAO.update(ks))
            return "Cập nhật kho sach thành công";
        return "Cập nhật thất bại";
    }
    
    public KhoSachDTO getBySdt(String maKho) {
        return ksDAO.getByID(maKho);
    }
    
    public ArrayList<KhoSachDTO> search(String searchContent) {
        return ksDAO.search(searchContent);
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
