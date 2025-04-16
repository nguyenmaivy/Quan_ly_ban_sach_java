/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import Config.Constant;
import dto.NhanVienDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
//import java.util.Date;
import java.sql.Date;
import java.time.LocalDate;

/**
 *
 * @author leduc
 */
public class NhanVienDAO implements DAOInterface<NhanVienDTO> {
    Constant jdbc = new Constant();
    
    @Override
    public ArrayList<NhanVienDTO> getALL() {
        ArrayList<NhanVienDTO> list = new ArrayList<>();
        try {
            jdbc.openConnection();
            String query = "SELECT * FROM NhanVien WHERE trangThai = 1";
            PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                list.add(new NhanVienDTO(
                        rs.getString("maNV"),
                        rs.getString("tenNV"),
                        rs.getInt("gioiTinh"),
                        rs.getString("diaChi"),
                        rs.getDate("ngayVao").toLocalDate(),
                        rs.getString("sdt"),
                        rs.getDate("ngaySinh").toLocalDate(),
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
            String query = "SELECT * FROM NhanVien WHERE maNV = ?";
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
    public boolean add(NhanVienDTO nv) {
        boolean result = false;
        try {
            jdbc.openConnection();
            String query = "INSERT INTO NhanVien (maNV, tenNV, gioiTinh, diaChi, ngayVao, sdt, ngaySinh, trangThai) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
            ps.setString(1, nv.getMaNV());
            ps.setString(2, nv.getTenNV());
            ps.setInt(3, nv.getGioiTinh());
            ps.setString(4, nv.getDiaChi());
            ps.setDate(5, Date.valueOf(nv.getNgayVao()));
            ps.setString(6, nv.getSdt());
            ps.setDate(7, Date.valueOf(nv.getNgaySinh()));
            ps.setInt(8, nv.getTrangThai());
            
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
        boolean result = false;
        try {
            jdbc.openConnection();
            String query = "UPDATE NhanVien SET trangThai = 0 WHERE maNV = ?";
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
    public boolean update(NhanVienDTO nv) {
        boolean result = false;
        try {
            jdbc.openConnection();
            String query = "UPDATE NhanVien SET tenNV = ?, gioiTinh = ?, diaChi = ?, ngayVao = ?, sdt = ?, ngaySinh = ? WHERE maNV = ?";
            PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
            ps.setString(1, nv.getTenNV());
            ps.setInt(2, nv.getGioiTinh());
            ps.setString(3, nv.getDiaChi());
            ps.setDate(4, Date.valueOf(nv.getNgayVao()));
            ps.setString(5, nv.getSdt());
            ps.setDate(6, Date.valueOf(nv.getNgaySinh()));
            ps.setString(7, nv.getMaNV());
            
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
    public NhanVienDTO getByID(String id) {
        NhanVienDTO nv = null;
        try {
            jdbc.openConnection();
            String query = "SELECT * FROM NhanVien WHERE maNV = ?";
            PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                nv = new NhanVienDTO(
                        rs.getString("maNV"),
                        rs.getString("tenNV"),
                        rs.getInt("gioiTinh"),
                        rs.getString("diaChi"),
                        rs.getDate("ngayVao").toLocalDate(),
                        rs.getString("sdt"),
                        rs.getDate("ngaySinh").toLocalDate(),
                        rs.getInt("trangThai")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jdbc.closeConnection();
        }
        return nv;
    }

    @Override
    public ArrayList<NhanVienDTO> search(String d) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}