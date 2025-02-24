/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

/**
 *
 * @author MZI
 */
public class ChiTietPhieuNhapDTO {
    private String maSach;
    private String soPN;
    private int soLuongNhap;
    private int giaNhap;
    private int trangThai;

    public ChiTietPhieuNhapDTO() {
    
    }

    public ChiTietPhieuNhapDTO(String maSach, String soPN, int soLuongNhap, int giaNhap, int trangThai) {
        this.maSach = maSach;
        this.soPN = soPN;
        this.soLuongNhap = soLuongNhap;
        this.giaNhap = giaNhap;
        this.trangThai = trangThai;
    }

    public String getMaSach() {
        return maSach;
    }

    public void setMaSach(String maSach) {
        this.maSach = maSach;
    }

    public String getSoPN() {
        return soPN;
    }

    public void setSoPN(String soPN) {
        this.soPN = soPN;
    }

    public int getSoLuongNhap() {
        return soLuongNhap;
    }

    public void setSoLuongNhap(int soLuongNhap) {
        this.soLuongNhap = soLuongNhap;
    }

    public int getGiaNhap() {
        return giaNhap;
    }

    public void setGiaNhap(int giaNhap) {
        this.giaNhap = giaNhap;
    }
    
    
}
