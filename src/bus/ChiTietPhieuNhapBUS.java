
package bus;
import dto.*;
import dao.*;
import java.util.ArrayList;
/**
 *
 * @author MZI
 */
public class ChiTietPhieuNhapBUS {
    ChiTietPhieuNhapDAO ctpndao = new ChiTietPhieuNhapDAO();
    public  ArrayList<ChiTietPhieuNhapDTO> getAllCTPN(){
        return ctpndao.getALL();
    }
    public void add(ChiTietPhieuNhapDTO ctpn){
        ctpndao.add(ctpn);
    }
    public ArrayList<ChiTietPhieuNhapDTO> getAllByID(String soPN){
        return ctpndao.getAllByID(soPN);
    }
    public ArrayList<ChiTietPhieuNhapDTO> getByGia(String data, String condition, String ma){
        return ctpndao.getByGia(data, condition, ma);
    }
    public ArrayList<ChiTietPhieuNhapDTO> getByCondition(String data,String condition,String ma){
        return ctpndao.getByCondition(data, condition, ma);
    }
    public ArrayList<ChiTietPhieuNhapDTO> getBySoluong(String data, String condition, String ma){
        return ctpndao.getBySoluong(data, condition, ma);
    }
    public void updateTrangThai(String ma){
        ctpndao.updateTrangthai(ma);
    }
}
