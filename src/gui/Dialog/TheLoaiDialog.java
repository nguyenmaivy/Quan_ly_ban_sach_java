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
    }

    private void initComponents(String type) {
        this.setSize(new Dimension(450, 250));
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout(0, 0));

        jpTop = new JPanel(new GridLayout(2, 2, 10, 10));
        jpTop.setBorder(new EmptyBorder(20, 20, 20, 20));
        jpTop.setBackground(Color.WHITE);

        txtMaLoai = new JTextField();
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
            TheLoaiDTO newLoai = new TheLoaiDTO();
            newLoai.setMaLoai(txtMaLoai.getText());
            newLoai.setTenLoai(txtTenLoai.getText());
            newLoai.setTrangThai(1); // mặc định hoạt động

            String result = theLoaiBUS.addTheLoai(newLoai);
            JOptionPane.showMessageDialog(this, result);
            dispose();

        } else if (source == btnUpdate) {
            if (theLoaiDTO != null) {
                theLoaiDTO.setMaLoai(txtMaLoai.getText());
                theLoaiDTO.setTenLoai(txtTenLoai.getText());
                theLoaiDTO.setTrangThai(1); // giữ trạng thái cũ hoặc mặc định

                String result = theLoaiBUS.updateTheLoai(theLoaiDTO);
                JOptionPane.showMessageDialog(this, result);
                dispose();
            }
        } else if (source == btnCancel) {
            dispose();
        }
    }

   
}
