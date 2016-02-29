package com.extensions.onemonster;

import java.io.File;
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
        File file;
        String absolutePath = "";
        switch (randomgenerator.nextInt(4)){
            case 0:

                m.setImage(absolutePath);
                break;
            case 1:

                m.setImage(absolutePath);
                break;
            case 2:

                m.setImage(absolutePath);
                break;
            case 3:

                m.setImage(absolutePath);
                break;
            default:
                break;
        }
        File f = null;
        try{
            String s = getClass().getName();
            int i = s.lastIndexOf(".");
            if(i > -1) s = s.substring(i + 1);
            s = s + ".class";
            System.out.println("name " +s);
            File current = new File(this.getClass().getResource(s).getPath());
            //System.out.println(current.getParentFile().getParentFile().getParentFile().getParentFile());
            System.out.println(this.getClass().getResource("352ec142.png"));
        }catch(Exception e){
            System.out.println("ERREUR FILE LKJZHLZKHKJZHZKJHJKDHZKJDHKJ"+e);
        }
        System.out.println("PATH OF IMAGE:"+f.getAbsolutePath());
        m.setImage(f.getAbsolutePath());



        return m;
    }
}
