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

                nxb.setMaNXB(rs.getString(1));

                nxb.setTenNXB(rs.getString(2));
                nxb.setDiachiNXB(rs.getString(3));
                nxb.setSdt(rs.getString(4));

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

            //B1
            conn.openConnection();

            //B2
            String query = "SELECT * FROM NhaXuatBan WHERE maNXB = ?";

            //B3
            PreparedStatement ps = conn.getConnection().prepareStatement(query);
            ps.setString(1, maNXB);

            //B4
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
        String query = "INSERT INTO NhaXuatBan (tenNXB, diaChiNXB, sdt, trangThai) VALUES ( ?, ?, ?, ?)";
        boolean result = false;

        try {
            conn.openConnection();
            PreparedStatement ps = conn.getConnection().prepareStatement(query);
            ps.setString(1, nxb.getTenNXB());
            ps.setString(2, nxb.getDiachiNXB());
            ps.setString(3, nxb.getSdt());
            ps.setInt(4, 1); // 1: Đang hoạt động

            result = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi thêm NXB: " + e.getMessage());

        } finally {
            conn.closeConnection();
        }
        return result;
    }


    @Override
    public boolean delete(String manxb) {
        boolean result = false;

        try {

            //B1
            conn.openConnection();

            //B2
            String query = "UPDATE NhaXuatBan SET trangthai = 0 WHERE maNXB = ?";

            //B3
            PreparedStatement ps = conn.getConnection().prepareStatement(query);
            ps.setString(1, manxb);

            //B4
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

        String query = "UPDATE NhaXuatBan SET tenNXB = ?, diaChiNXB = ?, sdt = ? WHERE maNXB = ?";
        boolean result = false;
        try {
            conn.openConnection();

            PreparedStatement ps = conn.getConnection().prepareStatement(query);
            ps.setString(1, nxb.getTenNXB());
            ps.setString(2, nxb.getDiachiNXB());
            ps.setString(3, nxb.getSdt());
            ps.setString(4, nxb.getMaNXB());
            if (ps.executeUpdate() > 0) {
                result = true;
            }
        } catch (Exception e) {
            System.err.println("Lỗi khi cập nhật NXB: " + e.getMessage());

        } finally {
            conn.closeConnection();
        }
        return result;
    }


    @Override
    public NhaXuatBanDTO getByID(String maNXB) {
        NhaXuatBanDTO nxb = null;

        try {

            //B1
            conn.openConnection();

            //B2
            String query = "SELECT * FROM NhaXuatBan WHERE maNXB = ?";

            //B3
            PreparedStatement ps = conn.getConnection().prepareStatement(query);
            ps.setString(1, maNXB);

            //B4
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String manxb = rs.getString(1);
                String tenNXB = rs.getString(2);
                String diaChiNXB = rs.getString(3);
                String sdt = rs.getString(4);

}

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
                    + "diaChiNXB LIKE ? OR"
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
                nxb.setMaNXB(rs.getString(1));
                nxb.setTenNXB(rs.getString(2));
                nxb.setDiachiNXB(rs.getString(3));
                nxb.setSdt(rs.getString(4));

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
                   // rs.getString("maNXB"),
                    rs.getString("tenNXB"),
                    rs.getString("diaChiNXB"),
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


}

    

