/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dto.ChiTietPhieuNhapDTO;
import Config.Constant;
import dto.ChiTietHoaDonDTO;
import java.util.ArrayList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author MZI
 */
public class ChiTietPhieuNhapDAO implements DAOInterface<ChiTietPhieuNhapDTO> {

    Constant jdbc = new Constant();

    @Override
    public ArrayList<ChiTietPhieuNhapDTO> getALL() {
        ArrayList<ChiTietPhieuNhapDTO> arr = new ArrayList<>();

        try {
            jdbc.openConnection();

            String query = "SELECT * FROM ChiTietPhieuNhap WHERE trangThai = 1";
            PreparedStatement ps = jdbc.getConnection().prepareStatement(query);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ChiTietPhieuNhapDTO ctpn = new ChiTietPhieuNhapDTO();
                ctpn.setMaSach(rs.getString(1));
                ctpn.setSoPN(rs.getString(2));
                ctpn.setSoLuongNhap(rs.getInt(3));
                ctpn.setGiaNhap(rs.getInt(4));

                arr.add(ctpn);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jdbc.closeConnection();
        }
        return arr;
    }

    public ArrayList<ChiTietPhieuNhapDTO> getAllByID(String sopn) {
        ArrayList<ChiTietPhieuNhapDTO> arr = new ArrayList<>();

        try {

            //B1
            jdbc.openConnection();

            //B2
            String query = "select * from ChiTietPhieuNhap where soPN=? and trangThai = 1";

            //B3
            PreparedStatement ps = jdbc.getConnection().prepareStatement(sopn);

            //B4
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String soPN = rs.getString(1);
                String maSach = rs.getString(2);
                int soLuongNhap = rs.getInt(3);
                int giaNhap = rs.getInt(4);
                arr.add(new ChiTietPhieuNhapDTO(maSach, soPN, soLuongNhap, giaNhap, 1));
//
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jdbc.closeConnection();
        }
        return arr;

    }

    @Override
    public boolean has(String D) {
        return false;
    }

    @Override
    public boolean add(ChiTietPhieuNhapDTO d) {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        boolean result = false;
        try {
            //b1
            jdbc.openConnection();

            //b2
            String queryInsert = "INSERT INTO ChiTietPhieuNhap VALUES(?,?,?,?,?)";

            //B3
            PreparedStatement ps = jdbc.getConnection().prepareStatement(queryInsert);
            ps.setString(1, d.getMaSach());
            ps.setString(2, d.getSoPN());
            ps.setInt(3, d.getSoLuongNhap());
            ps.setInt(4, d.getGiaNhap());
            ps.setInt(5, 1);

            //b4
            if (ps.executeUpdate() > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //b5: đóng kết nối tới database
            jdbc.closeConnection();
        }
        return result;

    }

    public ArrayList<ChiTietPhieuNhapDTO> getByCondition(String data, String condition, String ma) {
        ArrayList<ChiTietPhieuNhapDTO> arr = new ArrayList<ChiTietPhieuNhapDTO>();

        try {
            //B1
            jdbc.openConnection();

            //B2
            String query = "select soPN, maSach, soLuongNhap, giaNhap from ChiTietPhieuNhap where " + condition + " like ? and soPN = ?";

            //B3
            PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
            ps.setString(1, "%" + data + "%");
            ps.setString(2, ma);

            //B4
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String soPN = rs.getString(1);
                String maSach = rs.getString(2);
                int soLuongNhap = rs.getInt(3);
                int giaNhap = rs.getInt(4);
                arr.add(new ChiTietPhieuNhapDTO(maSach, soPN, soLuongNhap, giaNhap, 1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jdbc.closeConnection();
        }
        return arr;
    }

    public boolean updateTrangthai(String ma) {
        try {

            //B1
            jdbc.openConnection();

            //B2
            String query = "UPDATE ChiTietPhieuNhap SET trangThai = 0 WHERE soPN = ?";

            //B3
            PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
            ps.setString(1, ma);

            //B4
            if (ps.executeUpdate() > 0) {
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jdbc.closeConnection();
        }

        return false;
    }

    public ArrayList<ChiTietPhieuNhapDTO> getBySoluong(String data, String condition, String ma) {
        ArrayList<ChiTietPhieuNhapDTO> arr = new ArrayList<ChiTietPhieuNhapDTO>();
        try {

            //B1
            jdbc.openConnection();

            //B2
            String query = "SELECT soPN, maSach, soLuongNhap, giaNhap from ChiTietPhieuNhap WHERE  soLuongNhap " + condition + " ? AND soPN = ?";

            //B3
            PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
            ps.setString(1, data);
            ps.setString(2, ma);

            //B4
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String soPN = rs.getString(1);
                String maSach = rs.getString(2);
                int soLuongNhap = rs.getInt(3);
                int giaNhap = rs.getInt(4);
                arr.add(new ChiTietPhieuNhapDTO(maSach, soPN, soLuongNhap, giaNhap, 1));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jdbc.closeConnection();
        }

        return arr;
    }

    public ArrayList<ChiTietPhieuNhapDTO> getByGia(String data, String condition, String ma) {
        ArrayList<ChiTietPhieuNhapDTO> arr = new ArrayList<ChiTietPhieuNhapDTO>();

        try {
            jdbc.openConnection();

            String query = "SELECT soPN, maSach, soLuongNhap, giaNhap from ChiTietPhieuNhap WHERE giaNhap "
                    + condition + " ? AND soPN = ?";
            PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
            ps.setString(1, data);
            ps.setString(2, ma);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String soPN = rs.getString(1);
                String maSach = rs.getString(2);
                int soLuongNhap = rs.getInt(3);
                int giaNhap = rs.getInt(4);
                arr.add(new ChiTietPhieuNhapDTO(maSach, soPN, soLuongNhap, giaNhap, 1));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jdbc.closeConnection();
        }
        return arr;
    }

    @Override
    public boolean delete(String d) {
        return false;
    }

    @Override
    public boolean update(ChiTietPhieuNhapDTO d) {
        return false;
    }

    @Override
    public ChiTietPhieuNhapDTO getByID(String d) {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        return null;
    }

    @Override
    public ArrayList<ChiTietPhieuNhapDTO> search(String d) {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        return null;
    }

}
