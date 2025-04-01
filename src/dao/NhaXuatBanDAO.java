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
<<<<<<< HEAD
                nxb.setMaNXB(rs.getNString(1));
=======
                nxb.setMaNXB(rs.getString(1));
>>>>>>> a3477ed (giao dien nha xuat ban, tai khoan)
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

<<<<<<< HEAD
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
=======
        
    @Override
    public boolean add(NhaXuatBanDTO nxb) {
        String query = "INSERT INTO NhaXuatBan (tenNXB, diaChiNXB, sdt, trangThai) VALUES ( ?, ?, ?, ?)";
        boolean result = false;

        try {
            conn.openConnection();
            PreparedStatement ps = conn.getConnection().prepareStatement(query);
            ps.setString(1, nxb.getTenNXB());
            ps.setString(2, nxb.getDiachiNXB());
            ps.setString(3, nxb.getSdt());
            ps.setInt(4, 1); // 1: Đang hoạt động

            result = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi thêm NXB: " + e.getMessage());
>>>>>>> a3477ed (giao dien nha xuat ban, tai khoan)
        } finally {
            conn.closeConnection();
        }
        return result;
    }

<<<<<<< HEAD
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
=======

    @Override
    public boolean delete(String maNXB) {
        String query = "UPDATE NhaXuatBan SET trangThai = 0 WHERE maNXB = ?";
        boolean result = false;

        try {
            conn.openConnection();
            PreparedStatement ps = conn.getConnection().prepareStatement(query);
            ps.setString(1, maNXB);

            result = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi xóa NXB: " + e.getMessage());
>>>>>>> a3477ed (giao dien nha xuat ban, tai khoan)
        } finally {
            conn.closeConnection();
        }
        return result;
<<<<<<< HEAD

=======
>>>>>>> a3477ed (giao dien nha xuat ban, tai khoan)
    }

    @Override
    public boolean update(NhaXuatBanDTO nxb) {
<<<<<<< HEAD
        boolean result = false;

        try {

            //B1
            conn.openConnection();

            //B2
            String query = "UPDATE NhaXuatBan SET tenNXB = ?, diaChiNXB = ?, sdt = ? WHERE maNXB = ?";

            //B3
=======
        String query = "UPDATE NhaXuatBan SET tenNXB = ?, diaChiNXB = ?, sdt = ? WHERE maNXB = ?";
        boolean result = false;

        try {
            conn.openConnection();
>>>>>>> a3477ed (giao dien nha xuat ban, tai khoan)
            PreparedStatement ps = conn.getConnection().prepareStatement(query);
            ps.setString(1, nxb.getTenNXB());
            ps.setString(2, nxb.getDiachiNXB());
            ps.setString(3, nxb.getSdt());
            ps.setString(4, nxb.getMaNXB());

<<<<<<< HEAD
            //B4
            if (ps.executeUpdate() > 0) {
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
=======
            result = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi cập nhật NXB: " + e.getMessage());
>>>>>>> a3477ed (giao dien nha xuat ban, tai khoan)
        } finally {
            conn.closeConnection();
        }
        return result;
    }

<<<<<<< HEAD
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
=======
     @Override
    public NhaXuatBanDTO getByID(String maNXB) {
        String query = "SELECT * FROM NhaXuatBan WHERE maNXB = ?";
        NhaXuatBanDTO nxb = null;

        try {
            conn.openConnection();
            PreparedStatement ps = conn.getConnection().prepareStatement(query);
            ps.setString(1, maNXB);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                nxb = new NhaXuatBanDTO(
                   // rs.getString("maNXB"),
                    rs.getString("tenNXB"),
                    rs.getString("diaChiNXB"),
                    rs.getString("sdt"),
                    rs.getInt("trangThai")
                );
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi tìm NXB theo mã: " + e.getMessage());
>>>>>>> a3477ed (giao dien nha xuat ban, tai khoan)
        } finally {
            conn.closeConnection();
        }
        return nxb;
    }

<<<<<<< HEAD
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
=======
   @Override
    public ArrayList<NhaXuatBanDTO> search(String searchContent) {
        ArrayList<NhaXuatBanDTO> arr = new ArrayList<>();
        String query = "SELECT * FROM NhaXuatBan WHERE "
                + "maNXB LIKE ? OR tenNXB LIKE ? OR diaChiNXB LIKE ? OR sdt LIKE ?";

        try {
            conn.openConnection();
            PreparedStatement ps = conn.getConnection().prepareStatement(query);
            for (int i = 1; i <= 4; i++) {
                ps.setString(i, "%" + searchContent + "%");
            }
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                arr.add(new NhaXuatBanDTO(
                   // rs.getString("maNXB"),
                    rs.getString("tenNXB"),
                    rs.getString("diaChiNXB"),
                    rs.getString("sdt"),
                    rs.getInt("trangThai")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi tìm kiếm NXB: " + e.getMessage());
>>>>>>> a3477ed (giao dien nha xuat ban, tai khoan)
        } finally {
            conn.closeConnection();
        }
        return arr;
    }

<<<<<<< HEAD
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
=======
   public NhaXuatBanDTO getByName(String tenNXB) {
        String query = "SELECT * FROM NhaXuatBan WHERE tenNXB LIKE ?";
        NhaXuatBanDTO nxb = null;

        try {
            conn.openConnection();
            PreparedStatement ps = conn.getConnection().prepareStatement(query);
            ps.setString(1, "%" + tenNXB + "%");
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                nxb = new NhaXuatBanDTO(
                   // rs.getString("maNXB"),
                    rs.getString("tenNXB"),
                    rs.getString("diaChiNXB"),
                    rs.getString("sdt"),
                    rs.getInt("trangThai")
                );
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi tìm kiếm NXB theo tên: " + e.getMessage());
>>>>>>> a3477ed (giao dien nha xuat ban, tai khoan)
        } finally {
            conn.closeConnection();
        }
        return nxb;
    }
<<<<<<< HEAD
}
=======
   
    // Phương thức lấy mã nhà xuất bản lớn nhất
   public String getLastMaNXB() {
    String lastMaNXB = null;
    String sql = "SELECT TOP 1 maNXB FROM NhaXuatBan ORDER BY maNXB DESC";  // Lấy mã nhà xuất bản lớn nhất
    
    try (Connection conn = new Constant().getConnection();  // Kết nối với cơ sở dữ liệu
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {
        
        if (rs.next()) {
            lastMaNXB = rs.getString("maNXB");  // Lấy mã nhà xuất bản
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    
    return lastMaNXB;  // Trả về mã nhà xuất bản lớn nhất
}


}

    

>>>>>>> a3477ed (giao dien nha xuat ban, tai khoan)
