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
        m.setHp(1 + randomgenerator.nextInt(20));
        m.setImage(this.getClass().getResource("monster.png").getPath());
        /*switch (randomgenerator.nextInt(4)){
            case 0:
                m.setImage(this.getClass().getResource("monster.png").getPath());
                break;
            case 1:

                m.setImage(this.getClass().getResource("monster.png").getPath());
                break;
            case 2:

                m.setImage(this.getClass().getResource("monster.png").getPath());
                break;
            case 3:

                m.setImage(this.getClass().getResource("monster.png").getPath());
                break;
            default:
                break;
        }*/
        return m;
    }
}
