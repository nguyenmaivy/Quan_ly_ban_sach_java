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

        search = new IntegratedSearch(new String[]{"Tất cả", "Mã thể loại", "Tên thể loại"});
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
        TheLoaiBUS theLoaiBUS = new TheLoaiBUS(); // Dùng chung 1 đối tượng BUS

        if (source == mainFunction.btn.get("create")) {
            TheLoaiDialog dialog = new TheLoaiDialog(theLoaiBUS,
                    (JFrame) SwingUtilities.getWindowAncestor(this),
                    "Thêm Thể Loại", true, "create", null);
            loadDataTable();

        } else if (source == mainFunction.btn.get("update")) {
            int selectedRow = tblTheLoai.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một thể loại để sửa!");
                return;
            }

            String maLoai = tbModel.getValueAt(selectedRow, 0).toString();
            TheLoaiDTO tl = theLoaiBUS.getByID(maLoai);

            if (tl != null) {
                TheLoaiDialog dialog = new TheLoaiDialog(theLoaiBUS,
                        (JFrame) SwingUtilities.getWindowAncestor(this),
                        "Cập nhật Thể Loại", true, "update", tl);
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
                String result = theLoaiBUS.deleteTheLoai(maLoai);
                JOptionPane.showMessageDialog(this, result);
                loadDataTable();
            }

        } else if (source == mainFunction.btn.get("detail")) {
            int selectedRow = tblTheLoai.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một thể loại để xem chi tiết!");
                return;
            }

            String maLoai = tbModel.getValueAt(selectedRow, 0).toString();
            TheLoaiDTO tl = theLoaiBUS.getByID(maLoai);

            if (tl != null) {
                TheLoaiDialog dialog = new TheLoaiDialog(theLoaiBUS,
                        (JFrame) SwingUtilities.getWindowAncestor(this),
                        "Chi tiết Thể Loại", true, "detail", tl);
            } else {
                JOptionPane.showMessageDialog(this, "Không tìm thấy thể loại!");
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
    
    private void searchData() {
        String keyword = search.txtSearchForm.getText().trim().toLowerCase();
        String category = search.cbxChoose.getSelectedItem().toString();

        TheLoaiBUS theLoaiBUS = new TheLoaiBUS();
        ArrayList<TheLoaiDTO> list = theLoaiBUS.getAllTheLoai();

        tbModel.setRowCount(0); // Xoá dữ liệu bảng cũ

        for (TheLoaiDTO tl : list) {
            String ma = (tl.getMaLoai() != null) ? tl.getMaLoai().toLowerCase() : "";
            String ten = (tl.getTenLoai() != null) ? tl.getTenLoai().toLowerCase() : "";

            boolean match = switch (category) {
                case "Tất cả" -> ma.contains(keyword) || ten.contains(keyword);
                case "Mã thể loại" -> ma.contains(keyword);
                case "Tên thể loại" -> ten.contains(keyword);
                default -> false;
            };

            if (match) {
                tbModel.addRow(new Object[]{
                    tl.getMaLoai(), tl.getTenLoai()
                });
            }
        }
    }
    
    public class JTableExporter {

        public static void exportJTableToExcel(JTable table) throws IOException {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Nha Xuat Ban");

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
