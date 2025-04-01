
package gui.Panel;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class HoaDonGUI extends JFrame {

    private JTextField txtTenKhachHang, txtDiaChi, txtSoDienThoai;
    private JLabel lblMaHoaDon, lblNgayLap, lblTongTien;
    private JTable tblSach;
    private DefaultTableModel tblModel;

    public HoaDonGUI(String maHoaDon, String ngayLap, String tenKhachHang, String diaChi, String soDienThoai, List<Sach> danhSachSach) {
        setTitle("HÓA ĐƠN BÁN SÁCH");
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
        txtTenKhachHang = new JTextField(tenKhachHang);
        pnlKhachHangInfo.add(txtTenKhachHang);
        pnlKhachHangInfo.add(new JLabel("Địa chỉ:"));
        txtDiaChi = new JTextField(diaChi);
        pnlKhachHangInfo.add(txtDiaChi);
        pnlKhachHangInfo.add(new JLabel("Số điện thoại:"));
        txtSoDienThoai = new JTextField(soDienThoai);
        pnlKhachHangInfo.add(txtSoDienThoai);

        // Danh sách sách
        tblModel = new DefaultTableModel();
        tblModel.addColumn("Tên sách");
        tblModel.addColumn("Số lượng");
        tblModel.addColumn("Đơn giá");
        tblModel.addColumn("Thành tiền");

        for (Sach sach : danhSachSach) {
            tblModel.addRow(new Object[]{sach.getTenSach(), sach.getSoLuong(), sach.getDonGia(), sach.getSoLuong() * sach.getDonGia()});
        }

        tblSach = new JTable(tblModel);
        JScrollPane scrollPane = new JScrollPane(tblSach);

        // Tổng tiền
        lblTongTien = new JLabel("Tổng tiền: " + tinhTongTien(danhSachSach));

        // Nút chức năng
        JPanel pnlButtons = new JPanel(new FlowLayout());
        JButton btnInHoaDon = new JButton("In hóa đơn");
        JButton btnLuuHoaDon = new JButton("Lưu hóa đơn");
        JButton btnThoat = new JButton("Thoát");

        btnThoat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        pnlButtons.add(btnInHoaDon);
        pnlButtons.add(btnLuuHoaDon);
        pnlButtons.add(btnThoat);

        pnlMain.add(pnlHoaDonInfo, BorderLayout.NORTH);
        pnlMain.add(pnlKhachHangInfo, BorderLayout.CENTER);
        pnlMain.add(scrollPane, BorderLayout.WEST);
        pnlMain.add(lblTongTien, BorderLayout.SOUTH);
        pnlMain.add(pnlButtons, BorderLayout.EAST);

        add(pnlMain);
        setVisible(true);
    }

    private double tinhTongTien(List<Sach> danhSachSach) {
        double tongTien = 0;
        for (Sach sach : danhSachSach) {
            tongTien += sach.getSoLuong() * sach.getDonGia();
        }
        return tongTien;
    }

    // Lớp Sach để đại diện cho thông tin sách
    static class Sach {
        private String tenSach;
        private int soLuong;
        private double donGia;

        public Sach(String tenSach, int soLuong, double donGia) {
            this.tenSach = tenSach;
            this.soLuong = soLuong;
            this.donGia = donGia;
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
    }

    public static void main(String[] args) {
        // Dữ liệu mẫu
        List<Sach> danhSachSach = new ArrayList<>();
        danhSachSach.add(new Sach("Lập trình Java", 2, 50000));
        danhSachSach.add(new Sach("Cấu trúc dữ liệu", 1, 80000));

        new HoaDonGUI("HD001", "2023-11-20", "Nguyễn Văn A", "123 Đường ABC", "0901234567", danhSachSach);
    }
}
