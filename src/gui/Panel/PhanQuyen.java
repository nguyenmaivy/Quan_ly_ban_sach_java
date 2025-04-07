package gui.Panel;

import bus.PhanQuyenBUS;
import dto.nhomQuyenDTO;
import gui.Componet.Custom.IntegratedSearch;
import gui.Componet.Custom.MainFunction;
import gui.Componet.Custom.PanelBorderRadius;
import gui.Dialog.PhanQuyenDialog;
import gui.Main;
import helper.JTableExporter;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

public class PhanQuyen extends JPanel implements ActionListener {

    JFrame owner = (JFrame) SwingUtilities.getWindowAncestor(this);
    PanelBorderRadius main, functionBar;
    JPanel contentCenter;
    JTable tblnhomquyen;
    JScrollPane scrollTable;
    MainFunction mainFunction;
    IntegratedSearch search;
    DefaultTableModel tblModel;
    Main m;
    public PhanQuyenBUS PhanQuyenBUS = new PhanQuyenBUS();
    public ArrayList<nhomQuyenDTO> pqdao = PhanQuyenBUS.getALL();

    Color BackgroundColor = new Color(240, 247, 250);

    public PhanQuyen(Main m) {
        this.m = m;
        initComponent();
        loadDataTable(pqdao);
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

        // functionBar là thanh bên trên chứa các nút chức năng: thêm, xóa, sửa và tìm kiếm....
        functionBar = new PanelBorderRadius();
        functionBar.setPreferredSize(new Dimension(0, 100));
        functionBar.setLayout(new GridLayout(1, 2, 50, 0));
        functionBar.setBorder(new EmptyBorder(10, 10, 10, 10));

        String[] actionStrings = {"create", "update", "delete", "detail", "export"};
        mainFunction = new MainFunction(SOMEBITS, "nhomquyen", actionStrings);
        for (String ac : actionStrings) {
            mainFunction.btn.get(ac).addActionListener(this);
        }
        mainFunction.btn.get("create").setEnabled(false);
        mainFunction.btn.get("update").setEnabled(false);
        functionBar.add(mainFunction);

        search = new IntegratedSearch(new String[]{"Tất cả"});
        functionBar.add(search);

        contentCenter.add(functionBar, BorderLayout.NORTH);

        // main là phần ở dưới để bảng
        main = new PanelBorderRadius();
        BoxLayout boxLayout = new BoxLayout(main, BoxLayout.Y_AXIS);
        main.setLayout(boxLayout);
        contentCenter.add(main, BorderLayout.CENTER);

        tblnhomquyen = new JTable();
        tblnhomquyen.setDefaultEditor(Object.class, null);
        scrollTable = new JScrollPane();
        tblModel = new DefaultTableModel();
        String[] header = new String[]{"STT", "Mã nhóm quyền", "Tên nhóm quyền"};
        tblModel.setColumnIdentifiers(header);
        tblnhomquyen.setModel(tblModel);
        scrollTable.setViewportView(tblnhomquyen);
        DefaultTableCellRenderer cenCellRenderer = new DefaultTableCellRenderer();
        cenCellRenderer.setHorizontalAlignment(JLabel.CENTER);
        TableColumnModel columnModel = tblnhomquyen.getColumnModel();
        columnModel.getColumn(0).setCellRenderer(cenCellRenderer);
        columnModel.getColumn(0).setPreferredWidth(2);
        columnModel.getColumn(1).setCellRenderer(cenCellRenderer);
        columnModel.getColumn(1).setPreferredWidth(300);
        columnModel.getColumn(2).setCellRenderer(cenCellRenderer);
        columnModel.getColumn(2).setPreferredWidth(500);
        tblnhomquyen.setFocusable(false);
        main.add(scrollTable);
    }

    public void loadDataTable(ArrayList<nhomQuyenDTO> result) {
        tblModel.setRowCount(0);
        int stt = 1;
        for (nhomQuyenDTO nqDTO : result) {
            tblModel.addRow(new Object[]{
                stt++, nqDTO.getMaNhomQuyen(), nqDTO.getTenNhomQuyen()
            });
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == mainFunction.btn.get("create")) {
            // Mở PhanQuyenDialog để thêm nhóm quyền
            PhanQuyenDialog pqDialog = new PhanQuyenDialog(PhanQuyenBUS, this, owner, "Thêm nhóm quyền", true, "create");
            pqDialog.setVisible(true);
        } else if (source == mainFunction.btn.get("update")) {
            int index = this.getRowSelected();
            if (index >= 0) {
                // Mở PhanQuyenDialog để chỉnh sửa nhóm quyền đã chọn
                PhanQuyenDialog pqDialog = new PhanQuyenDialog(PhanQuyenBUS, this, owner, "Chỉnh sửa nhóm quyền", true, "update", pqdao.get(index));
                pqDialog.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "Vui lòng chọn một nhóm quyền để chỉnh sửa!");
            }
        } else if (source == mainFunction.btn.get("detail")) {
            int index = this.getRowSelected();
            if (index >= 0) {
                // Mở PhanQuyenDialog để xem chi tiết nhóm quyền
                PhanQuyenDialog pqDialog = new PhanQuyenDialog(PhanQuyenBUS, this, owner, "Chi tiết nhóm quyền", true, "view", pqdao.get(index));
                pqDialog.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "Vui lòng chọn một nhóm quyền để xem chi tiết!");
            }
        } else if (source == mainFunction.btn.get("delete")) {
            int index = this.getRowSelected();
            if (index >= 0) {
                int input = JOptionPane.showConfirmDialog(null,
                        "Bạn có chắc chắn muốn xóa nhóm quyền này?",
                        "Xóa nhóm quyền",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.INFORMATION_MESSAGE);

                if (input == JOptionPane.OK_OPTION) {
                    PhanQuyenBUS.deletePQ(String.valueOf(pqdao.get(index).getMaNhomQuyen())); // Sử dụng deletePQ từ BUS
                    JOptionPane.showMessageDialog(null, "Nhóm quyền đã được xóa!");
                    loadDataTable(PhanQuyenBUS.getALL()); // Cập nhật lại bảng dữ liệu
                }
            } else {
                JOptionPane.showMessageDialog(null, "Vui lòng chọn một nhóm quyền để xóa!");
            }
        } else if (source == mainFunction.btn.get("export")) {
            try {
                JTableExporter.exportJTableToExcel(tblnhomquyen);
                JOptionPane.showMessageDialog(null, "Xuất Excel thành công!");
            } catch (IOException ex) {
                Logger.getLogger(PhanQuyen.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "Lỗi khi xuất Excel!");
            }
        } else if (source == this.search.btnReset) {
            loadDataTable(PhanQuyenBUS.getALL()); // Reset lại bảng danh sách nhóm quyền
        }

    }

    public int getRowSelected() {
        int index = tblnhomquyen.getSelectedRow();
        if (tblnhomquyen.getSelectedRow() < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn nhóm quyền");
            return -1;
        }
        return index;
    }

}
