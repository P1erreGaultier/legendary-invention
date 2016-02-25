package com.alma.application;

import javax.imageio.ImageIO;
import javax.swing.*;

import com.alma.application.data.Monster;
import com.alma.plateform.*;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

	public class FirstTry extends JFrame {

			Plateform platform;
			private Monster m1;

	    public FirstTry(Monster m) {
					try{
						platform = platform.getInstance();
					}catch(Exception e){
						System.out.println("PROBLEM WHEN LOAD PLATFORM");
					}

					if(platform != null){
						System.out.println("PLATFORM LOADED !");
					}else{
						System.out.println("PLATFORM NOTLOADED !");
					}
	    		m1 = m;
	        initUI();
	    }

	    private void initUI() {
	    	
	        JPanel panel = new JPanel();

	        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
	        panel.setLayout(new GridLayout(2,1));
	        
	        JMenuBar menubar = new JMenuBar();
	        JMenu file = new JMenu("File");
	        file.setMnemonic(KeyEvent.VK_F);

	        JMenuItem eMenuItem = new JMenuItem("Exit");
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
	           
	        
	        final JTextArea area = new JTextArea(Integer.toString(m1.getHp()));
	        area.setPreferredSize(new Dimension(100, 100));
	        area.setFont(new Font("Calibri",Font.PLAIN,80));
	        
	        JLabel label = null;
	        try {
	            BufferedImage img = ImageIO.read(new File(m1.getImage()));
	            ImageIcon icon = new ImageIcon(img);
	            label = new JLabel(icon);
	         } catch (IOException e) {
	            e.printStackTrace();
	         }
	        
	        label.addMouseListener( new MouseAdapter() {
	            public void mouseClicked(MouseEvent e) {
	            	if((Integer.parseInt(area.getText())-1)>=0){
	            		area.setText(Integer.toString((Integer.parseInt(area.getText())-1))); //ligne du vomi
	            	}
	            	if(area.getText().equals("0")){
	            		JOptionPane.showMessageDialog(null, "My Goodness, THIS IS THE GGWP");
	            	}
	            }
	          });
	        panel.add(label);
	        panel.add(area);

	        add(panel);

	        pack();

	        setTitle("Clicker");
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setLocationRelativeTo(null);
	    }

	    
	
	 public static void main(String[] args) {

	        EventQueue.invokeLater(new Runnable() {
	        
	            @Override
	            public void run() {
	            	Monster m = new Monster(12, "src/main/java/com/alma/application/352ec142.png");
	                FirstTry ex = new FirstTry(m);
	                ex.setVisible(true);
	            }
	        });
	    }
	}
