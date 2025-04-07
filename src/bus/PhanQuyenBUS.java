/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bus;

import dto.nhomQuyenDTO;
import dao.PhanQuyenDAO;
import java.util.ArrayList;

/**
 *
 * @author MZI
 */
public class PhanQuyenBUS {

    PhanQuyenDAO pqdao = new PhanQuyenDAO();

    public ArrayList<nhomQuyenDTO> getALL() {
        return pqdao.getALL();
    }

    public String addPQ(nhomQuyenDTO nq) {
        if (pqdao.add(nq)) {
            return "Thêm nhóm quyền thành công" + nq.getMaNhomQuyen();
        }
        return "Thêm thất bại";
    }

    public String deletePQ(String maNhomQuyen) {
        if (pqdao.delete(maNhomQuyen)) {
            return "Xóa nhà cung cấp thành công";
        }
        return "Xóa nhà cung cấp thất bại";
    }

    public String updatePQ(nhomQuyenDTO nq) {
        if (pqdao.update(nq)) {
            return "Cập nhật nhóm quyền thành công";
        }
        return "Cập nhật nhóm quyền thất bại";
    }

    public ArrayList<nhomQuyenDTO> search(String searchContent) {
        return pqdao.search(searchContent);
    }

    public boolean kiemTraQuyen(int maNhomQuyen, String chucNang, String hanhDong) {
        // Admin có tất cả quyền
        if (maNhomQuyen == 1) {
            return true;
        }

        //  Quản lý bán hàng (mã nhóm quyền = 2)
        if (maNhomQuyen == 2) {
            // Các chức năng của quản lý bán hàng
            if ((chucNang.equals("hoadon") || chucNang.equals("khachhang") || chucNang.equals("thongke") || chucNang.equals("duyetdonhang"))
                    && (hanhDong.equals("view") || hanhDong.equals("update") || hanhDong.equals("delete"))) {
                return true;
            }
        }

        // ️ Quản lý kho (mã nhóm quyền = 3)
        if (maNhomQuyen == 3) {
            // Các chức năng của quản lý kho
            if ((chucNang.equals("phieunhap") || chucNang.equals("sach") || chucNang.equals("theloai") || chucNang.equals("khosach")
                    || chucNang.equals("tacgia") || chucNang.equals("nhaxuatban") || chucNang.equals("thongke") || chucNang.equals("dangxuat"))
                    && (hanhDong.equals("view") || hanhDong.equals("update") || hanhDong.equals("delete"))) {
                return true;
            }
        }

        //  Khách hàng (mã nhóm quyền = 4) - Ví dụ
        if (maNhomQuyen == 4) {
            if (chucNang.equals("sanpham") && (hanhDong.equals("view") || hanhDong.equals("buy") || hanhDong.equals("pay") || hanhDong.equals("addCart"))) {
                return true;
            }
        }

        //  Mặc định không có quyền
        return false;
    }

    public void add(String tenNhomQuyen, ArrayList<String> ctQuyen) {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        nhomQuyenDTO nq = new nhomQuyenDTO();
        nq.setTenNhomQuyen(tenNhomQuyen);
        nq.setTrangThai(1); // Mặc định trạng thái là 1 (hoạt động)
        addPQ(nq);
    }

}
