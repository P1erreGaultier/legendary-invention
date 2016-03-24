package com.extensions.LogMonitor;


import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LogTableModele extends AbstractTableModel {
    private final String[] headers = { "Level","Date", "Origin's class", "Message" };
    public List<LogForTable> logs;
    List<Color> rowColours = Arrays.asList(
            Color.RED,
            Color.GREEN,
            Color.YELLOW
    );

    LogTableModele() {
        logs = new ArrayList<LogForTable>();
    }

    /**
     * FOR COLORING ROW WITH LEVEL MESSAGE
     * @param row
     * @param c
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
        return logs.size();
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

                case 3:
                    return String.class;

                case 2:
                    return String.class;

                case 4:
                    return String.class;

                default:
                    return Object.class;
            }
        }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {

            case 0:

                return logs.get(rowIndex).getLevel();

            case 1:

                return logs.get(rowIndex).getTimestamp();

            case 2:

                return logs.get(rowIndex).getOrigin();

            case 3:

                return logs.get(rowIndex).getMessage();

            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    public String getColumnName(int columnIndex) {
        return headers[columnIndex];
    }
}
