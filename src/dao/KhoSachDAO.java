/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import Config.Constant;
import dto.KhoSachDTO;
import java.util.ArrayList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;

/**
 *
 * @author leduc
 */
public class KhoSachDAO implements DAOInterface<KhoSachDTO> {
    Constant jdbc = new Constant();
    
    @Override
    public ArrayList<KhoSachDTO> getALL() {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        ArrayList<KhoSachDTO> arr = new ArrayList<>();
        try {
            jdbc.openConnection();
            String query = "SELECT * FROM KhoSach WHERE trangThai = 1";
            PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                arr.add(new KhoSachDTO(
                        rs.getString("maKho"),
                        rs.getString("tenKho"),
                        rs.getString("diaChi"),
                        rs.getString("loai"),
                        rs.getInt("trangThai")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jdbc.closeConnection();
        }
        return arr;
    }

    @Override
    public boolean has(String maKho) {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        boolean result = false;
        try {
            jdbc.openConnection();
            String query = "SELECT * FROM KhoSach WHERE maKho = ?";
            PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
            ps.setString(1, maKho);
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
    public boolean add(KhoSachDTO kho) {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        boolean result = false;
        try {
            jdbc.openConnection();
            String query = "INSERT INTO KhoSach (maKho, tenKho, diaChi, loai, trangThai) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
            ps.setString(1, kho.getMaKho());
            ps.setString(2, kho.getTenKho());
            ps.setString(3, kho.getDiaChi());
            ps.setString(4, kho.getLoai());
            ps.setInt(5, kho.getTrangThai());

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
    public boolean delete(String maKho) {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        boolean result = false;
        try {
            jdbc.openConnection();
            String query = "UPDATE KhoSach SET trangThai = 0 WHERE maKho = ?";
            PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
            ps.setString(1, maKho);
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
    public boolean update(KhoSachDTO kho) {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        boolean result = false;
        try {
            jdbc.openConnection();
            String query = "UPDATE KhoSach SET tenKho = ?, diaChi = ?, loai = ? WHERE maKho = ?";
            PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
            ps.setString(1, kho.getTenKho());
            ps.setString(2, kho.getDiaChi());
            ps.setString(3, kho.getLoai());
            ps.setString(4, kho.getMaKho());

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
    public KhoSachDTO getByID(String maKho) {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        KhoSachDTO khoSach = null;
        try {
            jdbc.openConnection();
            String query = "SELECT * FROM KhoSach WHERE maKho = ?";
            PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
            ps.setString(1, maKho);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                khoSach = new KhoSachDTO(
                        rs.getString("maKho"),
                        rs.getString("tenKho"),
                        rs.getString("diaChi"),
                        rs.getString("loai"),
                        rs.getInt("trangThai")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jdbc.closeConnection();
        }
        return khoSach;
    }

    @Override
    public ArrayList<KhoSachDTO> search(String searchContent) {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        ArrayList<KhoSachDTO> arr = new ArrayList<>();
        try {
            jdbc.openConnection();
            String query = "SELECT * FROM KhoSach WHERE (maKho LIKE ? OR tenKho LIKE ? OR diaChi LIKE ? OR loai LIKE ?) AND trangThai = 1";
            PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
            ps.setString(1, "%" + searchContent + "%");
            ps.setString(2, "%" + searchContent + "%");
            ps.setString(3, "%" + searchContent + "%");
            ps.setString(4, "%" + searchContent + "%");

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                arr.add(new KhoSachDTO(
                        rs.getString("maKho"),
                        rs.getString("tenKho"),
                        rs.getString("diaChi"),
                        rs.getString("loai"),
                        rs.getInt("trangThai")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jdbc.closeConnection();
        }
        return arr;
    }
    
}
