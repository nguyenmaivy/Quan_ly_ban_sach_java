
package gui.Dialog;

import dto.NhaXuatBanDTO;
import dao.NhaXuatBanDAO;
import bus.NhaXuatBanBUS;
import gui.Componet.Custom.ButtonCustom;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class NhaXuatBanDialog extends JDialog implements ActionListener {
    private JLabel lblMaNXB, lblTenNXB, lblSdt, lblDiachi;
    private JTextField txtMaNXB, txtTenNXB, txtSdt, txtDiachi;
    private JPanel jpTop, jpBottom;
    private ButtonCustom btnAdd, btnUpdate, btnCancel, btnDetail;
    private NhaXuatBanBUS nhaXuatBanBUS;
    private NhaXuatBanDTO nhaXuatBanDTO;

    public NhaXuatBanDialog(NhaXuatBanBUS bus, JFrame owner, String title, boolean modal, String type, NhaXuatBanDTO dto) {
        super(owner, title, modal);
        this.nhaXuatBanBUS = bus;
        this.nhaXuatBanDTO = dto;
        initComponents(type);
        txtMaNXB.setEditable(false); // <<< Dòng này giúp không cho sửa mã NXB
    }

    private void initComponents(String type) {
        this.setSize(new Dimension(530, 300));  // Điều chỉnh kích thước cửa sổ
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout(0, 0));

        jpTop = new JPanel(new GridLayout(4, 2, 10, 10));  // Thay đổi thành GridLayout để sắp xếp các trường
        jpTop.setBorder(new EmptyBorder(20, 20, 20, 20));
        jpTop.setBackground(Color.WHITE);
        
        lblMaNXB = new JLabel("Mã Nhà Xuất Bản:");
        lblTenNXB = new JLabel("Tên Nhà Xuất Bản:");
        lblDiachi = new JLabel("Địa Chỉ:");
        lblSdt = new JLabel("Số Điện Thoại:");
        
        
        txtMaNXB = new JTextField();
        // Không cho phép nhập mã nhà xuất bản
        txtMaNXB.setEditable(false);
        txtTenNXB = new JTextField();
        txtDiachi = new JTextField();
        txtSdt = new JTextField();    
        
               
        jpTop.add(lblMaNXB);
        jpTop.add(txtMaNXB);
        jpTop.add(lblTenNXB);
        jpTop.add(txtTenNXB);
        jpTop.add(lblDiachi);
        jpTop.add(txtDiachi);
        jpTop.add(lblSdt);
        jpTop.add(txtSdt);        

        jpBottom = new JPanel(new FlowLayout());
        jpBottom.setBackground(Color.WHITE);
        jpBottom.setBorder(new EmptyBorder(10, 0, 10, 0));

        switch (type) {
            case "create" -> {
                btnAdd = new ButtonCustom("Thêm", "success", 14);
                btnAdd.addActionListener(this);
                jpBottom.add(btnAdd);
                
                // Set mã nhà xuất bản tự sinh lên txtMaNXB
                String nextMa = nhaXuatBanBUS.getNextMaNXB();
                txtMaNXB.setText(nextMa);
                txtMaNXB.setEditable(false); // Không cho sửa
            }
            case "update" -> {
                btnUpdate = new ButtonCustom("Cập nhật", "success", 14);
                btnUpdate.addActionListener(this);
                jpBottom.add(btnUpdate);
                if (nhaXuatBanDTO != null) {
                    txtMaNXB.setText(nhaXuatBanDTO.getMaNXB());
                    txtTenNXB.setText(nhaXuatBanDTO.getTenNXB());
                    txtDiachi.setText(nhaXuatBanDTO.getDiachiNXB());
                    txtSdt.setText(nhaXuatBanDTO.getSdt());                      
                }
            }
            case "detail" -> {
//                btnDetail = new ButtonCustom("Chi tiết", "success", 14);
//                btnDetail.addActionListener(this);
//                jpBottom.add(btnDetail);
                if (nhaXuatBanDTO != null) {
                    txtMaNXB.setText(nhaXuatBanDTO.getMaNXB());
                    txtTenNXB.setText(nhaXuatBanDTO.getTenNXB());
                    txtDiachi.setText(nhaXuatBanDTO.getDiachiNXB());
                    txtSdt.setText(nhaXuatBanDTO.getSdt());   
                    
                    // Set các field thành không chỉnh sửa
                    txtMaNXB.setEditable(false);
                    txtTenNXB.setEditable(false);
                    txtDiachi.setEditable(false);
                    txtSdt.setEditable(false);                   
                              
                }  

            }
        }

        btnCancel = new ButtonCustom("Hủy", "danger", 14);
        btnCancel.addActionListener(this);
        jpBottom.add(btnCancel);

        this.add(jpTop, BorderLayout.CENTER);
        this.add(jpBottom, BorderLayout.SOUTH);
        this.setVisible(true);
    }

    @Override

    public void actionPerformed(ActionEvent e) {
    Object source = e.getSource();
    
    if (source == btnAdd) {
        String tenNXB = txtTenNXB.getText().trim();        
        String diachi = txtDiachi.getText().trim();
        String sdt = txtSdt.getText().trim();
        
        // Kiểm tra rỗng
        if (tenNXB.isEmpty() || diachi.isEmpty() || sdt.isEmpty() ) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Kiểm tra số điện thoại (10 chữ số, bắt đầu bằng 0)
        if (!sdt.matches("^0\\d{9}$")) {
            JOptionPane.showMessageDialog(this, "Số điện thoại không hợp lệ! Phải có 10 chữ số và bắt đầu bằng 0.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        NhaXuatBanDAO dao = new NhaXuatBanDAO();      // tạo DAO
        String  maMoi = dao.NextMaNXB();   
        // Tạo đối tượng NXB mới và thêm vào CSDL
        NhaXuatBanDTO newNXB = new NhaXuatBanDTO();       
        newNXB.setMaNXB(maMoi); 
        newNXB.setTenNXB(tenNXB);        
        newNXB.setDiachiNXB(diachi);
        newNXB.setSdt(sdt);
        
        // Gọi BUS để thêm nhà xuất bản
        String result = nhaXuatBanBUS.addNhaXuatBan(newNXB);

       // Hiển thị thông báo kết quả
        if (result.equals("Số điện thoại đã tồn tại. Vui lòng nhập số khác!")) {
            JOptionPane.showMessageDialog(this, result, "Lỗi", JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, result);

            if (result.equals("Thêm nhà xuất bản thành công")) {
                dispose(); // Đóng cửa sổ khi thêm thành công
            }
        }

    } else if (source == btnUpdate) {
        if (nhaXuatBanDTO != null) {
            
            String tenNXB = txtTenNXB.getText().trim();            
            String diachi = txtDiachi.getText().trim();
            String sdt = txtSdt.getText().trim();

            // Kiểm tra rỗng
            if (tenNXB.isEmpty() || diachi.isEmpty() || sdt.isEmpty() ) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Kiểm tra số điện thoại (10 chữ số, bắt đầu bằng 0)
            if (!sdt.matches("^0\\d{9}$")) {
                JOptionPane.showMessageDialog(this, "Số điện thoại không hợp lệ! Phải có 10 chữ số và bắt đầu bằng 0.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }            

            // Cập nhật thông tin
            
            nhaXuatBanDTO.setTenNXB(tenNXB);
            nhaXuatBanDTO.setDiachiNXB(diachi);
            nhaXuatBanDTO.setSdt(sdt);
            

            String result = nhaXuatBanBUS.updateNhaXuatBan(nhaXuatBanDTO);            

            if (result.equals("Cập nhật nhà xuất bản thành công!")) {
            JOptionPane.showMessageDialog(this, result, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            dispose(); // Đóng form sau khi cập nhật
        } else {
            JOptionPane.showMessageDialog(this, result, "Lỗi", JOptionPane.ERROR_MESSAGE);
        }

    } else {
        JOptionPane.showMessageDialog(this, "Không tìm thấy nhà xuất bản!", "Lỗi", JOptionPane.ERROR_MESSAGE);
    }

        } else if (source == btnCancel) {
            dispose();
        }
    }
}



