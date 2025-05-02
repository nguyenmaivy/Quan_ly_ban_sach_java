
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

    public HoaDon(Main mainFrame){ // Đổi tên biến main thành mainFrame nếu cần
        this.mainFrame = mainFrame; // Store the main frame
        initComponents();
//        loadHoaDonData(); // Load dữ liệu hóa đơn khi khởi tạo
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

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}