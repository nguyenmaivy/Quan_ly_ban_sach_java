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
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileOutputStream;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.TableModel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

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

        search = new IntegratedSearch(new String[]{"Tất cả", "Mã kho", "Tên kho", "Địa chỉ"});
        search.txtSearchForm.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {}

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
        KhoSachBUS khoBUS = new KhoSachBUS();  // Dùng chung 1 BUS

        if (source == mainFunction.btn.get("create")) {
            KhoSachDialog dialog = new KhoSachDialog(khoBUS,
                    (JFrame) SwingUtilities.getWindowAncestor(this),
                    "Thêm Kho Sách", true, "create", null);
            loadDataTable();

        } else if (source == mainFunction.btn.get("update")) {
            int selectedRow = tblKhoSach.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một kho để sửa!");
                return;
            }

            String maKho = tbModel.getValueAt(selectedRow, 0).toString();
            KhoSachDTO kho = khoBUS.getByID(maKho);

            if (kho != null) {
                KhoSachDialog dialog = new KhoSachDialog(khoBUS,
                        (JFrame) SwingUtilities.getWindowAncestor(this),
                        "Cập nhật Kho Sách", true, "update", kho);
                loadDataTable();
            } else {
                JOptionPane.showMessageDialog(this, "Không tìm thấy kho sách!");
            }

        } else if (source == mainFunction.btn.get("delete")) {
            int selectedRow = tblKhoSach.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một kho để xóa!");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa kho này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                String maKho = tbModel.getValueAt(selectedRow, 0).toString();
                String result = khoBUS.deleteKhoSach(maKho);
                JOptionPane.showMessageDialog(this, result);
                loadDataTable();
            }

        } else if (source == mainFunction.btn.get("detail")) {
            int selectedRow = tblKhoSach.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một kho để xem chi tiết!");
                return;
            }

            String maKho = tbModel.getValueAt(selectedRow, 0).toString();
            KhoSachDTO kho = khoBUS.getByID(maKho);

            if (kho != null) {
                KhoSachDialog dialog = new KhoSachDialog(khoBUS,
                        (JFrame) SwingUtilities.getWindowAncestor(this),
                        "Chi tiết Kho Sách", true, "detail", kho);
            } else {
                JOptionPane.showMessageDialog(this, "Không tìm thấy kho sách!");
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
    
    private void searchData() {
        String keyword = search.txtSearchForm.getText().trim().toLowerCase();
        String category = search.cbxChoose.getSelectedItem().toString();

        KhoSachBUS khoBUS = new KhoSachBUS();
        ArrayList<KhoSachDTO> list = khoBUS.getALLKhoSach();

        tbModel.setRowCount(0); // Xoá bảng trước

        for (KhoSachDTO kho : list) {
            String ma = (kho.getMaKho() != null) ? kho.getMaKho().toLowerCase() : "";
            String ten = (kho.getTenKho() != null) ? kho.getTenKho().toLowerCase() : "";
            String diaChi = (kho.getDiaChi() != null) ? kho.getDiaChi().toLowerCase() : "";

            boolean match = switch (category) {
                case "Tất cả" -> ma.contains(keyword) || ten.contains(keyword) || diaChi.contains(keyword);
                case "Mã kho" -> ma.contains(keyword);
                case "Tên kho" -> ten.contains(keyword);
                case "Địa chỉ" -> diaChi.contains(keyword);
                default -> false;
            };

            if (match) {
                tbModel.addRow(new Object[]{
                    kho.getMaKho(), kho.getTenKho(), kho.getDiaChi(), kho.getLoai()
                });
            }
        }
    }

    
    public class JTableExporter {

        public static void exportJTableToExcel(JTable table) throws IOException {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Kho Sach");

            TableModel model = table.getModel();

            // Tạo dòng tiêu đề
            Row headerRow = sheet.createRow(0);
            for (int col = 0; col < model.getColumnCount(); col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(model.getColumnName(col));
            }

            // Ghi dữ liệu
            for (int row = 0; row < model.getRowCount(); row++) {
                Row excelRow = sheet.createRow(row + 1);
                for (int col = 0; col < model.getColumnCount(); col++) {
                    Cell cell = excelRow.createCell(col);
                    Object value = model.getValueAt(row, col);
                    cell.setCellValue(value != null ? value.toString() : "");
                }
            }

            // Mở hộp thoại lưu file
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Chọn nơi lưu file Excel");
            fileChooser.setFileFilter(new FileNameExtensionFilter("Excel files", "xlsx"));

            int userSelection = fileChooser.showSaveDialog(null);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                String savePath = fileChooser.getSelectedFile().getAbsolutePath();
                if (!savePath.toLowerCase().endsWith(".xlsx")) {
                    savePath += ".xlsx";
                }

                try (FileOutputStream fileOut = new FileOutputStream(savePath)) {
                    workbook.write(fileOut);
                }
            }

            workbook.close();
        }
    }
}
