package gui.Panel;

import bus.*;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import dao.NhaXuatBanDAO;
import dto.*;
import gui.Componet.Custom.ButtonCustom;
import gui.Componet.Custom.InputForm;
import gui.Componet.Custom.NumericDocumentFilter;
import gui.Componet.Custom.PanelBorderRadius;
import gui.Componet.Custom.SelectForm;
import gui.Main;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.text.PlainDocument;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public final class TaoPhieuNhap extends JPanel implements ItemListener, ActionListener {

    PanelBorderRadius right, left;
    JPanel pnlBorder1, pnlBorder2, pnlBorder3, pnlBorder4, contentCenter, left_top, main, content_right_bottom, content_btn;
    JTable tablePhieuNhap, tableSanPham;
    JScrollPane scrollTablePhieuNhap, scrollTableSanPham;
    DefaultTableModel tblModel, tblModelSP;
    ButtonCustom btnAddSp, btnEditSP, btnDelete, btnImport, btnNhapHang;
    InputForm txtMaphieu, txtNhanVien, txtMaSp, txtTenSp, txtDongia, txtSoLuong, txtTacGia, txtTheLoai;
    SelectForm cbxNXB, cbxTacGia, cbxTheLoai;
    JTextField txtTimKiem;
    JLabel lbltongtien;
    Main m;
    Color BackgroundColor = new Color(240, 247, 250);
    JFrame owner = (JFrame) SwingUtilities.getWindowAncestor(this);

    PhieuNhapBUS phieunhapBus = new PhieuNhapBUS();
    NhanVienDTO nvDto;
    SachBUS sachBus = new SachBUS();
    TheLoaiBUS theLoaiBUS = new TheLoaiBUS();
    NhaXuatBanBUS nxbbus = new NhaXuatBanBUS();
    NhaXuatBanDAO nxbDAO = new NhaXuatBanDAO();

    ArrayList<dto.SachDTO> listSP = sachBus.getAllSach();
    private SachDTO selectedSach;
    private PhieuNhap phieuNhapPanel;
    ArrayList<NhaXuatBanDTO> nhaXuatBanDTO = nxbDAO.getALL();
    ArrayList<ChiTietPhieuNhapDTO> chiTietPhieuNhapDTO;

    String maphieunhap;
    int rowPhieuSelect = -1;

    public TaoPhieuNhap(NhanVienDTO nv, String type, Main m, PhieuNhap phieuNhapPanel) {
        this.nvDto = nv;
        this.m = m;
        this.phieuNhapPanel = phieuNhapPanel;
        initComponent(type);
        maphieunhap = phieunhapBus.getAutoIncrement();
        txtMaphieu.setText(maphieunhap);
        loadDataTableSanPham(listSP);
    }

    public void initPadding() {
        pnlBorder1 = new JPanel();
        pnlBorder1.setPreferredSize(new Dimension(0, 5));
        pnlBorder1.setBackground(BackgroundColor);
        this.add(pnlBorder1, BorderLayout.NORTH);

        pnlBorder2 = new JPanel();
        pnlBorder2.setPreferredSize(new Dimension(0, 5));
        pnlBorder2.setBackground(BackgroundColor);
        this.add(pnlBorder2, BorderLayout.SOUTH);

        pnlBorder3 = new JPanel();
        pnlBorder3.setPreferredSize(new Dimension(5, 0));
        pnlBorder3.setBackground(BackgroundColor);
        this.add(pnlBorder3, BorderLayout.EAST);

        pnlBorder4 = new JPanel();
        pnlBorder4.setPreferredSize(new Dimension(5, 0));
        pnlBorder4.setBackground(BackgroundColor);
        this.add(pnlBorder4, BorderLayout.WEST);
    }

    private void initComponent(String type) {
        this.setBackground(BackgroundColor);
        this.setLayout(new BorderLayout(0, 0));
        this.setOpaque(true);

        // Phiếu nhập
        tablePhieuNhap = new JTable();
        scrollTablePhieuNhap = new JScrollPane();
        tblModel = new DefaultTableModel();
        String[] header = new String[]{"STT", "Mã sách", "Tên sách", "Thể loại", "Tác giả", "Nhà xuất bản", "Đơn giá", "Số lượng", "Mã kho"};
        tblModel.setColumnIdentifiers(header);
        tablePhieuNhap.setModel(tblModel);
        scrollTablePhieuNhap.setViewportView(tablePhieuNhap);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        TableColumnModel columnModel = tablePhieuNhap.getColumnModel();
        for (int i = 0; i < 8; i++) {
            if (i != 2) {
                columnModel.getColumn(i).setCellRenderer(centerRenderer);
            }
        }
        tablePhieuNhap.getColumnModel().getColumn(2).setPreferredWidth(250);
        tablePhieuNhap.setDefaultEditor(Object.class, null);
        tablePhieuNhap.setFocusable(false);
        scrollTablePhieuNhap.setViewportView(tablePhieuNhap);

        // Table sản phẩm
        tableSanPham = new JTable();
        scrollTableSanPham = new JScrollPane();
        tblModelSP = new DefaultTableModel();
        String[] headerSP = new String[]{"Mã sách", "Tên sách", "Số lượng tồn"};
        tblModelSP.setColumnIdentifiers(headerSP);
        tableSanPham.setModel(tblModelSP);
        scrollTableSanPham.setViewportView(tableSanPham);
        tableSanPham.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        tableSanPham.getColumnModel().getColumn(1).setPreferredWidth(450);
        tableSanPham.setDefaultEditor(Object.class, null);
        tableSanPham.setFocusable(false);
        scrollTableSanPham.setViewportView(tableSanPham);

        // phần xử lý nút nhấn
        tableSanPham.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int index = tableSanPham.getSelectedRow();
                if (index != -1) {
                    resetForm();
                    selectedSach = listSP.get(index);
                    setInfoSanh(selectedSach);

                }
            }
        });

        initPadding();

        contentCenter = new JPanel();
        contentCenter.setPreferredSize(new Dimension(1100, 600));
        contentCenter.setBackground(BackgroundColor);
        contentCenter.setLayout(new BorderLayout(5, 5));
        this.add(contentCenter, BorderLayout.CENTER);

        left = new PanelBorderRadius();
        left.setLayout(new BorderLayout(0, 5));
        left.setBackground(Color.white);

        left_top = new JPanel(); // Chứa tất cả phần ở phía trái trên cùng
        left_top.setLayout(new BorderLayout());
        left_top.setBorder(new EmptyBorder(5, 5, 10, 10));
        left_top.setOpaque(false);

        JPanel content_top, content_left, content_right, content_right_top;
        content_top = new JPanel(new GridLayout(1, 2, 5, 5));
        content_top.setOpaque(false);
        content_left = new JPanel(new BorderLayout(5, 5));
        content_left.setOpaque(false);
        content_left.setPreferredSize(new Dimension(0, 300));

        txtTimKiem = new JTextField();
        txtTimKiem.putClientProperty("JTextField.placeholderText", "Tên sách, mã sách...");
        txtTimKiem.putClientProperty("JTextField.showClearButton", true);
        txtTimKiem.putClientProperty("JTextField.leadingIcon", new FlatSVGIcon("./icon/search.svg"));

        txtTimKiem.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                ArrayList<SachDTO> rs = sachBus.search(txtTimKiem.getText());
                loadDataTableSanPham(rs);
            }
        });

        txtTimKiem.setPreferredSize(new Dimension(100, 40));
        content_left.add(txtTimKiem, BorderLayout.NORTH);
        content_left.add(scrollTableSanPham, BorderLayout.CENTER);

        content_right = new JPanel(new BorderLayout(5, 5));
        content_right.setOpaque(false);

        content_right_top = new JPanel(new BorderLayout());
        content_right_top.setPreferredSize(new Dimension(100, 260));
        txtMaSp = new InputForm("Mã sách");
        txtMaSp.setEditable(false);
        txtTenSp = new InputForm("Tên sách");
        txtTenSp.setEditable(false);

        String[] arrTen = {"Chọn sách"};
        JPanel content_right_top_cbx = new JPanel(new GridLayout(3, 1, 0, 0));
        content_right_top_cbx.setPreferredSize(new Dimension(100, 190));
        cbxTacGia = new SelectForm("Tác giả", arrTen);
        cbxTacGia.cbb.addItemListener(this);

        txtDongia = new InputForm("Giá nhập");
        txtDongia.setPreferredSize(new Dimension(100, 40));
        PlainDocument dongia = (PlainDocument) txtDongia.getTxtForm().getDocument();
        dongia.setDocumentFilter((new NumericDocumentFilter()));

        cbxTheLoai = new SelectForm("Thể loại", arrTen);

        cbxTheLoai.cbb.addItemListener(this);
        cbxTheLoai.setPreferredSize(new Dimension(100, 90));

        content_right_top_cbx.add(cbxTacGia);
        content_right_top_cbx.add(txtDongia);
        content_right_top_cbx.add(cbxTheLoai);
        content_right_top.add(txtMaSp, BorderLayout.WEST);
        content_right_top.add(txtTenSp, BorderLayout.CENTER);
        content_right_top.add(content_right_top_cbx, BorderLayout.SOUTH);

        content_right_bottom = new JPanel(new CardLayout());

        JPanel card_content_one = new JPanel(new BorderLayout());
        card_content_one.setBackground(Color.white);
        card_content_one.setPreferredSize(new Dimension(100, 90));
        JPanel card_content_one_model = new JPanel(new BorderLayout());
        card_content_one_model.setPreferredSize(new Dimension(100, 90));
        txtSoLuong = new InputForm("Số lượng");
        PlainDocument soluong = (PlainDocument) txtSoLuong.getTxtForm().getDocument();
        soluong.setDocumentFilter((new NumericDocumentFilter()));
        card_content_one_model.add(txtSoLuong, BorderLayout.CENTER);
        card_content_one.add(card_content_one_model, BorderLayout.NORTH);

        content_right_bottom.add(card_content_one);

        content_right.add(content_right_top, BorderLayout.NORTH);
        content_right.add(content_right_bottom, BorderLayout.CENTER);

        content_top.add(content_left);
        content_top.add(content_right);

        content_btn = new JPanel();
        content_btn.setPreferredSize(new Dimension(0, 47));
        content_btn.setLayout(new GridLayout(1, 4, 5, 5));
        content_btn.setBorder(new EmptyBorder(8, 5, 0, 10));
        content_btn.setOpaque(false);

        btnAddSp = new ButtonCustom("Thêm sản phẩm", "success", 14);
        btnImport = new ButtonCustom("Nhập Excel", "excel", 14);
        btnAddSp.addActionListener(this);
        btnImport.addActionListener(this);
        content_btn.add(btnAddSp);
        content_btn.add(btnImport);

        left_top.add(content_top, BorderLayout.CENTER);

        main = new JPanel();
        main.setOpaque(false);
        main.setPreferredSize(new Dimension(0, 250));
        main.setBorder(new EmptyBorder(0, 5, 10, 10));
        BoxLayout boxly = new BoxLayout(main, BoxLayout.Y_AXIS);
        main.setLayout(boxly);
        main.add(scrollTablePhieuNhap);
        left.add(left_top, BorderLayout.CENTER);
        left.add(main, BorderLayout.SOUTH);

        right = new PanelBorderRadius();
        right.setPreferredSize(new Dimension(270, 0));
        right.setBorder(new EmptyBorder(5, 5, 5, 5));
        right.setLayout(new BorderLayout());

        JPanel right_top, right_center, right_bottom, pn_tongtien;
        right_top = new JPanel(new GridLayout(4, 1, 0, 0));
        right_top.setPreferredSize(new Dimension(300, 360));
        right_top.setOpaque(false);
        txtMaphieu = new InputForm("Mã phiếu nhập");
        txtMaphieu.setText("PN" + maphieunhap);

        txtMaphieu.setEditable(false);
        txtNhanVien = new InputForm("Nhân viên nhập");
        txtNhanVien.setText(nvDto != null ? nvDto.getTenNV() : "Không xác định");
        txtNhanVien.setEditable(false);

        // Lấy danh sách tất cả nhà xuất bản
        ArrayList<NhaXuatBanDTO> listNXB = nxbbus.getAllNhaXuatBan();
        // Trích xuất mảng tên NXB
        String[] arrNXB = listNXB.stream().map(NhaXuatBanDTO::getTenNXB).toArray(String[]::new);
        // Tạo SelectForm với danh sách tên NXB và chọn giá trị mặc định là tenNXB
        cbxNXB = new SelectForm("Nhà xuất bản", arrNXB);

        right_top.add(txtMaphieu);
        right_top.add(txtNhanVien);
        right_top.add(cbxNXB);

        right_center = new JPanel();
        right_center.setPreferredSize(new Dimension(100, 100));
        right_center.setOpaque(false);

        right_bottom = new JPanel(new GridLayout(2, 1));
        right_bottom.setPreferredSize(new Dimension(300, 100));
        right_bottom.setBorder(new EmptyBorder(10, 10, 10, 10));
        right_bottom.setOpaque(false);

        pn_tongtien = new JPanel(new FlowLayout(1, 20, 0));
        pn_tongtien.setOpaque(false);
        JLabel lbltien = new JLabel("TỔNG TIỀN: ");
        lbltien.setFont(new Font(FlatRobotoFont.FAMILY, 1, 18));
        lbltongtien = new JLabel("0đ");
        lbltongtien.setFont(new Font(FlatRobotoFont.FAMILY, 1, 18));
        lbltien.setForeground(new Color(255, 51, 51));
        pn_tongtien.add(lbltien);
        pn_tongtien.add(lbltongtien);
        right_bottom.add(pn_tongtien);

        btnNhapHang = new ButtonCustom("Nhập hàng", "excel", 14);
        btnNhapHang.addActionListener(this);
        right_bottom.add(btnNhapHang);
        left_top.add(content_btn, BorderLayout.SOUTH);

        right.add(right_top, BorderLayout.NORTH);
        right.add(right_center, BorderLayout.CENTER);
        right.add(right_bottom, BorderLayout.SOUTH);

        contentCenter.add(left, BorderLayout.CENTER);
        contentCenter.add(right, BorderLayout.EAST);
    }

    public void actionbtn(String type) {
        boolean val_1 = type.equals("add");
        boolean val_2 = type.equals("update");
        btnAddSp.setEnabled(val_1);
        btnImport.setEnabled(val_1);
        content_btn.revalidate();
        content_btn.repaint();
    }

    public void resetForm() {
        this.txtMaSp.setText("");
        this.txtTenSp.setText("");
        String[] arr = {"Chọn sản phẩm"};
        this.cbxTacGia.setArr(arr);
        this.cbxTheLoai.setArr(arr);
        this.txtDongia.setText("");
        this.txtSoLuong.setText("");
    }

    public void loadDataTableSanPham(ArrayList<dto.SachDTO> result) {
        tblModelSP.setRowCount(0);
        for (SachDTO sachDTO : result) {
            tblModelSP.addRow(new Object[]{
                sachDTO.getId(), sachDTO.getTenSach(), sachDTO.getSoLuong()
            });
        }
    }

    public void setInfoSanh(SachDTO sach) {
        if (sach == null) {
            return; // Thoát nếu sách không tồn tại
        }
        this.txtMaSp.setText(sach.getId());
        this.txtTenSp.setText(sach.getTenSach());

        // Xử lý combobox tác giả
        String maTacGia = sach.getTacGia();
        String maTheloai = sach.getTheLoai();

        TacGiaBUS tacGiaBUS = new TacGiaBUS();
        TheLoaiBUS theLoaiBUS = new TheLoaiBUS();

        ArrayList<TheLoaiDTO> listTheLoai = theLoaiBUS.getAllTheLoai();
        String[] arrTheLoai = listTheLoai.stream().map(TheLoaiDTO::getTenLoai).toArray(String[]::new);
        this.cbxTheLoai.setArr(arrTheLoai);
        String tenTheLoai = listTheLoai.stream().filter(tl -> tl.getMaLoai().equals(maTheloai))
                .findFirst().map(TheLoaiDTO::getTenLoai).orElse("Khỗng xác định");
        this.cbxTheLoai.setSelectedItem(tenTheLoai);

        ArrayList<TacGiaDTO> listTacGia = tacGiaBUS.getAllTacGia();
        String[] arrTacGia = listTacGia.stream()
                .map(TacGiaDTO::getTenTG)
                .toArray(String[]::new);

        this.cbxTacGia.setArr(arrTacGia);
        // Tìm tên tác giả tương ứng với mã
        String tenTacGia = listTacGia.stream()
                .filter(tg -> tg.getMaTG().equals(maTacGia))
                .findFirst()
                .map(TacGiaDTO::getTenTG)
                .orElse("Không xác định");

        this.cbxTacGia.setSelectedItem(tenTacGia);

        // Hiển thị giá bán từ sách được chọn
        this.txtDongia.setText(Integer.toString(sach.getGiaBan()));
    }

    private void updateTotal() {
        int total = 0;
        for (int i = 0; i < tblModel.getRowCount(); i++) {
            int donGia = (int) tblModel.getValueAt(i, 6);
            int soLuong = (int) tblModel.getValueAt(i, 7);
            total += donGia * soLuong;
        }
        lbltongtien.setText(total + "đ");
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getSource() == cbxTacGia.cbb && selectedSach != null) {
            // Giữ nguyên giá bán từ sách đã chọn
            this.txtDongia.setText(Integer.toString(selectedSach.getGiaBan()));
        } else {
            this.txtDongia.setText("");
        }
    }

    private boolean validateForm() {
        if (txtMaSp.getText().isEmpty() || txtTenSp.getText().isEmpty()
                || txtDongia.getText().isEmpty() || txtSoLuong.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin sản phẩm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private void saveBillToDatabase() {
        if (tblModel.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng thêm sản phẩm vào phiếu nhập!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Xác nhận tạo phiếu nhập?", "Xác nhận", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                // Lấy thông tin từ form
                String tenNXB = cbxNXB.getSelectedItem().toString();
                NhaXuatBanDTO nxbDTO = nxbbus.getByName(tenNXB);

                if (nxbDTO == null) {
                    JOptionPane.showMessageDialog(this, "Nhà xuất bản không tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                String maNXB = nxbDTO.getMaNXB();
                String maKho = tblModel.getRowCount() > 0 ? tblModel.getValueAt(0, 8).toString() : null;

                // Tạo đối tượng PhieuNhapDTO
                PhieuNhapDTO phieuNhap = new PhieuNhapDTO(
                        maphieunhap,
                        nvDto.getMaNV(),
                        maNXB,
                        maKho,
                        LocalDate.now(),
                        Integer.parseInt(lbltongtien.getText().replace("đ", "")),
                        1
                );

                // Thêm phiếu nhập vào database
                phieunhapBus.add(phieuNhap);

                // Thêm chi tiết phiếu nhập
                ChiTietPhieuNhapBUS ctpnBus = new ChiTietPhieuNhapBUS();
                for (int i = 0; i < tblModel.getRowCount(); i++) {
                    ChiTietPhieuNhapDTO ctpn = new ChiTietPhieuNhapDTO(
                            tblModel.getValueAt(i, 1).toString(),
                            maphieunhap,
                            Integer.parseInt(tblModel.getValueAt(i, 7).toString()),
                            Integer.parseInt(tblModel.getValueAt(i, 6).toString()),
                            1
                    );
                    ctpnBus.add(ctpn);
                    // Cập nhật số lượng tồn sách
                    SachDTO sach = sachBus.getByID(ctpn.getMaSach());
                    if (sach != null) {
                        int soLuongHienTai = sach.getSoLuong();
                        int soLuongNhap = ctpn.getSoLuongNhap();
                        sach.setSoLuong(soLuongHienTai + soLuongNhap); // tăng số lượng tồn

                        String updated = sachBus.updateSach(sach);
                        if (!"Cập nhật thành công".equals(updated)) {
                            System.out.println("Cập nhật số lượng sách thất bại cho mã: " + sach.getId());
                        }
                    }

                }

                JOptionPane.showMessageDialog(this, "Nhập hàng thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);


                // Chuyển về giao diện PhieuNhap và làm mới bảng
                PhieuNhap phieuNhapPanel = new PhieuNhap(m, nvDto);
                m.setPanel(phieuNhapPanel);

                // Reset mã phiếu mới sau khi lưu
//                resetForm();
//                tblModel.setRowCount(0);
//                lbltongtien.setText("0đ");
                maphieunhap = phieunhapBus.getAutoIncrement();
                txtMaphieu.setText("PN" + maphieunhap);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Lỗi khi lưu phiếu nhập: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void importFromExcel() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try ( Workbook workbook = new XSSFWorkbook(file)) {
                Sheet sheet = workbook.getSheetAt(0);
                for (Row row : sheet) {
                    if (row.getRowNum() == 0) {
                        continue; // Bỏ qua header
                    }
                    String maSach = row.getCell(0).getStringCellValue();
                    String tenSach = row.getCell(1).getStringCellValue();
                    String theLoai = row.getCell(2).getStringCellValue();
                    String tacGia = row.getCell(3).getStringCellValue();
                    String nhaXB = row.getCell(4).getStringCellValue();
                    int donGia = (int) row.getCell(5).getNumericCellValue();
                    int soLuong = (int) row.getCell(6).getNumericCellValue();

                    tblModel.addRow(new Object[]{
                        tblModel.getRowCount() + 1, maSach, tenSach, theLoai, tacGia,
                        nhaXB, donGia, soLuong
                    });
                }
                updateTotal();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi đọc file Excel!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void addSach() {
        String maSach = txtMaSp.getText();
        String tenSach = txtTenSp.getText();
        String theLoai = cbxTheLoai.getSelectedItem().toString();
        String tacGia = cbxTacGia.getSelectedItem().toString();
        String nhaXB = cbxNXB.getSelectedItem().toString();
        int donGia = Integer.parseInt(txtDongia.getText());
        int soLuong = Integer.parseInt(txtSoLuong.getText());
        String makho = selectedSach.getMaKho();
        // Thêm dữ liệu vào bảng
        tblModel.addRow(new Object[]{
            tblModel.getRowCount() + 1,
            maSach, tenSach, theLoai, tacGia, nhaXB, donGia, soLuong, makho
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Xử lý sự kiện nút nhấn
        if (e.getSource() == btnAddSp) {
            if (validateForm()) {
                addSach();
                updateTotal();
                resetForm();
            }

        } else if (e.getSource() == btnNhapHang) {
            saveBillToDatabase();
        } else if (e.getSource() == btnImport) {
            importFromExcel();
        }
    }
}
