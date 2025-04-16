
package dao;

import Config.Constant;
import dto.TaiKhoanDTO;
import java.sql.*;
import java.util.ArrayList;

public class TaiKhoanDAO implements DAOInterface<TaiKhoanDTO> {
    Constant conn = new Constant();

    @Override
    public ArrayList<TaiKhoanDTO> getALL() {
        ArrayList<TaiKhoanDTO> arr = new ArrayList<>();
        try {
            conn.openConnection();
            String query = "SELECT * FROM TaiKhoan WHERE trangThai = 1";
            PreparedStatement ps = conn.getConnection().prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                TaiKhoanDTO tk = new TaiKhoanDTO();
                tk.setUseName(rs.getString("useName"));
                tk.setMatKhau(rs.getString("matKhau"));
                tk.setSdt(rs.getString("sdt"));
                tk.setMaNV(rs.getString("maNV"));               
                tk.setMaNhomQuyen(rs.getInt("maNhomQuyen"));
                tk.setTrangThai(rs.getInt("trangThai"));
                arr.add(tk);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.closeConnection();
        }
        return arr;
    }

    @Override
    public boolean has(String sdt) {
        boolean result = false;
        try {
            conn.openConnection();
            String query = "SELECT * FROM TaiKhoan WHERE sdt = ?";
            PreparedStatement ps = conn.getConnection().prepareStatement(query);
            ps.setString(1, sdt);
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
    public boolean add(TaiKhoanDTO tk) {
        boolean result = false;
        try {
            conn.openConnection();
            String query = "INSERT INTO TaiKhoan VALUES (?,?,?,?,?,?)";  
            PreparedStatement ps = conn.getConnection().prepareStatement(query);
            
            ps.setString(1, tk.getUseName());
            ps.setString(2, tk.getSdt());            
            ps.setString(3, tk.getMatKhau());   // sắp xếp theo thứ tự csdl            
            ps.setString(4, tk.getMaNV());          
            ps.setInt(5, tk.getMaNhomQuyen());
            ps.setInt(6, 1);
            //ps.setInt(6, tk.getTrangThai());
            if (ps.executeUpdate() > 0) {
                result = true;
            }
       } catch (SQLException e) {
        System.err.println("Lỗi khi thêm tài khoản: " + e.getMessage());
    } finally {
        conn.closeConnection();
    }
        return result;
    }

    @Override
    public boolean delete(String sdt) {
        boolean result = false;
        try {
            conn.openConnection();
            String query = "UPDATE TaiKhoan SET trangThai = 0 WHERE sdt = ?";
            PreparedStatement ps = conn.getConnection().prepareStatement(query);
            ps.setString(1, sdt);
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
    public boolean update(TaiKhoanDTO tk) {
        boolean result = false;
        try {
            conn.openConnection();
            String query = "UPDATE TaiKhoan SET useName = ?, matKhau = ?, maNV = ?,  maNhomQuyen = ? WHERE sdt = ?";
            PreparedStatement ps = conn.getConnection().prepareStatement(query);
            ps.setString(1, tk.getUseName());
            ps.setString(2, tk.getMatKhau());            
            ps.setString(3, tk.getMaNV());                  
            ps.setInt(4, tk.getMaNhomQuyen());
            ps.setString(5, tk.getSdt());
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
    public TaiKhoanDTO getByID(String sdt) {
        TaiKhoanDTO tk = null;
        try {
            conn.openConnection();
            String query = "SELECT * FROM TaiKhoan WHERE sdt = ?";
            PreparedStatement ps = conn.getConnection().prepareStatement(query);
            ps.setString(1, sdt);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                tk = new TaiKhoanDTO();
                tk.setSdt(rs.getString("sdt"));
                tk.setUseName(rs.getString("useName"));
                tk.setMatKhau(rs.getString("matKhau"));                
                tk.setMaNV(rs.getString("maNV"));               
                tk.setMaNhomQuyen(rs.getInt("maNhomQuyen"));
                tk.setTrangThai(rs.getInt("trangThai"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.closeConnection();
        }
        return tk;
    }

    @Override
    public ArrayList<TaiKhoanDTO> search(String searchContent) {
        ArrayList<TaiKhoanDTO> arr = new ArrayList<>();
        try {
            conn.openConnection();
            String query = "SELECT * FROM TaiKhoan WHERE "
                         + "useName LIKE ? OR "
                         + "matKhau LIKE ? OR "
                         + "sdt LIKE ? OR "
                         + "maNV LIKE ? OR "                        
                         + "maNhomQuyen = ? ";
            // Kiểm tra nếu searchContent là số, mới thêm điều kiện cho maNhomQuyen
        boolean isNumber = searchContent.matches("\\d+");
        if (isNumber) {
            query += " OR maNhomQuyen = ? ";
        }
            PreparedStatement ps = conn.getConnection().prepareStatement(query);
            ps.setString(1, "%" + searchContent + "%");
            ps.setString(2, "%" + searchContent + "%");
            ps.setString(3, "%" + searchContent + "%");
            ps.setString(4, "%" + searchContent + "%");            
            ps.setString(5, "%" + Integer.parseInt(searchContent) + "%");
            if (isNumber) {
        ps.setInt(6, Integer.parseInt(searchContent)); // Chỉ set giá trị nếu là số
    }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                TaiKhoanDTO tk = new TaiKhoanDTO();
                tk.setUseName(rs.getString("useName"));
                tk.setMatKhau(rs.getString("matKhau"));
                tk.setSdt(rs.getString("sdt"));
                tk.setMaNV(rs.getString("maNV"));               
                tk.setMaNhomQuyen(rs.getInt("maNhomQuyen"));
                arr.add(tk);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.closeConnection();
        }
        return arr;
    }
    
}
