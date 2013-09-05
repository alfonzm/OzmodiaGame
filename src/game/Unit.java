package game;

public abstract class Unit {
	
	private int level;
	private int health;
	private int mana;
	private int str;
	private int dex;
	private int intl;
	
	private String name;
	
	public Unit(int level,int health,int mana,int str,int dex,int intl, String name){
		this.setLevel(level);
		this.setHealth(health);
		this.setMana(mana);
		this.setStr(str);
		this.setDex(dex);
		this.setIntl(intl);
		this.setName(name);
	}
	
	public int getStr() {
		return str;
	}
	public void setStr(int str) {
		this.str = str;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getHealth() {
		return health;
	}
	public void setHealth(int health) {
		this.health = health;
	}
	public int getMana() {
		return mana;
	}
	public void setMana(int mana) {
		this.mana = mana;
	}
	public int getDex() {
		return dex;
	}
	public void setDex(int dex) {
		this.dex = dex;
	}
	public int getIntl() {
		return intl;
	}
	public void setIntl(int intl) {
		this.intl = intl;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	
}
