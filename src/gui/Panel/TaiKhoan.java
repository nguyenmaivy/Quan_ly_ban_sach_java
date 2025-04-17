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
import java.io.FileOutputStream;
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
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import net.miginfocom.layout.UnitValue;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

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
        mainFunction = new MainFunction(SOMEBITS, "taikhoan", acStrings);
        for (String ac : acStrings) {
            mainFunction.btn.get(ac).addActionListener(this);
        }
        functionBar.add(mainFunction);

        search = new IntegratedSearch(new String[]{"Tất cả", "Username", "Số điện thoại", "Mã nhân viên", "Mã nhóm quyền"});
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

            String sdt = tbModel.getValueAt(selectedRow, 2).toString();
            TaiKhoanDTO tk = tkBUS.getBySdt(sdt);

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
        } else if (source == mainFunction.btn.get("detail")) {
            int selectedRow = tbltk.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một tài khoản để xem chi tiết!");
                return;
            }

            String sdt = tbModel.getValueAt(selectedRow, 2).toString(); // lấy số điện thoại để tra đối tượng
            TaiKhoanDTO tk = tkBUS.getBySdt(sdt);

            if (tk != null) {
                TaiKhoanDialog dialog = new TaiKhoanDialog(tkBUS,
                        (JFrame) SwingUtilities.getWindowAncestor(this),
                        "Chi tiết tài khoản", true, "detail", tk);
            } else {
                JOptionPane.showMessageDialog(this, "Không tìm thấy tài khoản!");
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

    private void searchData() {
        String keyword = search.txtSearchForm.getText().trim().toLowerCase();
        String selectedField = search.cbxChoose.getSelectedItem().toString();

        TaiKhoanBUS tkBUS = new TaiKhoanBUS();
        ArrayList<TaiKhoanDTO> tkList = tkBUS.getAllTaiKhoan();

        // Xóa dữ liệu cũ trong bảng
        tbModel.setRowCount(0);

        for (TaiKhoanDTO tk : tkList) {
            boolean match = false;

            switch (selectedField) {
                case "Tất cả":
                    match = tk.getUseName().toLowerCase().contains(keyword)
                            || tk.getMatKhau().toLowerCase().contains(keyword)
                            || tk.getSdt().toLowerCase().contains(keyword)
                            || tk.getMaNV().toLowerCase().contains(keyword)
                            || String.valueOf(tk.getMaNhomQuyen()).toLowerCase().contains(keyword);
                    break;
                case "Username":
                    match = tk.getUseName().toLowerCase().contains(keyword);
                    break;
                case "Số điện thoại":
                    match = tk.getSdt().toLowerCase().contains(keyword);
                    break;
                case "Mã nhân viên":
                    match = tk.getMaNV().toLowerCase().contains(keyword);
                    break;
                case "Mã nhóm quyền":
                    match = String.valueOf(tk.getMaNhomQuyen()).contains(keyword);
                    break;
            }

            if (match) {
                tbModel.addRow(new Object[]{
                    tk.getUseName(),
                    tk.getMatKhau(),
                    tk.getSdt(),
                    tk.getMaNV(),
                    tk.getMaNhomQuyen()
                });
            }
        }
    }

    public class JTableExporter {

        public static void exportJTableToExcel(JTable table) throws IOException {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Chọn nơi lưu file Excel");
            fileChooser.setFileFilter(new FileNameExtensionFilter("Excel Files", "xlsx"));

            int userSelection = fileChooser.showSaveDialog(null);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                if (!filePath.toLowerCase().endsWith(".xlsx")) {
                    filePath += ".xlsx";
                }

                Workbook workbook = new XSSFWorkbook();
                Sheet sheet = workbook.createSheet("Danh sách tài khoản");

                TableModel model = table.getModel();

                // Tạo header
                Row headerRow = sheet.createRow(0);
                for (int col = 0; col < model.getColumnCount(); col++) {
                    Cell cell = headerRow.createCell(col);
                    cell.setCellValue(model.getColumnName(col));
                }

                // Ghi dữ liệu
                for (int row = 0; row < model.getRowCount(); row++) {
                    Row dataRow = sheet.createRow(row + 1);
                    for (int col = 0; col < model.getColumnCount(); col++) {
                        Cell cell = dataRow.createCell(col);
                        Object value = model.getValueAt(row, col);
                        cell.setCellValue(value != null ? value.toString() : "");
                    }
                }

                // Tự động căn chỉnh độ rộng cột
                for (int i = 0; i < model.getColumnCount(); i++) {
                    sheet.autoSizeColumn(i);
                }

                FileOutputStream fos = new FileOutputStream(filePath);
                workbook.write(fos);
                fos.close();
                workbook.close();
            }
        }
    }

}
