package com.extensions.followers.servants;

import com.alma.application.interfaces.monster.IMonster;
import com.alma.platform.monitor.Log;
import com.alma.platform.monitor.LogLevel;
import com.alma.platform.monitor.Monitor;

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
        Monitor.getInstance().addLog(new Log(LogLevel.NORMAL, this.getClass().getName(), "La puissance de " + name + " est accrue !"));
    }
}
