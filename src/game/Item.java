package game;

public class Item {

	public static enum itemType{
		helm, shield, greaves, armor, weapon, consumable;
	}
	
	private String name;
	private int value, price;
	private itemType type;
	
	public Item(String name, int value, itemType type, int price){
		setName(name);
		setValue(value);
		setType(type);
		setPrice(price);
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public itemType getType() {
		return type;
	}
	public void setType(itemType type) {
		this.type = type;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
}
