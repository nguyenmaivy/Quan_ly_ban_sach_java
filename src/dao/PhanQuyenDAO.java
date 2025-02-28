/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import Config.Constant;
import dto.nhomQuyenDTO;
//import java.beans.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author MZI
 */
public class PhanQuyenDAO implements DAOInterface<nhomQuyenDTO> {

    Constant jdbc = new Constant();

    @Override
    public ArrayList<nhomQuyenDTO> getALL() {
        ArrayList<nhomQuyenDTO> arr = new ArrayList<>();

        try {
            //b1: thiết lập kết nối tới database
            jdbc.openConnection();

            //b2: tọa query
            String query = "SELECT * FROM nhomQuyen WHERE trangThai = 1";

            //b3: Tạo đối tượng PreparedStatement
            PreparedStatement ps = jdbc.getConnection().prepareStatement(query);

            //b4: Xử lý kết quả
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                nhomQuyenDTO quyen = new nhomQuyenDTO();
                quyen.setMaNhomQuyen(rs.getInt(1)); // Sửa thành setter
                quyen.setTenNhomQuyen(rs.getString(2)); // Nếu có thêm cột, hãy gán giá trị
                arr.add(quyen);

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //b5: Đóng kết nối tới database
            jdbc.closeConnection();
        }

        return arr;
    }

    @Override
    public boolean has(String maNhomQuyen) {
        boolean result = false;

        try {
            //b1
            jdbc.openConnection();

            //b2
            String query = "SELECT * FROM nhomQuyen WHERE maNhomQuyen = ?";

            //b3
            PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
            ps.setString(1, String.valueOf(maNhomQuyen));

            //b4
            ResultSet rs = ps.executeQuery();
            result = rs.next(); //di chuyển con trỏ rs đến dòng tiếp theo, trả về true/false. Khi k có dòng tiếp theo để di chuyển tới thì trả về false, ngược lại thì true tức là có ít nhất 11 dòng dữ liệu

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //b5
            jdbc.closeConnection();
        }
        return result;
    }

    @Override
    public boolean add(nhomQuyenDTO nq) {
        boolean result = false;
        try {
            //b1
            jdbc.openConnection();

            //b2
            String queryInsert = "INSERT INTO nhomQuyen (tenNhomQuyen, trangThai) VALUES(?,?)";

            //B3
            PreparedStatement ps = jdbc.getConnection().prepareStatement(queryInsert, PreparedStatement.RETURN_GENERATED_KEYS);
//            ps.setInt(1, nq.getMaNhomQuyen());
            ps.setString(1, nq.getTenNhomQuyen());
            ps.setInt(2, 1);

            //b4
            if (ps.executeUpdate() > 0) {
                ResultSet generatedKeys = ps.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int newId = generatedKeys.getInt(1);
                    nq.setMaNhomQuyen(newId); // Cập nhật ID mới vào đối tượng DTO
                }
                result = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //b5: đóng kết nối tới database
            jdbc.closeConnection();
        }
        return result;

    }

    @Override
    public boolean delete(String maNhomQuyen) {
        boolean result = false;
        try {
            jdbc.openConnection();
            String query = "UPDATE nhomQuyen SET trangThai = 0 WHERE maNhomQuyen = ?";
            PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
            ps.setString(1, String.valueOf(maNhomQuyen));
            if (ps.executeUpdate() > 0) {
                result = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //b5 đóng kết nối
            jdbc.closeConnection();
        }
        return result;
    }

    @Override
    public boolean update(nhomQuyenDTO nq) {
        boolean result = false;
        try {
            //b1
            jdbc.openConnection();
            //b2
            String query = "UPDATE nhomQuyen SET tenNhomQuyen = ? WHERE maNhomQuyen = ?";
            //b3
            PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
            ps.setString(1, nq.getTenNhomQuyen());
            ps.setInt(2, nq.getMaNhomQuyen());
            //b4
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
    public nhomQuyenDTO getByID(String quyen) {
        nhomQuyenDTO nhomquyen = null;
        try {
            jdbc.openConnection();

            String query = "SELECT * FROM nhomQuyen WHERE maNhomQuyen = ?";

            PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
            ps.setString(1, quyen);
//            ps.setString(2, quyen);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int maNhomQuyen = rs.getInt(1);
                String tenNhomQuyen = rs.getString(2);
                nhomquyen = new nhomQuyenDTO(
                        rs.getInt("maNhomQuyen"),
                        rs.getString("tenNhomQuyen"),
                        rs.getInt("trangThai")
                );

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jdbc.closeConnection();
        }
        return nhomquyen;

    }

    @Override
    public ArrayList<nhomQuyenDTO> search(String searchContent) {
        ArrayList<nhomQuyenDTO> arr = new ArrayList<>();
        try {
            jdbc.openConnection();

            String query = "SELECT * FROM nhomQuyen WHERE (maNhomQuyen LIKE ? OR tenNhomQuyen LIKE ?) AND trangThai = 1";

            PreparedStatement ps = jdbc.getConnection().prepareStatement(query);

            ps.setString(1, "%" + searchContent + "%");
            ps.setString(2, "%" + searchContent + "%");

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                arr.add(new nhomQuyenDTO(
                        rs.getInt("maNhomQuyen"),
                        rs.getString("tenNhomQuyen"),
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
