package com.extensions.LogMonitor;

import com.alma.application.interfaces.handler.IClickHandler;
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
    JTextArea area;
    JPanel panel;
    JMenuBar menubar;
    JMenu file;
    JMenuItem eMenuItem;
    void setLogOn(){
        log_status=true;
    }

    void setLogOff(){
        log_status=false;
    }



    public LogMonitor() {
        log_status=false;

        panel = new JPanel();

        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setLayout(new GridLayout(1,1));

        menubar = new JMenuBar();
        file = new JMenu("File");
        file.setMnemonic(KeyEvent.VK_F);

        eMenuItem = new JMenuItem("Exit");
        eMenuItem.setMnemonic(KeyEvent.VK_E);
        eMenuItem.setToolTipText("Exit application");
        eMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        });

        file.add(eMenuItem);
        menubar.add(file);

        setJMenuBar(menubar);

        area = new JTextArea("DEFAULT LOG");
        area.setPreferredSize(new Dimension(500, 300));
        area.setFont(new Font("Calibri",Font.PLAIN,20));

        Monitor.getInstance().addLogListener(new LogObserver() {
            @Override
            public void execute(String s) {
                //S IS THE LOG
                Date t = new Date();
                String date_string = t.toString();
                String new_log = "[LogMonitorExtension]:"+date_string+s;
                System.out.println(new_log);
                area.setText(new_log);
                panel.add(area);
            }
        });


        panel.add(area);

        add(panel);

        pack();

        setTitle("LogMonitor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }

    public static void main(String[] args) {
        LogMonitor l = new LogMonitor();
    }


}
