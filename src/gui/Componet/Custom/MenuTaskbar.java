package gui.Componet.Custom;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import dto.NhanVienDTO;
import dto.nhomQuyenDTO;
import gui.Main;
import gui.Panel.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
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
    //tasbarMenu chia thành 3 phần chính là pnlCenter, pnlTop, pnlBottom
    JPanel pnlCenter, pnlTop, pnlBottom, bar1, bar2, bar3, bar4;

    Color FontColor = new Color(96, 125, 139);
    Color DefaultColor = new Color(255, 255, 255);
    Color HowerFontColor = new Color(1, 87, 155);
    Color HowerBackgroundColor = new Color(187, 222, 251);

//    private ArrayList<nhomQuyenDTO> listQuyen;
//    nhomQuyenDTO nhomQuyenDTO;
    public NhanVienDTO nhanVienDTO;
    JFrame owner = (JFrame) SwingUtilities.getWindowAncestor(this);
    public MenuTaskbar(Main main) {
        this.main = main;
        initComponent();
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

            int index = i; // Lưu trữ chỉ số để dùng trong sự kiện
            listitem[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent evt) {
                    switch (getStringses[index][2]) {
                        case "trangchu":
                            trangChu = new TrangChu();
                            main.setPanel(trangChu);
                            break;
                        case "Sach":
                            sach = new Sach();
                            main.setPanel(sach);
                            break;
                        case "theloai":
                            theLoai = new TheLoai();
                            main.setPanel(theLoai);
                            break;
                        case "khosach":
                            khoSach = new KhoSach();
                            main.setPanel(khoSach);
                            break;
                        case "nhaphang":
                            phieuNhap = new PhieuNhap(main, nhanVienDTO);
                            main.setPanel(phieuNhap);
                            break;
                        case "xuathang":
                            hoaDon = new HoaDon();
                            main.setPanel(hoaDon);
                            break;
                        case "khachhang":
                            kháchHang = new KhachHang();
                            main.setPanel(kháchHang);
                            break;
                        case "nhaxuatban":
                            nhaXuatBan = new NhaXuatBan();
                            main.setPanel(nhaXuatBan);
                            break;
                        case "tacgia":
                            tacGia = new TacGia();
                            main.setPanel(tacGia);
                            break;
                        case "nhanvien":
                            nhanVien = new NhanVien();
                            main.setPanel(nhanVien);
                            break;
                        case "taikhoan":
                            taiKhoan = new TaiKhoan();
                            main.setPanel(taiKhoan);
                            break;
                        case "thongke":
                            thongKe = new ThongKe();
                            main.setPanel(thongKe);
                            break;
                        case "nhomquyen":
                            phanQuyen = new PhanQuyen();
                            main.setPanel(phanQuyen);
                            break;
                        case "dangxuat":
                            int input = JOptionPane.showConfirmDialog(null,
                                "Bạn có chắc chắn muốn đăng xuất?", "Đăng xuất",
                                JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
//                            if (input == 0) {
//                                Log_In login = new Log_In();
//                                main.dispose();
//                                login.setVisible(true);
//                            }
                            break;
                        default:
                            break;
                    }
                }
            });
        }
    }

}
