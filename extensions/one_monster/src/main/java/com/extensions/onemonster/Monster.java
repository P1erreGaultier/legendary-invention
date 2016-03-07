
package com.extensions.onemonster;

import com.alma.application.interfaces.monster.IMonster;

public class Monster implements IMonster{

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

}

