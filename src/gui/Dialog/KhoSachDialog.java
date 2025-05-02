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
        txtMaKho.setEditable(false);// <<< Dòng này giúp không cho sửa
    }

    private void initComponents(String type) {
        this.setSize(new Dimension(500, 300));
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout(0, 0));

        jpTop = new JPanel(new GridLayout(4, 2, 10, 10));
        jpTop.setBorder(new EmptyBorder(20, 20, 20, 20));
        jpTop.setBackground(Color.WHITE);

        txtMaKho = new JTextField();
        txtMaKho.setEditable(false); //khong cho phep nhap ma
        
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
                
                // Set mã kho tự sinh lên txtMakho
                String nextMa = khoSachBUS.getNextMaKho();
                txtMaKho.setText(nextMa);
                txtMaKho.setEditable(false); // Không cho sửa
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
            case "detail" -> {
                if (khoSachDTO != null) {
                    txtMaKho.setText(khoSachDTO.getMaKho());
                    txtTenKho.setText(khoSachDTO.getTenKho());
                    txtDiaChi.setText(khoSachDTO.getDiaChi());
                    txtLoai.setText(khoSachDTO.getLoai());   
                    
                    // Set các field thành không chỉnh sửa
                    txtMaKho.setEditable(false);
                    txtTenKho.setEditable(false);
                    txtDiaChi.setEditable(false);
                    txtLoai.setEditable(false);                   
                              
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
            String tenKho = txtTenKho.getText().trim();
            String diaChi = txtDiaChi.getText().trim();
            String loai = txtLoai.getText().trim();

            // Kiểm tra rỗng
            if (tenKho.isEmpty() || diaChi.isEmpty() || loai.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Tạo mã kho mới
            String maMoi = khoSachBUS.getNextMaKho();

            // Tạo DTO
            KhoSachDTO newKho = new KhoSachDTO();
            newKho.setMaKho(maMoi);
            newKho.setTenKho(tenKho);
            newKho.setDiaChi(diaChi);
            newKho.setLoai(loai);
            newKho.setTrangThai(1); // trạng thái mặc định

            // Gọi BUS thêm
            String result = khoSachBUS.addKhoSach(newKho);

            JOptionPane.showMessageDialog(this, result);
            if (result.equals("Thêm kho sách thành công")) {
                dispose();
            }

        } else if (source == btnUpdate) {
            if (khoSachDTO != null) {
                String tenKho = txtTenKho.getText().trim();
                String diaChi = txtDiaChi.getText().trim();
                String loai = txtLoai.getText().trim();

                if (tenKho.isEmpty() || diaChi.isEmpty() || loai.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Gán lại dữ liệu
                khoSachDTO.setTenKho(tenKho);
                khoSachDTO.setDiaChi(diaChi);
                khoSachDTO.setLoai(loai);
                khoSachDTO.setTrangThai(1); // luôn đảm bảo trạng thái hoạt động

                String result = khoSachBUS.updateKhoSach(khoSachDTO);

                if (result.equals("Cập nhật kho sách thành công")) {
                    JOptionPane.showMessageDialog(this, result, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, result, "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Không tìm thấy kho sách!", "Lỗi", JOptionPane.ERROR_MESSAGE);
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
