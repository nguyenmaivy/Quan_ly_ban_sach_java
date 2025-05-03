package gui.Panel;

import javax.swing.*;
import bus.KhachHangBUS;
import gui.Componet.Custom.IntegratedSearch;
import gui.Componet.Custom.MainFunction;
import gui.Componet.Custom.PanelBorderRadius;
import helper.JTableExporter;
import dto.KhachHangDTO;
import gui.Dialog.KhachHangDialog;
import gui.Main;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

public class KhachHang extends JPanel implements ActionListener {

    PanelBorderRadius main, functionBar;
    JPanel contentCenter;
    JTable tblkh;
    JScrollPane scrollTable;
    MainFunction mainFunction;
    IntegratedSearch search;
    DefaultTableModel tbModel;

    Main m;
    Color BackgroundColor = new Color(240, 247, 250);

    public KhachHang(Main m) {
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

        String[] acStrings = {"create", "update", "delete", "export"};
        mainFunction = new MainFunction(MainFunction.SOMEBITS, "khachhang", acStrings);
        for (String ac : acStrings) {
            mainFunction.btn.get(ac).addActionListener(this);
        }
        functionBar.add(mainFunction);

        search = new IntegratedSearch(new String[]{"Tất cả"});
        functionBar.add(search);
        search.txtSearchForm.addActionListener(this);


        contentCenter.add(functionBar, BorderLayout.NORTH);

        main = new PanelBorderRadius();
        BoxLayout boxLayout = new BoxLayout(main, BoxLayout.Y_AXIS);
        main.setLayout(boxLayout);
        contentCenter.add(main, BorderLayout.CENTER);

        tblkh = new JTable();
        tblkh.setDefaultEditor(Object.class, null);
        scrollTable = new JScrollPane();
        tbModel = new DefaultTableModel();
        String[] header = new String[]{"Mã KH", "Tên KH", "SĐT", "Địa chỉ"};
        tbModel.setColumnIdentifiers(header);
        tblkh.setModel(tbModel);
        scrollTable.setViewportView(tblkh);

        DefaultTableCellRenderer cenCellRenderer = new DefaultTableCellRenderer();
        cenCellRenderer.setHorizontalAlignment(JLabel.CENTER);
        TableColumnModel columnModel = tblkh.getColumnModel();
        for (int i = 0; i < columnModel.getColumnCount(); i++) {
            columnModel.getColumn(i).setCellRenderer(cenCellRenderer);
        }

        tblkh.setFocusable(false);
        main.add(scrollTable);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        KhachHangBUS khBUS = new KhachHangBUS();

        if (source == mainFunction.btn.get("create")) {
            KhachHangDialog dialog = new KhachHangDialog(khBUS,
                    (JFrame) SwingUtilities.getWindowAncestor(this),
                    "Thêm Khách Hàng", true, "create", null);
            loadDataTable();

        } else if (source == mainFunction.btn.get("update")) {
            int selectedRow = tblkh.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một khách hàng để sửa!");
                return;
            }

            String maKH = tbModel.getValueAt(selectedRow, 0).toString();
            KhachHangDTO khachHang = khBUS.getById(maKH);

            if (khachHang != null) {
                KhachHangDialog dialog = new KhachHangDialog(khBUS,
                        (JFrame) SwingUtilities.getWindowAncestor(this),
                        "Cập nhật Khách Hàng", true, "update", khachHang);
                loadDataTable();
            } else {
                JOptionPane.showMessageDialog(this, "Không tìm thấy khách hàng!");
            }

        } else if (source == mainFunction.btn.get("delete")) {
            int selectedRow = tblkh.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một khách hàng để xóa!");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa khách hàng này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                String maKH = tbModel.getValueAt(selectedRow, 0).toString();
                String result = khBUS.deleteKhachHang(maKH);
                JOptionPane.showMessageDialog(this, result);
                loadDataTable();
            }

        } else if (source == mainFunction.btn.get("export")) {
            boolean success = JTableExporter.exportJTableToExcel(tblkh);
            if (success) {
                JOptionPane.showMessageDialog(this, "Xuất file Excel thành công!");
            }
        }
        
        else if (source == search.txtSearchForm) {
            String keyword = search.getTextField().getText().trim();
            ArrayList<KhachHangDTO> result = khBUS.searchKhachHang(keyword);
            tbModel.setRowCount(0);
            for (KhachHangDTO kh : result) {
                tbModel.addRow(new Object[]{
                    kh.getMaKH(),
                    kh.getTenKH(),
                    kh.getSdt(),
                    kh.getDiaChi()
                });
            }
        }
        
        search.btnReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Xóa kết quả tìm kiếm
                tbModel.setRowCount(0);

                // Hiển thị lại toàn bộ danh sách khách hàng
                ArrayList<KhachHangDTO> list = khBUS.getAllKhachHang();
                for (KhachHangDTO kh : list) {
                    tbModel.addRow(new Object[]{
                        kh.getMaKH(),
                        kh.getTenKH(),
                        kh.getSdt(),
                        kh.getDiaChi()
                    });
                }
            }
        });

    }

    private void loadDataTable() {
        KhachHangBUS khBUS = new KhachHangBUS();
        ArrayList<KhachHangDTO> khList = khBUS.getAllKhachHang();

        tbModel.setRowCount(0);

        for (KhachHangDTO kh : khList) {
            tbModel.addRow(new Object[]{
                kh.getMaKH(),
                kh.getTenKH(),
                kh.getSdt(),
                kh.getDiaChi()
            });
        }
    }
}
