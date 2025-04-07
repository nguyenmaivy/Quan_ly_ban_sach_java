/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;
import java.time.LocalDate;
/**
 *
 * @author MZI
 */
public class HoaDonDTO {
    private String soHD;
    private LocalDate ngayBan;
    private String maNV;
    private int trangThai;
    
    
    public HoaDonDTO(String soHD1, LocalDate ngayBan1, String maNV1, int trangThai1) {
        this.soHD = soHD1;
        this.ngayBan = ngayBan1;
        this.maNV = maNV1;
        this.trangThai = trangThai1;
    }

    public HoaDonDTO(String soHD, LocalDate ngayBan) {
        this.soHD = soHD;
        this.ngayBan = ngayBan;
    }

    public HoaDonDTO(String maNV, int trangThai) {
        this.maNV = maNV;
        this.trangThai = trangThai;
    }

    public HoaDonDTO() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    // GETTER, SETTER
    public String getSoHD() {
        return soHD;
    }

    public void setSoHD(String soHD) {
        this.soHD = soHD;
    }

    public LocalDate getNgayBan() {
        return ngayBan;
    }

    public void setNgayBan(LocalDate ngayBan) {
        this.ngayBan = ngayBan;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }
    
    
}
