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
public class PhieuNhapDTO {
    private String soPN;
    private String maNV;
    private String maNXB;
    private String maKho;
    private LocalDate ngayNhap;
    private int tongTien;
    private int trangThai;


    public PhieuNhapDTO() {
    
    }

    public PhieuNhapDTO(String soPN, String maNV,  String maNXB,String maKho, LocalDate ngayNhap, int tongTien, int trangThai) {
        this.soPN = soPN;
        this.maNV = maNV;
        this.maKho = maKho;
        this.trangThai = trangThai;
        this.maNXB = maNXB;
        this.ngayNhap = ngayNhap;
        this.tongTien = tongTien;
    }

    public String getSoPN() {
        return soPN;
    }

    public void setSoPN(String soPN) {
        this.soPN = soPN;
    }

    public String getMaKho() {
        return maKho;
    }

    public void setMaKho(String maKho) {
        this.maKho = maKho;
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

    public String getMaNXB() {
        return maNXB;
    }

    public void setMaNXB(String maNXB) {
        this.maNXB = maNXB;
    }

    public LocalDate getNgayNhap() {
        return ngayNhap;
    }

    public void setNgayNhap(LocalDate ngayNhap) {
        this.ngayNhap = ngayNhap;
    }

    public int getTongTien() {
        return tongTien;
    }

    public void setTongTien(int tongTien) {
        this.tongTien = tongTien;
    }
    
    
}
