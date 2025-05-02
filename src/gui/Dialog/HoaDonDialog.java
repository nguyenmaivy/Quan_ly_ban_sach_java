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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class HoaDonDialog extends JDialog implements ActionListener {

    HeaderTitle titlePage;
    private JPanel pnmain, pnmain_top, pnmain_bottom, pnmain_bottom_left, pnmain_btn;
    InputForm txtMaHoaDon, txtNhanVien, txtNgayBan;
    JLabel lblTrangThai;
    JComboBox<String> cmbTrangThai;
    DefaultTableModel tblModel;
    JTable table;
    JScrollPane scrollTable;
    ButtonCustom btnHuyBo, btnXacNhan;
    private boolean isEditMode;
    HoaDonDTO hoaDonDTO;
    HoaDonBUS hoaDonBUS = new HoaDonBUS();
    NhanVienBUS nhanVienBUS = new NhanVienBUS();
    ChiTietHoaDonBUS chiTietHoaDonBUS = new ChiTietHoaDonBUS();
    ArrayList<ChiTietHoaDonDTO> chiTietHoaDonDTOs;
    private JDialog dialog; // Để chứa dialog chi tiết

    public HoaDonDialog(JFrame owner, String title, boolean modal, boolean isEdit, HoaDonDTO hdDTO) {
        super(owner, title, modal);
        this.isEditMode = isEdit;
        this.hoaDonDTO = hdDTO;
        initComponents(title);
        if (isEditMode) {
            loadHoaDonData(hdDTO);
            
        } else {
            txtMaHoaDon.setText(hoaDonBUS.generateSoHD());
            txtNgayBan.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        }
        this.setVisible(true);
    }

    private void initComponents(String title) {
        this.setSize(new Dimension(1100, 600));
        this.setLayout(new BorderLayout(0, 0));
        titlePage = new HeaderTitle(title.toUpperCase());

        pnmain = new JPanel(new BorderLayout());

        pnmain_top = new JPanel(new GridLayout(2, 3, 10, 10));
        pnmain_top.setBorder(new EmptyBorder(10, 10, 10, 10));
        txtMaHoaDon = new InputForm("Mã hóa đơn");
        txtNhanVien = new InputForm("Nhân viên bán");
        txtNgayBan = new InputForm("Ngày bán");

        lblTrangThai = new JLabel("Trạng thái:");
        cmbTrangThai = new JComboBox<>(new String[]{"Đã thanh toán", "Đã hủy"});

        txtMaHoaDon.setEditable(false);
        txtNgayBan.setEditable(false);

        pnmain_top.add(txtMaHoaDon);
        pnmain_top.add(txtNhanVien);
        pnmain_top.add(txtNgayBan);
        pnmain_top.add(new JLabel(""));
        pnmain_top.add(lblTrangThai);
        pnmain_top.add(cmbTrangThai);

        pnmain_bottom = new JPanel(new BorderLayout(5, 5));
        pnmain_bottom.setBorder(new EmptyBorder(5, 5, 5, 5));
        pnmain_bottom.setBackground(Color.WHITE);

        pnmain_bottom_left = new JPanel(new GridLayout(1, 1));
        table = new JTable();
        scrollTable = new JScrollPane();
        tblModel = new DefaultTableModel();
        String[] header = new String[]{"STT", "Mã sách", "Số lượng", "Giá bán", "Thành tiền"};
        tblModel.setColumnIdentifiers(header);
        table.setModel(tblModel);
        table.setFocusable(false);
        scrollTable.setViewportView(table);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(Object.class, centerRenderer);
        table.getColumnModel().getColumn(1).setPreferredWidth(200);
        pnmain_bottom_left.add(scrollTable);

        pnmain_bottom.add(pnmain_bottom_left, BorderLayout.CENTER);

        pnmain_btn = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pnmain_btn.setBorder(new EmptyBorder(10, 0, 10, 0));
        pnmain_btn.setBackground(Color.white);
        btnHuyBo = new ButtonCustom("Huỷ bỏ", "danger", 14);
        btnXacNhan = new ButtonCustom(isEditMode ? "Cập nhật" : "Xác nhận", "primary", 14);

        btnHuyBo.addActionListener(this);
        btnXacNhan.addActionListener(this);
        pnmain_btn.add(btnHuyBo);
        pnmain_btn.add(btnXacNhan);

        pnmain.add(pnmain_top, BorderLayout.NORTH);
        pnmain.add(pnmain_bottom, BorderLayout.CENTER);
        pnmain.add(pnmain_btn, BorderLayout.SOUTH);

        this.add(titlePage, BorderLayout.NORTH);
        this.add(pnmain, BorderLayout.CENTER);
        this.setLocationRelativeTo(null);
    }

    private void loadHoaDonData(HoaDonDTO hdDTO) {
        txtMaHoaDon.setText(hdDTO.getSoHD());
        txtNhanVien.setText(hdDTO.getTenNV());
        txtNgayBan.setText(hdDTO.getNgayBan().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        cmbTrangThai.setSelectedIndex(hdDTO.getTrangThai());
    }

    private void loadChiTietHoaDonData(String soHD) throws SQLException {
        tblModel.setRowCount(0);
        chiTietHoaDonDTOs = (ArrayList<ChiTietHoaDonDTO>) chiTietHoaDonBUS.layChiTietHoaDon(soHD);
        if (chiTietHoaDonDTOs != null && !chiTietHoaDonDTOs.isEmpty()) {
            int stt = 1;
            for (ChiTietHoaDonDTO ct : chiTietHoaDonDTOs) {
                tblModel.addRow(new Object[]{
                    stt++, ct.getMaSach(), ct.getSoLuongBan(), ct.getGiaBan(), ct.getSoLuongBan() * ct.getGiaBan()
                });
            }
        }
    }

    private HoaDonDTO getHoaDonData() {
        HoaDonDTO hd = new HoaDonDTO();
        hd.setSoHD(txtMaHoaDon.getText());
        try {
            hd.setNgayBan(LocalDate.parse(txtNgayBan.getText(), DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this, "Ngày không hợp lệ. Vui lòng nhập theo định dạng dd-MM-yyyy.");
            return null;
        }
        hd.setTrangThai(cmbTrangThai.getSelectedIndex());
        return hd;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == btnHuyBo) {
            dispose();
        } else if (source == btnXacNhan) {
            if (isEditMode) {
                capNhatHoaDon();
            } else {
                themHoaDon();
            }
        }
    }

    private void themHoaDon() {
        HoaDonDTO hd = getHoaDonData();
        if (hd == null) {
            return;
        }
        try {
            if (hoaDonBUS.themHoaDon(hd)) {
                JOptionPane.showMessageDialog(this, "Thêm hóa đơn thành công!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Thêm hóa đơn thất bại!");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
            Logger.getLogger(HoaDonDialog.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void capNhatHoaDon() {
        HoaDonDTO hd = getHoaDonData();
        if (hd == null) {
            return;
        }
        try {
            if (hoaDonBUS.capNhatHoaDon(hd)) {
                JOptionPane.showMessageDialog(this, "Cập nhật hóa đơn thành công!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật hóa đơn thất bại!");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
            Logger.getLogger(HoaDonDialog.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

