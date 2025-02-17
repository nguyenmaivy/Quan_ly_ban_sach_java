/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

/**
 *
 * @author MZI
 */
public class ChiTietHoaDonDTO {
    private String maSach;
    private String soHD;
    private int soLuongBan;
    private int giaBan;

    public ChiTietHoaDonDTO() {
    
    }

    public ChiTietHoaDonDTO(String maSach, String soHD, int soLuongBan, int giaBan) {
        this.maSach = maSach;
        this.soHD = soHD;
        this.soLuongBan = soLuongBan;
        this.giaBan = giaBan;
    }

    public String getMaSach() {
        return maSach;
    }

    public void setMaSach(String maSach) {
        this.maSach = maSach;
    }

    public String getSoHD() {
        return soHD;
    }

    public void setSoHD(String soHD) {
        this.soHD = soHD;
    }

    public int getSoLuongBan() {
        return soLuongBan;
    }

    public void setSoLuongBan(int soLuongBan) {
        this.soLuongBan = soLuongBan;
    }

    public int getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(int giaBan) {
        this.giaBan = giaBan;
    }
    
    
}
