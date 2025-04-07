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
import gui.Main;
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

    public HoaDon(Main mainFrame) throws SQLException { // Đổi tên biến main thành mainFrame nếu cần
        initComponents();
        loadHoaDonData(); // Load dữ liệu hóa đơn khi khởi tạo
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
        String[] header = new String[]{"Mã Hóa Đơn", "Ngày bán", "Trạng thái", "Tên Nhân viên xuất hóa đơn"};
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
        for (HoaDonDTO hoaDon : hoaDonList) {
            tableModelHoaDon.addRow(new Object[]{
                hoaDon.getSoHD(),
                hoaDon.getNgayBan(),
                hoaDon.getTrangThai(),
                hoaDon.getMaNV() // Giả sử MaNV là tên nhân viên
            });
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        if (actionCommand.equals("create")) {
            // Xử lý sự kiện tạo hóa đơn
            // ...
        } else if (actionCommand.equals("update")) {
            // Xử lý sự kiện cập nhật hóa đơn
            // ...
        } else if (actionCommand.equals("delete")) {
            // Xử lý sự kiện xóa hóa đơn
            // ...
        } else if (actionCommand.equals("detail")) {
            // Xử lý sự kiện xem chi tiết hóa đơn
            // ...
        } else if (actionCommand.equals("export")) {
            // Xử lý sự kiện xuất hóa đơn
            // ...
        }
    }

   
    
}
