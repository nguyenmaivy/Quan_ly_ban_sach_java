package dao;

import dto.TacGiaDTO;
import Config.Constant;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author MZI
 */
public class TacGiaDAO implements DAOInterface<TacGiaDTO> {

    Constant jdbc = new Constant();

    @Override
    public ArrayList<TacGiaDTO> getALL() {
        ArrayList<TacGiaDTO> arr = new ArrayList<TacGiaDTO>();

        try {

            //B1
            jdbc.openConnection();

            //B2
            String query = "SELECT * FROM TacGia WHERE trangThai = 1";

            //B3
            PreparedStatement ps = jdbc.getConnection().prepareStatement(query);

            //B4
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                TacGiaDTO tacgia = new TacGiaDTO();
                tacgia.setMaTG(rs.getString(1));
                tacgia.setTenTG(rs.getString(2));
                tacgia.setLienLac(rs.getString(3));
                tacgia.setTrangThai(rs.getInt(4));
                arr.add(tacgia);
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
        boolean result = false;

        try {

            //B1
            jdbc.openConnection();

            //B2
            String query = "SELECT * FROM TacGia WHERE maTG = ?";

            //B3
            PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
            ps.setString(1, D);

            //B4
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
    public boolean add(TacGiaDTO d) {
        boolean result = false;

        try {

            //B1
            jdbc.openConnection();

            //B2
            String query = "INSERT INTO TacGia VALUES (?,?,?,?)";

            //B3
            PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
            ps.setString(1, d.getMaTG());
            ps.setString(2, d.getTenTG());
            ps.setString(3, d.getLienLac());
            ps.setInt(4, 1);

            //B4
            if (ps.executeUpdate() > 0) {
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jdbc.closeConnection();
        }
        return result;
    }

    @Override
    public boolean delete(String d) {
        boolean result = false;

        try {

            //B1
            jdbc.openConnection();

            //B2
            String query = "UPDATE TacGia SET trangThai = 0 WHERE maTG = ?";

            //B3
            PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
            ps.setString(1, d);

            //B4
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
    public boolean update(TacGiaDTO d) {
        boolean result = false;

        try {

            //B1
            jdbc.openConnection();

            //B2
            String query = "UPDATE TacGia SET tenTG = ?, lienLac = ? WHERE maTG = ?";

            //B3
            PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
            ps.setString(1, d.getTenTG());
            ps.setString(2, d.getLienLac());
            ps.setString(3, d.getMaTG());

            //B4
            if (ps.executeUpdate() > 0) {
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jdbc.closeConnection();
        }
        return result;
    }

    @Override
    public TacGiaDTO getByID(String d) {
        TacGiaDTO tacgia = null;
        
        try{
            
            //B1
            jdbc.openConnection();
            
            //B2
            String query = "SELECT * FROM TacGia WHERE maTG = ?";
            
            //B3
            PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
            ps.setString(1, d);
            
            //B4
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                String maTG = rs.getString(1);
                String tenTG = rs.getString(2);
                String lienLac = rs.getString(3);
                
                tacgia = new TacGiaDTO(maTG, tenTG, lienLac, 1);
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            jdbc.closeConnection();
        }
        return tacgia;
    }

    @Override
    public ArrayList<TacGiaDTO> search(String d) {
       ArrayList<TacGiaDTO> arr = new ArrayList<>();
        try {
            jdbc.openConnection();

            String query = "SELECT * FROM TacGia WHERE (maTG LIKE ? OR tenTG LIKE ? OR lienLac LIKE ?) AND trangThai = 1";

            PreparedStatement ps = jdbc.getConnection().prepareStatement(query);

            ps.setString(1, "%" + d + "%");
            ps.setString(2, "%" + d + "%");
            ps.setString(3, "%" + d + "%");

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                arr.add(new TacGiaDTO(
                        rs.getString("maTG"),
                        rs.getString("tenTG"),
                        rs.getString("lienLac"),
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
    
    public TacGiaDTO getByName(String tenTG) {
        TacGiaDTO tacgia = null;
        
        try{
            
            //B1
            jdbc.openConnection();
            
            //B2
            String query = "SELECT maTG, tenTG, lienLac FROM TacGia WHERE tenTG like ?";
            
            //B3
            PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
            ps.setString(1, "%" + tenTG + "%");
            
            //B4
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                String maTG = rs.getString(1);
                String tenTacGia =rs.getString(2);
                String lienLac = rs.getString(3);
                
                tacgia = new TacGiaDTO(maTG, tenTacGia, lienLac, 1);
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            jdbc.closeConnection();
        }
        return tacgia;
    }

}
