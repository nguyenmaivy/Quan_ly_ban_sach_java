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
    }

    private void initComponents(String type) {
        this.setSize(new Dimension(550, 450));
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout(0, 0));

        jpTop = new JPanel(new GridLayout(8, 2, 10, 10));
        jpTop.setBorder(new EmptyBorder(20, 20, 20, 20));
        jpTop.setBackground(Color.WHITE);

        txtMaNV = new JTextField();
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
            NhanVienDTO newNV = readFormData();
            String result = nhanVienBUS.addNhanVien(newNV);
            JOptionPane.showMessageDialog(this, result);
            dispose();
        } else if (source == btnUpdate) {
            if (nhanVienDTO != null) {
                NhanVienDTO updatedNV = readFormData();
                updatedNV.setTrangThai(1); // giữ trạng thái hoạt động
                String result = nhanVienBUS.updateNhanVien(updatedNV);
                JOptionPane.showMessageDialog(this, result);
                dispose();
            }
        } else if (source == btnCancel) {
            dispose();
        }
    }

    private NhanVienDTO readFormData() {
        String maNV = txtMaNV.getText();
        String tenNV = txtTenNV.getText();
        int gioiTinh = rbtnNam.isSelected() ? 1 : 0;
        String diaChi = txtDiaChi.getText();
        String sdt = txtSDT.getText();
        Date ngayVaoDate = dateNgayVao.getDate();
        Date ngaySinhDate = dateNgaySinh.getDate();
        LocalDate ngayVao = ngayVaoDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate ngaySinh = ngaySinhDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return new NhanVienDTO(maNV, tenNV, gioiTinh, diaChi, ngayVao, sdt, ngaySinh, 1);
    }

//    public static void main(String[] args) {
//        NhanVienBUS nhanVienBUS = new NhanVienBUS();
//        NhanVienDTO nhanVienDTO = new NhanVienDTO(); // mẫu DTO
//        new NhanVienDialog(nhanVienBUS, null, "Thêm Nhân Viên", true, "create", nhanVienDTO);
//    }
}
