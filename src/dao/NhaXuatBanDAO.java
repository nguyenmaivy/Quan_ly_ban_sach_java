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
                nxb.setTenNXB(rs.getNString(2));
                nxb.setDiachiNXB(rs.getNString(3));
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
        boolean result = false;

        try {

            //B1
            conn.openConnection();

            //B2
            String query = "INSERT INTO NhaXuatBan VALUES (?,?,?,?,?)";

            //B3
            PreparedStatement ps = conn.getConnection().prepareStatement(query);
            ps.setString(1, nxb.getMaNXB());
            ps.setString(2, nxb.getTenNXB());
            ps.setString(3, nxb.getDiachiNXB());
            ps.setString(4, nxb.getSdt());
            ps.setInt(5, 1);

            //B4
            if (ps.executeUpdate() > 0) {
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
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
        boolean result = false;

        try {

            //B1
            conn.openConnection();

            //B2
            String query = "UPDATE NhaXuatBan SET tenNXB = ?, diaChiNXB = ?, sdt = ? WHERE maNXB = ?";

            //B3
            PreparedStatement ps = conn.getConnection().prepareStatement(query);
            ps.setString(1, nxb.getTenNXB());
            ps.setString(2, nxb.getDiachiNXB());
            ps.setString(3, nxb.getSdt());
            ps.setString(4, nxb.getMaNXB());

            //B4
            if (ps.executeUpdate() > 0) {
                return true;
            }

        } catch (Exception e) {
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

                nxb = new NhaXuatBanDTO(manxb, diaChiNXB, sdt, tenNXB, 1);
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

    public NhaXuatBanDTO getByName(String tennxb) {
        NhaXuatBanDTO nxb = null;

        try {

            //B1
            conn.openConnection();

            //B2
            String query = "select maNXB,tenNXB,diaChiNXB,sdt from NhaXuatBan where tenNXB like ?";

            //B3
            PreparedStatement ps = conn.getConnection().prepareStatement(query);
            ps.setString(1, "%" + tennxb + "%");

            //B4
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String manxb = rs.getString(1);
                String tenxb = rs.getString(2);
                String diachi = rs.getString(3);
                String sdt = rs.getString(4);
                nxb = new NhaXuatBanDTO(manxb, diachi, sdt, tennxb, 1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.closeConnection();
        }
        return nxb;
    }
    // Phương thức lấy mã nhà xuất bản lớn nhất

    public String getLastMaNXB() {
        String lastMaNXB = null;
        String sql = "SELECT TOP 1 maNXB FROM NhaXuatBan ORDER BY maNXB DESC";  // Lấy mã nhà xuất bản lớn nhất

        try ( Connection conn = new Constant().getConnection(); // Kết nối với cơ sở dữ liệu
                  Statement stmt = conn.createStatement();  ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                lastMaNXB = rs.getString("maNXB");  // Lấy mã nhà xuất bản
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lastMaNXB;  // Trả về mã nhà xuất bản lớn nhất
    }

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
