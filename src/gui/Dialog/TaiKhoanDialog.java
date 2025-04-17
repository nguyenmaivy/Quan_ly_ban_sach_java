package gui.Dialog;

import bus.NhanVienBUS;
import bus.PhanQuyenBUS;
import bus.TaiKhoanBUS;
import dto.NhanVienDTO;
import dto.TaiKhoanDTO;
import dto.nhomQuyenDTO;
import gui.Componet.Custom.ButtonCustom;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.util.List;
import javax.swing.border.EmptyBorder;

public class TaiKhoanDialog extends JDialog implements ActionListener {

    private JLabel lblUsername, lblMatKhau, lblSdt, lblMaNhanVien, lblMaNhomQuyen;
    private JTextField txtUsername, txtSdt, txtMaNhanVien;
    private JComboBox<String> cbMaNhomQuyen;

    private JPasswordField txtMatKhau;
    private JPanel jpTop, jpBottom;
    private ButtonCustom btnAdd, btnUpdate, btnCancel, btnDetail;
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
        lblMatKhau = new JLabel("Mật khẩu:");
        lblSdt = new JLabel("Số điện thoại:");
        lblMaNhanVien = new JLabel("Mã nhân viên:");
        lblMaNhomQuyen = new JLabel("Mã nhóm quyền:");

        txtUsername = new JTextField();
        txtMatKhau = new JPasswordField();
        txtSdt = new JTextField();
        txtMaNhanVien = new JTextField();
        cbMaNhomQuyen = new JComboBox<>();

        // Tạo đối tượng BUS để gọi phương thức
        PhanQuyenBUS pqBUS = new PhanQuyenBUS();

        // Lấy danh sách mã nhóm quyền
        List<nhomQuyenDTO> dsNQ = pqBUS.getALL();  // Hàm phải tên đúng là getAll() nhé
        for (nhomQuyenDTO nq : dsNQ) {
            cbMaNhomQuyen.addItem(String.valueOf(nq.getMaNhomQuyen()));
        }

        jpTop.add(lblUsername);
        jpTop.add(txtUsername);
        jpTop.add(lblMatKhau);
        jpTop.add(txtMatKhau);
        jpTop.add(lblSdt);
        jpTop.add(txtSdt);
        jpTop.add(lblMaNhanVien);
        jpTop.add(txtMaNhanVien);
        jpTop.add(lblMaNhomQuyen);
        jpTop.add(cbMaNhomQuyen);

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
                    txtMatKhau.setText(taiKhoanDTO.getMatKhau());
                    txtSdt.setText(taiKhoanDTO.getSdt());
                    txtSdt.setEditable(false);
                    txtMaNhanVien.setText(taiKhoanDTO.getMaNV());
                    txtMaNhanVien.setEditable(false);
                    cbMaNhomQuyen.setSelectedItem(String.valueOf(taiKhoanDTO.getMaNhomQuyen()));

                }
            }
            case "detail" -> {
//                btnDetail = new ButtonCustom("Đóng", "info", 14);
//                btnDetail.addActionListener(this);
//                jpBottom.add(btnDetail);

                if (taiKhoanDTO != null) {
                    txtUsername.setText(taiKhoanDTO.getUseName());
                    txtMatKhau.setText(taiKhoanDTO.getMatKhau());
                    txtSdt.setText(taiKhoanDTO.getSdt());
                    txtMaNhanVien.setText(taiKhoanDTO.getMaNV());
                    cbMaNhomQuyen.setSelectedItem(String.valueOf(taiKhoanDTO.getMaNhomQuyen()));

                    // Set các field thành không chỉnh sửa
                    txtUsername.setEditable(false);
                    txtMatKhau.setEditable(false);
                    txtSdt.setEditable(false);
                    txtMaNhanVien.setEnabled(false);
                    cbMaNhomQuyen.setEnabled(false);

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

            String username = txtUsername.getText().trim();
            String matKhau = new String(txtMatKhau.getPassword()).trim();
            String sdt = txtSdt.getText().trim();
            String maNV = txtMaNhanVien.getText().trim();
            String maNhomQuyenStr = (String) cbMaNhomQuyen.getSelectedItem();

            if (username.isEmpty() || matKhau.isEmpty() || sdt.isEmpty() || maNV.isEmpty() || maNhomQuyenStr == null) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Kiểm tra số điện thoại đúng định dạng: bắt đầu bằng 0 và đủ 10 chữ số
            if (!sdt.matches("^0\\d{9}$")) {
                JOptionPane.showMessageDialog(this, "Số điện thoại phải bắt đầu bằng 0 và có đúng 10 chữ số!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Tạo đối tượng tài khoản mới
            TaiKhoanDTO newtk = new TaiKhoanDTO();
            newtk.setUseName(username);
            newtk.setMatKhau(matKhau);
            newtk.setSdt(sdt);
            newtk.setMaNV(maNV);
            newtk.setMaNhomQuyen(Integer.parseInt(maNhomQuyenStr));

            // Gọi phương thức thêm từ BUS
            String result = taiKhoanBUS.addTaiKhoan(newtk);
            JOptionPane.showMessageDialog(this, result);

            if (result.equals("Thêm tài khoản thành công")) {
                dispose(); // Đóng cửa sổ khi thêm thành công
            }

        } else if (source == btnUpdate) {
            // Kiểm tra xem đã chọn một tài khoản chưa
            if (taiKhoanDTO != null) {
                // Lấy dữ liệu từ các ô nhập
                String username = txtUsername.getText().trim();
                String matKhau = new String(txtMatKhau.getPassword()).trim();
                String maNV = txtMaNhanVien.getText().trim();
                String maNhomQuyenStr = (String) cbMaNhomQuyen.getSelectedItem();

                // Kiểm tra các trường không để trống
                if (username.isEmpty() || matKhau.isEmpty() || maNV.isEmpty() || maNhomQuyenStr == null) {
                    JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Thiếu thông tin", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                taiKhoanDTO.setUseName(username);
                taiKhoanDTO.setMatKhau(matKhau);
                taiKhoanDTO.setMaNV(maNV);
                taiKhoanDTO.setMaNhomQuyen(Integer.parseInt(maNhomQuyenStr));

                String result = taiKhoanBUS.updateTaiKhoan(taiKhoanDTO);
                JOptionPane.showMessageDialog(this, result);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Không tìm thấy tài khoản!");
            }

        } else if (source == btnCancel) {
            dispose();

        }

    }
}
