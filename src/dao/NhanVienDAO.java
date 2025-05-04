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
import java.sql.SQLException;
import java.sql.Date;
import java.time.LocalDate;
import javax.swing.JOptionPane;

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
            System.err.println("Lỗi khi thêm nhân viên: " + e.getMessage());
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

    public NhanVienDTO getByName(String tenNV) {
        String query = "SELECT * FROM NhanVien WHERE tenNV LIKE ?";
        NhanVienDTO nv = null;

        try {
            jdbc.openConnection();
            PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
            ps.setString(1, "%" + tenNV + "%");
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                nv = new NhanVienDTO();
                nv.setMaNV(rs.getString("maNV"));
                nv.setTenNV(rs.getString("tenNV"));
                nv.setGioiTinh(rs.getInt("gioiTinh"));
                nv.setDiaChi(rs.getString("diaChi"));
                nv.setNgayVao(rs.getDate("ngayVao").toLocalDate());
                nv.setSdt(rs.getString("sdt"));
                nv.setNgaySinh(rs.getDate("ngaySinh").toLocalDate());
                nv.setTrangThai(rs.getInt("trangThai"));
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi tìm kiếm nhân viên theo tên: " + e.getMessage());
            e.printStackTrace();
        } finally {
            jdbc.closeConnection();
        }

        return nv;
    }

    public String NextMaNXB() {
        String getMaxIdQuery = "SELECT MAX(maNV) FROM NhanVien";
        String newId = "NV01"; // Mã mặc định nếu bảng trống

        try {
            jdbc.openConnection();
            PreparedStatement ps = jdbc.getConnection().prepareStatement(getMaxIdQuery);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String maxId = rs.getString(1); // VD: NV07
                if (maxId != null) {
                    int num = Integer.parseInt(maxId.substring(2)) + 1;
                    newId = String.format("NV%02d", num); // VD: NV08
                }
            }

        } catch (SQLException e) {
            System.err.println("Lỗi khi sinh mã mới: " + e.getMessage());
        } finally {
            jdbc.closeConnection();
        }

        return newId;
    }
    
     public String layTenTheoMa(String maNV) {
        String sql = "SELECT TenNV FROM NHANVIEN WHERE MaNV = ?";
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            jdbc.openConnection(); // Mở kết nối

            con = jdbc.getConnection();
            if (con != null) {
                pstmt = con.prepareStatement(sql);
                pstmt.setString(1, maNV);
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    return rs.getString("TenNV");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Không thể lấy kết nối đến CSDL!");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi lấy tên nhân viên: " + e.getMessage());
        } finally {
            jdbc.closeConnection(); // Đóng kết nối
        }
        return "Không rõ";
    }

    @Override
    public ArrayList<NhanVienDTO> search(String searchContent) {
        ArrayList<NhanVienDTO> arr = new ArrayList<>();

        try {
            jdbc.openConnection();

            String query = "SELECT * FROM NhanVien WHERE "
                    + "maNV LIKE ? OR "
                    + "tenNV LIKE ? OR "
                    + "diaChi LIKE ? OR "
                    + "sdt LIKE ?";

            PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
            for (int i = 1; i <= 4; i++) {
                ps.setString(i, "%" + searchContent + "%");
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                NhanVienDTO nv = new NhanVienDTO();
                nv.setMaNV(rs.getString("maNV"));
                nv.setTenNV(rs.getString("tenNV"));
                nv.setGioiTinh(rs.getInt("gioiTinh"));
                nv.setDiaChi(rs.getString("diaChi"));
                nv.setNgayVao(rs.getDate("ngayVao").toLocalDate());
                nv.setSdt(rs.getString("sdt"));
                nv.setNgaySinh(rs.getDate("ngaySinh").toLocalDate());
                nv.setTrangThai(rs.getInt("trangThai"));
                arr.add(nv);
            }

        } catch (Exception e) {
            System.err.println("Lỗi khi tìm kiếm nhân viên: " + e.getMessage());
            e.printStackTrace();
        } finally {
            jdbc.closeConnection();
        }

        return arr;
    }

    public boolean isPhoneNumberExists(String phoneNumber, String currentMaNV) {
        String sql;
        boolean exists = false;

        try {
            jdbc.openConnection();

            if (currentMaNV == null || currentMaNV.isEmpty()) {
                // Kiểm tra khi thêm mới
                sql = "SELECT COUNT(*) FROM NhanVien WHERE sdt = ?";
            } else {
                // Kiểm tra khi sửa, loại trừ chính nó
                sql = "SELECT COUNT(*) FROM NhanVien WHERE sdt = ? AND maNV != ?";
            }

            PreparedStatement stmt = jdbc.getConnection().prepareStatement(sql);
            stmt.setString(1, phoneNumber);

            if (currentMaNV != null && !currentMaNV.isEmpty()) {
                stmt.setString(2, currentMaNV);
            }

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                exists = rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            System.err.println("Lỗi khi kiểm tra số điện thoại nhân viên: " + e.getMessage());
        } finally {
            jdbc.closeConnection();
        }

        return exists;
    }
}
