/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import Config.Constant;
import dto.KhoSachDTO;
import java.beans.Statement;
import java.util.ArrayList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;

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
        } catch (SQLException e) {
            System.err.println("Lỗi khi thêm nhà xuất bản: " + e.getMessage());
        } finally {
            jdbc.closeConnection();
        }
        return result;
    }

    @Override
    public boolean delete(String maKho) {
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
    
    public KhoSachDTO getByName(String tenKho) {
        String query = "SELECT * FROM KhoSach WHERE tenKho LIKE ?";
        KhoSachDTO kho = null;

        try {
            jdbc.openConnection();
            PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
            ps.setString(1, "%" + tenKho + "%");
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                kho = new KhoSachDTO();
                kho.setMaKho(rs.getString("maKho"));
                kho.setTenKho(rs.getString("tenKho"));
                kho.setDiaChi(rs.getString("diaChi"));
                kho.setLoai(rs.getString("loai"));
                kho.setTrangThai(rs.getInt("trangThai"));
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi tìm kho theo tên: " + e.getMessage());
        } finally {
            jdbc.closeConnection();
        }

        return kho;
    }
    
    public String getLastMaKho() {
        String lastMaKho = null;
        String sql = "SELECT TOP 1 maKho FROM KhoSach ORDER BY maKho DESC";

        try (Connection connection = new Constant().getConnection();
             java.sql.Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                lastMaKho = rs.getString("maKho");
            }

        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy mã kho cuối cùng: " + e.getMessage());
        }

        return lastMaKho;
    }
    
    public String nextMaKho() {
        String query = "SELECT maKho FROM KhoSach";
        String newId = "K001";
        int maxNum = 0;

        try {
            jdbc.openConnection();
            PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String ma = rs.getString("maKho");
                if (ma != null && ma.matches("^K\\d{3}$")) {  // Chỉ xử lý mã đúng định dạng "Kxxx"
                    int num = Integer.parseInt(ma.substring(1)); // Bỏ ký tự 'K'
                    if (num > maxNum) {
                        maxNum = num;
                    }
                }
            }

            newId = String.format("K%03d", maxNum + 1); // Format lại mã mới

        } catch (SQLException e) {
            System.err.println("Lỗi khi sinh mã kho mới: " + e.getMessage());
        } finally {
            jdbc.closeConnection();
        }

        return newId;
    }


    

}
