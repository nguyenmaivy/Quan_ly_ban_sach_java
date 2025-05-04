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
import java.io.IOException;

public class SachDialog extends JDialog implements ActionListener {

    private JTextField txtId, txtTenSach, txtTheLoai, txtTacGia, txtNhaXuatBan,
            txtGiaBan, txtSoLuong, txtMaKho, txtHinhAnh;
    private JPanel jpTop, jpBottom;
    private ButtonCustom btnAdd, btnUpdate, btnCancel;
    private JButton btnChonAnh;
    private SachBUS sachBUS;
    private SachDTO sachDTO;

    public SachDialog(SachBUS bus, JFrame owner, String title, boolean modal, String type, SachDTO dto) {
        super(owner, title, modal);
        this.sachBUS = bus;
        this.sachDTO = dto;
        initComponents(type);
        txtId.setEditable(false); // <<< Dòng này giúp không cho sửa mã sach
    }

    private void initComponents(String type) {
        this.setSize(new Dimension(550, 500));
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout(0, 0));

        jpTop = new JPanel(new GridLayout(9, 2, 10, 10));
        jpTop.setBorder(new EmptyBorder(20, 20, 20, 20));
        jpTop.setBackground(Color.WHITE);

        txtId = new JTextField();
        txtId.setEditable(false);// Không cho phép nhập mã sach
        
        txtTenSach = new JTextField();
        txtTheLoai = new JTextField();
        txtTacGia = new JTextField();
        txtNhaXuatBan = new JTextField();
        txtGiaBan = new JTextField();
        txtSoLuong = new JTextField();
        txtMaKho = new JTextField();
        txtHinhAnh = new JTextField();
        btnChonAnh = new JButton("Chọn ảnh");
        btnChonAnh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Hình ảnh", "jpg", "png", "jpeg", "webp"));
                int result = fileChooser.showOpenDialog(null);

                if (result == JFileChooser.APPROVE_OPTION) {
                    java.io.File selectedFile = fileChooser.getSelectedFile();
                    String fileName = selectedFile.getName();
                    txtHinhAnh.setText(fileName);  // Lưu tên file ảnh

                    // Sao chép file vào thư mục src/images/
                    try {
                        java.nio.file.Path destinationDir = java.nio.file.Paths.get("src/images/");
                        java.nio.file.Files.createDirectories(destinationDir); // Tạo folder nếu chưa có

                        java.nio.file.Path destination = destinationDir.resolve(fileName);
                        java.nio.file.Files.copy(selectedFile.toPath(), destination, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
                    } catch (IOException ex) {
                        ex.printStackTrace(); // Log lỗi ra console để debug dễ hơn
                        JOptionPane.showMessageDialog(null, "Lỗi khi sao chép ảnh vào thư mục: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

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
        jpTop.add(new JLabel("Hình ảnh (chọn từ máy):"));
        JPanel panelHinhAnh = new JPanel(new BorderLayout());
        panelHinhAnh.add(txtHinhAnh, BorderLayout.CENTER);
        panelHinhAnh.add(btnChonAnh, BorderLayout.EAST);
        jpTop.add(panelHinhAnh);

        jpBottom = new JPanel(new FlowLayout());
        jpBottom.setBackground(Color.WHITE);
        jpBottom.setBorder(new EmptyBorder(10, 0, 10, 0));

        switch (type) {
            case "create" -> {
                btnAdd = new ButtonCustom("Thêm", "success", 14);
                btnAdd.addActionListener(this);
                jpBottom.add(btnAdd);
                
                // Set mã nhà xuất bản tự sinh lên txtMaNXB
                String nextMa = sachBUS.getNextMaSach();
                txtId.setText(nextMa);
                txtId.setEditable(false); // Không cho sửa
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
            case "detail" -> {
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

                    // Set các field thành không chỉnh sửa
                    txtId.setEditable(false);
                    txtTenSach.setEditable(false);
                    txtTheLoai.setEditable(false);
                    txtTacGia.setEditable(false);
                    txtNhaXuatBan.setEditable(false);
                    txtGiaBan.setEditable(false);
                    txtSoLuong.setEditable(false);
                    txtMaKho.setEditable(false);
                    txtHinhAnh.setEditable(false);
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
            String tenSach = txtTenSach.getText().trim();
            String theLoai = txtTheLoai.getText().trim();
            String tacGia = txtTacGia.getText().trim();
            String nhaXuatBan = txtNhaXuatBan.getText().trim();
            String maKho = txtMaKho.getText().trim();
            String hinhAnh = txtHinhAnh.getText().trim();

            int giaBan, soLuong;

            // Kiểm tra rỗng
            if (tenSach.isEmpty() || theLoai.isEmpty() || tacGia.isEmpty()
                    || nhaXuatBan.isEmpty() || maKho.isEmpty() || hinhAnh.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                giaBan = Integer.parseInt(txtGiaBan.getText().trim());
                soLuong = Integer.parseInt(txtSoLuong.getText().trim());

                if (giaBan <= 0 || soLuong <= 0) {
                    JOptionPane.showMessageDialog(this, "Giá bán và số lượng phải lớn hơn 0!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Giá bán và số lượng phải là số hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Tạo mã sách mới
            String maMoi = sachBUS.getNextMaSach();

            // Tạo đối tượng sách mới
            SachDTO newSach = new SachDTO(maMoi, tenSach, theLoai, tacGia, nhaXuatBan, giaBan, soLuong, 1, maKho, hinhAnh);

            String result = sachBUS.addSach(newSach);

            JOptionPane.showMessageDialog(this, result);

            if (result.equals("Thêm sách thành công")) {
                dispose();
            }

        } else if (source == btnUpdate) {
            if (sachDTO != null) {
                String tenSach = txtTenSach.getText().trim();
                String theLoai = txtTheLoai.getText().trim();
                String tacGia = txtTacGia.getText().trim();
                String nhaXuatBan = txtNhaXuatBan.getText().trim();
                String maKho = txtMaKho.getText().trim();
                String hinhAnh = txtHinhAnh.getText().trim();

                int giaBan, soLuong;

                // Kiểm tra rỗng
                if (tenSach.isEmpty() || theLoai.isEmpty() || tacGia.isEmpty()
                        || nhaXuatBan.isEmpty() || maKho.isEmpty() || hinhAnh.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    giaBan = Integer.parseInt(txtGiaBan.getText().trim());
                    soLuong = Integer.parseInt(txtSoLuong.getText().trim());

                    if (giaBan <= 0 || soLuong <= 0) {
                        JOptionPane.showMessageDialog(this, "Giá bán và số lượng phải lớn hơn 0!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Giá bán và số lượng phải là số hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Cập nhật đối tượng
                sachDTO.setTenSach(tenSach);
                sachDTO.setTheLoai(theLoai);
                sachDTO.setTacGia(tacGia);
                sachDTO.setNhaXuatBan(nhaXuatBan);
                sachDTO.setMaKho(maKho);
                sachDTO.setHinhAnh(hinhAnh);
                sachDTO.setGiaBan(giaBan);
                sachDTO.setSoLuong(soLuong);
                sachDTO.setTrangThai(1);

                String result = sachBUS.updateSach(sachDTO);

                if (result.equals("Cập nhật sách thành công!")) {
                    JOptionPane.showMessageDialog(this, result, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, result, "Lỗi", JOptionPane.ERROR_MESSAGE);
                }

            } else {
                JOptionPane.showMessageDialog(this, "Không tìm thấy sách!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }

        } else if (source == btnCancel) {
            dispose();
        }
    }


}
