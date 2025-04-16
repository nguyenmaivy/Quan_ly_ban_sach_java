/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import Config.Constant;
import dto.NhaXuatBanDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NhaXuatBanDAO implements DAOInterface<NhaXuatBanDTO> {

    Constant conn = new Constant();


    @Override
    public ArrayList<NhaXuatBanDTO> getALL() {
        ArrayList<NhaXuatBanDTO> arr = new ArrayList<>();

        try {
            conn.openConnection();
            String query = "SELECT * FROM NhaXuatBan where trangThai = 1";
            PreparedStatement ps = conn.getConnection().prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                NhaXuatBanDTO nxb = new NhaXuatBanDTO();
                nxb.setMaNXB(rs.getString("maNXB"));
                nxb.setTenNXB(rs.getString("tenNXB")); 
                nxb.setDiachiNXB(rs.getString("diachiNXB")); 
                nxb.setSdt(rs.getString("sdt"));                              
                nxb.setTrangThai(rs.getInt("trangThai"));
                arr.add(nxb);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.closeConnection();
        }
        return arr;

    }

    @Override
    public boolean has(String maNXB) {
        boolean result = false;

        try {            
            conn.openConnection();            
            String query = "SELECT * FROM NhaXuatBan WHERE maNXB = ?";            
            PreparedStatement ps = conn.getConnection().prepareStatement(query);
            ps.setString(1, maNXB);            
            ResultSet rs = ps.executeQuery();
            result = rs.next();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.closeConnection();
        }
        return result;
    }        

    
    @Override
    public boolean add(NhaXuatBanDTO nxb) {
        String insertQuery = "INSERT INTO NhaXuatBan VALUES (?, ?, ?, ?, ?)";
        boolean result = false;

        try {
            conn.openConnection();
            PreparedStatement psInsert = conn.getConnection().prepareStatement(insertQuery);

            psInsert.setString(1, nxb.getMaNXB()); // Giờ mã lấy từ DTO
            psInsert.setString(2, nxb.getTenNXB());           
            psInsert.setString(3, nxb.getDiachiNXB());
            psInsert.setString(4, nxb.getSdt());
            psInsert.setInt(5, 1); // 1: Đang hoạt động

            result = psInsert.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Lỗi khi thêm nhà xuất bản: " + e.getMessage());
        } finally {
            conn.closeConnection();
        }

        return result;
    }



    @Override
    public boolean delete(String manxb) {
        boolean result = false;

        try {            
            conn.openConnection();           
            String query = "UPDATE NhaXuatBan SET trangthai = 0 WHERE maNXB = ?";            
            PreparedStatement ps = conn.getConnection().prepareStatement(query);
            ps.setString(1, manxb);
            if (ps.executeUpdate() > 0) {
                result = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
   
        } finally {
            conn.closeConnection();
        }
        return result;

    }

    @Override
        public boolean update(NhaXuatBanDTO nxb) {
            // Kiểm tra số điện thoại đã tồn tại chưa, ngoại trừ chính nó
            if (isPhoneNumberExists(nxb.getSdt(), nxb.getMaNXB())) {
                System.err.println("Số điện thoại đã tồn tại!");
                return false;
            }

            String query = "UPDATE NhaXuatBan SET tenNXB = ?, diaChiNXB = ?, sdt = ? WHERE maNXB = ?";
            boolean result = false;

            try {
                conn.openConnection();
                PreparedStatement ps = conn.getConnection().prepareStatement(query);
                ps.setString(1, nxb.getTenNXB()); 
                ps.setString(2, nxb.getDiachiNXB());
                ps.setString(3, nxb.getSdt());
                //ps.setInt(4, nxb.getTrangThai());
                ps.setString(4, nxb.getMaNXB());  // Điều kiện để tìm đúng bản ghi cập nhật

                    result = ps.executeUpdate() > 0;
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    conn.closeConnection();
                }

            return result;

        }



    @Override
    public NhaXuatBanDTO getByID(String maNXB) {
        NhaXuatBanDTO nxb = null;

        try {
            
            conn.openConnection();          
            String query = "SELECT * FROM NhaXuatBan WHERE maNXB = ?";            
            PreparedStatement ps = conn.getConnection().prepareStatement(query);
            ps.setString(1, maNXB);            
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {//               
                    nxb = new NhaXuatBanDTO();
                    nxb.setMaNXB(rs.getString("maNXB"));
                    nxb.setTenNXB(rs.getString("tenNXB"));
                    nxb.setDiachiNXB(rs.getString("diachiNXB"));
                    nxb.setSdt(rs.getString("sdt"));
                    nxb.setTrangThai(rs.getInt("trangThai"));}

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            conn.closeConnection();
        }
        return nxb;
    }


    @Override
    public ArrayList<NhaXuatBanDTO> search(String searchContent) {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        ArrayList<NhaXuatBanDTO> arr = new ArrayList<>();

        try {

            //B1
            conn.openConnection();

            //B2
            String query = "SELECT * FROM NhaXuatBan WHERE "
                    + "maNXB LIKE ? OR "
                    + "tenNXB LIKE ? OR"
                    + "diachiNXB LIKE ? OR"
                    + "sdt LIKE ?";

            //B3
            PreparedStatement ps = conn.getConnection().prepareStatement(query);
            ps.setString(1, "%" + searchContent + "%");
            ps.setString(2, "%" + searchContent + "%");
            ps.setString(3, "%" + searchContent + "%");
            ps.setString(4, "%" + searchContent + "%");

            //B4
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                NhaXuatBanDTO nxb = new NhaXuatBanDTO();
                nxb.setMaNXB(rs.getString("maNXB"));
                nxb.setTenNXB(rs.getString("tenNXB"));
                nxb.setDiachiNXB(rs.getString("diachiNXB"));
                nxb.setSdt(rs.getString("sdt"));
                arr.add(nxb);

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.closeConnection();
        }
        return arr;
    }


   
   public NhaXuatBanDTO getByName(String tenNXB) {
        String query = "SELECT * FROM NhaXuatBan WHERE tenNXB LIKE ?";
        NhaXuatBanDTO nxb = null;

        try {
            conn.openConnection();
            PreparedStatement ps = conn.getConnection().prepareStatement(query);
            ps.setString(1, "%" + tenNXB + "%");
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                nxb = new NhaXuatBanDTO(
                    rs.getString("maNXB"),
                    rs.getString("tenNXB"),
                    rs.getString("diachiNXB"),
                    rs.getString("sdt"),
                    rs.getInt("trangThai")
                );
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi tìm kiếm NXB theo tên: " + e.getMessage());

        } finally {
            conn.closeConnection();
        }
        return nxb;
    }
   
    // Phương thức lấy mã nhà xuất bản lớn nhất
   public String getLastMaNXB() {
        String lastMaNXB = null;
        String sql = "SELECT TOP 1 maNXB FROM NhaXuatBan ORDER BY maNXB DESC";  // Lấy mã nhà xuất bản lớn nhất

        try (Connection conn = new Constant().getConnection();  // Kết nối với cơ sở dữ liệu
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                lastMaNXB = rs.getString("maNXB");  // Lấy mã nhà xuất bản
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lastMaNXB;  // Trả về mã nhà xuất bản lớn nhất
    }
   
//   public boolean isPhoneNumberExists(String phoneNumber) {
//        String sql = "SELECT COUNT(*) FROM NhaXuatBan WHERE sdt = ?";
//        boolean exists = false;
//
//        try {
//            conn.openConnection(); // Mở kết nối
//            PreparedStatement stmt = conn.getConnection().prepareStatement(sql);
//            stmt.setString(1, phoneNumber);
//            //stmt.setString(2, currentMaNXB);  // Loại trừ chính nó
//            ResultSet rs = stmt.executeQuery();
//
//            if (rs.next()) {
//                exists = rs.getInt(1) > 0;  // Nếu số lượng > 0, số điện thoại đã tồn tại
//            }
//        } catch (SQLException e) {
//            System.err.println("Lỗi khi kiểm tra số điện thoại: " + e.getMessage());
//        } finally {
//            conn.closeConnection(); // Đóng kết nối
//        }
//        return exists;
//    }
   
   public boolean isPhoneNumberExists(String phoneNumber, String currentMaNXB) {
        String sql;
        boolean exists = false;

        try {
            conn.openConnection();

            if (currentMaNXB == null || currentMaNXB.isEmpty()) {
                // Kiểm tra số điện thoại có tồn tại khi thêm mới
                sql = "SELECT COUNT(*) FROM NhaXuatBan WHERE sdt = ?";
            } else {
                // Kiểm tra số điện thoại có tồn tại khi sửa (loại trừ chính nó)
                sql = "SELECT COUNT(*) FROM NhaXuatBan WHERE sdt = ? AND maNXB != ?";
            }

            PreparedStatement stmt = conn.getConnection().prepareStatement(sql);
            stmt.setString(1, phoneNumber);

            if (currentMaNXB != null && !currentMaNXB.isEmpty()) {
                stmt.setString(2, currentMaNXB); // Chỉ set khi sửa
            }

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                exists = rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi kiểm tra số điện thoại: " + e.getMessage());
        } finally {
            conn.closeConnection();
        }

        return exists;
    }

    public String NextMaNXB() {
        String getMaxIdQuery = "SELECT MAX(maNXB) FROM NhaXuatBan";
        String newId = "XB01"; // Mã mặc định nếu bảng trống

        try {
            conn.openConnection();
            PreparedStatement psGetMax = conn.getConnection().prepareStatement(getMaxIdQuery);
            ResultSet rs = psGetMax.executeQuery();

            if (rs.next() && rs.getString(1) != null) {
                String maxId = rs.getString(1); // VD: XB07
                int num = Integer.parseInt(maxId.substring(2)) + 1;
                newId = String.format("XB%02d", num); // VD: XB08
            }

        } catch (SQLException e) {
            System.err.println("Lỗi khi sinh mã mới: " + e.getMessage());
        } finally {
            conn.closeConnection();
        }

        return newId;
    }


}



    

