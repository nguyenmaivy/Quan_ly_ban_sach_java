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
//    // Lấy danh sách Nhà Xuất Bản
//    public List<NhaXuatBanDTO> getAllNXB() {
//        List<NhaXuatBanDTO> dsNXB = new ArrayList<>();
//        String query = "SELECT * FROM NhaXuatBan";
//        try (Statement stmt = conn.createStatement();
//             ResultSet rs = stmt.executeQuery(query)) {
//            while (rs.next()) {
//                dsNXB.add(new NhaXuatBanDTO(
//                        rs.getString("maNXB"),
//                        rs.getString("diachiNXB"),
//                        rs.getString("sdt"),
//                        rs.getString("tenNXB")));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return dsNXB;
//    }
//
//    // Kiểm tra xem Nhà Xuất Bản có tồn tại hay không
//    public NhaXuatBanDTO getNXBById(String maNXB) {
//        String query = "SELECT * FROM NhaXuatBan WHERE maNXB = ?";
//        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
//            pstmt.setString(1, maNXB);
//            ResultSet rs = pstmt.executeQuery();
//            if (rs.next()) {
//                return new NhaXuatBanDTO(
//                        rs.getString("maNXB"),
//                        rs.getString("diachiNXB"),
//                        rs.getString("sdt"),
//                        rs.getString("tenNXB"));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    // Kiểm tra xem Nhà Xuất Bản có sách liên quan không
//    public boolean hasRelatedBooks(String maNXB) {
//        String query = "SELECT COUNT(*) FROM Sach WHERE maNXB = ?";
//        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
//            pstmt.setString(1, maNXB);
//            ResultSet rs = pstmt.executeQuery();
//            if (rs.next() && rs.getInt(1) > 0) {
//                return true; // Có sách liên quan
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return false; // Không có sách liên quan
//    }
//
//    // Thêm Nhà Xuất Bản
//    public boolean themNXB(NhaXuatBanDTO nxb) {
//        String query = "INSERT INTO NhaXuatBan (maNXB, diachiNXB, sdt, tenNXB) VALUES (?, ?, ?, ?)";
//        try (PreparedStatement p = conn.prepareStatement(query)) {
//            p.setString(1, nxb.getManxb());
//            p.setString(2, nxb.getDiachiNXB());
//            p.setString(3, nxb.getSdt());
//            p.setString(4, nxb.getTenNXB());
//            return p.executeUpdate() > 0;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    // Cập nhật Nhà Xuất Bản
//    public boolean updateNXB(NhaXuatBanDTO nxb) {
//        String query = "UPDATE NhaXuatBan SET diachiNXB=?, sdt=?, tenNXB=? WHERE maNXB=?";
//        try (PreparedStatement p = conn.prepareStatement(query)) {
//            p.setString(1, nxb.getDiachiNXB());
//            p.setString(2, nxb.getSdt());
//            p.setString(3, nxb.getTenNXB());
//            p.setString(4, nxb.getManxb());
//            return p.executeUpdate() > 0;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    // Xóa Nhà Xuất Bản
//    public boolean deleteNXB(String maNXB) {
//        String query = "DELETE FROM NhaXuatBan WHERE maNXB=?";
//        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
//            pstmt.setString(1, maNXB);
//            return pstmt.executeUpdate() > 0;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }

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
                nxb.setMaNXB(rs.getNString(1));
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
}
