/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import Config.Constant;
import dto.SachDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author MZI
 */

public class SachDAO implements DAOInterface<SachDTO> {
    private Connection con;
    
    public boolean openConnection() {
        try {
            // Lấy kết nối từ lớp Constant
            con = Constant.getInstance().getConnection();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Hàm đóng kết nối với database.
    public void closeConnection() {
        try {
            if (con != null && !con.isClosed()) {
                con.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    
//    @Override
//    public ArrayList<SachDTO> getAll() {
//        ArrayList<SachDTO> arr = new ArrayList<>();
//        if (openConnection()) {
//            try {
//                String query = "SELECT * FROM Sach WHERE trangThai = 1";
//                PreparedStatement ps = con.prepareStatement(query);
//                ResultSet rs = ps.executeQuery();   // Trả về 1 đối tượng dạng bảng
//                while (rs.next()) {   // Duyệt qua result set
//                    SachDTO sach = new SachDTO(); // Tạo đối tượng
//                    sach.setId(rs.getString(1));
//                    sach.setTenSach(rs.getString(2));
//                    sach.setSoLuong(rs.getInt(3));
//                    sach.setTacGia(rs.getString(4));
//                    sach.setNhaXuatBan(rs.getString(5));
//                    sach.setTheLoai(rs.getString(6));
//                    sach.setGiaBan(rs.getInt(7)); 
//                    sach.setMaKho(rs.getString(8)); sach.setHinhAnh(rs.getString(9));
//                    arr.add(sach);
//                }
//                rs.close();
//                ps.close();
//            } catch (Exception e) {
//                e.printStackTrace();
//            } finally {
//                closeConnection();
//            }
//
//        }
//        return arr;
//    }

    @Override
    public boolean has(String masp) {
        boolean result = false;
        if (openConnection()) {
            try {
                String query = "SELECT * FROM Sach WHERE id = ?";
                PreparedStatement ps = con.prepareStatement(query);
                ps.setString(1, masp);
                ResultSet rs = ps.executeQuery();
                result = rs.next(); // Di chuyển con trỏ rs đến dòng tiếp theo, trả về true/false. Khi k có dòng tiếp theo để di chuyển tới thì trả về false, ngược lại thì true tức là có ít nhất 1 dòng dữ liệu
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                closeConnection();
            }
        }
        return result;
    }

    @Override
    public ArrayList<SachDTO> getALL() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean add(SachDTO d) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public boolean delete(String d) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public boolean update(SachDTO d) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public SachDTO getByID(String d) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public ArrayList<SachDTO> search(String d) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
    
    
}
