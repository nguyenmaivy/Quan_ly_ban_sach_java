
package gui.Panel;

import gui.Componet.Custom.IntegratedSearch;
import gui.Componet.Custom.MainFunction;
import gui.Componet.Custom.PanelBorderRadius;
import gui.Main;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;


public class TacGia extends JPanel implements ActionListener{
    PanelBorderRadius main, functionBar;
    JPanel contentCenter;
    JTable tbltacgia;
    JScrollPane scrollTable;
    MainFunction mainFunction;
    IntegratedSearch search;
    DefaultTableModel tbModel;
    
    Main m;
    Color BackgroundColor = new Color(240, 247, 250);
    
    public TacGia(Main m){
        this.m = m;
        initComponent();
    }
    
    private void initComponent(){
        this.setBackground(BackgroundColor);
        this.setLayout(new GridLayout(1, 1));
        this.setBorder(new EmptyBorder(10, 10, 10, 10));
        this.setOpaque(true);
        
        contentCenter = new JPanel();
        contentCenter.setPreferredSize(new Dimension(1100, 600));
        contentCenter.setBackground(BackgroundColor);
        contentCenter.setLayout(new BorderLayout(10, 20));
        this.add(contentCenter);
        
        // functionBar chứa các nút thực hiện: xóa, sửa...
        functionBar = new PanelBorderRadius();
        functionBar.setPreferredSize(new Dimension(0, 100));
        functionBar.setLayout(new GridLayout(1, 2, 50, 0));
        functionBar.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        String[] acStrings = {"create", "update", "delete", "detail", "export"};
        mainFunction = new MainFunction(SOMEBITS, "tacgia", acStrings);
        for(String ac : acStrings){
            mainFunction.btn.get(ac).addActionListener(this);
        }
        functionBar.add(mainFunction);
        
        search = new IntegratedSearch(new String[]{"Tất cả"});
        functionBar.add(search);
        
        contentCenter.add(functionBar, BorderLayout.NORTH);
        
        //main: phần ở dưới để bảng
        main = new PanelBorderRadius();
        BoxLayout boxLayout = new BoxLayout(main, BoxLayout.Y_AXIS);
        main.setLayout(boxLayout);
        contentCenter.add(main, BorderLayout.CENTER);
        
        tbltacgia = new JTable();
        tbltacgia.setDefaultEditor(Object.class, null);
        scrollTable = new JScrollPane();
        tbModel = new DefaultTableModel();
        String[] header = new String[]{"Mã tác giả", "Tên tác giả", "Liên lạc"};
        tbModel.setColumnIdentifiers(header);
        tbltacgia.setModel(tbModel);
        scrollTable.setViewportView(tbltacgia);
        DefaultTableCellRenderer cenCellRenderer = new DefaultTableCellRenderer();
        cenCellRenderer.setHorizontalAlignment(JLabel.CENTER);
        TableColumnModel columnModel = tbltacgia.getColumnModel();
        columnModel.getColumn(0).setCellRenderer(cenCellRenderer);
        columnModel.getColumn(0).setPreferredWidth(50);
        columnModel.getColumn(1).setCellRenderer(cenCellRenderer);
        columnModel.getColumn(1).setPreferredWidth(300);
        columnModel.getColumn(2).setCellRenderer(cenCellRenderer);
        columnModel.getColumn(2).setPreferredWidth(200);
        tbltacgia.setFocusable(false);
        main.add(scrollTable);
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
