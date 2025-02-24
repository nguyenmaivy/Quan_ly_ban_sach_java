/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

/**
 *
 * @author MZI
 */
public class TacGiaDTO {
    private String maTG;
    private String tenTG;
    private String lienLac;
    private int trangThai;

    public TacGiaDTO() {
    
    }

    public TacGiaDTO(String maTG, String tenTG, String lienLac, int trangThai) {
        this.maTG = maTG;
        this.tenTG = tenTG;
        this.lienLac = lienLac;
        this.trangThai = trangThai;
    }

    public String getMaTG() {
        return maTG;
    }

    public void setMaTG(String maTG) {
        this.maTG = maTG;
    }

    public String getTenTG() {
        return tenTG;
    }

    public void setTenTG(String tenTG) {
        this.tenTG = tenTG;
    }

    public String getLienLac() {
        return lienLac;
    }

    public void setLienLac(String lienLac) {
        this.lienLac = lienLac;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }
    
    
}
