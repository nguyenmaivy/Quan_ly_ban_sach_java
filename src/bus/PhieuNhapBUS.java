package bus;

import dto.*;
import dao.*;
import java.util.ArrayList;

public class PhieuNhapBUS {

    PhieuNhapDAO phieuNhapDAO = new PhieuNhapDAO();

    public void add(PhieuNhapDTO pn) {
        phieuNhapDAO.add(pn);
    }

    public int getSoLuongPhieu() {
        return phieuNhapDAO.countPhieuNhap();
    }

    public String delete(String sopn) {
        return phieuNhapDAO.delete(sopn) ? "Xóa thành công" : "Đã xảy ra lỗi";
    }

    public int countPhieuNhap() {
        return phieuNhapDAO.countPhieuNhap();
    }

    public ArrayList<PhieuNhapDTO> getAllPhieuNhap() {
        return phieuNhapDAO.getALL();
    }

    public ArrayList<PhieuNhapDTO> getByGia(String data, String condition) {
        return phieuNhapDAO.getByGia(data, condition);
    }

    public ArrayList<PhieuNhapDTO> getByCondition(String dataString, String condition) {
        return phieuNhapDAO.getByID(dataString, condition);
    }

    public ArrayList<PhieuNhapDTO> getByDate(String data, String condition) {
        return phieuNhapDAO.getByDate(data, condition);
    }

    public ArrayList<PhieuNhapDTO> getByStartEnd(String start, String end) {
        return phieuNhapDAO.getByStartEnd(start, end);
    }

    public ArrayList<String> getAllMaKho() {
        return phieuNhapDAO.getAllMaKho();
    }

    public boolean checkCancelPn(String soPN) {
        ChiTietPhieuNhapDAO chiTietDAO = new ChiTietPhieuNhapDAO();
        ArrayList<ChiTietPhieuNhapDTO> chiTietList = chiTietDAO.getAllByID(soPN);

        for (ChiTietPhieuNhapDTO chiTiet : chiTietList) {
            if (chiTiet.getSoLuongNhap() > 0) { // Nếu có sản phẩm đã nhập
                return false; // Không thể hủy phiếu nhập
            }
        }
        return true; // Có thể hủy phiếu nhập
    }

    public boolean cancelPhieuNhap(String soPN) {
        return phieuNhapDAO.delete(soPN); // Xóa phiếu nhập bằng cách cập nhật `trangThai = 0`
    }

}
