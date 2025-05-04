package gui.Dialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.Border;

public class ThemChiTietDialog extends JDialog implements ActionListener {

    private JLabel lblMaSach, lblSoLuong, lblGiaBan;
    private JTextField txtMaSach, txtSoLuong, txtGiaBan;
    private JButton btnOK, btnCancel;
    private Object[] chiTiet = null;
    private boolean isEdit = false;
    private String soHD; // Thêm trường SoHD

    public ThemChiTietDialog(JDialog owner, boolean modal, String soHD) {
        super(owner, "Thêm Chi Tiết", modal);
        this.soHD = soHD; // Nhận SoHD
        initComponents();
        setLocationRelativeTo(owner);
    }

    public ThemChiTietDialog(JDialog owner, boolean modal, Object[] data) {
        super(owner, "Sửa Chi Tiết", modal);
        this.isEdit = true;
        initComponents();
        txtMaSach.setText(data[0].toString());
        this.soHD = data[1].toString(); // Lấy SoHD từ dữ liệu sửa
        txtSoLuong.setText(data[2].toString());
        txtGiaBan.setText(data[3].toString());
        setLocationRelativeTo(owner);
    }

    private void initComponents() {
        setLayout(new GridLayout(4, 2, 10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        lblMaSach = new JLabel("Mã Sách:");
        txtMaSach = new JTextField();
        lblSoLuong = new JLabel("Số Lượng:");
        txtSoLuong = new JTextField();
        lblGiaBan = new JLabel("Giá Bán:");
        txtGiaBan = new JTextField();

        btnOK = new JButton(isEdit ? "Cập nhật" : "Thêm");
        btnCancel = new JButton("Hủy");

        btnOK.addActionListener(this);
        btnCancel.addActionListener(this);

        add(lblMaSach);
        add(txtMaSach);
        add(lblSoLuong);
        add(txtSoLuong);
        add(lblGiaBan);
        add(txtGiaBan);
        add(btnOK);
        add(btnCancel);

        pack();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnOK) {
            String maSach = txtMaSach.getText().trim();
            String soLuongStr = txtSoLuong.getText().trim();
            String giaBanStr = txtGiaBan.getText().trim();

            if (maSach.isEmpty() || soLuongStr.isEmpty() || giaBanStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                int soLuong = Integer.parseInt(soLuongStr);
                double giaBan = Double.parseDouble(giaBanStr);
                chiTiet = new Object[]{maSach, soHD, soLuong, giaBan}; // Sử dụng SoHD đã nhận
                dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Số lượng và giá bán phải là số.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == btnCancel) {
            chiTiet = null;
            dispose();
        }
    }

    public Object[] getChiTiet() {
        return chiTiet;
    }

    private void setBorder(Border createEmptyBorder) {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}