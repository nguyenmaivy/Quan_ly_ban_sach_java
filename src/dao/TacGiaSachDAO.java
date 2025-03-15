/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author LAPTOP
 */

import Config.Constant;
import dto.TacGiaSachDTO;
import java.sql.*;
import java.util.ArrayList;

public class TacGiaSachDAO implements DAOInterface<TacGiaSachDTO> {

    Constant conn = new Constant();

    @Override
    public ArrayList<TacGiaSachDTO> getALL() {
        ArrayList<TacGiaSachDTO> arr = new ArrayList<>();

        try {
            conn.openConnection();
            String query = "SELECT * FROM Viet";
            PreparedStatement ps = conn.getConnection().prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String maTG = rs.getString("maTG");
                String maSach = rs.getString("maSach");
                String vaiTro = rs.getString("vaiTro");

                arr.add(new TacGiaSachDTO(maTG, maSach, vaiTro));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.closeConnection();
        }
        return arr;
    }

    @Override
    public boolean has(String maTG) {
        boolean result = false;

        try {
            conn.openConnection();
            String query = "SELECT * FROM Viet WHERE maTG = ?";
            PreparedStatement ps = conn.getConnection().prepareStatement(query);
            ps.setString(1, maTG);

            ResultSet rs = ps.executeQuery();
            result = rs.next();  // Nếu có kết quả, trả về true

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.closeConnection();
        }
        return result;
    }

    @Override
    public boolean add(TacGiaSachDTO tgs) {
        boolean result = false;

        try {
            conn.openConnection();
            String query = "INSERT INTO Viet (maTG, maSach, vaiTro) VALUES (?, ?, ?)";

            PreparedStatement ps = conn.getConnection().prepareStatement(query);
            ps.setString(1, tgs.getMaTG());
            ps.setString(2, tgs.getMaSach());
            ps.setString(3, tgs.getVaiTro());

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
    public boolean delete(String maTG) {
        boolean result = false;

        try {
            conn.openConnection();
            String query = "DELETE FROM Viet WHERE maTG = ?";

            PreparedStatement ps = conn.getConnection().prepareStatement(query);
            ps.setString(1, maTG);

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
    public boolean update(TacGiaSachDTO tgs) {
        boolean result = false;

        try {
            conn.openConnection();
            String query = "UPDATE Viet SET maSach = ?, vaiTro = ? WHERE maTG = ?";

            PreparedStatement ps = conn.getConnection().prepareStatement(query);
            ps.setString(1, tgs.getMaSach());
            ps.setString(2, tgs.getVaiTro());
            ps.setString(3, tgs.getMaTG());

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
    public TacGiaSachDTO getByID(String maTG) {
        TacGiaSachDTO tgs = null;

        try {
            conn.openConnection();
            String query = "SELECT * FROM Viet WHERE maTG = ?";

            PreparedStatement ps = conn.getConnection().prepareStatement(query);
            ps.setString(1, maTG);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String maSach = rs.getString("maSach");
                String vaiTro = rs.getString("vaiTro");
                tgs = new TacGiaSachDTO(maTG, maSach, vaiTro);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.closeConnection();
        }
        return tgs;
    }

    @Override
    public ArrayList<TacGiaSachDTO> search(String searchContent) {
        ArrayList<TacGiaSachDTO> arr = new ArrayList<>();

        try {
            conn.openConnection();
            String query = "SELECT * FROM Viet WHERE maTG LIKE ? OR maSach LIKE ? OR vaiTro LIKE ?";

            PreparedStatement ps = conn.getConnection().prepareStatement(query);
            ps.setString(1, "%" + searchContent + "%");
            ps.setString(2, "%" + searchContent + "%");
            ps.setString(3, "%" + searchContent + "%");

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String maTG = rs.getString("maTG");
                String maSach = rs.getString("maSach");
                String vaiTro = rs.getString("vaiTro");

                arr.add(new TacGiaSachDTO(maTG, maSach, vaiTro));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.closeConnection();
        }
        return arr;
    }
}
