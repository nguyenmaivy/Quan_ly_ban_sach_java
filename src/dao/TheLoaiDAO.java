/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import Config.Constant;
import dto.TheLoaiDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
/**
 *
 * @author leduc
 */
public class TheLoaiDAO implements DAOInterface<TheLoaiDTO> {
    Constant jdbc = new Constant();

    @Override
    public ArrayList<TheLoaiDTO> getALL() {
        ArrayList<TheLoaiDTO> list = new ArrayList<>();
        try {
            jdbc.openConnection();
            String query = "SELECT * FROM TheLoai WHERE trangThai = 1";
            PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new TheLoaiDTO(
                        rs.getString("maLoai"),
                        rs.getString("tenLoai"),
                        rs.getInt("trangThai")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jdbc.closeConnection();
        }
        return list;
    }

    @Override
    public boolean has(String id) {
        boolean result = false;
        try {
            jdbc.openConnection();
            String query = "SELECT * FROM TheLoai WHERE maLoai = ?";
            PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            result = rs.next();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jdbc.closeConnection();
        }
        return result;
    }

    @Override
    public boolean add(TheLoaiDTO theLoai) {
        boolean result = false;
        try {
            jdbc.openConnection();
            String query = "INSERT INTO TheLoai (maLoai, tenLoai, trangThai) VALUES (?, ?, ?)";
            PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
            ps.setString(1, theLoai.getMaLoai());
            ps.setString(2, theLoai.getTenLoai());
            ps.setInt(3, theLoai.getTrangThai());

            if (ps.executeUpdate() > 0) {
                result = true;
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi thêm nhà xuất bản: " + e.getMessage());
        } finally {
            jdbc.closeConnection();
        }
        return result;
    }

    @Override
    public boolean delete(String id) {
        boolean result = false;
        try {
            jdbc.openConnection();
            String query = "UPDATE TheLoai SET trangThai = 0 WHERE maLoai = ?";
            PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
            ps.setString(1, id);
            if (ps.executeUpdate() > 0) {
                result = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jdbc.closeConnection();
        }
        return result;
    }

    @Override
    public boolean update(TheLoaiDTO theLoai) {
        boolean result = false;
        try {
            jdbc.openConnection();
            String query = "UPDATE TheLoai SET tenLoai = ? WHERE maLoai = ?";
            PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
            ps.setString(1, theLoai.getTenLoai());
            ps.setString(2, theLoai.getMaLoai());

            if (ps.executeUpdate() > 0) {
                result = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jdbc.closeConnection();
        }
        return result;
    }

    @Override
    public TheLoaiDTO getByID(String id) {
        TheLoaiDTO theLoai = null;
        try {
            jdbc.openConnection();
            String query = "SELECT * FROM TheLoai WHERE maLoai = ?";
            PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                theLoai = new TheLoaiDTO(
                        rs.getString("maLoai"),
                        rs.getString("tenLoai"),
                        rs.getInt("trangThai")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jdbc.closeConnection();
        }
        return theLoai;
    }

    @Override
    public ArrayList<TheLoaiDTO> search(String searchContent) {
        ArrayList<TheLoaiDTO> list = new ArrayList<>();
        try {
            jdbc.openConnection();
            String query = "SELECT * FROM TheLoai WHERE (maLoai LIKE ? OR tenLoai LIKE ?) AND trangThai = 1";
            PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
            ps.setString(1, "%" + searchContent + "%");
            ps.setString(2, "%" + searchContent + "%");

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new TheLoaiDTO(
                        rs.getString("maLoai"),
                        rs.getString("tenLoai"),
                        rs.getInt("trangThai")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jdbc.closeConnection();
        }
        return list;
    }
    
    public TheLoaiDTO getByName(String tenLoai) {
        String query = "SELECT * FROM TheLoai WHERE tenLoai LIKE ?";
        TheLoaiDTO theLoai = null;

        try {
            jdbc.openConnection();
            PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
            ps.setString(1, "%" + tenLoai + "%");
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                theLoai = new TheLoaiDTO();
                theLoai.setMaLoai(rs.getString("maLoai"));
                theLoai.setTenLoai(rs.getString("tenLoai"));
                theLoai.setTrangThai(rs.getInt("trangThai"));
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi tìm thể loại theo tên: " + e.getMessage());
        } finally {
            jdbc.closeConnection();
        }

        return theLoai;
    }
    
    public String nextMaLoai() {
        String query = "SELECT MAX(maLoai) FROM TheLoai";
        String newId = "TL01"; // Mặc định nếu bảng trống

        try {
            jdbc.openConnection();
            PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            if (rs.next() && rs.getString(1) != null) {
                String maxId = rs.getString(1); // VD: TL07
                int num = Integer.parseInt(maxId.substring(2)) + 1;
                newId = String.format("TL%02d", num); // VD: TL08
            }

        } catch (SQLException e) {
            System.err.println("Lỗi khi sinh mã thể loại mới: " + e.getMessage());
        } finally {
            jdbc.closeConnection();
        }

        return newId;
    }


}