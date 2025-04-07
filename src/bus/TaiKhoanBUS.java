///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
//package bus;
//
///**
// *
// * @author LAPTOP
// */
//
//import dao.TaiKhoanDAO;
//import dto.TaiKhoanDTO;
//import java.util.List;
//
//public class TaiKhoanBUS {
//    private TaiKhoanDAO tkDAO = new TaiKhoanDAO();
//
//    // Lấy danh sách tài khoản
//    public List<TaiKhoanDTO> layDanhSachTaiKhoan() {
//        return tkDAO.getAllTaiKhoan();
//    }
//
//    // Kiểm tra định dạng số điện thoại
//    private boolean isValidPhoneNumber(String sdt) {
//        return sdt.matches("^0\\d{9}$"); // Số điện thoại phải có 10 số và bắt đầu bằng 0
//    }
//
//    // Kiểm tra độ dài mật khẩu
//    private boolean isValidPassword(String matKhau) {
//        return matKhau.length() >= 6; // Mật khẩu phải ít nhất 6 ký tự
//    }
//
//    // Thêm tài khoản
//    public boolean themTaiKhoan(String useName, String matKhau, String sdt, int trangThai) {
//        if (useName.isEmpty() || matKhau.isEmpty() || sdt.isEmpty()) {
//            System.out.println("Lỗi: Không được để trống thông tin.");
//            return false;
//        }
//        if (!isValidPhoneNumber(sdt)) {
//            System.out.println("Lỗi: Số điện thoại không hợp lệ.");
//            return false;
//        }
//        if (!isValidPassword(matKhau)) {
//            System.out.println("Lỗi: Mật khẩu phải có ít nhất 6 ký tự.");
//            return false;
//        }
//        if (tkDAO.getTaiKhoanByUsername(useName) != null) {
//            System.out.println("Lỗi: Tên đăng nhập đã tồn tại.");
//            return false;
//        }
//
//        TaiKhoanDTO tk = new TaiKhoanDTO(useName, matKhau, sdt, trangThai);
//        return tkDAO.themTaiKhoan(tk);
//    }
//
//    // Cập nhật tài khoản
//    public boolean capNhatTaiKhoan(String useName, String matKhau, String sdt, int trangThai) {
//        if (useName.isEmpty() || matKhau.isEmpty() || sdt.isEmpty()) {
//            System.out.println("Lỗi: Không được để trống thông tin.");
//            return false;
//        }
//        if (!isValidPhoneNumber(sdt)) {
//            System.out.println("Lỗi: Số điện thoại không hợp lệ.");
//            return false;
//        }
//        if (!isValidPassword(matKhau)) {
//            System.out.println("Lỗi: Mật khẩu phải có ít nhất 6 ký tự.");
//            return false;
//        }
//        if (tkDAO.getTaiKhoanByUsername(useName) == null) {
//            System.out.println("Lỗi: Không tìm thấy tài khoản cần cập nhật.");
//            return false;
//        }
//
//        TaiKhoanDTO tk = new TaiKhoanDTO(useName, matKhau, sdt, trangThai);
//        return tkDAO.updateTaiKhoan(tk);
//    }
//
//    // Xóa tài khoản
//    public boolean xoaTaiKhoan(String useName) {
//        if (useName.isEmpty()) {
//            System.out.println("Lỗi: Tên đăng nhập không được để trống.");
//            return false;
//        }
//        if (tkDAO.getTaiKhoanByUsername(useName) == null) {
//            System.out.println("Lỗi: Không tìm thấy tài khoản cần xóa.");
//            return false;
//        }
//        return tkDAO.deleteTaiKhoan(useName);
//    }
//
//    // Kiểm tra đăng nhập
//    public boolean dangNhap(String useName, String matKhau) {
//        if (useName.isEmpty() || matKhau.isEmpty()) {
//            System.out.println("Lỗi: Tên đăng nhập và mật khẩu không được để trống.");
//            return false;
//        }
//        TaiKhoanDTO tk = tkDAO.getTaiKhoanByUsername(useName);
//        if (tk == null) {
//            System.out.println("Lỗi: Tài khoản không tồn tại.");
//            return false;
//        }
//        if (tk.getTrangThai() == 0) {
//            System.out.println("Lỗi: Tài khoản đã bị khóa.");
//            return false;
//        }
//        return tkDAO.checkLogin(useName, matKhau);
//    }
//}

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
    
}