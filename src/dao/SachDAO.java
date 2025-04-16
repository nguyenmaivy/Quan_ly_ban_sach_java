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
import java.util.List;
import static junit.runner.Version.id;

/**
 *
 * @author MZI
 */

public class SachDAO implements DAOInterface<SachDTO> {
    Constant jdbc = new Constant();

    @Override
    public ArrayList<SachDTO> getALL() {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
         ArrayList<SachDTO> list = new ArrayList<>();
        try {
            jdbc.openConnection();
            String query = "SELECT * FROM Sach WHERE trangThai = 1";
            PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                list.add(new SachDTO(
                        rs.getString("id"),
                        rs.getString("tenSach"),
                        rs.getString("theLoai"),
                        rs.getString("tacGia"),
                        rs.getString("nhaXuatBan"),
                        rs.getInt("giaBan"),
                        rs.getInt("soLuong"),
                        rs.getInt("trangThai"),
                        rs.getString("maKho"),
                        rs.getString("hinhAnh")
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
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        boolean result = false;
        try {
            jdbc.openConnection();
            String query = "SELECT * FROM Sach WHERE id = ?";
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
    public boolean add(SachDTO sach) {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        boolean result = false;
        try {
            jdbc.openConnection();
            String query = "INSERT INTO Sach (id, tenSach, theLoai, tacGia, nhaXuatBan, giaBan, soLuong, trangThai, maKho) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
            ps.setString(1, sach.getId());
            ps.setString(2, sach.getTenSach());
            ps.setString(3, sach.getTheLoai());
            ps.setString(4, sach.getTacGia());
            ps.setString(5, sach.getNhaXuatBan());
            ps.setInt(6, sach.getGiaBan());
            ps.setInt(7, sach.getSoLuong());
            ps.setInt(8, sach.getTrangThai());
            ps.setString(9, sach.getMaKho());
            
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
    public boolean delete(String id) {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        boolean result = false;
        try {
            jdbc.openConnection();
            String query = "UPDATE Sach SET trangThai = 0 WHERE id = ?";
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
    public boolean update(SachDTO sach) {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        boolean result = false;
        try {
            jdbc.openConnection();
            String query = "UPDATE Sach SET tenSach = ?, theLoai = ?, tacGia = ?, nhaXuatBan = ?, giaBan = ?, soLuong = ?, maKho = ? WHERE id = ?";
            PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
            ps.setString(1, sach.getTenSach());
            ps.setString(2, sach.getTheLoai());
            ps.setString(3, sach.getTacGia());
            ps.setString(4, sach.getNhaXuatBan());
            ps.setInt(5, sach.getGiaBan());
            ps.setInt(6, sach.getSoLuong());
            ps.setString(7, sach.getMaKho());
            ps.setString(8, sach.getId());
            
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
    public SachDTO getByID(String id) {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        SachDTO sach = null;
        try {
            jdbc.openConnection();
            String query = "SELECT * FROM Sach WHERE id = ?";
            PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                sach = new SachDTO(
                        rs.getString("id"),
                        rs.getString("tenSach"),
                        rs.getString("theLoai"),
                        rs.getString("tacGia"),
                        rs.getString("nhaXuatBan"),
                        rs.getInt("giaBan"),
                        rs.getInt("soLuong"),
                        rs.getInt("trangThai"),
                        rs.getString("maKho"),
                        rs.getString("hinhAnh")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jdbc.closeConnection();
        }
        return sach;
    }

    @Override
    public ArrayList<SachDTO> search(String searchContent) {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        ArrayList<SachDTO> list = new ArrayList<>();
        try {
            jdbc.openConnection();
            String query = "SELECT * FROM Sach WHERE (id LIKE ? OR tenSach LIKE ? OR theLoai LIKE ? OR tacGia LIKE ?) AND trangThai = 1";
            PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
            ps.setString(1, "%" + searchContent + "%");
            ps.setString(2, "%" + searchContent + "%");
            ps.setString(3, "%" + searchContent + "%");
            ps.setString(4, "%" + searchContent + "%");
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new SachDTO(
                        rs.getString("id"),
                        rs.getString("tenSach"),
                        rs.getString("theLoai"),
                        rs.getString("tacGia"),
                        rs.getString("nhaXuatBan"),
                        rs.getInt("giaBan"),
                        rs.getInt("soLuong"),
                        rs.getInt("trangThai"),
                        rs.getString("maKho"),
                        rs.getString("hinhAnh")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jdbc.closeConnection();
        }
        return list;
    }
}
