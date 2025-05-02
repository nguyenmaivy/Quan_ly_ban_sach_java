package gui.Componet.Custom;

import bus.NhanVienBUS;
import dto.NhanVienDTO;
import dto.TaiKhoanDTO;
import gui.LoginGUI;
import gui.Main;
import gui.Panel.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

public class MenuTaskbar extends JPanel {

    TrangChu trangChu;
    Sach sach;
    TheLoai theLoai;
    KhoSach khoSach;
    PhieuNhap phieuNhap;
    HoaDon hoaDon;
    KhachHang kháchHang;
    NhaXuatBan nhaXuatBan;
    TacGia tacGia;
    NhanVien nhanVien;
    TaiKhoan taiKhoan;
    PhanQuyen phanQuyen;
    ThongKe thongKe;
    String[][] getStringses = {
        {"Trang chủ", "home.svg", "trangchu"},
        {"Sách", "product.svg", "Sach"},
        {"Thể loại", "hinh", "theloai"},
        {"Kho sách", "area.svg", "khosach"},
        {"Phiếu nhập", "import.svg", "nhaphang"},
        {"Hóa đơn", "export.svg", "hoadon"},
        {"Khách hàng", "customer.svg", "khachhang"},
        {"Nhà xuất bản", "supplier.svg", "nhaxuatban"},
        {"Tác giả", "supplier.svg", "tacgia"},
        {"Nhân viên", "staff.svg", "nhanvien"},
        {"Tài khoản", "account.svg", "taikhoan"},
        {"Thống kê", "statistical.svg", "thongke"},
        {"Phân quyền", "permission.svg", "nhomquyen"},
        {"Đăng xuất", "log_out.svg", "dangxuat"}
    };
    Main main;
    public itemTaskbar[] listitem;

    JLabel lblTenNhomQuyen, lblUsername;
    JScrollPane scrollPane;
    JPanel pnlCenter, pnlTop, pnlBottom;

    Color FontColor = new Color(96, 125, 139);
    Color DefaultColor = new Color(255, 255, 255);
    Color HowerFontColor = new Color(1, 87, 155);
    Color HowerBackgroundColor = new Color(187, 222, 251);

    public NhanVienDTO nhanVienDTO;
    JFrame owner = (JFrame) SwingUtilities.getWindowAncestor(this);

    public MenuTaskbar(Main main, TaiKhoanDTO user) {
        this.main = main;
        initComponent();
        initPanels(user);  // Khởi tạo các panel ở đây
    }

    private void initComponent() {
        listitem = new itemTaskbar[getStringses.length];
        this.setOpaque(true);
        this.setBackground(DefaultColor);
        this.setLayout(new BorderLayout(0, 0));

        pnlCenter = new JPanel();
        pnlCenter.setPreferredSize(new Dimension(230, 600));
        pnlCenter.setBackground(DefaultColor);
        pnlCenter.setLayout(new FlowLayout(0, 0, 5));

        scrollPane = new JScrollPane(pnlCenter, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(new EmptyBorder(5, 10, 0, 10));
        this.add(scrollPane, BorderLayout.CENTER);

        for (int i = 0; i < getStringses.length; i++) {
            listitem[i] = new itemTaskbar(getStringses[i][1], getStringses[i][0]);
            pnlCenter.add(listitem[i]);

            int index = i;
            listitem[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent evt) {
                    handleMenuClick(index);
                }
            });
        }
    }

    private void initPanels(TaiKhoanDTO user) {
        NhanVienBUS nhanVienBUS = new NhanVienBUS();
        NhanVienDTO nhanVienDTO = (user != null && user.getMaNV() != null) ? nhanVienBUS.getByID(user.getMaNV()) : null;
        // Khởi tạo tất cả các panel một lần duy nhất
        trangChu = new TrangChu();
        sach = new Sach(main);
        theLoai = new TheLoai(main);
        khoSach = new KhoSach(main);
        phieuNhap = new PhieuNhap(main, nhanVienDTO);
        hoaDon = new HoaDon(main);
        kháchHang = new KhachHang(main);
        nhaXuatBan = new NhaXuatBan(main);
        tacGia = new TacGia(main);
        nhanVien = new NhanVien(main);
        taiKhoan = new TaiKhoan(main);
        thongKe = new ThongKe();
        phanQuyen = new PhanQuyen(main);
    }

    private void handleMenuClick(int index) {
        switch (getStringses[index][2]) {
            case "trangchu":
                main.setPanel(trangChu);
                break;
            case "Sach":
                main.setPanel(sach);
                break;
            case "theloai":
                main.setPanel(theLoai);
                break;
            case "khosach":
                main.setPanel(khoSach);
                break;
            case "nhaphang":
                main.setPanel(phieuNhap);
                break;
            case "hoadon":
                main.setPanel(hoaDon);
                break;
            case "khachhang":
                main.setPanel(kháchHang);
                break;
            case "nhaxuatban":
                main.setPanel(nhaXuatBan);
                break;
            case "tacgia":
                main.setPanel(tacGia);
                break;
            case "nhanvien":
                main.setPanel(nhanVien);
                break;
            case "taikhoan":
                main.setPanel(taiKhoan);
                break;
            case "thongke":
                main.setPanel(thongKe);
                break;
            case "nhomquyen":
                main.setPanel(phanQuyen);
                break;
            case "dangxuat":
                int input = JOptionPane.showConfirmDialog(null,
                        "Bạn có chắc chắn muốn đăng xuất?", "Đăng xuất",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
                if (input == JOptionPane.OK_OPTION) {
                    LoginGUI login = new LoginGUI();
                    main.dispose();
                    login.setVisible(true);
                }
                break;
            default:
                break;
        }
    }
}
