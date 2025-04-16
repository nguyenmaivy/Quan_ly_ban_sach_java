package gui.Panel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import bus.KhoSachBUS;
import dto.KhoSachDTO;
import gui.Componet.Custom.IntegratedSearch;
import gui.Componet.Custom.MainFunction;
import gui.Componet.Custom.PanelBorderRadius;
import gui.Dialog.KhoSachDialog;
import gui.Main;
import helper.JTableExporter;

public class KhoSach extends JPanel implements ActionListener {

    PanelBorderRadius main, functionBar;
    JPanel contentCenter;
    JTable tblKhoSach;
    JScrollPane scrollTable;
    MainFunction mainFunction;
    IntegratedSearch search;
    DefaultTableModel tbModel;

    Main m;
    Color BackgroundColor = new Color(240, 247, 250);

    public KhoSach(Main m) {
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
        mainFunction = new MainFunction(0, "khosach", acStrings);
        for (String ac : acStrings) {
            mainFunction.btn.get(ac).addActionListener(this);
        }
        functionBar.add(mainFunction);

        search = new IntegratedSearch(new String[]{"Tất cả"});
        functionBar.add(search);

        contentCenter.add(functionBar, BorderLayout.NORTH);

        main = new PanelBorderRadius();
        BoxLayout boxLayout = new BoxLayout(main, BoxLayout.Y_AXIS);
        main.setLayout(boxLayout);
        contentCenter.add(main, BorderLayout.CENTER);

        tblKhoSach = new JTable();
        tblKhoSach.setDefaultEditor(Object.class, null);
        scrollTable = new JScrollPane();
        tbModel = new DefaultTableModel();
        String[] header = new String[]{"Mã Kho", "Tên Kho", "Địa Chỉ", "Loại"};
        tbModel.setColumnIdentifiers(header);
        tblKhoSach.setModel(tbModel);
        scrollTable.setViewportView(tblKhoSach);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        TableColumnModel columnModel = tblKhoSach.getColumnModel();
        for (int i = 0; i < columnModel.getColumnCount(); i++) {
            columnModel.getColumn(i).setCellRenderer(centerRenderer);
        }

        tblKhoSach.setFocusable(false);
        main.add(scrollTable);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        KhoSachBUS ksBUS = new KhoSachBUS();

        if (source == mainFunction.btn.get("create")) {
            KhoSachDialog dialog = new KhoSachDialog(ksBUS,
                    (JFrame) SwingUtilities.getWindowAncestor(this),
                    "Thêm Kho Sách", true, "create", null);
            loadDataTable();
        } else if (source == mainFunction.btn.get("update")) {
            int selectedRow = tblKhoSach.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một kho sách để cập nhật!");
                return;
            }

            String maKho = tbModel.getValueAt(selectedRow, 0).toString();
            KhoSachDTO ks = ksBUS.getByID(maKho);

            if (ks != null) {
                KhoSachDialog dialog = new KhoSachDialog(ksBUS,
                        (JFrame) SwingUtilities.getWindowAncestor(this),
                        "Cập nhật Kho Sách", true, "update", ks);
                loadDataTable();
            } else {
                JOptionPane.showMessageDialog(this, "Không tìm thấy kho sách!");
            }
        } else if (source == mainFunction.btn.get("delete")) {
            int selectedRow = tblKhoSach.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một kho sách để xóa!");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa kho sách này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                String maKho = tbModel.getValueAt(selectedRow, 0).toString();
                String result = ksBUS.deleteKhoSach(maKho);
                JOptionPane.showMessageDialog(this, result);
                loadDataTable();
            }
        } else if (source == mainFunction.btn.get("export")) {
            try {
                JTableExporter.exportJTableToExcel(tblKhoSach);
                JOptionPane.showMessageDialog(this, "Xuất file Excel thành công!");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Xuất file thất bại: " + ex.getMessage());
            }
        }
    }

    private void loadDataTable() {
        KhoSachBUS ksBUS = new KhoSachBUS();
        ArrayList<KhoSachDTO> list = ksBUS.getALLKhoSach();

        tbModel.setRowCount(0);
        for (KhoSachDTO ks : list) {
            tbModel.addRow(new Object[]{
                ks.getMaKho(),
                ks.getTenKho(),
                ks.getDiaChi(),
                ks.getLoai()
            });
        }
    }
}
