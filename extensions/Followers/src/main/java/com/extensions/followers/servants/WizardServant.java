package com.extensions.followers.servants;

import com.alma.application.interfaces.monster.IMonster;

/**
 * Classe représentant un servant magicien
 */
public class WizardServant extends Servant{

    public WizardServant(String name, String logoFileName) {
        super(name, "Magicien", logoFileName, 20, 100);
    }

    @Override
    public void affectMonster(IMonster monster) {
        monster.setHp(monster.getHp() - (10 * level));
    }

    @Override
    public void upgrade() {
        level *= 2;
        System.out.println("La puissance de " + name + "aumgente !");
    }
}
