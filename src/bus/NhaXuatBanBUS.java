package bus;

import dao.NhaXuatBanDAO;
import dto.NhaXuatBanDTO;
import java.util.ArrayList;

public class NhaXuatBanBUS {

    NhaXuatBanDAO nxbDAO = new NhaXuatBanDAO();
    ArrayList<NhaXuatBanDTO> listNXB = nxbDAO.getALL();

    public ArrayList<NhaXuatBanDTO> getAllNhaXuatBan() {
        return nxbDAO.getALL();
    }

    public String addNhaXuatBan(NhaXuatBanDTO nxb) {
        if (nxbDAO.has(nxb.getMaNXB())) {
            return "Mã nhà nhà xuất bản đã tồn tại";
        }
        if (nxbDAO.add(nxb)) {
            return "Thêm nhà xuất bản thành công";
        }
        return "Thêm thất bại";
    }

    public String deleteNhaXuatBan(String manxb) {
        if (nxbDAO.delete(manxb)) {
            return "Xóa nhà xuất bản thành công";
        }
        return "Xóa nhà xuất bản thất bại";
    }

    public String updateNhaXuatBan(NhaXuatBanDTO nxb) {
        if (nxbDAO.update(nxb)) {
            return "Cập nhật nhà xuất bản thành công";
        }
        return "Cập nhật nhà xuất bản thất bại";
    }

    public NhaXuatBanDTO getByName(String tennxb) {
        return nxbDAO.getByName(tennxb);
    }

    public String getTenNXBByMa(String maNXB) {
        listNXB =  nxbDAO.getALL();
        for (NhaXuatBanDTO nxb : listNXB) {
            if (nxb.getMaNXB().equals(maNXB)) {
                return nxb.getTenNXB();
            }
        }
        return "Không rõ";
    }

}
