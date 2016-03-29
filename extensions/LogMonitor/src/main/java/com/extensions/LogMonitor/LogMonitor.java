package com.extensions.LogMonitor;


import com.alma.platform.monitor.Log;
import com.alma.platform.monitor.LogObserver;
import com.alma.platform.monitor.Monitor;

import javax.swing.*;
import java.awt.*;

public class LogMonitor extends JFrame{

    boolean log_status;
    private JPanel panelTop,panelBot;
    private JTable logTable;
    private LogTableModele model;
    private JTable statTable;
    private StatTableModele stat_model;
    LogMonitor frame;



    void setLogOn(){
        log_status=true;
    }

    void setLogOff(){
        log_status=false;
    }



    public LogMonitor() {
        super();
        this.frame = this;
        frame.log_status=false;
        frame.setTitle("LogMonitor");
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        panelTop = new JPanel(new BorderLayout());
        panelBot = new JPanel(new BorderLayout());

        frame.model = new LogTableModele();
        frame.logTable = new JTable(model);
        frame.stat_model = new StatTableModele();
        frame.statTable = new JTable(stat_model);

        Color mycolor = new Color(230, 221, 249);
        frame.logTable.setOpaque(true);
        frame.logTable.setFillsViewportHeight(true);
        frame.logTable.setBackground(mycolor);
        frame.statTable.setOpaque(true);
        frame.statTable.setFillsViewportHeight(true);
        frame.statTable.setBackground(mycolor);

        Monitor.getInstance().addLogListener(new LogObserver() {
            @Override
            public void execute(Log s) {
                //S IS THE LOG
                LogForTable l = new LogForTable();
                l.setLevel(s.getLevel().toString());
                l.setTimestamp(s.getTimestamp().toString());
                l.setOrigin(s.getOriginClassName());
                l.setMessage(s.getMessage());
                frame.model.logs.add(l);

                frame.logTable.getColumnModel().getColumn(0).setCellRenderer(new ColorCellRenderer());
                frame.logTable = new JTable(model);

                SwingUtilities.updateComponentTreeUI(frame);
            }
        });
        /*
        Monitor.getInstance().addLogListener(new LogObserver() {
            @Override
            public void execute(Log s) {
                //S IS THE LOG
                LogForTable l = new LogForTable();
                l.setLevel(s.getLevel().toString());
                l.setTimestamp(s.getTimestamp().toString());
                l.setOrigin(s.getOriginClassName());
                l.setMessage(s.getMessage());


                SwingUtilities.updateComponentTreeUI(frame);
            }
        });
        */
        panelTop.add(new JScrollPane(logTable));
        panelBot.add(new JScrollPane(statTable));
        frame.add(panelTop, BorderLayout.NORTH);
        frame.add(panelBot, BorderLayout.SOUTH);

        pack();
        frame.setVisible(true);
    }
}
