/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

/**
 *
 * @author MZI
 */
public class nhomQuyenDTO {

    private int maNhomQuyen;
    private String tenNhomQuyen;
    private int trangThai;

    public nhomQuyenDTO(int maNhomQuyen, String tenNhomQuyen, int trangThai) {
        this.maNhomQuyen = maNhomQuyen;
        this.tenNhomQuyen = tenNhomQuyen;
        this.trangThai = trangThai;
    }

    public nhomQuyenDTO() {
        this.maNhomQuyen = 0;
        this.tenNhomQuyen = "";
        this.trangThai = 1; // Giá trị mặc định là hoạt động
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    public int getMaNhomQuyen() {
        return maNhomQuyen;
    }

    public void setMaNhomQuyen(int maNhomQuyen) {
        this.maNhomQuyen = maNhomQuyen;
    }

    public String getTenNhomQuyen() {
        return tenNhomQuyen;
    }

    public void setTenNhomQuyen(String tenNhomQuyen) {
        this.tenNhomQuyen = tenNhomQuyen;
    }

}
