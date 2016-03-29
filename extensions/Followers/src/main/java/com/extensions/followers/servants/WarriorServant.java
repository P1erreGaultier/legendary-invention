package com.extensions.followers.servants;

import com.alma.application.interfaces.monster.IMonster;

/**
 * Classe représentant un servant guerrier
 */
public class WarriorServant extends Servant {

    public WarriorServant(String nom, String logoFileName) {
        super(nom, "Warrior", logoFileName, 10, 20);
    }

    @Override
    public void affectMonster(IMonster monster) {
        monster.setHp(monster.getHp() - (1 * level));
    }

    @Override
    public void upgrade() {
        level += 1;
        System.out.println("La puissance de " + name + " est accrue !");
    }
}
