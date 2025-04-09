/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui.Dialog;

import bus.SachBUS;
import dto.SachDTO;
import gui.Componet.Custom.ButtonCustom;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SachDialog extends JDialog implements ActionListener {

    private JTextField txtId, txtTenSach, txtTheLoai, txtTacGia, txtNhaXuatBan,
            txtGiaBan, txtSoLuong, txtMaKho, txtHinhAnh;
    private JPanel jpTop, jpBottom;
    private ButtonCustom btnAdd, btnUpdate, btnCancel;

    private SachBUS sachBUS;
    private SachDTO sachDTO;

    public SachDialog(SachBUS bus, JFrame owner, String title, boolean modal, String type, SachDTO dto) {
        super(owner, title, modal);
        this.sachBUS = bus;
        this.sachDTO = dto;
        initComponents(type);
    }

    private void initComponents(String type) {
        this.setSize(new Dimension(550, 500));
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout(0, 0));

        jpTop = new JPanel(new GridLayout(9, 2, 10, 10));
        jpTop.setBorder(new EmptyBorder(20, 20, 20, 20));
        jpTop.setBackground(Color.WHITE);

        txtId = new JTextField();
        txtTenSach = new JTextField();
        txtTheLoai = new JTextField();
        txtTacGia = new JTextField();
        txtNhaXuatBan = new JTextField();
        txtGiaBan = new JTextField();
        txtSoLuong = new JTextField();
        txtMaKho = new JTextField();
        txtHinhAnh = new JTextField();

        jpTop.add(new JLabel("Mã sách:"));
        jpTop.add(txtId);
        jpTop.add(new JLabel("Tên sách:"));
        jpTop.add(txtTenSach);
        jpTop.add(new JLabel("Thể loại:"));
        jpTop.add(txtTheLoai);
        jpTop.add(new JLabel("Tác giả:"));
        jpTop.add(txtTacGia);
        jpTop.add(new JLabel("Nhà xuất bản:"));
        jpTop.add(txtNhaXuatBan);
        jpTop.add(new JLabel("Giá bán:"));
        jpTop.add(txtGiaBan);
        jpTop.add(new JLabel("Số lượng:"));
        jpTop.add(txtSoLuong);
        jpTop.add(new JLabel("Mã kho:"));
        jpTop.add(txtMaKho);
        jpTop.add(new JLabel("Hình ảnh (URL hoặc tên file):"));
        jpTop.add(txtHinhAnh);

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
                if (sachDTO != null) {
                    txtId.setText(sachDTO.getId());
                    txtTenSach.setText(sachDTO.getTenSach());
                    txtTheLoai.setText(sachDTO.getTheLoai());
                    txtTacGia.setText(sachDTO.getTacGia());
                    txtNhaXuatBan.setText(sachDTO.getNhaXuatBan());
                    txtGiaBan.setText(String.valueOf(sachDTO.getGiaBan()));
                    txtSoLuong.setText(String.valueOf(sachDTO.getSoLuong()));
                    txtMaKho.setText(sachDTO.getMaKho());
                    txtHinhAnh.setText(sachDTO.getHinhAnh());
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
            SachDTO newSach = readFormData();
            String result = sachBUS.addSach(newSach);
            JOptionPane.showMessageDialog(this, result);
            dispose();
        } else if (source == btnUpdate) {
            if (sachDTO != null) {
                SachDTO updated = readFormData();
                updated.setTrangThai(1); // giữ trạng thái mặc định là hoạt động
                String result = sachBUS.updateSach(updated);
                JOptionPane.showMessageDialog(this, result);
                dispose();
            }
        } else if (source == btnCancel) {
            dispose();
        }
    }

    private SachDTO readFormData() {
        SachDTO s = new SachDTO();
        s.setId(txtId.getText());
        s.setTenSach(txtTenSach.getText());
        s.setTheLoai(txtTheLoai.getText());
        s.setTacGia(txtTacGia.getText());
        s.setNhaXuatBan(txtNhaXuatBan.getText());
        s.setGiaBan(Integer.parseInt(txtGiaBan.getText()));
        s.setSoLuong(Integer.parseInt(txtSoLuong.getText()));
        s.setTrangThai(1);
        s.setMaKho(txtMaKho.getText());
        s.setHinhAnh(txtHinhAnh.getText());
        return s;
    }

}
