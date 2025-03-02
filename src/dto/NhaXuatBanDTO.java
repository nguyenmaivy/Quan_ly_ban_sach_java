/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

/**
 *
 * @author MZI
 */
public class NhaXuatBanDTO {
    private String maNXB;
    private String diachiNXB;
    private String sdt;
    private String tenNXB;
    private int trangThai;

    public NhaXuatBanDTO() {
    
    }

    public NhaXuatBanDTO(String maNXB, String diachiNXB, String sdt, String tenNXB, int trangThai) {
        this.maNXB = maNXB;
        this.diachiNXB = diachiNXB;
        this.sdt = sdt;
        this.tenNXB = tenNXB;
        this.trangThai = trangThai;
    }

    public String getMaNXB() {
        return maNXB;
    }

    public void setMaNXB(String maNXB) {
        this.maNXB = maNXB;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    public String getDiachiNXB() {
        return diachiNXB;
    }

    public void setDiachiNXB(String diachiNXB) {
        this.diachiNXB = diachiNXB;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getTenNXB() {
        return tenNXB;
    }

    public void setTenNXB(String tenNXB) {
        this.tenNXB = tenNXB;
    }
    
    
}
