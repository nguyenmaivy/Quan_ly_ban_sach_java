
package gui.Panel;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ChiTietHoaDonGUI extends JFrame {

    private JLabel lblMaHoaDon, lblNgayLap, lblTenKhachHang, lblDiaChi, lblSoDienThoai, lblTongTien;
    private JTable tblChiTietHoaDon;
    private DefaultTableModel tblModel;

    public ChiTietHoaDonGUI(String maHoaDon, String ngayLap, String tenKhachHang, String diaChi, String soDienThoai, List<ChiTietHoaDon> danhSachChiTiet) {
        setTitle("CHI TIẾT HÓA ĐƠN");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel pnlMain = new JPanel(new BorderLayout());

        // Thông tin hóa đơn
        JPanel pnlHoaDonInfo = new JPanel(new GridLayout(2, 2));
        pnlHoaDonInfo.add(new JLabel("Mã hóa đơn:"));
        lblMaHoaDon = new JLabel(maHoaDon);
        pnlHoaDonInfo.add(lblMaHoaDon);
        pnlHoaDonInfo.add(new JLabel("Ngày lập:"));
        lblNgayLap = new JLabel(ngayLap);
        pnlHoaDonInfo.add(lblNgayLap);

        // Thông tin khách hàng
        JPanel pnlKhachHangInfo = new JPanel(new GridLayout(3, 2));
        pnlKhachHangInfo.add(new JLabel("Tên khách hàng:"));
        lblTenKhachHang = new JLabel(tenKhachHang);
        pnlKhachHangInfo.add(lblTenKhachHang);
        pnlKhachHangInfo.add(new JLabel("Địa chỉ:"));
        lblDiaChi = new JLabel(diaChi);
        pnlKhachHangInfo.add(lblDiaChi);
        pnlKhachHangInfo.add(new JLabel("Số điện thoại:"));
        lblSoDienThoai = new JLabel(soDienThoai);
        pnlKhachHangInfo.add(lblSoDienThoai);

        // Chi tiết hóa đơn
        tblModel = new DefaultTableModel();
        tblModel.addColumn("Tên sách");
        tblModel.addColumn("Số lượng");
        tblModel.addColumn("Đơn giá");
        tblModel.addColumn("Thành tiền");

        for (ChiTietHoaDon chiTiet : danhSachChiTiet) {
            tblModel.addRow(new Object[]{chiTiet.getTenSach(), chiTiet.getSoLuong(), chiTiet.getDonGia(), chiTiet.getThanhTien()});
        }

        tblChiTietHoaDon = new JTable(tblModel);
        JScrollPane scrollPane = new JScrollPane(tblChiTietHoaDon);

        // Tổng tiền
        lblTongTien = new JLabel("Tổng tiền: " + tinhTongTien(danhSachChiTiet));

        pnlMain.add(pnlHoaDonInfo, BorderLayout.NORTH);
        pnlMain.add(pnlKhachHangInfo, BorderLayout.CENTER);
        pnlMain.add(scrollPane, BorderLayout.WEST);
        pnlMain.add(lblTongTien, BorderLayout.SOUTH);

        add(pnlMain);
        setVisible(true);
    }

    private double tinhTongTien(List<ChiTietHoaDon> danhSachChiTiet) {
        double tongTien = 0;
        for (ChiTietHoaDon chiTiet : danhSachChiTiet) {
            tongTien += chiTiet.getThanhTien();
        }
        return tongTien;
    }

    // Lớp ChiTietHoaDon để đại diện cho thông tin chi tiết hóa đơn
    static class ChiTietHoaDon {
        private String tenSach;
        private int soLuong;
        private double donGia;
        private double thanhTien;

        public ChiTietHoaDon(String tenSach, int soLuong, double donGia, double thanhTien) {
            this.tenSach = tenSach;
            this.soLuong = soLuong;
            this.donGia = donGia;
            this.thanhTien = thanhTien;
        }

        public String getTenSach() {
            return tenSach;
        }

        public int getSoLuong() {
            return soLuong;
        }

        public double getDonGia() {
            return donGia;
        }

        public double getThanhTien() {
            return thanhTien;
        }
    }

    public static void main(String[] args) {
        // Dữ liệu mẫu
        List<ChiTietHoaDon> danhSachChiTiet = new ArrayList<>();
        danhSachChiTiet.add(new ChiTietHoaDon("Lập trình Java", 2, 50000, 100000));
        danhSachChiTiet.add(new ChiTietHoaDon("Cấu trúc dữ liệu", 1, 80000, 80000));

        new ChiTietHoaDonGUI("HD001", "2023-11-20", "Nguyễn Văn A", "123 Đường ABC", "0901234567", danhSachChiTiet);
    }
}
