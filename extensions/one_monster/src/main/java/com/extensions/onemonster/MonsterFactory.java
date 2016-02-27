package com.extensions.onemonster;

import java.util.Random;

import com.alma.application.interfaces.IMonster;
import com.alma.application.interfaces.IMonsterFactory;

public class MonsterFactory implements IMonsterFactory {

    public MonsterFactory() {

    }

    public IMonster createMonster20(){
        Random randomgenerator = new Random();
        Monster m = new Monster();
        m.setHp(1+randomgenerator.nextInt(20));
        switch (randomgenerator.nextInt(4)){
            case 0:
                m.setImage("src/main/java/com/extensions/onemonster/352ec142.png");
                break;
            case 1:
                m.setImage("src/main/java/com/extensions/onemonster/352ec142.png");
                break;
            case 2:
                m.setImage("src/main/java/com/extensions/onemonster/352ec142.png");
                break;
            case 3:
                m.setImage("src/main/java/com/extensions/onemonster/352ec142.png");
                break;
            default:
                break;
        }

        return m;
    }
}
