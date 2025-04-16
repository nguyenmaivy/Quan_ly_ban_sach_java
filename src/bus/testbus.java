//package bus;
//
//import dao.NhaXuatBanDAO;
//import dto.NhaXuatBanDTO;
//import java.util.ArrayList;
//
//public class testbus {
//    public static void main(String[] args) {
//        NhaXuatBanBUS nxbBUS = new NhaXuatBanBUS();

//        // Tạo một nhà xuất bản mới với đầy đủ 5 tham số
//        NhaXuatBanDTO nxbMoi = new NhaXuatBanDTO("NXB05", "HCM", "0123456789", "Nhà Xuất Bản Trẻ", 1);
//
//        // Thêm nhà xuất bản mới
//        System.out.println(nxbBUS.addNhaXuatBan(nxbMoi));
//
//        // Lấy danh sách tất cả nhà xuất bản                         // chay loi
//        System.out.println("Danh sách nhà xuất bản:");
//        for (NhaXuatBanDTO nxb : nxbBUS.getAllNhaXuatBan()) {
//            System.out.println(nxb.getMaNXB() + " | " + nxb.getTenNXB() + " | " + nxb.getDiachiNXB() + " | " + nxb.getSdt() + " | " + nxb.getTrangThai());
//        }
//
//        // Cập nhật thông tin nhà xuất bản
//        nxbMoi.setSdt("0987654321"); // Thay đổi số điện thoại
//        System.out.println(nxbBUS.updateNhaXuatBan(nxbMoi));
//
//        // Tìm kiếm nhà xuất bản theo tên
//        NhaXuatBanDTO foundNXB = nxbBUS.getByName("Nhà Xuất Bản Trẻ");
//        if (foundNXB != null) {
//            System.out.println("Tìm thấy nhà xuất bản: " + foundNXB.getTenNXB());
//        } else {
//            System.out.println("Không tìm thấy nhà xuất bản.");
//        }

//        // Xóa nhà xuất bản
//        System.out.println(nxbBUS.deleteNhaXuatBan("NXB05"));
//    }
//}



// test TAIKHOAN

//package bus;
//
//import bus.TaiKhoanBUS;
//import dto.TaiKhoanDTO;
//import java.util.ArrayList;
//
//public class testbus {
//    public static void main(String[] args) {
//        TaiKhoanBUS tkBUS = new TaiKhoanBUS();

//       //Thêm tài khoản mới (sdt là khóa chính)
//        TaiKhoanDTO newTK = new TaiKhoanDTO("2", "user123", "password", "NV01", "KH01", 2, 1);
//        System.out.println(tkBUS.addTaiKhoan(newTK));

//        // Lấy danh sách tài khoản
//        ArrayList<TaiKhoanDTO> list = tkBUS.getAllTaiKhoan();
//        System.out.println("Danh sách tài khoản:");
//        for (TaiKhoanDTO tk : list) {
//            System.out.println(tk.getSdt() + ", " + tk.getUseName() + ", " + tk.getMatKhau() + ", " 
//                + tk.getMaNV() + ", " + tk.getMaKH() + ", " + tk.getMaNhomQuyen() + ", " + tk.getTrangThai());
//        }
//
//        // Lấy tài khoản theo số điện thoại (vì sdt là khóa chính)
//        String sdtToFind = "0987654321";
//        TaiKhoanDTO tk = tkBUS.getBySdt(sdtToFind);
//        if (tk != null) {
//            System.out.println("Tìm thấy tài khoản: " + tk.getSdt() + " - " + tk.getUseName());
//        } else {
//            System.out.println("Không tìm thấy tài khoản với SĐT: " + sdtToFind);
//        }

//        // Cập nhật tài khoản theo sdt
//        if (tk != null) {
//            tk.setMatKhau("newpassword");
//            System.out.println(tkBUS.updateTaiKhoan(tk));
//        }
//
//        // Xóa tài khoản theo sdt
//        System.out.println(tkBUS.deleteTaiKhoan(sdtToFind));
//    }
//}


// TEST TACGIASACH

package bus;

import bus.TacGiaSachBUS;
import dto.TacGiaSachDTO;
import java.util.ArrayList;

public class testbus {
//    public static void main(String[] args) {
//        TacGiaSachBUS tgsBUS = new TacGiaSachBUS();
//        
//        // 1️⃣ Thêm một tác giả - sách mới
//        TacGiaSachDTO newTGS = new TacGiaSachDTO("TG001", "S001", "Tác giả chính");
//        String resultAdd = tgsBUS.addTacGiaSach(newTGS);
//        System.out.println(resultAdd);
//        
//        // 2️⃣ Hiển thị danh sách tất cả tác giả - sách
//        ArrayList<TacGiaSachDTO> list = tgsBUS.getAllTacGiaSach();
//        System.out.println("Danh sách tác giả - sách:");
//        for (TacGiaSachDTO tgs : list) {
//            System.out.println("Mã TG: " + tgs.getMaTG() + ", Mã sách: " + tgs.getMaSach() + ", Vai trò: " + tgs.getVaiTro());
//        }
//        
//        // 3️⃣ Cập nhật thông tin tác giả - sách
//        TacGiaSachDTO updatedTGS = new TacGiaSachDTO("TG001", "S001", "Đồng tác giả");
//        String resultUpdate = tgsBUS.updateTacGiaSach(updatedTGS);
//        System.out.println(resultUpdate);
//        
//        
//        // 5️⃣ Xóa tác giả - sách
//        String resultDelete = tgsBUS.deleteTacGiaSach("TG001");
//        System.out.println(resultDelete);
//    }
}
