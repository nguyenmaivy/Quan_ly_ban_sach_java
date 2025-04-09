/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui.Dialog;

import bus.KhoSachBUS;
import dto.KhoSachDTO;
import gui.Componet.Custom.ButtonCustom;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class KhoSachDialog extends JDialog implements ActionListener {

    private JTextField txtMaKho, txtTenKho, txtDiaChi, txtLoai;
    private JPanel jpTop, jpBottom;
    private ButtonCustom btnAdd, btnUpdate, btnCancel;

    private KhoSachBUS khoSachBUS;
    private KhoSachDTO khoSachDTO;

    public KhoSachDialog(KhoSachBUS bus, JFrame owner, String title, boolean modal, String type, KhoSachDTO dto) {
        super(owner, title, modal);
        this.khoSachBUS = bus;
        this.khoSachDTO = dto;
        initComponents(type);
    }

    private void initComponents(String type) {
        this.setSize(new Dimension(500, 300));
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout(0, 0));

        jpTop = new JPanel(new GridLayout(4, 2, 10, 10));
        jpTop.setBorder(new EmptyBorder(20, 20, 20, 20));
        jpTop.setBackground(Color.WHITE);

        txtMaKho = new JTextField();
        txtTenKho = new JTextField();
        txtDiaChi = new JTextField();
        txtLoai = new JTextField();

        jpTop.add(new JLabel("Mã kho:"));
        jpTop.add(txtMaKho);
        jpTop.add(new JLabel("Tên kho:"));
        jpTop.add(txtTenKho);
        jpTop.add(new JLabel("Địa chỉ:"));
        jpTop.add(txtDiaChi);
        jpTop.add(new JLabel("Loại kho:"));
        jpTop.add(txtLoai);

        jpBottom = new JPanel(new FlowLayout());
        jpBottom.setBackground(Color.WHITE);
        jpBottom.setBorder(new EmptyBorder(10, 0, 10, 0));

        switch (type) {
            case "create" -> {
                btnAdd = new ButtonCustom("Thêm", "success", 14);
                btnAdd.addActionListener(this);
                jpBottom.add(btnAdd);
            }
            case "update" -> {
                btnUpdate = new ButtonCustom("Cập nhật", "success", 14);
                btnUpdate.addActionListener(this);
                jpBottom.add(btnUpdate);

                if (khoSachDTO != null) {
                    txtMaKho.setText(khoSachDTO.getMaKho());
                    txtTenKho.setText(khoSachDTO.getTenKho());
                    txtDiaChi.setText(khoSachDTO.getDiaChi());
                    txtLoai.setText(khoSachDTO.getLoai());
                }
            }
        }

        btnCancel = new ButtonCustom("Hủy", "danger", 14);
        btnCancel.addActionListener(this);
        jpBottom.add(btnCancel);

        this.add(jpTop, BorderLayout.CENTER);
        this.add(jpBottom, BorderLayout.SOUTH);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == btnAdd) {
            KhoSachDTO newKho = new KhoSachDTO();
            newKho.setMaKho(txtMaKho.getText());
            newKho.setTenKho(txtTenKho.getText());
            newKho.setDiaChi(txtDiaChi.getText());
            newKho.setLoai(txtLoai.getText());
            newKho.setTrangThai(1); // Mặc định là hoạt động

            String result = khoSachBUS.addKhoSach(newKho);
            JOptionPane.showMessageDialog(this, result);
            dispose();

        } else if (source == btnUpdate) {
            if (khoSachDTO != null) {
                khoSachDTO.setMaKho(txtMaKho.getText());
                khoSachDTO.setTenKho(txtTenKho.getText());
                khoSachDTO.setDiaChi(txtDiaChi.getText());
                khoSachDTO.setLoai(txtLoai.getText());
                khoSachDTO.setTrangThai(1); // Giữ trạng thái

                String result = khoSachBUS.updateKhoSach(khoSachDTO);
                JOptionPane.showMessageDialog(this, result);
                dispose();
            }
        } else if (source == btnCancel) {
            dispose();
        }
    }
//
//    public static void main(String[] args) {
//        KhoSachBUS bus = new KhoSachBUS(); // Giả sử BUS đã được triển khai
//        KhoSachDTO dto = new KhoSachDTO();
//        new KhoSachDialog(bus, null, "Thêm Kho Sách", true, "create", dto);
//    }
}
