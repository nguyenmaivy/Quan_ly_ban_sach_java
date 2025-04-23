
package gui.Panel;

import Components.homeForm1;
import Components.homeForm2;
import Components.homeForm3;
import javax.swing.JPanel;
import bus.ThongKeBUS;

public class ThongKe extends JPanel{
    private homeForm1 home1;
    private homeForm2 home2;
    private homeForm3 home3;
    private ThongKeBUS thongKeBUS;
    public ThongKe(){
        init();
    }
    public void init(){
        setLayout(null);
        thongKeBUS = new ThongKeBUS();
        
        // form thống kê biểu đồ cột
        home1 = new homeForm1();
        home1.setBounds(10, 10, 550, 320);
        this.add(home1);
        thongKeBUS.loadBarChartData(home1);
        
        //form thống kê biểu đồ tròn
        home2 = new homeForm2();
        home2.setBounds(570, 10, 550, 320);
        this.add(home2);
        thongKeBUS.loadPieChartData(home2);
        
        //form hiện bảng thống kê
        home3 = new homeForm3();
        home3.setBounds(10, 340, 1110, 600);
        this.add(home3);
        thongKeBUS.loadTableData(home3);
    }
    public homeForm1 getForm1(){
        return this.home1;
    }
    public homeForm2 getForm2(){
        return this.home2;
    }
    public homeForm3 getForm3(){
        return this.home3;
    }
}
