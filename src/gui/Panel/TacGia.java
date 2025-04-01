package gui.Panel;

import gui.Componet.Custom.IntegratedSearch;
import gui.Componet.Custom.MainFunction;
import gui.Componet.Custom.PanelBorderRadius;
import gui.Main;
import bus.TacGiaBUS;
import dto.TacGiaDTO;
import helper.JTableExporter;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class TacGia extends JPanel implements ActionListener, ItemListener {

    PanelBorderRadius main, functionBar;
    JPanel contentCenter;
    JTable tbltacgia;
    JScrollPane scrollTable;
    MainFunction mainFunction;
    IntegratedSearch search;
    DefaultTableModel tbModel;
    TacGiaBUS tgbus = new TacGiaBUS();
    public ArrayList<TacGiaDTO> listtg = tgbus.getAllTacGia();

    Main m;
    Color BackgroundColor = new Color(240, 247, 250);

    public TacGia(Main m) {
        this.m = m;
        initComponent();
        tbltacgia.setDefaultEditor(Object.class, null);
        loadDataTable(listtg);

    }

    public void loadDataTable(ArrayList<TacGiaDTO> result) {
        tbModel.setRowCount(0);
        for (TacGiaDTO tg : result) {
            tbModel.addRow(new Object[]{
                tg.getMaTG(), tg.getTenTG(), tg.getLienLac()
            });
        }
    }

    private void initComponent() {
        this.setBackground(BackgroundColor);
        this.setLayout(new BorderLayout(0,0));
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

        String[] acStrings = {"create", "update", "delete", "detail", "import", "export"};
        mainFunction = new MainFunction(SOMEBITS, "tacgia", acStrings);
        for (String ac : acStrings) {
            mainFunction.btn.get(ac).addActionListener(this);
        }
        functionBar.add(mainFunction);

        search = new IntegratedSearch(new String[]{"Tất cả", "Mã tác giả", "Tên tác giả", "Liên lạc"});
        search.cbxChoose.addItemListener(this);
        search.txtSearchForm.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String type = (String) search.cbxChoose.getSelectedItem();
                String txt = search.txtSearchForm.getText();
                listtg = tgbus.search(txt, type);
                loadDataTable(listtg);
            }
        });
        search.btnReset.addActionListener(this);
        functionBar.add(search);

        contentCenter.add(functionBar, BorderLayout.NORTH);

        //main: phần ở dưới để bảng
        main = new PanelBorderRadius();
        BoxLayout boxLayout = new BoxLayout(main, BoxLayout.Y_AXIS);
        main.setLayout(boxLayout);
        contentCenter.add(main, BorderLayout.CENTER);

        tbltacgia = new JTable();
//        tbltacgia.setDefaultEditor(Object.class, null);
        scrollTable = new JScrollPane();
        tbModel = new DefaultTableModel();
        String[] header = new String[]{"Mã tác giả", "Tên tác giả", "Liên lạc"};
        tbModel.setColumnIdentifiers(header);
        tbltacgia.setModel(tbModel);
        tbltacgia.setFocusable(false);
        scrollTable.setViewportView(tbltacgia);
        DefaultTableCellRenderer cenCellRenderer = new DefaultTableCellRenderer();
        cenCellRenderer.setHorizontalAlignment(JLabel.CENTER);
        TableColumnModel columnModel = tbltacgia.getColumnModel();
        columnModel.getColumn(0).setCellRenderer(cenCellRenderer);
        columnModel.getColumn(0).setPreferredWidth(50);
        columnModel.getColumn(1).setCellRenderer(cenCellRenderer);
        columnModel.getColumn(1).setPreferredWidth(300);
        columnModel.getColumn(2).setCellRenderer(cenCellRenderer);
        columnModel.getColumn(2).setPreferredWidth(200);
        
        main.add(scrollTable);
        

    }

    public void openFile(String file) {
        try {
            File path = new File(file);
            Desktop.getDesktop().open(path);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void importExcel() {
        File excelFile;
        FileInputStream excelFIS = null;
        BufferedInputStream excelBIS = null;
        XSSFWorkbook excelJTableImport = null;
        JFileChooser jf = new JFileChooser();
        int result = jf.showOpenDialog(null);
        jf.setDialogTitle("Open file");
        int k = 0; // Đếm số dòng lỗi

        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                excelFile = jf.getSelectedFile();
                excelFIS = new FileInputStream(excelFile);
                excelBIS = new BufferedInputStream(excelFIS);
                excelJTableImport = new XSSFWorkbook(excelBIS);
                XSSFSheet excelSheet = excelJTableImport.getSheetAt(0);

                for (int row = 1; row <= excelSheet.getLastRowNum(); row++) {
                    int check = 1; // Kiểm tra dữ liệu hợp lệ
                    XSSFRow excelRow = excelSheet.getRow(row);

                    String maTG = excelRow.getCell(0).getStringCellValue();
                    String tenTG = excelRow.getCell(1).getStringCellValue();
                    String lienLac = excelRow.getCell(2) != null ? excelRow.getCell(2).getStringCellValue() : "";

                    // Kiểm tra dữ liệu
                    if (maTG == null || maTG.trim().isEmpty() || tenTG == null || tenTG.trim().isEmpty() || tgbus.has(maTG)) {
                        check = 0; // Dữ liệu không hợp lệ (trống hoặc mã trùng)
                    }

                    if (check == 0) {
                        k += 1; // Tăng số dòng lỗi
                    } else {
                        TacGiaDTO tacGia = new TacGiaDTO(maTG, tenTG, lienLac, 1); // trangThai mặc định là 1
                        tgbus.addTacGia(tacGia); // Thêm vào database
                    }
                }

                // Cập nhật lại danh sách và bảng
                listtg = tgbus.getAllTacGia();
                loadDataTable(listtg);

                if (k != 0) {
                    JOptionPane.showMessageDialog(this, "Có " + k + " dòng dữ liệu không hợp lệ không được thêm!");
                } else {
                    JOptionPane.showMessageDialog(this, "Nhập dữ liệu từ Excel thành công!");
                }

            } catch (FileNotFoundException ex) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy file!");
                ex.printStackTrace();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Lỗi khi đọc file Excel!");
                ex.printStackTrace();
            } finally {
                try {
                    if (excelBIS != null) {
                        excelBIS.close();
                    }
                    if (excelFIS != null) {
                        excelFIS.close();
                    }
                    if (excelJTableImport != null) {
                        excelJTableImport.close();
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public int getRowSelected() {
        int index = tbltacgia.getSelectedRow();
        if (index == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn tác giả");
        }
        return index;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == mainFunction.btn.get("create")) {
            // Giả định có TacGiaDialog để thêm tác giả
            // TacGiaDialog dialog = new TacGiaDialog(this, m, "Thêm tác giả", true, "create");
            JOptionPane.showMessageDialog(this, "Chức năng thêm tác giả chưa được triển khai!");
        } else if (e.getSource() == mainFunction.btn.get("update")) {
            int index = getRowSelected();
            if (index != -1) {
                // Giả định có TacGiaDialog để chỉnh sửa tác giả
                // TacGiaDialog dialog = new TacGiaDialog(this, m, "Chỉnh sửa tác giả", true, "update", listtg.get(index));
                JOptionPane.showMessageDialog(this, "Chức năng chỉnh sửa tác giả chưa được triển khai!");
            }
        } else if (e.getSource() == mainFunction.btn.get("delete")) {
            int index = getRowSelected();
            if (index != -1) {
                int input = JOptionPane.showConfirmDialog(null,
                        "Bạn có chắc chắn muốn xóa tác giả này?", "Xóa tác giả",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
                if (input == 0) {
                    tgbus.deleteTacGia(listtg.get(index).getMaTG());
                    listtg = tgbus.getAllTacGia(); // Cập nhật danh sách từ database
                    loadDataTable(listtg);
                    JOptionPane.showMessageDialog(this, "Xóa tác giả thành công!");
                }
            }
        } else if (e.getSource() == mainFunction.btn.get("detail")) {
            int index = getRowSelected();
            if (index != -1) {
                // Giả định có TacGiaDialog để xem chi tiết
                // TacGiaDialog dialog = new TacGiaDialog(this, m, "Chi tiết tác giả", true, "view", listtg.get(index));
                JOptionPane.showMessageDialog(this, "Chức năng xem chi tiết tác giả chưa được triển khai!");
            }
        } else if (e.getSource() == mainFunction.btn.get("import")) {
            importExcel(); // Gọi phương thức importExcel
        } else if (e.getSource() == mainFunction.btn.get("export")) {
            try {
                JTableExporter.exportJTableToExcel(tbltacgia);
                JOptionPane.showMessageDialog(this, "Xuất file Excel thành công!");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Lỗi khi xuất file Excel!");
                ex.printStackTrace();
            }
        } else if (e.getSource() == search.btnReset) {
            search.txtSearchForm.setText("");
            listtg = tgbus.getAllTacGia();
            loadDataTable(listtg);
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        String type = (String) search.cbxChoose.getSelectedItem();
        String txt = search.txtSearchForm.getText();
        // listtg = tgbus.search(txt, type); // Dòng này bị comment
        // loadDataTable(listtg);
    }
}
