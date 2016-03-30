package com.extensions.followers.servants;

import com.alma.application.interfaces.monster.IMonster;
import com.alma.platform.monitor.Log;
import com.alma.platform.monitor.LogLevel;
import com.alma.platform.monitor.Monitor;

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
        Monitor.getInstance().addLog(new Log(LogLevel.NORMAL, this.getClass().getName(), "La puissance de " + name + " est accrue !"));
    }
}
