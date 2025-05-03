package gui.Panel;

import javax.swing.JPanel;
import bus.NhanVienBUS;
import gui.Componet.Custom.IntegratedSearch;
import gui.Componet.Custom.MainFunction;
import gui.Componet.Custom.PanelBorderRadius;
import gui.Main;
import dto.NhanVienDTO;
import gui.Dialog.NhanVienDialog;
import helper.JTableExporter;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class NhanVien extends JPanel implements ActionListener {

    PanelBorderRadius main, functionBar;
    JPanel contentCenter;
    JTable tblNhanVien;
    JScrollPane scrollTable;
    MainFunction mainFunction;
    IntegratedSearch search;
    DefaultTableModel tbModel;

    Main m;
    Color BackgroundColor = new Color(240, 247, 250);

    public NhanVien(Main m) {
        this.m = m;
        initComponent();
        loadDataTable();
    }

    private void initComponent() {
        this.setBackground(BackgroundColor);
        this.setLayout(new GridLayout(1, 1));
        this.setBorder(new EmptyBorder(10, 10, 10, 10));
        this.setOpaque(true);

        contentCenter = new JPanel();
        contentCenter.setPreferredSize(new Dimension(1100, 600));
        contentCenter.setBackground(BackgroundColor);
        contentCenter.setLayout(new BorderLayout(10, 20));
        this.add(contentCenter);

        functionBar = new PanelBorderRadius();
        functionBar.setPreferredSize(new Dimension(0, 100));
        functionBar.setLayout(new GridLayout(1, 2, 50, 0));
        functionBar.setBorder(new EmptyBorder(10, 10, 10, 10));

        String[] acStrings = {"create", "update", "delete", "detail", "export"};
        mainFunction = new MainFunction(0, "nhanvien", acStrings);
        for (String ac : acStrings) {
            mainFunction.btn.get(ac).addActionListener(this);
        }
        functionBar.add(mainFunction);

        search = new IntegratedSearch(new String[]{"Tất cả", "Mã Nhân viên", "Họ tên", "Địa chỉ", "Số điện thoại"});
        search.txtSearchForm.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                searchData();
            }
        });

        search.cbxChoose.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {
                searchData();
            }
        });
        functionBar.add(search);

        contentCenter.add(functionBar, BorderLayout.NORTH);

        main = new PanelBorderRadius();
        BoxLayout boxLayout = new BoxLayout(main, BoxLayout.Y_AXIS);
        main.setLayout(boxLayout);
        contentCenter.add(main, BorderLayout.CENTER);

        tblNhanVien = new JTable();
        tblNhanVien.setDefaultEditor(Object.class, null);
        scrollTable = new JScrollPane();
        tbModel = new DefaultTableModel();
        String[] header = new String[]{"Mã nhân viên", "Họ tên", "Giới tính", "Địa chỉ", "Ngày vào", "Số điện thoại", "Ngày sinh"};
        tbModel.setColumnIdentifiers(header);
        tblNhanVien.setModel(tbModel);
        scrollTable.setViewportView(tblNhanVien);

        DefaultTableCellRenderer cenCellRenderer = new DefaultTableCellRenderer();
        cenCellRenderer.setHorizontalAlignment(JLabel.CENTER);
        TableColumnModel columnModel = tblNhanVien.getColumnModel();
        for (int i = 0; i < columnModel.getColumnCount(); i++) {
            columnModel.getColumn(i).setCellRenderer(cenCellRenderer);
        }

        tblNhanVien.setFocusable(false);
        main.add(scrollTable);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        NhanVienBUS nvBUS = new NhanVienBUS();

        if (source == mainFunction.btn.get("create")) {
            NhanVienDialog dialog = new NhanVienDialog(nvBUS,
                    (JFrame) SwingUtilities.getWindowAncestor(this),
                    "Thêm Nhân Viên", true, "create", null);
            loadDataTable();
        } else if (source == mainFunction.btn.get("update")) {
            int selectedRow = tblNhanVien.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một nhân viên để cập nhật!");
                return;
            }

            String maNV = tbModel.getValueAt(selectedRow, 0).toString();
            NhanVienDTO nv = nvBUS.getByID(maNV);

            if (nv != null) {
                NhanVienDialog dialog = new NhanVienDialog(nvBUS,
                        (JFrame) SwingUtilities.getWindowAncestor(this),
                        "Cập nhật Nhân Viên", true, "update", nv);
                loadDataTable();
            } else {
                JOptionPane.showMessageDialog(this, "Không tìm thấy nhân viên!");
            }
        } else if (source == mainFunction.btn.get("delete")) {
            int selectedRow = tblNhanVien.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một nhân viên để xóa!");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa nhân viên này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                String maNV = tbModel.getValueAt(selectedRow, 0).toString();
                String result = nvBUS.deleteNhanVien(maNV);
                JOptionPane.showMessageDialog(this, result);
                loadDataTable();
            }
        } else if (source == mainFunction.btn.get("detail")) {
            int selectedRow = tblNhanVien.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một nhân viên để xem chi tiết ! ");
                return;
            }
            String manv = tbModel.getValueAt(selectedRow, 0).toString();
            NhanVienDTO nhanVien = nvBUS.getByID(manv);

            if (nhanVien != null) {
                NhanVienDialog dialog = new NhanVienDialog(nvBUS, (JFrame) SwingUtilities.getWindowAncestor(this), "Chi tiết Nhân Viên", true, "detail", nhanVien);

            } else {
                JOptionPane.showMessageDialog(this, "Không tìm thấy nhân viên!");
            }
        } else if (source == mainFunction.btn.get("export")) {
            boolean success = JTableExporter.exportJTableToExcel(tblNhanVien);
            if (success) {
                JOptionPane.showMessageDialog(this, "Xuất file Excel thành công!");
            }
        }
    }

    private void loadDataTable() {
        NhanVienBUS nvBUS = new NhanVienBUS();
        ArrayList<NhanVienDTO> nvList = nvBUS.getAllNhanVien();

        tbModel.setRowCount(0);
        for (NhanVienDTO nv : nvList) {
            tbModel.addRow(new Object[]{
                nv.getMaNV(),
                nv.getTenNV(),
                nv.getGioiTinh(),
                nv.getDiaChi(),
                nv.getNgayVao(),
                nv.getSdt(),
                nv.getNgaySinh()
            });
        }
    }

    private void searchData() {
        String keyword = search.txtSearchForm.getText().trim().toLowerCase();
        String category = search.cbxChoose.getSelectedItem().toString();

        NhanVienBUS nvBUS = new NhanVienBUS();
        ArrayList<NhanVienDTO> list = nvBUS.getAllNhanVien();

        tbModel.setRowCount(0); // Xóa dữ liệu cũ trên bảng

        for (NhanVienDTO nv : list) {
            String ma = (nv.getMaNV() != null) ? nv.getMaNV().toLowerCase() : "";
            String ten = (nv.getTenNV() != null) ? nv.getTenNV().toLowerCase() : "";
            String sdt = (nv.getSdt() != null) ? nv.getSdt().toLowerCase() : "";
            String diachi = (nv.getDiaChi() != null) ? nv.getDiaChi().toLowerCase() : "";

            boolean match = false;

            switch (category) {
                case "Tất cả":
                    match = ma.contains(keyword) || ten.contains(keyword) || diachi.contains(keyword) || sdt.contains(keyword);
                    break;
                case "Mã Nhân viên":
                    match = ma.contains(keyword);
                    break;
                case "Họ tên":
                    match = ten.contains(keyword);
                    break;
                case "Địa chỉ":
                    match = diachi.contains(keyword);
                    break;
                case "Số điện thoại":
                    match = sdt.contains(keyword);
                    break;
            }

            if (match) {
                tbModel.addRow(new Object[]{
                    nv.getMaNV(), nv.getTenNV(), nv.getGioiTinh(), nv.getDiaChi(), nv.getNgayVao(), nv.getSdt(), nv.getNgaySinh()
                });
            }
        }
    }

}
