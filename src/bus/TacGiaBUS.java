package bus;

import dao.TacGiaDAO;
import dto.TacGiaDTO;
import java.util.ArrayList;

public class TacGiaBUS {

    TacGiaDAO tacGiaDAO = new TacGiaDAO();

    public ArrayList<TacGiaDTO> getAllTacGia() {
        return tacGiaDAO.getALL();
    }
    public boolean has(String maTG){
        return tacGiaDAO.has(maTG);
    }

    public String addTacGia(TacGiaDTO tacgia) {
        if (tacGiaDAO.has(tacgia.getMaTG())) {
            return "Mã tác giả đã tồn tại";
        }
        if (tacGiaDAO.add(tacgia)) {
            return "Thêm tác giả thành công";
        }
        return "Thêm thất bại";
    }

    public String updateTacGia(TacGiaDTO tacgia) {
        if (tacGiaDAO.update(tacgia)) {
            return "Cập nhật tác giả thành công";
        }
        return "Cập nhật tác giả thất bại";
    }

    public String deleteTacGia(String tacgia) {
        if (tacGiaDAO.delete(tacgia)) {
            return "Xóa tác giả thành công";
        }
        return "Xóa tác giả thất bại";
    }

    public TacGiaDTO getByName(String tacgia) {
        return tacGiaDAO.getByName(tacgia);
    }

    // Thêm phương thức search
    public ArrayList<TacGiaDTO> search(String txt, String type) {
        ArrayList<TacGiaDTO> result = new ArrayList<>();
        txt = txt.toLowerCase();

        switch (type) {
            case "Tất cả":
                return tacGiaDAO.search(txt); // Gọi trực tiếp search từ DAO vì tìm trên tất cả cột
            case "Mã tác giả":
                for (TacGiaDTO tg : tacGiaDAO.search(txt)) {
                    if (tg.getMaTG().toLowerCase().contains(txt)) {
                        result.add(tg);
                    }
                }
                break;
            case "Tên tác giả":
                for (TacGiaDTO tg : tacGiaDAO.search(txt)) {
                    if (tg.getTenTG().toLowerCase().contains(txt)) {
                        result.add(tg);
                    }
                }
                break;
            case "Liên lạc":
                for (TacGiaDTO tg : tacGiaDAO.search(txt)) {
                    if (tg.getLienLac().toLowerCase().contains(txt)) {
                        result.add(tg);
                    }
                }
                break;
        }
        return result;
    }
    public String getMaxMaTG(){
        return tacGiaDAO.getMaxMaTG();
    }
}
