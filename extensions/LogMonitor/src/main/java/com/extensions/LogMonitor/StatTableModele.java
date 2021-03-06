package com.extensions.LogMonitor;


import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Model used to print method call number
 */
public class StatTableModele extends AbstractTableModel {
    /**
     * HEADER
     */
    private final String[] headers = { "Extension","Instance","Method", "Call Number" };
    /**
     * DATA
     */
    public List<StatForTable> stats;
    /**
     * COLOR
     */
    List<Color> rowColours = Arrays.asList(
            Color.RED,
            Color.GREEN,
            Color.YELLOW
    );

    /**
     * CONSTRUCTOR
     */
    StatTableModele() {
        stats = new ArrayList<StatForTable>();
    }



    /**
     * FOR COLORING ROW WITH LEVEL MESSAGE
     */
    public void setRowColour(int row, Color c) {
        rowColours.set(row, c);
        fireTableRowsUpdated(row, row);
    }
    public Color getRowColour(int row) {
        return rowColours.get(row);
    }

    @Override
    public int getRowCount() {
        return stats.size();
    }

    @Override
    public int getColumnCount() {
        return headers.length;
    }

    @Override
        public Class<?> getColumnClass(int columnIndex) {
            switch (columnIndex) {
                case 0:
                case 1:
                    return String.class;
                case 2:
                    return String.class;

                case 3:
                    return String.class;

                case 4:
                    return Integer.class;

                default:
                    return Object.class;
            }
        }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return stats.get(rowIndex).getExtensionName();
            case 1:
                return stats.get(rowIndex).getInstanceName();

            case 2:
                return stats.get(rowIndex).getMethodName();

            case 3:
                return stats.get(rowIndex).getNbCall();

            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    public String getColumnName(int columnIndex) {
        return headers[columnIndex];
    }
}
