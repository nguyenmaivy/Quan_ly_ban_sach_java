/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author MZI
 */
public class ThongKeDTO {
    public static class YearStats{
        private int year;
        private int soluongsach;
        private double avgSach;

        public YearStats(int year, int soluongsach, double avgSach) {
            this.year = year;
            this.soluongsach = soluongsach;
            this.avgSach = avgSach;
        }

        public int getYear() {
            return year;
        }

        public int getSoluongsach() {
            return soluongsach;
        }

        public double getAvgSach() {
            return avgSach;
        }
        
    }
    
    public static class DoanhThu{
        private String tenTL;
        private long tongDT;
        private double ptram;

        public DoanhThu(String tenTL, long tongDT, double ptram) {
            this.tenTL = tenTL;
            this.tongDT = tongDT;
            this.ptram = ptram;
        }

        public String getTenTL() {
            return tenTL;
        }

        public long getTongDT() {
            return tongDT;
        }

        public double getPtram() {
            return ptram;
        }
        
    }
    private List<YearStats> yearList;
    private List<DoanhThu> doanhThuList;

    public ThongKeDTO() {
        this.yearList = new ArrayList<>();
        this.doanhThuList = new ArrayList<>();
    }
    
    // thêm thống kê theo năm
    public void addYear(int year, int soluongsach, double avgSach){
        yearList.add(new YearStats(year, soluongsach, avgSach));
    }
    // thêm thống kê theo doanh thu
    public void addDoanhthu(String tenTL, long TongDT, double ptram){
        doanhThuList.add(new DoanhThu(tenTL, TongDT, ptram));
    }
    public List<YearStats> getYearList(){
        return yearList;
    }
    public List<DoanhThu> getDoanhThuList(){
       return doanhThuList;
    }
    
}
