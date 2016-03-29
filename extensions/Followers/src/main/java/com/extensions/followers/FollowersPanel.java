package com.extensions.followers;

import com.alma.application.interfaces.IAdditionnalPanel;
import com.alma.application.interfaces.monster.IMonster;
import com.alma.platform.monitor.Log;
import com.alma.platform.monitor.LogLevel;
import com.alma.platform.monitor.Monitor;
import com.extensions.followers.servants.Servant;
import com.extensions.followers.servants.WarriorServant;
import com.extensions.followers.servants.WizardServant;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Classe représentant un panel permettant d'acheter des servants
 */
public class FollowersPanel implements IAdditionnalPanel {

    private JPanel panel;
    private Map<String, JLabel> servantStates;
    private Map<String, JLabel> servantLevels;
    private List<Servant> employableServants;
    private List<Servant> hiredServants;

    public FollowersPanel() {
        panel = new JPanel();
        employableServants = new ArrayList<>();
        hiredServants = new ArrayList<>();
        servantStates = new HashMap<>();
        servantLevels = new HashMap<>();

        // on ajoute des servants
        employableServants.add(new WarriorServant("Sonya", "sonya.png"));
        employableServants.add(new WizardServant("Kael'thas", "kaelthas.png"));

        // on initialise le panel
        initPanel();
    }

    /**
     * Méthode qui permet d'embaucher un servant
     * @param servant
     */
    private void hireServant(Servant servant) {
        if(employableServants.contains(servant)) {
            employableServants.remove(servant);
            hiredServants.add(servant);
        } else {
            Monitor.getInstance().addLog(new Log(LogLevel.WARNING, this.getClass().getName(), "Error : cannot hire a servant" + servant.toString() + " who isn't employable"));
        }
    }

    @Override
    public boolean isLeftAlign() {
        return true;
    }

    @Override
    public boolean isRightAlign() {
        return false;
    }

    @Override
    public void affectMonster(IMonster monster) {
        for(Servant servant : hiredServants) {
            servant.affectMonster(monster);
        }
    }

    @Override
    public JPanel getPanel() {
        return panel;
    }

    /**
     * Méthode initialisant le panel
     */
    private void initPanel() {
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        panel.setLayout(new GridLayout(employableServants.size(), 1));

        for(final Servant servant : employableServants) {
            JPanel servantPanel = new JPanel();
            servantPanel.setLayout(new FlowLayout());

            // on charge l'image du servant
            final JLabel servantImg = new JLabel();
            BufferedImage img = null;
            try {
                img = ImageIO.read(new File(servant.getLogoFileName()));
                ImageIcon icon = new ImageIcon(img);
                servantImg.setIcon(icon);
                servantPanel.add(servantImg);
            } catch (IOException e) {
                e.printStackTrace();
            }

            // on crée les textes pour le nom, le job du servant, son état et son niveau
            final JLabel servantName = new JLabel(servant.getName());
            final JLabel jobName = new JLabel(servant.getJobName());
            JLabel servantState = new JLabel("Non embauché");
            JLabel servantLevel = new JLabel("Niveau 0");
            servantPanel.add(servantName);
            servantPanel.add(jobName);
            servantPanel.add(servantLevel);
            servantPanel.add(servantState);

            // on sauvegarde l'état et le niveau du servant pour pouvoir les changer ultérieurement
            servantStates.put(servant.getName(), servantState);
            servantLevels.put(servant.getName(), servantLevel);

            // on crée les boutons "embaucher" et "améliorer"
            final JButton hireButton = new JButton("Embaucher pour " + servant.getHiringPrice() + " or");
            final JButton uppgradeButton = new JButton("Améliorer pour " + servant.getUppgradePrice() + " or");
            hireButton.setEnabled(true);
            uppgradeButton.setEnabled(false);

            // on bind les actions des boutons
            hireButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    hireServant(servant);
                    hireButton.setEnabled(false);
                    uppgradeButton.setEnabled(true);
                    // on met à jour le statut du servant
                    servantStates.get(servant.getName()).setText("Embauché");
                }
            });

            uppgradeButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int newLevel = servant.getLevel() + 1;
                    servant.upgrade();
                    servantLevels.get(servant.getName()).setText("Level " + newLevel);
                }
            });

            servantPanel.add(hireButton);
            servantPanel.add(uppgradeButton);

            panel.add(servantPanel);
        }
    }
}
