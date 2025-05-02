package bus;

import dao.HoaDonDAO;
import dto.HoaDonDTO;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.JOptionPane;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JFileChooser;

public class HoaDonBUS {

    private HoaDonDAO hoaDonDAO;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public HoaDonBUS() {
        hoaDonDAO = new HoaDonDAO();
    }

    public boolean themHoaDon(HoaDonDTO hoaDon) {
        // Kiểm tra dữ liệu
        if (hoaDon.getSoHD() == null || hoaDon.getSoHD().isEmpty()) {
            throw new IllegalArgumentException("Mã hóa đơn không được để trống.");
        }
        if (hoaDon.getNgayBan() == null) {
            throw new IllegalArgumentException("Ngày bán không được để trống.");
        }
        if (hoaDon.getMaNV() == null) {
            throw new IllegalArgumentException("Mã Nhân viên không được để trống");
        }
        if (hoaDon.getTrangThai() < 0 || hoaDon.getTrangThai() > 1) {
            throw new IllegalArgumentException("Trạng thái không hợp lệ");
        }
        return hoaDonDAO.themHoaDon(hoaDon);
    }

    public HoaDonDTO getByMaHD(String soHD) {
        if (soHD == null || soHD.isEmpty()) {
            throw new IllegalArgumentException("Mã hóa đơn không được để trống.");
        }
        return hoaDonDAO.getByMaHD(soHD);
    }

    public boolean capNhatTrangThaiHoaDon(String soHD, int trangThai) throws SQLException {
        // Thêm logic nghiệp vụ (ví dụ: kiểm tra trạng thái hợp lệ)
        if (soHD == null || soHD.isEmpty()) {
            throw new IllegalArgumentException("Mã hóa đơn không được để trống.");
        }
        if (trangThai < 0 || trangThai > 1) {
            throw new IllegalArgumentException("Trạng thái không hợp lệ.");
        }
        return hoaDonDAO.capNhatTrangThaiHoaDon(soHD, trangThai);
    }

    public boolean xoaHoaDon(String soHD) throws SQLException {
        // Logic nghiệp vụ: Kiểm tra mã hóa đơn
        if (soHD == null || soHD.isEmpty()) {
            throw new IllegalArgumentException("Mã hóa đơn không được để trống.");
        }

        // Gọi DAO để xóa hóa đơn
        return hoaDonDAO.xoaHoaDon(soHD);
    }

    public boolean capNhatHoaDon(HoaDonDTO hoaDon) throws SQLException {
        // Logic nghiệp vụ: Kiểm tra dữ liệu đầu vào
        if (hoaDon.getSoHD() == null || hoaDon.getSoHD().isEmpty()) {
            throw new IllegalArgumentException("Mã hóa đơn không được để trống.");
        }
        if (hoaDon.getNgayBan() == null) {
            throw new IllegalArgumentException("Ngày bán không được để trống.");
        }
        if (hoaDon.getMaNV() == null || hoaDon.getMaNV().isEmpty()) {
            throw new IllegalArgumentException("Mã nhân viên không được để trống.");
        }
        if (hoaDon.getTrangThai() < 0 || hoaDon.getTrangThai() > 1) {
            throw new IllegalArgumentException("Trạng thái không hợp lệ.");
        }

        // Gọi DAO để cập nhật hóa đơn
        return hoaDonDAO.capNhatHoaDon(hoaDon);
    }

    

    public String generateSoHD() {
        Random random = new Random();
        int randomNumber = random.nextInt(1000000);
        return String.format("HD%06d", randomNumber);
    }

//    public void xuatExcelHoaDon() throws SQLException, IOException {
//        List<HoaDonDTO> danhSachHoaDon = layDanhSachHoaDon();
//        if (danhSachHoaDon == null || danhSachHoaDon.isEmpty()) {
//            JOptionPane.showMessageDialog(null, "Không có hóa đơn để xuất ra Excel.");
//            return;
//        }
//
//        Workbook workbook = new XSSFWorkbook();
//        Sheet sheet = workbook.createSheet("Hóa Đơn");
//
//        // Tạo header row
//        Row headerRow = sheet.createRow(0);
//        String[] headers = {"Mã Hóa Đơn", "Ngày bán", "Trạng thái", "Tên Nhân viên"};
//        for (int i = 0; i < headers.length; i++) {
//            Cell cell = headerRow.createCell(i);
//            cell.setCellValue(headers[i]);
//        }
//
//        // Ghi dữ liệu hóa đơn vào các hàng
//        int rowNum = 1;
//        for (HoaDonDTO hoaDon : danhSachHoaDon) {
//            Row row = sheet.createRow(rowNum++);
//            Cell cell1 = row.createCell(0);
//            cell1.setCellValue(hoaDon.getSoHD());
//            Cell cell2 = row.createCell(1);
//            cell2.setCellValue(hoaDon.getNgayBan().format(DATE_FORMATTER)); // Format ngày
//            Cell cell3 = row.createCell(2);
//            cell3.setCellValue(hoaDon.getTrangThai() == 1 ? "Đã thanh toán" : "Đã hủy");
//            Cell cell4 = row.createCell(3);
//        }
//
//        // Tự động điều chỉnh kích thước cột
//        for (int i = 0; i < headers.length; i++) {
//            sheet.autoSizeColumn(i);
//        }
//
//        // Lưu workbook vào file
//        try {
//            JFileChooser fileChooser = new JFileChooser();
//            fileChooser.setDialogTitle("Lưu file Excel");
//            int userSelection = fileChooser.showSaveDialog(null); // Sử dụng null hoặc component cha thích hợp
//
//            if (userSelection == JFileChooser.APPROVE_OPTION) {
//                java.io.File fileToSave = fileChooser.getSelectedFile();
//                try ( FileOutputStream fileOut = new FileOutputStream(fileToSave + ".xlsx")) {
//                    workbook.write(fileOut);
//                    JOptionPane.showMessageDialog(null, "Xuất Excel thành công!");
//                } catch (IOException e) {
//                    throw new IOException("Lỗi khi ghi file Excel: " + e.getMessage(), e);
//                } finally {
//                    workbook.close();
//                }
//            }
//
//        } catch (IOException e) {
//            throw new IOException("Lỗi khi xuất file Excel: " + e.getMessage(), e);
//        }
//    }
}
