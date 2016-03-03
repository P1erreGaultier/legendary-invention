package com.alma.application;

import javax.imageio.ImageIO;
import javax.swing.*;

import com.alma.application.interfaces.IMonster;
import com.alma.application.interfaces.IMonsterFactory;
import com.alma.platform.Platform;
import com.alma.platform.exceptions.PropertyNotFound;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

public class FirstTry extends JFrame {

			Platform platform;
			private IMonster m1;

	    public FirstTry() {
					try{
						platform = Platform.getInstance();
					}catch(Exception e){
						System.out.println("PROBLEM WHEN LOAD PLATFORM");
					}

					if(platform != null){
						System.out.println("PLATFORM LOADED !");
					}else{
						System.out.println("PLATFORM NOTLOADED !");
					}
            try {

				IMonsterFactory facto = (IMonsterFactory) Platform.getInstance().getExtension("one_monster");
                m1 = facto.createMonster20();
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | MalformedURLException | PropertyNotFound e) {
                e.printStackTrace();
            }

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
	                FirstTry ex = new FirstTry();
	                ex.setVisible(true);
	            }
	        });
	    }
	}
