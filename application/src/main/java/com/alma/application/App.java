package com.alma.application;

import javax.imageio.ImageIO;
import javax.swing.*;

import com.alma.application.interfaces.IAdditionnalPanel;
import com.alma.application.interfaces.handler.IClickHandler;
import com.alma.application.interfaces.monster.IMonster;
import com.alma.application.interfaces.monster.IMonsterFactory;
import com.alma.platform.Platform;
import com.alma.platform.exceptions.NoSavedInstanceException;
import com.alma.platform.exceptions.PropertyNotFoundException;

import java.awt.Dimension;
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

    public static final String extpath = "com.alma.application.interfaces";

    private IMonster currentMonster;
    private JPanel contentPanel;
    private JPanel mainPanel;
    private List<IMonsterFactory> factories;
    private List<IClickHandler> handlers;
    private List<IAdditionnalPanel> additionnalPanels;
    private Random randomGenerator;

    public App() {
        factories = new ArrayList<>();
        handlers = new ArrayList<>();
        additionnalPanels = new ArrayList<>();

        try {
            for(String factory_name: Platform.getInstance().getByInterface(extpath + ".monster.IMonsterFactory")) {
                factories.add((IMonsterFactory) Platform.getInstance().getExtension(factory_name));
            }

            for(String handler_name : Platform.getInstance().getByInterface(extpath + ".handler.IClickHandler")) {
                handlers.add((IClickHandler) Platform.getInstance().getExtension(handler_name));
            }

            for(String panel_name : Platform.getInstance().getByInterface(extpath + ".IAdditionnalPanel")) {
                additionnalPanels.add((IAdditionnalPanel) Platform.getInstance().getExtension(panel_name));
            }

        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | MalformedURLException | PropertyNotFoundException | NoSavedInstanceException e) {
            e.printStackTrace();
        }

        // on crée le panel container
        contentPanel = new JPanel();
        contentPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        contentPanel.setLayout(new GridLayout(1, additionnalPanels.size() + 1));

        // on crée le panel principal
        mainPanel = new JPanel();

        mainPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        mainPanel.setLayout(new GridLayout(2,1));

        // on init le panel principal
        initMainPanel();

        // on ajoute tous les panels au contentPanel dans l'ordre suivant :
        // IAdditionnalPanel alignés à gauche, mainPanel puis IAdditionnalPanel alignés à droite
        for(IAdditionnalPanel panel : additionnalPanels) {
            if(panel.isLeftAlign()) {
                contentPanel.add(panel.getPanel());
            }
        }
        contentPanel.add(mainPanel);
        for(IAdditionnalPanel panel : additionnalPanels) {
            if(panel.isRightAlign()) {
                contentPanel.add(panel.getPanel());
            }
        }

        // on ajoute le main pannel et on configure le reste de la frame
        add(contentPanel);
        pack();
        setTitle("Clicker");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initMainPanel() {

        // on tire au random un producteur de monstrers
        randomGenerator = new Random();
        currentMonster = factories.get(randomGenerator.nextInt(factories.size())).createMonster20();

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

        final JTextArea area = new JTextArea(Integer.toString(currentMonster.getHp()));
        area.setPreferredSize(new Dimension(100, 100));
        area.setFont(new Font("Calibri",Font.PLAIN,80));
        area.setEditable(false);
        final JLabel label = new JLabel();
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(currentMonster.getImage()));
            ImageIcon icon = new ImageIcon(img);
            label.setIcon(icon);
         } catch (IOException e) {
            e.printStackTrace();
         }

        assert(label!=null);
        label.addMouseListener( new MouseAdapter(){
            public void mouseClicked(MouseEvent e) {
            currentMonster.setHp(currentMonster.getHp() - 1);
            if(currentMonster.getHp() >= 0){
                area.setText(Integer.toString(currentMonster.getHp()));
            }

            if(area.getText().equals("0")){
                JOptionPane.showMessageDialog(null, "Victory!!!");
                currentMonster = factories.get(randomGenerator.nextInt(factories.size())).createMonster20();
                area.setText(Integer.toString(currentMonster.getHp()));
                label.setIcon(new ImageIcon((currentMonster.getImage())));
            }
            }
          });

        for(IClickHandler handler : handlers) {
            handler.setHandler(label);
        }
        mainPanel.add(label);
        mainPanel.add(area);
    }
}
