package gui.Panel;

import javax.swing.JPanel;
import bus.NhaXuatBanBUS;
import gui.Componet.Custom.IntegratedSearch;
import gui.Componet.Custom.MainFunction;
import gui.Componet.Custom.PanelBorderRadius;
import gui.Componet.Custom.TableSorter;
import gui.Main;
import dto.NhaXuatBanDTO;
import gui.Dialog.NhaXuatBanDialog;
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

public class NhaXuatBan extends JPanel implements ActionListener {

    PanelBorderRadius main, functionBar;
    JPanel contentCenter;
    JTable tblnxb;
    JScrollPane scrollTable;
    MainFunction mainFunction;
    IntegratedSearch search;
    DefaultTableModel tbModel;

    Main m;
    Color BackgroundColor = new Color(240, 247, 250);

    public NhaXuatBan(Main m) {
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

        tblnxb = new JTable();
        tblnxb.setDefaultEditor(Object.class, null);
        scrollTable = new JScrollPane();
        tbModel = new DefaultTableModel();
        String[] header = new String[]{"Mã nhà xuất bản", "Tên nhà xuất bản", "Số điện thoại", "Địa chỉ"};
        tbModel.setColumnIdentifiers(header);
        tblnxb.setModel(tbModel);
        scrollTable.setViewportView(tblnxb);
        DefaultTableCellRenderer cenCellRenderer = new DefaultTableCellRenderer();
        cenCellRenderer.setHorizontalAlignment(JLabel.CENTER);
        TableColumnModel columnModel = tblnxb.getColumnModel();
        columnModel.getColumn(0).setCellRenderer(cenCellRenderer);
        columnModel.getColumn(0).setPreferredWidth(50);
        columnModel.getColumn(1).setCellRenderer(cenCellRenderer);
        columnModel.getColumn(1).setPreferredWidth(300);
        columnModel.getColumn(2).setCellRenderer(cenCellRenderer);
        columnModel.getColumn(2).setPreferredWidth(200);
        tblnxb.setFocusable(false);
        main.add(scrollTable);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        NhaXuatBanBUS nxbBUS = new NhaXuatBanBUS();  // Sử dụng chung một đối tượng BUS

        if (source == mainFunction.btn.get("create")) {
            NhaXuatBanDialog dialog = new NhaXuatBanDialog(nxbBUS,
                    (JFrame) SwingUtilities.getWindowAncestor(this),
                    "Thêm Nhà Xuất Bản", true, "create", null);
//            loadDataTable();  // Cập nhật bảng sau khi thêm

        } else if (source == mainFunction.btn.get("update")) {
            int selectedRow = tblnxb.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một nhà xuất bản để sửa!");
                return;
            }

            String maNXB = tbModel.getValueAt(selectedRow, 0).toString();
            NhaXuatBanDTO nhaXuatBan = nxbBUS.getByName(maNXB);

            if (nhaXuatBan != null) {
                NhaXuatBanDialog dialog = new NhaXuatBanDialog(nxbBUS,
                        (JFrame) SwingUtilities.getWindowAncestor(this),
                        "Cập nhật Nhà Xuất Bản", true, "update", nhaXuatBan);
                loadDataTable();  // Cập nhật bảng sau khi cập nhật
            } else {
                JOptionPane.showMessageDialog(this, "Không tìm thấy nhà xuất bản!");
            }
        } else if (source == mainFunction.btn.get("delete")) {
            int selectedRow = tblnxb.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một nhà xuất bản để xóa!");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa nhà xuất bản này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                String maNXB = tbModel.getValueAt(selectedRow, 0).toString();
                String result = nxbBUS.deleteNhaXuatBan(maNXB);
                JOptionPane.showMessageDialog(this, result);
                loadDataTable();  // Cập nhật bảng sau khi xóa
            }
        } else if (source == mainFunction.btn.get("export")) {
            try {
                JTableExporter.exportJTableToExcel(tblnxb);
                JOptionPane.showMessageDialog(this, "Xuất file Excel thành công!");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Xuất file thất bại: " + ex.getMessage());
            }
        }
    }
    // chưa có lấy dữ liệu ra nên có thế sửa lại bus, dao
    private void loadDataTable() {
        NhaXuatBanBUS nxbBUS = new NhaXuatBanBUS();
        ArrayList<NhaXuatBanDTO> nxbList = nxbBUS.getAllNhaXuatBan();

        // Xóa toàn bộ dữ liệu cũ trong bảng
        tbModel.setRowCount(0);

        // Thêm dữ liệu mới vào bảng
        for (NhaXuatBanDTO nxb : nxbList) {
            tbModel.addRow(new Object[]{
                (nxb.getMaNXB() != null) ? nxb.getMaNXB() : "", // Tránh null
                nxb.getTenNXB(),
                nxb.getSdt(),
                nxb.getDiachiNXB()
            });

        }
    }
}
