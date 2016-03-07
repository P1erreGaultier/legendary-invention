package com.extensions.onemonster;

import java.util.Random;
import com.alma.application.interfaces.IMonster;
import com.alma.application.interfaces.IMonsterFactory;

public class MonsterFactory implements IMonsterFactory {

    public MonsterFactory() {
    }

    public IMonster createMonster20(){
        Random randomgenerator = new Random();
        IMonster m = new Monster();

        switch (randomgenerator.nextInt(4)){
            case 0:
                m.setImage(this.getClass().getResource("bob.png").getPath());
                break;
            case 1:

                m.setImage(this.getClass().getResource("gremlins-gizmo.jpg").getPath());
                break;
            case 2:

                m.setImage(this.getClass().getResource("pascal.jpg").getPath());
                break;
            case 3:

                m.setImage(this.getClass().getResource("terriblvert.jpg").getPath());
                break;
            default:
                break;
        }
        return m;
    }
}
