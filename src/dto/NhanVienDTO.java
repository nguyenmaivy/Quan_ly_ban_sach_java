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
public class NhanVienDTO {

    private String maNV;
    private String tenNV;
    private int gioiTinh;
    private String diaChi;
    private LocalDate ngayVao;
    private String sdt;
    private LocalDate ngaySinh;
    private int trangThai;

    public NhanVienDTO() {

    }

    public NhanVienDTO(String maNV, String tenNV, int gioiTinh, String diaChi, LocalDate ngayVao, String sdt, LocalDate ngaySinh, int trangThai) {
        this.maNV = maNV;
        this.tenNV = tenNV;
        this.gioiTinh = gioiTinh;
        this.diaChi = diaChi;
        this.ngayVao = ngayVao;
        this.sdt = sdt;
        this.ngaySinh = ngaySinh;
        this.trangThai = trangThai;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getTenNV() {
        return tenNV;
    }

    public void setTenNV(String tenNV) {
        this.tenNV = tenNV;
    }

    public int getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(int gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public LocalDate getNgayVao() {
        return ngayVao;
    }

    public void setNgayVao(LocalDate ngayVao) {
        this.ngayVao = ngayVao;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public LocalDate getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(LocalDate ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

}
