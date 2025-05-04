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

import bus.HoaDonBUS;
import dto.HoaDonDTO;
import gui.Componet.Custom.IntegratedSearch;
import gui.Componet.Custom.MainFunction;
import gui.Componet.Custom.PanelBorderRadius;
import gui.Dialog.ChiTietHoaDonDialog;
import gui.Dialog.ChiTietHoaDonDialog; // Import dialog thêm/sửa
import gui.Main;

import java.time.format.DateTimeFormatter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class HoaDon extends JPanel implements ActionListener {

    PanelBorderRadius panelMain, panelFunctionBar;
    JPanel panelContentCenter;
    JTable tableHoaDon;
    JScrollPane scrollPaneTable;
    MainFunction mainFunction;
    IntegratedSearch searchField;
    DefaultTableModel tableModelHoaDon;
    HoaDonBUS hoaDonBUS = new HoaDonBUS();
    Color backgroundColor = new Color(240, 247, 250);
    private Main mainFrame;
    private static final DateTimeFormatter DISPLAY_DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public HoaDon(Main mainFrame){
        this.mainFrame = mainFrame;
        initComponents();
        loadHoaDonData();
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


        panelContentCenter.add(panelFunctionBar, BorderLayout.NORTH);

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
        columnModel.getColumn(0).setPreferredWidth(100);
        columnModel.getColumn(1).setCellRenderer(centerCellRenderer);
        columnModel.getColumn(1).setPreferredWidth(150);
        columnModel.getColumn(2).setCellRenderer(centerCellRenderer);
        columnModel.getColumn(2).setPreferredWidth(100);
        columnModel.getColumn(3).setPreferredWidth(200); // Thêm kích thước cho cột tên nhân viên

        tableHoaDon.setFocusable(false);
        panelMain.add(scrollPaneTable);
    }


    private void loadHoaDonData() {
        try {
            List<HoaDonDTO> danhSachHoaDon = hoaDonBUS.getAllHoaDon();
            System.out.println("Kích thước danh sách hóa đơn: " + danhSachHoaDon.size());
            tableModelHoaDon.setRowCount(0);

            for (HoaDonDTO hd : danhSachHoaDon) {
                String soHD = hd.getSoHD();
                String ngayBan = hd.getNgayBan() != null ? hd.getNgayBan().format(DISPLAY_DATE_FORMATTER) : "";
                String trangThai = hd.getTrangThai() == 1 ? "Đã thanh toán" : "Đã hủy";
                String tenNV = hd.getTenNV(); // Lấy tên nhân viên

                Object[] rowData = {soHD, ngayBan, trangThai, tenNV};
                tableModelHoaDon.addRow(rowData);
            }
            System.out.println("Số hàng trong tableModel: " + tableModelHoaDon.getRowCount());
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu hóa đơn: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        System.out.println("Action performed: " + command); // In ra action command
        if (command.equals("THÊM")) {
            System.out.println("Creating ChiTietHoaDonDialog..."); // In ra khi vào nhánh create
            ChiTietHoaDonDialog dialog = new ChiTietHoaDonDialog(mainFrame, true, this, null, false);
            System.out.println("ChiTietHoaDonDialog created."); // In ra sau khi tạo dialog
            dialog.setVisible(true);
            System.out.println("ChiTietHoaDonDialog setVisible(true)."); // In ra sau khi gọi setVisible
        } else if (command.equals("SỬA")) {
            int selectedRow = tableHoaDon.getSelectedRow();
            if (selectedRow >= 0) {
                String soHD = (String) tableModelHoaDon.getValueAt(selectedRow, 0);
                try {
                    HoaDonDTO hoaDon = hoaDonBUS.getByMaHD(soHD);
                    if (hoaDon != null) {
                        ChiTietHoaDonDialog dialog = new ChiTietHoaDonDialog(mainFrame, true, this, hoaDon, false);
                        dialog.setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(this, "Không tìm thấy hóa đơn để sửa.");
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "Lỗi khi lấy thông tin hóa đơn: " + ex.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một hóa đơn để sửa.");
            }
        } else if (command.equals("XÓA")) {
            int selectedRow = tableHoaDon.getSelectedRow();
            if (selectedRow >= 0) {
                String soHD = (String) tableModelHoaDon.getValueAt(selectedRow, 0);
                int option = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa hóa đơn " + soHD + "?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    try {
                        if (hoaDonBUS.xoaHoaDon(soHD)) {
                            JOptionPane.showMessageDialog(this, "Xóa hóa đơn thành công.");
                            loadHoaDonData();
                        } else {
                            JOptionPane.showMessageDialog(this, "Xóa hóa đơn thất bại.");
                        }
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(this, "Lỗi khi xóa hóa đơn: " + ex.getMessage());
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một hóa đơn để xóa.");
            }
        } else if (command.equals("CHI TIẾT")) {
            int selectedRow = tableHoaDon.getSelectedRow();
            if (selectedRow >= 0) {
                try {
                    String soHD = (String) tableModelHoaDon.getValueAt(selectedRow, 0);
                    HoaDonDTO hoaDon = hoaDonBUS.getByMaHD(soHD);
                    if (hoaDon != null) {
                        ChiTietHoaDonDialog dialog = new ChiTietHoaDonDialog(mainFrame, true, this, hoaDon, true);
                        dialog.setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(this, "Không tìm thấy hóa đơn với mã: " + soHD);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(HoaDon.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một hóa đơn để xem chi tiết.");
            }
        } else if (command.equals("XUẤT EXCEL")) {
            exportToExcel(); // Gọi phương thức exportToExcel của panel này
        }
    }

    public void reloadData() {
        loadHoaDonData();
    }

    private void exportToExcel() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Lưu file Excel");
        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            java.io.File fileToSave = fileChooser.getSelectedFile();
            String filePath = fileToSave.getAbsolutePath();
            if (!filePath.endsWith(".xlsx")) {
                filePath += ".xlsx";
            }

            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Hóa Đơn");

            // Tạo header
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < tableModelHoaDon.getColumnCount(); i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(tableModelHoaDon.getColumnName(i));
            }

            // Đổ dữ liệu
            for (int row = 0; row < tableModelHoaDon.getRowCount(); row++) {
                Row dataRow = sheet.createRow(row + 1);
                for (int col = 0; col < tableModelHoaDon.getColumnCount(); col++) {
                    Cell cell = dataRow.createCell(col);
                    Object value = tableModelHoaDon.getValueAt(row, col);
                    cell.setCellValue(value != null ? value.toString() : "");
                }
            }

            // Ghi file
            try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
                workbook.write(outputStream);
                JOptionPane.showMessageDialog(this, "Xuất dữ liệu thành công đến: " + filePath);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Lỗi khi xuất file Excel: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            } finally {
                try {
                    workbook.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}