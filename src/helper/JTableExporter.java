
package helper;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.TableModel;
import java.io.FileOutputStream;
import java.io.IOException;

public class JTableExporter {

    public static boolean exportJTableToExcel(JTable table) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn đường dẫn lưu file Excel");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("XLSX files", "xlsx");
        fileChooser.setFileFilter(filter);
        fileChooser.setAcceptAllFileFilterUsed(false);

        int userChoice = fileChooser.showSaveDialog(null);
        if (userChoice != JFileChooser.APPROVE_OPTION) {
            return false; // Người dùng bấm Cancel
        }

        try {
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();
            if (!filePath.toLowerCase().endsWith(".xlsx")) {
                filePath += ".xlsx";
            }

            TableModel model = table.getModel();
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Sheet1");

            // Create header row
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < model.getColumnCount(); i++) {
                Cell headerCell = headerRow.createCell(i);
                headerCell.setCellValue(model.getColumnName(i));
            }

            // Create data rows
            for (int i = 0; i < model.getRowCount(); i++) {
                Row dataRow = sheet.createRow(i + 1);
                for (int j = 0; j < model.getColumnCount(); j++) {
                    Cell dataCell = dataRow.createCell(j);
                    Object value = model.getValueAt(i, j);
                    if (value != null) {
                        dataCell.setCellValue(value.toString());
                    }
                }
            }

            // Resize columns
            for (int i = 0; i < model.getColumnCount(); i++) {
                sheet.autoSizeColumn(i);
            }

            try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                workbook.write(fileOut);
            }

            workbook.close();
            return true; // Xuất thành công
        } catch (IOException e) {
            e.printStackTrace();
            return false; // Có lỗi xảy ra
        }
    }
}
