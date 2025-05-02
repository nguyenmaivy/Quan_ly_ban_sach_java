package dao;

import Config.Constant;
import dto.KhachHangDTO;

import java.sql.*;
import java.util.ArrayList;

public class KhachHangDAO {
    private final Constant db = new Constant();

    public ArrayList<KhachHangDTO> getAllKhachHang() {
        ArrayList<KhachHangDTO> list = new ArrayList<>();
        if (db.openConnection()) {
            try {
                String sql = "SELECT * FROM KhachHang";
                Statement stmt = db.getConnection().createStatement();
                ResultSet rs = stmt.executeQuery(sql);

                while (rs.next()) {
                    KhachHangDTO kh = new KhachHangDTO();
                    kh.setMaKH(rs.getString("MaKH"));
                    kh.setTenKh(rs.getString("TenKH"));
                    kh.setSdt(rs.getString("Sdt"));
                    kh.setDiaChi(rs.getString("DiaChi"));
                    kh.setTrangThai(rs.getInt("TrangThai"));  // nếu có cột này
                    list.add(kh);
                }

                rs.close();
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                db.closeConnection();
            }
        }
        return list;
    }

    public boolean addKhachHang(KhachHangDTO kh) {
        boolean result = false;
        if (db.openConnection()) {
            try {
                String sql = "INSERT INTO KhachHang (MaKH, TenKH, Sdt, DiaChi, TrangThai) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement stmt = db.getConnection().prepareStatement(sql);
                stmt.setString(1, kh.getMaKH());
                stmt.setString(2, kh.getTenKH());
                stmt.setString(3, kh.getSdt());
                stmt.setString(4, kh.getDiaChi());
                stmt.setInt(5, kh.getTrangThai());

                result = stmt.executeUpdate() > 0;
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                db.closeConnection();
            }
        }
        return result;
    }

    public boolean updateKhachHang(KhachHangDTO kh) {
        boolean result = false;
        if (db.openConnection()) {
            try {
                String sql = "UPDATE KhachHang SET TenKH=?, Sdt=?, DiaChi=?, TrangThai=? WHERE MaKH=?";
                PreparedStatement stmt = db.getConnection().prepareStatement(sql);
                stmt.setString(1, kh.getTenKH());
                stmt.setString(2, kh.getSdt());
                stmt.setString(3, kh.getDiaChi());
                stmt.setInt(4, kh.getTrangThai());
                stmt.setString(5, kh.getMaKH());

                result = stmt.executeUpdate() > 0;
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                db.closeConnection();
            }
        }
        return result;
    }

    public boolean deleteKhachHang(String maKH) {
        boolean result = false;
        if (db.openConnection()) {
            try {
                String sql = "DELETE FROM KhachHang WHERE MaKH = ?";
                PreparedStatement stmt = db.getConnection().prepareStatement(sql);
                stmt.setString(1, maKH);

                result = stmt.executeUpdate() > 0;
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                db.closeConnection();
            }
        }
        return result;
    }

    public KhachHangDTO getByMaKH(String maKH) {
        KhachHangDTO kh = null;
        if (db.openConnection()) {
            try {
                String sql = "SELECT * FROM KhachHang WHERE MaKH = ?";
                PreparedStatement stmt = db.getConnection().prepareStatement(sql);
                stmt.setString(1, maKH);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    kh = new KhachHangDTO();
                    kh.setMaKH(rs.getString("MaKH"));
                    kh.setTenKh(rs.getString("TenKH"));
                    kh.setSdt(rs.getString("Sdt"));
                    kh.setDiaChi(rs.getString("DiaChi"));
                    kh.setTrangThai(rs.getInt("TrangThai"));
                }

                rs.close();
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                db.closeConnection();
            }
        }
        return kh;
    }
}
