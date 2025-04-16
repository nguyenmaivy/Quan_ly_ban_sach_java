/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui.Dialog;

import bus.*;
import dto.*;
import gui.Componet.Custom.ButtonCustom;
import gui.Componet.Custom.HeaderTitle;
import gui.Componet.Custom.InputForm;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author MZI
 */
public final class ChiTietPhieuDialog extends JDialog implements ActionListener {

    HeaderTitle titlePage;
    private JPanel pnmain, pnmain_top, pnmain_bottom, pnmain_bottom_left, pnmain_btn;
    InputForm txtMaPhieu, txtNhanVien, txtNXB, txtThoiGian, txtKho;
    DefaultTableModel tblModel;
    JTable table;
    JScrollPane scrollTable;

    PhieuNhapDTO phieunhap;
    PhieuNhapBUS phieunhapBus = new PhieuNhapBUS();
    NhaXuatBanBUS nxbbus = new NhaXuatBanBUS();
    KhoSachBUS khoSachBUS = new KhoSachBUS();
    NhanVienBUS nvbus = new NhanVienBUS();
    SachBUS sachBUS = new SachBUS();
    NhaXuatBanBUS nhaXuatBanBUS = new NhaXuatBanBUS();
    TheLoaiBUS theLoaiBUS = new TheLoaiBUS();
    ChiTietPhieuNhapBUS chiTietPhieuNhapBUS = new ChiTietPhieuNhapBUS();
    TacGiaBUS tacGiaBUS = new TacGiaBUS();

    ButtonCustom btnHuyBo;

    ArrayList<ChiTietPhieuNhapDTO> chitietphieu;

//    HashMap<Integer, ArrayList<ChiTietSanPhamDTO>> chitietsanpham = new HashMap<>();
    public ChiTietPhieuDialog(JFrame owner, String title, boolean modal, PhieuNhapDTO phieunhapDTO) {
        super(owner, title, modal);
        this.phieunhap = phieunhapDTO;
        phieunhapBus = new PhieuNhapBUS();
        chitietphieu = chiTietPhieuNhapBUS.getAllByID(phieunhap.getSoPN());

        initComponent(title);
        initPhieuNhap();
        loadDataTableChiTietPhieu(chitietphieu);
        this.setVisible(true);
    }

    public ChiTietPhieuDialog(JFrame owner, String title, boolean modal) {
        super(owner, title, modal);
        initComponent(title);
        loadDataTableChiTietPhieu(chitietphieu);
        this.setVisible(true);
    }

    public void initPhieuNhap() {

        txtMaPhieu.setText(phieunhap.getSoPN());
        txtNhanVien.setText(nvbus.getTenNVByMa(phieunhap.getMaNV()));
        txtNXB.setText(nxbbus.getTenNXBByMa(phieunhap.getMaNXB()));
        txtKho.setText(khoSachBUS.getTenKhoByMa(phieunhap.getMaKho()));
        txtThoiGian.setText(phieunhap.getNgayNhap().toString());

    }

    public void loadDataTableChiTietPhieu(ArrayList<ChiTietPhieuNhapDTO> ctPhieu) {
        tblModel.setRowCount(0);
        if (ctPhieu == null || ctPhieu.isEmpty()) {
            System.out.println("Không có dữ liệu để load.");
            return;
        }
        int stt = 1;
        for (ChiTietPhieuNhapDTO chiTietPhieuNhapDTO : ctPhieu) {
            String maSach = chiTietPhieuNhapDTO.getMaSach();
            SachDTO sach = sachBUS.getByID(maSach);
            // Kiểm tra sách có tồn tại không
            if (sach == null) {
                System.err.println("Không tìm thấy sách với mã: " + maSach);
                continue; // Bỏ qua dòng này
            }
            String tenSach = sach.getTenSach();
            String tennxb = nhaXuatBanBUS.getTenNXBByMa(sach.getNhaXuatBan());
            String tentl = theLoaiBUS.getTenTL(sach.getTheLoai());
            String tentg = tacGiaBUS.getTenTG(sach.getTacGia());
            int dongia = chiTietPhieuNhapDTO.getGiaNhap();
            int soluong = chiTietPhieuNhapDTO.getSoLuongNhap();
            String makho = sach.getMaKho();
            tblModel.addRow(new Object[]{
                stt++, maSach, tenSach, tentl, tentg, tennxb, dongia, soluong, makho
            });

        }
    }

    public void initComponent(String title) {
        this.setSize(new Dimension(1100, 500));
        this.setLayout(new BorderLayout(0, 0));
        titlePage = new HeaderTitle(title.toUpperCase());

        pnmain = new JPanel(new BorderLayout());

        pnmain_top = new JPanel(new GridLayout(1, 4));
        txtMaPhieu = new InputForm("Mã phiếu");
        txtNhanVien = new InputForm("Nhân viên nhập");
        txtNXB = new InputForm("Nhà xuất bản");
        txtKho = new InputForm("Kho");
        txtThoiGian = new InputForm("Thời gian tạo");

        txtMaPhieu.setEditable(false);
        txtNhanVien.setEditable(false);
        txtNXB.setEditable(false);
        txtKho.setEditable(false);
        txtThoiGian.setEditable(false);

        pnmain_top.add(txtMaPhieu);
        pnmain_top.add(txtNhanVien);
        pnmain_top.add(txtNXB);
        pnmain_top.add(txtKho);
        pnmain_top.add(txtThoiGian);

        pnmain_bottom = new JPanel(new BorderLayout(5, 5));
        pnmain_bottom.setBorder(new EmptyBorder(5, 5, 5, 5));
        pnmain_bottom.setBackground(Color.WHITE);

        pnmain_bottom_left = new JPanel(new GridLayout(1, 1));
        table = new JTable();
        scrollTable = new JScrollPane();
        tblModel = new DefaultTableModel();
        String[] header = new String[]{"STT", "Mã sách", "Tên sách", "Thể loại", "Tác giả", "Nhà xuất bản", "Đơn giá", "Số lượng", "Mã kho"};
        tblModel.setColumnIdentifiers(header);
        table.setModel(tblModel);
        table.setFocusable(false);
        scrollTable.setViewportView(table);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(Object.class, centerRenderer);
        table.getColumnModel().getColumn(2).setPreferredWidth(200);
        pnmain_bottom_left.add(scrollTable);

        pnmain_bottom.add(pnmain_bottom_left, BorderLayout.CENTER);

        pnmain_btn = new JPanel(new FlowLayout());
        pnmain_btn.setBorder(new EmptyBorder(10, 0, 10, 0));
        pnmain_btn.setBackground(Color.white);
        btnHuyBo = new ButtonCustom("Huỷ bỏ", "danger", 14);

        btnHuyBo.addActionListener(this);
        pnmain_btn.add(btnHuyBo);

        pnmain.add(pnmain_top, BorderLayout.NORTH);
        pnmain.add(pnmain_bottom, BorderLayout.CENTER);
        pnmain.add(pnmain_btn, BorderLayout.SOUTH);

        this.add(titlePage, BorderLayout.NORTH);
        this.add(pnmain, BorderLayout.CENTER);
        this.setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == btnHuyBo) {
            dispose();
        }
    }
}
