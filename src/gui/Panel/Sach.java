package gui.Panel;

import javax.swing.JPanel;
import bus.SachBUS;
import gui.Componet.Custom.IntegratedSearch;
import gui.Componet.Custom.MainFunction;
import gui.Componet.Custom.PanelBorderRadius;
import gui.Main;
import dto.SachDTO;
import gui.Dialog.SachDialog;
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

public class Sach extends JPanel implements ActionListener {

    PanelBorderRadius main, functionBar;
    JPanel contentCenter;
    JTable tblSach;
    JScrollPane scrollTable;
    MainFunction mainFunction;
    IntegratedSearch search;
    DefaultTableModel tbModel;

    Main m;
    Color BackgroundColor = new Color(240, 247, 250);

    public Sach(Main m) {
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
        mainFunction = new MainFunction(SOMEBITS, "sach", acStrings);
        for (String ac : acStrings) {
            mainFunction.btn.get(ac).addActionListener(this);
        }
        functionBar.add(mainFunction);

        search = new IntegratedSearch(new String[]{"Tất cả", "Mã sách", "Tên sách", "Giá bán"});
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
                searchData();  // Khi đổi tiêu chí tìm kiếm, cũng lọc lại
            }
        });

        functionBar.add(search);

        contentCenter.add(functionBar, BorderLayout.NORTH);

        main = new PanelBorderRadius();
        BoxLayout boxLayout = new BoxLayout(main, BoxLayout.Y_AXIS);
        main.setLayout(boxLayout);
        contentCenter.add(main, BorderLayout.CENTER);

        tblSach = new JTable();
        tblSach.setDefaultEditor(Object.class, null);
        scrollTable = new JScrollPane();
        tbModel = new DefaultTableModel();
        String[] header = new String[]{"ID", "Tên Sách", "Thể Loại", "Tác Giả", "Nhà Xuất Bản", "Giá Bán", "Số Lượng", "Mã Kho"};
        tbModel.setColumnIdentifiers(header);
        tblSach.setModel(tbModel);
        scrollTable.setViewportView(tblSach);

        DefaultTableCellRenderer cenCellRenderer = new DefaultTableCellRenderer();
        cenCellRenderer.setHorizontalAlignment(JLabel.CENTER);
        TableColumnModel columnModel = tblSach.getColumnModel();
        for (int i = 0; i < columnModel.getColumnCount(); i++) {
            columnModel.getColumn(i).setCellRenderer(cenCellRenderer);
        }

        tblSach.setFocusable(false);
        main.add(scrollTable);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        SachBUS sachBUS = new SachBUS();

        if (source == mainFunction.btn.get("create")) {
            SachDialog dialog = new SachDialog(sachBUS,
                    (JFrame) SwingUtilities.getWindowAncestor(this),
                    "Thêm Sách", true, "create", null);
            loadDataTable();
        } else if (source == mainFunction.btn.get("update")) {
            int selectedRow = tblSach.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một sách để cập nhật!");
                return;
            }

            String id = tbModel.getValueAt(selectedRow, 0).toString();
            SachDTO sach = sachBUS.getByID(id);

            if (sach != null) {
                SachDialog dialog = new SachDialog(sachBUS,
                        (JFrame) SwingUtilities.getWindowAncestor(this),
                        "Cập nhật Sách", true, "update", sach);
                loadDataTable();
            } else {
                JOptionPane.showMessageDialog(this, "Không tìm thấy sách!");
            }
        } else if (source == mainFunction.btn.get("delete")) {
            int selectedRow = tblSach.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một sách để xóa!");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa sách này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                String id = tbModel.getValueAt(selectedRow, 0).toString();
                String result = sachBUS.deleteSach(id);
                JOptionPane.showMessageDialog(this, result);
                loadDataTable();
            }
        } else if (source == mainFunction.btn.get("detail")) {
            int selectedRow = tblSach.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một sách để xem chi tiết!");
                return;
            }

            String maSach = tbModel.getValueAt(selectedRow, 0).toString();
            SachDTO sach = sachBUS.getByID(maSach);

            if (sach != null) {
                SachDialog dialog = new SachDialog(
                        sachBUS,
                        (JFrame) SwingUtilities.getWindowAncestor(this),
                        "Chi tiết Sách", true, "detail", sach
                );
                // Không cần load lại bảng vì chỉ xem chi tiết
            } else {
                JOptionPane.showMessageDialog(this, "Không tìm thấy sách!");
            }
        } else if (source == mainFunction.btn.get("export")) {
            boolean success = JTableExporter.exportJTableToExcel(tblSach);
            if (success) {
                JOptionPane.showMessageDialog(this, "Xuất file Excel thành công!");
            }
        }
    }

    private void loadDataTable() {
        SachBUS sachBUS = new SachBUS();
        ArrayList<SachDTO> list = sachBUS.getAllSach();

        tbModel.setRowCount(0);
        for (SachDTO s : list) {
            tbModel.addRow(new Object[]{
                s.getId(),
                s.getTenSach(),
                s.getTheLoai(),
                s.getTacGia(),
                s.getNhaXuatBan(),
                s.getGiaBan(),
                s.getSoLuong(),
                s.getMaKho()
            });
        }
    }

    private void searchData() {
        String keyword = search.txtSearchForm.getText().trim().toLowerCase();
        String category = search.cbxChoose.getSelectedItem().toString();

        SachBUS sachBUS = new SachBUS();
        ArrayList<SachDTO> list = sachBUS.getAllSach();

        tbModel.setRowCount(0); // Clear bảng

        for (SachDTO sach : list) {
            String ma = (sach.getId() != null) ? sach.getId().toLowerCase() : "";
            String ten = (sach.getTenSach() != null) ? sach.getTenSach().toLowerCase() : "";
            int giaban = sach.getGiaBan();

            boolean match = false;

            switch (category) {
                case "Tất cả":
                    match = ma.contains(keyword) || ten.contains(keyword) || String.valueOf(giaban).contains(keyword);
                    break;
                case "Mã sách":
                    match = ma.contains(keyword);
                    break;
                case "Tên sách":
                    match = ten.contains(keyword);
                    break;
                case "Giá bán":
                    match = String.valueOf(giaban).contains(keyword);  // So sánh bằng chữ số
                    break;

            }

            if (match) {
                tbModel.addRow(new Object[]{
                    sach.getId(), sach.getTenSach(), sach.getTheLoai(), sach.getTacGia(),
                    sach.getNhaXuatBan(), sach.getGiaBan(), sach.getSoLuong(), sach.getMaKho(), sach.getHinhAnh()
                });
            }
        }
    }

}
