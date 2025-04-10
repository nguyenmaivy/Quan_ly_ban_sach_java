package bus;

import dto.*;
import dao.*;
import java.util.ArrayList;
import org.apache.poi.ss.usermodel.FormulaError;

public class PhieuNhapBUS {

    PhieuNhapDAO phieuNhapDAO = new PhieuNhapDAO();
    ArrayList<PhieuNhapDTO> listpn = phieuNhapDAO.getALL();

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
    // Thêm vào PhieuNhapBUS.java

    public String getAutoIncrement() {
        int nextID = phieuNhapDAO.getAutoIncrement();
        return "PN" + String.format("%02d", nextID);
    }

    public ArrayList<PhieuNhapDTO> search(String txt, String type) {
        ArrayList<PhieuNhapDTO> result = new ArrayList<>();
        txt = txt.toLowerCase();

        ArrayList<PhieuNhapDTO> searchResult = phieuNhapDAO.search(txt);
        if (searchResult == null) {
            searchResult = new ArrayList<>(); // Return empty list if DAO returns null
        }

        switch (type) {
            case "Tất cả":
                return searchResult;
            case "Mã phiếu nhập":
                for (PhieuNhapDTO pn : searchResult) {
                    if (pn.getSoPN().toLowerCase().contains(txt)) {
                        result.add(pn);
                    }
                }
                break;
            case "Nhà xuất bản":
                NhaXuatBanBUS nxbbus = new NhaXuatBanBUS();
                for (PhieuNhapDTO pn : searchResult) {
                    String tenNXB = nxbbus.getTenNXBByMa(pn.getMaNXB().toLowerCase());
                    if (tenNXB != null && tenNXB.contains(txt)) {
                        result.add(pn);
                    }
                }
                break;
            case "Nhân viên nhập":
                NhanVienBUS nvbus = new NhanVienBUS();
                for (PhieuNhapDTO pn : searchResult) {
                    String tennv = nvbus.getTenNVByMa(pn.getMaNV().toLowerCase());
                    if (tennv != null && tennv.contains(txt)) {
                        result.add(pn);
                    }
                }
                break;
            case "Kho":
                KhoSachBUS khoSachBUS = new KhoSachBUS();
                for (PhieuNhapDTO pn : searchResult) {
                    String tenkho = khoSachBUS.getTenKhoByMa(pn.getMaKho().toLowerCase());
                    if (tenkho != null && tenkho.contains(txt)) {
                        result.add(pn);
                    }
                }
                break;
            default:
                return new ArrayList<>(); // Return empty list for unknown type
        }
        return result;
    }

}
