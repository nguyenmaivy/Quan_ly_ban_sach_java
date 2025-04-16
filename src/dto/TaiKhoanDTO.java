/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

/**
 *
 * @author MZI
 */
public class TaiKhoanDTO {
    private String useName;
    private String matKhau;
    private String sdt;
    private String maNV;
    
    private int maNhomQuyen;
    private int trangThai;
    
    public TaiKhoanDTO(){
    
    }

    public TaiKhoanDTO(String useName, String matKhau, String sdt, String maNV, int maNhomQuyen, int trangThai) {
        this.useName = useName;
        this.matKhau = matKhau;
        this.sdt = sdt;
        this.maNV = maNV;
        
        this.maNhomQuyen = maNhomQuyen;
        this.trangThai = trangThai;
    }

//    public TaiKhoanDTO(String maNV, String maKH) {
//        this.maNV = maNV;
//        this.maKH = maKH;
//    }
    

    public String getUseName() {
        return useName;
    }

    public void setUseName(String useName) {
        this.useName = useName;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
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

    
     public int getMaNhomQuyen() {
        return maNhomQuyen;
    }

    public void setMaNhomQuyen(int maNhomQuyen) {
        this.maNhomQuyen = maNhomQuyen;
    }
    
 }