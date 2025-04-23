/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Components;

import Components.chart.ModelPieChart;
import Components.chart.PieChart;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class homeForm2 extends JPanel {

    private ArrayList<Object[]> dataPieChart;
    private JPanel panelDetail;
    private ArrayList<ModelPieChart> arr1;
    private PieChart pieChart1;
    private static Color[] colorList = {
        Color.decode("#0984e3"),
        Color.decode("#4cd137"),
        Color.decode("#f1c40f"),
        Color.decode("#FF8000"),
        Color.decode("#e74c3c"),
        Color.decode("#9b59b6"),
        Color.decode("#2ecc71") // Thêm màu nếu cần
    };

    public homeForm2() {
        this.dataPieChart = new ArrayList<>();
        init();
    }

    public void init() {

        this.setBounds(555, 10, 520, 320);
        this.setBackground(Color.white);
        this.setLayout(null);

        JLabel titelLabel2 = new JLabel("Tổng doanh thu");
        titelLabel2.setForeground(new Color(0, 0, 0, 140));
        titelLabel2.setFont(new Font("sansserif", 1, 16));
        titelLabel2.setBounds(15, 15, 200, 24);
        this.add(titelLabel2);

        pieChart1 = new PieChart();
        pieChart1.setChartType(PieChart.PeiChartType.DONUT_CHART);
        pieChart1.setBounds(280, 60, 220, 220);
        this.add(pieChart1);
        panelDetail = new JPanel();
        panelDetail.setBounds(0, 50, 260, 300);
        panelDetail.setLayout(null);
        panelDetail.setBackground(Color.white);
        this.add(panelDetail);
    }

    public void setData(ArrayList<Object[]> data) {
        this.dataPieChart = data;

        panelDetail.removeAll();
        for (int i = 0; i < dataPieChart.size(); i++) {

            JPanel pn = new JPanel();
            pn.setBackground(colorList[i]);
            pn.setBounds(30, 40 + i * 32, 12, 12);
            panelDetail.add(pn);
            JLabel lbpie1 = new JLabel((String) dataPieChart.get(i)[0]);
            lbpie1.setFont(new Font("sansserif", 1, 12));
            lbpie1.setForeground(new Color(0, 0, 0, 200));
            lbpie1.setBounds(50, 35 + i * 32, 200, 20);
            panelDetail.add(lbpie1);
        }
    }

    public void ani() {
        if (dataPieChart == null || dataPieChart.isEmpty()) {
            return;
        }

        ModelPieChart md = new ModelPieChart("P1", 0, Color.white);
        arr1 = new ArrayList<>();
        for (int i = 0; i < dataPieChart.size(); i++) {
            ModelPieChart md1 = new ModelPieChart("P2", 0, colorList[i]);
            arr1.add(md1);
        }
        arr1.add(md);

        pieChart1.setData(arr1);

        new Thread(new Runnable() {
            @Override
            public void run() {
                int sizeList = arr1.size() - 1;
                int total = 0;
                for (int i = 0; i < dataPieChart.size(); i++) {
                    total += (int) dataPieChart.get(i)[1];
                }

                // Thay đổi ở đây: Tăng giá trị theo bước 10% tổng và giảm sleep
                int steps = 100; // Số bước animation
                int stepSize = total / steps;

                for (int step = 0; step < steps; step++) {
                    for (int i = 0; i < dataPieChart.size(); i++) {
                        int currentValue = (int) arr1.get(i).getValues();
                        int targetValue = (int) dataPieChart.get(i)[1];
                        int newValue = Math.min(currentValue + stepSize, targetValue);
                        arr1.get(i).setValues(newValue);
                    }
                    // Cập nhật tổng còn lại
                    int remaining = total - (step + 1) * stepSize;
                    arr1.get(sizeList).setValues(remaining > 0 ? remaining : 0);
                    repaint();
                    try {
                        Thread.sleep(10); // Giảm thời gian sleep xuống 10ms
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                // Đặt giá trị cuối cùng chính xác
                for (int i = 0; i < dataPieChart.size(); i++) {
                    arr1.get(i).setValues((int) dataPieChart.get(i)[1]);
                }
                pieChart1.delDate(md);
                repaint();
            }
        }).start();
    }
}
