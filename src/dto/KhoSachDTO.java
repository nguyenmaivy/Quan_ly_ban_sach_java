/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

/**
 *
 * @author MZI
 */
public class KhoSachDTO {
    private String maKho;
    private String tenKho;
    private String diaChi;
    private String loai;
    
    public KhoSachDTO(){
    
    }

    public KhoSachDTO(String maKho, String tenKho, String diaChi, String loai) {
        this.maKho = maKho;
        this.tenKho = tenKho;
        this.diaChi = diaChi;
        this.loai = loai;
    }

    public String getMaKho() {
        return maKho;
    }

    public void setMaKho(String maKho) {
        this.maKho = maKho;
    }

    public String getTenKho() {
        return tenKho;
    }

    public void setTenKho(String tenKho) {
        this.tenKho = tenKho;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getLoai() {
        return loai;
    }

    public void setLoai(String loai) {
        this.loai = loai;
    }

    
    
    
}
