package gui.Dialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JDialog;
import dto.TacGiaDTO;
import gui.Panel.TacGia;
import dao.TacGiaDAO;
import bus.TacGiaBUS;
import gui.Componet.Custom.ButtonCustom;
import gui.Componet.Custom.HeaderTitle;
import gui.Componet.Custom.InputForm;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.PlainDocument;

public class TacGiaDialog extends JDialog implements ActionListener {

    private TacGia jptacgia;
    private HeaderTitle titlePage;
    private JPanel pnmain, pnbottom;
    private ButtonCustom btnThem, btnHuyBo, btnCapNhat;
    private InputForm tentg;
    private InputForm lienlac;
    private TacGiaDTO tgDTO;

    public TacGiaDialog(TacGia jptacgia, JFrame owner, String title, boolean modal, String type) {
        super(owner, title, modal);
        this.jptacgia = jptacgia;
        initComponents(title, type);
    }

    public TacGiaDialog(TacGia jptacgia, JFrame owner, String title, boolean modal, String type, TacGiaDTO tgDTO) {
        super(owner, title, modal);
        this.jptacgia = jptacgia;
        this.tgDTO = tgDTO;
        initComponents(title, type);
    }

    public void initComponents(String title, String type) {
        this.setSize(new Dimension(900, 360));
        this.setLayout(new BorderLayout(0, 0));
        titlePage = new HeaderTitle(title.toUpperCase());
        pnmain = new JPanel(new GridLayout(2, 2, 20, 0));
        pnmain.setBackground(Color.white);
        tentg = new InputForm("Tên tác giả");
        lienlac = new InputForm("Liên lạc");

        pnmain.add(tentg);
        pnmain.add(lienlac);

        pnbottom = new JPanel(new FlowLayout());
        pnbottom.setBorder(new EmptyBorder(10, 0, 10, 0));
        pnbottom.setBackground(Color.white);
        btnThem = new ButtonCustom("Thêm đơn vị", "success", 14);
        btnHuyBo = new ButtonCustom("Huỷ bỏ", "danger", 14);
        btnCapNhat = new ButtonCustom("Cập nhật", "success", 14);

        //Add MouseListener btn
        btnThem.addActionListener(this);
        btnHuyBo.addActionListener(this);
        btnCapNhat.addActionListener(this);
        switch (type) {
            case "create" ->
                pnbottom.add(btnThem);
            case "update" -> {
                pnbottom.add(btnCapNhat);
                initInfo();
            }
            case "view" -> {
                initInfo();
                initView();
            }
            default ->
                throw new AssertionError();
        }

//        pnbottom.add(btnThem);
        pnbottom.add(btnHuyBo);
        this.add(titlePage, BorderLayout.NORTH);
        this.add(pnmain, BorderLayout.CENTER);
        this.add(pnbottom, BorderLayout.SOUTH);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        TacGiaBUS tacGiaBUS = new TacGiaBUS();

        if (e.getSource() == btnThem) {
            String tenTG = tentg.getText().trim();
            String lienlac = this.lienlac.getText().trim();
            String matg;

            if (tenTG.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Tên tác giả không được để rỗng!");
                return;
            }
            if (tgDTO == null) {
                String maxMaTG = tacGiaBUS.getMaxMaTG();
                int count;
                if (maxMaTG == null) {
                    count = 1;
                } else {
                    String numbetStr = maxMaTG.substring(2);
                    count = Integer.parseInt(numbetStr) + 1;

                }

                matg = "TG" + String.format("%02d", count);

                if (tacGiaBUS.has(matg)) {
                    JOptionPane.showMessageDialog(this, "Mã TG đã tồn tại!");
                    return;
                }

                TacGiaDTO newtacGiaDTO = new TacGiaDTO(matg, tenTG, lienlac, 1);
                String result = tacGiaBUS.addTacGia(newtacGiaDTO);

                if (result.equals("Thêm tác giả thành công")) {
                    JOptionPane.showMessageDialog(this, result);
                    jptacgia.listtg = tacGiaBUS.getAllTacGia(); // cập nhật danh sách
                    jptacgia.loadDataTable(jptacgia.listtg); // cập nhật bảng
                    dispose(); // đóng dialog

                } else {
                    JOptionPane.showMessageDialog(this, result);
                }
            }

        } else if (e.getSource() == btnHuyBo) {
            dispose();
        } else if (e.getSource() == btnCapNhat) {
            String tenTG = tentg.getText().trim();
            String lienLac = lienlac.getText().trim();
            if (tenTG.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Tên tác giả không được để rỗng");
                return;
            }
            
            tgDTO.setTenTG(tenTG);
            tgDTO.setLienLac(lienLac);

            // Gọi phương thức update từ BUS
            String result = tacGiaBUS.updateTacGia(tgDTO);

            if (result.equals("Cập nhật tác giả thành công")) {
                JOptionPane.showMessageDialog(this, result);
                // Cập nhật danh sách và bảng
                jptacgia.listtg = tacGiaBUS.getAllTacGia();
                jptacgia.loadDataTable(jptacgia.listtg);
                dispose(); // Đóng dialog
            } else {
                JOptionPane.showMessageDialog(this, result);
            }
        }
    }

    private void initInfo() {
        tentg.setText(tgDTO.getTenTG());
        lienlac.setText(tgDTO.getLienLac());
    }

    private void initView() {
        tentg.setEditable(false);
        lienlac.setEditable(false);
    }

}
