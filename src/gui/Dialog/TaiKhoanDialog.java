package gui.Dialog;

import bus.TaiKhoanBUS;
import dto.TaiKhoanDTO;
import gui.Componet.Custom.ButtonCustom;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class TaiKhoanDialog extends JDialog implements ActionListener {

    private JLabel lblUsername, lblPassword, lblPhone, lblMaNhanVien, lblMaNhomQuyen;
    private JTextField txtUsername, txtPhone, txtMaNhanVien, txtMaNhomQuyen;
    private JPasswordField txtPassword;
    private JPanel jpTop, jpBottom;
    private ButtonCustom btnAdd, btnUpdate, btnCancel;
    private TaiKhoanBUS taiKhoanBUS;
    private TaiKhoanDTO taiKhoanDTO;

    public TaiKhoanDialog(TaiKhoanBUS bus, JFrame owner, String title, boolean modal, String type, TaiKhoanDTO dto) {
        super(owner, title, modal);
        this.taiKhoanBUS = bus;
        this.taiKhoanDTO = dto;
        initComponents(type);
    }

    private void initComponents(String type) {
        this.setSize(new Dimension(530, 350));
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout(0, 0));

        jpTop = new JPanel(new GridLayout(5, 2, 10, 10));
        jpTop.setBorder(new EmptyBorder(20, 20, 20, 20));
        jpTop.setBackground(Color.WHITE);

        lblUsername = new JLabel("Tên đăng nhập:");
        lblPassword = new JLabel("Mật khẩu:");
        lblPhone = new JLabel("Số điện thoại:");
        lblMaNhanVien = new JLabel("Mã nhân viên:");
        lblMaNhomQuyen = new JLabel("Mã nhóm quyền:");

        txtUsername = new JTextField();
        txtPassword = new JPasswordField();
        txtPhone = new JTextField();
        txtMaNhanVien = new JTextField();
        txtMaNhomQuyen = new JTextField();

        jpTop.add(lblUsername);
        jpTop.add(txtUsername);
        jpTop.add(lblPassword);
        jpTop.add(txtPassword);
        jpTop.add(lblPhone);
        jpTop.add(txtPhone);
        jpTop.add(lblMaNhanVien);
        jpTop.add(txtMaNhanVien);
        jpTop.add(lblMaNhomQuyen);
        jpTop.add(txtMaNhomQuyen);

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
                if (taiKhoanDTO != null) {
                    txtUsername.setText(taiKhoanDTO.getUseName());
                    txtPassword.setText(taiKhoanDTO.getMatKhau());
                    txtPhone.setText(taiKhoanDTO.getSdt());
                    txtMaNhanVien.setText(taiKhoanDTO.getMaNV());
                    txtMaNhomQuyen.setText(String.valueOf(taiKhoanDTO.getMaNhomQuyen()));

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
            TaiKhoanDTO newAccount = new TaiKhoanDTO();
            newAccount.setUseName(txtUsername.getText());
            newAccount.setMatKhau(new String(txtPassword.getPassword()));
            newAccount.setSdt(txtPhone.getText());
            newAccount.setMaNV(txtMaNhanVien.getText());
            newAccount.setMaNhomQuyen(Integer.parseInt(txtMaNhomQuyen.getText()));

            String result = taiKhoanBUS.addTaiKhoan(newAccount);
            JOptionPane.showMessageDialog(this, result);
            dispose();
        } else if (source == btnUpdate) {
            if (taiKhoanDTO != null) {
                taiKhoanDTO.setUseName(txtUsername.getText());
                taiKhoanDTO.setMatKhau(new String(txtPassword.getPassword()));
                taiKhoanDTO.setSdt(txtPhone.getText());
                taiKhoanDTO.setMaNV(txtMaNhanVien.getText());
                taiKhoanDTO.setMaNhomQuyen(Integer.parseInt(txtMaNhomQuyen.getText()));
                String result = taiKhoanBUS.updateTaiKhoan(taiKhoanDTO);
                JOptionPane.showMessageDialog(this, result);
                dispose();
            }
        } else if (source == btnCancel) {
            dispose();
        }
    }

//    public static void main(String[] args) {
//        // Khởi tạo các đối tượng giả lập
//        TaiKhoanBUS taiKhoanBUS = new TaiKhoanBUS(); // Giả sử TaiKhoanBUS đã được triển khai
//        TaiKhoanDTO taiKhoanDTO = new TaiKhoanDTO();
//
//        // Tạo cửa sổ tài khoản (có thể thay đổi 'create' thành 'update' để cập nhật)
//        TaiKhoanDialog dialog = new TaiKhoanDialog(taiKhoanBUS, null, "Tạo Tài Khoản", true, "create", taiKhoanDTO);
//
//        // Hiển thị giao diện
//        dialog.setVisible(true);
//    }
}
