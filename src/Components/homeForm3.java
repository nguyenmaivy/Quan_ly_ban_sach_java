/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Components;

import gui.Componet.Custom.myScrollBar;
import gui.Componet.Custom.myTable;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class homeForm3 extends JPanel {

    private static Color[] colorList = {Color.decode("#0984e3"), Color.decode("#4cd137"), Color.decode("#f1c40f"), Color.decode("#FF8000"), Color.decode("#e74c3c"), Color.decode("#9b59b6")};
    private myTable table;
    private DefaultTableModel modelTable;
    private Object[][] data;
    private String[] str = new String[]{"STT", "Thể loại sách", "Doanh thu", "Tỷ lệ(%)"};

    public homeForm3() {

        init();
    }

    public void init() {

        this.setBounds(555, 10, 520, 320);
        this.setBackground(Color.white);
        this.setLayout(null);

        JLabel titelLabel2 = new JLabel("Thông tin tổng quan");
        titelLabel2.setForeground(new Color(0, 0, 0, 140));
        titelLabel2.setFont(new Font("sansserif", 1, 16));
        titelLabel2.setBounds(15, 15, 200, 24);
        this.add(titelLabel2);
        JScrollPane jsp = new JScrollPane();
        jsp.setBackground(Color.white);
        jsp.setBounds(10, 40, 1090, 550);
        jsp.setVerticalScrollBar(new myScrollBar());
        table = new myTable();
        table.setBackground(Color.white);
        jsp.setViewportView(table);

        jsp.setBorder(new EmptyBorder(0, 0, 0, 0));
        this.add(jsp);
        this.setBackground(Color.white);

        renderTableData();

    }

    public void setTableData(Object[][] data) {
        this.data = data;
        renderTableData();
    }

    public void renderTableData() {
        modelTable = new DefaultTableModel(data, str) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table.setModel(modelTable);
        formatColumn();
    }

    public void formatColumn() {
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.getColumnModel().getColumn(0).setPreferredWidth(100);  // stt
        table.getColumnModel().getColumn(1).setPreferredWidth(500);  // stt
        table.getColumnModel().getColumn(2).setPreferredWidth(200);  // stt
        table.getColumnModel().getColumn(3).setPreferredWidth(290);  // stt
    }
}
