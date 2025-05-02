package gui.Dialog;

import bus.ChiTietHoaDonBUS;
import dto.ChiTietHoaDonDTO;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.EmptyBorder;

public class ChiTietHoaDonDialog extends JDialog implements ActionListener {

    private JLabel lblSoHD, lblMaSach, lblSoLuongBan, lblGiaBan;
    private JTextField txtSoHD, txtMaSach, txtSoLuongBan, txtGiaBan;
    private JComboBox<String> cbMaSach; // Để hiển thị danh sách mã sách có sẵn.
    private JPanel panelForm, panelButton;
    private JButton btnLuu, btnHuy;
    private ChiTietHoaDonBUS chiTietHoaDonBUS;
    private DefaultTableModel tableModelChiTiet;
    private JTable tableChiTiet;
    private JScrollPane scrollPaneTable;
    private boolean isUpdate = false;
    private String soHDToUpdate;

    public ChiTietHoaDonDialog(Frame owner, boolean modal) {
        super(owner, "Thêm Chi Tiết Hóa Đơn", modal);
        initComponents();
        layoutComponents();
        addListeners();
        this.soHDToUpdate = null;
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    public ChiTietHoaDonDialog(Frame owner, boolean modal, String soHD) {
        super(owner, "Chi Tiết Hóa Đơn", modal);
        this.isUpdate = true;
        this.soHDToUpdate = soHD;
        initComponents();
        layoutComponents();
        loadDataToUpdate(soHD);
        addListeners();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private void initComponents() {
        chiTietHoaDonBUS = new ChiTietHoaDonBUS();

        lblSoHD = new JLabel("Số Hóa Đơn:");
        lblMaSach = new JLabel("Mã Sách:");
        lblSoLuongBan = new JLabel("Số Lượng Bán:");
        lblGiaBan = new JLabel("Giá Bán:");

        txtSoHD = new JTextField(10);
        txtMaSach = new JTextField(10);
        txtSoLuongBan = new JTextField(10);
        txtGiaBan = new JTextField(10);
        cbMaSach = new JComboBox<>();

        btnLuu = new JButton("Lưu");
        btnHuy = new JButton("Hủy");

        panelForm = new JPanel(new GridBagLayout());
        panelForm.setBackground(Color.WHITE);
        panelButton = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelButton.setBackground(Color.WHITE);

        tableModelChiTiet = new DefaultTableModel();
        tableModelChiTiet.addColumn("Mã Sách");
        tableModelChiTiet.addColumn("Số Lượng Bán");
        tableModelChiTiet.addColumn("Giá Bán");
        tableChiTiet = new JTable(tableModelChiTiet);
        scrollPaneTable = new JScrollPane(tableChiTiet);
        scrollPaneTable.setPreferredSize(new Dimension(400, 150));
        tableChiTiet.setEnabled(false);
    }

    private void layoutComponents() {
        setLayout(new BorderLayout());
        panelForm.setBorder(new EmptyBorder(10, 10, 10, 10));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panelForm.add(lblSoHD, gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        panelForm.add(txtSoHD, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panelForm.add(lblMaSach, gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        panelForm.add(cbMaSach, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panelForm.add(lblSoLuongBan, gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        panelForm.add(txtSoLuongBan, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panelForm.add(lblGiaBan, gbc);
        gbc.gridx = 1;
        gbc.gridy = 3;
        panelForm.add(txtGiaBan, gbc);

        panelButton.add(btnLuu);
        panelButton.add(btnHuy);

        add(panelForm, BorderLayout.NORTH);
        add(scrollPaneTable, BorderLayout.CENTER);
        add(panelButton, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(getOwner());
    }

    private void addListeners() {
        btnLuu.addActionListener(this);
        btnHuy.addActionListener(this);
    }

    

    private void loadDataToUpdate(String soHD) {
        try {
            List<ChiTietHoaDonDTO> danhSachChiTiet = chiTietHoaDonBUS.layChiTietHoaDon(soHD);
            if (danhSachChiTiet != null && !danhSachChiTiet.isEmpty()) {
                txtSoHD.setText(danhSachChiTiet.get(0).getSoHD());
                txtSoHD.setEditable(false);

                // Load chi tiết vào bảng
                tableModelChiTiet.setRowCount(0);
                for (ChiTietHoaDonDTO ct : danhSachChiTiet) {
                    tableModelChiTiet.addRow(new Object[]{ct.getMaSach(), ct.getSoLuongBan(), ct.getGiaBan()});
                }
                //Set gia tri dau tien len form
                cbMaSach.setSelectedItem(danhSachChiTiet.get(0).getMaSach());
                txtSoLuongBan.setText(String.valueOf(danhSachChiTiet.get(0).getSoLuongBan()));
                txtGiaBan.setText(String.valueOf(danhSachChiTiet.get(0).getGiaBan()));

            } else {
                JOptionPane.showMessageDialog(this, "Không tìm thấy chi tiết hóa đơn để cập nhật!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                dispose();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(ChiTietHoaDonDialog.class.getName()).log(Level.SEVERE, null, ex);
            dispose();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnLuu) {
            saveChiTietHoaDon();
        } else if (e.getSource() == btnHuy) {
            dispose();
        }
    }

   

    private boolean validateInput() {
        if (txtSoHD.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập số hóa đơn.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (txtSoLuongBan.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập số lượng bán.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (txtGiaBan.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập giá bán.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        try {
            Integer.parseInt(txtSoLuongBan.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Số lượng bán phải là một số nguyên.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        try {
            Integer.parseInt(txtGiaBan.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Giá bán phải là một số nguyên.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    private void saveChiTietHoaDon() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}

