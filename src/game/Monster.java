package game;

import org.newdawn.slick.Image;

public class Monster extends Unit{
	
	Image sprite;

	public Monster(int level, int health, int mana, int str, int dex, int intl, Image sprite, String name) {
		super(level, health, mana, str, dex, intl, name);
		
		this.sprite = sprite;
	}

}
