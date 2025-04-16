//
///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */

package bus;
import dao.NhaXuatBanDAO;
import dto.NhaXuatBanDTO;
import java.util.ArrayList;

public class NhaXuatBanBUS {
    NhaXuatBanDAO nxbDAO = new NhaXuatBanDAO();
    
    public ArrayList<NhaXuatBanDTO> getAllNhaXuatBan() {
        return nxbDAO.getALL();
    }
    
    // Thêm nhà xuất bản
    public String addNhaXuatBan(NhaXuatBanDTO nxb) {
        if (nxb.getTenNXB().trim().isEmpty() || nxb.getDiachiNXB().trim().isEmpty() || nxb.getSdt().trim().isEmpty()) {
            return "Vui lòng nhập đầy đủ thông tin!";
        }

        // Kiểm tra số điện thoại có hợp lệ hay không (10 chữ số, bắt đầu bằng 0)
        if (!nxb.getSdt().matches("^0\\d{9}$")) {
            return "Số điện thoại không hợp lệ! Phải 10 chữ số và bắt đầu bằng 0.";
        }

        // Kiểm tra số điện thoại đã tồn tại chưa
        if (nxbDAO.isPhoneNumberExists(nxb.getSdt(), null)) {
            return "Số điện thoại đã tồn tại. Vui lòng nhập số khác!";
        }

        if (nxbDAO.add(nxb)) {
            return "Thêm nhà xuất bản thành công";
        }
        return "Thêm nhà xuất bản thất bại";
    }
    
    // Xóa nhà xuất bản
    public String deleteNhaXuatBan(String manxb) {
        if (nxbDAO.delete(manxb)) {
            return "Xóa nhà xuất bản thành công";
        }
        return "Xóa nhà xuất bản thất bại";
    }
    
    // Cập nhật nhà xuất bản
    public String updateNhaXuatBan(NhaXuatBanDTO nxb) {
        if (nxb == null || nxb.getMaNXB() == null || nxb.getMaNXB().isEmpty()) {
            return "Mã nhà xuất bản không hợp lệ!";
        }
        
        // Kiểm tra số điện thoại có hợp lệ hay không (10 chữ số, bắt đầu bằng 0)
        if (!nxb.getSdt().matches("^0\\d{9}$")) {
            return "Số điện thoại không hợp lệ! Phải có 10 chữ số và bắt đầu bằng 0.";
        }

         // Kiểm tra số điện thoại có bị trùng không (loại trừ chính bản ghi đang cập nhật)
        if (nxbDAO.isPhoneNumberExists(nxb.getSdt(), nxb.getMaNXB())) {
            return "Số điện thoại đã tồn tại trong hệ thống!";
        }
        
        boolean isUpdated = nxbDAO.update(nxb);
        
        if (isUpdated) {
            return "Cập nhật nhà xuất bản thành công!";
        } else {
            return "Cập nhật nhà xuất bản thất bại!";
        }
    }

    // Lấy nhà xuất bản theo tên
    public NhaXuatBanDTO getByID(String manxb) {
        return nxbDAO.getByID(manxb);
    }
    
    public String getNextMaNXB() {
        NhaXuatBanDAO dao = new NhaXuatBanDAO();
        return dao.NextMaNXB();
    }
    
    
    public ArrayList<NhaXuatBanDTO> searchNhaXuatBan(String searchContent) {
        if (searchContent == null || searchContent.trim().isEmpty()) {
            // Nếu không có nội dung tìm kiếm, trả về toàn bộ danh sách
            return nxbDAO.getALL();
        }

        return nxbDAO.search(searchContent.trim());
    }

     
}
