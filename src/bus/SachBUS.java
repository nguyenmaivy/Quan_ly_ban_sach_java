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
    ArrayList<SachDTO> listSach = sachDAO.getALL();

    public ArrayList<SachDTO> getAllSach() {
        return sachDAO.getALL();
    }

    public String addSach(SachDTO sach) {
        // Kiểm tra rỗng
        if (sach.getTenSach().trim().isEmpty()
                || sach.getTheLoai().trim().isEmpty()
                || sach.getTacGia().trim().isEmpty()
                || sach.getNhaXuatBan().trim().isEmpty()
                || sach.getMaKho().trim().isEmpty()
                || sach.getHinhAnh().trim().isEmpty()) {
            return "Vui lòng nhập đầy đủ thông tin!";
        }

        // Kiểm tra số lượng và giá bán
        if (sach.getSoLuong() <= 0 || sach.getGiaBan() <= 0) {
            return "Giá bán và số lượng phải lớn hơn 0!";
        }

        // Kiểm tra trùng mã
        if (sachDAO.has(sach.getId())) {
            return "Mã sách đã tồn tại";
        }

        // Thêm sách
        if (sachDAO.add(sach)) {
            return "Thêm sách thành công";
        }

        return "Thêm sách thất bại";
    }

    public String deleteSach(String idsach) {
        if (sachDAO.delete(idsach)) {
            return "Xóa sách thành công";
        }
        return "Xóa sách thất bại";
    }

    public String updateSach(SachDTO sach) {
        if (sach == null || sach.getId() == null || sach.getId().trim().isEmpty()) {
        return "Mã sách không hợp lệ!";
        }
        boolean isUpdated = sachDAO.update(sach);

        if (isUpdated) {
            return "Cập nhật sách thành công!";
        } else {
            return "Cập nhật sách thất bại!";
        }
    }

    public SachDTO getByID(String id) {
        return sachDAO.getByID(id);
    }
    
    public String getNextMaSach (){
        SachDAO dao = new SachDAO();
        return dao.NextMaSach();
    }

    public ArrayList<SachDTO> search(String searchContent) {
        if (searchContent == null || searchContent.trim().isEmpty()) {
            // Nếu không có nội dung tìm kiếm, trả về toàn bộ danh sách
            return sachDAO.getALL();
        }
        return sachDAO.search(searchContent);
    }
    public String getTenSachByMa(String maSach){
        listSach = sachDAO.getALL();
        for (SachDTO sachDTO : listSach) {
            if (sachDTO.getId().equals(maSach)) {
                return sachDTO.getTenSach();
            }
        }
        return "Không rõ";
    }
    public SachDTO getByName(String tenSach) {
        return null; 
    }

}
