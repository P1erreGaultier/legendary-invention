
package com.extensions.warriorservant;

import com.alma.application.interfaces.pnj.IServant;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class WarriorServant implements IServant {

    private String name;
    private int price;
    private boolean is_hired;

    public WarriorServant() {
        name = "Sonya la barbare";
        price = 10;
        is_hired = false;
    }

    @Override
    public void hire(int total_gold) {
        if( total_gold < price) {
            System.err.println("Erreur : vous n'avez pas assez d'or pour embaucher ce servant");
        } else {
            System.out.println(name + " est maintenant à votre service !");
            is_hired = true;
        }
    }

    @Override
    public void act(JLabel click) {
        if(is_hired) {
            click.addMouseListener( new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    System.out.println("Allez mec, tu peux le faire !");
                }
            });
        }
    }
}

