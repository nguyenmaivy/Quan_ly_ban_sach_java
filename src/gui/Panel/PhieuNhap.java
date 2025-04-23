package gui.Panel;

import bus.NhaXuatBanBUS;
import bus.NhanVienBUS;
import bus.PhieuNhapBUS;
import bus.KhoSachBUS;
import gui.Componet.Custom.MainFunction;
import gui.Componet.Custom.PanelBorderRadius;
import gui.Componet.Custom.TableSorter;
import gui.Main;
import dto.NhanVienDTO;
import dto.PhieuNhapDTO;
import gui.Dialog.ChiTietPhieuDialog;
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
import java.util.ArrayList;
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

public final class PhieuNhap extends JPanel implements ActionListener, KeyListener, PropertyChangeListener, ItemListener {
    public JFrame owner = (JFrame) SwingUtilities.getWindowAncestor(this);
    PanelBorderRadius main, functionBar;
    JPanel pnlBorder1, pnlBorder2, pnlBorder3, pnlBorder4, contentCenter;
    JTable tablePhieuNhap;
    JScrollPane scrollTablePhieuNhap;
    MainFunction mainFunction;
    DefaultTableModel tblModel;

    Color BackgroundColor = new Color(240, 247, 250);
    TaoPhieuNhap nhapkho;
    Main m;
    NhanVienDTO nv;

    PhieuNhapBUS phieunhapBUS = new PhieuNhapBUS();
    NhaXuatBanBUS nxbbus = new NhaXuatBanBUS();
    NhanVienBUS nvbus = new NhanVienBUS();
    KhoSachBUS khobus = new KhoSachBUS();
    ArrayList<PhieuNhapDTO> listPhieu = phieunhapBUS.getAllPhieuNhap();

    public PhieuNhap(Main m, NhanVienDTO nv) {
        this.m = m;
        this.nv = nv;
        initComponent();
        loadDataTable(listPhieu);
    }

    public void initPadding() {
        pnlBorder1 = new JPanel();
        pnlBorder1.setPreferredSize(new Dimension(0, 10));
        pnlBorder1.setBackground(BackgroundColor);
        this.add(pnlBorder1, BorderLayout.NORTH);

        pnlBorder2 = new JPanel();
        pnlBorder2.setPreferredSize(new Dimension(0, 10));
        pnlBorder2.setBackground(BackgroundColor);
        this.add(pnlBorder2, BorderLayout.SOUTH);

        pnlBorder3 = new JPanel();
        pnlBorder3.setPreferredSize(new Dimension(10, 0));
        pnlBorder3.setBackground(BackgroundColor);
        this.add(pnlBorder3, BorderLayout.EAST);

        pnlBorder4 = new JPanel();
        pnlBorder4.setPreferredSize(new Dimension(10, 0));
        pnlBorder4.setBackground(BackgroundColor);
        this.add(pnlBorder4, BorderLayout.WEST);
    }

    private void initComponent() {
        this.setBackground(BackgroundColor);
        this.setLayout(new BorderLayout(0, 0));
        this.setOpaque(true);

        tablePhieuNhap = new JTable();
        scrollTablePhieuNhap = new JScrollPane();
        tblModel = new DefaultTableModel();
        String[] header = new String[]{"STT", "Mã phiếu nhập", "Nhà xuất bản", "Kho", "Nhân viên nhập", "Thời gian", "Tổng tiền"};
        tblModel.setColumnIdentifiers(header);
        tablePhieuNhap.setModel(tblModel);
        tablePhieuNhap.setDefaultEditor(Object.class, null);
        scrollTablePhieuNhap.setViewportView(tablePhieuNhap);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tablePhieuNhap.setDefaultRenderer(Object.class, centerRenderer);
        tablePhieuNhap.setFocusable(false);
        tablePhieuNhap.getColumnModel().getColumn(0).setPreferredWidth(10);
        tablePhieuNhap.getColumnModel().getColumn(1).setPreferredWidth(10);
        tablePhieuNhap.getColumnModel().getColumn(2).setPreferredWidth(200);

        tablePhieuNhap.setAutoCreateRowSorter(true);
        TableSorter.configureTableColumnSorter(tablePhieuNhap, 0, TableSorter.INTEGER_COMPARATOR);
        TableSorter.configureTableColumnSorter(tablePhieuNhap, 1, TableSorter.INTEGER_COMPARATOR);
        TableSorter.configureTableColumnSorter(tablePhieuNhap, 5, TableSorter.VND_CURRENCY_COMPARATOR);

        this.setBackground(BackgroundColor);
        this.setLayout(new BorderLayout(0, 0));
        this.setOpaque(true);

        initPadding();

        contentCenter = new JPanel();
        contentCenter.setPreferredSize(new Dimension(1100, 600));
        contentCenter.setBackground(BackgroundColor);
        contentCenter.setLayout(new BorderLayout(10, 10));
        this.add(contentCenter, BorderLayout.CENTER);

        functionBar = new PanelBorderRadius();
        functionBar.setPreferredSize(new Dimension(0, 100));
        functionBar.setLayout(new GridLayout(1, 2, 50, 0));
        functionBar.setBorder(new EmptyBorder(10, 10, 10, 10));

        String[] action = {"create", "detail", "cancel", "export"};
        mainFunction = new MainFunction(SOMEBITS, "nhaphang", action);
        for (String ac : action) {
            mainFunction.btn.get(ac).addActionListener(this);
        }
        functionBar.add(mainFunction);


        contentCenter.add(functionBar, BorderLayout.NORTH);

        main = new PanelBorderRadius();
        BoxLayout boxly = new BoxLayout(main, BoxLayout.Y_AXIS);
        main.setLayout(boxly);
        main.setBorder(new EmptyBorder(0, 0, 0, 0));
        contentCenter.add(main, BorderLayout.CENTER);
        main.add(scrollTablePhieuNhap);
    }

    public int getRowSelected() {
        int index = tablePhieuNhap.getSelectedRow();
        if (index == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn phiếu nhập");
        }
        return index;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object sourObject = e.getSource();
        if (sourObject == mainFunction.btn.get("create")) {
            if (nv == null) {
                JOptionPane.showMessageDialog(this, "Không thể tạo phiếu nhập: Thông tin nhân viên không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            nhapkho = new TaoPhieuNhap(nv, "create", m, this); // Truyền this vào
            m.setPanel(nhapkho);
        } else if (sourObject == mainFunction.btn.get("detail")) {
            int index = getRowSelected();
            if (index != -1) {
                ChiTietPhieuDialog ctsp = new ChiTietPhieuDialog(m, "Thông tin phiếu nhập", true, listPhieu.get(index));
            }
        } else if (sourObject == mainFunction.btn.get("cancel")) {
            int index = getRowSelected();
            if (index != -1) {
                int input = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn hủy phiếu?", "Hủy phiếu", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
                if (input == 0) {
                    PhieuNhapDTO pn = listPhieu.get(index);
                    System.out.println(pn);
                    if (!phieunhapBUS.checkCancelPn(pn.getSoPN())) {
                        JOptionPane.showMessageDialog(null, "Sản phẩm trong phiếu này đã được xuất đi không thể hủy phiếu này!");
                    } else {
                        boolean isDeleted = phieunhapBUS.cancelPhieuNhap(pn.getSoPN());
                        if (!isDeleted) {
                            JOptionPane.showMessageDialog(null, "Hủy phiếu không thành công!");
                        } else {
                            JOptionPane.showMessageDialog(null, "Hủy phiếu thành công");
                            loadDataTable(phieunhapBUS.getAllPhieuNhap());
                        }
                    }
                }
            }
        } else if (e.getSource() == mainFunction.btn.get("export")) {
            boolean success = JTableExporter.exportJTableToExcel(tablePhieuNhap);
            if (success) {
                JOptionPane.showMessageDialog(this, "Xuất file Excel thành công!");
            }
        }
    }

    public void loadDataTable(ArrayList<PhieuNhapDTO> listpPhieuNhap) {
        tblModel.setRowCount(0);
        int stt = 1;
        for (PhieuNhapDTO phieuNhapDTO : listpPhieuNhap) {
            String tenNXB = nxbbus.getTenNXBByMa(phieuNhapDTO.getMaNXB());
            String tenNV = nvbus.getTenNVByMa(phieuNhapDTO.getMaNV());
            String tenKho = khobus.getTenKhoByMa(phieuNhapDTO.getMaKho());

            tblModel.addRow(new Object[]{
                stt++, phieuNhapDTO.getSoPN(),
                tenNXB,
                tenKho,
                tenNV,
                phieuNhapDTO.getNgayNhap(),
                phieuNhapDTO.getTongTien()
            });
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void keyPressed(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void keyReleased(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
