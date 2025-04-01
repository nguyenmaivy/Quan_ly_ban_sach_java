package gui.Dialog;

import bus.PhanQuyenBUS;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import dao.PhanQuyenDAO;
import dto.nhomQuyenDTO;
import gui.Componet.Custom.ButtonCustom;
import gui.Panel.PhanQuyen;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class PhanQuyenDialog extends JDialog implements ActionListener {

    private JLabel lbltennhomquyen;
    private JTextField txttennhomquyen;
    private JPanel jpTop, jpLeft, jpCen, jpBottom;
    private JCheckBox[][] listcheckbox;
    private ButtonCustom btnAddnhomquyen, btnUpdatenhomquyen, btnDeletenhomquyen;
    private PhanQuyen jpPhanquyen;
    private int size1, sizehanhdong;

    String[] mahanhdong = {"view", "create", "update", "delete"};
    private nhomQuyenDTO nhomQuyenDTO;
    private PhanQuyenBUS phanQuyenBUS;
//    private PhanQuyenDAO
    int index;

    public void initComponents(String type) {
        ArrayList<nhomQuyenDTO> dsNhomQuyen = PhanQuyenDAO.getInstance().getALL();

        String[] hanhdong = {"Xem", "Tạo mới", "Cập nhật", "Xoá"};

        this.setSize(new Dimension(1000, 580));
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout(0, 0));

        // Hiển thị tên nhóm quyền
        jpTop = new JPanel(new BorderLayout(20, 10));
        jpTop.setBorder(new EmptyBorder(20, 20, 20, 20));
        jpTop.setBackground(Color.WHITE);
        lbltennhomquyen = new JLabel("Tên nhóm quyền");
        txttennhomquyen = new JTextField();
        txttennhomquyen.setPreferredSize(new Dimension(150, 35));
        jpTop.add(lbltennhomquyen, BorderLayout.WEST);
        jpTop.add(txttennhomquyen, BorderLayout.CENTER);

        // Hiển thị danh sách các chức năng
        
        // Hiển thị chức năng CRUD
        String[] chucnangs = {"hoadon", "khachhang", "thongke", "duyetdonhang",
            "phieunhap", "sach", "theloai", "khosach",
            "tacgia", "nhaxuatban", "dangxuat", "sanpham"};
        jpLeft = new JPanel(new GridLayout(chucnangs.length + 1, 1));
        jpLeft.setBackground(Color.WHITE);
        jpLeft.setBorder(new EmptyBorder(0, 20, 0, 14));
        JLabel lblDanhMuc = new JLabel("Các chức năng");
        lblDanhMuc.setFont(new Font(FlatRobotoFont.FAMILY, Font.BOLD, 15));
        jpLeft.add(lblDanhMuc);

        for (String chucnang : chucnangs) {
            JLabel lblChucNang = new JLabel(chucnang);
            jpLeft.add(lblChucNang);
        }

        size1 = chucnangs.length;
        sizehanhdong = hanhdong.length;
        jpCen = new JPanel(new GridLayout(size1 + 1, sizehanhdong));
        jpCen.setBackground(Color.WHITE);

        listcheckbox = new JCheckBox[size1][sizehanhdong];

        for (int i = 0; i < sizehanhdong; i++) {
            JLabel lblhanhdong = new JLabel(hanhdong[i]);
            lblhanhdong.setHorizontalAlignment(SwingConstants.CENTER);
            jpCen.add(lblhanhdong);
        }
        for (int i = 0; i < size1; i++) {
            for (int j = 0; j < sizehanhdong; j++) {
                listcheckbox[i][j] = new JCheckBox();
                listcheckbox[i][j].setHorizontalAlignment(SwingConstants.CENTER);
                jpCen.add(listcheckbox[i][j]);
            }
        }

        // Hiển thị nút thêm
        jpBottom = new JPanel(new FlowLayout());
        jpBottom.setBackground(Color.white);
        jpBottom.setBorder(new EmptyBorder(20, 0, 20, 0));

        switch (type) {
            case "create" -> {
                btnAddnhomquyen = new ButtonCustom("Thêm nhóm quyền", "success", 14);
                btnAddnhomquyen.addActionListener(this);
                jpBottom.add(btnAddnhomquyen);
            }
            case "update" -> {
                btnUpdatenhomquyen = new ButtonCustom("Cập nhật nhóm quyền", "success", 14);
                btnUpdatenhomquyen.addActionListener(this);
                jpBottom.add(btnUpdatenhomquyen);
                initUpdate();
            }
            case "view" -> {
                initUpdate();
            }
            default ->
                throw new AssertionError();
        }

        btnDeletenhomquyen = new ButtonCustom("Huỷ bỏ", "danger", 14);
        btnDeletenhomquyen.addActionListener(this);
        jpBottom.add(btnDeletenhomquyen);

        this.add(jpTop, BorderLayout.NORTH);
        this.add(jpLeft, BorderLayout.WEST);
        this.add(jpCen, BorderLayout.CENTER);
        this.add(jpBottom, BorderLayout.SOUTH);

        this.setVisible(true);
    }

    public ArrayList<String> getListChiTietQuyen(int manhomquyen) {
        ArrayList<String> result = new ArrayList<>();
        PhanQuyenBUS phanQuyenBUS = new PhanQuyenBUS();

        String[] hanhdong = {"view", "create", "update", "delete"};
        String[] chucnangs = {"hoadon", "khachhang", "thongke", "duyetdonhang",
            "phieunhap", "sach", "theloai", "khosach",
            "tacgia", "nhaxuatban", "dangxuat", "sanpham"};

        for (String chucnang : chucnangs) {
            for (String action : hanhdong) {
                if (phanQuyenBUS.kiemTraQuyen(manhomquyen, chucnang, action)) {
                    result.add("Nhóm quyền " + manhomquyen + " có quyền " + action + " trên " + chucnang);
                }
            }
        }

        return result;
    }

    public void initUpdate() {
        this.txttennhomquyen.setText(nhomQuyenDTO.getTenNhomQuyen());
        PhanQuyenBUS phanQuyenBUS = new PhanQuyenBUS();

        String[] hanhdong = {"view", "create", "update", "delete"};
        String[] chucnangs = {"hoadon", "khachhang", "thongke", "duyetdonhang",
            "phieunhap", "sach", "theloai", "khosach",
            "tacgia", "nhaxuatban", "dangxuat", "sanpham"};

        for (int i = 0; i < chucnangs.length; i++) {
            for (int j = 0; j < hanhdong.length; j++) {
                if (phanQuyenBUS.kiemTraQuyen(nhomQuyenDTO.getMaNhomQuyen(), chucnangs[i], hanhdong[j])) {
                    listcheckbox[i][j].setSelected(true);
                }
            }
        }
    }

    public PhanQuyenDialog(PhanQuyenBUS buss, PhanQuyen jpPhanQuyen, JFrame owner, String title, boolean modal, String type) {
        super(owner, title, modal);
        this.phanQuyenBUS = buss;
        this.jpPhanquyen = jpPhanQuyen;
        initComponents(type);
    }

    public PhanQuyenDialog(PhanQuyenBUS buss, PhanQuyen jpPhanQuyen, JFrame owner, String title, boolean modal, String type, nhomQuyenDTO nhomquyendto) {
        super(owner, title, modal);
        this.phanQuyenBUS = buss;
        this.jpPhanquyen = jpPhanQuyen;
        this.nhomQuyenDTO = nhomquyendto;
        this.index = this.phanQuyenBUS.getALL().indexOf(this.nhomQuyenDTO);
//        this.ctQuyen = ChiTietQuyenDAO.getInstance().selectAll(Integer.toString(nhomquyendto.getManhomquyen()));
        initComponents(type);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        Object source = e.getSource();

        if (source == btnAddnhomquyen) {
            String tenNhomQuyen = txttennhomquyen.getText().trim();
            if (tenNhomQuyen.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập tên nhóm quyền!");
                return;
            }
            // Gọi BUS để thêm nhóm quyền
            phanQuyenBUS.add(tenNhomQuyen, null); // Truyền null cho ctQuyen vì không dùng

            // Cập nhật bảng nhóm quyền
            this.jpPhanquyen.loadDataTable(phanQuyenBUS.getALL());

            JOptionPane.showMessageDialog(this, "Thêm nhóm quyền thành công!");
            dispose();

        } else if (source == btnUpdatenhomquyen) {
            // Cập nhật nhóm quyền
            String tenNhomQuyen = txttennhomquyen.getText().trim();
            if (tenNhomQuyen.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập tên nhóm quyền!");
                return;
            }

            // Tạo DTO mới với thông tin cập nhật
            nhomQuyenDTO nhomquyen = new nhomQuyenDTO(this.nhomQuyenDTO.getMaNhomQuyen(), tenNhomQuyen, this.nhomQuyenDTO.getTrangThai());

            // Gọi BUS để cập nhật
            phanQuyenBUS.updatePQ(nhomquyen);

            // Cập nhật lại bảng danh sách nhóm quyền
            this.jpPhanquyen.loadDataTable(phanQuyenBUS.getALL());

            JOptionPane.showMessageDialog(this, "Cập nhật nhóm quyền thành công!");
            dispose();
        } else if (source == btnDeletenhomquyen) {
            dispose(); // Đóng dialog
        }

//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
