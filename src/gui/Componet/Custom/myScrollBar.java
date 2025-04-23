/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui.Componet.Custom;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JScrollBar;

public class myScrollBar extends JScrollBar {

    public myScrollBar() {
        setUI(new myDesignScrollBar());
        setPreferredSize(new Dimension(5, 0));     // thanh truot doc
        setBackground(new Color(242, 242, 242));
        setUnitIncrement(16);                     // tốc độ trượt của bảng
    }
}
