

package gui.Panel;

import javax.swing.JPanel;
import bus.NhaXuatBanBUS;
import gui.Componet.Custom.IntegratedSearch;
import gui.Componet.Custom.MainFunction;
import gui.Componet.Custom.PanelBorderRadius;
import gui.Componet.Custom.TableSorter;
import gui.Main;
import dto.NhaXuatBanDTO;
import dto.TaiKhoanDTO;
import gui.Dialog.NhaXuatBanDialog;
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

import javax.swing.JTable;
import javax.swing.table.TableModel;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;



public class NhaXuatBan extends JPanel implements ActionListener{
    PanelBorderRadius main, functionBar;
    JPanel contentCenter;
    JTable tblnxb;
    JScrollPane scrollTable;
    MainFunction mainFunction;
    IntegratedSearch search;
    DefaultTableModel tbModel;
    
    Main m;
    Color BackgroundColor = new Color(240, 247, 250);
    
    public NhaXuatBan(Main m){
        this.m = m;
        initComponent();
        loadDataTable(); // Thêm dòng này để load danh sách ngay khi mở giao diện
    }
    
    private void initComponent(){
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
        mainFunction = new MainFunction(SOMEBITS, "nhaxuatban", acStrings);
        for(String ac : acStrings){
            mainFunction.btn.get(ac).addActionListener(this);
        }
        functionBar.add(mainFunction);
        
        search = new IntegratedSearch(new String[]{"Tất cả", "Mã nhà xuất bản", "Tên nhà xuất bản", "Địa chỉ", "Số điện thoại"});        
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
        
        //main: phần ở dưới để bảng
        main = new PanelBorderRadius();
        BoxLayout boxLayout = new BoxLayout(main, BoxLayout.Y_AXIS);
        main.setLayout(boxLayout);
        contentCenter.add(main, BorderLayout.CENTER);
        
        tblnxb = new JTable();
        tblnxb.setDefaultEditor(Object.class, null);
        scrollTable = new JScrollPane();
        tbModel = new DefaultTableModel();
        String[] header = new String[]{"Mã nhà xuất bản", "Tên nhà xuất bản", "Địa chỉ", "Số điện thoại"};
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


//    @Override
//    public void actionPerformed(ActionEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
//    }
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        NhaXuatBanBUS nxbBUS = new NhaXuatBanBUS();  // Sử dụng chung một đối tượng BUS

       if (source == mainFunction.btn.get("create")) {
        NhaXuatBanDialog dialog = new NhaXuatBanDialog(nxbBUS, 
                             (JFrame) SwingUtilities.getWindowAncestor(this), 
                             "Thêm Nhà Xuất Bản", true, "create", null);
        loadDataTable();  // Cập nhật bảng sau khi thêm


        } else if (source == mainFunction.btn.get("update")) {
            int selectedRow = tblnxb.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một nhà xuất bản để sửa!");
                return;
            }

            String manxb  = tbModel.getValueAt(selectedRow, 0).toString();
            NhaXuatBanDTO nhaXuatBan = nxbBUS.getByID(manxb);

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
        } else if (source == mainFunction.btn.get("detail")) {
            int selectedRow = tblnxb.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một nhà xuất bản để xem chi tiết!");
                return;
            }

            String manxb = tbModel.getValueAt(selectedRow, 0).toString();
            NhaXuatBanDTO nhaXuatBan = nxbBUS.getByID(manxb);

            if (nhaXuatBan != null) {
                NhaXuatBanDialog dialog = new NhaXuatBanDialog(nxbBUS,
                                         (JFrame) SwingUtilities.getWindowAncestor(this),
                                         "Chi tiết Nhà Xuất Bản", true, "detail", nhaXuatBan);
                // Không load lại bảng vì không có thay đổi
            } else {
                JOptionPane.showMessageDialog(this, "Không tìm thấy nhà xuất bản!");
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
    
     private void loadDataTable() {
        NhaXuatBanBUS nxbBUS = new NhaXuatBanBUS();
        ArrayList<NhaXuatBanDTO> nxbList = nxbBUS.getAllNhaXuatBan();

        // Xóa toàn bộ dữ liệu cũ trong bảng
        tbModel.setRowCount(0);

        // Thêm dữ liệu mới vào bảng
        for (NhaXuatBanDTO nxb : nxbList) {
            tbModel.addRow(new Object[]{
                (nxb.getMaNXB() != null) ? nxb.getMaNXB() : "",  // Tránh null
                nxb.getTenNXB(),                  
                nxb.getDiachiNXB(),
                nxb.getSdt(),
               
                 
            });

        }
    }
    private void searchData() {
        String keyword = search.txtSearchForm.getText().trim().toLowerCase();
        String category = search.cbxChoose.getSelectedItem().toString();


        NhaXuatBanBUS nxbBUS = new NhaXuatBanBUS();
        ArrayList<NhaXuatBanDTO> list = nxbBUS.getAllNhaXuatBan();

        tbModel.setRowCount(0); // Clear bảng trước

        for (NhaXuatBanDTO nxb : list) {
            String ma = (nxb.getMaNXB() != null) ? nxb.getMaNXB().toLowerCase() : "";
            String ten = (nxb.getTenNXB() != null) ? nxb.getTenNXB().toLowerCase() : "";
            String sdt = (nxb.getSdt() != null) ? nxb.getSdt().toLowerCase() : "";
            String diachi = (nxb.getDiachiNXB() != null) ? nxb.getDiachiNXB().toLowerCase() : "";
                        
            boolean match = false;

            switch (category) {
                case "Tất cả":
                    match = ma.contains(keyword) || ten.contains(keyword) || sdt.contains(keyword) || diachi.contains(keyword);
                    break;
                case "Mã nhà xuất bản":
                    match = ma.contains(keyword);
                    break;
                case "Tên nhà xuất bản":
                    match = ten.contains(keyword);
                    break;
                case "Địa chỉ":
                    match = diachi.contains(keyword);
                    break;
                case "Số điện thoại":
                    match = sdt.contains(keyword);
                    break;                 
            }

            if (match) {
                tbModel.addRow(new Object[]{
                    nxb.getMaNXB(), nxb.getTenNXB(),  nxb.getDiachiNXB(), nxb.getSdt(),
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





