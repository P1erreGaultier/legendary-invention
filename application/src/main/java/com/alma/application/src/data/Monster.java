package data;

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
	public Monster(int hp, String image) {
		super();
		this.hp = hp;
		this.image = image;
	}
	@Override
	public String toString() {
		return "Monster [hp=" + hp + ", image=" + image + "]";
	}

}
