/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui.Dialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import bus.HoaDonBUS;
import dto.HoaDonDTO;

public class HoaDonDialog extends JDialog implements ActionListener {

    private JTextField txtSoHD, txtMaNV;
    private JFormattedTextField txtNgayBan;
    private JComboBox<Integer> cbTrangThai;
    private JButton btnThem, btnSua, btnXoa, btnHuy;

    private HoaDonBUS hoaDonBUS = new HoaDonBUS();
    private HoaDonDTO hoaDonDTO;
    private boolean isUpdate = false;

    public HoaDonDialog(Frame owner, String title, boolean modal) {
        super(owner, title, modal);
        initComponents();
        layoutComponents();
        addListeners();
        setLocationRelativeTo(owner);
    }

    private void initComponents() {
        txtSoHD = new JTextField(10);
        txtMaNV = new JTextField(10);
        txtNgayBan = new JFormattedTextField(LocalDate.now());
        cbTrangThai = new JComboBox<>(new Integer[]{0, 1}); // 0: Đã hủy, 1: Đã thanh toán
        btnThem = new JButton("Thêm");
        btnSua = new JButton("Sửa");
        btnXoa = new JButton("Xóa");
        btnHuy = new JButton("Hủy");
    }

    private void layoutComponents() {
        JPanel panel = new JPanel(new GridLayout(5, 2));
        panel.add(new JLabel("Mã hóa đơn:"));
        panel.add(txtSoHD);
        panel.add(new JLabel("Ngày bán:"));
        panel.add(txtNgayBan);
        panel.add(new JLabel("Mã nhân viên:"));
        panel.add(txtMaNV);
        panel.add(new JLabel("Trạng thái:"));
        panel.add(cbTrangThai);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnThem);
        buttonPanel.add(btnSua);
        buttonPanel.add(btnXoa);
        buttonPanel.add(btnHuy);

        setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        pack();
    }

    private void addListeners() {
        btnThem.addActionListener(this);
        btnSua.addActionListener(this);
        btnXoa.addActionListener(this);
        btnHuy.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnThem) {
            themHoaDon();
        } else if (e.getSource() == btnSua) {
            capNhatHoaDon();
        } else if (e.getSource() == btnXoa) {
            xoaHoaDon();
        } else if (e.getSource() == btnHuy) {
            dispose();
        }
    }

    public void setHoaDon(HoaDonDTO hoaDonDTO) {
        this.hoaDonDTO = hoaDonDTO;
        if (hoaDonDTO != null) {
            txtSoHD.setText(hoaDonDTO.getSoHD());
            txtNgayBan.setValue(hoaDonDTO.getNgayBan());
            txtMaNV.setText(hoaDonDTO.getMaNV());
            cbTrangThai.setSelectedItem(hoaDonDTO.getTrangThai());
            isUpdate = true;
            btnThem.setEnabled(false);
        } else {
            isUpdate = false;
            btnSua.setEnabled(false);
            btnXoa.setEnabled(false);
        }
    }

    private void themHoaDon() {
        try {
            HoaDonDTO hoaDon = new HoaDonDTO(txtSoHD.getText(), (LocalDate) txtNgayBan.getValue(), txtMaNV.getText(), (int) cbTrangThai.getSelectedItem());
            if (hoaDonBUS.themHoaDon(hoaDon)) {
                JOptionPane.showMessageDialog(this, "Thêm hóa đơn thành công!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Thêm hóa đơn thất bại!");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void capNhatHoaDon() {
        try {
            HoaDonDTO hoaDon = new HoaDonDTO(txtSoHD.getText(), (LocalDate) txtNgayBan.getValue(), txtMaNV.getText(), (int) cbTrangThai.getSelectedItem());
            if (hoaDonBUS.capNhatHoaDon(hoaDon)) {
                JOptionPane.showMessageDialog(this, "Cập nhật hóa đơn thành công!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật hóa đơn thất bại!");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void xoaHoaDon() {
        int choice = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa hóa đơn này?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            try {
                if (hoaDonBUS.xoaHoaDon(txtSoHD.getText())) {
                    JOptionPane.showMessageDialog(this, "Xóa hóa đơn thành công!");
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Xóa hóa đơn thất bại!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }
}