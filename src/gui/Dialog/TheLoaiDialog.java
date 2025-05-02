/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui.Dialog;

import bus.TheLoaiBUS;
import dto.TheLoaiDTO;
import gui.Componet.Custom.ButtonCustom;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TheLoaiDialog extends JDialog implements ActionListener {

    private JTextField txtMaLoai, txtTenLoai;
    private JPanel jpTop, jpBottom;
    private ButtonCustom btnAdd, btnUpdate, btnCancel;

    private TheLoaiBUS theLoaiBUS;
    private TheLoaiDTO theLoaiDTO;

    public TheLoaiDialog(TheLoaiBUS bus, JFrame owner, String title, boolean modal, String type, TheLoaiDTO dto) {
        super(owner, title, modal);
        this.theLoaiBUS = bus;
        this.theLoaiDTO = dto;
        initComponents(type);
        txtMaLoai.setEditable(false);// <<< Dòng này giúp không cho sửa
    }

    private void initComponents(String type) {
        this.setSize(new Dimension(450, 250));
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout(0, 0));

        jpTop = new JPanel(new GridLayout(2, 2, 10, 10));
        jpTop.setBorder(new EmptyBorder(20, 20, 20, 20));
        jpTop.setBackground(Color.WHITE);

        txtMaLoai = new JTextField();
        txtMaLoai.setEditable(false); // khong cho nhap ma
        
        txtTenLoai = new JTextField();

        jpTop.add(new JLabel("Mã thể loại:"));
        jpTop.add(txtMaLoai);
        jpTop.add(new JLabel("Tên thể loại:"));
        jpTop.add(txtTenLoai);

        jpBottom = new JPanel(new FlowLayout());
        jpBottom.setBackground(Color.WHITE);
        jpBottom.setBorder(new EmptyBorder(10, 0, 10, 0));

        switch (type) {
            case "create" -> {
                btnAdd = new ButtonCustom("Thêm", "success", 14);
                btnAdd.addActionListener(this);
                jpBottom.add(btnAdd);
                
                // Set mã the loai tự sinh lên txtMalaoi
                String nextMa = theLoaiBUS.getNextMaLoai();
                txtMaLoai.setText(nextMa);
                txtMaLoai.setEditable(false); // Không cho sửa
            }
            case "update" -> {
                btnUpdate = new ButtonCustom("Cập nhật", "success", 14);
                btnUpdate.addActionListener(this);
                jpBottom.add(btnUpdate);

                if (theLoaiDTO != null) {
                    txtMaLoai.setText(theLoaiDTO.getMaLoai());
                    txtTenLoai.setText(theLoaiDTO.getTenLoai());
                }
            }
            case "detail" -> {
                if (theLoaiDTO != null) {
                    txtMaLoai.setText(theLoaiDTO.getMaLoai());
                    txtTenLoai.setText(theLoaiDTO.getTenLoai()); 
                    
                    // Set các field thành không chỉnh sửa
                    txtMaLoai.setEditable(false);
                    txtTenLoai.setEditable(false);                   
                              
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
            String tenLoai = txtTenLoai.getText().trim();

            // Kiểm tra rỗng
            if (tenLoai.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập tên thể loại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Sinh mã mới
            String maMoi = theLoaiBUS.getNextMaLoai();

            // Tạo DTO
            TheLoaiDTO newTL = new TheLoaiDTO();
            newTL.setMaLoai(maMoi);
            newTL.setTenLoai(tenLoai);
            newTL.setTrangThai(1);

            // Gọi BUS thêm
            String result = theLoaiBUS.addTheLoai(newTL);

            JOptionPane.showMessageDialog(this, result);
            if (result.equals("Thêm thể loại thành công")) {
                dispose();
            }

        } else if (source == btnUpdate) {
            if (theLoaiDTO != null) {
                String tenLoai = txtTenLoai.getText().trim();

                if (tenLoai.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Vui lòng nhập tên thể loại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                theLoaiDTO.setTenLoai(tenLoai);
                theLoaiDTO.setTrangThai(1);

                String result = theLoaiBUS.updateTheLoai(theLoaiDTO);

                if (result.equals("Cập nhật thể loại thành công")) {
                    JOptionPane.showMessageDialog(this, result, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, result, "Lỗi", JOptionPane.ERROR_MESSAGE);
                }

            } else {
                JOptionPane.showMessageDialog(this, "Không tìm thấy thể loại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }

        } else if (source == btnCancel) {
            dispose();
        }
    }

}
