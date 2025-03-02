/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

/**
 *
 * @author MZI
 */
public class TacGia_Sach {

    private String maTG;
    private String maSach;
    private String vaitro;

    public TacGia_Sach() {

    }

    public TacGia_Sach(String maTG, String maSach, String vaitro) {
        this.maTG = maTG;
        this.maSach = maSach;
        this.vaitro = vaitro;
    }

    public String getVaitro() {
        return vaitro;
    }

    public void setVaitro(String vaitro) {
        this.vaitro = vaitro;
    }

    public String getMaTG() {
        return maTG;
    }

    public void setMaTG(String maTG) {
        this.maTG = maTG;
    }

    public String getMaSach() {
        return maSach;
    }

    public void setMaSach(String maSach) {
        this.maSach = maSach;
    }

}
