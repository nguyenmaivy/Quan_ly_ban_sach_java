package dto;

import java.time.LocalDate;
import java.sql.Date; // Import java.sql.Date for database compatibility

/**
 * Data Transfer Object cho Hóa đơn
 */
public class HoaDonDTO {
    private String soHD;
    private LocalDate ngayBan;
    private String maNV;
    private int trangThai;
    private String tenNV; //Thêm thuộc tính tên nhân viên

    public HoaDonDTO() {
    }

    public HoaDonDTO(String soHD, LocalDate ngayBan, String maNV, int trangThai) {
        this.soHD = soHD;
        this.ngayBan = ngayBan;
        this.maNV = maNV;
        this.trangThai = trangThai;
    }

    public HoaDonDTO(String soHD, LocalDate ngayBan) {
        this.soHD = soHD;
        this.ngayBan = ngayBan;
    }

    public HoaDonDTO(String maNV, int trangThai) {
        this.maNV = maNV;
        this.trangThai = trangThai;
    }

    // Constructor đầy đủ, bao gồm tenNV
    public HoaDonDTO(String soHD, LocalDate ngayBan, String maNV, int trangThai, String tenNV) {
        this.soHD = soHD;
        this.ngayBan = ngayBan;
        this.maNV = maNV;
        this.trangThai = trangThai;
        this.tenNV = tenNV;
    }

    // GETTER, SETTER
    public String getSoHD() {
        return soHD;
    }

    public void setSoHD(String soHD) {
        this.soHD = soHD;
    }

    public LocalDate getNgayBan() {
        return ngayBan;
    }

    public void setNgayBan(LocalDate ngayBan) {
        this.ngayBan = ngayBan;
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

    public String getTenNV() {
        return tenNV;
    }

    public void setTenNV(String tenNV) {
        this.tenNV = tenNV;
    }
}
