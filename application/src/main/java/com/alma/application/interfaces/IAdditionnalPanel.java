package com.alma.application.interfaces;

import com.alma.application.interfaces.monster.IMonster;

import javax.swing.*;

/**
 * Interface représentant un panel additionnel pour l'application
 */
public interface IAdditionnalPanel {

    /**
     * Indique si le panel doit être aligné à gauche
     * @return Vrai si le panel est aligné à gauche, Faux sinon
     */
    boolean isLeftAlign();

    /**
     * Indique si le panel est aligné à droite
     * @return Vrai si le panel est aligné à droite, Faux sinon
     */
    boolean isRightAlign();

    /**
     * Méthode qui applique des effets lié au panel sur un monstre
     * @param monster
     */
    void affectMonster(IMonster monster);

    /**
     * Accesseur sur le panel
     * @return
     */
    JPanel getPanel();
}
