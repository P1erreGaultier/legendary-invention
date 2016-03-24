package com.extensions.LogMonitor;

import com.alma.application.interfaces.handler.IClickHandler;
import com.alma.platform.monitor.Log;
import com.alma.platform.monitor.LogObserver;
import com.alma.platform.monitor.Monitor;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;

public class LogMonitor extends JFrame{

    boolean log_status;

    //WINDOW
    private JTextArea area;
    private JPanel panel;
    private JMenuBar menubar;
    private JMenu file;
    private JMenuItem eMenuItem;
    private JTable logTable;
    private LogTableModele model;
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

        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);



        frame.model = new LogTableModele();
        frame.logTable = new JTable(model);
        frame.logTable.setAutoCreateRowSorter(true);

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
                frame.logTable.setAutoCreateRowSorter(true);
                SwingUtilities.updateComponentTreeUI(frame);
            }
        });



        frame.getContentPane().add(new JScrollPane(logTable));
        pack();
        frame.setVisible(true);
    }
}
