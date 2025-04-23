/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bus;

import Components.homeForm1;
import Components.homeForm2;
import Components.homeForm3;
import dao.ThongKeDAO;
import dto.ThongKeDTO;
import java.time.LocalDate;
import java.util.ArrayList;

public class ThongKeBUS {

    private ThongKeDAO thongKeDAO;

    public ThongKeBUS() {
        this.thongKeDAO = new ThongKeDAO();
    }
    // Nạp dữ liệu cho biểu đồ cột (homeForm1)

    public void loadBarChartData(homeForm1 form1) {
        ThongKeDTO stats = thongKeDAO.getAllStats();
        int currentYear = LocalDate.now().getYear();
        int[] soLuong = new int[4];
        int[] avg = new int[4];

        // Lấy dữ liệu cho 4 năm gần nhất
        for (int i = 0; i < 4; i++) {
            int year = currentYear - 3 + i;
            for (ThongKeDTO.YearStats yearStat : stats.getYearList()) {
                if (yearStat.getYear() == year) {
                    soLuong[i] = yearStat.getSoluongsach();
                    avg[i] = (int) yearStat.getAvgSach();
                }
            }
        }

        form1.setData(soLuong, avg);
        form1.runChart();
    }

    // Nạp dữ liệu cho biểu đồ tròn (homeForm2)
    public void loadPieChartData(homeForm2 form2) {
        ThongKeDTO stats = thongKeDAO.getAllStats();
        ArrayList<Object[]> pieData = new ArrayList<>();

        for (ThongKeDTO.DoanhThu doanhthu : stats.getDoanhThuList()) {
            pieData.add(new Object[]{doanhthu.getTenTL(), (int) doanhthu.getTongDT()});
        }

        form2.setData(pieData);
        form2.ani();
    }

    // Nạp dữ liệu cho bảng (homeForm3)
    public void loadTableData(homeForm3 form3) {
        ThongKeDTO stats = thongKeDAO.getAllStats();
        Object[][] tableData = new Object[stats.getDoanhThuList().size()][4];

        int index = 0;
        for (ThongKeDTO.DoanhThu doanhthu : stats.getDoanhThuList()) {
            tableData[index][0] = index + 1; // STT
            tableData[index][1] = doanhthu.getTenTL(); // Thể loại sách
            tableData[index][2] = doanhthu.getTongDT(); // Doanh thu
            tableData[index][3] = String.format("%.2f", doanhthu.getPtram()); // Tỷ lệ (%)
            index++;
        }

        form3.setTableData(tableData);
    }
}
