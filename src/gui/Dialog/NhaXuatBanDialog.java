package gui.Dialog;

import bus.NhaXuatBanBUS;
import dto.NhaXuatBanDTO;
import gui.Componet.Custom.ButtonCustom;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class NhaXuatBanDialog extends JDialog implements ActionListener {

    private JLabel lblMaNXB, lblTenNXB, lblSdt, lblDiachi;
    private JTextField txtMaNXB, txtTenNXB, txtSdt, txtDiachi;
    private JPanel jpTop, jpBottom;
    private ButtonCustom btnAdd, btnUpdate, btnCancel;
    private NhaXuatBanBUS nhaXuatBanBUS;
    private NhaXuatBanDTO nhaXuatBanDTO;

    public NhaXuatBanDialog(NhaXuatBanBUS bus, JFrame owner, String title, boolean modal, String type, NhaXuatBanDTO dto) {
        super(owner, title, modal);
        this.nhaXuatBanBUS = bus;
        this.nhaXuatBanDTO = dto;
        initComponents(type);
    }

    private void initComponents(String type) {
        this.setSize(new Dimension(530, 300));  // Điều chỉnh kích thước cửa sổ
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout(0, 0));

        jpTop = new JPanel(new GridLayout(4, 2, 10, 10));  // Thay đổi thành GridLayout để sắp xếp các trường
        jpTop.setBorder(new EmptyBorder(20, 20, 20, 20));
        jpTop.setBackground(Color.WHITE);

        lblMaNXB = new JLabel("Mã Nhà Xuất Bản:");
        lblTenNXB = new JLabel("Tên Nhà Xuất Bản:");
        lblSdt = new JLabel("Số Điện Thoại:");
        lblDiachi = new JLabel("Địa Chỉ:");

        txtMaNXB = new JTextField();
        txtTenNXB = new JTextField();
        txtSdt = new JTextField();
        txtDiachi = new JTextField();

        // Không cho phép nhập mã nhà xuất bản
        txtMaNXB.setEnabled(false);

        jpTop.add(lblMaNXB);
        jpTop.add(txtMaNXB);
        jpTop.add(lblTenNXB);
        jpTop.add(txtTenNXB);
        jpTop.add(lblSdt);
        jpTop.add(txtSdt);
        jpTop.add(lblDiachi);
        jpTop.add(txtDiachi);

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
                if (nhaXuatBanDTO != null) {
                    txtMaNXB.setText(nhaXuatBanDTO.getMaNXB());
                    txtTenNXB.setText(nhaXuatBanDTO.getTenNXB());
                    txtSdt.setText(nhaXuatBanDTO.getSdt());
                    txtDiachi.setText(nhaXuatBanDTO.getDiachiNXB());
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
            NhaXuatBanDTO newNXB = new NhaXuatBanDTO();
            newNXB.setMaNXB(txtMaNXB.getText());
            newNXB.setTenNXB(txtTenNXB.getText());
            newNXB.setSdt(txtSdt.getText());
            newNXB.setDiachiNXB(txtDiachi.getText());
            String result = nhaXuatBanBUS.addNhaXuatBan(newNXB);
            JOptionPane.showMessageDialog(this, result);
            dispose();

            JOptionPane.showMessageDialog(this, "Thêm nhà xuất bản thành công!");
            dispose();
        } else if (source == btnUpdate) {
            if (nhaXuatBanDTO != null) {
                nhaXuatBanDTO.setMaNXB(txtMaNXB.getText());
                nhaXuatBanDTO.setTenNXB(txtTenNXB.getText());
                nhaXuatBanDTO.setSdt(txtSdt.getText());
                nhaXuatBanDTO.setDiachiNXB(txtDiachi.getText());
                String result = nhaXuatBanBUS.updateNhaXuatBan(nhaXuatBanDTO);
                JOptionPane.showMessageDialog(this, result);
                dispose();

                JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
                dispose();
            }
        } else if (source == btnCancel) {
            dispose();
        }
    }
}
