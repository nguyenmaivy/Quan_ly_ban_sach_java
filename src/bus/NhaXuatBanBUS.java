<<<<<<< HEAD
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
=======
>>>>>>> a3477ed (giao dien nha xuat ban, tai khoan)
package bus;
import dao.NhaXuatBanDAO;
import dto.NhaXuatBanDTO;
import java.util.ArrayList;
<<<<<<< HEAD
public class NhaXuatBanBUS {
     NhaXuatBanDAO nxbDAO = new NhaXuatBanDAO();
    
    public ArrayList<NhaXuatBanDTO> getAllNhaXuatBan(){
        return nxbDAO.getALL();
    }
    
    public String addNhaXuatBan(NhaXuatBanDTO nxb){
        if(nxbDAO.has(nxb.getMaNXB()))
            return "Mã nhà nhà xuất bản đã tồn tại";
        if(nxbDAO.add(nxb))
            return "Thêm nhà xuất bản thành công";
        return "Thêm thất bại";
    }
    
    public String deleteNhaXuatBan(String manxb){
        if(nxbDAO.delete(manxb))
            return "Xóa nhà xuất bản thành công";
        return "Xóa nhà xuất bản thất bại";
    }
    
    public String updateNhaXuatBan(NhaXuatBanDTO nxb){
        if(nxbDAO.update(nxb))
            return "Cập nhật nhà xuất bản thành công";
        return "Cập nhật nhà xuất bản thất bại";
    }
  
    public NhaXuatBanDTO getByName(String tennxb) {
    	return nxbDAO.getByName(tennxb);
    }
   
=======

public class NhaXuatBanBUS {
    private final NhaXuatBanDAO nxbDAO = new NhaXuatBanDAO();
    public static String generateNewMaNXB() {
        NhaXuatBanDAO nxbDAO = new NhaXuatBanDAO();  // Tạo đối tượng của NhaXuatBanDAO
        String lastMaNXB = nxbDAO.getLastMaNXB(); // Lấy mã lớn nhất từ CSDL
    if (lastMaNXB == null) {
        return "XB01"; // Nếu chưa có dữ liệu, bắt đầu từ XB01
    }
    
    // Lấy phần số từ mã hiện tại (VD: "XB09" -> 9, "XB99" -> 99, "XB100" -> 100)
    int num = Integer.parseInt(lastMaNXB.substring(2)) + 1;

    // Format mã mới: "XBxx" (từ 01 đến 99), sau đó chuyển sang "XBxxx"
    return String.format("XB%02d", num); // Khi num ≥ 100, tự động thành "XB100"
}

    
    public ArrayList<NhaXuatBanDTO> getAllNhaXuatBan() {
        return nxbDAO.getALL();
    }
    
   public String addNhaXuatBan(NhaXuatBanDTO nxb) {
    // Gọi phương thức tạo mã mới tự động
    nxb.setMaNXB(generateNewMaNXB());  // Tạo mã tự động cho nhà xuất bản
    
    // Kiểm tra và thêm nhà xuất bản vào cơ sở dữ liệu
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
>>>>>>> a3477ed (giao dien nha xuat ban, tai khoan)
}
