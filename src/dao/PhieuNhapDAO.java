/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.util.ArrayList;
import dto.PhieuNhapDTO;
import Config.Constant;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
//import java.util.Date;
import java.sql.Date;

/**
 *
 * @author MZI
 */
public class PhieuNhapDAO implements DAOInterface<PhieuNhapDTO> {

    Constant jdbc = new Constant();

    @Override
    public ArrayList<PhieuNhapDTO> getALL() {
        ArrayList<PhieuNhapDTO> arr = new ArrayList<>();

        try {
            jdbc.openConnection();

            String query = "SELECT * FROM PhieuNhap WHERE trangThai = 1";

            PreparedStatement ps = jdbc.getConnection().prepareStatement(query);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                PhieuNhapDTO phieuNhap = new PhieuNhapDTO();
                phieuNhap.setSoPN(rs.getString(1));
                phieuNhap.setMaNV(rs.getString(2));
                phieuNhap.setMaNXB(rs.getString(3));
                phieuNhap.setMaKho(rs.getString(4));
                LocalDate dateFromDatabase = rs.getObject("ngayNhap", LocalDate.class);
                phieuNhap.setNgayNhap(dateFromDatabase);
                phieuNhap.setTongTien(rs.getInt(6));

                arr.add(phieuNhap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jdbc.closeConnection();
        }
        return arr;
    }

    @Override
    public boolean has(String soPN) {
        boolean result = false;
        try {
            jdbc.openConnection();

            String query = "SELECT * FROM PhieuNhap WHERE soPN = ?";

            PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
            ps.setString(1, soPN);

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
    public boolean add(PhieuNhapDTO pn) {
        boolean result = false;
        try {
            jdbc.openConnection();

            String query = "INSERT INTO PhieuNhap VALUES (?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
            ps.setString(1, pn.getSoPN());
            ps.setString(2, pn.getMaNV());
            ps.setString(3, pn.getMaNXB());
            ps.setString(4, pn.getMaKho());
            ps.setDate(5, Date.valueOf(pn.getNgayNhap()));
            ps.setInt(6, pn.getTongTien());
            ps.setInt(7, 1);

            if (ps.executeUpdate() > 0) {
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jdbc.openConnection();
        }
        return result;
    }

    @Override
    public boolean delete(String soPN) {
        boolean result = false;

        try {
            jdbc.openConnection();

            String query = "UPDATE PhieuNhap SET trangThai = 0 WHERE soPN = ?";

            PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
            ps.setString(1, soPN);

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
    public PhieuNhapDTO getByID(String d) {
        PhieuNhapDTO pn = null;

        try {
            jdbc.openConnection();

            String query = "SELECT soPN, maNV, maNXB, maKho, ngayNhap, tongTien FROM PhieuNhap WHERE trangThai = 1";

            PreparedStatement ps = jdbc.getConnection().prepareStatement(query);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String soPN = rs.getString(1);
                String maNV = rs.getString(2);
                String maNXB = rs.getString(3);
                String maKho = rs.getString(4);
                LocalDate ngayNhap = rs.getObject("ngayNhap", LocalDate.class);
                int tongTien = rs.getInt(6);

                pn = new PhieuNhapDTO(soPN, maNV, maNXB, maKho, ngayNhap, tongTien, 1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jdbc.closeConnection();
        }
        return null;
    }

    public int countPhieuNhap() {
        int pn = -1;
        try {
            jdbc.openConnection();

            String query = "SELECT COUNT(soPN) FROM PhieuNhap";

            PreparedStatement ps = jdbc.getConnection().prepareStatement(query);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                pn = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jdbc.closeConnection();
        }
        return pn;
    }

    public ArrayList<PhieuNhapDTO> getByID(String data, String condition) {
        ArrayList<PhieuNhapDTO> arr = new ArrayList<PhieuNhapDTO>();

        try {

            //B1
            jdbc.openConnection();

            //B2
            String query = "SELECT soPN, maNV, maNXB, maKho, ngayNhap, tongTien FROM PhieuNhap WHERE "
                    + condition + " like ? AND trangThai = 1";

            //B3
            PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
            ps.setString(1, "%" + data + "%");

            //B4
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String soPN = rs.getString(1);
                String maNV = rs.getString(2);
                String maNXB = rs.getString(3);
                String maKho = rs.getString(4);
                Date sqlDate = rs.getDate(5);
                LocalDate ngayNhap = sqlDate.toLocalDate();
                int tongTien = rs.getInt(6);

                arr.add(new PhieuNhapDTO(soPN, maNV, maNXB, maKho, ngayNhap, tongTien, 1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jdbc.closeConnection();
        }

        return arr;
    }

    public ArrayList<PhieuNhapDTO> getByGia(String data, String condition) {
        ArrayList<PhieuNhapDTO> arr = new ArrayList<PhieuNhapDTO>();

        try {

            //B1
            jdbc.openConnection();

            //B2
            String query = "SELECT * FROM PhieuNhap WHERE tongTien "
                    + condition + " ?";

            //B3
            PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
            ps.setString(1, "%" + data + "%");

            //B4
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String soPN = rs.getString(1);
                String maNV = rs.getString(2);
                String maNXB = rs.getString(3);
                String maKho = rs.getString(4);
                Date sqlDate = rs.getDate(5);
                LocalDate ngayNhap = sqlDate.toLocalDate();
                int tongTien = rs.getInt(6);

                arr.add(new PhieuNhapDTO(soPN, maNV, maNXB, maKho, ngayNhap, tongTien, 1));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jdbc.closeConnection();
        }

        return arr;
    }

    //Lọc phiếu nhập theo ngày
    public ArrayList<PhieuNhapDTO> getByDate(String data, String condition) {
        ArrayList<PhieuNhapDTO> arr = new ArrayList<PhieuNhapDTO>();

        try {

            //B1
            jdbc.openConnection();

            //B2
            String query = "SELECT * FROM PhieuNhap WHERE  ngayNhap "
                    + condition + " ? AND trangThai = 1";

            //B3
            PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
            ps.setString(1, data);

            //B4
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String soPN = rs.getString(1);
                String maNV = rs.getString(2);
                String maNXB = rs.getString(3);
                String maKho = rs.getString(4);
                Date sqlDate = rs.getDate(5);
                LocalDate ngayNhap = sqlDate.toLocalDate();
                int tongTien = rs.getInt(6);

                arr.add(new PhieuNhapDTO(soPN, maNV, maNXB, maKho, ngayNhap, tongTien, 1));

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jdbc.closeConnection();
        }

        return arr;
    }

    public ArrayList<String> getAllMaKho() {
        ArrayList<String> arr = new ArrayList<String>();

        try {

            //B1
            jdbc.openConnection();

            //B2
            String query = "SELECT DISTINCT maKho FROM PhieuNhap WHERE trangThai=1";

            //B3
            PreparedStatement ps = jdbc.getConnection().prepareStatement(query);

            //B4
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                arr.add(rs.getString(1));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jdbc.closeConnection();
        }

        return arr;
    }

    public ArrayList<PhieuNhapDTO> getByStartEnd(String start, String end) {
        ArrayList<PhieuNhapDTO> arr = new ArrayList<PhieuNhapDTO>();

        try {

            //B1
            jdbc.openConnection();

            //B2
            String query = "SELECT * FROM PhieuNhap WHERE  ngayNhap BETWEEN ?  AND ?  AND trangThai = 1";

            //B3
            PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
            ps.setString(1, start);
            ps.setString(2, end);

            //B4
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String soPN = rs.getString(1);
                String maNV = rs.getString(2);
                String maNXB = rs.getString(3);
                String maKho = rs.getString(4);
                Date sqlDate = rs.getDate(5);
                LocalDate ngayNhap = sqlDate.toLocalDate();
                int tongtien = rs.getInt(6);

                arr.add(new PhieuNhapDTO(soPN, maNV, maNXB, maKho, ngayNhap, tongtien, tongtien));

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jdbc.closeConnection();
        }

        return arr;
    }

    @Override
    public boolean update(PhieuNhapDTO d) {
        return false;
    }

    @Override
    public ArrayList<PhieuNhapDTO> search(String searchContent) {
        return null;
    }

}
