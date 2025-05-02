/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui.Dialog;

import bus.NhanVienBUS;
import com.toedter.calendar.JDateChooser;
import dao.NhanVienDAO;
import dto.NhanVienDTO;
import gui.Componet.Custom.ButtonCustom;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractButton;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.text.PlainDocument;

/**
 *
 * @author leduc
 */
public class NhanVienDialog extends JDialog implements ActionListener {

    private JTextField txtMaNV, txtTenNV, txtDiaChi, txtSDT;
    private JRadioButton rbtnNam, rbtnNu;
    private ButtonGroup genderGroup;
    private JDateChooser dateNgayVao, dateNgaySinh;
    private JPanel jpTop, jpBottom;
    private ButtonCustom btnAdd, btnUpdate, btnCancel;

    private NhanVienBUS nhanVienBUS;
    private NhanVienDTO nhanVienDTO;

    public NhanVienDialog(NhanVienBUS bus, JFrame owner, String title, boolean modal, String type, NhanVienDTO dto) {
        super(owner, title, modal);
        this.nhanVienBUS = bus;
        this.nhanVienDTO = dto;
        initComponents(type);
        txtMaNV.setEditable(false);
    }

    private void initComponents(String type) {
        this.setSize(new Dimension(550, 450));
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout(0, 0));

        jpTop = new JPanel(new GridLayout(8, 2, 10, 10));
        jpTop.setBorder(new EmptyBorder(20, 20, 20, 20));
        jpTop.setBackground(Color.WHITE);
        
        txtMaNV = new JTextField();
        txtMaNV.setEditable(false); // Không cho phép nhập mã nhân viên
        
        txtTenNV = new JTextField();
        txtDiaChi = new JTextField();
        txtSDT = new JTextField();
        dateNgayVao = new JDateChooser();
        dateNgaySinh = new JDateChooser();

        rbtnNam = new JRadioButton("Nam");
        rbtnNu = new JRadioButton("Nữ");
        genderGroup = new ButtonGroup();
        genderGroup.add(rbtnNam);
        genderGroup.add(rbtnNu);

        jpTop.add(new JLabel("Mã nhân viên:"));
        jpTop.add(txtMaNV);
        jpTop.add(new JLabel("Tên nhân viên:"));
        jpTop.add(txtTenNV);
        jpTop.add(new JLabel("Giới tính:"));

        JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        genderPanel.add(rbtnNam);
        genderPanel.add(rbtnNu);
        genderPanel.setBackground(Color.WHITE);
        jpTop.add(genderPanel);

        jpTop.add(new JLabel("Địa chỉ:"));
        jpTop.add(txtDiaChi);
        jpTop.add(new JLabel("Số điện thoại:"));
        jpTop.add(txtSDT);
        jpTop.add(new JLabel("Ngày vào làm:"));
        jpTop.add(dateNgayVao);
        jpTop.add(new JLabel("Ngày sinh:"));
        jpTop.add(dateNgaySinh);

        jpBottom = new JPanel(new FlowLayout());
        jpBottom.setBackground(Color.WHITE);
        jpBottom.setBorder(new EmptyBorder(10, 0, 10, 0));

        switch (type) {
            case "create" -> {
                btnAdd = new ButtonCustom("Thêm", "success", 14);
                btnAdd.addActionListener(this);
                jpBottom.add(btnAdd);
                
                String nextMa = nhanVienBUS.getNextMaNV();
                txtMaNV.setText(nextMa);
                txtMaNV.setEditable(false);//khong cho sua
                
            }
            case "update" -> {
                btnUpdate = new ButtonCustom("Cập nhật", "success", 14);
                btnUpdate.addActionListener(this);
                jpBottom.add(btnUpdate);
                if (nhanVienDTO != null) {
                    txtMaNV.setText(nhanVienDTO.getMaNV());
                    txtTenNV.setText(nhanVienDTO.getTenNV());
                    txtDiaChi.setText(nhanVienDTO.getDiaChi());
                    txtSDT.setText(nhanVienDTO.getSdt());
                    dateNgayVao.setDate(java.sql.Date.valueOf(nhanVienDTO.getNgayVao()));
                    dateNgaySinh.setDate(java.sql.Date.valueOf(nhanVienDTO.getNgaySinh()));
                    if (nhanVienDTO.getGioiTinh() == 1) {
                        rbtnNam.setSelected(true);
                    } else {
                        rbtnNu.setSelected(true);
                    }
                }
            }
            case "detail" -> {
                if (nhanVienDTO != null) {
                    txtMaNV.setText(nhanVienDTO.getMaNV());
                    txtTenNV.setText(nhanVienDTO.getTenNV());
                    txtDiaChi.setText(nhanVienDTO.getDiaChi());
                    txtSDT.setText(nhanVienDTO.getSdt());
                    dateNgayVao.setDate(java.sql.Date.valueOf(nhanVienDTO.getNgayVao()));
                    dateNgaySinh.setDate(java.sql.Date.valueOf(nhanVienDTO.getNgaySinh()));
                    
                    // Chọn giới tính
                    if (nhanVienDTO.getGioiTinh() == 1) {
                        rbtnNam.setSelected(true);
                    } else {
                        rbtnNu.setSelected(true);
                    }

                    // Set các field thành không chỉnh sửa
                    txtMaNV.setEditable(false);
                    txtTenNV.setEditable(false);
                    txtDiaChi.setEditable(false);
                    txtSDT.setEditable(false);
                    dateNgayVao.setEnabled(false);
                    dateNgaySinh.setEnabled(false);
                    rbtnNam.setEnabled(false);
                    rbtnNu.setEnabled(false);
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
        String tenNV = txtTenNV.getText().trim();
        String diaChi = txtDiaChi.getText().trim();
        String sdt = txtSDT.getText().trim();
        Date ngayVaoDate = dateNgayVao.getDate();
        Date ngaySinhDate = dateNgaySinh.getDate();
        int gioiTinh = rbtnNam.isSelected() ? 1 : (rbtnNu.isSelected() ? 0 : -1);

        // Kiểm tra rỗng
        if (tenNV.isEmpty() || diaChi.isEmpty() || sdt.isEmpty() || ngayVaoDate == null || ngaySinhDate == null || gioiTinh == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Kiểm tra số điện thoại hợp lệ
        if (!sdt.matches("^0\\d{9}$")) {
            JOptionPane.showMessageDialog(this, "Số điện thoại không hợp lệ! Phải có 10 chữ số và bắt đầu bằng 0.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Sinh mã nhân viên mới
        String maMoi = nhanVienBUS.getNextMaNV();

        LocalDate ngayVao = ngayVaoDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate ngaySinh = ngaySinhDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        NhanVienDTO newNV = new NhanVienDTO(maMoi, tenNV, gioiTinh, diaChi, ngayVao, sdt, ngaySinh, 1);

        String result = nhanVienBUS.addNhanVien(newNV);

        if (result.equals("Số điện thoại đã tồn tại. Vui lòng nhập số khác!")) {
            JOptionPane.showMessageDialog(this, result, "Lỗi", JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, result);
            if (result.equals("Thêm nhân viên thành công")) {
                dispose();
            }
        }

    } else if (source == btnUpdate) {
        if (nhanVienDTO != null) {
            String tenNV = txtTenNV.getText().trim();
            String diaChi = txtDiaChi.getText().trim();
            String sdt = txtSDT.getText().trim();
            Date ngayVaoDate = dateNgayVao.getDate();
            Date ngaySinhDate = dateNgaySinh.getDate();
            int gioiTinh = rbtnNam.isSelected() ? 1 : (rbtnNu.isSelected() ? 0 : -1);

            // Kiểm tra rỗng
            if (tenNV.isEmpty() || diaChi.isEmpty() || sdt.isEmpty() || ngayVaoDate == null || ngaySinhDate == null || gioiTinh == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Kiểm tra số điện thoại hợp lệ
            if (!sdt.matches("^0\\d{9}$")) {
                JOptionPane.showMessageDialog(this, "Số điện thoại không hợp lệ! Phải có 10 chữ số và bắt đầu bằng 0.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            LocalDate ngayVao = ngayVaoDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate ngaySinh = ngaySinhDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            nhanVienDTO.setTenNV(tenNV);
            nhanVienDTO.setDiaChi(diaChi);
            nhanVienDTO.setSdt(sdt);
            nhanVienDTO.setNgayVao(ngayVao);
            nhanVienDTO.setNgaySinh(ngaySinh);
            nhanVienDTO.setGioiTinh(gioiTinh);
            nhanVienDTO.setTrangThai(1);

            String result = nhanVienBUS.updateNhanVien(nhanVienDTO);

            if (result.equals("Cập nhật nhân viên thành công")) {
                JOptionPane.showMessageDialog(this, result, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, result, "Lỗi", JOptionPane.ERROR_MESSAGE);
            }

        } else {
            JOptionPane.showMessageDialog(this, "Không tìm thấy nhân viên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }

            } else if (source == btnCancel) {
                dispose();
            }
    }

}
