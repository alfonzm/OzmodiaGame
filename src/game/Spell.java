package game;

public class Spell {
	
	public static enum spellType{
		damage,buff,debuff;
	}
	public static enum spellTarget{
		self,enemy;
	}
	public static enum spellEffect{
		burn,freeze,shock,poison,noEffect,buffStr,buffDex,buffInt;
	}
	
	private String name;
	private int baseDamage;
	private spellType type;
	private spellTarget target;
	private spellEffect effect;
	
	public Spell(String name,int baseDamage,spellType type,spellTarget target,spellEffect effect){
		setName(name);
		setBaseDamage(baseDamage);
		setType(type);
		setTarget(target);
		setEffect(effect);
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getBaseDamage() {
		return baseDamage;
	}
	public void setBaseDamage(int baseDamage) {
		this.baseDamage = baseDamage;
	}
	public spellType getType() {
		return type;
	}
	public void setType(spellType type) {
		this.type = type;
	}

	public spellTarget getTarget() {
		return target;
	}

	public void setTarget(spellTarget target) {
		this.target = target;
	}

	public spellEffect getEffect() {
		return effect;
	}

	public void setEffect(spellEffect effect) {
		this.effect = effect;
	}
}
