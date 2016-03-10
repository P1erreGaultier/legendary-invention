package com.alma.application;

import javax.imageio.ImageIO;
import javax.swing.*;

import com.alma.application.interfaces.handler.IClickHandler;
import com.alma.application.interfaces.monster.IMonster;
import com.alma.application.interfaces.monster.IMonsterFactory;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class App extends JFrame {

	Platform platform;
	private IMonster m1;
	private List<IMonsterFactory> factories;
	private List<IClickHandler> handlers;

	    public App() {
            factories = new ArrayList<>();
            handlers = new ArrayList<>();

            try {
                for(String factory_name: Platform.getInstance().getByInterface("com.alma.application.interfaces.monster.IMonsterFactory")) {
                    factories.add((IMonsterFactory) Platform.getInstance().getExtension(factory_name));
                }

                for(String handler_name : Platform.getInstance().getByInterface("com.alma.application.interfaces.handler.IClickHandler")) {
                    handlers.add((IClickHandler) Platform.getInstance().getExtension(handler_name));
                }
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | MalformedURLException | PropertyNotFound e) {
                e.printStackTrace();
            }

            // on tire au random un producteur
            Random randomgenerator = new Random();
            m1 = factories.get(randomgenerator.nextInt(factories.size())).createMonster20();
            m1.setHp(10);

			initUI();

           setVisible(true);
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

            for(IClickHandler handler : handlers) {
                handler.setHandler(label);
            }
	        panel.add(label);
	        panel.add(area);

	        add(panel);

	        pack();

	        setTitle("Clicker");
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setLocationRelativeTo(null);
	    }
}
