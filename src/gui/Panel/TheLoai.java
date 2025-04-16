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

import bus.TheLoaiBUS;
import dto.TheLoaiDTO;
import gui.Componet.Custom.IntegratedSearch;
import gui.Componet.Custom.MainFunction;
import gui.Componet.Custom.PanelBorderRadius;
import gui.Dialog.TheLoaiDialog;
import gui.Main;
import helper.JTableExporter;

public class TheLoai extends JPanel implements ActionListener {

    PanelBorderRadius main, functionBar;
    JPanel contentCenter;
    JTable tblTheLoai;
    JScrollPane scrollTable;
    MainFunction mainFunction;
    IntegratedSearch search;
    DefaultTableModel tbModel;

    Main m;
    Color BackgroundColor = new Color(240, 247, 250);

    public TheLoai(Main m) {
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
        mainFunction = new MainFunction(0, "theloai", acStrings);
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

        tblTheLoai = new JTable();
        tblTheLoai.setDefaultEditor(Object.class, null);
        scrollTable = new JScrollPane();
        tbModel = new DefaultTableModel();
        String[] header = new String[]{"Mã Loại", "Tên Loại"};
        tbModel.setColumnIdentifiers(header);
        tblTheLoai.setModel(tbModel);
        scrollTable.setViewportView(tblTheLoai);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        TableColumnModel columnModel = tblTheLoai.getColumnModel();
        for (int i = 0; i < columnModel.getColumnCount(); i++) {
            columnModel.getColumn(i).setCellRenderer(centerRenderer);
        }

        tblTheLoai.setFocusable(false);
        main.add(scrollTable);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        TheLoaiBUS tlBUS = new TheLoaiBUS();

        if (source == mainFunction.btn.get("create")) {
            TheLoaiDialog dialog = new TheLoaiDialog(tlBUS,
                    (JFrame) SwingUtilities.getWindowAncestor(this),
                    "Thêm Thể Loại", true, "create", null);
            loadDataTable();
        } else if (source == mainFunction.btn.get("update")) {
            int selectedRow = tblTheLoai.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một thể loại để cập nhật!");
                return;
            }

            String maLoai = tbModel.getValueAt(selectedRow, 0).toString();
            TheLoaiDTO theLoai = tlBUS.getByID(maLoai);

            if (theLoai != null) {
                TheLoaiDialog dialog = new TheLoaiDialog(tlBUS,
                        (JFrame) SwingUtilities.getWindowAncestor(this),
                        "Cập nhật Thể Loại", true, "update", theLoai);
                loadDataTable();
            } else {
                JOptionPane.showMessageDialog(this, "Không tìm thấy thể loại!");
            }
        } else if (source == mainFunction.btn.get("delete")) {
            int selectedRow = tblTheLoai.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một thể loại để xóa!");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa thể loại này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                String maLoai = tbModel.getValueAt(selectedRow, 0).toString();
                String result = tlBUS.deleteTheLoai(maLoai);
                JOptionPane.showMessageDialog(this, result);
                loadDataTable();
            }
        } else if (source == mainFunction.btn.get("export")) {
            try {
                JTableExporter.exportJTableToExcel(tblTheLoai);
                JOptionPane.showMessageDialog(this, "Xuất file Excel thành công!");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Xuất file thất bại: " + ex.getMessage());
            }
        }
    }

    private void loadDataTable() {
        TheLoaiBUS tlBUS = new TheLoaiBUS();
        ArrayList<TheLoaiDTO> list = tlBUS.getAllTheLoai();

        tbModel.setRowCount(0);
        for (TheLoaiDTO tl : list) {
            tbModel.addRow(new Object[]{
                tl.getMaLoai(),
                tl.getTenLoai(),
                tl.getTrangThai() == 1 ? "Hoạt động" : "Ngừng hoạt động"
            });
        }
    }
}
