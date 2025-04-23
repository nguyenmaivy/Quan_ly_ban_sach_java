package bus;

import dao.TaiKhoanDAO;
import dto.TaiKhoanDTO;
import java.util.ArrayList;
import dto.NhanVienDTO;
import dao.NhanVienDAO;
import bus.NhanVienBUS;
import dto.nhomQuyenDTO;
import dao.PhanQuyenDAO;
import bus.PhanQuyenBUS;
import dto.NhaXuatBanDTO;

public class TaiKhoanBUS {
    private TaiKhoanDAO tkDAO = new TaiKhoanDAO();    
    private PhanQuyenBUS pqBUS = new PhanQuyenBUS();
    
    // Lấy danh sách tất cả tài khoản
    public ArrayList<TaiKhoanDTO> getAllTaiKhoan() {
        return tkDAO.getALL();
    }    
    
    public ArrayList<Integer> getAllMaNhomQuyen() {
        ArrayList<Integer> dsMaNQ = new ArrayList<>();
        for (nhomQuyenDTO nq : pqBUS.getALL()) {
            dsMaNQ.add(nq.getMaNhomQuyen());
        }
        return dsMaNQ;
    }

    // Thêm tài khoản mới, kiểm tra sdt là duy nhất
   public String addTaiKhoan(TaiKhoanDTO tk) {    
    if (tk.getUseName().trim().isEmpty() ||
            tk.getMatKhau() .trim().isEmpty() ||
            tk.getMaNV() .trim().isEmpty() ||
            tk.getMaNhomQuyen() <=0) {
            return "Vui lòng nhập đầy đủ thông tin";
        }
    
        // Lấy số điện thoại từ mã nhân viên
        String sdt = getSdtByMaNV(tk.getMaNV());       

        // Gán số điện thoại vào tài khoản
        tk.setSdt(sdt);        

        return tkDAO.add(tk) ? "Thêm tài khoản thành công" : "Thêm tài khoản thất bại";
    }



    // Xóa tài khoản theo sdt (khóa chính)
    public String deleteTaiKhoan(String sdt) {
        if (tkDAO.has(sdt)) { // Kiểm tra tồn tại trước khi xóa
            if (tkDAO.delete(sdt)) {
                return "Xóa tài khoản thành công";
            }
            return "Xóa tài khoản thất bại";
        }
        return "Số điện thoại không tồn tại";
    }

    // Cập nhật thông tin tài khoản
    public String updateTaiKhoan(TaiKhoanDTO tk) {
       
        
        if (!tkDAO.has(tk.getSdt())) { // Kiểm tra xem tài khoản có tồn tại không
            return "Số điện thoại không tồn tại";
        }
        if (tkDAO.update(tk)) {
            return "Cập nhật tài khoản thành công";
        }
        return "Cập nhật tài khoản thất bại";
    }

    // Tìm tài khoản theo sdt (vì sdt là khóa chính)
    public TaiKhoanDTO getBySdt(String sdt) {
        return tkDAO.getByID(sdt);
    }      
    

    // Thêm phương thức mới để lấy tài khoản theo username
    public TaiKhoanDTO getByUsername(String username) {
        ArrayList<TaiKhoanDTO> list = tkDAO.getALL();
        for (TaiKhoanDTO tk : list) {
            if (tk.getUseName().equals(username)) {
                return tk;
            }
        }
        return null;
    }

    // Thêm phương thức kiểm tra đăng nhập
    public boolean checkLogin(String username, String password) {
        TaiKhoanDTO tk = getByUsername(username);
        if (tk == null) {
            return false;
        }
        return tk.getMatKhau().equals(password) && tk.getTrangThai() == 1;
    }
    
     public ArrayList<TaiKhoanDTO> search(String searchContent) {
        if (searchContent == null || searchContent.trim().isEmpty()) {
            // Nếu không có nội dung tìm kiếm, trả về toàn bộ danh sách
            return tkDAO.getALL();
        }

        return tkDAO.search(searchContent.trim());
    }
     
    // Lấy danh sách mã nhân viên chưa có tài khoản
    public ArrayList<String> getDanhSachMaNVChuaCoTK() {
        return tkDAO.getDSMaNVChuaCoTaiKhoan();
    }

    public String getSdtByMaNV(String maNV) {        
        return tkDAO.getSdtNV(maNV); 
    }

}

