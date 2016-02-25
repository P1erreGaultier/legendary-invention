
package com.onemonster;

import java.util.Random;

public class Monster {

	private int hp;
	private String image;
	
	public int getHp() {
		return hp;
	}
	public void setHp(int hp) {
		this.hp = hp;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
    public Monster() {
        hp = 0;
        image = "";
    }
	public Monster(int hp, String image) {
		this.hp = hp;
		this.image = image;
	}
	@Override
	public String toString() {
		return "Monster [hp=" + hp + ", image=" + image + "]";
	}

   static public Monster createMonster20(){
        Random randomgenerator = new Random();
        Monster m = new Monster();
        m.setHp(1+randomgenerator.nextInt(20));
        switch (randomgenerator.nextInt(4)){
            case 0:
                m.setImage("src/main/java/com/alma/extension/onemonster/352ec142.png");
                break;
            case 1:
                m.setImage("src/main/java/com/alma/extension/onemonster/352ec142.png");
                break;
            case 2:
                m.setImage("src/main/java/com/alma/extension/onemonster/352ec142.png");
                break;
            case 3:
                m.setImage("src/main/java/com/alma/extension/onemonster/352ec142.png");
                break;
            default:
                break;
        }

        return m;
    }
}

