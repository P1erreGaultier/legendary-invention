package com.extensions.LogMonitor;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

/**
 * Created by E113209D on 24/03/16.
 */
public class ColorCellRenderer extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component cell= super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if(table.getValueAt(row,column).toString().equals("NORMAL") ){
            super.getTableCellRendererComponent(table,value,isSelected,hasFocus,row,column).setBackground(Color.GREEN);
        }else if(table.getValueAt(row,column).toString().equals("WARNING") ){
            super.getTableCellRendererComponent(table,value,isSelected,hasFocus,row,column).setBackground(Color.YELLOW);
        }else if(table.getValueAt(row,column).toString().equals("CRITICAL") ){
            super.getTableCellRendererComponent(table,value,isSelected,hasFocus,row,column).setBackground(Color.RED);
        }

        return cell;
    }
}
