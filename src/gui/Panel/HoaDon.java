package gui.Panel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import bus.HoaDonBUS;
import dto.HoaDonDTO;
import gui.Componet.Custom.IntegratedSearch;
import gui.Componet.Custom.MainFunction;
import gui.Componet.Custom.PanelBorderRadius;
import gui.Dialog.ChiTietHoaDonDialog; // Import the new dialog
import gui.Main;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

//import util.PanelBorderRadius;
//import util.IntegratedSearch;
//import util.MainFunction;
public class HoaDon extends JPanel implements ActionListener {

    PanelBorderRadius panelMain, panelFunctionBar;
    JPanel panelContentCenter;
    JTable tableHoaDon;
    JScrollPane scrollPaneTable;
    MainFunction mainFunction;
    IntegratedSearch searchField;
    DefaultTableModel tableModelHoaDon;
    HoaDonBUS hoaDonBUS = new HoaDonBUS(); // Thêm BUS để xử lý logic
    Color backgroundColor = new Color(240, 247, 250);
    private Main mainFrame; // Hold a reference to the main frame
    private static final DateTimeFormatter DISPLAY_DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public HoaDon(Main mainFrame) throws SQLException { // Đổi tên biến main thành mainFrame nếu cần
        this.mainFrame = mainFrame; // Store the main frame
        initComponents();
        loadHoaDonData(); // Load dữ liệu hóa đơn khi khởi tạo
    }

    public HoaDon() {
        //        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void initComponents() {
        this.setBackground(backgroundColor);
        this.setLayout(new GridLayout(1, 1));
        this.setBorder(new EmptyBorder(10, 10, 10, 10));
        this.setOpaque(true);

        panelContentCenter = new JPanel();
        panelContentCenter.setPreferredSize(new Dimension(1100, 600));
        panelContentCenter.setBackground(backgroundColor);
        panelContentCenter.setLayout(new BorderLayout(10, 20));
        this.add(panelContentCenter);

        // functionBar chứa các nút thực hiện: thêm, sửa, xóa, chi tiết, xuất
        panelFunctionBar = new PanelBorderRadius();
        panelFunctionBar.setPreferredSize(new Dimension(0, 100));
        panelFunctionBar.setLayout(new GridLayout(1, 2, 50, 0));
        panelFunctionBar.setBorder(new EmptyBorder(10, 10, 10, 10));

        String[] actionStrings = {"create", "update", "delete", "detail", "export"};
        mainFunction = new MainFunction(SOMEBITS, "hoadon", actionStrings);
        for (String action : actionStrings) {
            mainFunction.btn.get(action).addActionListener(this);
        }
        panelFunctionBar.add(mainFunction);

        searchField = new IntegratedSearch(new String[]{"Tất cả"});
        panelFunctionBar.add(searchField);

        panelContentCenter.add(panelFunctionBar, BorderLayout.NORTH);

        // panelMain: phần ở dưới để bảng
        panelMain = new PanelBorderRadius();
        BoxLayout boxLayout = new BoxLayout(panelMain, BoxLayout.Y_AXIS);
        panelMain.setLayout(boxLayout);
        panelContentCenter.add(panelMain, BorderLayout.CENTER);

        tableHoaDon = new JTable();
        tableHoaDon.setDefaultEditor(Object.class, null);
        scrollPaneTable = new JScrollPane();
        tableModelHoaDon = new DefaultTableModel();
        String[] header = new String[]{"Mã Hóa Đơn", "Ngày bán", "Trạng thái", "Tên Nhân viên"};
        tableModelHoaDon.setColumnIdentifiers(header);
        tableHoaDon.setModel(tableModelHoaDon);
        scrollPaneTable.setViewportView(tableHoaDon);

        DefaultTableCellRenderer centerCellRenderer = new DefaultTableCellRenderer();
        centerCellRenderer.setHorizontalAlignment(JLabel.CENTER);
        TableColumnModel columnModel = tableHoaDon.getColumnModel();
        columnModel.getColumn(0).setCellRenderer(centerCellRenderer);
        columnModel.getColumn(0).setPreferredWidth(50);
        columnModel.getColumn(1).setCellRenderer(centerCellRenderer);
        columnModel.getColumn(1).setPreferredWidth(300);
        columnModel.getColumn(2).setCellRenderer(centerCellRenderer);
        columnModel.getColumn(2).setPreferredWidth(200);

        tableHoaDon.setFocusable(false);
        panelMain.add(scrollPaneTable);
    }

    private void loadHoaDonData() throws SQLException {
        tableModelHoaDon.setRowCount(0); // Xóa dữ liệu cũ trước khi load dữ liệu mới
        List<HoaDonDTO> hoaDonList = hoaDonBUS.layDanhSachHoaDon();
        if (hoaDonList != null) {
            for (HoaDonDTO hoaDon : hoaDonList) {
                tableModelHoaDon.addRow(new Object[]{
                    hoaDon.getSoHD(),
                    hoaDon.getNgayBan().format(DISPLAY_DATE_FORMATTER), // Format the date here
                    hoaDon.getTrangThai() == 1 ? "Đã thanh toán" : "Đã hủy",
                    hoaDon.getTenNV() // Use the employee name
                });
            }
        } else {
            JOptionPane.showMessageDialog(this, "Không có hóa đơn để hiển thị.");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        if (actionCommand.equals("create")) {
            // Mở dialog thêm hóa đơn
            ChiTietHoaDonDialog dialog = new ChiTietHoaDonDialog(mainFrame, true);
            dialog.setVisible(true);
            try {
                loadHoaDonData();
            } catch (SQLException ex) {
                Logger.getLogger(HoaDon.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else if (actionCommand.equals("update")) {
            // Mở dialog sửa hóa đơn, truyền mã hóa đơn được chọn
            int selectedRow = tableHoaDon.getSelectedRow();
            if (selectedRow != -1) {
                String maHD = (String) tableHoaDon.getValueAt(selectedRow, 0);
                ChiTietHoaDonDialog dialog = new ChiTietHoaDonDialog(mainFrame, true, maHD);
                dialog.setVisible(true);
                try {
                    loadHoaDonData();
                } catch (SQLException ex) {
                    Logger.getLogger(HoaDon.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn để cập nhật.");
            }
        } else if (actionCommand.equals("delete")) {
            // Xử lý sự kiện xóa hóa đơn
            int selectedRow = tableHoaDon.getSelectedRow();
            if (selectedRow != -1) {
                String maHD = (String) tableHoaDon.getValueAt(selectedRow, 0);
                try {
                    if (hoaDonBUS.xoaHoaDon(maHD)) {
                        JOptionPane.showMessageDialog(this, "Xóa hóa đơn thành công!");
                        loadHoaDonData();
                    } else {
                        JOptionPane.showMessageDialog(this, "Xóa hóa đơn thất bại!");
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
                    Logger.getLogger(HoaDon.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn để xóa.");
            }
        } else if (actionCommand.equals("detail")) {
            // Xử lý sự kiện xem chi tiết hóa đơn
            int selectedRow = tableHoaDon.getSelectedRow();
            if (selectedRow != -1) {
                String maHD = (String) tableHoaDon.getValueAt(selectedRow, 0);
                ChiTietHoaDonDialog dialog = new ChiTietHoaDonDialog(mainFrame, true, maHD);
                dialog.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn để xem chi tiết.");
            }
        } else if (actionCommand.equals("export")) {
            // Xử lý sự kiện xuất hóa đơn
            try {
                exportToExcel();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Lỗi khi xuất Excel: " + ex.getMessage());
                Logger.getLogger(HoaDon.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void exportToExcel() throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Hóa Đơn");

        // Tạo header row
        Row headerRow = sheet.createRow(0);
        String[] headers = {"Mã Hóa Đơn", "Ngày bán", "Trạng thái", "Tên Nhân viên"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }

        // Lấy dữ liệu từ JTable và ghi vào Excel
        for (int i = 0; i < tableHoaDon.getRowCount(); i++) {
            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < tableHoaDon.getColumnCount(); j++) {
                Cell cell = row.createCell(j);
                Object value = tableHoaDon.getValueAt(i, j);
                if (value != null) {
                    cell.setCellValue(value.toString());
                } else {
                    cell.setCellValue(""); // Ghi chuỗi rỗng nếu giá trị là null
                }
            }
        }

        // Tự động điều chỉnh kích thước cột
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // Lưu file Excel
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Lưu file Excel");
        int userSelection = fileChooser.showSaveDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            java.io.File fileToSave = fileChooser.getSelectedFile();
            try (FileOutputStream fileOut = new FileOutputStream(fileToSave + ".xlsx")) {
                workbook.write(fileOut);
                JOptionPane.showMessageDialog(this, "Xuất Excel thành công!");
            } catch (IOException e) {
                throw new IOException("Error writing to file: " + e.getMessage(), e); // Wrap the original exception
            } finally {
                workbook.close();
            }
        }
    }
}
