package gui.Panel;

import javax.swing.JPanel;
import bus.TaiKhoanBUS;
import gui.Componet.Custom.IntegratedSearch;
import gui.Componet.Custom.MainFunction;
import gui.Componet.Custom.PanelBorderRadius;
import gui.Componet.Custom.TableSorter;
import gui.Main;
import dto.TaiKhoanDTO;
import gui.Dialog.TaiKhoanDialog;
import javax.swing.*;
import helper.JTableExporter;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import static java.awt.image.ImageObserver.SOMEBITS;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import net.miginfocom.layout.UnitValue;
import javax.swing.table.TableColumnModel;

public class TaiKhoan extends JPanel implements ActionListener {

    PanelBorderRadius main, functionBar;
    JPanel contentCenter;
    JTable tbltk;
    JScrollPane scrollTable;
    MainFunction mainFunction;
    IntegratedSearch search;
    DefaultTableModel tbModel;

    Main m;
    Color BackgroundColor = new Color(240, 247, 250);

    public TaiKhoan(Main m) {
        this.m = m;
        initComponent();
        loadDataTable(); // Thêm dòng này để load danh sách ngay khi mở giao diện
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

        // functionBar chứa các nút thực hiện: xóa, sửa...
        functionBar = new PanelBorderRadius();
        functionBar.setPreferredSize(new Dimension(0, 100));
        functionBar.setLayout(new GridLayout(1, 2, 50, 0));
        functionBar.setBorder(new EmptyBorder(10, 10, 10, 10));

        String[] acStrings = {"create", "update", "delete", "detail", "export"};
        mainFunction = new MainFunction(SOMEBITS, "tacgia", acStrings);
        for (String ac : acStrings) {
            mainFunction.btn.get(ac).addActionListener(this);
        }
        functionBar.add(mainFunction);

        search = new IntegratedSearch(new String[]{"Tất cả"});
        functionBar.add(search);

        contentCenter.add(functionBar, BorderLayout.NORTH);

        //main: phần ở dưới để bảng
        main = new PanelBorderRadius();
        BoxLayout boxLayout = new BoxLayout(main, BoxLayout.Y_AXIS);
        main.setLayout(boxLayout);
        contentCenter.add(main, BorderLayout.CENTER);

        tbltk = new JTable();
        tbltk.setDefaultEditor(Object.class, null);
        scrollTable = new JScrollPane();
        tbModel = new DefaultTableModel();
        String[] header = new String[]{"Username", "Mật khẩu", "Số điện thoại", "Mã nhân viên", "Mã nhóm quyền"};
        tbModel.setColumnIdentifiers(header);
        tbltk.setModel(tbModel);
        scrollTable.setViewportView(tbltk);
        DefaultTableCellRenderer cenCellRenderer = new DefaultTableCellRenderer();
        cenCellRenderer.setHorizontalAlignment(JLabel.CENTER);
        TableColumnModel columnModel = tbltk.getColumnModel();
        columnModel.getColumn(0).setCellRenderer(cenCellRenderer);
        columnModel.getColumn(0).setPreferredWidth(50);
        columnModel.getColumn(1).setCellRenderer(cenCellRenderer);
        columnModel.getColumn(1).setPreferredWidth(300);
        columnModel.getColumn(2).setCellRenderer(cenCellRenderer);
        columnModel.getColumn(2).setPreferredWidth(200);
        tbltk.setFocusable(false);
        main.add(scrollTable);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        TaiKhoanBUS tkBUS = new TaiKhoanBUS();  // Sử dụng chung một đối tượng BUS

        if (source == mainFunction.btn.get("create")) {
            TaiKhoanDialog dialog = new TaiKhoanDialog(tkBUS,
                    (JFrame) SwingUtilities.getWindowAncestor(this),
                    "Thêm Tài Khoản", true, "create", null);
            loadDataTable();  // Cập nhật bảng sau khi thêm
        } else if (source == mainFunction.btn.get("update")) {
            int selectedRow = tbltk.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một tài khoản để cập nhật!");
                return;
            }

            String usename = tbModel.getValueAt(selectedRow, 0).toString();
            TaiKhoanDTO tk = tkBUS.getBySdt(usename);

            if (tk != null) {
                TaiKhoanDialog dialog = new TaiKhoanDialog(tkBUS,
                        (JFrame) SwingUtilities.getWindowAncestor(this),
                        "Cập nhật tài khoản", true, "update", tk);
                loadDataTable();  // Cập nhật bảng sau khi cập nhật
            } else {
                JOptionPane.showMessageDialog(this, "Không tìm thấy tài khoản!");
            }
        } else if (source == mainFunction.btn.get("delete")) {
            int selectedRow = tbltk.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một tài khoản để xóa!");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa tài khoản này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                String sdt = tbModel.getValueAt(selectedRow, 2).toString();

                String result = tkBUS.deleteTaiKhoan(sdt);
                JOptionPane.showMessageDialog(this, result);
                loadDataTable();  // Cập nhật bảng sau khi xóa
            }
        } else if (source == mainFunction.btn.get("export")) {
            try {
                JTableExporter.exportJTableToExcel(tbltk);
                JOptionPane.showMessageDialog(this, "Xuất file Excel thành công!");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Xuất file thất bại: " + ex.getMessage());
            }
        }
    }

    private void loadDataTable() {
        TaiKhoanBUS tkBUS = new TaiKhoanBUS();
        ArrayList<TaiKhoanDTO> tkList = tkBUS.getAllTaiKhoan();

        // Xóa toàn bộ dữ liệu cũ trong bảng
        tbModel.setRowCount(0);

        // Thêm dữ liệu mới vào bảng
        for (TaiKhoanDTO tk : tkList) {
            tbModel.addRow(new Object[]{
                tk.getUseName(),
                tk.getMatKhau(),
                tk.getSdt(),
                tk.getMaNV(),
                tk.getMaNhomQuyen()});
        }
    }

}
