package gui.Dialog;

import bus.TaiKhoanBUS;
import dto.TaiKhoanDTO;
import gui.Componet.Custom.ButtonCustom;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import bus.NhanVienBUS;
import bus.PhanQuyenBUS;
import java.util.List;
import dto.NhanVienDTO;
import dto.nhomQuyenDTO;

public class TaiKhoanDialog extends JDialog implements ActionListener {
    private JLabel lblUsername, lblMatKhau, lblSdt, lblMaNhanVien, lblMaNhomQuyen;
    private JTextField txtUsername, txtSdt;
    private JComboBox<String> cbMaNhanVien, cbMaNhomQuyen;

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
        cbMaNhanVien = new JComboBox<>();
        cbMaNhomQuyen = new JComboBox<>();
        
       // Tạo đối tượng BUS để gọi phương thức
        NhanVienBUS nvBUS = new NhanVienBUS();
        PhanQuyenBUS pqBUS = new PhanQuyenBUS();

        // Lấy danh sách mã nhân viên
        List<NhanVienDTO> dsNV = nvBUS.getAllNhanVien();
        for (NhanVienDTO nv : dsNV) {
            cbMaNhanVien.addItem(nv.getMaNV());
        }

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
        jpTop.add(cbMaNhanVien);
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
                    cbMaNhanVien.setSelectedItem(taiKhoanDTO.getMaNV());                    
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
                    cbMaNhanVien.setSelectedItem(taiKhoanDTO.getMaNV());                    
                    cbMaNhomQuyen.setSelectedItem(String.valueOf(taiKhoanDTO.getMaNhomQuyen()));
                    

                    // Set các field thành không chỉnh sửa
                    txtUsername.setEditable(false);
                    txtMatKhau.setEditable(false);
                    txtSdt.setEditable(false);
                    cbMaNhanVien.setEnabled(false);
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

//   @Override
//public void actionPerformed(ActionEvent e) {
//    Object source = e.getSource();
//
//    if (source == btnAdd) {
//        // Lấy dữ liệu từ các ô nhập
//        String username = txtUsername.getText().trim();
//        String matKhau = new String(txtMatKhau.getPassword()).trim();
//        String maNV = txtMaNhanVien.getText().trim();
//        String sdt = txtSdt.getText().trim();
//        String maNhomQuyenStr = txtMaNhomQuyen.getText().trim();
//
//        // Kiểm tra rỗng
//        if (username.isEmpty() || matKhau.isEmpty() || maNV.isEmpty() || sdt.isEmpty() || maNhomQuyenStr.isEmpty()) {
//            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
//            return;
//        }
//
//        // Kiểm tra `MaNhomQuyen` có phải số không
//        int maNhomQuyen;
//        try {
//            maNhomQuyen = Integer.parseInt(maNhomQuyenStr);
//        } catch (NumberFormatException ex) {
//            JOptionPane.showMessageDialog(this, "Mã nhóm quyền phải là số nguyên hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
//            return;
//        }
//
//        // Tạo đối tượng tài khoản
//        TaiKhoanDTO newtk = new TaiKhoanDTO(username, matKhau, sdt, maNV, maNhomQuyen);
//
//        // Thêm tài khoản
//        String result = taiKhoanBUS.addTaiKhoan(newtk);
//        JOptionPane.showMessageDialog(this, result);
//        dispose();
//    } 
//    
//    else if (source == btnUpdate) {
//        if (taiKhoanDTO != null) {
//            // Lấy dữ liệu từ các ô nhập
//            String username = txtUsername.getText().trim();
//            String matKhau = new String(txtMatKhau.getPassword()).trim();
//            String maNV = txtMaNhanVien.getText().trim();
//            String sdt = txtSdt.getText().trim();
//            String maNhomQuyenStr = txtMaNhomQuyen.getText().trim();
//
//            // Kiểm tra rỗng
//            if (username.isEmpty() || matKhau.isEmpty() || maNV.isEmpty() || sdt.isEmpty() || maNhomQuyenStr.isEmpty()) {
//                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
//                return;
//            }
//
//            // Kiểm tra `MaNhomQuyen` có phải số không
//            int maNhomQuyen;
//            try {
//                maNhomQuyen = Integer.parseInt(maNhomQuyenStr);
//            } catch (NumberFormatException ex) {
//                JOptionPane.showMessageDialog(this, "Mã nhóm quyền phải là số nguyên hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
//                return;
//            }
//
//            // Cập nhật đối tượng tài khoản
//            taiKhoanDTO.setUseName(username);
//            taiKhoanDTO.setMatKhau(matKhau);
//            taiKhoanDTO.setSdt(sdt);
//            taiKhoanDTO.setMaNV(maNV);
//            taiKhoanDTO.setMaNhomQuyen(maNhomQuyen);
//
//            // Cập nhật tài khoản
//            String result = taiKhoanBUS.updateTaiKhoan(taiKhoanDTO);
//            JOptionPane.showMessageDialog(this, result);
//            dispose();
//        }
//    } 
//    
//    else if (source == btnCancel) {
//        dispose();
//    }
//}
//
//}
//   
    

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == btnAdd) {
            // Kiểm tra xem các trường nhập có rỗng không
//            if (txtUsername.getText().trim().isEmpty() || 
//                new String(txtMatKhau.getPassword()).trim().isEmpty() || 
//                txtMaNhanVien.getText().trim().isEmpty() || 
//                txtSdt.getText().trim().isEmpty() || 
//                txtMaNhomQuyen.getText().trim().isEmpty()) {
//                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
//                return; // Dừng lại nếu có trường rỗng
//            }

            String username = txtUsername.getText().trim();
            String matKhau = new String(txtMatKhau.getPassword()).trim();
            String sdt = txtSdt.getText().trim();
            String maNV = (String) cbMaNhanVien.getSelectedItem();
            String maNhomQuyenStr = (String) cbMaNhomQuyen.getSelectedItem();

            if (username.isEmpty() || matKhau.isEmpty() || sdt.isEmpty() || maNV == null || maNhomQuyenStr == null) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Kiểm tra số điện thoại đúng định dạng: bắt đầu bằng 0 và đủ 10 chữ số
             if (!sdt.matches("^0\\d{9}$")) {
                JOptionPane.showMessageDialog(this, "Số điện thoại phải bắt đầu bằng 0 và có đúng 10 chữ số!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }


//            // Tạo đối tượng tài khoản mới
//            TaiKhoanDTO newtk = new TaiKhoanDTO();
//            newtk.setUseName(txtUsername.getText().trim());
//            newtk.setMatKhau(new String(txtMatKhau.getPassword()).trim());
//            newtk.setSdt(txtSdt.getText().trim());
//            newtk.setMaNV(txtMaNhanVien.getText().trim());
//            newtk.setMaNhomQuyen(Integer.parseInt(txtMaNhomQuyen.getText().trim()));

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
                String maNV = (String) cbMaNhanVien.getSelectedItem();
                String maNhomQuyenStr = (String) cbMaNhomQuyen.getSelectedItem();

           
                // Kiểm tra các trường không để trống
                if (username.isEmpty() || matKhau.isEmpty() || maNV == null || maNhomQuyenStr == null) {
                    JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Thiếu thông tin", JOptionPane.WARNING_MESSAGE);
                    return;
                }

//                try {
//                    // Ép kiểu mã nhóm quyền
//                    int maNhomQuyen = Integer.parseInt(maNhomQuyenStr);
//
//                    // Cập nhật DTO
//                    taiKhoanDTO.setUseName(username);
//                    taiKhoanDTO.setMatKhau(matKhau);
//                    taiKhoanDTO.setMaNV(maNV);
//                    taiKhoanDTO.setMaNhomQuyen(maNhomQuyen);
//
//                    // Gọi phương thức cập nhật từ BUS
//                    String result = taiKhoanBUS.updateTaiKhoan(taiKhoanDTO);
//
//                    // Hiển thị thông báo kết quả
//                    JOptionPane.showMessageDialog(this, result);
//                    dispose(); // Đóng form sau khi hoàn thành
//
//                } catch (NumberFormatException ex) {
//                    JOptionPane.showMessageDialog(this, "Mã nhóm quyền phải là số nguyên!", "Lỗi nhập liệu", JOptionPane.ERROR_MESSAGE);
//                }

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
        
        }else if (source == btnCancel) {
            dispose();        
    
        } 

    }
}



