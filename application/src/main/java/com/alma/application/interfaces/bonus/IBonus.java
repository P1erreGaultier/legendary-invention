package com.alma.application.interfaces.bonus;

import com.alma.application.interfaces.monster.IMonster;

/**
 * Created by E122371M on 10/03/16.
 */
public interface IBonus {

    String getImage();

    void action(IMonster monstre);
}
