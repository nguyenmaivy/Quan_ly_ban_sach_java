
package gui.Panel;

import javax.swing.JPanel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class KhachHangGUI extends JFrame {

    private JTextField txtMaKhachHang, txtTenKhachHang, txtDiaChi, txtSoDienThoai;
    private JTable tblKhachHang;
    private DefaultTableModel tblModel;

    public KhachHangGUI() {
        setTitle("QUẢN LÝ KHÁCH HÀNG");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel pnlMain = new JPanel(new BorderLayout());

        // Thông tin khách hàng
        JPanel pnlKhachHangInfo = new JPanel(new GridLayout(4, 2));
        pnlKhachHangInfo.add(new JLabel("Mã khách hàng:"));
        txtMaKhachHang = new JTextField();
        pnlKhachHangInfo.add(txtMaKhachHang);
        pnlKhachHangInfo.add(new JLabel("Tên khách hàng:"));
        txtTenKhachHang = new JTextField();
        pnlKhachHangInfo.add(txtTenKhachHang);
        pnlKhachHangInfo.add(new JLabel("Địa chỉ:"));
        txtDiaChi = new JTextField();
        pnlKhachHangInfo.add(txtDiaChi);
        pnlKhachHangInfo.add(new JLabel("Số điện thoại:"));
        txtSoDienThoai = new JTextField();
        pnlKhachHangInfo.add(txtSoDienThoai);

        // Bảng khách hàng
        tblModel = new DefaultTableModel();
        tblModel.addColumn("Mã khách hàng");
        tblModel.addColumn("Tên khách hàng");
        tblModel.addColumn("Địa chỉ");
        tblModel.addColumn("Số điện thoại");
        tblKhachHang = new JTable(tblModel);
        JScrollPane scrollPane = new JScrollPane(tblKhachHang);

        // Nút chức năng
        JPanel pnlButtons = new JPanel(new FlowLayout());
        JButton btnThem = new JButton("Thêm");
        JButton btnSua = new JButton("Sửa");
        JButton btnXoa = new JButton("Xóa");
        JButton btnLamMoi = new JButton("Làm mới");

        btnThem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                themKhachHang();
            }
        });

        btnSua.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                suaKhachHang();
            }
        });

        btnXoa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                xoaKhachHang();
            }
        });

        btnLamMoi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lamMoi();
            }
        });

        pnlButtons.add(btnThem);
        pnlButtons.add(btnSua);
        pnlButtons.add(btnXoa);
        pnlButtons.add(btnLamMoi);

        pnlMain.add(pnlKhachHangInfo, BorderLayout.NORTH);
        pnlMain.add(scrollPane, BorderLayout.CENTER);
        pnlMain.add(pnlButtons, BorderLayout.SOUTH);

        add(pnlMain);
        setVisible(true);
    }

    private void themKhachHang() {
        String maKhachHang = txtMaKhachHang.getText();
        String tenKhachHang = txtTenKhachHang.getText();
        String diaChi = txtDiaChi.getText();
        String soDienThoai = txtSoDienThoai.getText();

        // Thêm khách hàng vào cơ sở dữ liệu hoặc danh sách khách hàng
        // ...

        // Thêm khách hàng vào bảng
        tblModel.addRow(new Object[]{maKhachHang, tenKhachHang, diaChi, soDienThoai});

        lamMoi();
    }

    private void suaKhachHang() {
        int selectedRow = tblKhachHang.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng để sửa.");
            return;
        }

        String maKhachHang = txtMaKhachHang.getText();
        String tenKhachHang = txtTenKhachHang.getText();
        String diaChi = txtDiaChi.getText();
        String soDienThoai = txtSoDienThoai.getText();

        // Cập nhật khách hàng trong cơ sở dữ liệu hoặc danh sách khách hàng
        // ...

        // Cập nhật khách hàng trong bảng
        tblModel.setValueAt(maKhachHang, selectedRow, 0);
        tblModel.setValueAt(tenKhachHang, selectedRow, 1);
        tblModel.setValueAt(diaChi, selectedRow, 2);
        tblModel.setValueAt(soDienThoai, selectedRow, 3);

        lamMoi();
    }

    private void xoaKhachHang() {
        int selectedRow = tblKhachHang.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng để xóa.");
            return;
        }

        // Xóa khách hàng khỏi cơ sở dữ liệu hoặc danh sách khách hàng
        // ...

        // Xóa khách hàng khỏi bảng
        tblModel.removeRow(selectedRow);

        lamMoi();
    }

    private void lamMoi() {
        txtMaKhachHang.setText("");
        txtTenKhachHang.setText("");
        txtDiaChi.setText("");
        txtSoDienThoai.setText("");
        tblKhachHang.clearSelection();
    }

    public static void main(String[] args) {
        new KhachHangGUI();
    }
}
