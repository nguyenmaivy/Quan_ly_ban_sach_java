package gui.Dialog;

import bus.HoaDonBUS;
import dto.HoaDonDTO;
import gui.Componet.Custom.ButtonCustom;
import gui.Panel.HoaDon;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class ChiTietHoaDonDialog extends JDialog implements ActionListener {

    private JLabel lblSoHD, lblNgayBan, lblMaNV, lblTrangThai;
    private JTextField txtSoHD, txtNgayBan, txtMaNV;
    private JComboBox<String> cbTrangThai;
    private JPanel jpTop, jpCenter, jpBottom, jpDetailButtons;
    private ButtonCustom btnLuu, btnHuy, btnThemCT, btnSuaCT, btnXoaCT;
    private HoaDonBUS hoaDonBUS;
    private HoaDon hoaDonPanel;
    private HoaDonDTO hoaDonToEdit;
    private boolean isEditMode = false;
    private boolean isViewMode = false;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    private JTable tblChiTiet;
    private DefaultTableModel tblModelChiTiet;
    private JScrollPane scrollPaneChiTiet;
    private List<Object[]> danhSachChiTiet = new ArrayList<>();

    public ChiTietHoaDonDialog(Frame owner, boolean modal, HoaDon hoaDonPanel, HoaDonDTO hoaDonToEdit, boolean isViewMode) {
        super(owner, isViewMode ? "Xem Chi Tiết Hóa Đơn" : "Chi Tiết Hóa Đơn", modal);
        this.hoaDonPanel = hoaDonPanel;
        this.hoaDonToEdit = hoaDonToEdit;
        this.hoaDonBUS = new HoaDonBUS();
        this.isViewMode = isViewMode;
        initComponents();
        if (hoaDonToEdit != null) {
            isEditMode = !isViewMode;
            loadHoaDonData();
            loadChiTietHoaDon();
            txtSoHD.setEditable(false);
        } else {
            try {
                txtSoHD.setText(hoaDonBUS.generateSoHD());
                txtSoHD.setEditable(false);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Lỗi khi tạo mã hóa đơn: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
        if (isViewMode) {
            disableEditing();
        }
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(owner);
        setVisible(false);
    }

    private void initComponents() {
        this.setSize(new Dimension(650, 500));
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout(0, 10));

        // Panel thông tin hóa đơn (phía trên)
        jpTop = new JPanel(new GridLayout(4, 2, 10, 10));
        jpTop.setBorder(new EmptyBorder(20, 20, 10, 20));
        jpTop.setBackground(Color.WHITE);

        lblSoHD = new JLabel("Mã Hóa Đơn:");
        lblNgayBan = new JLabel("Ngày Bán (dd-MM-yyyy):");
        lblMaNV = new JLabel("Mã Nhân Viên:");
        lblTrangThai = new JLabel("Trạng Thái:");

        txtSoHD = new JTextField();
        txtNgayBan = new JTextField();
        txtMaNV = new JTextField();
        cbTrangThai = new JComboBox<>(new String[]{"Đã hủy", "Đã thanh toán"});

        txtSoHD.setEnabled(false);

        jpTop.add(lblSoHD);
        jpTop.add(txtSoHD);
        jpTop.add(lblNgayBan);
        jpTop.add(txtNgayBan);
        jpTop.add(lblMaNV);
        jpTop.add(txtMaNV);
        jpTop.add(lblTrangThai);
        jpTop.add(cbTrangThai);

        this.add(jpTop, BorderLayout.NORTH);

        // Panel chi tiết hóa đơn (phía giữa)
        jpCenter = new JPanel(new BorderLayout());
        jpCenter.setBorder(new EmptyBorder(10, 20, 10, 20));
        jpCenter.setBackground(Color.WHITE);

        tblModelChiTiet = new DefaultTableModel(new Object[]{"Mã Sách", "Số HĐ", "Số Lượng Bán", "Giá Bán"}, 0);
        tblChiTiet = new JTable(tblModelChiTiet);
        scrollPaneChiTiet = new JScrollPane(tblChiTiet);
        jpCenter.add(scrollPaneChiTiet, BorderLayout.CENTER);

        jpDetailButtons = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        jpDetailButtons.setBackground(Color.WHITE);
        btnThemCT = new ButtonCustom("Thêm CT", "success", 12);
        btnSuaCT = new ButtonCustom("Sửa CT", "warning", 12);
        btnXoaCT = new ButtonCustom("Xóa CT", "danger", 12);

        btnThemCT.addActionListener(this);
        btnSuaCT.addActionListener(this);
        btnXoaCT.addActionListener(this);

        jpDetailButtons.add(btnThemCT);
        jpDetailButtons.add(btnSuaCT);
        jpDetailButtons.add(btnXoaCT);

        jpCenter.add(jpDetailButtons, BorderLayout.SOUTH);

        this.add(jpCenter, BorderLayout.CENTER);

        // Panel nút Lưu/Hủy (phía dưới)
        jpBottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        jpBottom.setBackground(Color.WHITE);
        jpBottom.setBorder(new EmptyBorder(10, 20, 20, 20));

        btnLuu = new ButtonCustom(isEditMode ? "Cập nhật" : "Lưu", "success", 14);
        btnHuy = new ButtonCustom("Hủy", "danger", 14);

        btnLuu.addActionListener(this);
        btnHuy.addActionListener(this);

        jpBottom.add(btnLuu);
        jpBottom.add(btnHuy);

        this.add(jpBottom, BorderLayout.SOUTH);
    }

    private void loadHoaDonData() {
        if (hoaDonToEdit != null) {
            txtSoHD.setText(hoaDonToEdit.getSoHD());
            txtNgayBan.setText(hoaDonToEdit.getNgayBan() != null ? hoaDonToEdit.getNgayBan().format(DATE_FORMATTER) : "");
            txtMaNV.setText(hoaDonToEdit.getMaNV());
            cbTrangThai.setSelectedIndex(hoaDonToEdit.getTrangThai());
        }
    }

    private void loadChiTietHoaDon() {
        if (hoaDonToEdit != null) {
            try {
                List<Object[]> chiTietList = hoaDonBUS.layChiTietHoaDon(hoaDonToEdit.getSoHD());
                tblModelChiTiet.setRowCount(0);
                danhSachChiTiet.clear();
                for (Object[] chiTiet : chiTietList) {
                    tblModelChiTiet.addRow(new Object[]{chiTiet[0], chiTiet[1], chiTiet[2], chiTiet[3]}); // Đảm bảo đủ cột
                    danhSachChiTiet.add(chiTiet);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Lỗi khi tải chi tiết hóa đơn: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            tblModelChiTiet.setRowCount(0);
            danhSachChiTiet.clear();
        }
    }

    private void disableEditing() {
        txtNgayBan.setEditable(false);
        txtMaNV.setEditable(false);
        cbTrangThai.setEnabled(false);
        btnLuu.setEnabled(false);
        btnThemCT.setEnabled(false);
        btnSuaCT.setEnabled(false);
        btnXoaCT.setEnabled(false);
    }

    private boolean validateInput() {
        if (txtNgayBan.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập ngày bán.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        try {
            LocalDate.parse(txtNgayBan.getText().trim(), DATE_FORMATTER);
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this, "Định dạng ngày không hợp lệ (dd-MM-yyyy).", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (txtMaNV.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã nhân viên.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private void saveHoaDon() {
        if (validateInput() && !isViewMode) {
            HoaDonDTO hoaDon = new HoaDonDTO();
            hoaDon.setSoHD(txtSoHD.getText()); // Lấy SoHD từ giao diện
            try {
                hoaDon.setNgayBan(LocalDate.parse(txtNgayBan.getText().trim(), DATE_FORMATTER));
            } catch (DateTimeParseException e) {
                JOptionPane.showMessageDialog(this, "Lỗi chuyển đổi ngày.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            hoaDon.setMaNV(txtMaNV.getText());
            hoaDon.setTrangThai(cbTrangThai.getSelectedIndex());

            List<Object[]> chiTietToSave = new ArrayList<>();
            for (int i = 0; i < tblModelChiTiet.getRowCount(); i++) {
                Object maSach = tblModelChiTiet.getValueAt(i, 0);
                Object soHD = txtSoHD.getText(); // Lấy SoHD từ giao diện
                Object soLuongBan = tblModelChiTiet.getValueAt(i, 2); // Lấy từ cột 2 ("Số Lượng Bán")
                Object giaBan = tblModelChiTiet.getValueAt(i, 3);   // Lấy từ cột 3 ("Giá Bán")
                if (maSach != null && soLuongBan != null && giaBan != null) {
                    chiTietToSave.add(new Object[]{maSach, soHD, soLuongBan, giaBan});
                } else {
                    JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin chi tiết hóa đơn.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            try {
                boolean success = false;
                if (isEditMode) {
                    success = hoaDonBUS.capNhatHoaDon(hoaDon);
                    if (success) {
                        // TODO: Cập nhật chi tiết hóa đơn
                        JOptionPane.showMessageDialog(this, "Cập nhật hóa đơn thành công (chưa cập nhật chi tiết).");
                    } else {
                        JOptionPane.showMessageDialog(this, "Cập nhật hóa đơn thất bại.");
                    }
                } else {
                    // Thêm hóa đơn chính
                    boolean hoaDonSuccess = hoaDonBUS.themHoaDon(hoaDon);
                    if (hoaDonSuccess) {
                        boolean chiTietSuccess = hoaDonBUS.themNhieuChiTietHoaDon(chiTietToSave);
                        if (chiTietSuccess) {
                            JOptionPane.showMessageDialog(this, "Thêm hóa đơn và chi tiết thành công.");
                            success = true;
                        } else {
                            JOptionPane.showMessageDialog(this, "Thêm hóa đơn thành công, nhưng có lỗi khi thêm chi tiết.");
                            success = true; // Coi như thành công để reload hóa đơn chính
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Thêm hóa đơn thất bại.");
                    }
                }
                if (success) {
                    hoaDonPanel.reloadData();
                    dispose();
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Lỗi khi lưu hóa đơn: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } else if (isViewMode) {
            dispose();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == btnLuu) {
            saveHoaDon();
        } else if (source == btnHuy) {
            dispose();
        } else if (source == btnThemCT) {
            String soHDHienTai = txtSoHD.getText();
            ThemChiTietDialog themCTDialog = new ThemChiTietDialog(this, true, soHDHienTai);
            themCTDialog.setVisible(true);
            Object[] newRow = themCTDialog.getChiTiet();
            if (newRow != null) {
                tblModelChiTiet.addRow(newRow);
            }
        } else if (source == btnSuaCT) {
            int selectedRow = tblChiTiet.getSelectedRow();
            if (selectedRow >= 0) {
                Object[] currentRow = new Object[tblModelChiTiet.getColumnCount()];
                for (int i = 0; i < currentRow.length; i++) {
                    currentRow[i] = tblModelChiTiet.getValueAt(selectedRow, i);
                }
                ThemChiTietDialog suaCTDialog = new ThemChiTietDialog(this, true, currentRow);
                suaCTDialog.setVisible(true);
                Object[] updatedRow = suaCTDialog.getChiTiet();
                if (updatedRow != null) {
                    for (int i = 0; i < updatedRow.length; i++) {
                        tblModelChiTiet.setValueAt(updatedRow[i], selectedRow, i);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một chi tiết để sửa.");
            }
        } else if (source == btnXoaCT) {
            int selectedRow = tblChiTiet.getSelectedRow();
            if (selectedRow >= 0) {
                tblModelChiTiet.removeRow(selectedRow);
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một chi tiết để xóa.");
            }
        }
    }

    public String getSoHD() {
        return txtSoHD.getText();
    }

    // Phương thức để thêm chi tiết từ ThemChiTietDialog
    public void addChiTiet(Object[] chiTiet) {
        tblModelChiTiet.addRow(chiTiet);
    }

    // Phương thức để lấy model của bảng chi tiết (có thể không cần thiết)
    public DefaultTableModel getChiTietTableModel() {
        return tblModelChiTiet;
    }
}