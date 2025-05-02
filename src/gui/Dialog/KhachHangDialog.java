package gui.Dialog;

import bus.KhachHangBUS;
import dto.KhachHangDTO;
import gui.Componet.Custom.ButtonCustom;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class KhachHangDialog extends JDialog implements ActionListener {

    private JLabel lblMaKH, lblTenKH, lblSdt, lblDiaChi;
    private JTextField txtMaKH, txtTenKH, txtSdt, txtDiaChi;
    private JPanel jpTop, jpBottom;
    private ButtonCustom btnAdd, btnUpdate, btnCancel;
    private KhachHangBUS khachHangBUS;
    private KhachHangDTO khachHangDTO;

    public KhachHangDialog(KhachHangBUS bus, JFrame owner, String title, boolean modal, String type, KhachHangDTO dto) {
        super(owner, title, modal);
        this.khachHangBUS = bus;
        this.khachHangDTO = dto;
        initComponents(type);
    }

    private void initComponents(String type) {
        this.setSize(new Dimension(530, 300));
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout(0, 0));

        jpTop = new JPanel(new GridLayout(4, 2, 10, 10));
        jpTop.setBorder(new EmptyBorder(20, 20, 20, 20));
        jpTop.setBackground(Color.WHITE);

        lblMaKH = new JLabel("Mã Khách Hàng:");
        lblTenKH = new JLabel("Tên Khách Hàng:");
        lblSdt = new JLabel("Số Điện Thoại:");
        lblDiaChi = new JLabel("Địa Chỉ:");

        txtMaKH = new JTextField();
        txtTenKH = new JTextField();
        txtSdt = new JTextField();
        txtDiaChi = new JTextField();

        txtMaKH.setEnabled(false);

        jpTop.add(lblMaKH);
        jpTop.add(txtMaKH);
        jpTop.add(lblTenKH);
        jpTop.add(txtTenKH);
        jpTop.add(lblSdt);
        jpTop.add(txtSdt);
        jpTop.add(lblDiaChi);
        jpTop.add(txtDiaChi);

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
                if (khachHangDTO != null) {
                    txtMaKH.setText(khachHangDTO.getMaKH());
                    txtTenKH.setText(khachHangDTO.getTenKH());
                    txtSdt.setText(khachHangDTO.getSdt());
                    txtDiaChi.setText(khachHangDTO.getDiaChi());
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
            KhachHangDTO newKH = new KhachHangDTO();
            newKH.setMaKH(txtMaKH.getText());
            newKH.setTenKH(txtTenKH.getText());
            newKH.setSdt(txtSdt.getText());
            newKH.setDiaChi(txtDiaChi.getText());
            String result = khachHangBUS.addKhachHang(newKH);
            JOptionPane.showMessageDialog(this, result);
            dispose();
        } else if (source == btnUpdate) {
            if (khachHangDTO != null) {
                khachHangDTO.setMaKH(txtMaKH.getText());
                khachHangDTO.setTenKH(txtTenKH.getText());
                khachHangDTO.setSdt(txtSdt.getText());
                khachHangDTO.setDiaChi(txtDiaChi.getText());
                String result = khachHangBUS.updateKhachHang(khachHangDTO);
                JOptionPane.showMessageDialog(this, result);
                dispose();
            }
        } else if (source == btnCancel) {
            dispose();
        }
    }
}
