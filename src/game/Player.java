package game;

public class Player extends Unit{

	public int[] learnedSkills;//Players can learn an infinite amount of skills para sayung lng i code haha
	public int[] inventory;//index = item ID and value = quantity
	private int gold;
	public int[] equippedItems;//index = slot and value = item ID 0=weapon 1=helm 2=shield 3=armor 4=greaves
	
	public Player(int level, int health, int mana, int str, int dex, int intl,int[] learnedSkills,int[] inventory,int gold,int[] equippedItems, String name) {
		super(level, health, mana, str, dex, intl, name);
		setLearnedSkills(learnedSkills);
		setInventory(inventory);
		setEquippedItems(new int[5]);
		setGold(gold);
		setEquippedItems(equippedItems);
	}
	
	public int[] getLearnedSkills() {
		return learnedSkills;
	}
	public void setLearnedSkills(int[] learnedSkills) {
		this.learnedSkills = learnedSkills;
	}


	public int[] getInventory() {
		return inventory;
	}

	public void setInventory(int[] inventory) {
		this.inventory = inventory;
	}

	public int[] getEquippedItems() {
		return equippedItems;
	}

	public void setEquippedItems(int[] equippedItems) {
		this.equippedItems = equippedItems;
	}

	public int getGold() {
		return gold;
	}

	public void setGold(int gold) {
		this.gold = gold;
	}


}
