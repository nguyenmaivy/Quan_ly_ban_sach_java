
package bus;

import dao.TaiKhoanDAO;
import dto.TaiKhoanDTO;
import java.util.ArrayList;

public class TaiKhoanBUS {

    private TaiKhoanDAO tkDAO = new TaiKhoanDAO();

    // Lấy danh sách tất cả tài khoản
    public ArrayList<TaiKhoanDTO> getAllTaiKhoan() {
        return tkDAO.getALL();
    }

    // Thêm tài khoản mới, kiểm tra sdt là duy nhất
    public String addTaiKhoan(TaiKhoanDTO tk) {
        if (tkDAO.has(tk.getSdt())) {
            return "Số điện thoại tài khoản đã tồn tại";
        }
        if (tkDAO.add(tk)) {
            return "Thêm tài khoản thành công";
        }
        return "Thêm tài khoản thất bại";
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

}
